package card;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class EasyAI extends AI{

    private final int numPlayers;

    public static Random rand;
    private int player;
    private boolean mustuse = false;

    public EasyAI(int players, int play) {
        super(players);
        numPlayers = players;
        player = play;
    }

    public boolean drawOrDiscard(Card dis) { //takes as a parameter the top of the discard pile
        rand = new Random();
        //true means Draw pile false means discard pile
        int b = rand.nextInt(2);
        if (b == 0 && dis.getType() == Card.Type.NUMBER) {
            mustuse = true;
            return false;
            

        } else {
            return true;

        }
    }

     public boolean drawOrDiscard() {
        return true;
    }

    public int[] cardDraw(Card drawnCard) {
        rand = new Random();
        int[] a = {};
        if (drawnCard.getType() == Card.Type.NUMBER) {
            int c = rand.nextInt(2); // first part of the array is if it should be discarded or put into the hand
            if (mustuse) {
                c= 1;
                mustuse = false;
            }
            a = new int[]{c, rand.nextInt(4)}; //second part of the array is which hand part it should be put in
            return a;

        } else if (drawnCard.getType() == Card.Type.PEEK) {
            int c = rand.nextInt(2); //first part of the array is whether or not to use the peek card
            if (c == 1)  {
                c = 2;
            }
            a = new int[]{c, rand.nextInt(4)}; // second part of the array is which card to peek at, starting at their own cards and going clockwise
            return a;
        } else if (drawnCard.getType() == Card.Type.SWAP) {
            int c = rand.nextInt(2); //first part of the array is whether or not to use the swap card
            if (c == 1)  {
                c = 3;
            }
            int d = rand.nextInt(numPlayers);
            while (d== player) {
                d = rand.nextInt(numPlayers);
            }
            a = new int[]{c, rand.nextInt(4), d, rand.nextInt(4)}; //second part of the array is the card that the player has that will be swapped, the third part is the "destination" of that card
            return a;
        } else if (drawnCard.getType() == Card.Type.DRAW2) {
            a = new int[]{4};
            return a;
        }
        return a;

    }
    public void update(ArrayList<Hand> handsin) {
        return;
    }

    /*public static void main(String[] args) {
        EasyAI a = new EasyAI(3);
        //WORKS
        Card c = new Card(9);
        System.out.println(Arrays.toString(a.cardDraw(c)));
        System.out.println(a.drawOrDiscard(c));
        
        //works
        Card d = new Card(Type.PEEK);
        System.out.println(Arrays.toString(a.cardDraw(d)));
        System.out.println(a.drawOrDiscard(d));
        //works
        Card e = new Card(Type.SWAP);
        System.out.println(Arrays.toString(a.cardDraw(e)));
        System.out.println(a.drawOrDiscard(e));
    }
    */
}
