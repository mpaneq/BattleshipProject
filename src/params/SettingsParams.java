package params;

public class SettingsParams {

    public int mapWidth;
    public int moves;
    public int battleshipCount;
    public int battleshipPunctation;
    public int cruiserCount;
    public int cruiserPunctation;
    public int destroyerCount;
    public int destroyerPunctation;
    public int difficultyLevel;

    public SettingsParams(int mapWidth, int moves, int battleshipCount, int battleshipPunctation, int cruiserCount, int cruiserPunctation, int destroyerCount, int destroyerPunctation, int difficultyLevel) {
        this.mapWidth = mapWidth;
        this.moves = moves;
        this.battleshipCount = battleshipCount;
        this.battleshipPunctation = battleshipPunctation;
        this.cruiserCount = cruiserCount;
        this.cruiserPunctation = cruiserPunctation;
        this.destroyerCount = destroyerCount;
        this.destroyerPunctation = destroyerPunctation;
        this.difficultyLevel = difficultyLevel;
    }


    public int getmapWidth() {
        return mapWidth;
    }

    public int getMoves() {
        return moves;
    }

    public int getbattleshipCount() {
        return battleshipCount;
    }

    public int getbattleshipPunctation() {
        return battleshipPunctation;
    }

    public int getcruiserCount() {
        return cruiserCount;
    }

    public int getcruiserPunctation() {
        return cruiserPunctation;
    }

    public int getdestroyerCount() {
        return destroyerCount;
    }

    public int getdestroyerPunctation() {
        return destroyerPunctation;
    }

    public int getdifficultyLevel() {
        return difficultyLevel;
    }
}
