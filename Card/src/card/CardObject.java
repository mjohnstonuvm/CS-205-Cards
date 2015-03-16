/*
 Created by Matt Johnston
 */
package card;

/*
 Card class that has a type or a number associated with it
 */
public class CardObject {

    //enum of type and number
    public enum Type {

        DRAW2, PEEK, SWAP, NUMBER
    }

    /*
     Card class gets the type and the number
     */
    private static class Card {

        private final Type type;
        private int number = -1; //number is default -1 if type is not a number

        /*
         Constructor to set the type
         */
        public Card(Type type) {
            this.type = type;
        }

        /*
         Constructor to set the number
         */
        public Card(int number) {
            this.number = number;
            type = Type.NUMBER;
        }

        /*
        Returns type of card
        */
        public Type getType() {
            return this.type;
        }

        /*
        Returns number with the card
        -1 if the Type != NUMBER
        */
        public int getNumber() {
            return this.number;
        }
    }

    public static void main(String[] args) {
        Card c = new Card(2);
        Card x = new Card(Type.DRAW2);
        Card d = new Card(8);
        Card e = new Card(Type.PEEK);
        Card f = new Card(Type.SWAP);
        
        System.out.println("Card # = " + c.getNumber() + " Type = " + c.getType());
        System.out.println("Card # = " + x.getNumber() + " Type = " + x.getType());
        System.out.println("Card # = " + d.getNumber() + " Type = " + d.getType());
        System.out.println("Card # = " + e.getNumber() + " Type = " + e.getType());
        System.out.println("Card # = " + f.getNumber() + " Type = " + f.getType());
        

    }

}
