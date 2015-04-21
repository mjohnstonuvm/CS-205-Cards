/*
 Created by Keegan and Matt
 */
package card;

import java.util.ArrayList;

public class GameData {

    private Deck deck;
    public DiscardPile dp;
    public ArrayList<Hand> hands;
    public final String playerName;
    public final int numOfAI;

    public GameData(Deck deck, DiscardPile dp, ArrayList<Hand> hands, String playerName, int numOfAI) {
        this.deck = deck;
        this.dp = dp;
        this.hands = hands;
        this.playerName = playerName;
        this.numOfAI = numOfAI;
        
    }// End of Constructor

    public Card deckPop() {
        Card popped = deck.pop();

        // If no more cards in deck, use dp as new deck
        if (popped == null) {
            deck = new Deck(dp);
            dp = new DiscardPile();
            dp.push(deck.pop());
            popped = deck.pop();
        }

        return popped;
    }// End of deckPop()

    public void deckPush(Card pushed) {
        deck.push(pushed);
    }// End of deckPush()

}// End of GameData