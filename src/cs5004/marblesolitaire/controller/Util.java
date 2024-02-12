package cs5004.marblesolitaire.controller;

import java.io.IOException;
import java.util.Scanner;

import cs5004.marblesolitaire.view.MarbleSolitaireView;

/**
 * a helper class for the marble solitaire controller class.
 */
public class Util {

  /**
   *   helper function to get valid inputs.
   */
  public static Input getValidInput(Scanner scanner) {
    while (scanner.hasNext()) {
      String input1 = scanner.next();
      if (input1.equalsIgnoreCase("q")) {
        return new Input(0, InputType.QUIT);
      }
      try {
        int num1 = Integer.parseInt(input1);
        return new Input(num1, InputType.VALID);

      } catch (NumberFormatException e) {
        throw new NumberFormatException("invalid input.");
      }
      catch (IllegalArgumentException e) {
        throw new IllegalStateException("enter number.");
      }
    }
    throw new IllegalStateException("run out of input!");
  }

  /**
   *   a method to write to the screen for the users.
   */
  public static void writeToView(String message, MarbleSolitaireView view)
          throws IllegalStateException {
    try {
      view.renderMessage(message);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to write to view");
    }
  }

}
