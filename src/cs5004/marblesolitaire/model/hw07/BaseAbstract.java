package cs5004.marblesolitaire.model.hw07;

import java.util.ArrayList;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;
import cs5004.marblesolitaire.model.hw05.Slot;

/**
 * an abstract class for all three classes, including the function move and isGameOver.
 */
public abstract class BaseAbstract implements MarbleSolitaireModel {
  private ArrayList<ArrayList<Slot>> board = new ArrayList<>();
  private int armThickness;
  private int size;
  private int numOfMoves;




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

  /**
   * a helper function for the move function.
   */
  public void addMarble(int row, int col) throws IllegalArgumentException {
    if (board.get(row).get(col) != Slot.EMPTY) {
      throw new IllegalArgumentException("Please add a marble at an empty spot");
    }
    board.get(row).set(col, Slot.MARBLE);
  }

}
