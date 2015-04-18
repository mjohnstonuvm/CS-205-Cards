package card;

import java.util.ArrayList;

public class Game extends CardObject {

    static protected Deck deck;
    static protected ArrayList<Hand> hands = new ArrayList<>();
    static protected EasyAI ai;
    static protected String endCondition, difficulty, name;
    static protected DiscardPile dp = new DiscardPile();
    static protected GuiClassTest gui = new GuiClassTest();
    static public int roundCount = 0, counter = 0;
    static public boolean gameState = true, userTurnDone = false,aiTurnDone = false;
    static Card dpTop;
    static Hand playerHand,aiHand;

    public Game(String endCondition, int numOfAI, String difficulty, String name) {
        //creates a new deck
        deck = new Deck();
        //sets the end condition
        this.endCondition = endCondition;
        //sets the difficult
        this.difficulty = difficulty;
        //sets the name
        this.name = name;

        //sets the easy ai
        ai = new EasyAI(numOfAI);

        //deals hands to players
        for (int i = 0; i < numOfAI; i++) {
            hands.add(new Hand(deck.pop(), deck.pop(), deck.pop(), deck.pop()));
        }
        //pops a card from the deck
        dpTop = deck.pop();

        // AVOID INFINITE LOOP IF ALL CARDS ARE POWER CARDS
        while (dpTop.getType() != Type.NUMBER) {
            deck.push(dpTop);
            dpTop = deck.pop();
        }

        //add to discard pile
        dp.add(dpTop);

        //creates gui card layout
        gui.createCards(numOfAI, dp);
        run(true, false,false);

    }// End of Game constructor

    public static void run(boolean gameState, boolean userTurnDone,boolean aiTurnDone) {
        Game.userTurnDone = userTurnDone;
        Game.gameState = gameState;
        // GAME LOOP
        while (gameState && roundCount < 10) {
            System.out.println("Round # : " + roundCount);
            for (int i = 0; i < hands.size(); i++) {
                // Player's Turn
                if (i == 0 && userTurnDone != true) {
                    gameState = false;
                    printStatus();
                } //AI's Turn
                if(aiTurnDone != true){
                    //gets AI hand
                    aiHand = new Hand(hands.get(i).peek(0),hands.get(i).peek(1),hands.get(i).peek(2),hands.get(i).peek(3));
                    //if discard pile is not empty
                    if (!dp.discardPile.isEmpty()) {
                        Card dpPeek = dp.draw(); //draws from discard pile
                        boolean action = ai.DrawOrDiscard(dpPeek); //gets decision
                        gui.opponentTurn(aiHand,action,i); //method for the ai to make a move
                        gameState = false;
                    } else {
                        boolean action = ai.DrawOrDiscard(); //gets decision
                        gui.opponentTurn(aiHand,action,i); //method for the ai to make a move
                        gameState = false;
                    }
                }
            }
            Game.userTurnDone = false;
            Game.aiTurnDone = false;
            Game.gameState = true;
            roundCount++;
        }
        Game.gameState = true;

    }// End of run()

    static protected void printStatus() {
        //creates the user hand
        playerHand = new Hand(hands.get(0).peek(0), hands.get(0).peek(1), hands.get(0).peek(2), hands.get(0).peek(3));
        //passes the hand and the round number to the gui
        gui.UserTurn(playerHand, roundCount);
    }


}// End of Game Class
