package card;

import java.util.ArrayList;

public class Game extends CardObject {

    protected EasyAI ai;
    protected GameData data;
    protected GuiClassTest gui = new GuiClassTest();
    protected String endCondition, difficulty, playerName;
    public int roundCount = 0;

    public Game(String endCondition, int numOfAI, String difficulty, String playerName) {
        
        Deck deck = new Deck();
        ArrayList<Hand> hands = new ArrayList<>();
        Card dpTop;
        DiscardPile dp = new DiscardPile();
        //sets the end condition
        this.endCondition = endCondition;
        //sets the difficult
        this.difficulty = difficulty;
        //sets the playerName
        this.playerName = playerName;
        //sets the easy ai
        ai = new EasyAI(numOfAI);

        //creates the user hand
        hands.add(new Hand(deck.pop(), deck.pop(), deck.pop(), deck.pop()));

        //deals hands to players
        while (numOfAI > 0) {
            hands.add(new Hand(deck.pop(), deck.pop(), deck.pop(), deck.pop()));
            numOfAI--;
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

        data = new GameData(deck, dp, hands, playerName);

        //creates gui card layout
        gui.createCards(numOfAI, dp);

        run(true);

    }// End of Game constructor
    
    public void run(boolean gameState) {

        // GAME LOOP
        //end game options can go in the while loop
        while (gameState && roundCount < 10) {
            System.out.println("Round # : " + roundCount);
            for (int i = 0; i < data.hands.size(); i++) {
                // Player's Turn
                if (i == 0) {
                    gameState = false;
                    //passes the game data to gui for user's turn
                    data = gui.userTurn(data);
                }
                else {
                    opponentTurn(i);
                }
            }
            if (roundCount == 0) {
                data.firstRound = false;
            }
            roundCount++; 
        }

        // End game statistics

    }// End of run()
    
    protected void opponentTurn(int oppNum) {
        boolean drawDecision;
        Hand oppHand = data.hands.get(oppNum);
        int[] result;
        String fileName = "";
        //if discard pile is not empty
        Card selectedCard = data.dp.draw(); //draws from discard pile
        drawDecision = ai.DrawOrDiscard(selectedCard); //returns action
        
        if (drawDecision) {
            selectedCard = data.deck.pop();
            while(selectedCard.getType() == Type.DRAW2) {
                data.dp.add(selectedCard);
                selectedCard = data.deck.pop();
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
        if (result[0] == 0 || result[0] == 2) {
            data.dp.add(selectedCard);
            fileName = selectedCard.getCard();
        }
        // result[0] == 1: when the AI wants to exchange a card in its hand with the one drawn
        else if (result[0] == 1) {
            Card toDiscard = oppHand.swap(selectedCard, result[1]);
            data.dp.add(toDiscard);
            fileName = toDiscard.getCard();
        }
        // result[0] == 3: when the AI uses a swap card to swap cards with another player
        else if (result[0] == 3) {
            // Swap cards between opponent and other player
            Hand otherHand = data.hands.get(result[2]);
            Card otherCard = otherHand.swap(oppHand.peek(result[1]), result[3]);
            otherCard = oppHand.swap(otherHand.peek(result[3]), result[1]);
            otherCard = null;
            // Update the hand that the opponent swapped cards with
            data.hands.set(result[2], otherHand);
            fileName = selectedCard.getCard();
        }
        // else, say an error occurred
        else {
            System.out.println("Game.java Error: AI result not in the correct format "
                    + "result[0] should be an int from 0 to 3");
        }
        // Update opponent hand after turn is complete
        data.hands.set(oppNum, oppHand);
        // Update gui with changes made during turn
        gui.opponentTurn(fileName);
    }// End of opponentTurn()

}// End of Game Class