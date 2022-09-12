package models;

import models.Board;
import models.Computer;
import params.SettingsParams;

import java.util.Collections;
public class EasyLevelComputer extends Computer {
    public EasyLevelComputer(Board playerBoard, SettingsParams settingsParams) {
        super(playerBoard, settingsParams);
        Collections.shuffle(movesToMake);
    }
    @Override
    public void reset() {
        super.reset();
        Collections.shuffle(movesToMake);
    }
    @Override
    public Placement getMove() {
        Placement moveToMake = movesToMake.get(0);
        movesToMake.remove(0);
        return moveToMake;
    }
}
