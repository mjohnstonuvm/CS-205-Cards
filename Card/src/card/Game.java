package card;

import java.util.ArrayList;

public class Game  {

    protected ArrayList<AI> ai = new ArrayList<>();
    protected GameData data;
    protected GuiClassTest gui;
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


        //creates the user hand
        hands.add(new Hand(deck.pop(), deck.pop(), deck.pop(), deck.pop()));

        // Creates the AI hands and the AI themselves
        for (int i = 0; i < numOfAI; i++) {
            System.out.println("NumAI= " + numOfAI);
            hands.add(new Hand(deck.pop(), deck.pop(), deck.pop(), deck.pop()));      
            
        }

        for (int i = 0; i < numOfAI; i++) {
            if (difficulty == "Easy") {
                ai.add(new EasyAI(numOfAI, i));
            }
            else if (difficulty == "Medium") {
                ai.add(new MediumAI(numOfAI, hands, i));
            }
            else { 
                ai.add(new HardAI(numOfAI, hands, i));
            }        
        }


        //pops a card from the deck
        dpTop = deck.pop();

        // AVOID INFINITE LOOP IF ALL CARDS ARE POWER CARDS
        while (dpTop.getType() != Card.Type.NUMBER) {
            deck.push(dpTop);
            dpTop = deck.pop();
        }

        //add to discard pile
        dp.push(dpTop);

        data = new GameData(deck, dp, hands, playerName, numOfAI);
        
        gui = new GuiClassTest(data);
        
        //creates gui card layout
        gui.createCards(numOfAI, dp);

        run(true);

    }// End of Game constructor
    
    public void run(boolean gameState) {

        boolean win = true;
        int[] scores;
        //EndGame endScreen;

        // GAME LOOP
        //end game options can go in the while loop

        gui.userInitialPeek(data);

        while (roundCount < 10) {
            System.out.println("Round " + roundCount);
            for (int i = 0; i < data.hands.size(); i++) {
                System.out.println("Number of hands: " + data.hands.size());
                System.out.println("iterator:" + i);
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
            roundCount++; 
        }

        scores = calculateScores();

        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > scores[0]) {
                win = false;
            }
        }

        // End game statistics screen
        //endScreen = new EndGame(playerName, difficulty, scores[0]`, win);

    }// End of run()
    
    protected void opponentTurn(int oppNum) {
        
        AI oppAI = ai.get(oppNum - 1);
        boolean drawDecision;
        Hand oppHand = data.hands.get(oppNum);
        int[] result;
        String fileName = "";
        Card selectedCard = data.dp.pop(); //draws from discard pile

        oppAI.update(data.hands);
        drawDecision = oppAI.drawOrDiscard(selectedCard); //returns action
        
        if (drawDecision) {
            selectedCard = data.deckPop();
            while(selectedCard.getType() == Card.Type.DRAW2) {
                data.dp.push(selectedCard);
                selectedCard = data.deckPop();
            }
            result = oppAI.cardDraw(selectedCard);
        }
        else {
            result = oppAI.cardDraw(selectedCard);
        }

        /*
            update model with result and selectedCard
            produce fileNames to send to gui
            result[0] == 0: when the AI wants to discard the card that was drawn
            result[0] == 2: when the AI wants uses a peek card, effectly does nothing
        */
        if (result[0] == 0 || result[0] == 2) {
            data.dp.push(selectedCard);
            fileName = selectedCard.getCard();
        }
        // result[0] == 1: when the AI wants to exchange a card in its hand with the one drawn
        else if (result[0] == 1) {
            Card toDiscard = oppHand.swap(selectedCard, result[1]);
            data.dp.push(toDiscard);
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

    protected int[] calculateScores() {

        Card popped;
        Hand hand;
        int[] scores = new int [data.hands.size()];

        for (int i = 0; i < data.hands.size(); i++) {

            hand = data.hands.get(i);

            // Calculate score for hand
            for (int j = 0; j < 4; j++) {
                popped = hand.peek(j);
                // Replace power card in hand with number card
                // as per the official rules
                while (popped.getType() != Card.Type.NUMBER) {
                    data.dp.push(hand.swap(data.deckPop(), j));
                }
                
            }
            scores[i] = hand.total();
        }

        return scores;

    }// End of calculateScores()

}// End of Game Class