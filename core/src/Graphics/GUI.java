/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Math.CoordinateTranslator;
import Math.Point2D;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Bakuryu
 */
public class GUI
{

    private Input input;
    private Point mouseSP;
    private Point2D mouseWP;
    private CoordinateTranslator corT;
    private ShapeRenderer sRender;
    private GameMap gMap;
    private Texture whiteRect;
    private SpriteBatch sBatch;
    private TextureRegion rect;

    public GUI(GameMap gMap, CoordinateTranslator corT)
    {
        whiteRect = new Texture("graphics/whiteRect.png");
        sRender = new ShapeRenderer();
        sBatch = new SpriteBatch();
        rect = new TextureRegion(whiteRect);
        this.corT = corT;
        mouseSP = new Point();
        mouseWP = new Point2D();
        this.gMap = gMap;
    }

    public void update(float t)
    {
        input = Gdx.input;
        mouseSP = new Point(input.getX(), input.getY());
        mouseWP = corT.screenToWorld(mouseSP);
        System.out.println("MouseWP: " + mouseWP);
        int tx = (int) (mouseWP.getX() / 2.5);
        double hMY = 100 - mouseWP.getY();
        double hDTF = (double) 100 / 35;
        int ty = (int) (Math.round(hMY / hDTF));
        System.out.println("TileCord: " + tx + ", " + ty);

    }

    public void render()
    {

        Point2D tileInWorld = new Point2D(convertFromTileCord(convertToTileCord(mouseWP).x, convertToTileCord(mouseWP).y));
        Point tileInScreen = corT.worldToScreen(tileInWorld);

//        sRender.setAutoShapeType(true);
//        sRender.begin();
//        sRender.rect((int) tileInScreen.getX(), (int) tileInScreen.getY() - 32, 32, 32);
//        sRender.end();
        for (Rectangle r : gMap.getFreeRect())
        {
            //sRender.setAutoShapeType(true);
            sBatch.setColor(Color.WHITE);
            sBatch.begin();
            drawRect(r.x, r.y, r.width, r.height, 1);
            sBatch.end();
        }

        sBatch.begin();
        sBatch.setColor(Color.GREEN);
        if (isLegal(tileInWorld))
        {
            drawRect((int) tileInScreen.getX(), (int) tileInScreen.getY() - 32, 32, 32, 4);
        }
        else
        {
            if (isEdgeR(tileInWorld))
            {
                drawRect((int) tileInScreen.getX() - 16, (int) tileInScreen.getY() - 32, 16, 32, 4);
            }
        }
        sBatch.end();
    }

    private Point convertToTileCord(Point2D p)
    {
        int tx = (int) (p.getX() / 2.5);
        double hMY = 100 - p.getY();
        int ty = (int) (hMY / ((double) 100 / 35));

        Point tilePoint = new Point(tx, ty);

        return tilePoint;
    }

    private Point2D convertFromTileCord(int x, int y)
    {
        double wX = 0;
        if (x != 39)
        {
            wX = x * 2.5;
        }
        double hDTF = (double) 100 / 35;
        double wY = 100 - (y * hDTF);

        Point2D worldPoint = new Point2D(wX, wY);
        return worldPoint;
    }

    private void drawRect(int x, int y, int width, int height, int thickness)
    {
        sBatch.draw(rect, x, y, width, thickness);
        sBatch.draw(rect, x, y, thickness, height);
        sBatch.draw(rect, x, y + height - thickness, width, thickness);
        sBatch.draw(rect, x + width - thickness, y, thickness, height);
    }

    private void drawLine(int x1, int y1, int x2, int y2, int thickness)
    {
        int dx = x2 - x1;
        int dy = y2 - y1;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        float rad = (float) Math.atan2(dy, dx);
        //sBatch.draw(rect, x1, y1, dist, thickness, 0, 0, rad);
    }

    private boolean isLegal(Point2D p)
    {
        int i = 0;
        int rEnd = 0;
        int j = 0;
        int cEnd = 0;
        boolean isLegal = true;
        Point pTileCord = new Point(convertToTileCord(p));
        //A tile is open to the the left or above the current position

        if (pTileCord.x == 39 && pTileCord.y != 34)
        {
            i = pTileCord.x;
            j = pTileCord.y;

            cEnd = j + 1;
            rEnd = i;

        }

        if (pTileCord.x != 39 && pTileCord.y == 34)
        {
            i = pTileCord.x;
            j = pTileCord.y;

            cEnd = j;
            rEnd = i + 1;

        }

        if (pTileCord.x == 39 && pTileCord.y == 34)
        {
            i = pTileCord.x;
            j = pTileCord.y;

            cEnd = j;
            rEnd = i;

        }

        if (pTileCord.x != 39 && pTileCord.y != 34)
        {
            i = pTileCord.x;
            j = pTileCord.y;

            cEnd = j + 1;

            rEnd = i + 1;
        }
        for (int k = j; k < cEnd; k++)
        {
            for (int l = i; l < rEnd; l++)
            {
                if (!isNeighborBlocked(l, k))
                {
                    isLegal = false;
                }
            }
        }

        return isLegal;
    }

    private boolean isEdgeR(Point2D p)
    {
        Point pTileCord = new Point(convertToTileCord(p));
        return isNeighborBlocked(pTileCord.x - 1, pTileCord.y);
    }

    private boolean isEdgeD(Point2D p)
    {
        Point pTileCord = new Point(convertToTileCord(p));
        return isNeighborBlocked(pTileCord.x, pTileCord.y - 1);
    }

    private boolean isNeighborBlocked(int x, int y)
    {
        boolean isBlocked[][] = gMap.getBlocked();

        return (isBlocked[x][y]);

    }
}
