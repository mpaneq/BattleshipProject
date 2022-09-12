package models;

import models.*;

public class Rectangle {

    protected Placement placement;
    protected int width;
    protected int height;
    public Rectangle(Placement placement, int width, int height) {
        this.placement = placement;
        this.width = width;
        this.height = height;
    }
    public Rectangle(int x, int y, int width, int height) {
        this( new Placement(x,y),width,height );
    }

    public boolean isPositionInside(Placement field) {
        return field.x >= placement.x && field.y >= placement.y
                && field.x < placement.x + width && field.y < placement.y + height;
    }
}
