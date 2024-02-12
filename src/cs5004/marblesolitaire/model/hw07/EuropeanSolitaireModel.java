package cs5004.marblesolitaire.model.hw07;

import java.util.ArrayList;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;
import cs5004.marblesolitaire.model.hw05.Slot;

/**
 * an abstract class for the European and the English boards.
 */
public class EuropeanSolitaireModel
        implements MarbleSolitaireModel {
  private ArrayList<ArrayList<Slot>> board = new ArrayList<>();
  private int armThickness;
  private int size;
  private int numOfMoves;

  public EuropeanSolitaireModel() {
    this(3, 3, 3);
  }

  /**
   * constructor that takes in col and row as parameters.
   */
  public EuropeanSolitaireModel(int row, int col) throws IllegalArgumentException {
    this(3, row, col);
  }

  /**
   * constructor that takes in the arm thickness as its parameter.
   */
  public EuropeanSolitaireModel(int armThickness) {
    this(armThickness, ((armThickness * 3) / 2) - 1, ((armThickness * 3) / 2) - 1);

    if (armThickness % 2 != 1) {
      throw new IllegalArgumentException("the arm thickness needs to be a positive odd number");
    }

  }

  /**
   * constructor that takes in arm thickness, row and column as its parameter.
   */

  public EuropeanSolitaireModel(int armThickness, int row, int col) {
    size = 3 * armThickness - 2;
    this.numOfMoves = 0;
    this.armThickness = armThickness;

    if (armThickness % 2 != 1) {
      throw new IllegalArgumentException(
              "The arm thickness needs to be a positive odd number.");
    } else if (!withinBound(row, col)) {
      throw new IllegalArgumentException("outside the bound.");
    } else {
      for (int i = 0; i < 3 * armThickness - 2; i++) {
        ArrayList<Slot> temp = new ArrayList<>();
        for (int j = 0; j < 3 * armThickness - 2; j++) {
          if (withinBound(i, j)) {
            temp.add(Slot.MARBLE);
          } else {
            temp.add(Slot.INVALID);
          }
        }

        board.add(temp);

      }
      board.get(row).set(col, Slot.EMPTY);
    }
  }
  /**
   * a helper function to decide if a marble if within bound.
   * by within bound we mean it's either a valid or an empty slot.
   */

  public boolean withinBound(int row, int col) {
    size = armThickness * 3 - 2;
    return row >= 0 && col >= 0 && col <= size - 1 && row <= size - 1
            && !(row < armThickness - 1 && col < (size / 2)
            - ((this.armThickness + (2 * row)) / 2))
            && !(row < this.armThickness - 1 && col > (size / 2)
            + ((this.armThickness + (2 * row)) / 2))
            && !(row > (size / 2) + ((this.armThickness + (2 * col)) / 2)
            && col < armThickness - 1)
            && !((row > armThickness * 2 - 2 && col > (size / 2)
            + ((this.armThickness + ((size - row - 1) * 2)) / 2)));
  }

  /**
   *a helper method to add marbles on the board.
   */
  public void addMarble(int row, int col) throws IllegalArgumentException {
    if (board.get(row).get(col) != Slot.EMPTY) {
      throw new IllegalArgumentException("Please add a marble at an empty spot");
    }
    board.get(row).set(col, Slot.MARBLE);
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    if (canMove(fromRow, fromCol, toRow, toCol)) {
      board.get(fromRow).set(fromCol, Slot.EMPTY);
      board.get((fromRow + toRow) / 2).set(((fromCol + toCol) / 2), Slot.EMPTY);
      this.addMarble(toRow, toCol);
      this.numOfMoves += 1;
    }
    else {
      throw new IllegalArgumentException("Invalid move.");
    }

  }

  /**
   * a function to test if a move can be made.
   */
  public boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
    int rowHopped = (fromRow + toRow) / 2;
    int colHopped = (fromCol + toCol) / 2;
    int boardMax = this.getBoardSize();

    return (withinBound(fromRow, fromCol) && withinBound(toRow,toCol)
            && (board.get(fromRow).get(fromCol) == Slot.MARBLE)
            && ((Math.abs(fromRow - toRow) == 2 && fromCol - toCol == 0)
            || (Math.abs(fromCol - toCol) == 2 && fromRow - toRow == 0))
            && (board.get(rowHopped).get(colHopped) == Slot.MARBLE)
            && (board.get(toRow).get(toCol) == Slot.EMPTY));
  }

  /**
   * a function to test of a marble position can make a move.
   */
  public boolean canMarbleMove(int row, int col) {
    int size = board.size();

    if (row < 0 || col < 0 || board.get(row).get(col) != Slot.MARBLE) {
      return false;
    }

    return row < (size - 1) && this.canMove(row, col, row + 2, col)
            || row > 1 && this.canMove(row, col, row - 2, col)
            || col < (size - 1) && this.canMove(row, col, row, col + 2)
            || col > 1 && this.canMove(row, col, row, col - 2);
  }


  /**
   * a function to decide if the game is over.
   */

  public boolean isGameOver() {
    if (this.getScore() == 1) {
      return true;
    }

    int count = board.size();
    for (int i = 0; i < count; i++) {
      for (int j = 0; j < count; j++) {

        if (board.get(i).get(j).equals(Slot.MARBLE) && this.canMarbleMove(i, j)) {
          return false;
        }
      }
    }
    return true;
  }



  @Override
  public int getBoardSize() {
    this.size = this.armThickness * 3 - 2;
    return this.size;
  }

  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (row > size - 1 || row < 0 || col > size - 1 || col < 0) {
      throw new IllegalArgumentException("slot outside the board");
    }
    else {
      if (board.get(row).get(col) == Slot.MARBLE) {
        return SlotState.Marble;
      }
      if (board.get(row).get(col) == Slot.EMPTY) {
        return SlotState.Empty;
      }
      else {
        return SlotState.Invalid;
      }
    }
  }

  @Override
  public int getScore() {
    return (armThickness * (armThickness - 1) * 4 + armThickness * armThickness - 1 - numOfMoves);
  }
}
