package card;

import java.util.Arrays;
import java.util.Random;

public class EasyAI extends CardObject {

    public static int numPlayers;
    DiscardPile d;
    public static Random rand;

    public EasyAI(int players, DiscardPile discard) {
        numPlayers = players;
        d = discard;
    }

    public boolean DrawOrDiscard(Card dis) { //takes as a parameter the top of the discard pile
        rand = new Random();
        //true means Draw pile false means discard pile
        int b = rand.nextInt(2);
        if (b == 0 && dis.getType() == Type.NUMBER) {
            return false;

        } else {
            return true;

        }
    }

    public static int[] CardDraw(Card drawnCard) {
        rand = new Random();
        int[] a = {};
        if (drawnCard.getType() == Type.NUMBER) {
            int c = rand.nextInt(2); // first part of the array is if it should be discarded or put into the hand
            a = new int[]{c, rand.nextInt(4)}; //second part of the array is which hand part it should be put in
            return a;

        } else if (drawnCard.getType() == Type.PEEK) {
            int c = rand.nextInt(2); //first part of the array is whether or not to use the peek card
            a = new int[]{c, rand.nextInt(numPlayers * 4)}; // second part of the array is which card to peek at, starting at their own cards and going clockwise
            return a;
        } else if (drawnCard.getType() == Type.SWAP) {
            int c = rand.nextInt(2); //first part of the array is whether or not to use the swap card
            a = new int[]{c, rand.nextInt(4), rand.nextInt(numPlayers - 1 * 4)}; //second part of the array is the card that the player has that will be swapped, the third part is the "destination" of that card
            return a;
        }
        return a;

    }

    public static void main(String[] args) {
        //WORKS
        Card c = new Card(Type.NUMBER);
        System.out.println(Arrays.toString(CardDraw(c)));
        
        //doesnt work
        Card d = new Card(Type.PEEK);
        System.out.println(Arrays.toString(CardDraw(d)));
        
        //doesnt work
        Card e = new Card(Type.SWAP);
        System.out.println(Arrays.toString(CardDraw(e)));
    }
}