package card;

import java.util.ArrayList;

public class Game {

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
        System.out.println("difficult:" + difficulty);
        for (int i = 0; i < numOfAI; i++) {
            if (difficulty == "Easy") {
                ai.add(new EasyAI(numOfAI, i));
            } else if (difficulty == "Medium") {
                ai.add(new MediumAI(numOfAI, hands, i));
            } else {
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

        run();

    }// End of Game constructor

    public void run() {
        boolean win = true;
        int[] scores;
        //EndGame endScreen;

        // Forces user to look at their two outer cards before game begins
        gui.userInitialPeek(data);
        //passes the game data to gui for user's turn
        //gui.userTurn();  
        //gui.method; 
        
        // GAME LOOP
    if (endCondition == "Number of Rounds") {
        while (roundCount < 10) {
            System.out.println("Round " + roundCount);
            System.out.println(data.hands.get(0));
            System.out.println(data.hands.get(1));
            for (int i = 0; i < data.hands.size(); i++) {
                System.out.println("iterator is" + i);
                if(i == 0){
                    gui.userTurn();
                    data = gui.method(data);    
                }else{

                    opponentTurn(i);
                    try{
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
            roundCount++;
        }
    } else if (endCondition == "Time") {

    } else if (endCondition =="Number of Points") {

    }

        scores = calculateScores();

        for (int i = 1; i < scores.length; i++) {
            if (scores[i] < scores[0]) {
                win = false;
            }
        }

        // End game statistics screen
        gui.close();
        EndGame endScreen = new EndGame(playerName, difficulty, scores, win);
        endScreen.getEndGame();
    }// End of run()

    protected boolean opponentTurn(int oppNum) {

        AI oppAI = ai.get(oppNum-1);
        boolean drawDecision;
        Hand oppHand = data.hands.get(oppNum);
        int[] result;
        String fileName = "";
        Card selectedCard = data.dp.peek(); //peeks discard pile
        boolean usedCard = true;
        
        oppAI.update(data.hands);
        drawDecision = oppAI.drawOrDiscard(selectedCard); //returns action

        if (drawDecision) {
            selectedCard = data.deckPop();
            /*while (selectedCard.getType() == Card.Type.DRAW2) { //The AI always use Draw 2 cards
                data.dp.push(selectedCard);
                System.out.println("Pushed a card to discard pile");               
                selectedCard = data.deckPop();
                drawTwice = true;
            }*/
            result = oppAI.cardDraw(selectedCard);
        } else {
            selectedCard = data.dp.pop();
            result = oppAI.cardDraw(selectedCard);
        }
        System.out.println("result[0]= " + result[0]);
        /*
         update model with result and selectedCard
         produce fileNames to send to gui
         result[0] == 0: when the AI wants to discard the card that was drawn
         result[0] == 2: when the AI wants uses a peek card, effectly does nothing
         */
        if (result[0] == 0 || result[0] == 2) {
            data.dp.push(selectedCard);
            System.out.println("Pushed a card to discard pile");
            System.out.println("Discard pile is now size:" + data.dp.size());
            fileName = selectedCard.getCard();
            if (result[0]== 0) {
                usedCard = false;
            }
            data.hands.set(oppNum, oppHand);// Update opponent hand after turn is complete
            gui.opponentTurn(fileName);// Update gui with changes made during turn
        } // result[0] == 1: when the AI wants to exchange a card in its hand with the one drawn
        else if (result[0] == 1) {
            Card toDiscard = oppHand.swap(selectedCard, result[1]);
            data.dp.push(toDiscard);
            System.out.println("Pushed a card to discard pile");
            System.out.println("Discard pile is now size:" + data.dp.size());
            fileName = toDiscard.getCard();
            data.hands.set(oppNum, oppHand);// Update opponent hand after turn is complete
            gui.opponentTurn(fileName);// Update gui with changes made during turn
        } // result[0] == 3: when the AI uses a swap card to swap cards with another player
        else if (result[0] == 3) {
            // Swap cards between opponent and other player
            Hand otherHand = data.hands.get(result[2]);
            Card otherCard = otherHand.swap(oppHand.peek(result[1]), result[3]);
            otherCard = oppHand.swap(otherHand.peek(result[3]), result[1]);
            otherCard = null;
            // Update the hand that the opponent swapped cards with
            data.hands.set(result[2], otherHand);
            fileName = selectedCard.getCard();
            data.hands.set(oppNum, oppHand);// Update opponent hand after turn is complete
            gui.opponentTurn(fileName);// Update gui with changes made during turn
        } // else, say an error occurred
        else if (result[0] == 4) { //Draw 2 loops around
            if(!opponentTurn(oppNum)) {//if the card that is drawn again is not used, then draw another card
                opponentTurn(oppNum);//draw another card
            }
        }
        else {
            System.out.println("Game.java Error: AI result not in the correct format "
                    + "result[0] should be an int from 0 to 3");
        }

        
        
        
        
        return usedCard;
    }// End of opponentTurn()

    protected int[] calculateScores() {

        Card card;
        Hand hand;
        int[] scores = new int[data.hands.size()];

        for (int i = 0; i < data.hands.size(); i++) {

            hand = data.hands.get(i);
            System.out.println(hand);
            // Calculate score for hand
            for (int j = 0; j < 4; j++) {
                card = hand.peek(j);
                // Replace power card in hand with number card
                // as per the official rules

                if(card.getType() != Card.Type.NUMBER) {
                    while (card.getType() != Card.Type.NUMBER) {
                        card = data.deckPop();
                    }
                    card = hand.swap(card, j);

                }
                

            }
            scores[i] = hand.total();
        }

        return scores;

    }// End of calculateScores()

}// End of Game Class
