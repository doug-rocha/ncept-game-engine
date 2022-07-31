/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.imaging;

import com.ncept.engine.utils.ResourceLoader;
import java.awt.image.BufferedImage;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Image {

    private BufferedImage Img;

    public Image(String path) {
        this.Img = ResourceLoader.loadImage(path);
    }
    
    public Image(BufferedImage img) {
        this.Img = img;
    }

    public BufferedImage getRawImage() {
        return this.Img;
    }

    public int getWidth() {
        return this.Img.getWidth(null);
    }

    public int getHeight() {
        return this.Img.getHeight(null);
    }
}
