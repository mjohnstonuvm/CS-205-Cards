/*
 Created by Matt Johnston
 */
package card;

/*
 Card class that has a type and a number associated with it
*/
public class Card {

    //enum of type and number
    public enum Type {DRAW2,PEEK,SWAP,NUMBER}

    class CardObject{
        Type type;
        
        public CardObject(Type type){
        this.type = type;
        }
        
    //will return card obejct
    public void getType(){
        
        switch(type){
            
            case DRAW2:
                break;
            case PEEK:
                break;
            case SWAP:
                break;
            case NUMBER:
        }
    }
    
    //will return card obejct
    public void getNumber(){
        
    }
    }
    public static void main(String[]args){
        
    }
    
}
