package cs5004.marblesolitaire.model.hw05;

import java.util.ArrayList;


/**
 * a class to implement the MarbleSolitaireModel interface.
 */
public class EnglishSolitaireModel implements MarbleSolitaireModel {
  private ArrayList<ArrayList<Slot>> board = new ArrayList<>();
  private int armThickness;
  private int size;
  private int numOfMoves;


  /**
   *  constructor no.1 that takes in no parameters.
   */
  public EnglishSolitaireModel() {
    this(3,3,3);
  }

  /**
   * constructor that takes in col and row as parameters.
   */
  public EnglishSolitaireModel(int row, int col) throws IllegalArgumentException {
    this(3,row, col);
  }

  /**
   * constructor that takes in the arm thickness as its parameter.
   */
  public EnglishSolitaireModel(int armThickness) {
    this(armThickness, ((armThickness * 3 ) / 2) - 1, ((armThickness * 3) / 2) - 1);

    if (armThickness % 2 != 1) {
      throw new IllegalArgumentException("the arm thickness needs to be a positive odd number");
    }

  }

  /**
   * constructor that takes in arm thickness, row and column as its parameter.
   */

  public EnglishSolitaireModel(int armThickness, int row, int col) {
    size = 3 * armThickness - 2;
    this.numOfMoves = 0;
    this.armThickness = armThickness;

    if (armThickness % 2 != 1) {
      throw new IllegalArgumentException(
              "The arm thickness needs to be a positive odd number.");
    } else if (!withinBound(row,col)) {
      throw new IllegalArgumentException("outside the bound.");
    }
    else {
      for (int i = 0; i < 3 * armThickness - 2; i++) {
        ArrayList<Slot> temp = new ArrayList<>();
        for (int j = 0; j < 3 * armThickness - 2; j++) {
          if ((armThickness - 2 < i && i < 2 * armThickness - 1)
                  || (armThickness - 2 < j && j < 2 * armThickness - 1)) {
            temp.add(Slot.MARBLE);
          } else {
            temp.add(Slot.INVALID);
          }
        }

        board.add(temp);

      }
      board.get(row).set(col,Slot.EMPTY);
    }
  }

  /**
   *a method for moving the marbles. a marble can only move iff.
   * the "from" slot has a marble, the "to" slot is empty.
   * there is exactly one slot in between and it's empty.
   *
   * @param fromRow the row number of the position to be moved from
   *                (starts at 0).
   * @param fromCol the column number of the position to be moved from
   *                (starts at 0).
   * @param toRow   the row number of the position to be moved to
   *                (starts at 0).
   * @param toCol   the column number of the position to be moved to
   *                (starts at 0).
   */
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
   * a helper function for the move function.
   */
  public void addMarble(int row, int col) throws IllegalArgumentException {
    if (board.get(row).get(col) != Slot.EMPTY) {
      throw new IllegalArgumentException("Please add a marble at an empty spot");
    }
    board.get(row).set(col, Slot.MARBLE);
  }


  /**
   * a helper function to see if a slot is within bound.
   */
  public boolean withinBound(int row, int col) {
    size = armThickness * 3 - 2;
    return row >= 0 && col >= 0 && col <= size - 1 && row <= size - 1
            && (row >= armThickness - 1 || col >= armThickness - 1)
            && (row >= armThickness - 1 || col <= armThickness * 2 - 2)
            && (row <= armThickness * 2 - 2 || col >= armThickness - 1)
            && (row <= armThickness * 2 - 2 || col <= armThickness * 2 - 2);
  }

  /**
   * a helper function for isGameOver().
   * @param fromRow from row.
   * @param fromCol from column.
   * @param toRow to row.
   * @param toCol to column.
   * @return a boolean.
   */

  private boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
    int rowHopped = (fromRow + toRow) / 2;
    int colHopped = (fromCol + toCol) / 2;
    int boardMax = this.getBoardSize();

    return ((fromRow >= 0 && fromCol >= 0) && (toRow >= 0 && toCol >= 0))
            && ((fromRow < boardMax && fromCol < boardMax)
            && (toRow < boardMax && toCol < boardMax))
            && (board.get(fromRow).get(fromCol) == Slot.MARBLE)
            && ((Math.abs(fromRow - toRow) == 2 && fromCol - toCol == 0)
            || (Math.abs(fromCol - toCol) == 2 && fromRow - toRow == 0))
            && (board.get(rowHopped).get(colHopped) == Slot.MARBLE)
            && (board.get(toRow).get(toCol) == Slot.EMPTY);

  }


  /**
   * a method to decide if there are more moves to make.
   * @return true if there are more moves to make; false if it's game over.
   */
  @Override
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


  /**
   * a helper function for isgameover().
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
   * a function that returns the size of the board.
   */
  @Override

  public int getBoardSize() {
    this.size = this.armThickness * 3 - 2;
    return this.size;
  }

  /**
   * a method to determine the slot state of a slot.
   * @param row the row of the position sought, starting at 0
   * @param col the column of the position sought, starting at 0
   * @return the slot slate which is an enum.
   */
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

  /**
   * a method that gets the score of the current game.
   * @return the score.
   */
  @Override
  public int getScore() {
    return (armThickness * (armThickness - 1) * 4 + armThickness * armThickness - 1 - numOfMoves);
  }

}


