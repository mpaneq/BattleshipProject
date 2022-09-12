package models;

import java.awt.*;

public class Field extends Rectangle {
    private final Color HIT_COLOUR = new Color(219, 23, 23, 180);
    private final Color MISS_COLOUR = new Color(26, 26, 97, 180);
    private final int PADDING = 3;
    private boolean showMarker;
    private Ship shipAtMarker;
    public int value = 0;

    public Field(int x, int y, int width, int height) {
        super(x, y, width, height);
        reset();
    }

    public void reset() {
        shipAtMarker = null;
        showMarker = false;
    }
    public void mark() {
        if(!showMarker && isShip()) {
            shipAtMarker.destroySection();
        }
        showMarker = true;
    }
    public boolean isMarked() {
        return showMarker;
    }

    public void setAsShip(Ship ship, int value) {
        this.value = value;
        this.shipAtMarker = ship;
    }

    public boolean isShip() {
        return shipAtMarker != null;
    }

    public Ship getAssociatedShip() {
        return shipAtMarker;
    }
    public void paint(Graphics g) {
        if(!showMarker) return;
        g.setColor(isShip() ? HIT_COLOUR : MISS_COLOUR);
        g.fillRect(placement.x+PADDING+1, placement.y+PADDING+1, width-PADDING*2, height-PADDING*2);
    }
}
