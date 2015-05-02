/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Math.CoordinateTranslator;
import Math.Point2D;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author Bakuryu
 */
public class TowerButton
{
   
    private Sprite towerSpr;
    private String towerType;
    private Point2D position;
    private ShapeDrawer sDraw;
    private SpriteBatch sBatch;
    private CoordinateTranslator corT2;
    
    public TowerButton(double x, double y, String type, CoordinateTranslator corT2)
    {
        this.corT2 = corT2;
        sDraw = new ShapeDrawer();
        position = new Point2D(x,y);
        towerType = type;
        createButton();
    }
    
    private void createButton()
    {
        if(towerType == "reg")
        {
            towerSpr = new Sprite(new Texture("graphics/RegTower.png"));
        }
      
        sBatch.draw(towerSpr,corT2.worldToScreen(position).x , corT2.worldToScreen(position).y, towerSpr.getWidth()/2, towerSpr.getHeight()/2, towerSpr.getWidth(), towerSpr.getHeight(), 3, 3, 0);
    }
}
