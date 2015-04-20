package TWDGDX;

import Graphics.GameMap;
import com.badlogic.gdx.ApplicationAdapter;

public class TWDGame extends ApplicationAdapter {

    private GameMap gMap;
	
	@Override
	public void create () {
            gMap = new GameMap();
	}

	@Override
	public void render () {
            gMap.render();

	}
}
