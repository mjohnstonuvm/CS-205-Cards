package card;
import java.util.Random;
public class Deck extends CardObject {

	protected Card[] deck = new Card[54];
	protected int lastCard = 53;
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
		for (int j = 0; j < 3 ; j++) {
			deck[i] = new Card(Type.DRAW2);
			deck[i + 1] = new Card(Type.PEEK);
			deck[i + 2] = new Card(Type.SWAP);
			i += 3;
		}

		shuffle();
	}

	public Card pop() {
		Card returnCard = null;

		if (lastCard < deck.length) {
			returnCard = deck[lastCard];
			deck[lastCard] = null;
			lastCard--;
		}
		
		return returnCard;

	}

	public void push(Card returnedCard) {
		if (lastCard < 53 && lastCard > -2) {
			lastCard++;
			deck[lastCard] = returnedCard;
		}
		else {
			System.out.println("Deck Push: card couldn't not be added, out of bounds.");
		}
	}

	protected void shuffle() {
		Random rand = new Random();
		for (int i = 0; i < lastCard; i++) {
			int j = rand.nextInt(lastCard + 1);
			Card temp = deck[j];
			deck[j] = deck[i];
			deck[i] = temp;
		}
	}

}// End of Deck Class