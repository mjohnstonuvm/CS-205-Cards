//package card;

import java.util.ArrayList;

public class Game extends CardObject {

   protected Deck deck;
   protected ArrayList<Hand> hands = new ArrayList<>();
   protected EasyAI ai;
   protected String endCondition, difficulty, name;
   protected DiscardPile dp = new DiscardPile();
   protected GuiClassTest gui = new GuiClassTest();
   public int roundCount = 0, counter = 0, index = 0;
   public boolean gameState = true, userTurnDone, op1TurnDone,
            op2TurnDone, op3TurnDone, turnsDone = false;
   Card dpTop;
   Hand playerHand, aiHand;

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
    
    public void addToDP(Card toAdd) {
        dp.add(toAdd);
    }
    
    public Card drawFromDeck() { 
        
        return deck.pop();
        
    }// End of drawFromDeck()
    
    public Card drawFromDP() {
        
        return dp.draw();
        
    }// End of drawFromDP()
    
    public String getName() {
        return name;
    }// End of getName()
    
    public void run(boolean gameState, boolean userTurnDone, boolean op1TurnDone,
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
                                op1TurnDone = true;
                                opponentTurn();
                            }
                            if (op2TurnDone != true && op == 2) {
                                op2TurnDone = true;
                                opponentTurn();
                            }
                            if (op3TurnDone != true && op == 3) {
                                op3TurnDone = true;
                                opponentTurn();
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
    
    protected void opponentTurn() {
        boolean drawDecision;
        int[] result;
        //if discard pile is not empty
        Card dpPeek = dp.draw(); //draws from discard pile
        drawDecision = ai.DrawOrDiscard(dpPeek); //returns action
        
        if (drawDecision) {
            result = ai.CardDraw(deck.pop());
        }
        else {
            result = ai.CardDraw(dpPeek);
        }
        
        gui.opponentTurn(result); //method for the ai to make a move
    }

}// End of Game Class
