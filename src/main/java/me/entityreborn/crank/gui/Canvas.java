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

import java.awt.*;
import javax.swing.JPanel;
import me.entityreborn.crank.GameManager;

/**
 *
 * @author Jason Unger <entityreborn@gmail.com>
 */
public class Canvas extends JPanel {

    private static final long serialVersionUID = 1L;

    public enum FadeState {

        HIDDEN,
        FADEIN,
        SHOWING,
        FADEOUT
    }
    private static Canvas instance;
    private GameManager manager = GameManager.get();
    private float opacity;
    private FadeState fade = FadeState.FADEIN;

    public static Canvas get() {
        if (instance == null) {
            instance = new Canvas();
        }

        return instance;
    }

    public Canvas() {
        initComponents();

        if (instance == null) {
            instance = this;
        }

        manager.start();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D gfx = (Graphics2D) g;

        gfx.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, opacity));
        gfx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (fade == FadeState.FADEIN) {
            opacity += 0.03;

            if (opacity > 1) {
                fade = FadeState.SHOWING;
                opacity = 1;
            }
        } else if (fade == FadeState.FADEOUT) {
            opacity -= 0.03;

            if (opacity < 0) {
                fade = FadeState.HIDDEN;
                opacity = 0;
            }
        }

        manager.render(gfx);

        Toolkit.getDefaultToolkit().sync();

        g.dispose();
    }

    public void fadeIn() {
        fade = FadeState.FADEIN;
    }

    public void fadeOut() {
        fade = FadeState.FADEOUT;
    }

    public FadeState getFadeState() {
        return fade;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
