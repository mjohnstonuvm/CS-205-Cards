//package card;

import java.util.Arrays;
import java.util.Random;

public class AI extends CardObject {

    private final int numPlayers;


    public AI(int players) {
        numPlayers = players;
    }

    public boolean DrawOrDiscard(Card dis) { //takes as a parameter the top of the discard pile
        return true;
    }

     public boolean DrawOrDiscard() {
        return true;
    }

    public int[] CardDraw(Card drawnCard) {
        int[] a = {};     
        return a;

    }
    public void Update() {
        return;
    }

    public static void main(String[] args) {
        AI a = new EasyAI(3);
        //WORKS
        Card c = new Card(9);
        System.out.println(Arrays.toString(a.CardDraw(c)));
        System.out.println(a.DrawOrDiscard(c));
        
        //works
        Card d = new Card(Type.PEEK);
        System.out.println(Arrays.toString(a.CardDraw(d)));
        System.out.println(a.DrawOrDiscard(d));
        //works
        Card e = new Card(Type.SWAP);
        System.out.println(Arrays.toString(a.CardDraw(e)));
        System.out.println(a.DrawOrDiscard(e));
    }
}
