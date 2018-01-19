import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
import java.awt.Color;
/**
 * @Author Kavan
 * Pathfinder for A*
 * 
 * @frontier: arraylist of next nodes to be expanded, sorted by heuristic
 * @closed: boolean array of coordinates that have already been searched
 * @running: True if the pathfinder is searching for a path, false otherwise
 * @worldwidth_: the width of the world this pathfinder exists in, in px
 * @worldHeight_: the height of the world this pathfinder exists in, in px
 * @nodeMap: a 2D array containing all Nodes, indexed by x,y coordinates
 * @g: the goal this pathfinder is searching for
 */
public class Pathfinder extends Actor
{
    private ArrayList<Branch> frontier;
    private boolean[][] closed;
    private boolean running;
    private boolean initialized;
    private int worldWidth_, worldHeight_;
    private Node[][] nodeMap;
    private static final int BUFFER = 3, OFFSET = 1;
    private Goal g;

    /**
     * Creates a new pathfinder instance
     */
    public Pathfinder()
    {
        running = false;
        initialized = false;
    }

    /**
     * Takes key input to move the pathfinder around the world;
     */
    public void keys()
    {
        if(Greenfoot.isKeyDown("up"))
            if(getY() - OFFSET >= 0)
                setLocation(getX(), getY() - OFFSET);
        if(Greenfoot.isKeyDown("left"))
            if(getX() - OFFSET >= 0)
                setLocation(getX() - OFFSET, getY());
        if(Greenfoot.isKeyDown("down"))
            if(getY() + OFFSET < worldHeight_)
                setLocation(getX(), getY() + OFFSET);
        if(Greenfoot.isKeyDown("right"))
            if(getX() + OFFSET < worldWidth_)
                setLocation(getX() + OFFSET, getY());
    }

    /**
     * clears all collections and arrays pertaining to this pathfinder
     */
    public void clearSets()
    {
        nodeMap = new Node[worldWidth_][worldHeight_];
        getWorld().removeObjects(getWorld().getObjects(Node.class));
        for(int x = 0; x < worldWidth_; x++)
        {
            for(int y = 0; y < worldHeight_; y++)
            {
                nodeMap[x][y] = new Node(x, y, g, null);
                getWorld().addObject(nodeMap[x][y], x, y);
            }
        }
        frontier = new ArrayList(worldWidth_ * worldHeight_);
        closed = new boolean[worldWidth_][worldHeight_];
        frontier.add(new Branch(nodeMap[this.getX()][this.getY()]));
    }

    /**
     * Main backtracking method for backtracking; A* implementation
     */
    public void search()
    {
        for(int x = 0; x<frontier.size(); x++)//if a solution exists, backtrack through it and show the solution
        {
            Branch b = frontier.get(x);
            if(b.getNode().isAtGoal())
            {
                System.out.println("solved");
                System.out.println("Press 'c' to show the solution");
                while(!Greenfoot.isKeyDown("c"))
                {
                    Greenfoot.delay(OFFSET);
                }
                showSolution(b);
                running = false;
                return;
            }
        }
        if(frontier.size() <= 0)//there is nothing left to check in the frontier -> no solution
        {
            System.out.println("No path found");
            clearSets();
            running = false;
            return;
        }
        else//else expand the open list
        {
            Branch bestBranch = frontier.get(0);
            double optimalH_ = bestBranch.getNode().getHeuristic();
            int tempIndex = 0;
            for(int index = 0; index < frontier.size(); index++)
            {
                Branch checkB = frontier.get(index);
                double checkH = checkB.getNode().getHeuristic();
                if(checkH <= optimalH_)
                {
                    bestBranch = checkB;
                    optimalH_ = checkH;
                    tempIndex = index;
                }
            }
            frontier.remove(tempIndex);//"pop" the branch from where you want to expand from the open list
            closed[bestBranch.getNode().getX()][bestBranch.getNode().getY()] = true;//put the "popped" branch in the closed list so it won't be evaluated again
            bestBranch.getNode().setFontColor(Color.red);
            expandFrontier(bestBranch);//expand from the popped branch
        }
    }

