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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Class to represent card game as a UI
 */
public final class Game extends CardObject {

    //instances of classes
    PlayClip c;
    Card card, deckCard;
    DiscardPile discardClass = new DiscardPile();
    Hand userHand, op1Hand, op2Hand, op3Hand;
    //variables
    private int op, numTurns = 0, numClicks = 0, numDeckClicks = 1, turn = 0, numDraw2 = 2, result;
    private String diff, end,name;
    private boolean buttonState = true, gameState = true, drawAgain = true;
    private final JMenu fileMenu, helpMenu;
    private final JPanel gamePanel;
    private final JFrame mainFrame;
    private final Deck deck;
    private final Card[] UserHandArray = new Card[4];
    private final Card[] op1HandArray = new Card[4];
    private final Card[] op2HandArray = new Card[4];
    private final Card[] op3HandArray = new Card[4];
    private final JButton[] userButtonsArray = new JButton[4];
    private final JButton[] op1ButtonsArray = new JButton[4];
    private final JButton[] op2ButtonsArray = new JButton[4];
    private final JButton[] op3ButtonsArray = new JButton[4];
    private JButton newCard, deckPic;
    /*
     displays window 
     */

    public Game() {
        this.deck = new Deck();
        this.gamePanel = new JPanel();
        this.mainFrame = new JFrame();
        this.helpMenu = new JMenu("Help");
        this.fileMenu = new JMenu("File");
        StartMenu s = new StartMenu();
        s.getStartMenu();
    }
    /*
     Constructor to display game with required parameters
     */

    public Game(int op, String diff, String end,String name) {
        this.deck = new Deck();
        this.gamePanel = new JPanel();
        this.mainFrame = new JFrame();
        this.helpMenu = new JMenu("Help");
        this.fileMenu = new JMenu("File");
        this.op = op;
        this.diff = diff;
        this.end = end;
        this.name = name;
        getGame();
    }
    /*
     calls methods to get game
     */

    public void getGame() {
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //builds menu bar
        buildMenuBar();
        //gets game options
        getOptions();
        //deals cards to players
        deal();
        //gets the deck
        getDeck();
        //loop
        loop(0);

    }

    public void getOptions() {
        JPanel pan = new JPanel();
        if (end.equalsIgnoreCase("Time")) {
           
        }
    }

