package models;

public class Placement {
    public static final Placement DOWN = new Placement(0,1);
    public static final Placement UP = new Placement(0,-1);
    public static final Placement LEFT = new Placement(-1,0);
    public static final Placement RIGHT = new Placement(1,0);

    public int x;

    public int y;

    public Placement(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Placement(Placement placementToCopy) {
        this.x = placementToCopy.x;
        this.y = placementToCopy.y;
    }

    public void add(Placement otherPlacement) {
        this.x += otherPlacement.x;
        this.y += otherPlacement.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Placement placement = (Placement) o;
        return x == placement.x && y == placement.y;
    }
}
