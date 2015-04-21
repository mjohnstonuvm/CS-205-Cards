package card;

import java.util.Random;

public class Deck {

    protected Card[] deck = new Card[54];
    protected int topOfDeck = 53;
    public final int length = deck.length;

    public Deck() {
        int i = 0;
        // Create normal cards
        for (int j = 0; j < 9; j++) {
            deck[i] = new Card(j);
            deck[i + 1] = new Card(j);
            deck[i + 2] = new Card(j);
            deck[i + 3] = new Card(j);
            i += 4;
        }
        // Create 9 cards of value 9
        for (int j = i; i < j + 9; i++) {
            deck[i] = new Card(9);
        }
        for (int j = 0; j < 3; j++) {
            deck[i] = new Card(Card.Type.DRAW2);
            deck[i + 1] = new Card(Card.Type.PEEK);
            deck[i + 2] = new Card(Card.Type.SWAP);
            i += 3;
        }

        shuffle();
    }

    public Deck(DiscardPile dp) {
        topOfDeck = 0;

        while (!dp.discardPile.isEmpty()) {
            deck[topOfDeck] = dp.pop();
            topOfDeck++;
        }

        shuffle();
    }

    public Card pop() {
        Card popped = null;

        if (topOfDeck >= 0) {
            popped = deck[topOfDeck];
            deck[topOfDeck] = null;
            topOfDeck--;
        } else {
            System.out.println("Deck Pop: card could not be removed, out of bounds.");
        }

        return popped;

    }// End of pop()

    public void push(Card pushed) {
        if (topOfDeck < 53 && topOfDeck >= -1) {
            topOfDeck++;
            deck[topOfDeck] = pushed;
        } else {
            System.out.println("Deck Push: card could not be added, out of bounds.");
        }
    }// End of push()

    protected void shuffle() {
        Random rand = new Random();
        for (int i = 0; i < topOfDeck; i++) {
            int j = rand.nextInt(topOfDeck + 1);
            Card temp = deck[j];
            deck[j] = deck[i];
            deck[i] = temp;
        }
    }// End of shuffle()

}// End of Deck Class
