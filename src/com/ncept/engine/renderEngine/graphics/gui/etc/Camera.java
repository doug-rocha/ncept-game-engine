/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ncept.engine.renderEngine.graphics.gui.etc;

import com.ncept.engine.renderEngine.core.GraphicsCore;
import java.awt.Point;

/**
 *
 * @author Douglas Rocha de Oliveira - NonaCept
 */
public class Camera {

    private static double targetOldX, targetOldY;
    private static Point oldPoint = new Point();

    private static final Point position = new Point();
    private static final Point mPosition = new Point();

    public static Point calculatePos(double target_x, double target_y, double target_sx, double target_sy, double min_x, double min_y, double max_x, double max_y) {
        Point retorno = oldPoint;

        int onScreenx = (int) (target_x + retorno.x);
        int onScreeny = (int) (target_y + retorno.y);
        if (onScreenx < min_x) {
            if (target_x < targetOldX) {
                retorno.x = (int) (min_x - target_x);
            }
        } else if (onScreenx + target_sx > max_x) {
            if (target_x > targetOldX) {
                retorno.x = (int) (max_x - (target_x + target_sx));
            }
        }
        if (onScreeny < min_y) {
            if (target_y < targetOldY) {
                retorno.y = (int) (min_y - target_y);
            }
        } else if (onScreeny + target_sy > max_y) {
            if (target_y > targetOldY) {
                retorno.y = (int) (max_y - (target_y + target_sy));
            }
        }
        targetOldX = target_x;
        targetOldY = target_y;
        oldPoint = retorno;
        return retorno;
    }

    public static Point getPosition() {
        return position;
    }

    public static void setPosition(Point position) {
        setPosition(position.getX(), position.getY());
    }

    public static synchronized void setPosition(double x, double y) {
        position.setLocation(x, y);
        recalcModifiedPosition();
    }

    public static Point getModifiedPosition() {
        return mPosition;
    }

    public static void recalcModifiedPosition() {
        mPosition.setLocation(GraphicsCore.calcSizeDouble(position.getX()), GraphicsCore.calcSizeDouble(position.getY()));
    }

}
