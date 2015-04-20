//package card;
import java.util.Random;

public class Deck extends CardObject {

	protected CardObject.Card[] deck = new CardObject.Card[54];
	protected int lastCard = 53;
	public final int length = deck.length;

	public Deck() {
		int i = 0;
		// Create normal cards
		for (int j = 0; j < 9; j++) {
			deck[i] = new CardObject.Card(j);
			deck[i + 1] = new CardObject.Card(j);
			deck[i + 2] = new CardObject.Card(j);
			deck[i + 3] = new CardObject.Card(j);
			i += 4;
		}
		// Create 9 cards of value 9
		for (int j = i; i < j + 9; i++) {
			deck[i] = new CardObject.Card(9);
		}
		for (int j = 0; j < 3 ; j++) {
			deck[i] = new CardObject.Card(CardObject.Type.DRAW2);
			deck[i + 1] = new CardObject.Card(CardObject.Type.PEEK);
			deck[i + 2] = new CardObject.Card(CardObject.Type.SWAP);
			i += 3;
		}

		shuffle();
	}

	public CardObject.Card pop() {
		CardObject.Card returnCard = null;

		if (lastCard >= 0) {
			returnCard = deck[lastCard];
			deck[lastCard] = null;
			lastCard--;
		}
		else {
			System.out.println("Deck Pop: card could not be removed, out of bounds.");
		}
		
		return returnCard;

	}

	public void push(CardObject.Card returnedCard) {
		if (lastCard < 53 && lastCard >= -1) {
			lastCard++;
			deck[lastCard] = returnedCard;
		}
		else {
			System.out.println("Deck Push: card could not be added, out of bounds.");
		}
	}// End

	protected void shuffle() {
		Random rand = new Random();
		for (int i = 0; i < lastCard; i++) {
			int j = rand.nextInt(lastCard + 1);
			CardObject.Card temp = deck[j];
			deck[j] = deck[i];
			deck[i] = temp;
		}
	}

}// End of Deck Class
