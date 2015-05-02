/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Components.Collider;
import Graphics.AnimationManager;
import Math.Point2D;
import Math.PointManager;
import Math.Vector2D;
import com.badlogic.gdx.graphics.g2d.Animation;
import java.util.LinkedList;

/**
 *
 * @author Bakuryu
 */
public class AgentEntity extends Entity
{

    private String enemyType;
    private AnimationManager animM;
    private Animation anim;
    private int speed;
    private int hp;
    private int dmg;
    private LinkedList<Point2D> path;
    private LinkedList<Point2D> backtrack;
    private Point2D currentTargetP;
    private boolean isBacktracking;
    private Point2D centerPos;
    private Collider hitBox;
    private boolean isAlive;
    private PlayerEntity p;
    private PointManager pointM;

    /**
     * Create an AgentEntity at location (x,y).
     *
     * @param x Agent's starting x coordinate
     * @param y Agent's starting y coordinate
     * @param type
     * @param p
     */
    public AgentEntity(double x, double y, String type, PlayerEntity p, PointManager pM)
    {
        pointM = pM;
        this.p = p;
        isAlive = true;
        currentTargetP = new Point2D();
        position = new Point2D(x, y);
        centerPos = new Point2D(x + 2, y + 4);
        enemyType = type;
        animM = new AnimationManager();
        hitBox = new Collider(position, 30, 29);
        path = new LinkedList<Point2D>();
        backtrack = new LinkedList<Point2D>();
        isBacktracking = false;
        createAgent(type);
        generatePath();

    }

    /**
     * Update Agent's current animation based on delta time, execute the current
     * state, determine animation based on targets location from Agent, and
     * check for collision.
     *
     * @param t
     */
    @Override
    public void update(float t)
    {
        //System.out.println("Position: " + position);
        centerPos.set(new Point2D(position.getX() + 1, position.getY() + 4));
        
        if(!isBacktracking)
        {
            anim = animM.setAgentAnimation(enemyType);
        }
        if (!isBacktracking && !path.isEmpty() && !(isAgentNear(this, path.getFirst())))
        {
            Vector2D dist = path.getFirst().minus(this.position);
            dist.normalize();

            double nX = position.getX() + dist.getX() * speed * t / 1000;
            double nY = position.getY() + dist.getY() * speed * t / 1000;

            Point2D newPos = new Point2D(nX, nY);

            position.set(newPos);
        }
        
//        if(isCollidingPlayer())
//        {
//            p.takeDmg(dmg);
//            takeDmg(hp);
//            return;
//        }

        if (isBacktracking && !backtrack.isEmpty() && !isAgentNear(this, backtrack.getFirst()))
        {
            Vector2D dist = backtrack.getFirst().minus(this.position);
            dist.normalize();

            double nX = position.getX() + dist.getX() * speed * t / 1000;
            double nY = position.getY() + dist.getY() * speed * t / 1000;
            Point2D newPos = new Point2D(nX, nY);
            position.set(newPos);
        }
        
        if(isBacktracking)
        {
            anim = animM.setAgentAnimation("Scared");
        }

        if (!isBacktracking && !path.isEmpty() && isAgentNear(this, path.getFirst()))
        {
           // System.out.println("Position:" + position);
            backtrack.addFirst(path.getFirst());
            path.remove();
        }

        if (isBacktracking && !backtrack.isEmpty() && isAgentNear(this, backtrack.getFirst()))
        {
           // System.out.println("Position:" + position);
            path.addFirst(backtrack.getFirst());
            backtrack.remove();
        }

        if (isBacktracking && backtrack.isEmpty())
        {
            isBacktracking = false;
            //Collections.reverse(path);
        }

        if (!isBacktracking && path.isEmpty())
        {
            isBacktracking = true;
            //Collections.reverse(backtrack);
        }

    }

    public Animation getAnimation()
    {
        return anim;
    }

    private void generatePath()
    {
        path.add(new Point2D(70, 3));
        path.add(new Point2D(93, 3));
        path.add(new Point2D(93, 31.43));
        path.add(new Point2D(12.5, 31.43));
        path.add(new Point2D(12.5, 46));
        path.add(new Point2D(93, 46));
        path.add(new Point2D(93, 77.30));
        path.add(new Point2D(25, 77.30));
        path.add(new Point2D(22.5, 70));
        path.add(new Point2D(10, 70));
    }

    public void takeDmg(int dmg)
    {
        hp -= dmg;
        if(hp == 0)
        {
            if(enemyType == "Blinky")
            {
                pointM.addPoints(1);
            }
            isAlive = false;
        }
    }

    public boolean isAgentNear(AgentEntity a, Point2D dest)
    {
        boolean closeX = false;
        boolean closeY = false;
        if (Math.abs(a.getPosition().getX() - dest.getX()) < 0.5)
        {
            closeX = true;
        }

        if (Math.abs(a.getPosition().getY() - dest.getY()) < 0.5)
        {
            closeY = true;
        }

        return (closeX && closeY);
    }

    public void createAgent(String type)
    {
        if (type == "Blinky")
        {
            anim = animM.setAgentAnimation(type);
            speed = 50;
            hp = 5;
            dmg = 1;
        }
    }
    
    public boolean isAlive()
    {
        return isAlive;
    }
    
    public boolean isCollidingPlayer()
    {
         boolean pCollision = false;
        if (hitBox.checkEntityCollision(this.getCollider(), p.getCollider()))
        {
            pCollision = true;
        }
        return pCollision;
    }
    
    

    public Point2D getCenterPos()
    {
        return centerPos;
    }

    public Collider getCollider()
    {
        return hitBox;
    }

}
