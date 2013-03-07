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

import java.awt.Image;
import java.awt.image.BufferedImage;
import me.entityreborn.crank.Util;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class Sprite {

    private BufferedImage spriteSheet;
    private String filename;
    private int curRow;
    private int curFrame;
    private int framesPerRow;
    private int frameHeight;
    private int frameWidth;
    protected boolean flipH;
    protected boolean flipV;

    public Sprite(String fname, int width, int height) {
        spriteSheet = Util.loadImage(fname);
        frameHeight = height;
        frameWidth = width;
    }

    public void selectRow(int row, int framecount) {
        if (curRow != row) {
            curRow = row;
            curFrame = 0;
        }
        framesPerRow = framecount;
    }

    public Image getImage() {
        int x = curFrame * frameWidth;
        int y = curRow * frameHeight;
        
        BufferedImage img = spriteSheet.getSubimage(x, y, frameWidth, frameHeight);
        
        if (isHFlipped()) {
           img = Util.flipImageH(img);
        }
        
        if (isVFlipped()) {
            img = Util.flipImageV(img);
        }
        return img;
    }
    
    public void increment() {
        curFrame += 1;
        
        if (curFrame >= framesPerRow) {
            curFrame = 0;
        }
    }
    
    public int getHeight() {
        return frameHeight;
    }
    
    public int getWidth() {
        return frameWidth;
    }

    public boolean isHFlipped() {
        return flipH;
    }

    public boolean isVFlipped() {
        return flipV;
    }

    public void setFlipH(boolean flipH) {
        this.flipH = flipH;
    }

    public void setFlipV(boolean flipV) {
        this.flipV = flipV;
    }

    public void selectFrame(int i) {
        curFrame = i;
    }
}
