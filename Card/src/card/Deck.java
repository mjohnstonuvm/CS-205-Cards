package card;

import java.util.Random;
public class Deck extends CardObject {

	private Card[] deck = new Card[54];
	private int lastCard = 0;

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
			lastCard++;
		}
		
		return returnCard;

	}

	private void shuffle() {
		Random rand = new Random();
		for (int i = 0; i < deck.length; i++) {
			int j = rand.nextInt(54);
			Card temp = deck[j];
			deck[j] = deck[i];
			deck[i] = temp;
		}
	}

	public static void main(String[] args) {
		Deck test = new Deck();
		for (int i = 0; i < 54; i++) {
			Card card = test.pop();
			if(card != null) System.out.println("Card " + String.valueOf(i) + ": " + card.toString());
			else System.out.println("NULLLLLLLL");
		}
	}

}// End of Deck Class