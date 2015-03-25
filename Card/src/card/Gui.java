/*
 Created by Matt Johnston
 */
package card;

import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class to represent card game as a UI
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

        //Title of game
        JLabel title = new JLabel("Rat-a-Tat Cat");
        title.setForeground(Color.blue.darker());
        title.setBounds(60, 0, 500, 60);
        title.setFont(title.getFont().deriveFont(64.0f));

        //start menu title
        JLabel title2 = new JLabel("Start Menu");
        title2.setForeground(Color.blue.darker());
        title2.setBounds(170, 70, 300, 60);
        title2.setFont(title2.getFont().deriveFont(40.0f));

        //title of window
        startFrame.setTitle("Start Menu");
        startFrame.setBounds(400, 80, 610, 700);
        startFrame.add(title);
        startFrame.add(title2);
        howToPlayButton.setBounds(200, 450, 200, 50);
        howToPlayButton.addActionListener((ActionEvent sb) -> {
            System.out.println("Help");
        });
        startFrame.add(howToPlayButton);
        startButton.setBounds(200, 520, 200, 50);
        startButton.addActionListener((ActionEvent sb) -> {
            System.out.println("Start Game");
        });
        startFrame.add(startButton);
        soundButton.setBounds(550, 610, 50, 50);
        soundButton.addActionListener((ActionEvent sb) -> {
            if (soundButton.getText().equals("ON")) {
                soundButton.setText("OFF");
            } else {
                soundButton.setText("ON");
            }
        });
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
