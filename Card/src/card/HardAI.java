//package card;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class HardAI extends AI {

    private final int numPlayers;

    public static Random rand;
    private int [] ownhand = new int[4];
    private int playernum;
    private ArrayList<Hand> hands;
    public HardAI(int players, ArrayList<Hand> handsin, int player) {
        super(players);
        hands = handsin;
        playernum = player;
         Hand h  = handsin.get(player);
       ownhand[0] = h.peek(0);
       ownhand[1] = h.peek(1);
       ownhand[2] = h.peek(2);
       ownhand[3] = h.peek(3);
    }

    public boolean DrawOrDiscard(Card dis) { //takes as a parameter the top of the discard pile
        //true means Draw pile false means discard pile
        int highestindex = 0;
            int highestvalue = ownhand[0];
            for (int i = 0; i < 4; i++) {
                if (ownhand[i] > highestvalue) {
                    highestindex = i;
                    highestvalue = ownhand[i];
                }
            }

        if (dis.getNumber() <= 5 && dis.getType() == Type.NUMBER && dis.getNumber() < highestvalue) { // if the discard has a less than 5 than use it to swap with your hand
            return false;

        } else {
            return true;

        }
    }

    public boolean DrawOrDiscard() {
        return true;
    }

    public void Update(ArrayList<Hand> handsin) {
        hands = handsin;
          Hand h  = handsin.get(player);
       ownhand[0] = h.peek(0);
       ownhand[1] = h.peek(1);
       ownhand[2] = h.peek(2);
       ownhand[3] = h.peek(3);
    }

    public int[] CardDraw(Card drawnCard) {
        int[] a = {};
        if (drawnCard.getType() == Type.NUMBER) {
           // int c = rand.nextInt(2); // first part of the array is if it should be discarded or put into the hand
           // a = new int[]{c, rand.nextInt(4)}; //second part of the array is which hand part it should be put in
           // return a;
            int highestindex = 0;
            int highestvalue = ownhand[0];
            for (int i = 0; i < 4; i++) {
                if (ownhand[i] > highestvalue) {
                    highestindex = i;
                    highestvalue = ownhand[i];
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
            for (int i = 0; i < peeked.length; i++) {
                if (hand[i] == 5) {
                    a = new int[] {2, i};
                    return a;
                } else {
                    a = new int[] {0, 0};
                    return a;
                }
            }
        } else if (drawnCard.getType() == Type.SWAP) {
           // int c = rand.nextInt(2); //first part of the array is whether or not to use the swap card
            //a = new int[]{c, rand.nextInt(4), rand.nextInt((numPlayers - 1) * 4)}; //second part of the array is the card that the player has that will be swapped, the third part is the "destination" of that card
            //return a;
            
            
            int highestindex = 0;
            int highestvalue = hand[0];
            for (int i = 0; i < 4; i++) {
                if (hand[i] > highestvalue) {
                    highestindex = i;
                    highestvalue = hand[i];
                }
            }
            int lowestplayer = 0;
            int lowestindex = 0;
            int lowestvalue = 9;
            for (int i = 0; i < 4; i++) {
                Hand f = hands.get(i);
                for (int j= 0; j < 4; j++ ) {
                    if (f.peek(j) < lowestvalue) {
                        lowestplayer = i;
                        lowestindex = j;
                        lowestvalue = f.peek(j);
                    }
                }
            }
            a = new int[] {3, highestindex, lowestplayer, lowestindex};
            return a;

        }
        return a;

    }
/*
    public static void main(String[] args) {
        Card b = new Card(2);
        Card f = new Card(4);
        HardAI a = new HardAI(3, b, f);
        //WORKS
        Card c = new Card(9);
        System.out.println(Arrays.toString(a.CardDraw(c)));
        System.out.println(a.DrawOrDiscard(c));
        
        //doesnt work
        Card g = new Card(4);
        System.out.println(Arrays.toString(a.CardDraw(g)));
        System.out.println(a.DrawOrDiscard(g));

        Card d = new Card(Type.PEEK);
        System.out.println(Arrays.toString(a.CardDraw(d)));
        System.out.println(a.DrawOrDiscard(d));
        //doesnt work
        Card e = new Card(Type.SWAP);
        System.out.println(Arrays.toString(a.CardDraw(e)));
        System.out.println(a.DrawOrDiscard(e));
    }
    */
}
