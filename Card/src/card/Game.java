//package card;

import java.util.ArrayList;

public class Game extends CardObject {

    protected ArrayList<Hand> hands = new ArrayList<>();
    protected Card dpTop;
    protected Deck deck;
    protected DiscardPile dp = new DiscardPile();
    protected EasyAI ai;
    protected GuiClassTest gui = new GuiClassTest();
    protected String endCondition, difficulty, name;
    public boolean gameState = true, userTurnDone, op1TurnDone,
            op2TurnDone, op3TurnDone, turnsDone = false;
    public int roundCount = 0, counter = 0, index = 0;

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

        //creates the user hand
        hands.add(new Hand(hands.get(0).peek(0), hands.get(0).peek(1), hands.get(0).peek(2), hands.get(0).peek(3)));
        

        //deals hands to players
        for (int i = 0; i < numOfAI + 1; i++) {
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
                if (i == 0) {
                    gameState = false;
                    //passes the hand and the round number to the gui
                    gui.UserTurn(hands.get(0), roundCount);
                    // NEED TO GET DATA BACK AFTER USER TURN
                }
                else {
                    opponentTurn(i);
                }
            }
             roundCount++;       
        }

    }// End of run()
    
    protected void opponentTurn(int oppNum) {
        boolean drawDecision;
        Hand oppHand = hand.get(oppNum);
        int[] result;
        String fileName;
        //if discard pile is not empty
        Card selectedCard = dp.draw(); //draws from discard pile
        drawDecision = ai.DrawOrDiscard(selectedCard); //returns action
        
        if (drawDecision) {
            Card selectedCard = deck.pop();
            while(selectedCard == Type.DRAW2) {
                dp.add(selectedCard);
                selectedCard = deck.pop();
            }
            result = ai.CardDraw(selectedCard);
        }
        else {
            result = ai.CardDraw(selectedCard);
        }

        // update model with result and selectedCard
        // produce fileNames to send to gui
        // result[0] == 0: when the AI wants to discard the card that was drawn
        // result[0] == 2: when the AI wants uses a peek card, effectly does nothing
        if (result[0] == 0 || result == 2) {
            dp.add(selectedCard);
            fileName == selectedCard.getCard();
        }
        // result[0] == 1: when the AI wants to exchange a card in its hand with the one drawn
        else if (result[0] == 1) {
            Card toDiscard = oppHand.swap(selectedCard, result[1]);
            dp.add(toDiscard);
            fileName == toDiscard.getCard();
        }
        // result[0] == 3: when the AI uses a swap card to swap cards with another player
        else if (result[0] == 3) {
            // Swap cards between opponent and other player
            Hand otherHand = hands.get(result[2]);
            Card otherCard = otherHand.swap(oppHand.get(result[1]), result[3])
            otherCard = oppHand.swap(otherHand, result[1])
            otherCard = null;
            // Update the hand that the opponent swapped cards with
            hands.set(result[2], otherHand);
            fileName == selectedCard.getCard();
        }
        // else, say an error occurred
        else {
            System.out.println("Game.java Error: AI result not in the correct format. 
                result[0] should be an int from 0 to 3");
        }
        // Update opponent hand after turn is complete
        hands.set(oppNum, oppHand);
        // Update gui with changes made during turn
        gui.opponentTurn(result, drawDecision, fileName);
    }// End of opponentTurn()

}// End of Game Class