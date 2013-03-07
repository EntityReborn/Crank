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
package me.entityreborn.crank.gui;

import me.entityreborn.crank.entities.Entity;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import me.entityreborn.crank.Crank;
import me.entityreborn.crank.EntityManager;
import me.entityreborn.crank.GameManager;
import me.entityreborn.crank.KeyListener;
import me.entityreborn.crank.KeyListenerAdapter;
import me.entityreborn.crank.MouseListener;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class CameraView implements KeyListener, MouseListener  {
    int x;
    int xaccel;
    GameManager manager = GameManager.get();
    
    public CameraView() {
        Crank.get().addKeyListener(new KeyListenerAdapter(this));
    }
    
    public int getWidth() {
        return Canvas.get().getWidth();
    }
    
    public int getX() {
        return x;
    }
    
    public void render(Graphics2D gfx) {
        for (Entity ent : EntityManager.get().getEntitiesInView(this)) {
            ent.render(gfx, x);
        }
    }
    
    public void tick() {
        x += xaccel;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_Q) {
            xaccel = 1;
        }

        if (key == KeyEvent.VK_E) {
             xaccel = -1;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_Q || key == KeyEvent.VK_E) {
             xaccel = 0;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
