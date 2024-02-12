package cs5004.marblesolitaire.controller;

/**
 * a class for the input for the marblesolitaire controller.
 */
public class Input implements IInput {
  private final int valueRead;
  private final InputType inputType;

  /**
   *   a constructor that takes in int value and enum inputType.
   */
  public Input(int valueRead, InputType inputType) {
    this.valueRead = valueRead;
    if (inputType == null) {
      throw new IllegalArgumentException("Input type cannot be null.");
    }
    this.inputType = inputType;
  }

  @Override
  public int getInput() {
    return this.valueRead;
  }

  @Override
  public InputType getInputType() {
    return this.inputType;
  }
}
