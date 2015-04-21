/*
 Created by Matt Johnston
 */
package card;

/*
 Card class that has a type or a number associated with it
 */
/*
 Card class gets the type and the number
 */
public class Card {

    //enum of type and number
    public enum Type {

        DRAW2, PEEK, SWAP, NUMBER
    }

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

    /*
     Returns card from the string image file
     */
    public String getCard() {
        String path;
        if (type == Type.NUMBER) {
            path = Integer.toString(this.number) + ".png";
        } else {
            path = this.type.toString() + ".png";
        }
        return path;
    }

    @Override
    public String toString() {
        if (number != -1) {
            return " type = " + type + ", Number = " + number;
        }
        return " type = " + type;
    }

}
