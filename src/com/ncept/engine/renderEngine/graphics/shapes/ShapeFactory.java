package com.ncept.engine.renderEngine.graphics.shapes;

import com.ncept.engine.renderEngine.core.GraphicsCore;
import java.awt.geom.Path2D;

/**
 *
 * @author Douglas Rocha de Oliveira
 */
public final class ShapeFactory {

    public static class Star {

        public static Path2D createStar(double centerX, double centerY, double innerRadius, double outerRadius, int numRays, double startAngleRad) {
            Path2D path = new Path2D.Double();
            double deltaAngleRad = Math.PI / numRays;
            for (int i = 0; i < numRays * 2; i++) {
                double angleRad = startAngleRad + i * deltaAngleRad;
                double ca = Math.cos(angleRad);
                double sa = Math.sin(angleRad);
                double relX = ca;
                double relY = sa;
                if ((i & 1) == 0) {
                    relX *= outerRadius;
                    relY *= outerRadius;
                } else {
                    relX *= innerRadius;
                    relY *= innerRadius;
                }
                if (i == 0) {
                    path.moveTo(centerX + relX, centerY + relY);
                } else {
                    path.lineTo(centerX + relX, centerY + relY);
                }
            }
            path.closePath();
            return path;
        }

        public static Path2D createStar(double centerX, double centerY, double innerRadius, double outerRadius, int numRays, double startAngleRad, boolean autoSize) {
            centerX = GraphicsCore.calcSizeDouble(centerX);
            centerY = GraphicsCore.calcSizeDouble(centerY);
            innerRadius = GraphicsCore.calcSizeDouble(innerRadius);
            outerRadius = GraphicsCore.calcSizeDouble(outerRadius);
            return createStar(centerX, centerY, innerRadius, outerRadius, numRays, startAngleRad);
        }

        public static Path2D createDefaultStar(double radius, double centerX, double centerY, boolean autoSize) {
            return createStar(centerX, centerY, radius, radius * 2.5, 5, Math.toRadians(-18), autoSize);
        }

        public static Path2D createDefaultStar(double radius, double centerX, double centerY) {
            return createStar(centerX, centerY, radius, radius * 2.5, 5, Math.toRadians(-18));
        }
    }

}
