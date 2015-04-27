/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Entity.Entity;
import Entity.EntityManager;
import Entity.TowerEntity;
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
public class TowerGUI
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
    private Point2D tileInWorld;
    private Point tileInScreen;
    private EntityManager entM;
    private SideMenuGUI sGUI;

    private boolean isPlacing;

    public TowerGUI(GameMap gMap, CoordinateTranslator corT, EntityManager entM, SideMenuGUI sGUI)
    {
        this.sGUI = sGUI;
        this.entM = entM;
        isPlacing = true;
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

        System.out.println("MouseWP: " + mouseWP.getX() + ", " + mouseWP.getY());
        int tx = convertToTileCord(mouseWP).x;
        int ty = convertToTileCord(mouseWP).y;
        //System.out.println("TileCord: " + tx + ", " + ty);
        if (isPlacing)
        {
            if (input.isButtonPressed(Input.Buttons.LEFT))
            {
                if (isLegal(tileInWorld))
                {

                        String type = sGUI.getSelectedType();
                        TowerEntity tEntity = new TowerEntity(type, tileInWorld, entM);
                        entM.addEnt(tEntity);
                    
                }
            }
        }

    }

    public void render()
    {

        checkMouseBounds();

        tileInWorld = new Point2D(convertFromTileCord(convertToTileCord(mouseWP).x, convertToTileCord(mouseWP).y));
        tileInScreen = corT.worldToScreen(tileInWorld);

        if (isPlacing)
        {
            for (Rectangle r : gMap.getFreeRect())
            {

                sBatch.setColor(Color.WHITE);
                sBatch.begin();
                drawRect(r.x, r.y, r.width, r.height, 1);
                sBatch.end();
            }

            sBatch.begin();

            if (isLegal(tileInWorld))
            {

                sBatch.setColor(Color.GREEN);
                if (isEdgeR(tileInWorld))
                {
                    drawRect((int) tileInScreen.getX() - 16, (int) tileInScreen.getY() - 16, 32, 32, 4);
                }
                else
                {
                    drawRect((int) tileInScreen.getX(), (int) tileInScreen.getY() - 16, 32, 32, 4);
                }

            }

            if (!isLegal(tileInWorld))
            {
                sBatch.setColor(Color.RED);
                if (isEdgeR(tileInWorld))
                {
                    drawRect((int) tileInScreen.getX() - 16, (int) tileInScreen.getY() - 16, 32, 32, 4);
                }
                else
                {
                    drawRect((int) tileInScreen.getX(), (int) tileInScreen.getY() - 16, 32, 32, 4);
                }
            }
            sBatch.end();
        }

    }

    private Point convertToTileCord(Point2D p)
    {
        int tx = (int) (p.getX() / 2.5);
        double hMY = p.getY();
        double hDTF = 100.0 / 35.0;
        double total = hMY / hDTF;

        int ty = (int) total;

        Point tilePoint = new Point(tx, ty);

        return tilePoint;
    }

    private Point2D convertFromTileCord(int x, int y)
    {
        double wX = 0;

        wX = x * 2.5;
        double hDTF = (double) 100 / 35;
        double wY = (y * hDTF);

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

        int tx = pTileCord.x;
        int ty = pTileCord.y;

        //A tile is open to the the left or above the current position
        if (pTileCord.x != 39 && pTileCord.y != 34)
        {
            i = pTileCord.x;
            j = pTileCord.y;

            cEnd = j - 2;

            rEnd = i + 2;
        }

        if (pTileCord.x == 39 && pTileCord.y != 34)
        {
            i = pTileCord.x - 1;
            j = pTileCord.y;

            cEnd = j - 2;

            rEnd = i + 2;
        }

        if (pTileCord.x != 39 && pTileCord.y == 34)
        {
            i = pTileCord.x;
            j = pTileCord.y;

            cEnd = j - 2;

            rEnd = i + 2;
        }

        if (pTileCord.x == 39 && pTileCord.y == 34)
        {
            i = pTileCord.x - 1;
            j = pTileCord.y - 1;

            cEnd = j - 2;
            rEnd = i + 2;

        }
        System.out.println("DrawTile: " + i + ", " + j);
        for (int k = j; k > cEnd; k--)
        {
            System.out.println("");
            for (int l = i; l < rEnd; l++)
            {
                System.out.print("(" + l + ", " + k + ") ");
                System.out.print(isNeighborBlocked(l, k) + " ");
                System.out.print(isTower(l,k));

                if (isNeighborBlocked(l, k)|| isTower(l,k))
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
        return pTileCord.x == 39;
    }

//Don't need this apparently?   
//    private boolean isEdgeD(Point2D p)
//    {
//        Point pTileCord = new Point(convertToTileCord(p));
//        return isNeighborBlocked(pTileCord.x, pTileCord.y - 1);
//    }
    public boolean isNeighborBlocked(int x, int y)
    {
        boolean isBlocked[][] = gMap.getBlocked();
        return (isBlocked[x][y]);

    }

    public void checkMouseBounds()
    {
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
    }

    public void setIsPlacing(boolean b)
    {
        isPlacing = b;
    }

    public boolean IsPlacing()
    {
        return isPlacing;
    }
    
    private boolean isTower(int x, int y)
    {
        Point mTileCord = new Point(x,y);
        boolean TowerBlock = false;
        for(Entity e : entM.getEnts())
        {
            if(e instanceof TowerEntity)
            {
//                System.out.println("T Pos: " + e.getPosition());
//                System.out.println("TIW: " + tileInWorld);
                Point tTilePos = convertToTileCord(e.getPosition());
                if(tTilePos.equals(mTileCord))
                {
                    TowerBlock = true;
                }
            }
        }
        return TowerBlock;
    }
}
