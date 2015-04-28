/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.ArrayList;


/**
 *
 * @author Bakuryu
 */
public class EntityManager
{
    /* Array list of entities used for storing and managing entities in the game*/
    ArrayList<Entity> e;

    /**
     * Create an EntityManager and initialize entity ArrayList
     */
    public EntityManager()
    {
        e = new ArrayList();
    }

    /**
     * Adds an entity, ent, to the array list
     * @param ent Entity to be added to array list
     * @throws SlickException 
     */
    public void addEnt(Entity ent)
    {
        e.add(ent);
    }

    /**
     * Removes entity, ent, from array list
     * @param ent Entity to be removed from array list
     */
    public void destroyEnt(Entity ent)
    {
        e.remove(ent);
    }

    /* Update method used to update all entities in the array list*/
    public void updateEnts(float t) 
    {
        for (Entity ents : e)
        {
            ents.update(t);
        }

    }

//    /* Render method used to render all entities in the array list*/
//    public void renderEnts(SpriteRenderer ren) throws SlickException
//    {
//        for (Entity ents : e)
//        {
//            //ren.render(ents);
//        }
//    }
    
    public ArrayList<Entity> getEnts()
    {
        return e;
    }
}
