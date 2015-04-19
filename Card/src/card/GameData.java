public class GameData {
	
	public Deck deck;
	public DiscardPile dp;
	public ArrayList<Hand> hands;
	public String playerName;
	public boolean firstRound;

	public GameData(Deck deck, DiscardPile dp, ArrayList<Hand> hands, String playerName) {
		this.deck = deck;
		this.dp = dp;
		this.hands = hands;
		this.playerName = playerName;
		firstRound = true;
	}// End of Constructor


}// End of GameData