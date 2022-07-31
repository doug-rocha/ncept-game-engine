/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.imaging;

import com.ncept.engine.utils.Debug;
import java.awt.image.BufferedImage;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class SpriteSheet {

    private BufferedImage Img;
    private int SX, SY;

    public SpriteSheet(Image img, int sx, int sy) {
        this.Img = img.getRawImage();
        this.SX = sx;
        this.SY = sy;
    }

    private BufferedImage getSpriteImage(int x, int y) {
        try {
            return Img.getSubimage(x * this.SX, y * this.SY, this.SX, this.SY);
        } catch (Exception e) {
            Debug.LOG_ERROR("Sprite >> Falha ao adquirir \"SubImagem\"\n" + e.toString());
        }
        return null;
    }

    public Image getSprite(int array_x, int array_y) {
        return new Image(getSpriteImage(array_x, array_y));
    }

    public Image[][] getSpriteArray() {
        int cols = (int) Math.floor(this.Img.getWidth() / SX);
        int rows = (int) Math.floor(this.Img.getHeight() / SY);

        Image[][] retorno = new Image[cols][rows];
        for (int ix = 0; ix < cols; ix++) {
            for (int iy = 0; iy < rows; iy++) {
                retorno[ix][iy] = new Image(getSpriteImage(ix, iy));
            }
        }
        return retorno;
    }

}
