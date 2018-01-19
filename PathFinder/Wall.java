import greenfoot.*;
import java.awt.Color;

/**
* A wall that the pathfinder cannot traverse in it's path
* @PIXEL_SIZE: the size of the image representing this wall
*/
public class Wall extends Actor
{
    private final static int PIXEL_SIZE = 50;
    
    /**
    * Creates an new Wall instance
    */
    public Wall()
    {
        GreenfootImage wall = new GreenfootImage(PIXEL_SIZE, PIXEL_SIZE);
        wall.setColor(Color.orange);
        wall.fill();
        this.setImage(wall);
    }
    
    public void act() 
    {
        // Add your action code here.
    }    
}
