/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Math.CoordinateTranslator;
import Math.PointManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 *
 * @author Bakuryu
 */
public class SideMenuGUI
{

    private CoordinateTranslator corT2;
    private OrthographicCamera cam;
    private SpriteBatch sBatch;

    private Skin skin;
    private Stage stage;
    private String sType;
    private BitmapFont font;
    private int towerPoints;
    private PointManager pointM;
    private ShapeDrawer sDraw;

    public SideMenuGUI(CoordinateTranslator corT2, PointManager pM)
    {
        sDraw = new ShapeDrawer();
        pointM = pM;
        sType = "reg";
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();

        sBatch = new SpriteBatch();
        this.corT2 = corT2;
        cam = new OrthographicCamera(112, 560);
        font = new BitmapFont();

    }

    public void render()
    {
        cam.position.set(640f, 0, 0);

        sDraw.drawRect((int) cam.position.x, (int) cam.position.y, 112, 560, 2, Color.WHITE);
        sDraw.drawLine(corT2.worldToScreen(2, 94).x, corT2.worldToScreen(2, 94).y, corT2.worldToScreen(15, 94).x, corT2.worldToScreen(15, 94).y, 2, Color.WHITE);
        sBatch.begin();
        font.draw(sBatch, "Tower Points", corT2.worldToScreen(2, 98).x, corT2.worldToScreen(2, 98).y);

        font.draw(sBatch, "" + (pointM.getPoints()), corT2.worldToScreen(8, 92).x, corT2.worldToScreen(8, 92).y);
        sBatch.end();

    }

    public String getSelectedType()
    {
        return sType;
    }
}
