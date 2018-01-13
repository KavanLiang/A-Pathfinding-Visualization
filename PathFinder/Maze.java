import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A Maze for the Pathfinder to navigate through.
 *
 * @Author Kavan
 *
 * @param g: the goal of this maze
 * @param p: the Pathfinder that wants to navigate to the goal
 */
public class Maze extends World
{
    private Goal g;
    private Pathfinder p;


    /**
     * Creates a new maze of size 23x23 with a Pathfinder and Goal
     */
    public Maze(){
        super(23, 23, 50);
        g = new Goal();
        p = new Pathfinder();
        this.setPaintOrder(Pathfinder.class, Goal.class, Wall.class, Node.class);
        addObject(g, 0, 0);
        addObject(p, getWidth(), getHeight());
    }

    /**
     * Generates a random wall configuration on the maze
     * Note: there is no guaranteed path
     */
    public void generateMaze(){
        removeObjects(getObjects(Wall.class));
        int numWalls = (int)(Math.random()*getWidth()*getHeight()/2);
        for(int x = 0; x < numWalls; x++)
        {
            int i = (int)(Math.random()*getWidth());
            int j = (int)(Math.random()*getHeight());
            if(getObjectsAt(i,j, null).isEmpty())
                addObject(new Wall(), i, j);
        }
    }

    /**
     * @return the goal of this maze
     */
    public Goal getGoal(){
        return g;
    }
    public void act(){
        if(Greenfoot.isKeyDown("g")){
            generateMaze();
        }
    }
}

