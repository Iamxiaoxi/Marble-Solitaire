import org.junit.Assert;
import org.junit.Test;

import cs5004.marblesolitaire.model.hw05.EnglishSolitaireModel;
import cs5004.marblesolitaire.model.hw05.MarbleSolitaireModelState;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * a test class for the marble solitaire model.
 */
public class EnglishSolitaireModelTest {
  @Test
  public void testBoard() {
    MarbleSolitaireModelState model = new EnglishSolitaireModel();
    MarbleSolitaireModelState model2 = new EnglishSolitaireModel(5);
    MarbleSolitaireModelState model3 = new EnglishSolitaireModel(2,2);
    MarbleSolitaireModelState model4 = new EnglishSolitaireModel(3,2,3);
    assertEquals(model.getSlotAt(3,3), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(model.getSlotAt(3,4), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(model2.getSlotAt(6,6), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(model2.getSlotAt(2,2), MarbleSolitaireModelState.SlotState.Invalid);
    assertEquals(model3.getSlotAt(2,2), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(model4.getSlotAt(2,3), MarbleSolitaireModelState.SlotState.Empty);

  }



  /**
   * testing exception case for making the board.
   */
  @Test
  public void testExceptions() {
    try {
      MarbleSolitaireModelState model1 = new EnglishSolitaireModel(0,2);
    }
    catch (Exception e) {
      Assert.fail("outside the bound." + e);
    }

    try {
      MarbleSolitaireModelState model2 = new EnglishSolitaireModel(5,10,2);
    }
    catch (Exception e) {
      Assert.fail("outside the bound." + e);
    }

    try {
      MarbleSolitaireModelState model3 = new EnglishSolitaireModel(4);
    }
    catch (Exception e) {
      Assert.fail("The arm thickness needs to be a positive odd number." + e);
    }

    try {
      MarbleSolitaireModelState model4 = new EnglishSolitaireModel(-4,-5);
    }
    catch (Exception e) {
      Assert.fail("outside the bound." + e);
    }

    try {
      MarbleSolitaireModelState model4 = new EnglishSolitaireModel(10,10,10);
    }
    catch (Exception e) {
      Assert.fail("The arm thickness needs to be a positive odd number." + e);
    }

  }

  /**
   * another way to test the board constructor.
   */


  @Test
  public void testToString() {
    MarbleSolitaireModelState model = new EnglishSolitaireModel();
    assertEquals(model.toString(),"  OOO\n"
            + "  OOO\n"
            + "OOOOOOO\n"
            + "OOOOOOO\n"
            + "OOOOOOO\n"
            + "  OOO\n"
            + "  OOO"
    );


  }

  /**
   * a test method for valid moves, in different scenarios.
   */

  @Test
  public void testMove() {
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    model.move(1,3,3,3);
    assertEquals(model.getSlotAt(3,3), MarbleSolitaireModelState.SlotState.Marble);
    assertEquals(model.getSlotAt(1,3), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(model.getSlotAt(2,3), MarbleSolitaireModelState.SlotState.Empty);
    model.move(4,3, 2,3 );
    assertEquals(model.getSlotAt(4,3), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(model.getSlotAt(2,3), MarbleSolitaireModelState.SlotState.Marble);
    model.move(4,1,4,3);
    assertEquals(model.getSlotAt(4,1), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(model.getSlotAt(4,2), MarbleSolitaireModelState.SlotState.Empty);
    model.move(3,5,3,3);
    assertEquals(model.getSlotAt(3,5), MarbleSolitaireModelState.SlotState.Empty);
    assertEquals(model.getSlotAt(3,3), MarbleSolitaireModelState.SlotState.Marble);
  }

  /**
   * a test method to test invalid moves in different scenarios.
   */
  @Test
  public void testInvalidMove() {
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    try {
      model.move(-1, 3, 3, 3);

    } catch (Exception e) {
      Assert.fail("invalid move." + e);
    }

    try {
      model.move(1, 3, 33, 3);

    } catch (Exception e) {
      Assert.fail("invalid move." + e);
    }
    try {
      model.move(3,3,5,3);

    }
    catch (Exception e) {
      Assert.fail("invalid move." + e);
    }
    try {
      model.move(1,2,3,2);

    }
    catch (Exception e) {
      Assert.fail("invalid move." + e);
    }

    try {
      model.move(2,3,4,3);

    }
    catch (Exception e) {
      Assert.fail("invalid move." + e);
    }
    try {
      model.move(2,3,5,3);

    }
    catch (Exception e) {
      Assert.fail("invalid move." + e);
    }
    try {
      model.move(1,2,2,3);

    }
    catch (Exception e) {
      Assert.fail("invalid move." + e);
    }

  }

  /**
   * a test method for the get score method.
   */

  @Test
  public void testGetScore() {
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    model.move(1,3,3,3);
    model.move(4,3, 2,3 );
    assertEquals(model.getScore(),30);

  }

  /**
   * a test method for the game over method.
   */
  @Test
  public void testIsGameOver() {
    EnglishSolitaireModel model = new EnglishSolitaireModel();
    model.move(1,3,3,3);
    assertFalse(model.isGameOver());

  }
}
