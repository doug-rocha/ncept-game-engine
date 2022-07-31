/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.physicsEngine.objects;

import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.GameManager;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.imaging.Image;
import com.ncept.engine.utils.Debug;
import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public abstract class GameObject {
    
    protected double x, y, sx, sy;
    protected boolean doDraw = true, didDraw, hasImage, isDestroyed, drawHitbox, hasColision;
    protected Image image;
    protected Color color;
    protected Rectangle hitbox;
    
    public abstract Enum getType();
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public void render(Window win, Drawer d) {
        if (doDraw) {
            if (hasImage) {
                d.drawImage(image, (int) x, (int) y, (int) sx, (int) sy);
            } else {
                if (color == null) {
                    color = Color.WHITE;
                }
                d.fillRect((int) x, (int) y, (int) sx, (int) sy, color);
            }
            if (drawHitbox && hasColision) {
                d.fillRect((int) (hitbox.x + this.x), (int) (hitbox.y + this.y), hitbox.width, hitbox.height, new Color(25, 50, 150, 100));
            }
            didDraw = true;
        }
    }
    
    public abstract void update(Window win, GameManager gm);
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getSx() {
        return sx;
    }
    
    public double getSy() {
        return sy;
    }
    
    public boolean isDidDraw() {
        return didDraw;
    }
    
    public Rectangle getHitbox() {
        return this.hitbox;
    }
    
    public void setHitbox(Rectangle box) {
        this.hitbox = box;
        this.setColidable(true);
    }
    
    public void setHitbox(int bx, int by, int bsx, int bsy) {
        this.setHitbox(new Rectangle(bx, by, bsx, bsy));
    }
    
    public void setColidable(boolean colidable) {
        this.hasColision = colidable;
    }
    
    public boolean isColidable() {
        return this.hasColision;
    }
    
    public void setImage(Image img) {
        this.image = img;
        this.hasImage = true;
    }
    
    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
