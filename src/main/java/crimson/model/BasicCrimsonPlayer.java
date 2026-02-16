package crimson.model;

import java.util.ArrayList;
import java.util.List;

/**
 * The basic player for the Storm of Crimson game.
 * This player holds the hand, the cards that hand
 * is created from, the player color, and the hand
 * size. It contains methods to get the hand, get
 * the color, and remove a card from the hand.
 */
public class BasicCrimsonPlayer implements CrimsonPlayer {

  List<CrimsonCard> hand;
  int handSize;
  List<CrimsonCard> cards;
  Color color;

  /**
   * The basic constructor for the player. Takes
   * in variables, then sets the global variables
   * to the passed variables.
   *
   * @param cards The list of cards being used for
   *              the player
   * @param handSize The size of the player's hand
   * @param color The color of the player
   */
  public BasicCrimsonPlayer(List<CrimsonCard> cards, int handSize, Color color) {
    this.cards = cards;
    this.handSize = handSize;
    List<CrimsonCard> tempHand = cards.subList(0, handSize);
    this.hand = new ArrayList<>();
    for (CrimsonCard card : tempHand) {
      this.hand.add(new BasicCrimsonCard(card.getName(), card.getPower(), card.getCost(),
          card.getInfluence(), card.getColor()));
    }
    this.color = color;
  }

  @Override
  public List<CrimsonCard> getHand() {
    return hand;
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public void removeCardFromHand(int index) {
    hand.remove(index);
    cards.remove(index);
    makeFullHand();
  }

  /**
   * Checks to see if the hand has all the needed
   * cards. If it doesn't, checks if it is possible
   * to add more cards to the hand. If it is, the
   * method loops until the hand size matches the
   * handSize variable.
   */
  private void makeFullHand() {
    if (handSize < cards.size() + hand.size()) {
      int size = hand.size();
      for (int card = size; card < handSize; card++) {
        if (card < cards.size()) {
          System.out.printf("Replacing with %s\n", cards.get(card).getName());
          hand.add(cards.get(card));
        } else {
          System.out.println("No cards left to replace!");
        }
      }
    }
  }
}
