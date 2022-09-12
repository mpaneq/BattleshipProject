package models;

import models.Board;
import models.Computer;
import models.Field;
import params.SettingsParams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class HardLevelComputer extends Computer {

    private List<Placement> shipHits;

    public HardLevelComputer(Board playerBoard, SettingsParams settingsParams) {
        super(playerBoard, settingsParams);
        shipHits = new ArrayList<>();
        Collections.shuffle(movesToMake);
    }
    @Override
    public void reset() {
        super.reset();
        shipHits.clear();
        Collections.shuffle(movesToMake);
    }
    @Override
    public Placement getMove() {
        Placement selectedMove;
        if(shipHits.size() > 0) {
            selectedMove = getSmarterAttack();
        } else {
            selectedMove = findMostOpenPosition();
        }
        updateShipHits(selectedMove);
        movesToMake.remove(selectedMove);
        return selectedMove;
    }

    private Placement getSmarterAttack() {
        List<Placement> suggestedMoves = getAdjacentSmartMoves();
        for(Placement possibleOptimalMove : suggestedMoves) {
            if(atLeastTwoHitsInDirection(possibleOptimalMove, Placement.LEFT)) return possibleOptimalMove;
            if(atLeastTwoHitsInDirection(possibleOptimalMove, Placement.RIGHT)) return possibleOptimalMove;
            if(atLeastTwoHitsInDirection(possibleOptimalMove, Placement.DOWN)) return possibleOptimalMove;
            if(atLeastTwoHitsInDirection(possibleOptimalMove, Placement.UP)) return possibleOptimalMove;
        }
        Collections.shuffle(suggestedMoves);
        return  suggestedMoves.get(0);
    }
    private Placement findMostOpenPosition() {
        Placement placement = movesToMake.get(0);;
        int highestNotAttacked = -1;
        for(int i = 0; i < movesToMake.size(); i++) {
            int testCount = getAdjacentNotAttackedCount(movesToMake.get(i));
            if(testCount == 4) {
                return movesToMake.get(i);
            } else if(testCount > highestNotAttacked) {
                highestNotAttacked = testCount;
                placement = movesToMake.get(i);
            }
        }
        return placement;
    }
    private int getAdjacentNotAttackedCount(Placement placement) {
        List<Placement> adjacentCells = getAdjacentCells(placement);
        int notAttackedCount = 0;
        for(Placement adjacentCell : adjacentCells) {
            if(!playerBoard.getMarkerAtPosition(adjacentCell).isMarked()) {
                notAttackedCount++;
            }
        }
        return notAttackedCount;
    }
    private boolean atLeastTwoHitsInDirection(Placement start, Placement direction) {
        Placement testPlacement = new Placement(start);
        testPlacement.add(direction);
        if(!shipHits.contains(testPlacement)) return false;
        testPlacement.add(direction);
        if(!shipHits.contains(testPlacement)) return false;
        return true;
    }
    private List<Placement> getAdjacentSmartMoves() {
        List<Placement> result = new ArrayList<>();
        for(Placement shipHitPos : shipHits) {
            List<Placement> adjacentPlacements = getAdjacentCells(shipHitPos);
            for(Placement adjacentPlacement : adjacentPlacements) {
                if(!result.contains(adjacentPlacement) && movesToMake.contains(adjacentPlacement)) {
                    result.add(adjacentPlacement);
                }
            }
        }
        return result;
    }
    private void printPositionList(String messagePrefix, List<Placement> data) {
        String result = "[";
        for(int i = 0; i < data.size(); i++) {
            result += data.get(i);
            if(i != data.size()-1) {
                result += ", ";
            }
        }
        result += "]";
        System.out.println(messagePrefix + " " + result);
    }
    private List<Placement> getAdjacentCells(Placement placement) {
        List<Placement> result = new ArrayList<>();
        if(placement.x != 0) {
            Placement left = new Placement(placement);
            left.add(Placement.LEFT);
            result.add(left);
        }
        if(placement.x != settingsParams.getmapWidth()-1) {
            Placement right = new Placement(placement);
            right.add(Placement.RIGHT);
            result.add(right);
        }
        if(placement.y != 0) {
            Placement up = new Placement(placement);
            up.add(Placement.UP);
            result.add(up);
        }
        if(placement.y != settingsParams.getmapWidth()-1) {
            Placement down = new Placement(placement);
            down.add(Placement.DOWN);
            result.add(down);
        }
        return result;
    }
    private void updateShipHits(Placement testPlacement) {
        Field marker = playerBoard.getMarkerAtPosition(testPlacement);
        if(marker.isShip()) {
            shipHits.add(testPlacement);
            List<Placement> allPositionsOfLastShip = marker.getAssociatedShip().getOccupiedCoordinates();
            boolean hitAllOfShip = containsAllPositions(allPositionsOfLastShip, shipHits);
            if(hitAllOfShip) {
                for(Placement shipPlacement : allPositionsOfLastShip) {
                    for(int i = 0; i < shipHits.size(); i++) {
                        if(shipHits.get(i).equals(shipPlacement)) {
                            shipHits.remove(i);
                            break;
                        }
                    }
                }
            }
        }
    }
    private boolean containsAllPositions(List<Placement> positionsToSearches, List<Placement> listToSearchIn) {
        for(Placement searchPlacement : positionsToSearches) {
            boolean found = false;
            for(Placement searchInPlacement : listToSearchIn) {
                if(searchInPlacement.equals(searchPlacement)) {
                    found = true;
                    break;
                }
            }
            if(!found) return false;
        }
        return true;
    }
}
