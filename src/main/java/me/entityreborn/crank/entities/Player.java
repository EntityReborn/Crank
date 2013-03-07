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
package me.entityreborn.crank.entities;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Set;
import me.entityreborn.crank.EntityManager;
import me.entityreborn.crank.KeyListener;
import me.entityreborn.crank.SoundManager;
import me.entityreborn.crank.gui.Sprite;
import me.entityreborn.crank.screens.PlayScreen;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class Player extends LivingEntity implements KeyListener {
    
    private static Player instance;
    
    public static Player get() {
        if (instance == null) {
            instance = new Player();
        }
        
        return instance;
    }
    
    private boolean leftPressed;
    private boolean rightPressed;
    private PlayerState playerState;
    
    private Player() {
        sprite = new Sprite("Player.png", 128, 256);
        sprite.selectRow(0, 4);
        y = 10;
    }

    @Override
    public void tick() {
        x += xAccel;
        
        
        if (yAccel == 0 ) {
            if (xAccel != 0) {
                playerState = PlayerState.WALKING;
                sprite.selectRow(2, 2);
                sprite.setFlipH(xAccel < 0);
            } else {
                playerState = PlayerState.STANDING;
                sprite.selectRow(0, 4);
            }
        }
        
        if (yAccel < 0) {
            playerState = PlayerState.JUMPING;
            sprite.selectRow(1, 4);
        } else if (yAccel > 0){
            playerState = PlayerState.FALLING;
            sprite.selectRow(1, 4);
        }
        
        Set<Entity> ents = EntityManager.get().getEntityCollisions(this);
        boolean doaccel = true;
        
        if (playerState != PlayerState.JUMPING && !ents.isEmpty()) {
            for (Entity ent : ents) {
                if (ent.isSolid) {
                    doaccel = false;
                    setY(ent.getY() - ent.getHeight() + 1);
                    ent.addRider(this);
                    yAccel = 0;
                    break;
                }
            }
        } 
        
        if (doaccel) {
            y += yAccel;
            yAccel += 1;
            
            if (riding != null) {
                riding.removeRider(this);
            }
        }
    }

        public void jump() {
        yAccel -= 18;
        
        if (riding != null) {
            riding.removeRider(this);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_RIGHT) {
            if (leftPressed) {
                xAccel = 0;
            } else {
                xAccel = 5;
            }
            
            rightPressed = true;
        } else if (key == KeyEvent.VK_LEFT) {
            if (rightPressed) {
                xAccel = 0;
            } else {
                xAccel = -5;
            }
            
            leftPressed = true;
        } else if (key == KeyEvent.VK_V) {
            setVisible(!isVisible());
        } else if (key == KeyEvent.VK_SPACE) {
            SoundManager.get().playSound("jump.wav");
            jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_RIGHT) {
            if (!leftPressed) {
                xAccel = 0;
            } else {
                xAccel = -5;
            }
            
            rightPressed = false;
        } else if (key == KeyEvent.VK_LEFT) {
            if (!rightPressed) {
                xAccel = 0;
            } else {
                xAccel = 5;
            }
            
            leftPressed = false;
        }
    }
    
    @Override
    public Rectangle getBounds() {
        int offsetX = PlayScreen.get().getCamera().getX();
        
        return new Rectangle(getX() - 32 - offsetX, 
                    getActualY() - getHeight(), 64, getHeight());
    }
    
    @Override
    public void animate() {
        if (playerState == PlayerState.WALKING || 
                playerState == PlayerState.STANDING) {
            sprite.increment();
        } else {
            if (yAccel < 0) {
                sprite.selectFrame(1);
            } else {
                sprite.selectFrame(2);
            }
        }
    }
}
