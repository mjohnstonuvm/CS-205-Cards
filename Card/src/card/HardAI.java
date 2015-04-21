package card;

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
        numPlayers = players;
        hands = handsin;
        playernum = player;
         Hand h  = handsin.get(player);
       ownhand[0] = h.peek(0).getNumber();
       ownhand[1] = h.peek(1).getNumber();
       ownhand[2] = h.peek(2).getNumber();
       ownhand[3] = h.peek(3).getNumber();
    }

    public boolean drawOrDiscard(Card dis) { //takes as a parameter the top of the discard pile
        //true means Draw pile false means discard pile
        int highestindex = 0;
            int highestvalue = ownhand[0];
            for (int i = 0; i < 4; i++) {
                if (ownhand[i] > highestvalue) {
                    highestindex = i;
                    highestvalue = ownhand[i];
                }
            }

        if (dis.getNumber() <= 5 && dis.getType() == Card.Type.NUMBER && dis.getNumber() < highestvalue) { // if the discard has a less than 5 than use it to swap with your hand
            return false;

        } else {
            return true;

        }
    }

    public boolean drawOrDiscard() {
        return true;
    }

    public void update(ArrayList<Hand> handsin) {
        hands = handsin;
          Hand h  = handsin.get(playernum);
       ownhand[0] = h.peek(0).getNumber();
       ownhand[1] = h.peek(1).getNumber();
       ownhand[2] = h.peek(2).getNumber();
       ownhand[3] = h.peek(3).getNumber();
    }

    public int[] cardDraw(Card drawnCard) {
        int[] a = {};
        if (drawnCard.getType() == Card.Type.NUMBER) {
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
                if (ownhand[i]==-1) {
                    highestindex = i;
                    highestvalue = 10;
                }
            }
            if (drawnCard.getNumber() < highestvalue) {
                a = new int [] {1, highestindex};
                return a;
                 
            } else {
                a = new int [] {0, 0};
                return a;
            }

        } else if (drawnCard.getType() == Card.Type.PEEK) {
                    a = new int[] {0, 0};
                    return a;
            }
         else if (drawnCard.getType() == Card.Type.SWAP) {
           // int c = rand.nextInt(2); //first part of the array is whether or not to use the swap card
            //a = new int[]{c, rand.nextInt(4), rand.nextInt((numPlayers - 1) * 4)}; //second part of the array is the card that the player has that will be swapped, the third part is the "destination" of that card
            //return a;
            
            
            int highestindex = 0;
            int highestvalue = ownhand[0];
            for (int i = 0; i < 4; i++) {
                if (ownhand[i] > highestvalue) {
                    highestindex = i;
                    highestvalue = ownhand[i];
                }
            }
            int lowestplayer = 0;
            int lowestindex = 0;
            int lowestvalue = 9;
            for (int i = 0; i < 4; i++) {
                Hand f = hands.get(i);
                for (int j= 0; j < 4; j++ ) {
                    if (f.peek(j).getNumber() < lowestvalue && f.peek(j).getType() == Card.Type.NUMBER) {
                        lowestplayer = i;
                        lowestindex = j;
                        lowestvalue = f.peek(j).getNumber();
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
        System.out.println(Arrays.toString(a.cardDraw(c)));
        System.out.println(a.drawOrDiscard(c));
        
        //doesnt work
        Card g = new Card(4);
        System.out.println(Arrays.toString(a.cardDraw(g)));
        System.out.println(a.drawOrDiscard(g));

        Card d = new Card(Type.PEEK);
        System.out.println(Arrays.toString(a.cardDraw(d)));
        System.out.println(a.drawOrDiscard(d));
        //doesnt work
        Card e = new Card(Type.SWAP);
        System.out.println(Arrays.toString(a.cardDraw(e)));
        System.out.println(a.drawOrDiscard(e));
    }
    */
}