    /*
     Starts game and sets panel to the number of opponents
     */
    public void deal() {
        //panels to hold the players cards
        JPanel userPanel = new JPanel();
        JPanel opponent1 = new JPanel();
        JPanel opponent2 = new JPanel();
        JPanel opponent3 = new JPanel();

        //set layouts
        GridBagConstraints gb = new GridBagConstraints(); //constraints
        gamePanel.setLayout(new BorderLayout()); //MAIN PANEL
        userPanel.setLayout(new GridBagLayout());
        opponent1.setLayout(new GridBagLayout());
        opponent2.setLayout(new GridBagLayout());
        opponent3.setLayout(new GridBagLayout());

        //for loop to deal cards to opponents
        for (int i = 0; i < 4; i++) {
            //stores index
            int index = i;
            //pops card from deck
            card = deck.pop();
            //adds card to the userhandarray
            UserHandArray[i] = card;
            //makes a facedown picure button
            JButton userHandPic = new JButton(new ImageIcon("deck.png"));
            //adds that button to the userbutton array
            userButtonsArray[i] = userHandPic;
            //sets the button to disabled
            userButtonsArray[i].setDisabledIcon(new ImageIcon("deck.png"));
            userButtonsArray[i].setEnabled(false);
            //sets the constraint of buttons using grid layout
            gb.gridx = i;
            gb.gridy = 0;
            //actionlistener for the button
            userButtonsArray[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ex) {
                    //new constraint
                    GridBagConstraints gbs = new GridBagConstraints();
                    //x constraint 0 - 3
                    gbs.gridx = index;
                    //if first turn, flip 2 outside cards to peek
                    if (numTurns == 1 || deckCard.getType() == Type.PEEK) {
                        JButton showcard = new JButton(new ImageIcon(UserHandArray[index].getCard()));
                        //hide facedown card
                        userButtonsArray[index].setVisible(false);
                        //adds to panel
                        showcard.setLocation(new Point(gbs.gridx, 0));
                        userPanel.add(showcard, gbs);
                        //actionlistener for faceup card
                        showcard.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent ex) {
                                numClicks++;//increment number of clicks
                                showcard.setVisible(false);
                                userButtonsArray[index].setVisible(true);
                                if (deckCard.getType() == Type.PEEK && numClicks == 1) {
                                    //makes all the buttons disabled
                                    buttonState = false;
                                    loop(0);
                                }
                                if (numTurns == 1 && numClicks == 2) {
                                    JButton temp = (JButton) ex.getSource();
                                    for (JButton b : userButtonsArray) {
                                        if (b == userButtonsArray[index]) {
                                            if (temp != b) {
                                                buttonState = false;
                                                loop(0);
                                            }
                                        }
                                    }
                                }
                            }
                        });
                        //sets to 0
                        if (numTurns > 1) {
                            numClicks = 0;
                        }

                        //refresh panel
                        userPanel.revalidate();
                    }
                }
            });
            //add the images to the panels
            userPanel.add(userHandPic, gb);

            //pops card from deck
            card = deck.pop();
            //adds card to the op1handarray
            op1HandArray[i] = card;
            //makes a facedown picture button
            JButton op1HandPic = new JButton(new ImageIcon("deck.png"));
            //adds that  button to the op1buttonarray
            op1ButtonsArray[i] = op1HandPic;
            //sets the button to disabled
            op1ButtonsArray[i].setDisabledIcon(new ImageIcon("deck.png"));
            op1ButtonsArray[i].setEnabled(false);
            //add the images to the panels
            opponent1.add(op1HandPic, gb);

            //if opponent number == 2 or 3
            if (op == 2 || op == 3) {
                //pops card from deck
                card = deck.pop();
                //adds card to the op1handarray
                op2HandArray[i] = card;
                //sts the card to the image icon
                //makes a facedown picture button
                RotatedIcon op2 = new RotatedIcon(new ImageIcon("deck.png"), RotatedIcon.Rotate.DOWN);
                JButton op2HandPic = new JButton(op2);
                //adds that  button to the op1buttonarray
                op2ButtonsArray[i] = op2HandPic;
                //sets the button to disabled
                op2ButtonsArray[i].setDisabledIcon(op2);
                op2ButtonsArray[i].setEnabled(false);
                gb.gridx = 0;
                gb.gridy = i;
                //add the images to the panels
                opponent2.add(op2HandPic, gb);
            }
            if (op == 3) {
                //pops card from deck
                card = deck.pop();
                //adds card to the op1handarray
                op3HandArray[i] = card;
                //makes a facedown picture button
                RotatedIcon op3 = new RotatedIcon(new ImageIcon("deck.png"), RotatedIcon.Rotate.UP);
                JButton op3HandPic = new JButton(op3);
                //adds that  button to the op1buttonarray
                op3ButtonsArray[i] = op3HandPic;
                //sets the button to disabled
                op3ButtonsArray[i].setDisabledIcon(op3);
                op3ButtonsArray[i].setEnabled(false);
                //add the images to the panels
                opponent3.add(op3HandPic, gb);
            }

        }
        //adds cards to make a hand
        userHand = new Hand(UserHandArray[0], UserHandArray[1], UserHandArray[2], UserHandArray[3]);
        System.out.println("User " + userHand.peek(0));
        System.out.println("User " + userHand.peek(1));
        System.out.println("User " + userHand.peek(2));
        System.out.println("User " + userHand.peek(3));
        op1Hand = new Hand(op1HandArray[0], op1HandArray[1], op1HandArray[2], op1HandArray[3]);
        System.out.println("Opponent 1 " + op1Hand.peek(0));
        System.out.println("Opponent 1 " + op1Hand.peek(1));
        System.out.println("Opponent 1 " + op1Hand.peek(2));
        System.out.println("Opponent 1 " + op1Hand.peek(3));
        //add a single opponent as default to the game Panel
        gamePanel.add(userPanel, BorderLayout.PAGE_END);
        gamePanel.add(opponent1, BorderLayout.PAGE_START);

        //if num of opponents is more than 1, add the other panel
        if (op == 2 || op == 3) {
            op2Hand = new Hand(op2HandArray[0], op2HandArray[1], op2HandArray[2], op2HandArray[3]);
            System.out.println("Opponent 2 " + op2Hand.peek(0));
            System.out.println("Opponent 2 " + op2Hand.peek(1));
            System.out.println("Opponent 2 " + op2Hand.peek(2));
            System.out.println("Opponent 2 " + op2Hand.peek(3));
            gamePanel.add(opponent2, BorderLayout.LINE_START);
        }
        if (op == 3) {
            op3Hand = new Hand(op3HandArray[0], op3HandArray[1], op3HandArray[2], op3HandArray[3]);
            System.out.println("Opponent 3 " + op3Hand.peek(0));
            System.out.println("Opponent 3 " + op3Hand.peek(1));
            System.out.println("Opponent 3 " + op3Hand.peek(2));
            System.out.println("Opponent 3 " + op3Hand.peek(3));
            gamePanel.add(opponent3, BorderLayout.LINE_END);
        }
    }

    public void getDeck() {
        //Displays the deck and discard image
        JPanel deckPanel = new JPanel();
        JPanel discardPanel = new JPanel();
        JPanel deckandDiscard = new JPanel();

        //get image
        ImageIcon facedown = new ImageIcon("deck.png");
        deckPic = new JButton(facedown);
        //sets the button to disabled
        deckPic.setDisabledIcon(facedown);
        deckPic.setEnabled(false);

        GridBagConstraints gb = new GridBagConstraints(); //constraints
        deckandDiscard.setLayout(new GridBagLayout());

        //gets the first discard card
        deckCard = deck.pop();

        //if card is a power card, add it back to deck and draw again
        while (deckCard.getType() != Type.NUMBER) {
            deck.push(deckCard);
            deckCard = deck.pop();
        }
        //add card to discard pile to begin game
        discardClass.add(deckCard);
        //gets image for discard pile
        JButton discardPic = new JButton(new ImageIcon(discardClass.peek().getCard()));
        //adds the discard and deck pile to the panel
        discardPanel.add(discardPic);
        deckPanel.add(deckPic);
        gb.gridx = 0;
        deckandDiscard.add(deckPanel, gb);
        gb.gridx = 1;
        deckandDiscard.add(discardPanel, gb);
        gamePanel.add(deckandDiscard, BorderLayout.CENTER);
        mainFrame.add(gamePanel);
        mainFrame.setVisible(true);

        //if the deck is clicked, add it to the discard pile
        deckPic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ex) {
                deckCard = deck.pop(); //takes from deck
                discardClass.add(deckCard); //adds to discard stack
                discardPanel.removeAll(); //removes cards in panel
                //gets image for discard pile
                newCard = new JButton(new ImageIcon(deckCard.getCard()));
                //if the discard pile is clicked
                newCard.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ex) {
                        newCard.setVisible(false);
                        discardClass.draw();
                        newCard = new JButton(new ImageIcon(discardClass.peek().getCard()));
                        discardPanel.add(newCard);
                        discardPanel.revalidate();
                    }
                });
                discardPanel.add(newCard);//add the card to the panel
                discardPanel.revalidate();
                prompt();
            }

        });
    }
    /*
     swap card method performed
     */

    public void cardSwap() {
        ImageIcon icon = new ImageIcon(deckCard.getCard());
        String[] opponentValues;
        if (op == 1) {
            opponentValues = new String[]{"No Thanks", "Opponent 1"};
        } else if (op == 2) {
            opponentValues = new String[]{"No Thanks", "Opponent 1", "Opponent 2"};
        } else {
            opponentValues = new String[]{"No Thanks", "Opponent 1", "Opponent 2", "Opponent 3"};
        }

        Object selectedValue = JOptionPane.showOptionDialog(null, "Swap 1 of your cards "
                + "with 1 of your opponents? Which Opponent?",
                "SWAP CARD", JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, icon,
                opponentValues, opponentValues[0]);
        //result value -- option selected
        int result = (Integer) selectedValue;

        //sets buttons to enabled based on selection
        if (result == 1) {
            for (int i = 0; i < 4; i++) {
                op1ButtonsArray[i].setEnabled(true);
                int index = i;
                op1ButtonsArray[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //sets user buttons to clickable
                        for (int i = 0; i < 4; i++) {
                            int index2 = i;
                            numClicks = 0;
                            userButtonsArray[index2].setEnabled(true);
                            userButtonsArray[index2].addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    numClicks++;
                                    if (deckCard.getType() != Type.PEEK && numClicks < 2) {
                                        Card temp = UserHandArray[index2];
                                        userHand.swap(op1HandArray[index], index2);
                                        op1Hand.swap(temp, index);
                                        JOptionPane.showMessageDialog(gamePanel, "Card Swapped!");
                                        buttonState = false;
                                        turn = 1;
                                        loop(turn);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
        if (result == 2) {
            for (int i = 0; i < 4; i++) {
                op2ButtonsArray[i].setEnabled(true);
                int index = i;
                op2ButtonsArray[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //sets user buttons to clickable
                        for (int i = 0; i < 4; i++) {
                            int index2 = i;
                            numClicks = 0;
                            userButtonsArray[index2].setEnabled(true);
                            userButtonsArray[index2].addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    numClicks++;
                                    if (deckCard.getType() != Type.PEEK && numClicks < 2) {
                                        Card temp = UserHandArray[index2];
                                        userHand.swap(op2HandArray[index], index2);
                                        op2Hand.swap(temp, index);
                                        JOptionPane.showMessageDialog(gamePanel, "Card Swapped!");
                                        buttonState = false;
                                        turn = 2;
                                        loop(turn);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
        if (result == 3) {
            for (int i = 0; i < 4; i++) {
                op3ButtonsArray[i].setEnabled(true);
                int index = i;
                op3ButtonsArray[i].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //sets user buttons to clickable
                        for (int i = 0; i < 4; i++) {
                            int index2 = i;
                            numClicks = 0;
                            userButtonsArray[index2].setEnabled(true);
                            userButtonsArray[index2].addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    numClicks++;
                                    if (deckCard.getType() != Type.PEEK && numClicks < 2) {
                                        Card temp = UserHandArray[index2];
                                        userHand.swap(op3HandArray[index], index2);
                                        op3Hand.swap(temp, index);
                                        JOptionPane.showMessageDialog(gamePanel, "Card Swapped!");
                                        buttonState = false;
                                        turn = 3;
                                        loop(turn);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
        /*
         END TURN
         */
    }
    /*
     peek card method performed
     */

    public void peek() {
        //makes all the buttons enabled
        for (JButton b : userButtonsArray) {
            b.setEnabled(true);
        }
        /*
         END TURN
         */
    }

    public void draw2() {
        if (drawAgain = true) {
            deckPic.addActionListener(new draw2Listener());
        } else {
            deckPic.setEnabled(false);
        }
        /*
         END TURN
         */
    }

    //draw 2 listener
    private class draw2Listener implements ActionListener {

        public void actionPerformed(ActionEvent d) {

            while (drawAgain == true && numDeckClicks < numDraw2) {
                numDeckClicks++;
                if (deckCard.getType() == Type.DRAW2) {
                    //gets icon
                    ImageIcon icon = new ImageIcon(deckCard.getCard());
                    JOptionPane.showMessageDialog(gamePanel, "You drew Another Draw 2!",
                            "DRAW2 CARD", JOptionPane.PLAIN_MESSAGE, icon);

                    numDraw2 -= numDeckClicks;
                    drawAgain = true;
                    deckPic.setEnabled(true);
                    draw2();
                }
                //if an option was selected, player cant draw again
                if (result == 1) {
                    drawAgain = false;
                    deckPic.setEnabled(false);
                    numDeckClicks = 0;
                    draw2();
                    //if option wasnt selected, the player can draw again
                } else if (result == 0) {
                    drawAgain = false;
                    deckPic.setEnabled(false);
                    draw2();
                }

            }
        }
    }
    /*
     prompts the user when a card is drawn
     */

    public void prompt() {
        //gets icon
        ImageIcon icon = new ImageIcon(deckCard.getCard());
        //if its users turn, show the frame
        if (deckCard.getType() == Type.DRAW2) {
            JOptionPane.showMessageDialog(gamePanel, "Draw 2 cards!",
                    "DRAW2 CARD", JOptionPane.PLAIN_MESSAGE, icon);
            draw2();
        } else if (deckCard.getType() == Type.PEEK) {
            deckPic.setEnabled(true);//should be false
            JOptionPane.showMessageDialog(gamePanel, "Peek at 1 of your cards",
                    "PEEK CARD", JOptionPane.PLAIN_MESSAGE, icon);
            peek();
        } else if (deckCard.getType() == Type.SWAP) {
            //Swap card
            deckPic.setEnabled(true);//should be false
            cardSwap();
        } else {
            //number card
            String[] optionValues = new String[]{"Add to Discard Pile", "Swap with 1 of your cards"};
            Object selectedValue = JOptionPane.showOptionDialog(null, "You drew a number card,"
                    + " do you want to discard it, "
                    + "or swap it with one of yours?", "NUMBER CARD", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE, icon,
                    optionValues, optionValues[0]);
            //result value -- option selected
            result = (Integer) selectedValue;
            if (result == 1) {
                //sets user buttons to clickable
                for (int i = 0; i < 4; i++) {
                    int index = i;
                    numClicks = 0;
                    userButtonsArray[index].setEnabled(true);
                    userButtonsArray[index].addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            numClicks++;
                            if (deckCard.getType() != Type.PEEK && numClicks < 2) {
                                userHand.swap(deckCard, index);
                                UserHandArray[index] = deckCard;
                                discardClass.draw();
                                JOptionPane.showMessageDialog(gamePanel, "Card Swapped!");
                                System.out.println("User " + userHand.peek(0));
                                System.out.println("User " + userHand.peek(1));
                                System.out.println("User " + userHand.peek(2));
                                System.out.println("User " + userHand.peek(3) + "\n");
                                buttonState = false;
                                deckPic.setEnabled(true);//should be false
                                turn = 0;
                                loop(turn);
                            }
                        }
                    });

                }
            }
        }
        deckPic.setEnabled(true);//should be false
            /*
         END TURN
         */
    }

    /*
     loop for the game
     */
    public void loop(int t) {
        t = turn;

        while (gameState) {
            numTurns++;
            if (numTurns == 1) {
                JOptionPane.showMessageDialog(gamePanel, "Welcome, "
                        + name + "!" + " You go first!"
                        + " PEEK at"
                        + " your outer 2 cards!");
                userButtonsArray[0].setEnabled(true);
                userButtonsArray[3].setEnabled(true);
                gameState = false;
            }
            //sets all buttons to disabled
            if (buttonState == false) {
                for (JButton b : userButtonsArray) {
                    b.setEnabled(false);
                    buttonState = true;
                    if (numTurns == 2) {
                        //sets deck to be enalbed after first turn
                        deckPic.setEnabled(true);
                    }
                }
                if (t == 1) {
                    for (JButton b : op1ButtonsArray) {
                        b.setEnabled(false);
                        buttonState = true;
                    }
                } else if (t == 2) {
                    for (JButton b : op2ButtonsArray) {
                        b.setEnabled(false);
                        buttonState = true;

                    }
                } else if (t == 3) {
                    for (JButton b : op3ButtonsArray) {
                        b.setEnabled(false);
                        buttonState = true;
                    }
                }
                gameState = false;
            }
        }
        gameState = true;

    }
    /*
     builds the menu bar
     */

    public JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        buildFileMenu();
        buildHelpMenu();
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        mainFrame.setJMenuBar(menuBar);
        return menuBar;
    }
    /*
     file menu
     */

    private JMenu buildFileMenu() {
        JMenu soundMenu = new JMenu("Sound Options");
        JMenuItem toggleON = new JMenuItem("ON");
        JMenuItem toggleOFF = new JMenuItem("OFF");
        soundMenu.add(toggleON);
        soundMenu.add(toggleOFF);
        soundMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ex) {
                if (ex.getActionCommand().equals(toggleON.getText())) {
                    System.out.println("on");
                    c.resume();
                } else {
                    System.out.println("off");
                    c.stop();
                }
            }
        });
        fileMenu.add(soundMenu);

        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ex) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
        return fileMenu;
    }
    /*
     help menu
     */

    private JMenu buildHelpMenu() {
        JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ex) {
                System.out.println("help");
            }
        });
        return helpMenu;
    }

    //testing
    public static void main(String[] args) {
        new Game();
    }
}
