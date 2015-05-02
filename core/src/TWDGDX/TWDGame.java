package TWDGDX;

import Entity.AgentEntity;
import Entity.EntityManager;
import Entity.PlayerEntity;
import Graphics.TowerGUI;
import Graphics.GameMap;
import Graphics.SideMenuGUI;
import Graphics.SpriteRenderer;
import Math.CoordinateTranslator;
import Math.Point2D;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class TWDGame extends ApplicationAdapter
{

    private GameMap gMap;
    private EntityManager entM;
    private AgentEntity agent;
    private PlayerEntity player;
    private float deltaTime;
    private SpriteRenderer sRen;
    private FPSLogger fLog;
    private TowerGUI tGUI;
    private Stage stage;
    private SideMenuGUI sGUI;

    private CoordinateTranslator corT1;
    private CoordinateTranslator corT2;

    @Override
    public void create()
    {
        stage = new Stage(new ScreenViewport());

        fLog = new FPSLogger();
        deltaTime = 0f;
        gMap = new GameMap(stage);
        entM = new EntityManager();

        player = new PlayerEntity(10, 73);
        agent = new AgentEntity(72.5, -5.71, "Blinky",player);
        entM.addEnt(agent);
        entM.addEnt(player);
        corT1 = new CoordinateTranslator(Gdx.graphics.getWidth() - 80, Gdx.graphics.getHeight(), 100, 100, new Point2D(0, 0));
        corT2 = new CoordinateTranslator(Gdx.graphics.getWidth() - 640, Gdx.graphics.getHeight(), 12.5, 100, new Point2D(0, 0));
        sRen = new SpriteRenderer(entM, corT1);

        sGUI = new SideMenuGUI(corT2);
        tGUI = new TowerGUI(gMap, corT1, entM, sGUI);
        //curFrame = new TextureRegion();
    }

    @Override
    public void render()
    {
        stage.getViewport().apply();
        stage.draw();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        deltaTime = 400 * Gdx.graphics.getRawDeltaTime();

        fLog.log();
        gMap.render();

        tGUI.render();

        sGUI.render();
        sRen.render();
        update();

    }

    public void update()
    {
        tGUI.update(deltaTime);
        entM.updateEnts(deltaTime);
    }
}
