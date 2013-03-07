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

import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;
import me.entityreborn.crank.gui.Canvas;
import me.entityreborn.crank.screens.IntroScreen;
import me.entityreborn.crank.screens.PlayScreen;
import me.entityreborn.crank.screens.Screen;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class GameManager {
    
    private static GameManager instance;
    
    public static GameManager get() {
        if (instance == null) {
            instance = new GameManager();
        }
        
        return instance;
    }

    private Screen currentScreen;
    private Timer timer;
    private TimerTask tickTask;
    private GameState state;
    
    private GameManager() {
        currentScreen = new IntroScreen();
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }
    
    public void setGameState(GameState newstate) {
        state = newstate;
        
        if (state == GameState.PLAY) {
            currentScreen = PlayScreen.get();
        }
    }
    
    public GameState getGameState() {
        return state;
    }
    
    public void start() {
        timer = new Timer();
        
        tickTask = new TimerTask() {

            @Override
            public void run() {
                tick();
            }
        };
        
        timer.scheduleAtFixedRate(tickTask, 0, 1000/30);
    }
    
    public void tick() {
        getCurrentScreen().tick();
        Canvas.get().repaint();
    }
    
    public void render(Graphics2D gfx) {
        getCurrentScreen().render(gfx);
    }
}
