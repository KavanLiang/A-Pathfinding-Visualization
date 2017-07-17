import greenfoot.*;
import java.awt.Color;
public class Wall extends Actor
{
    /**
     * Act - do whatever the Wall wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    private final static int PIXEL_SIZE = 50;
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
