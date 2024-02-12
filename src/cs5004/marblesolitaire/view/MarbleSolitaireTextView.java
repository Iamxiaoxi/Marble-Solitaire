package cs5004.marblesolitaire.view;

import java.io.IOException;
import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState;


/**
 * a class for the view of the game.
 */
public class MarbleSolitaireTextView implements MarbleSolitaireView {
  private MarbleSolitaireModelState modelState;
  private Appendable ap;


  /**
   * a constructor for this class.
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState x) {
    this(x, System.out);
  }


  /**
   *   a constructor that takes in the model state and output as its arguments.
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState x, Appendable ap) {
    if (x == null || ap == null) {
      throw new IllegalArgumentException("arguments cannot be null.");
    }
    this.modelState = x;
    this.ap = ap;
  }



  @Override
 public  String toString() {
    StringBuilder boardState = new StringBuilder();

    // Size of board.
    int maxSize = this.modelState.getBoardSize();
    int armThickness = (maxSize + 2) / 3;

    // Loops through each spot.
    for (int row = 0; row < maxSize; row++) {
      for (int col = 0; col < maxSize + 1; col++) {

        // Temporary Spot for loop.
        MarbleSolitaireModelState.SlotState tempSlot = modelState.getSlotAt(row,col);

        // For top and bottom parts of board, not appending spaces after.
        if (tempSlot == MarbleSolitaireModelState.SlotState.Invalid && col < armThickness) {
          boardState.append(" ");
        }

        // For arms of board appending the remaining spots.
        if (tempSlot == MarbleSolitaireModelState.SlotState.Marble ) {
          boardState.append("0 ");
        }
        if (tempSlot == MarbleSolitaireModelState.SlotState.Empty) {
          boardState.append("_ ");
        }
        if (col == maxSize) {
          boardState.append("\n");
        }
      }
      // Appends new line for the board.

    }
    return boardState.toString();



  }

  @Override
  public void renderBoard() throws IOException {
    this.ap.append(toString());
  }

  @Override

  public void renderMessage(String message) throws IOException {
    if ( message == null ) {
      throw new IllegalStateException("Message cannot be null.");
    }

    this.ap.append(message);
  }
}
