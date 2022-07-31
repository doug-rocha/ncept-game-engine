/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class ResourceLoader {

    public static BufferedImage loadImage(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            return img;
        } catch (Exception e) {
            Debug.LOG_ERROR(e.getMessage());
        }
        return null;
    }

}
