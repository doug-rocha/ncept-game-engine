package com.ncept.engine.renderEngine.graphics.gui.components;

import com.ncept.engine.renderEngine.core.Drawer;
import com.ncept.engine.renderEngine.core.Window;
import com.ncept.engine.renderEngine.graphics.gui.GUI;
import com.ncept.engine.renderEngine.graphics.imaging.Image;
import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public final class CustomMouseCursor extends GUI {

    private Path2D pathShape;
    private double oldx, oldy;

    private Paint backPaint;

    public CustomMouseCursor() {
        setBackColor(Color.WHITE);
        doDraw = true;
    }

    @Override
    public void render(Window win, Drawer d) {
        if (doDraw) {
            if (pathShape != null) {
                d.fillShape(pathShape, backPaint != null ? backPaint : backColor);
            } else if (hasBackground) {
                d.fillRect((int) x, (int) y, (int) msx, (int) msy, backColor);
            } else if (hasImage && img != null) {
                d.drawImage(img, (int) x, (int) y);
            }
            if (showWireFrame) {
                d.drawRect((int) x, (int) y, (int) msx, (int) msy, frameColor);
            }
            if (hasLabel && !labelText.equals("")) {
                d.drawString(labelText, labelColor, mLabelFont, mLabelX, mLabelY);
            }
        }
    }

    @Override
    public void update(Window win) {
        if (pathShape != null) {
            AffineTransform at = new AffineTransform();
            at.setToTranslation(x - oldx, y - oldy);
            pathShape.transform(at);
            oldx = x;
            oldy = y;
        }
    }

    @Override
    public void setText(String string) {

    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
        hasImage = img != null;
    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
        hasBackground = backColor != null;
    }

    public Path2D getPathShape() {
        return pathShape;
    }

    public void setPathShape(Path2D pathShape) {
        this.pathShape = pathShape;
        oldx = oldy = 0;
    }

    public Paint getBackPaint() {
        return backPaint;
    }

    public void setBackPaint(Paint backPaint) {
        this.backPaint = backPaint;
    }
}
