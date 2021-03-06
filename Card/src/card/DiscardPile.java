 /*
 Created by My Mai
 */
package card;

import java.util.*;

/*
Discard Pile Object implemented using a stack
*/
public class DiscardPile  {

   /*
    creating the stack
    */
   Stack<Card> discardPile = new Stack();
  int size = 0;
   /*
   method for adding a card to the discard pile
   */
   public void push(Card cardDropped){
      discardPile.push(cardDropped);
      size++;
   }
   
   /*
   method for drawing a card from the discard pile
   */
   public Card pop(){
      Card popped;

      if (discardPile.isEmpty()) {
        popped = null;
      }
      else {
        popped = discardPile.pop();
        size--;
      }

      return popped;
   }
   
  /*
   method for peeking at the top card of the discard pile
   */
   public Card peek(){
      Card peekAt = discardPile.peek();
      return peekAt;
   }

   public int size() {
    return size;
   }
   /*
   testing
   */
   public static void main(String args[]){
   
      DiscardPile aPile = new DiscardPile();
      Card b = new Card(2);
      aPile.push(b);
      aPile.peek();
      Card newCard = aPile.pop();
   }//main
   
}//DiscardPile