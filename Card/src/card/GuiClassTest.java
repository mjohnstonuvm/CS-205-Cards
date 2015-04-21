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
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Class to represent card game as a UI
 */
public final class GuiClassTest {

    //variables
    private JMenu fileMenu, helpMenu;
    private JPanel gamePanel;
    private JFrame mainFrame;
    private JButton[] ButtonsArray;
    private JPanel userPanel, opponent1, opponent2, opponent3,
            deckPanel, discardPanel, deckandDiscard;
    private JButton deckPic, discardPic, cardToShow, cardToShow2;
    private boolean AddToDiscard = false;
    private volatile boolean userAction = false;
    private Card cardInDiscardPile, cardToDiscard;
    private Timer timer;
    private JLabel youLabel, opponent1Label, opponent2Label, opponent3Label;
    private boolean drawnCardUsed = false;
    protected GameData data;
    protected Deck deck;
    protected DiscardPile dp;
    protected String playerName;
    protected ArrayList<Hand> hands;
    protected int numOfAI, result;

    /*
     Constructor to assign GameData 
     */
    public GuiClassTest(GameData data) {
        this.gamePanel = new JPanel();
        this.mainFrame = new JFrame();
        this.helpMenu = new JMenu("Help");
        this.fileMenu = new JMenu("File");
        this.data = data;
    }
    /*
     Creates facedown cards on the game panel
     */

