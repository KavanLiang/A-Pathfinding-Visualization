import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
/**
 * @Author Kavan
 */
public class Goal extends Actor
{
    private static final int PIXEL_SIZE = 30;
    private static final int OFFSET = 1;
    public Goal()
    {
        GreenfootImage wall = new GreenfootImage(PIXEL_SIZE, PIXEL_SIZE);
        wall.setColor(Color.green);
        wall.fill();
        this.setImage(wall);
    }
    public void moveGoal()
    {
        if(Greenfoot.isKeyDown("w"))
            setLocation(getX(), getY() - OFFSET);
        if(Greenfoot.isKeyDown("a"))
            setLocation(getX() - OFFSET, getY());
        if(Greenfoot.isKeyDown("s"))
            setLocation(getX(), getY() + OFFSET);
        if(Greenfoot.isKeyDown("d"))
            setLocation(getX() + OFFSET, getY());
    }
    public void act() 
    {
        moveGoal();// Add your action code here.
    }    
}
