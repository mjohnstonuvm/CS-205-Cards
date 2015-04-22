/**********************************
        CALLBACK VERISON
**********************************/

package card;

import java.util.ArrayList;

public class Game {

    protected ArrayList<Player> players = new ArrayList<>();
    protected int playerIndex;
    protected int roundCount = 0;
    protected String endCondition, difficulty, playerName;
    protected Player currentPlayer;
    public GameData data;

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

        players.add(new Player());

        for (int i = 0; i < numOfAI; i++) {
            if (difficulty == "Easy") {
                players.add(new Player(new EasyAI(numOfAI, i)));
            } else if (difficulty == "Medium") {
                players.add(new Player(new MediumAI(numOfAI, hands, i)));
            } else {
                players.add(new Player(new HardAI(numOfAI, hands, i)));
            }
        }

        playerIndex = 0;

        turn();

    }// End of Game constructor

    public void endGame() {

        boolean win = true;
        Card card;
        Hand hand;
        int[] scores = new int[data.hands.size()];

        for (int i = 0; i < data.hands.size(); i++) {

            hand = data.hands.get(i);

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

        for (int i = 1; i < scores.length; i++) {
            if (scores[i] > scores[0]) {
                win = false;
            }
        }

        // End game statistics screen
        //endScreen = new EndGame(playerName, difficulty, scores[0], win);

    }// End of endGame()

    public void endTurn() {
        int nextPlayerIndex = (playerIndex + 1) % players.size();

        if (nextPlayerIndex == 0) {
            roundCount++;
        }

        currentPlayer = players.get(nextPlayerIndex);
        if (roundCount < 10) {
            endGame();
        }
        else {
            turn();
        }
    }// End of endTurn()

    public void turn() {
        data = currentPlayer.run(data, playerIndex);
        if (!currentPlayer.isHuman) {
            endTurn();
        }
    }// End of turn()

}// End of Game Class
