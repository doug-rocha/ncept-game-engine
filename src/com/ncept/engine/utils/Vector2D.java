package com.ncept.engine.utils;

public class Vector2D {

    protected double dX;
    protected double dY;

    public Vector2D() {
        dX = dY = 0.0;
    }

    public Vector2D(double dX, double dY) {
        this.dX = dX;
        this.dY = dY;
    }

    public String toString() {
        return "Vector2D(" + dX + ", " + dY + ")";
    }

    public double length() {
        return Math.sqrt(dX * dX + dY * dY);
    }

    public Vector2D add(Vector2D v1) {
        Vector2D v2 = new Vector2D(this.dX + v1.dX, this.dY + v1.dY);
        return v2;
    }

    public Vector2D sub(Vector2D v1) {
        Vector2D v2 = new Vector2D(this.dX - v1.dX, this.dY - v1.dY);
        return v2;
    }

    public Vector2D scale(double scaleFactor) {
        Vector2D v2 = new Vector2D(this.dX * scaleFactor, this.dY * scaleFactor);
        return v2;
    }

    public Vector2D normalize() {
        Vector2D v2 = new Vector2D();

        double length = Math.sqrt(this.dX * this.dX + this.dY * this.dY);
        if (length != 0) {
            v2.dX = this.dX / length;
            v2.dY = this.dY / length;
        }

        return v2;
    }

    public double dotProduct(Vector2D v1) {
        return this.dX * v1.dX + this.dY * v1.dY;
    }

    public void setX(double x) {
        this.dX = x;
    }

    public void setY(double y) {
        this.dY = y;
    }

    public double getX() {
        return dX;
    }

    public double getY() {
        return dY;
    }
}
