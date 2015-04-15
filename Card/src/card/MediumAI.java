package card;

import java.util.Arrays;
import java.util.Random;

public class MediumAI extends CardObject {

    private final int numPlayers;

    public static Random rand;
    private int [] hand;
    private int [] peeked;

    public MediumAI(int players, Card one, Card two) {
        numPlayers = players;
        hand[0] = one.getNumber();
        hand[1] = 5;// average value
        hand[2] = 5; //average value
        hand[3] = two.getNumber();
        peeked[0] = hand[0];
        peeked[3] = hand[3];
    }

    public boolean DrawOrDiscard(Card dis) { //takes as a parameter the top of the discard pile
        //true means Draw pile false means discard pile
        if (dis.getNumber() <= 5 && dis.getType() == Type.NUMBER) { // if the discard has a less than 5 than use it to swap with your hand
            return false;

        } else {
            return true;

        }
    }

    public int[] CardDraw(Card drawnCard) {
        int[] a = {};
        if (drawnCard.getType() == Type.NUMBER) {
           // int c = rand.nextInt(2); // first part of the array is if it should be discarded or put into the hand
           // a = new int[]{c, rand.nextInt(4)}; //second part of the array is which hand part it should be put in
           // return a;
            int highestindex = 0;
            int highestvalue = hand[0];
            for (int i = 0, i < 4, i++) {
                if (hand[i] > highestvalue) {
                    highestindex = i;
                    highestvalue = hand[i];
                }
            }
            if (drawnCard.getNumber() < highestvalue) {
                a = new int [] {1, highestindex};
                return a;
            } else {
                a = new int [] {0, 0};
                return a;
            }

        } else if (drawnCard.getType() == Type.PEEK) {
            for (int i = 0, i < peeked.length, i++) {
                if (peeked[i] == NULL) {
                    a = new int[] {1, i};
                    return a;
                } else {
                    a = new int[] {0, 0};
                }
            }
        } else if (drawnCard.getType() == Type.SWAP) {
           // int c = rand.nextInt(2); //first part of the array is whether or not to use the swap card
            //a = new int[]{c, rand.nextInt(4), rand.nextInt((numPlayers - 1) * 4)}; //second part of the array is the card that the player has that will be swapped, the third part is the "destination" of that card
            //return a;
            
        }
        return a;

    }

    public static void main(String[] args) {
        MediumAI a = new MediumAI(3, new Card(2), new Card(4));
        //WORKS
        Card c = new Card(9);
        System.out.println(Arrays.toString(a.CardDraw(c)));
        System.out.println(a.DrawOrDiscard(c));
        
        //doesnt work
        Card d = new Card(Type.PEEK);
        System.out.println(Arrays.toString(a.CardDraw(d)));
        System.out.println(a.DrawOrDiscard(d));
        //doesnt work
        Card e = new Card(Type.SWAP);
        System.out.println(Arrays.toString(a.CardDraw(e)));
        System.out.println(a.DrawOrDiscard(e));
    }
}
