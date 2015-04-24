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
    private boolean thing;

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
        thing = true;
    }

    public void update(float t)
    {
        input = Gdx.input;
        mouseSP = new Point(input.getX(), input.getY());
        mouseWP = corT.screenToWorld(mouseSP);

        int tx = convertToTileCord(mouseWP).x;
        int ty = convertToTileCord(mouseWP).y;
        System.out.println("TileCord: " + tx + ", " + ty);

    }

    public void render()
    {
        boolean test = isNeighborBlocked(0, 5);
        if (mouseWP.getX() > 97.5)
        {
            mouseWP.setX(97.5);
        }
        if (mouseWP.getY() > 97.5)
        {
            mouseWP.setY(97.5);
        }

        if (mouseWP.getY() < (double) 100 / 35)
        {
            mouseWP.setY((double) 100 / 35);
        }
        Point2D tileInWorld = new Point2D(convertFromTileCord(convertToTileCord(mouseWP).x, convertToTileCord(mouseWP).y));
        Point tileInScreen = corT.worldToScreen(tileInWorld);
        Point test2 = new Point(1, 32);
        Point test3 = new Point(corT.worldToScreen(convertFromTileCord(test2.x, test2.y).getX(), convertFromTileCord(test2.x, test2.y).getY()));

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
//
//        for (Rectangle r : gMap.getBlocRect())
//        {
//            sBatch.setColor(Color.RED);
//            sBatch.begin();
//            drawRect(r.x, r.y, r.width, r.height, 1);
//            sBatch.end();
//        }
        sBatch.begin();

        if (isLegal(tileInWorld))
        {
//            if (isEdgeR(tileInWorld))
//            {
//                drawRect((int) tileInScreen.getX(), (int) tileInScreen.getY() - 32, 16, 32, 4);
//            }
//            else
//            {
            sBatch.setColor(Color.GREEN);
            drawRect((int) tileInScreen.getX(), (int) tileInScreen.getY() - 32, 32, 32, 4);
            //}
        }

        if (!isLegal(tileInWorld))
        {
            sBatch.setColor(Color.RED);
            drawRect((int) tileInScreen.getX(), (int) tileInScreen.getY() - 32, 32, 32, 4);
        }
        sBatch.end();

        sBatch.setColor(Color.ORANGE);
        sBatch.begin();
        drawRect(test3.x, test3.y, 32, 32, 10);
        sBatch.end();

    }

    private Point convertToTileCord(Point2D p)
    {
        int tx = (int) (p.getX() / 2.5);
        double hMY = 100.0 - p.getY();
        double hDTF = 100.0 / 35.0;
        double total = Math.round(hMY / hDTF);

        int ty = (int) total;

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
        boolean hasBlockedTile = false;
        Point pTileCord = new Point(convertToTileCord(p));
        //pTileCord.setLocation(pTileCord.x, 34-pTileCord.y);
        int tx = pTileCord.x;
        int ty = pTileCord.y;
        int txty = tx + ty;
        //A tile is open to the the left or above the current position

        if (pTileCord.x != 39 && pTileCord.y != 34)
        {
            i = pTileCord.x;
            j = pTileCord.y;

            cEnd = j + 2;

            rEnd = i + 2;
        }

        if (pTileCord.x == 39 && pTileCord.y != 34)
        {
            i = pTileCord.x - 1;
            j = pTileCord.y;

            cEnd = j + 2;

            rEnd = i + 2;
        }

        if (pTileCord.x != 39 && pTileCord.y == 34)
        {
            i = pTileCord.x;
            j = pTileCord.y - 1;

            cEnd = j + 2;

            rEnd = i + 2;
        }

        if (pTileCord.x == 39 && pTileCord.y == 34)
        {
            i = pTileCord.x - 1;
            j = pTileCord.y - 1;

            cEnd = j + 2;
            rEnd = i + 2;

        }

        for (int k = j; k < cEnd; k++)
        {
            System.out.println("");
            for (int l = i; l < rEnd; l++)
            {
                System.out.print("(" + l + ", " + k + ") ");
                System.out.print(isNeighborBlocked(l, k) + " ");
//                if (isNeighborBlocked(l, k))
//                {
//                    System.out.print("1");
//                }
//                else
//                {
//                    System.out.print("0");
//                }
                if (isNeighborBlocked(l, k))
                {
                    hasBlockedTile = true;
                }
            }
        }
        System.out.println("");
        if (hasBlockedTile == false)
        {
            isLegal = true;
        }

        if (hasBlockedTile == true)
        {
            isLegal = false;
        }
        System.out.println("IsLegal?: " + isLegal);
        return isLegal;
    }

    private boolean isEdgeR(Point2D p)
    {
        Point pTileCord = new Point(convertToTileCord(p));
        if (pTileCord.x + 1 < 41)
        {
            return isNeighborBlocked(pTileCord.x + 1, pTileCord.y);
        }
        else
        {
            return false;
        }
    }

    private boolean isEdgeD(Point2D p)
    {
        Point pTileCord = new Point(convertToTileCord(p));
        return isNeighborBlocked(pTileCord.x, pTileCord.y - 1);
    }

    public boolean isNeighborBlocked(int x, int y)
    {
        boolean isBlocked[][] = gMap.getBlocked();
        if (thing)
        {
            for (int j = 0; j < 35; j++)
            {
                System.out.println("");
                for (int i = 0; i < 40; i++)
                {
                    if (isBlocked[i][j])
                    {
                        System.out.print("1 ");
                    }
                    else
                    {
                        System.out.print("0 ");
                    }
                }
            }
            thing = false;
            System.out.println("");
        }

        //System.out.println("Blocked: " + isBlocked[x][y]);
        return (isBlocked[x][y]);

    }
}
