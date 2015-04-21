/*
 Created by Zach Chay-Dolan
 */
package card;

import java.util.Arrays;

public class Hand {

        private  Card[] cards = new Card[4];

        /*
        constructor for the Hand
        */
        public Hand(Card card1, Card card2, Card card3, Card card4) {
            this.cards[0] = card1;
            this.cards[1] = card2;
            this.cards[2] = card3;
            this.cards[3] = card4;
        }
        /*
        allows you to peek at a card 
        */
        public Card peek(int index) {
            return this.cards[index];
        }

    @Override
    public String toString() {
        return "Hand{" + "cards=" + Arrays.toString(cards) + '}';
    }

        /*
        allows to swap a card NOTE: index is offset by 1
        */
        public Card swap(Card newCard, int index) {
            Card temp = this.cards[index];
            this.cards[index] = newCard;
            return temp;
        }

        /*
        Totals up the card values after replacing all power cards
        */
        public int total() {
            int total = this.cards[0].getNumber();
            total += this.cards[1].getNumber();
            total += this.cards[2].getNumber();
            total += this.cards[3].getNumber();
            return total;
        }

}
