/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Math.Point2D;
import Math.TileConverter;
import Math.Vector2D;
import com.badlogic.gdx.graphics.Texture;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Bakuryu
 */
public class TowerEntity extends Entity
{

    private String type;
    private Texture towerSpr;
    private int coolDown;
    private double rotateSpeed;
    private double viewDist;
    private Vector2D targetDist;
    private double angleToTarget;
    private EntityManager entM;
    private Point2D centerOfTower;
    private ArrayList<Point> TilesCovered;
    private TileConverter tCon;

    public TowerEntity(String type, Point2D pos, EntityManager entM)
    {
        this.entM = entM;
        this.type = type;
        createTower();
        
        position = new Point2D(pos);
        centerOfTower = new Point2D(position.getX() + 2.5, position.getY() + (100 / 35));
        tCon = new TileConverter();
        TilesCovered = new ArrayList<Point>();
        setTilesCovered();

    }

    @Override
    public void update(float t)
    {
        findClosestTarget();

    }

    public Texture getTSprite()
    {
        return towerSpr;
    }

    private void createTower()
    {
        if (type == "norm")
        {
            towerSpr = new Texture("graphics/tower32x32.png");
            coolDown = 5;
            viewDist = 30;
            rotateSpeed = 10;
        }
    }

    public double getRotation()
    {
        return angleToTarget;
    }

    private void findClosestTarget()
    {
        for (Entity e : entM.getEnts())
        {
            if (e instanceof AgentEntity)
            {
                double newAngle = 0.0;
                targetDist = centerOfTower.minus(e.getPosition());
                if (targetDist.magnitude() < viewDist)
                {

                    newAngle = targetDist.angle();// Math.atan2(targetDist.getY(), targetDist.getX());
                    newAngle *= 180 / Math.PI;
                    if (newAngle < 0)

                    {

                        newAngle = 360 - (-newAngle);

                    }

                    newAngle -= 90;

                    if (angleToTarget > newAngle)
                    {
                        angleToTarget -= rotateSpeed;
                    }
                    else if (angleToTarget < newAngle)
                    {
                        angleToTarget += rotateSpeed;
                    }
                }

            }
        }
    }

    private void setTilesCovered()
    {
        Point startTile = new Point(tCon.convertToTileCord(position));
        System.out.println("TileClick: " + startTile);
        int rEnd = startTile.x+2;
        int cEnd = startTile.y-2;
        for (int j = startTile.y; j > cEnd; j--)
        {
            for (int i = startTile.x; i < rEnd; i++)
            {
                Point curTile = new Point(i, j);
                TilesCovered.add(curTile);
            }
        }

    }

    
    public ArrayList<Point> getTilesCovered()
    {
        return TilesCovered;
    }
}
