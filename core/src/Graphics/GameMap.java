/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 *
 * @author Bakuryu
 */
public class GameMap
{

    private Texture tex;
    private TiledMap map;
    private OrthographicCamera cam;
    private TiledMapRenderer tRenderer;

    public GameMap()
    {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        tex = new Texture(Gdx.files.internal("badlogic.jpg"));
        cam = new OrthographicCamera();
        cam.setToOrtho(false, w, h);
        cam.update();
        map = new TmxMapLoader().load("TDMap.tmx");
        tRenderer = new OrthogonalTiledMapRenderer(map);
    }

    public void render()
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cam.update();
        tRenderer.setView(cam);
        tRenderer.render();
    }
}