    public void createCards(int numOfAI, DiscardPile dp) {
        this.numOfAI = numOfAI;
        //sets window states
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
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
                //add buttons to panel
                userPanel.add(ButtonsArray[i], gb);
                //add panel to the game panel
                gamePanel.add(userPanel, BorderLayout.SOUTH);
            }
            //gets teh buttons for the opponnent panel
            if (i >= 4 && i <= 7) {
                //add buttons to panel
                opponent1.add(ButtonsArray[i], gb);
                //add panel to the game panel
                gamePanel.add(opponent1, BorderLayout.NORTH);
            }
            //gets the buttons for the opponent 2 panel
            if (i >= 8 && i <= 11) {
                gb.gridx = 0; //constraints
                gb.gridy = i; //constraints
                //uses rotated icon class to rotate the card
                RotatedIcon op2 = new RotatedIcon(new ImageIcon("deck.png"), RotatedIcon.Rotate.DOWN);
                ButtonsArray[i] = new JButton(op2); //create new button
                opponent2.add(ButtonsArray[i], gb); //add button to panel
                gamePanel.add(opponent2, BorderLayout.WEST); //add to game panel
            }
            //gets the buttons for the opponent 3 panel
            if (i >= 12 && i <= 15) {
                gb.gridx = 0;
                gb.gridy = i;
                RotatedIcon op2 = new RotatedIcon(new ImageIcon("deck.png"), RotatedIcon.Rotate.UP);
                ButtonsArray[i] = new JButton(op2);
                opponent3.add(ButtonsArray[i], gb);
                gamePanel.add(opponent3, BorderLayout.EAST);
            }
            //sets all the cards to false, so you cant click them
            ButtonsArray[i].setEnabled(false);
        }
        //makes a facedown picure button
        deckPic = new JButton(new ImageIcon("deck.png"));
        deckPic.setDisabledIcon(new ImageIcon("deck.png")); //sets enabled icon so picture it is not greyed out
        deckPic.setEnabled(false); //sets deck card enable to false
        discardPic = new JButton(new ImageIcon(dp.peek().getCard()));
        discardPic.setDisabledIcon(new ImageIcon(dp.peek().getCard())); //sets enabled icon so picture it is not greyed out
        discardPic.setEnabled(false); //sets discard pile to false
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
    /*
     method that lets the user peek at their 2 outer cards 
     */

    public void userInitialPeek(GameData data) {
        this.data = data;
        //deck or discard pile prompt
        String[] optionValues = new String[]{"Ok"};
        Object selectedValue = JOptionPane.showOptionDialog(null, "Welcome " + data.playerName
                + "! Peek at your 2 outer cards to begin your turn.", "Your First Turn", JOptionPane.DEFAULT_OPTION,
                JOptionPane.OK_OPTION, null,
                optionValues, optionValues[0]);
        //result value -- option selected
        int result = (Integer) selectedValue;
        if (result == 0) {
            //hands.get(0) is the users hand
            Card cardToPeek = data.hands.get(0).peek(0);
            Card cardToPeek2 = data.hands.get(0).peek(3);
            //new constraint
            GridBagConstraints gbs = new GridBagConstraints();
            //creates a new button of the peek card
            cardToShow = new JButton(new ImageIcon(cardToPeek.getCard()));
            cardToShow2 = new JButton(new ImageIcon(cardToPeek2.getCard()));
            //hide facedown card
            ButtonsArray[0].setVisible(false);
            ButtonsArray[3].setVisible(false);
            //sets the location of the new card to show to the user panel
            gbs.gridx = 0;
            cardToShow.setLocation(new Point(gbs.gridx, 0));
            //add the card to panel
            userPanel.add(cardToShow, gbs);
            gbs.gridx = 3;
            cardToShow2.setLocation(new Point(gbs.gridx, 0));
            //add the card to panel
            userPanel.add(cardToShow2, gbs);
            //refresh panel
            userPanel.revalidate();
            //timer to flip cards after a certiain # of seconds
            timer = new Timer(3000, new flipCardsBack());
            timer.setRepeats(false);
            timer.start();

        }
    }

    public void userTurn() {

        //deck or discard pile prompt
        String[] optionValues = new String[]{"Draw from Deck", "Draw from Discard Pile"};
        Object selectedValue = JOptionPane.showOptionDialog(null, data.playerName
                + ", do you want to draw from the DECK or DISCARD Pile?", "DRAW", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,
                optionValues, optionValues[0]);
        //result value -- option selected
        result = (Integer) selectedValue;
        method();
        //pauses the game for a certain # of seconds
        //data is defined in waitTimer method

    }// End of userTurn()

    public GameData method() {
        //IF DRAW FROM DECK IS CHOSEN
        if (result == 0) {
            deckPic.setEnabled(true); //sets deck to enabled  
            //deck listener that performs a different action depending on type of card
            deckPic.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //pop card from the deck
                    Card deckCard = data.deckPop();
                    //gets icon of the drawn card
                    ImageIcon icon = new ImageIcon(deckCard.getCard());
                    String[] optionValues = {}; //hodls the options for the specific card
                    Object selectedValue; //selected value from prompt

                    //if a number card is drawn
                    //user can either swap the card with 1 from their hand, 
                    //then discard the swaped card
                    //OR chose to discard the drawn card
                    if (deckCard.getType() == Card.Type.NUMBER) {
                        //number card options
                        optionValues = new String[]{"Add to Discard Pile", "Swap with 1 of your cards"};
                        selectedValue = JOptionPane.showOptionDialog(null, "You drew a number card,"
                                + " do you want to discard it, "
                                + "or swap it with one of yours?", "NUMBER CARD", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE, icon,
                                optionValues, optionValues[0]);
                        //if the user chose to add the card to discard pile
                        if ((Integer) selectedValue == 0) {
                            cardToDiscard = deckCard;
                            AddToDiscard = true;
                        } else {
                            //boolean is true if number card is used
                            drawnCardUsed = true;
                            //peform swap using swapWithyourHand function
                            //returns the global variable with the card to discard
                            swapWithYourHand(deckCard);
                        }
                    } //end if number card is drawn
                    //if a peek card is drawn
                    //user can peek at any of their cards
                    else if (deckCard.getType() == Card.Type.PEEK) {
                        //peek card options
                        optionValues = new String[]{"Add to Discard Pile", "Peek 1 of your cards"};
                        selectedValue = JOptionPane.showOptionDialog(null, "You drew a peek card,"
                                + " do you want to discard it, "
                                + "or peek at one of your cards?", "PEEK CARD", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE, icon,
                                optionValues, optionValues[0]);
                        //if the user chose to peek a card
                        if ((Integer) selectedValue == 1) {
                            //boolean is true if peek card is used
                            drawnCardUsed = true;
                            //uses peek function to show card to user
                            peek(deckCard);
                        }
                        //peek card goes to discard pile 
                        cardToDiscard = deckCard;
                        AddToDiscard = true;
                    } //end else if peek
                    //if draw 2 card is drawn
                    //the user can either choose to use the first drawn card,
                    //OR discard that card and draw another one
                    //if either of the drawn cards are power cards, then use their function
                    //if it is a draw2 card, then user has the option to draw 2 more
                    else if (deckCard.getType() == Card.Type.DRAW2) {
                        //draw2 card options
                        optionValues = new String[]{"Add to Discard Pile", "Draw 2 more cards"};
                        selectedValue = JOptionPane.showOptionDialog(null, "You drew a draw 2 card,"
                                + " do you want to discard it, "
                                + "or draw 2 more cards?", "DRAW 2 CARD", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE, icon,
                                optionValues, optionValues[0]);
                        //if the user chose to draw 2 more cards
                        if ((Integer) selectedValue == 1) {
                            //passes the swap card to be discarded as first param
                            draw2(deckCard);
                        } else {
                            //user decides to discard the draw 2 card, goes to discard pile 
                            cardToDiscard = deckCard;
                            AddToDiscard = true;
                        }
                    } //end draw 2 else if
                    //if a swap card is drawn
                    //the user can swap a card from their hand with any opponent card
                    //user CANNOT see the cards they are swapping
                    //swap card goes to disard pile
                    else {
                        //swap card options
                        if (numOfAI == 1) {
                            optionValues = new String[]{"Add to Discard Pile", "Opponent 1"};
                        } else if (numOfAI == 2) {
                            optionValues = new String[]{"Add to Discard Pile", "Opponent 1", "Opponent 2"};
                        } else {
                            optionValues = new String[]{"Add to Discard Pile", "Opponent 1", "Opponent 2", "Opponent 3"};
                        }
                        //user selected value
                        selectedValue = JOptionPane.showOptionDialog(null, "You drew a swap card,"
                                + " do you want to discard it, "
                                + "or swap it with one of your oppoents cards?", "SWAP CARD", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE, icon,
                                optionValues, optionValues[0]);
                        //if the user chose to swap a card
                        if ((Integer) selectedValue != 0) {
                            //boolean is true if swap card is used
                            drawnCardUsed = true;
                            //uses swap function to swap a card from your hand with your opponents
                            swapWithOpponent((Integer) selectedValue);
                        }
                        //swap card goes to discard pile 
                        cardToDiscard = deckCard;
                        AddToDiscard = true;
                    } // end swap card drawn else statement

                    //once the function has been completed, add the card to discard pile
                    while (AddToDiscard) {
                        //creates a discarded card from the cardTodiscard given from each function
                        JButton discardCard = new JButton(new ImageIcon(cardToDiscard.getCard()));
                        discardPanel.removeAll();
                        discardPanel.add(discardCard);
                        AddToDiscard = false;
                    }
                    discardPanel.revalidate();
                    deckPic.setEnabled(false); //sets deck to disabled
                    discardPic.setEnabled(false); //sets discard to disabled

                }
            }); //action listener for deck pic

            //DRAW FROM DISCARD IS CHOSEN
        } else {
            discardPic.setEnabled(true); //sets discard to enabled
            //action listener that performs a swap with a card from the user hand
            //with the discard pile card.
            //card from hand is discarded
            discardPic.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cardInDiscardPile = data.dp.pop(); //takes from discard pile
                    //prompt user, "Click on one of your cards to swap with"
                    JOptionPane.showMessageDialog(gamePanel, "Click on one of your cards to swap this card with",
                            "SWAP WITH YOURS", JOptionPane.PLAIN_MESSAGE);
                    //peform swap using swapWithyourHand function
                    //returns the global variable with the card to discard
                    swapWithYourHand(cardInDiscardPile);
                    //discard the swapped card to the discard pile
                    //creates a discarded card from the cardTodiscard given from the cardToDiscard global variable
                    while (AddToDiscard) {
                        JButton discardCard = new JButton(new ImageIcon(cardToDiscard.getCard()));
                        discardPanel.removeAll();
                        discardPanel.add(discardCard);
                        AddToDiscard = false;
                    }
                    discardPanel.revalidate();
                    discardPic.setEnabled(false); //sets discard to disabled
                }

            }); //action listener for discard pic

        }//end else
        waitTimer();
        return data;
    }
    /*
     Method to perform swap using the cardToSwapIn to swap in your OWN hand
     */

    public void swapWithYourHand(Card cardToSwapIn) {
        //loop through user buttons to set them to clickable/enabled
        //user buttons are index  0 - 3
        for (int i = 0; i < 4; i++) {
            //sets buttons to enabled
            ButtonsArray[i].setEnabled(true);
            //saves the index so it can be accessed in the aciton listener
            int index = i;
            //action listener for each enabled button
            ButtonsArray[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //swap function from Hand.Java returns the card that is being discarded
                    //hands.get(0) is the users hand
                    //saves the card into the global variable cardToDiscard
                    //it can be accessed in the UserTurn function
                    Card cardToDiscard = data.hands.get(0).swap(cardToSwapIn, index);
                    //creates a discarded card from the cardTodiscard given from each function
                    JButton discardCard = new JButton(new ImageIcon(cardToDiscard.getCard()));
                    discardPanel.removeAll();
                    discardPanel.add(discardCard);
                    discardPanel.revalidate();
                    //sets all the buttons to diabled
                    for (int i = 0; i < 4; i++) {
                        //sets buttons to enabled
                        ButtonsArray[i].setEnabled(false);
                    }

                }
            }); //end of action listener
        }// end of for loop

    } //end of swapFromDiscardPile

    /*
     Method to perform peek on your OWN hand
     */
    public void peek(Card cardToDiscard) {
        //loop through user buttons to set them to clickable/enabled
        //user buttons are index  0 - 3
        for (int i = 0; i < 4; i++) {
            //sets buttons to enabled
            ButtonsArray[i].setEnabled(true);
            //saves the index so it can be accessed in the aciton listener
            final int index = i;
            //action listener for each enabled button
            ButtonsArray[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //hands.get(0) is the users hand
                    Card cardToPeek = data.hands.get(0).peek(index);
                    //new constraint
                    GridBagConstraints gbs = new GridBagConstraints();
                    //x constraint 0 - 3
                    gbs.gridx = index;
                    //creates a new button of the peek card
                    cardToShow = new JButton(new ImageIcon(cardToPeek.getCard()));
                    //hide facedown card
                    ButtonsArray[index].setVisible(false);
                    //sets the location of the new card to show to the user panel
                    cardToShow.setLocation(new Point(gbs.gridx, 0));
                    //add the card to panel
                    userPanel.add(cardToShow, gbs);
                    //refresh panel
                    userPanel.revalidate();
                    //timer to flip cards after a certiain # of seconds
                    timer = new Timer(3000, new flipCardsBack());
                    timer.setRepeats(false);
                    timer.start();
                    AddToDiscard = false;
                }
            }); //end of action listener

        }// end of for loop

    }//END PEEK FUNCTION

    /*
     action listener to flip cards back down if a peek card has been used
     */
    private class flipCardsBack implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            //turns flipped cards to invisible
            for (int i = 0; i < 4; i++) {
                cardToShow2.setVisible(false);
                cardToShow.setVisible(false);
                ButtonsArray[i].setVisible(true);
                ButtonsArray[i].setEnabled(false);
            }
        }
    }; //END FlipCardsBack listener

    /*
     Method to click on users hand and then pass the card to the opponents hand
     using swapComplete function
     */
    public void swapWithOpponent(int selectedOpponent) {
        //loop through user buttons to set them to clickable/enabled
        //user buttons are index  0 - 3
        for (int i = 0; i < 4; i++) {
            //sets buttons to enabled
            ButtonsArray[i].setEnabled(true);
            //saves the index so it can be accessed in the aciton listener
            int userCardIndex = i;
            //action listener for each enabled button
            ButtonsArray[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //hands.get(0) is the users hand
                    Card userCardToSwap = data.hands.get(0).peek(userCardIndex);
                    swapComplete(userCardToSwap, userCardIndex, selectedOpponent);
                    //sets all the buttons to diabled
                    for (int i = 0; i < 4; i++) {
                        //sets buttons to enabled
                        ButtonsArray[i].setEnabled(false);
                    }
                    AddToDiscard = false;
                }
            });
        }

    } //end swapWithOpponent function

    /*
     Method to perform swap from your hand to your opponents
     */
    public void swapComplete(Card userCardToSwap, int userCardIndex, int selectedOpponent) {
        //finds the cards corresponding the the opponent that the user selected
        //last card in opponents hand
        int lastOpponentCard;
        //Opponent 1 last card index
        if (selectedOpponent == 1) {
            lastOpponentCard = 7;
            //Opponent 2 last card index
        } else if (selectedOpponent == 2) {
            lastOpponentCard = 11;
            //Opponent 3 last card index
        } else {
            lastOpponentCard = 15;
        }
        //loop through opponents buttons
        for (int i = lastOpponentCard - 3; i < lastOpponentCard; i++) {
            //sets buttons to enabled
            ButtonsArray[i].setEnabled(true);
            //saves the index so it can be accessed in the action listener
            int opponentCardIndex = i - 4;
            ButtonsArray[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //swaps the card to the opponents hand, 
                    //function returns the card to put into users hand
                    Card cardToGiveUser = data.hands.get(selectedOpponent).swap(userCardToSwap, opponentCardIndex);
                    //swaps to the user hand
                    data.hands.get(0).swap(cardToGiveUser, userCardIndex);
                    //sets all the buttons to diabled
                    for (int i = lastOpponentCard - 3; i < lastOpponentCard; i++) {
                        //sets buttons to enabled
                        ButtonsArray[i].setEnabled(false);
                    }
                    AddToDiscard = false;
                }
            }); //end of action listener

        }// end of for loop

    }//END OF SwapComplete function

    /*
     This method perfoms the draw 2 function passes in the draw 2 
     card to be discarded as param, and array of cards
     */
    public void draw2(Card cardDiscarded) {
        //uses draw 2 function to draw 2 more cards
        //discard the drawn draw 2 card
        JButton discardCard = new JButton(new ImageIcon(cardDiscarded.getCard()));
        discardPanel.removeAll();
        discardPanel.add(discardCard);
        discardPanel.revalidate();

        //while drawnCard is not used
        while (drawnCardUsed == false) {
            //set the deck to clickable 
            deckPic.setEnabled(true);

        }//end while loop
    }//end DRAW 2 FUNCTION

    /*
     This method slows down the game so it is not instant 
     */
    public void waitTimer() {
        //timer to flip cards after a certiain # of seconds
        timer = new Timer(3000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //creates an instance of GameData.java, stores the updated parameters in "data"
                //data = new GameData(deck, dp, hands, playerName, numOfAI);
            }
        });
        timer.start();
    }
    /*
     Opponent turn. shows the discarded card in the game panel
     */

    public void opponentTurn(String fileName) {
        // fileName is card to discard
        JButton discard = new JButton(new ImageIcon(fileName));
        discardPanel.removeAll();
        discardPanel.add(discard);
        discardPanel.revalidate();
        //pauses the game for a certain # of seconds
        waitTimer();
    }

}
