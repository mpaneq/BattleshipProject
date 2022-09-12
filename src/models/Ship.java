package models;

import models.Board;
import models.Placement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class Ship {

    static final int BATTLESHIP_SIZE = 6;
    static final int CRUISER_SIZE = 4;
    static final int DESTROYER_SIZE = 2;
    public enum ShipPlacementColour {Valid, Invalid, Placed}
    private Placement fieldPlacement;
    private Placement drawPlacement;
    private int segments;
    private boolean isTurned;
    private int destroyedSections;
    private ShipPlacementColour shipPlacementColour;
    public Ship(Placement fieldPlacement, Placement drawPlacement, int segments, boolean isTurned) {
        this.fieldPlacement = fieldPlacement;
        this.drawPlacement = drawPlacement;
        this.segments = segments;
        this.isTurned = isTurned;
        destroyedSections = 0;
        shipPlacementColour = ShipPlacementColour.Placed;
    }

    public void paint(Graphics g) {
        if(shipPlacementColour == ShipPlacementColour.Placed) {
            g.setColor(destroyedSections >= segments ? Color.RED : Color.DARK_GRAY);
        } else {
            g.setColor(shipPlacementColour == ShipPlacementColour.Valid ? Color.GREEN : Color.RED);
        }
        if(isTurned) paintHorizontal(g);
        else paintVertical(g);
    }

    public void setShipPlacementColour(ShipPlacementColour shipPlacementColour) {
        this.shipPlacementColour = shipPlacementColour;
    }

    public void toggleSideways() {
        isTurned = !isTurned;
    }

    public void destroySection() {
        destroyedSections++;
    }

    public boolean isDestroyed() { return destroyedSections >= segments; }

    public void setDrawPosition(Placement fieldPlacement, Placement drawPlacement) {
        this.drawPlacement = drawPlacement;
        this.fieldPlacement = fieldPlacement;
    }
    public boolean isTurned() {
        return isTurned;
    }

    public int getSegments() {
        return segments;
    }

    public List<Placement> getOccupiedCoordinates() {
        List<Placement> result = new ArrayList<>();
        if(isTurned) {
            for(int x = 0; x < segments; x++) {
                result.add(new Placement(fieldPlacement.x+x, fieldPlacement.y));
            }
        } else {
            for(int y = 0; y < segments; y++) {
                result.add(new Placement(fieldPlacement.x, fieldPlacement.y+y));
            }
        }
        return result;
    }

    public void paintVertical(Graphics g) {
        int boatWidth = (int)(Board.FIELD_SIZE * 0.8);
        int boatLeftX = drawPlacement.x + Board.FIELD_SIZE / 2 - boatWidth / 2;
        g.fillPolygon(new int[]{drawPlacement.x+Board.FIELD_SIZE/2,boatLeftX,boatLeftX+boatWidth},
                new int[]{drawPlacement.y+Board.FIELD_SIZE/4, drawPlacement.y+Board.FIELD_SIZE, drawPlacement.y+Board.FIELD_SIZE},3);
        g.fillRect(boatLeftX, drawPlacement.y+Board.FIELD_SIZE, boatWidth,
                (int)(Board.FIELD_SIZE * (segments-1.2)));
    }
    public void paintHorizontal(Graphics g) {
        int boatWidth = (int)(Board.FIELD_SIZE * 0.8);
        int boatTopY = drawPlacement.y + Board.FIELD_SIZE / 2 - boatWidth / 2;
        g.fillPolygon(new int[]{drawPlacement.x+Board.FIELD_SIZE/4, drawPlacement.x+Board.FIELD_SIZE, drawPlacement.x+Board.FIELD_SIZE},
                new int[]{drawPlacement.y+Board.FIELD_SIZE/2,boatTopY,boatTopY+boatWidth},3);
        g.fillRect(drawPlacement.x+Board.FIELD_SIZE,boatTopY,
                (int)(Board.FIELD_SIZE * (segments-1.2)), boatWidth);
    }
}
