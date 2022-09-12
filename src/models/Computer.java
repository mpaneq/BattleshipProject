package models;

import models.Board;
import params.SettingsParams;

import java.util.ArrayList;
import java.util.List;

public class Computer {
    Board playerBoard;
    List <Placement> movesToMake;
    SettingsParams settingsParams;
    public Computer(Board playerBoard, SettingsParams settingsParams) {
        this.playerBoard = playerBoard;
        this.settingsParams = settingsParams;
        createMovesToMakeList();
    }
    public Placement getMove() { return new Placement(0,0); }
    public void reset() { createMovesToMakeList(); }
    void createMovesToMakeList() {
        movesToMake = new ArrayList<>();
        for(int x = 0; x < settingsParams.getmapWidth(); x++) {
            for(int y = 0; y < settingsParams.getmapWidth(); y++) { movesToMake.add(new Placement(x,y)); }
        }
    }
}
