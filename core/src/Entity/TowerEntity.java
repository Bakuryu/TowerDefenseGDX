/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

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

    
    public TowerEntity(String type)
    {
        towerSpr = new Texture("res/graphics/tower32x32.png");
        this.type = type;
  
    }
    
    @Override
    public void update(float t)
    {

    }
    

    
}
