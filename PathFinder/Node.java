import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * A* Node
 *
 * @PIXEL_SIZE: the size of this node
 * @parent_: the parent node that results in the lowest cost
 * @g_: the goal that heuristics are based off of
 * @FS: font size for labels
 * @heuristic_: the current best heuristic assigned to this Node
 * @x_: the x coordinate of this Node
 * @y_: the y coordinate of this Node
 * @d_: the approximate Manhattan distance to the goal from this Node
 * @hasNotBeenCheck: True if this Node has been checked; false otherwise
 * @valid: True if this Node is valid; i.e. if this node can be traversed; false otherwise
 */
public class Node extends Actor
{
    private final static int PIXEL_SIZE = 30;
    private Node parent_;
    private Goal g_;
    private int cost_;
    private static final int FS = 25;
    private double heuristic_;
    private int x_, y_;
    private double d_;
    private boolean hasNotBeenChecked;
    private boolean valid;

    /**
     * Create a new node
     * @param x: the x coordinate of this Node
     * @param y: the y coordinate of this Node
     * @param g: the Goal this Node will base heuristics off of
     * @param parent: the Parent Node to this node, if there is one;
     */
    public Node(int x, int y, Goal g, Node parent)
    {
        this.setImage(new GreenfootImage(PIXEL_SIZE, PIXEL_SIZE));
        x_ = x;
        parent_ = parent;
        cost_ = 1;//default
        g_ = g;
        y_ = y;
        hasNotBeenChecked = true;
        d_ = Math.abs(g.getX() - x_) + Math.abs(g.getY()-y_);
        heuristic_ = Double.MAX_VALUE;
    }

    /**
     * @return manhattan distance to the goal
     */
    public double getD()
    {
        return d_;
    }

    /**
     * @return the current best parent; i.e. the one with the lowest cost
     */
    public Node getParent()
    {
        return parent_;
    }

    /**
     * Checks if this Node is traverseable
     * @return: True: if this Node is not off-screen or in a wall
     */
    public boolean isInvalid()
    {
        if(hasNotBeenChecked)
        {
            hasNotBeenChecked = false;
            return valid = x_ < 0 || x_ > getWorld().getWidth() || y_ < 0 || y_ > getWorld().getHeight() || this.isTouching(Wall.class);
        }
        else
            return valid;
    }

    /**
     * checks if a Node is better than the parent, and replaces the parent if so.
     * @param n: the Node to be checked
     */
    public void updateHeuristic(Node n)//updates if the new parent is better
    {
        if(this.isInvalid())
        {
            return;
        }
        else if(n != null)
        {
            double posH = calcH(n);
            if(posH < heuristic_)
            {
                cost_ = n.cost_ + 1;
                parent_ = n;
                heuristic_ = posH;
                updateImage();
            }
        }
        else
        {
            heuristic_ = 1 + d_;
            updateImage();
        }
    }
    
    
    public void setFontColor(java.awt.Color c)//for debugging purposes
    {
        GreenfootImage img = new GreenfootImage(cost_ + "", FS, c, null);
        this.setImage(img);
    }

    /**
     * updates the image with the new cost, or heuristic
     */
    private void updateImage()
    {
        GreenfootImage img = new GreenfootImage(cost_ + "", FS, null, null);
        //GreenfootImage img = new GreenfootImage(cost_ + "", FS, null, null);
        this.setImage(img);
    }

    /**
     * Changes the color of this Node to display a path
     */
    public void displayPath()
    {
        GreenfootImage img = new GreenfootImage(PIXEL_SIZE, PIXEL_SIZE);
        img.setColor(Color.red);
        img.fill();
        this.setImage(img);
    }

    /**
     * Changes this Node to be a white square
     */
    public void clear()
    {
        this.setImage(new GreenfootImage(PIXEL_SIZE, PIXEL_SIZE));
    }

    /**
     * Check if this Node is at the goal
     * @return: True if this Node is at the Goal, False otherwise
     */
    public boolean isAtGoal()
    {
        return this.isTouching(Goal.class);
    }

    /**
     * Return the heuristic of this Node
     * @return: the current heuristic
     */
    public double getHeuristic()
    {
        return heuristic_;
    }

    /**
     * Calculates the heuristic given a Node n
     * "recursively" finds the cost of this node using the cost of a parent
     * - same idea as computing the fibonacci sequence
     * @param n
     * @return
     */
    public double calcH(Node n)
    {
        double posCost = n.cost_ + 1;
        return posCost + d_;
    }
}
