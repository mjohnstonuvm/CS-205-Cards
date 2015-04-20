/*
Created by Keegan and Matt
*/
package card;

import java.util.ArrayList;

public class GameData {
	
	public Deck deck;
	public DiscardPile dp;
	public ArrayList<Hand> hands;
	public String playerName;
        public int numOfAI;
	public boolean firstRound;

	public GameData(Deck deck, DiscardPile dp, ArrayList<Hand> hands, String playerName,int numOfAI) {
		this.deck = deck;
		this.dp = dp;
		this.hands = hands;
		this.playerName = playerName;
                this.numOfAI = numOfAI;
		firstRound = true;
	}// End of Constructor

}// End of GameData