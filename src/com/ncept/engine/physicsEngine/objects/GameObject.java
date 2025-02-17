/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.physicsEngine.objects;

import com.ncept.engine.EngineProperties;
import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.GameManager;
import com.ncept.engine.renderEngine.core.GraphicsCore;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.imaging.Image;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public abstract class GameObject implements Comparable<GameObject> {

    protected double x, y, sx, sy;
    protected boolean doDraw = true, didDraw, hasImage, isDestroyed, drawHitbox, hasColision;
    protected Image image;
    protected Color color;
    protected Rectangle hitbox;

    protected double mx, my, msx, msy;
    protected Rectangle mHitbox;
    private double lastMod;

    protected boolean colliding;
    protected Point collidingPoint = new Point();
    protected Point oldCollidingPoint = new Point();

    private Integer zIndex = 0;

    private boolean outBoundRender;

    public abstract Enum getType();

    public void setColor(Color color) {
        this.color = color;
    }

    public void render(Window win, Drawer d) {
        if (doDraw) {
            if (hasImage) {
                d.drawImage(image, (int) mx, (int) my, (int) msx, (int) msy);
            } else {
                if (color == null) {
                    color = Color.WHITE;
                }
                d.fillRect((int) mx, (int) my, (int) msx, (int) msy, color);
            }
            if (drawHitbox && hasColision) {
                d.fillRect((int) (mHitbox.x + this.mx), (int) (mHitbox.y + this.my), mHitbox.width, mHitbox.height, new Color(255, 50, 150, 100));
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
        this.mHitbox = hitbox;
        this.setColidable(true);
        this.drawHitbox = EngineProperties.DEBUG_MODE;
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

    public void setColliding(boolean colliding) {
        this.colliding = colliding;
    }

    public void setColliding(boolean colliding, Point p) {
        this.colliding = colliding;
        this.collidingPoint = p;
    }

    public boolean isColliding() {
        return colliding;
    }

    public Integer getzIndex() {
        return zIndex;
    }

    public void setzIndex(Integer zIndex) {
        this.zIndex = zIndex;
    }

    public void recalculateSize() {
        recalculateSize(false);
    }

    public void recalculateSize(boolean forced) {
        if ((GraphicsCore.MOD_RESOL != lastMod) || forced) {
            mHitbox = new Rectangle((int) calcSize(hitbox.x), (int) calcSize(hitbox.y), (int) calcSize(hitbox.width), (int) calcSize(hitbox.height));
            msx = Math.round(calcSize(sx));
            msy = Math.round(calcSize(sy));
            mx = calcSize(x);
            my = calcSize(y);
            lastMod = GraphicsCore.MOD_RESOL;
        }
    }

    public void setSizes(int sx, int sy) {
        this.sx = sx;
        this.sy = sy;
    }

    protected double calcSize(double value) {
        return GraphicsCore.calcSizeDouble(value);
    }

    @Override
    public int compareTo(GameObject o) {
        return zIndex.compareTo(o.getzIndex());
    }

    public boolean isOnScreen(Drawer d) {
        Rectangle rect1 = new Rectangle((int) Math.round(mx), (int) Math.round(my), (int) Math.round(msx), (int) Math.round(msy));
        Rectangle rect2 = new Rectangle((int) -d.getMCX(), (int) -d.getMCY(), EngineProperties.BUFFER_WIDTH, EngineProperties.BUFFER_HEIGHT);
        return rect1.intersects(rect2);
    }

    public boolean isOutBoundRender() {
        return outBoundRender;
    }

    public void setOutBoundRender(boolean outBoundRender) {
        this.outBoundRender = outBoundRender;
    }

}
