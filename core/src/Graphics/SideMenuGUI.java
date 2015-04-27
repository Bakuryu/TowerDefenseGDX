/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Math.CoordinateTranslator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
    private Texture whiteRect;
    private TextureRegion rect;
    private Skin skin;
    private Stage stage;
    private String sType;

    public SideMenuGUI(CoordinateTranslator corT2)
    {
        sType = "norm";
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        whiteRect = new Texture("graphics/whiteRect.png");
        rect = new TextureRegion(whiteRect);
        sBatch = new SpriteBatch();
        this.corT2 = corT2;
        cam = new OrthographicCamera(80, 560);

    }

    public void render()
    {
        cam.position.set(640f, 0, 0);
        sBatch.begin();
        drawRect((int) cam.position.x, (int) cam.position.y, 10, 10, 4);
        sBatch.end();
    }

    private void drawRect(int x, int y, int width, int height, int thickness)
    {
        sBatch.draw(rect, x, y, width, thickness);
        sBatch.draw(rect, x, y, thickness, height);
        sBatch.draw(rect, x, y + height - thickness, width, thickness);
        sBatch.draw(rect, x + width - thickness, y, thickness, height);
    }
    
    public String getSelectedType()
    {
        return sType;
    }
}
