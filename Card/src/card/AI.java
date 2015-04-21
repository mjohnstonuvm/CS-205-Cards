package card;

import java.util.Arrays;
import java.util.Random;
import java.util.ArrayList;

public class AI {

    private final int numPlayers;


    public AI(int players) {
        numPlayers = players;
    }

    public boolean drawOrDiscard(Card dis) { //takes as a parameter the top of the discard pile
        return true;
    }

     public boolean drawOrDiscard() {
        return true;
    }

    public int[] cardDraw(Card drawnCard) {
        int[] a = {};     
        return a;

    }
    public void update(ArrayList<Hand> handsin) {
        return;
    }
/*
    public static void main(String[] args) {
        AI a = new EasyAI(3);
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