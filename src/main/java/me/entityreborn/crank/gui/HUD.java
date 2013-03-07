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

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import me.entityreborn.crank.Crank;
import me.entityreborn.crank.MouseListener;
import me.entityreborn.crank.MouseListenerAdapter;
import me.entityreborn.crank.entities.Player;
import me.entityreborn.crank.screens.PlayScreen;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class HUD implements MouseListener {
    static HUD instance;
    final ArrayList<String> lines;
    MouseEvent lastEvent;
    
    public static HUD get() {
        if (instance == null) {
            instance = new HUD();
        }
        
        return instance;
    }

    public HUD() {
        this.lines = new ArrayList<>();
        Crank.get().addMouseListener(new MouseListenerAdapter(this));
    }
    
    public void addLine(String line) {
        lines.add(line);

        if (lines.size() > 10) {
            lines.remove(0);
        }
    }
    
    private int getWidth(String txt, Graphics2D gfx) {
        FontMetrics mtx = gfx.getFontMetrics();
        return mtx.stringWidth(txt);
    }
    
    public void render(Graphics2D gfx) {
        gfx.drawString("Player: " + Player.get().getX() + ":" + Player.get().getY(), 5, 20);
        int x = PlayScreen.get().getCamera().getX();
        gfx.drawString("View: " + x, 5, 35);
        
        if (lastEvent != null) {
            gfx.drawString("Mouse: " + lastEvent.getX() + "," + lastEvent.getY(), 5, 50);
        }
        
        String txt = "Press Left and Right to move your player.";
        gfx.drawString(txt, 400 - getWidth(txt, gfx) / 2, 20);
        txt = "Press Space to jump.";
        gfx.drawString(txt, 400 - getWidth(txt, gfx) / 2, 35);
        txt = "Press Q and E to move the view.";
        gfx.drawString(txt, 400 - getWidth(txt, gfx) / 2, 50);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        lastEvent = e;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        lastEvent = e;
    }
}
