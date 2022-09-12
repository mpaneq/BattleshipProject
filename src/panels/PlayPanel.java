package panels;



import params.*;
import models.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {
    private Board player;
    private Board opponent;
    private SettingsParams settingsParams;
    public enum GameState { shipBeingPlaceds, FiringShots, GameOver }
    private StatePanel statePanel;
    private Computer ComputerController;
    private Ship shipBeingPlaced;
    private Placement tempPlacingPlacement;
    private int shipBeingPlacedIndex;
    private GameState gameState;
    private int movesCounter;

    PlayPanel(SettingsParams settingsParams) {
        this.settingsParams = settingsParams;
        createComponents();
        restart();
    }
    
    public void createComponents(){
        createBoards(settingsParams);
        createStatePanel();
        createListiners();
        createComputerControllers(settingsParams.difficultyLevel);
        createFrame(settingsParams.mapWidth);
    }
    
    public void createComputerControllers(int difficultyLevel){
        if(difficultyLevel == 0) ComputerController = new EasyLevelComputer(player, settingsParams);
        else ComputerController = new HardLevelComputer(player, settingsParams);
    }
    public void createStatePanel(){ this.statePanel = new StatePanel(new Placement(0,opponent.getHeight()+1), opponent.getWidth(),50); }
    
    public void createListiners(){
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void createBoards(SettingsParams settingsParams){
        player = new Board(0,0, settingsParams);
        opponent = new Board(0, player.getHeight() + 50, settingsParams);
    }

    public void createFrame(int mapWidth){
        JFrame frame = new JFrame();
        frame.setSize(Board.FIELD_SIZE * mapWidth, 79 + (Board.FIELD_SIZE * mapWidth * 2) );
        frame.add(this);
        frame.setTitle("Battleship");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addKeyListener(this);
        setBackground(Color.pink);
    }

    public void paint(Graphics g){
        super.paint(g);
        player.paint(g);
        opponent.paint(g);
        if(gameState == GameState.shipBeingPlaceds) { shipBeingPlaced.paint(g); }
        statePanel.paint(g);
    }

    public void restart(){
        {
            this.movesCounter = 1;
            opponent.reset();
            player.reset();
            player.setShouldShowShips(true);
            ComputerController.reset();
            tempPlacingPlacement = new Placement(0,0);
            shipBeingPlaced = new Ship(new Placement(0,0),
                    new Placement(player.getPosition().x,player.getPosition().y),
                    player.boatSizes[0], true);
            shipBeingPlacedIndex = 0;
            updateShipPlacement(tempPlacingPlacement);
            opponent.populateShips();
            statePanel.reset();
            gameState = GameState.shipBeingPlaceds;
        }

    }

    public void handleInput(int keyCode) {
        if(keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        } else if(keyCode == KeyEvent.VK_R) {
            restart();
        } else if(gameState == GameState.shipBeingPlaceds && keyCode == KeyEvent.VK_Z) {
            shipBeingPlaced.toggleSideways();
            updateShipPlacement(tempPlacingPlacement);
        }
        repaint();
    }
    private void tryPlaceShip(Placement mousePlacement) {
        Placement targetPlacement = player.getPositionInGrid(mousePlacement.x, mousePlacement.y);
        updateShipPlacement(targetPlacement);
        if(player.canPlaceShipAt(targetPlacement.x, targetPlacement.y,
                player.boatSizes[shipBeingPlacedIndex],shipBeingPlaced.isTurned())) {
            placeShip(targetPlacement);
        }
    }
    
    private void placeShip(Placement targetPlacement) {
        shipBeingPlaced.setShipPlacementColour(Ship.ShipPlacementColour.Placed);
        player.placeShip(shipBeingPlaced, tempPlacingPlacement.x, tempPlacingPlacement.y, shipBeingPlacedIndex);
        shipBeingPlacedIndex++;
        if(shipBeingPlacedIndex < player.boatSizes.length) {
            shipBeingPlaced = new Ship(
                    new Placement(targetPlacement.x, targetPlacement.y),
                    new Placement(
                            player.getPosition().x + targetPlacement.x * Board.FIELD_SIZE,
                            player.getPosition().y + targetPlacement.y * Board.FIELD_SIZE
                    ),
                    player.boatSizes[shipBeingPlacedIndex],
                    true
            );
            updateShipPlacement(tempPlacingPlacement);
        } else {
            gameState = GameState.FiringShots;
            statePanel.setTopLine("Zatakuj przeciwnika!");
            statePanel.setBottomLine("");
        }
    }

    private void tryFireAtOpponent(Placement mousePlacement) {
        Placement target = getTarget(mousePlacement);
        if(!opponent.isPositionMarked(target)) {
            doPlayerTurn(target);
            if(isOpponentTurn()) { doOpponentTurns(); }
        }
        movesCounter++;
    }
    private Placement getTarget(Placement mousePlacement){
        return opponent.getPositionInGrid(mousePlacement.x, mousePlacement.y);
    }

    private void doOpponentTurns(){
        for(int i = 0 ; i < settingsParams.getMoves() ; i++) { doComputerTurn(); }

    }
    private boolean isOpponentTurn() {
        return (!opponent.areAllShipsDestroyed() && (movesCounter % settingsParams.getMoves()== 0));
    }

    private void doPlayerTurn(Placement targetPlacement) {
        opponent.markPosition(targetPlacement);
        statePanel.setTopLine("Gracz: " + opponent.getPoints(targetPlacement.x, targetPlacement.y) + " pkt");
        if(opponent.areAllShipsDestroyed()) {
            gameState = GameState.GameOver;
            statePanel.showGameOver(true);
        }
    }

    private void doComputerTurn() {
        Placement ComputerMove = ComputerController.getMove();
        player.markPosition(ComputerMove);
        statePanel.setBottomLine("Komputer: " + player.getPoints(ComputerMove.x, ComputerMove.y) + " pkt");
        if(player.areAllShipsDestroyed()) {
            gameState = GameState.GameOver;
            statePanel.showGameOver(false);
        }
    }

    private void tryMoveShipBeingPlaced(Placement mousePlacement) {
        if(player.isPositionInside(mousePlacement)) {
            Placement targetPos = player.getPositionInGrid(mousePlacement.x, mousePlacement.y);
            updateShipPlacement(targetPos);
        }
    }

    private void updateShipPlacement(Placement targetPos) {
        if(shipBeingPlaced.isTurned()) {
            targetPos.x = Math.min(targetPos.x, settingsParams.mapWidth - player.boatSizes[shipBeingPlacedIndex]);
        } else {
            targetPos.y = Math.min(targetPos.y, settingsParams.mapWidth - player.boatSizes[shipBeingPlacedIndex]);
        }
        shipBeingPlaced.setDrawPosition(new Placement(targetPos),
                new Placement(player.getPosition().x + targetPos.x * Board.FIELD_SIZE,
                        player.getPosition().y + targetPos.y * Board.FIELD_SIZE));
        tempPlacingPlacement = targetPos;
        if(player.canPlaceShipAt(tempPlacingPlacement.x, tempPlacingPlacement.y,
                player.boatSizes[shipBeingPlacedIndex],shipBeingPlaced.isTurned())) {
            shipBeingPlaced.setShipPlacementColour(Ship.ShipPlacementColour.Valid);
        } else {
            shipBeingPlaced.setShipPlacementColour(Ship.ShipPlacementColour.Invalid);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Placement mousePlacement = new Placement(e.getX(), e.getY());
        if(gameState == GameState.shipBeingPlaceds && player.isPositionInside(mousePlacement)) {
            tryPlaceShip(mousePlacement);
        } else if(gameState == GameState.FiringShots && opponent.isPositionInside(mousePlacement)) {
            tryFireAtOpponent(mousePlacement);
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(gameState != GameState.shipBeingPlaceds) return;
        tryMoveShipBeingPlaced(new Placement(e.getX(), e.getY()));
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) { handleInput(e.getKeyCode());}
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
