package TWDGDX;

import Entity.AgentEntity;
import Entity.EntityManager;
import Graphics.GameMap;
import Graphics.SpriteRenderer;
import Math.CoordinateTranslator;
import Math.Point2D;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import java.awt.Point;

public class TWDGame extends ApplicationAdapter
{

    private GameMap gMap;
    private EntityManager entM;
    private AgentEntity agent;
    private float deltaTime;
    private SpriteRenderer sRen;
    private FPSLogger fLog;

    private CoordinateTranslator corT;

    @Override
    public void create()
    {
        fLog = new FPSLogger();
        deltaTime = 0f;
        gMap = new GameMap();
        entM = new EntityManager();
        agent = new AgentEntity(72.5, -5.71, "Blinky");
        entM.addEnt(agent);
        corT = new CoordinateTranslator(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 100, 100, new Point2D(0, 0));
        sRen = new SpriteRenderer(entM, corT);
        //curFrame = new TextureRegion();
    }

    @Override
    public void render()
    {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        deltaTime = 400*Gdx.graphics.getRawDeltaTime();
        fLog.log();
        gMap.render();
        sRen.render();
        agent.update(deltaTime);

        System.out.println("Mouse :" + Gdx.input.getX() + ", " + Gdx.input.getY());
        System.out.println("stateTime: " + deltaTime);
    }
}
