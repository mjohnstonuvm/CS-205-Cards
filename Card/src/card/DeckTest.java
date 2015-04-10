package card;

public class DeckTest extends CardObject {

	public static void main(String[] args) {

		Deck deck = new Deck();

		for (int i = 0; i < deck.length; i++) {
			Card card = deck.pop();
			if(card != null) System.out.println("Card " + String.valueOf(i) + ": " + card.toString());
			else System.out.println("NULLLLLLLL");
		}
		Card pushed = new Card(Type.DRAW2);
		deck.push(pushed);
		deck.push(pushed);

		Card card = deck.pop();
		if(card != null) System.out.println("Pushed Card " + card.toString());
		else System.out.println("NULLLLLLLL");

		card = deck.pop();
		if(card != null) System.out.println("Pushed Card " + card.toString());
		else System.out.println("NULLLLLLLL");	

	}

}