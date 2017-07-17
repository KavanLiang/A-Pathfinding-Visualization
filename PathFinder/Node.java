import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
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
    public double getD()
    {
        return d_;
    }
    public Node getParent()
    {
        return parent_;
    }
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
    private void updateImage()//updates the image with the new cost, or heuristic
    {
        GreenfootImage img = new GreenfootImage(cost_ + "", FS, null, null);
        //GreenfootImage img = new GreenfootImage(cost_ + "", FS, null, null);
        this.setImage(img);
    }
    public void displayPath()
    {
        GreenfootImage img = new GreenfootImage(PIXEL_SIZE, PIXEL_SIZE);
        img.setColor(Color.red);
        img.fill();
        this.setImage(img);
    }
    public void clear()//sets the image to a white square
    {
        this.setImage(new GreenfootImage(PIXEL_SIZE, PIXEL_SIZE));
    }
    public boolean isAtGoal()
    {
        return this.isTouching(Goal.class);
    }
    public double getHeuristic()
    {
        return heuristic_;
    }
    public double calcH(Node n)//"recursively" finds the cost of this node using the cost of a parent - same idea as computing the fibonacci sequence
    {
        double posCost = n.cost_ + 1;
        return posCost + d_;
    }
}

