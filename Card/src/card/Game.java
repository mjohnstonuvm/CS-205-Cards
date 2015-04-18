package card;

import java.util.ArrayList;

public class Game extends CardObject {

    static protected Deck deck;
    static protected ArrayList<Hand> hands = new ArrayList<>();
    static protected EasyAI ai;
    static protected String endCondition, difficulty, name;
    static protected DiscardPile dp = new DiscardPile();
    static protected GuiClassTest gui = new GuiClassTest();
    static public int roundCount = 0, counter = 0, index = 0;
    static public boolean gameState = true, userTurnDone, op1TurnDone,
            op2TurnDone, op3TurnDone, action, turnsDone = false;
    static Card dpTop;
    static Hand playerHand, aiHand;

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
        ai = new EasyAI(numOfAI+1);

        //deals hands to players
        for (int i = 0; i < numOfAI + 1; i++) {
            hands.add(new Hand(deck.pop(), deck.pop(), deck.pop(), deck.pop()));
        }
        //creates the user hand
        playerHand = new Hand(hands.get(0).peek(0), hands.get(0).peek(1), hands.get(0).peek(2), hands.get(0).peek(3));
        
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
        run(true, false, false, false, false);

    }// End of Game constructor

    public static void run(boolean gameState, boolean userTurnDone, boolean op1TurnDone,
            boolean op2TurnDone, boolean op3TurnDone) {

        // GAME LOOP
        //end game options can go in the while loop
        while (gameState && roundCount < 10) {
            System.out.println("Round # : " + roundCount);
            for (int i = 0; i < hands.size(); i++) {
                // Player's Turn
                if (i == 0 && userTurnDone != true && op1TurnDone == false
                        && op2TurnDone == false && op3TurnDone == false) {
                    gameState = false;
                    //passes the hand and the round number to the gui
                    gui.UserTurn(playerHand, roundCount);
                }
                if (i > 0 && userTurnDone == true) {
                    //loop thru opponents hands
                    for (int op = 1; op < hands.size(); op++) {
                        //waits for the user input
                            //gets AI hand
                            aiHand = new Hand(hands.get(op).peek(0), hands.get(op).peek(1), hands.get(op).peek(2), hands.get(op).peek(3));
                            if (op1TurnDone != true && op == 1) {
                                //if discard pile is not empty
                                if (!dp.discardPile.isEmpty()) {
                                    Card dpPeek = dp.draw(); //draws from discard pile
                                    action = ai.DrawOrDiscard(dpPeek); //returns action
                                } else {
                                    action = ai.DrawOrDiscard(); //returns true if discardpile is empty
                                }
                                gui.opponentTurn(aiHand, action, op); //method for the ai to make a move
                            }
                            if (op2TurnDone != true && op == 2) {
                                op2TurnDone = true;
                                gui.opponentTurn(aiHand, action, op); //method for the ai to make a move
                            }
                            if (op3TurnDone != true && op == 3) {
                                op3TurnDone = true;
                                gui.opponentTurn(aiHand, action, op); //method for the ai to make a move
                            }
                        //userTurnDone = false;
                    }//end of inner for loop
                    userTurnDone = false;
                    op1TurnDone = false;
                    op2TurnDone = false;
                    op3TurnDone = false;
                    gameState = true;
                    turnsDone = true;
                }//end if

            }//end of outer for loop
            if (turnsDone == true) {
                roundCount++;
                turnsDone = false;
            }
        }

    }// End of run()

}// End of Game Class
