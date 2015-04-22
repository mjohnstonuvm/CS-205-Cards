package card;

public class Player {

	public boolean isHuman;
	protected AI ai;

	public Player() {
		isHuman = true;
	}// End of Constructor 1

	public Player(AI ai) {
		isHuman = false;
		this.ai = ai;
	}// End of Constructor 2

	public GameData run(GameData data, playerIndex) {

		if (isHuman) {
			return;
		}
		else {
			opponentTurn(data, playerIndex);
		}

		return data;

	}

	protected void opponentTurn(GameData data, playerIndex) {

		Hand oppHand = data.hands.get(playerIndex)
        boolean drawDecision;
        int[] result;
        Card selectedCard = data.dp.pop(); //draws from discard pile

        ai.update(data.hands);
        drawDecision = ai.drawOrDiscard(selectedCard); //returns action

        if (drawDecision) {
            selectedCard = data.deckPop();
            while (selectedCard.getType() == Card.Type.DRAW2) {
                data.dp.push(selectedCard);
                selectedCard = data.deckPop();
            }
            result = ai.cardDraw(selectedCard);
        } else {
            result = ai.cardDraw(selectedCard);
        }

        /*
         update model with result and selectedCard
         produce fileNames to send to gui
         result[0] == 0: when the AI wants to discard the card that was drawn
         result[0] == 2: when the AI wants uses a peek card, effectly does nothing
         */
        if (result[0] == 0 || result[0] == 2) {
            data.dp.push(selectedCard);
        } // result[0] == 1: when the AI wants to exchange a card in its hand with the one drawn
        else if (result[0] == 1) {
            Card toDiscard = oppHand.swap(selectedCard, result[1]);
            data.dp.push(toDiscard);
        } // result[0] == 3: when the AI uses a swap card to swap cards with another player
        else if (result[0] == 3) {
            // Swap cards between opponent and other player
            Hand otherHand = data.hands.get(result[2]);
            Card otherCard = otherHand.swap(oppHand.peek(result[1]), result[3]);
            otherCard = oppHand.swap(otherHand.peek(result[3]), result[1]);
            otherCard = null;
            // Update the hand that the opponent swapped cards with
            data.hands.set(result[2], otherHand);
        } // else, say an error occurred
        else {
            System.out.println("Player.java Error: AI result not in the correct format "
                    + "result[0] should be an int from 0 to 3");
        }

        // Update opponent hand after turn is complete
        data.hands.set(oppNum, oppHand);

        return data;

    }// End of opponentTurn()
	
}// End of Player Class