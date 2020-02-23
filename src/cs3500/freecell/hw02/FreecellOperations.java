package cs3500.freecell.hw02;

import java.util.List;

/**
 * This is the interface of the Freecell model. It is parameterized over the
 * card type, i.e. when you implement it, you can substitute K with your
 * implementation of a card.
 */
public interface FreecellOperations<K> {

  /**
   * Return a valid and complete deck of cards for a game of Freecell. There is
   * no restriction imposed on the ordering of these cards in the deck. An
   * invalid deck is defined as a deck that has one or more of these flaws: <ul>
   * <li>It does not have 52 cards</li> <li>It has duplicate cards</li> <li>It
   * has at least one invalid card (invalid suit or invalid number) </li> </ul>
   *
   * @return the deck of cards as a list
   */
  List<K> getDeck();

  /**
   * Deal a new game of freecell with the given deck, with or without shuffling
   * it first. This method first verifies that the deck is valid. It deals the
   * deck among the cascade piles in roundrobin fashion. Thus if there are 4
   * cascade piles, the 1st pile will get cards 0, 4, 8, ..., the 2nd pile will
   * get cards 1, 5, 9, ..., the 3rd pile will get cards 2, 6, 10, ... and the
   * 4th pile will get cards 3, 7, 11, .... Depending on the number of cascade
   * piles, they may have a different number of cards
   *
   * @param numCascadePiles number of cascade piles
   * @param numOpenPiles    number of open piles
   * @param deck            the deck to be dealt
   * @param shuffle         if true, shuffle the deck else deal the deck as-is
   * @throws IllegalArgumentException if the deck is invalid
   */
  void startGame(List<K> deck, int numCascadePiles, int numOpenPiles, boolean shuffle);

  /**
   * Move a card from the given source pile to the given destination pile, if
   * the move is valid.
   *
   * @param source         the type of the source pile see {@link PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source
   *                       pile, starting at 0
   * @param destination    the type of the destination pile (see {@link PileType})
   * @param destPileNumber the pile number of the given type, starting at 0
   * @throws IllegalStateException if the game is not started
   * @throws IllegalArgumentException if the move is not possible
   */
  void move(PileType source, int pileNumber, int cardIndex, PileType destination,
            int destPileNumber);

  /**
   * Signal if the game is over or not.
   *
   * @return true if game is over, false otherwise
   */
  boolean isGameOver();

  /**
   * Return the card at the given index in the specified pile.
   *
   * @param pile the type of the pile
   * @param pileNumber the pile number of the given type, starting from 0
   * @param cardIndex the index of the card, starting from 0
   * @return the card
   * @throws IllegalStateException if the game is not started
   * @throws IllegalArgumentException if there is no such card
   */
  K getCard(PileType pile, int pileNumber, int cardIndex);

  /**
   * Return the present state of the game as a string. The string is formatted
   * as follows:
   * <pre>
   * F1:[b]f11,[b]f12,[b],...,[b]f1n1[n] (Cards in foundation pile 1 in order)
   * F2:[b]f21,[b]f22,[b],...,[b]f2n2[n] (Cards in foundation pile 2 in order)
   * ...
   * Fm:[b]fm1,[b]fm2,[b],...,[b]fmnm[n] (Cards in foundation pile m in
   * order)
   * O1:[b]o11[n] (Cards in open pile 1)
   * O2:[b]o21[n] (Cards in open pile 2)
   * ...
   * Ok:[b]ok1[n] (Cards in open pile k)
   * C1:[b]c11,[b]c12,[b]...,[b]c1p1[n] (Cards in cascade pile 1 in order)
   * C2:[b]c21,[b]c22,[b]...,[b]c2p2[n] (Cards in cascade pile 2 in order)
   * ...
   * Cs:[b]cs1,[b]cs2,[b]...,[b]csps (Cards in cascade pile s in order)
   *
   * where [b] is a single blankspace, [n] is newline. Note that there is no
   * newline on the last line
   * </pre>
   *
   * @return the formatted string as above, or empty string if the game is not started
   */
  String getGameState();
}
