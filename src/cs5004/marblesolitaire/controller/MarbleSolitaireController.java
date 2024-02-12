package cs5004.marblesolitaire.controller;


/**
 * an interface for the marble solitaire controller, where we take input from the user.
 * and feed it to the output.
 */
public interface MarbleSolitaireController {

  void playGame() throws IllegalStateException;
}
