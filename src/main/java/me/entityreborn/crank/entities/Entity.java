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

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.HashSet;
import java.util.Set;
import me.entityreborn.crank.gui.Canvas;
import me.entityreborn.crank.gui.Sprite;
import me.entityreborn.crank.screens.PlayScreen;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public abstract class Entity {

    protected int x;
    protected int y;
    protected int xAccel;
    protected int yAccel;
    protected boolean isSolid;
    protected boolean isVisible = true;
    protected boolean affectedByGravity;
    protected int gravAccel;
    protected Sprite sprite;
    protected Entity riding;
    private Set<Entity> riders = new HashSet<>();
    
    public Rectangle getBounds() {
        int offsetX = PlayScreen.get().getCamera().getX();
        
        return new Rectangle(getX() - (getWidth() / 2) - offsetX, 
                    getActualY() - getHeight(), getWidth(), getHeight());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    
    public int getActualY() {
        return y + Canvas.get().getHeight();
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getXaccel() {
        return xAccel;
    }

    public void setXaccel(int xaccel) {
        this.xAccel = xaccel;
    }

    public int getYaccel() {
        return yAccel;
    }

    public void setYaccel(int yaccel) {
        this.yAccel = yaccel;
    }
    
    public int getWidth() {
        return sprite.getWidth();
    }
    
    public int getHeight() {
        return sprite.getHeight();
    }
    
    private void landedOn(Entity entity) {
        gravAccel = 0;
    }
    
    private boolean isSolid() {
        return isSolid;
    }
    
    public void render(Graphics2D gfx, int offsetX) {
        if (sprite != null && isVisible) {
            gfx.drawImage(sprite.getImage(), 
                    getX() - offsetX - (getWidth() / 2), 
                    getActualY() - getHeight(), null);
            gfx.drawRect(getX() - 1 - offsetX, getActualY() - 1, 2, 2);
        }
    }

    protected boolean isVisible() {
        return isVisible;
    }
    
    public void tick() {
        x += xAccel;
        y += yAccel;
        
        for (Entity entity : riders) {
            entity.setX(entity.getX() + xAccel);
            entity.setY(entity.getY() + yAccel);
        }
    }

    protected void setVisible(boolean visible) {
        isVisible = visible;
    }

    public Set<Entity> getRiders() {
        return riders;
    }
    
    public void addRider(Entity rider) {
        riders.add(rider);
        rider.setRiding(this);
    }
    
    public void removeRider(Entity rider) {
        riders.remove(rider);
        rider.setRiding(null);
    }

    public Entity getRiding() {
        return riding;
    }

    public void setRiding(Entity riding) {
        this.riding = riding;
    }

    public void animate() {
        if (sprite != null) {
            sprite.increment();
        }
    }
}
