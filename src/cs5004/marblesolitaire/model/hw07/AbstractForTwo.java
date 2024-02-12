package cs5004.marblesolitaire.model.hw07;

import java.util.ArrayList;

import cs5004.marblesolitaire.model.hw05.Slot;

/**
 * an abstract class supposedly for the European and the English versions.
 */
public abstract class AbstractForTwo extends BaseAbstract {
  private ArrayList<ArrayList<Slot>> board = new ArrayList<>();
  private int armThickness;
  private int size;
  private int numOfMoves;

  /**
   * constructor no.1 that takes in no parameters.
   */
  public AbstractForTwo() {
    this(3, 3, 3);
  }

  /**
   * constructor that takes in col and row as parameters.
   */
  public AbstractForTwo(int row, int col) throws IllegalArgumentException {
    this(3, row, col);
  }

  /**
   * constructor that takes in the arm thickness as its parameter.
   */
  public AbstractForTwo(int armThickness) {
    this(armThickness, ((armThickness * 3) / 2) - 1, ((armThickness * 3) / 2) - 1);

    if (armThickness % 2 != 1) {
      throw new IllegalArgumentException("the arm thickness needs to be a positive odd number");
    }

  }

  /**
   * constructor that takes in arm thickness, row and column as its parameter.
   */

  public AbstractForTwo(int armThickness, int row, int col) {
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
   * a helper function to see if a slot is within bound.
   */
  protected boolean withinBound(int row, int col) {
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


  public int getBoardSize() {
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
  protected abstract boolean canMove(int fromRow, int fromCol, int toRow, int toCol);

  protected boolean canMarbleMove(int row, int col) {
    int size = board.size();

    if (row < 0 || col < 0 || board.get(row).get(col) != Slot.MARBLE) {
      return false;
    }

    return row < (size - 1) && this.canMove(row, col, row + 2, col)
            || row > 1 && this.canMove(row, col, row - 2, col)
            || col < (size - 1) && this.canMove(row, col, row, col + 2)
            || col > 1 && this.canMove(row, col, row, col - 2);
  }

  public int getScore() {
    return (armThickness * (armThickness - 1) * 4 + armThickness * armThickness - 1 - numOfMoves);
  }


}

