package panels;

import models.*;
import models.Rectangle;

import javax.swing.*;
import java.awt.*;

public class StatePanel extends Rectangle {

    private final Font font = new Font("Courier", Font.BOLD, 15);

    private final String shipBeingPlacedLine1 = "Rozmieść swoje statki powyżej";

    private final String shipBeingPlacedLine2 = "Wciśnij Z, aby obrócić statek";

    private final String gameOverLossLine = "Nie tym razem :( ";

    private final String gameOverWinLine = "Wygrana! Gratulacja";

    private final String gameOverBottomLine = "Wciśnij R, aby kontynować";

    private String topLine;

    private String bottomLine;

    public StatePanel(Placement placement, int width, int height) {
        super(placement, width, height);
        reset();
    }

//    public StatePanel(Placement placement, int width, int height) {
//    }

    public void reset() {
        topLine = shipBeingPlacedLine1;
        bottomLine = shipBeingPlacedLine2;
    }

    public void showGameOver(boolean playerWon) {
        topLine = (playerWon) ? gameOverWinLine : gameOverLossLine;
        bottomLine = gameOverBottomLine;
    }

    public void setTopLine(String message) {
        topLine = message;
    }


    public void setBottomLine(String message) {
        bottomLine = message;
    }

    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(placement.x, placement.y, width, height);
        g.setColor(Color.BLACK);
        g.setFont(font);
        int strWidth = g.getFontMetrics().stringWidth(topLine);
        g.drawString(topLine, placement.x+width/2-strWidth/2, placement.y+20);
        strWidth = g.getFontMetrics().stringWidth(bottomLine);
        g.drawString(bottomLine, placement.x+width/2-strWidth/2, placement.y+40);
    }
}
