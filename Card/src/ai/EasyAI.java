import java.util.Random;

public class EasyAI extends CardObject{
	
	int numPlayers;
	DiscardPile d;
	public EasyAI(int players, DiscardPile discard) {
		numPlayers = players
		d = discard;
	}

	public boolean DrawOrDiscard(Card dis) { //takes as a parameter the top of the discard pile
	//true means Draw pile false means discard pile
		Random rand = new Random();
		int b = rand.nextInt(2);
		if (b ==  0  && dis.getType == Type.NUMBER) {
			return false;
			
		} else {
			return true;
			
		}
	}
	public int [] CardDraw(Card drawnCard) {
		if (drawnCard.getType()==Type.NUMBER) {
			int c = rand.nextInt(2); // first part of the array is if it should be discarded or put into the hand
			int []	a = {c, rand.nextInt(4)}; //second part of the array is which hand part it should be put in
			return a;
			
		} else if(drawnCard.getType==Type.PEEK) {
			int c = rand.nextInt(2); //first part of the array is whether or not to use the peek card
			int [] a = {c, rand.nextInt(numPlayers*4)}; // second part of the array is which card to peek at, starting at their own cards and going clockwise
			return a;
		} else if(drawnCard.getType==Type.SWAP) {
			int c = rand.nextInt(2); //first part of the array is whether or not to use the swap card
			int [] a = {c, rand.nextInt(4), rand.nextInt(numPlayers-1*4)}; //second part of the array is the card that the player has that will be swapped, the third part is the "destination" of that card
			return a; 
		}
	}



}