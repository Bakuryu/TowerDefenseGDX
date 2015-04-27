/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Math.Point2D;
import Math.Vector2D;
import com.badlogic.gdx.graphics.Texture;

/**
 *
 * @author Bakuryu
 */
public class TowerEntity extends Entity
{

    private String type;
    private Texture towerSpr;
    private int fireSpeed;
    private Vector2D target;
    private double angleToTarget;
    private EntityManager entM;

    public TowerEntity(String type, Point2D pos, EntityManager entM)
    {
        this.entM = entM;
        this.type = type;
        setSprite();

        position = new Point2D(pos);

    }

    @Override
    public void update(float t)
    {
        Point2D centerOfTower = new Point2D(position.getX()+2.5,position.getY()+(100.35));
        for(Entity e : entM.getEnts())
        {
            if(e instanceof AgentEntity)
            {
                
                target = centerOfTower.minus(e.getPosition());
            }
        }
    }

    public Texture getTSprite()
    {
        return towerSpr;
    }

    private void setSprite()
    {
        if (type == "norm")
        {
            towerSpr = new Texture("graphics/tower32x32.png");
        }
    }
    
    public double getRotation()
    {
        return angleToTarget;
    }

}
