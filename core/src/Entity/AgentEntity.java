/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Graphics.AnimationManager;
import Math.Point2D;
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
    private LinkedList<Point2D> path;
    private LinkedList<Point2D> backtrack;
    private Point2D currentTargetP;
    private boolean isBacktracking;

    /**
     * Create an AgentEntity at location (x,y).
     *
     * @param x Agent's starting x coordinate
     * @param y Agent's starting y coordinate
     * @param type
     */
    public AgentEntity(double x, double y, String type) 
    {
        currentTargetP = new Point2D();
        position = new Point2D(x, y);
        enemyType = type;
        animM = new AnimationManager();
        anim = animM.setAnimation(type);
        path = new LinkedList<Point2D>();
        backtrack = new LinkedList<Point2D>();
        speed = 50;
        isBacktracking = false;
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

        if (!isBacktracking && !path.isEmpty() && !(isAgentNear(this, path.getFirst())))
        {
            Vector2D dist = path.getFirst().minus(this.position);
            dist.normalize();

            double nX = position.getX() + dist.getX() * speed * t / 1000;
            double nY = position.getY() + dist.getY() * speed * t / 1000;
            Point2D newPos = new Point2D(nX, nY);
            position.set(newPos);
        }

        if (isBacktracking && !backtrack.isEmpty() && !isAgentNear(this, backtrack.getFirst()))
        {
            Vector2D dist = backtrack.getFirst().minus(this.position);
            dist.normalize();

            double nX = position.getX() + dist.getX() * speed * t / 1000;
            double nY = position.getY() + dist.getY() * speed * t / 1000;
            Point2D newPos = new Point2D(nX, nY);
            position.set(newPos);
        }

        if (!isBacktracking && !path.isEmpty() && isAgentNear(this, path.getFirst()))
        {
            backtrack.addFirst(path.getFirst());
            path.remove();
        }

        if (isBacktracking && !backtrack.isEmpty() && isAgentNear(this, backtrack.getFirst()))
        {
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
        path.add(new Point2D(72.5, 5.72));
        path.add(new Point2D(92, 11.43));
        path.add(new Point2D(92, 37.14));
        path.add(new Point2D(12.5, 37.14));
        path.add(new Point2D(12.5, 51.43));
        path.add(new Point2D(92, 51.43));
        path.add(new Point2D(92, 82.86));
        path.add(new Point2D(25, 82.86));
        path.add(new Point2D(22.5, 77));
        path.add(new Point2D(15, 77));
    }

    public boolean isAgentNear(AgentEntity a, Point2D dest)
    {
        boolean closeX = false;
        boolean closeY = false;
        if (Math.abs(a.getPosition().getX() - dest.getX()) < 1)
        {
            closeX = true;
        }

        if (Math.abs(a.getPosition().getY() - dest.getY()) < 1)
        {
            closeY = true;
        }

        return (closeX && closeY);
    }

}
