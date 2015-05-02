/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Components.Collider;
import Math.Point2D;
import Math.Vector2D;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author Bakuryu
 */
public class BulletEntity extends Entity
{

    private String type;
    private double speed;
    private Texture bullSpr;
    private Point2D target;
    private Vector2D targetVector;
    private Collider hitBox;
    private SpriteBatch sBatch;
    private Texture whiteRect;
    private TextureRegion rect;
    private AgentEntity targetEntity;
    private TowerEntity bulletOwner;

    public BulletEntity(TowerEntity t, AgentEntity a)
    {
        bulletOwner = t;
        this.type = t.getTType();
        position = new Point2D(t.getTCenterPoint());
        sBatch = new SpriteBatch();

        whiteRect = new Texture("graphics/whiteRect.png");
        rect = new TextureRegion(whiteRect);
        target = a.getCenterPos();
        targetEntity = a;
        createBullet();
    }

    @Override
    public void update(float t)
    {
        System.out.println("Bullet Position: " + position);
        if (!hitBox.checkEntityCollision(hitBox, targetEntity.getCollider()))
        {
            targetVector = target.minus(position);
            targetVector.normalize();
            targetVector.timesEquals(speed * t / 1000);
            position.plusEquals(targetVector);
        }
        else
        {
            bulletOwner.targetHit(this);
            targetEntity.takeDmg(1);
        }
        
        sBatch.begin();
        sBatch.setColor(Color.WHITE);
        drawRect(hitBox.getHitBox().x,hitBox.getHitBox().y,hitBox.getHitBox().width,hitBox.getHitBox().height,2);
        sBatch.end();
        

    }

    private void createBullet()
    {
        if (type == "norm")
        {
            bullSpr = new Texture("graphics/normalBullet.png");

            hitBox = new Collider(position, 8, 8);
            speed = 150;

        }
    }

    public Texture getBSprite()
    {
        return bullSpr;
    }

    private void drawRect(int x, int y, int width, int height, int thickness)
    {
        sBatch.draw(rect, x, y, width, thickness);
        sBatch.draw(rect, x, y, thickness, height);
        sBatch.draw(rect, x, y + height - thickness, width, thickness);
        sBatch.draw(rect, x + width - thickness, y, thickness, height);
    }
    
    public Collider getCollider()
    {
     return hitBox;   
    }

}
