/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Entity.AgentEntity;
import Entity.Entity;
import Entity.EntityManager;
import Entity.TowerEntity;
import Math.CoordinateTranslator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.awt.Point;

/**
 *
 * @author Bakuryu
 */
public class SpriteRenderer
{

    private EntityManager entM;
    private Animation anim;
    private TextureRegion curFrame;
    private SpriteBatch sBatch;
    private float stateTime;
    private CoordinateTranslator corT;
    

    public SpriteRenderer(EntityManager entM, CoordinateTranslator corT)
    {
        sBatch = new SpriteBatch();
        this.entM = entM;
        this.corT = corT;
        stateTime = 0f;
        
    }

    public void render()
    {
        for (Entity e : entM.getEnts())
        {
            if (e instanceof AgentEntity)
            {
                renderAgentEntity((AgentEntity) e);
            }
            
            if (e instanceof TowerEntity)
            {
                renderTowerEntity((TowerEntity)e);
            }
        }
    }

    private void renderAgentEntity(AgentEntity a)
    {
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        anim = a.getAnimation();
        curFrame = anim.getKeyFrame(stateTime, true);
        sBatch.begin();
        Point aScrPos = new Point(corT.worldToScreen(a.getPosition()));
        sBatch.draw(curFrame, (float) aScrPos.getX(), (float) aScrPos.getY());
        sBatch.end();
    }

    private void renderTowerEntity(TowerEntity t)
    {
        sBatch.begin();
        Point tScrPos = new Point(corT.worldToScreen(t.getPosition()));
        sBatch.draw(t.getTSprite(), tScrPos.x, tScrPos.y);
    }
}
