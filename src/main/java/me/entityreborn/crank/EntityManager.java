/*
 * The MIT License
 *
 * Copyright 2013 Jason Unger <entityreborn@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package me.entityreborn.crank;

import java.util.HashSet;
import java.util.Set;
import me.entityreborn.crank.entities.Entity;
import me.entityreborn.crank.gui.CameraView;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class EntityManager {

    private static EntityManager instance;

    public static EntityManager get() {
        if (instance == null) {
            instance = new EntityManager();
        }

        return instance;
    }
    private final Set<Entity> entities = new HashSet<>();

    private EntityManager() {
    }

    public Set<Entity> getEntities() {
        return new HashSet<>(entities);
    }

    public boolean addEntity(Entity ent) {
        return entities.add(ent);
    }

    public boolean removeEntity(Entity ent) {
        return entities.remove(ent);
    }

    public Set<Entity> getEntitiesInView(CameraView view) {
        Set<Entity> inView = new HashSet<>();

        for (Entity ent : getEntities()) {
            if (ent.getX() >= view.getX() - ent.getWidth()
                    && ent.getX() - ent.getWidth() / 2 <= view.getX() + view.getWidth()) {
                inView.add(ent);
            }
        }

        return inView;
    }

    public Set<Entity> getEntityCollisions(Entity entity) {
        Set<Entity> ents = new HashSet<>();

        for (Entity ent : getEntities()) {
            if (ent.getBounds().intersects(entity.getBounds())
                    && ent != entity) {
                ents.add(ent);
            }
        }

        return ents;
    }
}
