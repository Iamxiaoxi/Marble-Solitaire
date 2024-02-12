package cs5004.marblesolitaire.model.hw05;

/**
 * an enum class to mark the state of a slot.
 */
public enum Slot {
  EMPTY("_"),
  MARBLE("O"),
  INVALID(" ");

  private final String symbol;

  /**
   * a constructor to initiate a slot.
   */
  Slot(String symbol) {
    if (symbol == null) {
      throw new IllegalArgumentException("symbol is null");
    }

    this.symbol = symbol;
  }

  /**
   * a method to get symbol.
   */


  public String getSymbol() {
    return symbol;
  }
}
