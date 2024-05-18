/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.core;

import com.ncept.engine.Properties;
import com.ncept.engine.renderEngine.graphics.imaging.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Drawer {

    private final Graphics2D g;
    private BufferStrategy st;

    private int cameraX, cameraY;

    public Drawer(Window win) {
        //this.st = win.getBufferStrategy();
        GraphicsCore.BUFFER = new BufferedImage(Properties.WIDTH, Properties.HEIGHT - 31, BufferedImage.TYPE_INT_ARGB);
        this.g = (Graphics2D) GraphicsCore.BUFFER.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
    }

    public void drawImage(Image img, int x, int y) {
        g.drawImage(img.getRawImage(), x, y, null);
    }

    public void drawImage(Image img, int x, int y, float alpha) {
        float[] scales = {1f, 1f, 1f, alpha};
        float[] offsets = new float[4];
        RescaleOp rop = new RescaleOp(scales, offsets, null);
        g.drawImage(img.getRawImage(), rop, x, y);
    }

    public void drawImage(Image img, int x, int y, int sx, int sy) {
        g.drawImage(img.getRawImage(), x, y, sx, sy, null);
    }

    public void drawImage(Image img, int x, int y, int sx, int sy, float alpha) {
        float[] scales = {1f, 1f, 1f, alpha};
        float[] offsets = new float[4];
        RescaleOp rop = new RescaleOp(scales, offsets, null);
        BufferedImage buff = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) buff.getGraphics();
        g2d.drawImage(img.getRawImage(), rop, 0, 0);
        g.drawImage(buff, x, y, sx, sy, null);
    }

    public void drawImage(Image img, int x, int y, int x2, int y2, int sx, int sy) {
        g.drawImage(img.getRawImage(), x, y, x2, y2, 0, 0, sx, sy, null);
    }

    public void drawString(String str, int x, int y) {
        g.drawString(str, x, y);
    }

    public void drawString(String str, Color color, Font font, int x, int y) {
        Color old_color = g.getColor();
        g.setColor(color);

        Font old_font = g.getFont();
        if (font != null) {
            g.setFont(font);
        }

        g.drawString(str, x, y);

        g.setColor(old_color);
        g.setFont(old_font);
    }

    public void fillRect(int x, int y, int sx, int sy) {
        g.fillRect(x, y, sx, sy);
    }

    public void fillRoundRect(int x, int y, int sx, int sy, int rh, int rv) {
        g.fillRoundRect(x, y, sx, sy, rh, rv);
    }

    public void setColor(Color color) {
        g.setColor(color);
    }

    public void setCameraPos(int cx, int cy) {
        g.translate(-cameraX, -cameraY);
        cameraX = cx;
        cameraY = cy;
        g.translate(cameraX, cameraY);
    }

    public void moveCamera(int mx, int my) {
        cameraX += mx;
        cameraY += my;
        g.translate(mx, my);
    }

    public int getCX() {
        return cameraX;
    }

    public int getCY() {
        return cameraY;
    }

    public void fillRect(int x, int y, int sx, int sy, Color color) {
        Color old_color = g.getColor();
        g.setColor(color);
        g.fillRect(x, y, sx, sy);
        g.setColor(old_color);
    }

    public void fillRoundRect(int x, int y, int sx, int sy, int rh, int rv, Color color) {
        Color old_color = g.getColor();
        g.setColor(color);
        g.fillRoundRect(x, y, sx, sy, rh, rv);
        g.setColor(old_color);
    }

    public void drawRect(int x, int y, int sx, int sy) {
        g.drawRect(x, y, sx, sy);
    }

    public void drawRect(int x, int y, int sx, int sy, Color color) {
        Color old_color = g.getColor();
        g.setColor(color);
        g.drawRect(x, y, sx, sy);
        g.setColor(old_color);
    }

    public void drawRoundRect(int x, int y, int sx, int sy, int rh, int rv) {
        g.drawRoundRect(x, y, sx, sy, rh, rv);
    }

    public void drawRoundRect(int x, int y, int sx, int sy, int rh, int rv, Color color) {
        Color old_color = g.getColor();
        g.setColor(color);
        g.drawRoundRect(x, y, sx, sy, rh, rv);
        g.setColor(old_color);
    }

    public FontMetrics getFontMetrics() {
        return g.getFontMetrics();
    }

    public FontMetrics getFontMetrics(Font font) {
        return g.getFontMetrics(font);
    }

    public RenderingHints getRenderingHints() {
        return g.getRenderingHints();
    }

    public void clear(Color clear_color) {
        Color old = g.getColor();
        g.setColor(clear_color);
        g.fillRect(-cameraX, -cameraY, Properties.WIDTH, Properties.HEIGHT);
        g.setColor(old);
    }
}
