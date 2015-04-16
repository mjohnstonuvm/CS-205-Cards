package card;
import java.util.ArrayList;

public class Game extends CardObject {

	protected Deck deck = new Deck();
	protected ArrayList<Hand> hands = new ArrayList<>();
	protected EasyAI ai = new EasyAI();
	protected String endCondition;
	protected DiscardPile dp = new DiscardPile();

	public Game(String endCondition, int numOfAI, String difficulty) {

		for (int i = 0; i < numOfAI; i++) {
			hands.add(new Hand(deck.pop(), deck.pop(), deck.pop(), deck.pop()));
		}

		this.endCondition = endCondition;
		this.difficulty = difficulty;

	}// End of Game constructor

	public void run() {

		Card dpTop = deck.pop();
		// AVOID INFINITE LOOP IF ALL CARDS ARE POWER CARDS
		while(dpTop.getType() != Type.NUM  && ) { 
			deck.push(dpTop);
			dpTop = deck.pop();
		}
		
		dp.add(dpTop);

		// GAME LOOP
		int roundCount = 0;
		while(roundCount < 10) {
			for (int i = 0; i < hands.size(); i++) {
				Card dpPeek = dp.draw();
				// Player's Turn
				if (i == 0) {
					printStatus();
					// Get player input
				}
				//AI's Turn
				else {
					boolean action = ai.DrawOrDiscard(dpPeek);
					Card 
					if (action) {
						
					}
					else {
						hands.get(i).a
					}
				}
			}
			roundCount++;
		}

	}// End of run()

	protected void printStatus() {
		Hand playerHand = hands.get(0);
		System.out.print();
	}

}// End of Game Class