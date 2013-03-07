/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.entityreborn.crank;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;

/**
 *
 * @author import
 */
public class Util {

    public static BufferedImage flipImageV(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage img = op.filter(image, null);
        
        return img;
    }

    public static BufferedImage flipImageH(BufferedImage image) {
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-image.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        BufferedImage img = op.filter(image, null);
        
        return img;
    }

    public static BufferedImage loadImage(String filename) {
        URL url = Util.class.getResource("/graphics/" + filename);
        Image sourceImage = Toolkit.getDefaultToolkit().createImage(url);
        
        LoaderObserver obs = new LoaderObserver();
        
        sourceImage.getWidth(obs);
        
        while (!obs.isLoaded()) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }
        }
        
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
        
        BufferedImage image = graphicsConfiguration.createCompatibleImage(sourceImage.getWidth(null), sourceImage.getHeight(null), Transparency.BITMASK);
        
        Graphics graphics = image.createGraphics();
        graphics.drawImage(sourceImage, 0, 0, null);
        graphics.dispose();
        
        return image;
    }

    private Util() {
    }

    private static class LoaderObserver implements ImageObserver {

        boolean imageLoaded = false;

        public boolean isLoaded() {
            return imageLoaded;
        }

        @Override
        public boolean imageUpdate(Image image, int flags, int x, int y, int width, int height) {
            if ((flags & ALLBITS) != 0) {
                imageLoaded = true;
                return false;
            }

            return true;
        }
    }
}
