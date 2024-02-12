package cs5004.marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModel;
import cs5004.marblesolitaire.view.MarbleSolitaireView;

/**
 * a class that implements the marblesolitaire interface.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {
  private Scanner scan;
  private Appendable ap;
  private MarbleSolitaireModel model;
  private MarbleSolitaireView view;

  /**
   *   a constructor that takes in model, view and readable.
   */
  public MarbleSolitaireControllerImpl(MarbleSolitaireModel model,
                                       MarbleSolitaireView view, Readable rd) {
    if (model == null || view == null || rd == null) {
      throw new IllegalArgumentException("model or view or input cannot be null");
    }

    this.model = model;
    this.view = view;
    this.scan = new Scanner(rd);

  }

  @Override
  public void playGame() throws IllegalArgumentException {

    try {
      view.renderBoard();
    } catch (IOException e) {
      throw new IllegalStateException("cannot transmit output");
    }

    String score = "Score: " + model.getScore() + "\n";
    try {
      view.renderMessage(score);
    } catch (IOException e) {
      throw new IllegalArgumentException("cannot transmit output");
    }
    try {
      while (!(model.isGameOver())) {
        Input fromRow = Util.getValidInput(scan);
        if (fromRow.getInputType() == InputType.QUIT) {
          break;
        }
        Input fromColumn = Util.getValidInput(scan);
        if (fromColumn.getInputType() == InputType.QUIT) {
          break;
        }
        Input toRow = Util.getValidInput(scan);
        if (toRow.getInputType() == InputType.QUIT) {
          break;
        }
        Input toColumn = Util.getValidInput(scan);
        if (toColumn.getInputType() == InputType.QUIT) {
          break;
        }

        try {
          model.move(fromRow.getInput(), fromColumn.getInput(),
                toRow.getInput(), toColumn.getInput());
        } catch (IllegalArgumentException e) {
          Util.writeToView("Invalid move. Play again. Try using better values.\n", view);


        }

      }
    }
    catch (NullPointerException e) {
      throw new IllegalArgumentException("Received null model.");
    }

    Util.writeToView("Game over!\n", view);
    try {
      view.renderBoard();
    } catch (IOException e) {
      throw new IllegalArgumentException("cannot transmit message");
    }
    Util.writeToView(score, view);
    return;

  }
}