    /**
     * Displays the solution for this path
     * @param end: the last branch of the path, in which the Node pertaining to this branch intersects the Goal
     */
    public void showSolution(Branch end)//iterates over the Nodes in the solution to display a path
    {
        for(Node[] row : nodeMap)
        {
            for(Node n : row)
                n.clear();
        }
        LinkedList<Node> path = new LinkedList();
        Node n = end.getNode();
        path.push(n);
        while(n != null)
        {
            n.displayPath();
            path.push(n);
            n = n.getParent();
        }
        for(int x = 0; x < path.size() - OFFSET; x++)
        {
            Node next = path.get(x);
            this.setLocation(next.getX(), next.getY());
            Greenfoot.delay(BUFFER);
            getWorld().removeObject(next);
        }
    }

    /**
     * Adds new branches to be explored to the frontier
     * @param b: the branch to expand from
     */
    private void expandFrontier(Branch b)//adds new Branches **does not include diagonals
    {
        int x = b.getNode().getX();
        int y = b.getNode().getY();
        if(x - OFFSET >= 0)
            if(!closed[x - OFFSET][y] && !nodeMap[x - OFFSET][y].isInvalid())
                frontier.add(new Branch(nodeMap[x - OFFSET][y]));
        if(x + OFFSET < nodeMap.length)
            if(!closed[x + OFFSET][y] && !nodeMap[x + OFFSET][y].isInvalid())
                frontier.add(new Branch(nodeMap[x + OFFSET][y]));
        if(y - OFFSET >= 0)
            if(!closed[x][y - OFFSET] && !nodeMap[x][y - OFFSET].isInvalid())
                frontier.add(new Branch(nodeMap[x][y - OFFSET]));
        if(y + OFFSET < nodeMap[0].length)
            if(!closed[x][y + OFFSET] && !nodeMap[x][y + OFFSET].isInvalid())
                frontier.add(new Branch(nodeMap[x][y + OFFSET]));
    }

    public void act()
    {
        if(!initialized)
        {
            worldWidth_ = getWorld().getWidth();
            worldHeight_ = getWorld().getHeight();
            g = ((Maze)getWorld()).getGoal();
        }
        if(running)
        {
            search();
        }
        else if(!running && Greenfoot.isKeyDown("space"))
        {
            clearSets();
            running = true;
        }
        else
        {
            getWorld().removeObjects(getWorld().getObjects(Node.class));
            keys();
        }
    }

    /**
     * A branch of Nodes
     * 
     * @node_: the main Node
     */
    private class Branch
    {
        private Node node_;

        /**
         * Creates a new Branch instance
         * @param n: the Node this Branch extends off of; the main Node
         */
        private Branch(Node n)
        {
            node_ = n;
            updateNeighbors();
        }

        /**
         * updates heuristics and costs for all neighbouring nodes to the main Node
         */
        private void updateNeighbors()//updates all the neighboring nodes to the main node
        {
            int x = node_.getX();
            int y = node_.getY();
            if(x - OFFSET >= 0)
                if(!closed[x - OFFSET][y])
                    nodeMap[x - OFFSET][y].updateHeuristic(this.getNode());
            if(x + OFFSET < nodeMap.length)
                if(!closed[x + OFFSET][y])
                    nodeMap[x + OFFSET][y].updateHeuristic(this.getNode());
            if(y - OFFSET >= 0)
                if(!closed[x][y - OFFSET])
                    nodeMap[x][y - OFFSET].updateHeuristic(this.getNode());
            if(y + OFFSET < nodeMap[0].length)
                if(!closed[x][y + OFFSET])
                    nodeMap[x][y + OFFSET].updateHeuristic(this.getNode());
        }

        /**
         * @return: the main Node
         */
        private Node getNode()
        {
            return node_;
        }
    }
}
