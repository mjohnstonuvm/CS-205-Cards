package card;
import java.util.ArrayList;

public class Game extends CardObject {

	protected Deck deck = new Deck();
	protected ArrayList<Hand> hands = new ArrayList<>();
	protected boolean endCondition;

	public Game(boolean endCondition, int players) {

		for (int i = 0; i < players; i++) {
			hands.add(new Hand(deck.pop(), deck.pop(), deck.pop(), deck.pop()));
		}

		this.endCondition = endCondition;

	}// End of Game constructor

	public void run() {

		printStatus();
		Card discardPileTop = deck.pop();
		// AVOID INFINITE LOOP IF ALL CARDS ARE POWER CARDS
		while(discardPileTop.getType() != Type.NUM  && ) { 
			deck.push(discardPileTop);
			discardPileTop = deck.pop();
		}
		
		// GAME LOOP
		int roundCount = 0;
		while(roundCount < 10) {
			for (int i = 0; i < hands.size(); i++) {
				// Can take top card in discard pile (unless it's a power card)
				// or take new one from top of the deck

				// choose which card to replace, or discard picked up card

				// check if there are cards in the deck
					// shuffle the discard pile and use it as deck
			}
			roundCount++;
		}

	}// End of run()

	protected void printStatus() {
		Hand playerHand = hands.get(0);
		System.out.print();
	}

}// End of Game Class