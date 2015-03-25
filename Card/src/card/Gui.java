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
public class Gui extends JFrame{

    //Buttons
    private JButton startButton = new JButton("Start Game");
    private JButton howToPlayButton = new JButton("How to Play");
    private JToggleButton soundButton = new JToggleButton("ON");
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
        title2.setBounds(170, 120, 300, 60);
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

        //num of opponents combo box
        String[] numOp = {"1", "2", "3"};
        JComboBox op = new JComboBox(numOp);
        op.setBounds(200, 390, 200, 50);
        op.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ops) {
                System.out.println(op.getSelectedItem());
            }
        });
        startFrame.add(op);

        //dfficulty combobox
        String[] diff = {"Easy", "Medium", "Hard"};
        JComboBox difficulty = new JComboBox(diff);
        difficulty.setBounds(200, 350, 200, 50);
        difficulty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent d) {
                System.out.println(difficulty.getSelectedItem());
            }
        });
        startFrame.add(difficulty);
              
        //End game options
        JPanel panel = new JPanel();
        panel.setBounds(180, 220, 200, 200);
        JRadioButton roundNum = new JRadioButton("Number of Rounds",true);
        JRadioButton time   = new JRadioButton("Time");
        JRadioButton numPoints = new JRadioButton("Number of points");
        ButtonGroup group = new ButtonGroup();
        group.add(roundNum);
        group.add(time);
        group.add(numPoints);
        panel.add(roundNum);
        panel.add(time);
        panel.add(numPoints);
        startFrame.add(panel);
        
        startFrame.setAlwaysOnTop(true);
        startFrame.setResizable(false);
        startFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        startFrame.setVisible(true);
        return startFrame;
    }

    public static void main(String[] args) {
        new Gui();
    }


}
