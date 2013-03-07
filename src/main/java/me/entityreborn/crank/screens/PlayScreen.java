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
package me.entityreborn.crank.screens;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;
import me.entityreborn.crank.Crank;
import me.entityreborn.crank.entities.Entity;
import me.entityreborn.crank.EntityManager;
import me.entityreborn.crank.KeyListenerAdapter;
import me.entityreborn.crank.entities.Block;
import me.entityreborn.crank.entities.MovingBlock;
import me.entityreborn.crank.entities.Player;
import me.entityreborn.crank.gui.CameraView;
import me.entityreborn.crank.gui.HUD;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class PlayScreen implements Screen {
    private CameraView camera;
    Set<Block> blocks = new HashSet<>();
    int animatorcount;
    
    private static PlayScreen instance;
    
    public static PlayScreen get() {
        if (instance == null) {
            instance = new PlayScreen();
        }
        
        return instance;
    }

    private PlayScreen() {
        camera = new CameraView();
        EntityManager.get().addEntity(Player.get());
        Crank.get().addKeyListener(new KeyListenerAdapter(Player.get()));
        Player.get().setY(-100);
        Player.get().setX(100);
        
        Block b1 = new Block(100, 2);
        blocks.add(b1);
        b1.setY(-40);
        b1.setX(100);
        EntityManager.get().addEntity(b1);
        
        Block b2 = new Block(100, 2);
        blocks.add(b2);
        b2.setY(-40);
        b2.setX(300);
        EntityManager.get().addEntity(b2);
        
        Block b3 = new Block(100, 2);
        blocks.add(b3);
        b3.setY(-40);
        b3.setX(500);
        EntityManager.get().addEntity(b3);
        
        Block b4 = new Block(100, 2);
        blocks.add(b4);
        b4.setY(-40);
        b4.setX(700);
        EntityManager.get().addEntity(b4);
        
        MovingBlock b5 = new MovingBlock(100, 2);
        blocks.add(b5);
        b5.setY(-60);
        b5.setX(400);
        EntityManager.get().addEntity(b5);
    }
    
    @Override
    public void render(Graphics2D gfx) {
        camera.render(gfx);
        /*for (Entity ent : EntityManager.get().getEntitiesInView(getCamera())) {
            ent.render(gfx, getCamera().getX());
            Rectangle bounds = ent.getBounds();
            gfx.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }*/
        
        HUD.get().render(gfx);
    }

    @Override
    public void tick() {
        getCamera().tick();
        
        for (Entity ent : EntityManager.get().getEntities()) {
            ent.tick();
        }
        
        animatorcount += 1;
        
        if (animatorcount >= 10) {
            for (Entity ent : EntityManager.get().getEntities()) {
                ent.animate();
            }
            
            animatorcount = 0;
        }
    }

    public CameraView getCamera() {
        return camera;
    }
}
