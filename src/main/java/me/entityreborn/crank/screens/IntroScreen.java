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

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import me.entityreborn.crank.gui.Canvas;
import me.entityreborn.crank.Crank;
import me.entityreborn.crank.GameManager;
import me.entityreborn.crank.KeyListener;
import me.entityreborn.crank.KeyListenerAdapter;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class IntroScreen implements Screen, KeyListener {

    public IntroScreen() {
        Crank.get().addKeyListener(new KeyListenerAdapter(this));
    }

    @Override
    public void render(Graphics2D gfx) {
        String text = "Press enter to start...";
        FontMetrics mtx = gfx.getFontMetrics();
        int width = mtx.stringWidth(text);
        int height = mtx.getHeight();
        int canvaswidth = Canvas.get().getWidth() / 2;
        int canvasheight = Canvas.get().getHeight() / 2;
        
        gfx.drawString(text, canvaswidth - (width / 2), 
                canvasheight - (height / 2));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_ENTER) {
            GameManager.get().setGameState(GameManager.GameState.PLAY);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void tick() {
    }
    
}
