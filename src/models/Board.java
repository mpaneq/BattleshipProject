package models;

import params.SettingsParams;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    public static final int FIELD_SIZE = 30;
    int boardWidth;
    int width;
    int height;
    int leftTopCornerX;
    int leftTopCornerY;
    List<Ship> ships;
    Field[][] fields;
    Random random;

    SettingsParams settingsParams;
    Placement placement;
    boolean shouldShowShips;
    boolean allShipsDestroyed;
    public int [] boatSizes;

    int point = 0;

    private int [] points;
    public Board(int leftTopCornerX, int leftTopCornerY, SettingsParams settingsParams) {
        this.placement = new Placement(leftTopCornerX,leftTopCornerY);
        this.settingsParams = settingsParams;
        this.boardWidth = settingsParams.getmapWidth();
        this.leftTopCornerX = leftTopCornerX;
        this.leftTopCornerY = leftTopCornerY;
        this.width = FIELD_SIZE * boardWidth;
        this.height = FIELD_SIZE * boardWidth;
        this.fields = new Field[boardWidth][boardWidth];
        this.boatSizes = new int [settingsParams.battleshipCount + settingsParams.cruiserCount + settingsParams.destroyerCount];
        this.points = new int [settingsParams.battleshipCount + settingsParams.cruiserCount + settingsParams.destroyerCount];
        fillPointsArray();
        fillBoatSize();
        createMarkerGrid();
        ships = new ArrayList<>();
        random = new Random();
        shouldShowShips = false;
    }

    private void fillPointsArray(){
        for(int i = 0 ; i < settingsParams.battleshipCount ; i ++ ) { points[i] = settingsParams.battleshipPunctation; }
        for(int i = settingsParams.battleshipCount ; i < settingsParams.battleshipCount + settingsParams.cruiserCount ; i ++ ) { points[i] = settingsParams.cruiserPunctation; }
        for(int i = settingsParams.battleshipCount + settingsParams.cruiserCount ; i < settingsParams.battleshipCount + settingsParams.cruiserCount + settingsParams.destroyerCount ; i ++ ) { points[i] = settingsParams.destroyerPunctation; }
    }

    private void fillBoatSize(){
        for(int i = 0 ; i < settingsParams.battleshipCount ; i ++ ) { boatSizes[i] = Ship.BATTLESHIP_SIZE; }
        for(int i = settingsParams.battleshipCount ; i < settingsParams.battleshipCount + settingsParams.cruiserCount ; i ++ ) { boatSizes[i] = Ship.CRUISER_SIZE; }
        for(int i = settingsParams.battleshipCount + settingsParams.cruiserCount ; i < settingsParams.battleshipCount + settingsParams.cruiserCount + settingsParams.destroyerCount ; i ++ ) { boatSizes[i] = Ship.DESTROYER_SIZE; }
    }
    public void paint(Graphics g) {
        ImageIcon icon = new ImageIcon("src/images/sea.jpeg");
        Image image = icon.getImage();
        if(leftTopCornerX == 0 && leftTopCornerY == 0){
            g.drawImage(image,0,0,getWidth(), (getHeight() * 2 + 59), null);
        }
        for(Ship ship : ships) {
            if(shouldShowShips || ship.isDestroyed()) {
                ship.paint(g);
            }
        }
        drawFields(g);
        drawGrid(g);
    }
    public void setShouldShowShips(boolean shouldShowShips) {
        this.shouldShowShips = shouldShowShips;
    }
    public void reset() {
        for(int x = 0; x < boardWidth; x++) {
            for(int y = 0; y < boardWidth; y++) {
                fields[x][y].reset();
            }
        }
        ships.clear();
        shouldShowShips = false;
        allShipsDestroyed = false;
    }
    public boolean markPosition(Placement posToMark) {
        fields[posToMark.x][posToMark.y].mark();

        allShipsDestroyed = true;
        for(Ship ship : ships) {
            if(!ship.isDestroyed()) {
                allShipsDestroyed = false;
                break;
            }
        }
        return fields[posToMark.x][posToMark.y].isShip();
    }
    public boolean areAllShipsDestroyed() {
        return allShipsDestroyed;
    }
    public boolean isPositionMarked(Placement posToTest) {
        return fields[posToTest.x][posToTest.y].isMarked();
    }
    public Field getMarkerAtPosition(Placement posToSelect) {
        return fields[posToSelect.x][posToSelect.y];
    }
    public Placement getPositionInGrid(int mouseX, int mouseY) {
        if(!isPositionInside(new Placement(mouseX,mouseY))) return new Placement(-1,-1);

        return new Placement((mouseX - leftTopCornerX)/FIELD_SIZE, (mouseY - leftTopCornerY)/FIELD_SIZE);
    }
    public Placement getPosition() {
        return placement;
    }
    public boolean canPlaceShipAt(int x, int y, int segments, boolean sideways) {
        if(x < 0 || y < 0) return false;
        if(sideways) {
            if(y > boardWidth || x + segments > boardWidth) return false;
            for(int i = 0; i < segments; i++) {
                if(fields[x+i][y].isShip()) return false;
            }
        } else {
            if(y + segments > boardWidth || x > boardWidth) return false;
            for(int i = 0; i < segments; i++) {
                if(fields[x][y+i].isShip()) return false;
            }
        }
        return true;
    }
    private void drawGrid(Graphics g) {
        g.setColor(Color.BLACK);
        for(int i = 0; i <= boardWidth; i++) { g.drawLine(leftTopCornerX + i * FIELD_SIZE, leftTopCornerY + height, leftTopCornerX + i * FIELD_SIZE, leftTopCornerY); }
        for(int i = 0; i <= boardWidth; i++) { g.drawLine(leftTopCornerX+width, leftTopCornerY + i * FIELD_SIZE, leftTopCornerX , leftTopCornerY+i * FIELD_SIZE); }
    }

    private void drawFields(Graphics g) {
        for(int x = 0; x < boardWidth; x++) {
            for(int y = 0; y < boardWidth; y++) {
                fields[x][y].paint(g);
            }
        }
    }

    public boolean isPositionInside(Placement target) { return target.x >= placement.x && target.y >= placement.y && target.x < placement.x + width && target.y < placement.y + height; }

    private void createMarkerGrid() {
        for(int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardWidth; j++) {
                fields[i][j] = new Field(leftTopCornerX + i *FIELD_SIZE,  leftTopCornerY +j*FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);
            }
        }
    }

    public void populateShips() {
        ships.clear();
        for(int i = 0; i < boatSizes.length; i++) {
            boolean sideways = random.nextBoolean();
            int gridX,gridY;
            do {
                gridX = random.nextInt(sideways?boardWidth-boatSizes[i]:boardWidth);
                gridY = random.nextInt(sideways?boardWidth:boardWidth-boatSizes[i]);
            } while(!canPlaceShipAt(gridX,gridY,boatSizes[i],sideways));
            placeShip(gridX, gridY, boatSizes[i], sideways, i);
        }
    }

    public void placeShip(Ship ship, int gridX, int gridY, int shipBeingPlacedIndex) {
        ships.add(ship);
        if(ship.isTurned()) {
            for(int x = 0; x < ship.getSegments(); x++) {
                fields[gridX+x][gridY].setAsShip(ships.get(ships.size()-1), points[shipBeingPlacedIndex]);
            }
        } else {
            for(int y = 0; y < ship.getSegments(); y++) {
                fields[gridX][gridY+y].setAsShip(ships.get(ships.size()-1), points[shipBeingPlacedIndex]);
            }
        }
    }

    private void placeShip(int gridX, int gridY, int segments, boolean sideways, int index) {
        placeShip(new Ship(new Placement(gridX, gridY),
                new Placement(leftTopCornerX+gridX*FIELD_SIZE, leftTopCornerY+gridY*FIELD_SIZE),
                segments, sideways), gridX, gridY, index);
    }

    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

    public int getPoints(int x,int y){
        int sum = point + fields[x][y].value;
        point = sum;
        return sum;
    }
}
