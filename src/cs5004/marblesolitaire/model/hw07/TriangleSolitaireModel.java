package cs5004.marblesolitaire.model.hw07;

import java.util.ArrayList;

import cs5004.marblesolitaire.model.hw05.Slot;


/**
 * a class to represent the triangle marble solitaire game.
 */
public class TriangleSolitaireModel extends BaseAbstract {

  private ArrayList<ArrayList<Slot>> board = new ArrayList<>();
  private int armThickness;
  private int size;
  private int numOfMoves;

  public TriangleSolitaireModel() {
    this(5, 0, 0);
  }

  /**
   * constructor that takes in col and row as parameters.
   */
  public TriangleSolitaireModel(int row, int col) throws IllegalArgumentException {
    this(5, row, col);
  }

  /**
   * constructor that takes in the arm thickness as its parameter.
   */
  public TriangleSolitaireModel(int armThickness) {
    this(armThickness, 0, 0);

    if (armThickness % 2 != 1) {
      throw new IllegalArgumentException("the arm thickness needs to be a positive odd number");
    }

  }

  /**
   * constructor that takes in arm thickness, row and column as its parameter.
   */

  public TriangleSolitaireModel(int armThickness, int row, int col) {
    size = armThickness;
    this.armThickness = armThickness;

    if (armThickness < 1) {
      throw new IllegalArgumentException(
              "The arm thickness needs to be a positive number.");
    } else if (!withinBound(row, col)) {
      throw new IllegalArgumentException("outside the bound.");
    } else {
      ArrayList<Slot> temp = new ArrayList<>();
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < size; j++) {
          if (i > j) {
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
  public boolean withinBound(int row, int col) {
    size = armThickness;
    return row >= 0 && col >= 0 && col < size && row < size
            && (row >= col);
  }


  @Override
  public int getBoardSize() {
    return this.size;
  }

  @Override
  public SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    if (row > size - 1 || row < 0 || col > size - 1 || col < 0) {
      throw new IllegalArgumentException("slot outside the board");
    } else {
      if (board.get(row).get(col) == Slot.MARBLE) {
        return SlotState.Marble;
      }
      if (board.get(row).get(col) == Slot.EMPTY) {
        return SlotState.Empty;
      } else {
        return SlotState.Invalid;
      }
    }
  }


  @Override
  public int getScore() {
    return (armThickness * (armThickness - 1) * 4 + armThickness * armThickness - 1 - numOfMoves);
  }

  @Override
  public boolean canMove(int fromRow, int fromCol, int toRow, int toCol) {
    int rowHopped = (fromRow + toRow) / 2;
    int colHopped = (fromCol + toCol) / 2;
    return withinBound(fromRow, fromCol) && withinBound(toRow, toCol)
            && (board.get(fromRow).get(fromCol) == Slot.MARBLE)
            && ((Math.abs(fromRow - toRow) == 2 && fromCol - toCol == 0)
            || (Math.abs(fromCol - toCol) == 2 && fromRow - toRow == 0))
            || (Math.abs(fromCol - toCol) == 2 && Math.abs(fromRow - toRow) == 2)
            && (board.get(rowHopped).get(colHopped) == Slot.MARBLE)
            && (board.get(toRow).get(toCol) == Slot.EMPTY);

  }

}
