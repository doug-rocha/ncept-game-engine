/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.physicsEngine.collision;

import com.ncept.engine.inputEngine.Mouse;
import com.ncept.engine.physicsEngine.objects.GameObject;
import com.ncept.engine.renderEngine.core.GraphicsCore;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.gui.GUI;
import java.awt.Point;
import java.awt.Rectangle;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Collision {
    
    public static boolean IsCollinding(GameObject go1, GameObject go2) {
        
        Rectangle hitbox1 = go1.getHitbox();
        Rectangle hitbox2 = go2.getHitbox();
        
        Rectangle real_hitbox1 = new Rectangle((int) (go1.getX() + hitbox1.x), (int) (go1.getY() + hitbox1.y), hitbox1.width, hitbox1.height);
        Rectangle real_hitbox2 = new Rectangle((int) (go2.getX() + hitbox2.x), (int) (go2.getY() + hitbox2.y), hitbox2.width, hitbox2.height);
        
        return real_hitbox1.intersects(real_hitbox2);
    }
    
    public static boolean isInArea(GameObject go1, GameObject go2) {
        
        Rectangle hitbox1 = new Rectangle((int) go1.getX(), (int) go1.getY(), (int) go1.getSx(), (int) go1.getSy());
        Rectangle hitbox2 = new Rectangle((int) go2.getX(), (int) go2.getY(), (int) go2.getSx(), (int) go2.getSy());
        
        return hitbox1.intersects(hitbox2);
    }
    
    public static boolean isMouseColliding(GUI gui) {
        Mouse m = Window.getMouse();
        
        int mx = (int) (m.getX() * GraphicsCore.MODSIZE_X);
        int my = (int) (m.getY() * GraphicsCore.MODSIZE_Y);
        
        Rectangle r1 = new Rectangle(mx, my, 1, 1);
        Rectangle r2 = new Rectangle((int) gui.getX(), (int) gui.getY(), (int) gui.getSX(), (int) gui.getSY());
        
        return r1.intersects(r2);
    }
    
    public static Point deColliding(GameObject go1, GameObject go2) {
        Point retorno = new Point();
        Rectangle hitbox1 = go1.getHitbox();
        Rectangle hitbox2 = go2.getHitbox();
        
        Rectangle real_hitbox1 = new Rectangle((int) (go1.getX() + hitbox1.x), (int) (go1.getY() + hitbox1.y), hitbox1.width, hitbox1.height);
        Rectangle real_hitbox2 = new Rectangle((int) (go2.getX() + hitbox2.x), (int) (go2.getY() + hitbox2.y), hitbox2.width, hitbox2.height);
        
        double w = 0.5 * (real_hitbox2.width + real_hitbox1.width);
        double h = 0.5 * (real_hitbox2.height + real_hitbox1.height);
        double dx = real_hitbox2.getCenterX() - real_hitbox1.getCenterX();
        double dy = real_hitbox2.getCenterY() - real_hitbox1.getCenterY();
        
        if (Math.abs(dx) < w && Math.abs(dy) < h) {
            double dif_x = real_hitbox1.x - real_hitbox2.x;
            double dif_y = real_hitbox1.y - real_hitbox2.y;
            
            double wy = w * dy;
            double hx = h * dx;
            
            if (wy >= hx) {
                if (wy >= -hx) {
                    retorno.y = (int) (-real_hitbox1.height - dif_y);
                } else {
                    retorno.x = (int) (real_hitbox2.width - dif_x);
                }
            } else {
                if (wy >= -hx) {
                    retorno.x = (int) (-real_hitbox1.width - dif_x);
                } else {
                    retorno.y = (int) (real_hitbox2.height - dif_y);
                }
            }
        }
        return retorno;
    }
    
}
