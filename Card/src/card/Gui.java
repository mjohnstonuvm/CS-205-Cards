/*
 Created by Matt Johnston
 */
package card;

import java.awt.event.*;
import javax.swing.*;

/**
 * Gui class to represent card game as a UI
 */
public class Gui extends JFrame implements ActionListener {

    //Buttons
    private JButton startButton = new JButton("Start Game");
    private JButton howToPlayButton = new JButton("How to Play");
    private JButton soundButton = new JButton("ON");
    /*
     Constructor to set window
     */

    public Gui() {
        super.setTitle("Rat-A-Tat CAT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        getStartMenu();
    }

    private JFrame getStartMenu() {
        final JFrame startFrame = new JFrame();
        startFrame.setLayout(null);
        startFrame.setTitle("Start Menu");
        startFrame.setBounds(400, 80, 610, 700);
        
        howToPlayButton.setBounds(200, 450, 200, 50);
        startFrame.add(howToPlayButton);
        startButton.setBounds(200, 520, 200, 50);
        startFrame.add(startButton);
        soundButton.setBounds(550, 610, 50, 50);
        startFrame.add(soundButton);
        startFrame.setAlwaysOnTop(true);
        startFrame.setResizable(false);
        startFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        startFrame.setVisible(true);
        return startFrame;
    }

    public static void main(String[] args) {
        new Gui();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
