/*
 Created by Zach Chay-Dolan
 */
//package card;

public class Hand extends CardObject{

        private final Card[] cards = new Card[4];

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
            this.replacePowerCards();
            int total = this.cards[0].getNumber();
            total += this.cards[1].getNumber();
            total += this.cards[2].getNumber();
            total += this.cards[3].getNumber();
            return total;
        }

        public static void main(String[] args) {
            Card c = new Card(2);
            Card x = new Card(Type.DRAW2);
            Card d = new Card(8); //second card
            Card e = new Card(Type.PEEK);
            Card f = new Card(Type.SWAP); //first card
            Card g = new Card(9);// third card
            Card h = new Card(1); //fourth card

            Hand a = new Hand(c, d, g, h);
            System.out.println("The total is " + a.total()); //should be 20
            System.out.println("Swapping in a power card");
            a.swap(f, 0);
            System.out.println("The first card is:" + a.peek(0));
            System.out.println("The second card is:" + a.peek(1));
            System.out.println("The third card is:" + a.peek(2));
            System.out.println("The fourth card is:" + a.peek(3));

        }


}
