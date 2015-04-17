/*
 Created by Matt Johnston
 */
package card;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Class to represent card game as a UI
 */
public final class GuiClassTest extends CardObject {

    //variables
    private final JMenu fileMenu, helpMenu;
    private final JPanel gamePanel;
    private final JFrame mainFrame;
    private JButton[] ButtonsArray;
    JPanel userPanel, opponent1, opponent2, opponent3,
            deckPanel, discardPanel, deckandDiscard;
    public int roundCount, result, numberResult, numClicks;
    public Hand h;
    public JButton showcard;
    public boolean gameState, userTurnDone,aiTurnDone;
    public ArrayList<JButton> temp = new ArrayList<>();
    JButton deckPic, discardPic, newCard;
    Card deckCard, discardCard;
    /*
     displays window 
     */

    public GuiClassTest() {
        this.gamePanel = new JPanel();
        this.mainFrame = new JFrame();
        this.helpMenu = new JMenu("Help");
        this.fileMenu = new JMenu("File");
        //sets window states
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    /*
     calls methods to get game
     */

    public void createCards(int numOfAI, DiscardPile dp) {
        //gets the number of cards to add
        int dimension = (numOfAI + 1) * 4;
        //creates buttons from the number of opponents (4 each)
        ButtonsArray = new JButton[dimension];
        //panels to hold the players cards
        userPanel = new JPanel();
        opponent1 = new JPanel();
        opponent2 = new JPanel();
        opponent3 = new JPanel();
        deckPanel = new JPanel();
        discardPanel = new JPanel();
        deckandDiscard = new JPanel();

        //set layouts
        GridBagConstraints gb = new GridBagConstraints(); //constraints
        gamePanel.setLayout(new BorderLayout()); //MAIN PANEL
        userPanel.setLayout(new GridBagLayout());
        opponent1.setLayout(new GridBagLayout());
        opponent2.setLayout(new GridBagLayout());
        opponent3.setLayout(new GridBagLayout());
        deckandDiscard.setLayout(new GridBagLayout());

        //for loop to deal cards to opponents
        for (int i = 0; i < dimension; i++) {
            //makes a facedown picure button
            JButton HandPic = new JButton(new ImageIcon("deck.png"));
            //adds that button to the userbutton array
            ButtonsArray[i] = HandPic;
            //sets the button to disabled
            ButtonsArray[i].setDisabledIcon(new ImageIcon("deck.png"));
            ButtonsArray[i].setEnabled(false);
            //sets the constraint of buttons using grid layout
            gb.gridx = i;
            gb.gridy = 0;
            //gets the buttons for the user panel
            if (i <= 3) {
                userPanel.add(ButtonsArray[i], gb);
                gamePanel.add(userPanel, BorderLayout.SOUTH);
            }
            //gets teh buttons for the opponnent panel
            if (i >= 4 && i <= 7) {
                opponent1.add(ButtonsArray[i], gb);
                gamePanel.add(opponent1, BorderLayout.NORTH);
            }
            //gets the buttons for the opponent 2 panel
            if (i >= 8 && i <= 11) {
                gb.gridx = 0;
                gb.gridy = i;
                //uses rotated icon class to rotate the card
                RotatedIcon op2 = new RotatedIcon(new ImageIcon("deck.png"), RotatedIcon.Rotate.DOWN);
                ButtonsArray[i] = new JButton(op2);
                opponent2.add(ButtonsArray[i], gb);
                gamePanel.add(opponent2, BorderLayout.EAST);
            }
            //gets the buttons for the opponent 3 panel
            if (i >= 12 && i <= 15) {
                gb.gridx = 0;
                gb.gridy = i;
                RotatedIcon op2 = new RotatedIcon(new ImageIcon("deck.png"), RotatedIcon.Rotate.UP);
                ButtonsArray[i] = new JButton(op2);
                opponent3.add(ButtonsArray[i], gb);
                gamePanel.add(opponent3, BorderLayout.WEST);
            }
            //sets all the cards to false, so you cant click them
            ButtonsArray[i].setEnabled(false);
            //actionlistener for the button
            ButtonsArray[i].addActionListener(new CardsClick());
        }
        //makes a facedown picure button
        deckPic = new JButton(new ImageIcon("deck.png"));
        deckPic.setDisabledIcon(new ImageIcon("deck.png")); //sets enabled icon so picture it is not greyed out
        deckPic.setEnabled(false); //sets deck card enable to false
        deckPic.addActionListener(new deckListener()); //action listener for deck pic
        discardPic = new JButton(new ImageIcon(dp.peek().getCard()));
        discardPic.setDisabledIcon(new ImageIcon(dp.peek().getCard())); //sets enabled icon so picture it is not greyed out
        discardPic.setEnabled(false); //sets discard pile to false
        discardPic.addActionListener(new discardListener()); //action listener for discard pic
        deckPanel.add(deckPic); //adds to the panel
        discardPanel.add(discardPic);
        gb.gridx = 0; //grid layout constraint
        deckandDiscard.add(deckPanel);
        gb.gridx = 1; //grid layout constraint
        deckandDiscard.add(discardPanel); //adds the deck and discard panel 
        gamePanel.add(deckandDiscard, BorderLayout.CENTER); //adds the deck image to the panel
        //adds all panel to the main frame
        mainFrame.add(gamePanel);
        mainFrame.setVisible(true);
    }

    //card listener
    private class CardsClick implements ActionListener {

        public void actionPerformed(ActionEvent d) {
            for (int i = 0; i < ButtonsArray.length; i++) {
                //if first round, peek at outer two cards
                if (roundCount == 0) {
                    //gets outside cards by index
                    if (i == 0 || i == 3) {
                        //new constraint
                        GridBagConstraints gbs = new GridBagConstraints();
                        //x constraint 0 - 3
                        gbs.gridx = i;
                        showcard = new JButton(new ImageIcon(h.peek(i).getCard()));
                        temp.add(showcard);
                        //hide facedown card
                        ButtonsArray[i].setVisible(false);
                        //adds to panel
                        showcard.setLocation(new Point(gbs.gridx, 0));
                        //add card to panel
                        userPanel.add(showcard, gbs);
                        //refresh panel
                        userPanel.revalidate();
                        //timer to flip cards after a certiain # of seconds
                        Timer timer = new Timer(2000, new listener());
                        timer.setRepeats(false);
                        timer.start();
                    }
                }

            }
            //prompts the user to draw from discard pile or deck
            prompt();
        }
    ;

    }
    
    //action listener to flip cards back down
    private class listener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            //turns flipped cards to invisible
            for (JButton b : temp) {
                b.setVisible(false);
            }
            //turns facedown cards to visible
            for (JButton facedown : ButtonsArray) {
                facedown.setVisible(true);
                facedown.setEnabled(false);
            }
        }
    };

    //action listener for deck
    private class deckListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            deckCard = Game.deck.pop(); //takes from deck
            Game.dp.add(deckCard); //adds to discard stack
            discardPanel.removeAll(); //removes cards in panel
            //gets image for discard pile
            newCard = new JButton(new ImageIcon(deckCard.getCard()));
            discardPanel.add(newCard);//add the card to the panel
            discardPanel.revalidate(); //refresh discard panel
            deckPic.setEnabled(false); //sets deck card enable to false
            cardTypePrompt(); //gets prompt from user on what to do with drawn card

            //loops through buttons
            for (int i = 0; i < ButtonsArray.length; i++) {
                //if drawn card is a swap card
                if (numberResult == 1) {
                    int index = i;//copy the index
                    //adds action listner to the button
                    ButtonsArray[i].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            numClicks++; //incremets click count
                            //prevents user from clicking twice
                            if (numClicks < 2) {
                                //swaps the card using game class
                                Game.playerHand.swap(deckCard, index);
                                //prints confirmation to use
                                JOptionPane.showMessageDialog(gamePanel, "Card Swapped!");
                                deckPic.setEnabled(false);//should be false
                            }

                        }
                    });
                }
            }
            //returns game state and finished turn back to the loop
            gameState = true;
            userTurnDone = true;
            Game.run(gameState, userTurnDone,aiTurnDone);
        }
    };

    //action listener for discard
    private class discardListener implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            discardCard = Game.dp.draw(); //takes from deck
            //if stack is empty, remove the panel
            if (Game.dp.discardPile.isEmpty()) {
                discardPanel.setVisible(false);
            } else {
                //gets image for discard pile, from top of discard stack
                newCard = new JButton(new ImageIcon(Game.dp.peek().getCard()));
            }
            discardPanel.revalidate(); //refresh discard panel
            discardPic.setEnabled(false); //sets deck card enable to false
            cardTypePrompt(); //gets prompt from user on what to do with drawn card
            //returns game state back to the loop
            gameState = true;
            userTurnDone = true;
            Game.run(gameState, userTurnDone,aiTurnDone);
        }
    };
    /*
     prompts the user for deck or discard, returns to the gameloop the option
     */

    public void prompt() {
        //deck or discard pile prompt
        String[] optionValues = new String[]{"Draw from Deck", "Draw from Discard Pile"};
        Object selectedValue = JOptionPane.showOptionDialog(null, Game.name
                + " Do you want to draw from the Deck or Discard Pile?", "DRAW", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,
                optionValues, optionValues[0]);
        //result value -- option selected
        result = (Integer) selectedValue;
        //if deck is chosen
        if (result == 0) {
            deckPic.setEnabled(true); //sets deck to enabled    
        } else {
            discardPic.setEnabled(true); //sets discard to enabled
        }

    }
    /*
     gets prompt from user on what to do with drawn card
     */

    public void cardTypePrompt() {
        //gets icon
        ImageIcon icon = new ImageIcon(deckCard.getCard());
        //if its users turn, show the frame
        if (deckCard.getType() == Type.DRAW2) {
            JOptionPane.showMessageDialog(gamePanel, "Draw 2 cards!",
                    "DRAW2 CARD", JOptionPane.PLAIN_MESSAGE, icon);
            //draw2();
        } else if (deckCard.getType() == Type.PEEK) {
            deckPic.setEnabled(true);//should be false
            JOptionPane.showMessageDialog(gamePanel, "Peek at 1 of your cards",
                    "PEEK CARD", JOptionPane.PLAIN_MESSAGE, icon);
            //peek();
        } else if (deckCard.getType() == Type.SWAP) {
            //Swap card
            deckPic.setEnabled(true);//should be false
            //cardSwap();
        } else {
            //number card options
            String[] optionValues = new String[]{"Add to Discard Pile", "Swap with 1 of your cards"};
            Object selectedValue = JOptionPane.showOptionDialog(null, "You drew a number card,"
                    + " do you want to discard it, "
                    + "or swap it with one of yours?", "NUMBER CARD", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, icon,
                    optionValues, optionValues[0]);
            //result value -- option selected
            numberResult = (Integer) selectedValue;
            if (numberResult == 1) {
                //sets buttons to clickable
                for (int i = 0; i < 4; i++) {
                    ButtonsArray[i].setEnabled(true);
                }
            } else {
                Game.dp.add(deckCard);
                discardPanel.add(newCard);//add the card to the panel
                discardPanel.revalidate(); //refresh discard panel
            }
        }
    }
    /*
     users turn prompt 
     */

    public void UserTurn(Hand h, int round) {
        //stores round number in variable
        this.roundCount = round;
        //stores reference to hand object
        this.h = new Hand(h.peek(0), h.peek(1), h.peek(2), h.peek(3));
        //if round is the first round
        if (round == 0) {
            JOptionPane.showMessageDialog(gamePanel, "Welcome, "
                    + Game.name + "!" + " You go first!"
                    + " PEEK at"
                    + " your outer 2 cards!");
            //sets the first and last card to enabled
            ButtonsArray[0].setEnabled(true); //sets to true
            ButtonsArray[3].setEnabled(true); //sets to true

        } else {
            prompt();
        }

    }

    public void opponentTurn(Hand h, boolean action, int opponent) {
        String d; //string for output
        //if true, draw from deck
        if (action == true) {
            d = "deck";
            deckCard = Game.deck.pop(); //pop a card from deck
            int a[] = Game.ai.CardDraw(deckCard); //uses ai function to determine decision
            if (deckCard.getType() == Type.NUMBER) {
                //add to discard pile if first index == 0
                if (a[0] == 0) {
                    Game.dp.add(deckCard);
                    discardPanel.add(newCard);//add the card to the panel
                    discardPanel.revalidate(); //refresh discard panel
                } else {
                    //swap it using the 2nd index
                    Game.aiHand.swap(deckCard, a[1]);
                }
            } else if (deckCard.getType() == Type.PEEK) {
                //add to discard pile if first index == 0
                if (a[0] == 0) {
                    Game.dp.add(deckCard);
                    discardPanel.add(newCard);//add the card to the panel
                    discardPanel.revalidate(); //refresh discard panel
                } else {
                    //swap it using the 2nd index
                    Game.aiHand.peek(a[1]);
                }
            } else {
                //add to discard pile if first index == 0
                if (a[0] == 0) {
                    Game.dp.add(deckCard);
                    discardPanel.add(newCard);//add the card to the panel
                    discardPanel.revalidate(); //refresh discard panel
                } else {

                }
            }

        } else {
            d = "discard";
        }

        JOptionPane.showMessageDialog(gamePanel, "It is opponent " + opponent
                + "'s turn, they are drawing from the " + d + " pile");
        gameState = true;
        aiTurnDone = true;
        userTurnDone = false;
        Game.run(gameState, userTurnDone,aiTurnDone);
    }

}
