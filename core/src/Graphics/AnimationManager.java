/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphics;

import Entity.AgentEntity;
import Entity.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author Bakuryu
 */
public class AnimationManager
{

    private Texture blinky;
    private Texture inky;
    private Texture pinky;
    private Texture clyde;
    private TextureRegion[] animFrames;
    private int FRAME_COL;
    private int FRAME_ROW;


    /* Animation to be return to entity*/
    private Animation anim;

    /* Temporary animation variable for storing the next animation*/
    /**
     * Create an AnimationManager, load player and agent sprite into sprite
     * sheets, and initialize variables.
     *
     */
    public AnimationManager()
    {
        FRAME_COL = 2;
        FRAME_ROW = 1;
        blinky = new Texture("graphics/BlinkyAnim2.png");
        inky = new Texture("graphics/InkyAnim2.png");
        pinky = new Texture("graphics/PinkyAnim2.png");
        clyde = new Texture("graphics/ClydeAnim2.png");

    }

    /* Retrieve animation based on entity type*/
    public Animation setAnimation(String type)
    {

        if (type == "Blinky")
        {
            TextureRegion[][] tmp = TextureRegion.split(blinky, blinky.getWidth() / 2, blinky.getHeight());
            animFrames = new TextureRegion[FRAME_ROW * FRAME_COL];
            int index = 0;
            for (int i = 0; i < FRAME_ROW; i++)
            {
                for (int j = 0; j < FRAME_COL; j++)
                {
                    animFrames[index++] = tmp[i][j];
                }

            }
            anim = new Animation(0.25f, animFrames);
        }

        if (type == "Inky")
        {
            TextureRegion[][] tmp = TextureRegion.split(inky, inky.getWidth() / 2, inky.getHeight());
            animFrames = new TextureRegion[FRAME_ROW * FRAME_COL];
            int index = 0;
            for (int i = 0; i < FRAME_ROW; i++)
            {
                for (int j = 0; j < FRAME_COL; j++)
                {
                    animFrames[index++] = tmp[i][j];
                }

            }
            anim = new Animation(0.025f, animFrames);
        }

        if (type == "Pinky")
        {
            TextureRegion[][] tmp = TextureRegion.split(pinky, pinky.getWidth() / 2, pinky.getHeight());
            animFrames = new TextureRegion[FRAME_ROW * FRAME_COL];
            int index = 0;
            for (int i = 0; i < FRAME_ROW; i++)
            {
                for (int j = 0; j < FRAME_COL; j++)
                {
                    animFrames[index++] = tmp[i][j];
                }

            }
            anim = new Animation(0.025f, animFrames);
        }

        if (type == "Clyde")
        {
            TextureRegion[][] tmp = TextureRegion.split(clyde, clyde.getWidth() / 2, clyde.getHeight());
            animFrames = new TextureRegion[FRAME_ROW * FRAME_COL];
            int index = 0;
            for (int i = 0; i < FRAME_ROW; i++)
            {
                for (int j = 0; j < FRAME_COL; j++)
                {
                    animFrames[index++] = tmp[i][j];
                }

            }
            anim = new Animation(0.025f, animFrames);
        }
        return anim;
    }

}
