import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * @Author Kavan
 */
public class Maze extends World
{
    private Goal g;
    private Pathfinder p;
    public Maze()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(23, 23, 50);
        g = new Goal();
        p = new Pathfinder();
        this.setPaintOrder(Pathfinder.class, Goal.class, Wall.class, Node.class);
        addObject(g, 0, 0);
        addObject(p, getWidth(), getHeight());
    }
    public void generateMaze()//path that exists is not guaranteed
    {
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
    public Goal getGoal()
    {
        return g;
    }
    public void act()
    {
        if(Greenfoot.isKeyDown("g"))
        {
            generateMaze();
        }
    }
}
