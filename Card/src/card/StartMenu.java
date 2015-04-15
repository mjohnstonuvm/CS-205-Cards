/*
 Created by Matt Johnston
 */
package card;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

/**
 * Class to represent card start menu as a UI
 */
public class StartMenu extends JFrame {

    //PlayClip c = new PlayClip("hp.au");
    Game g;
    public int NUM_OPPONENTS;
    public String END_GAME, DIFFICULTY,NAME = "";
    /*
     set window
     */

    public JFrame getStartMenu() {
        final JFrame startFrame = new JFrame();
        JButton startButton = new JButton("Start Game");
        JButton howToPlayButton = new JButton("How to Play");
        JButton closeButton = new JButton("Exit");
        JButton submitButton = new JButton("Submit");
        JToggleButton soundButton = new JToggleButton("ON");
        //set null layout
        startFrame.setLayout(null);
        //labels
        JLabel nameLab = new JLabel("Your Name:");
        nameLab.setBounds(100, 0, 610, 700);
        startFrame.add(nameLab);
        JTextField field = new JTextField(10);
        field.setBounds(200, 330, 200, 30);
        
        startFrame.add(field);
        submitButton.setBounds(420, 330, 100, 30);
        submitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                NAME = field.getText();  
            }
        });
        startFrame.add(submitButton);
        JLabel opLab = new JLabel("# Opponents:");
        opLab.setBounds(100, 65, 610, 700);
        startFrame.add(opLab);
        JLabel diffLab = new JLabel("Difficulty:");
        diffLab.setBounds(100, 25, 610, 700);
        startFrame.add(diffLab);
        JLabel endLab = new JLabel("End Game:");
        endLab.setBounds(100, 230, 130, 10);
        startFrame.add(endLab);
        //Title of game
        JLabel title = new JLabel("Rat-a-Tat Cat");
        title.setForeground(Color.blue.darker());
        title.setBounds(80, 50, 500, 60);
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
        //how to play button
        howToPlayButton.setBounds(200, 450, 200, 50);
        howToPlayButton.addActionListener((ActionEvent sb) -> {
            startFrame.setVisible(false);
            howToPlay();
        });
        startFrame.add(howToPlayButton);
        //start button
        startButton.setBounds(200, 520, 200, 50);
        startButton.addActionListener((ActionEvent sb) -> {
            startFrame.dispose();
            if (NUM_OPPONENTS == 0) {
                NUM_OPPONENTS = 1;
            }
            if(END_GAME == null){
                END_GAME = "Time";
            }
            if(NAME == null){
                NAME = "Player1";
            }
            g = new Game(NUM_OPPONENTS, DIFFICULTY,END_GAME,NAME);
        });
        startFrame.add(startButton);
        //close button
        closeButton.setBounds(200, 590, 200, 50);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        startFrame.add(closeButton);
        //sound button
        soundButton.setBounds(550, 610, 50, 50);
        soundButton.addActionListener((ActionEvent sb) -> {
            if (soundButton.getText().equals("ON")) {
                soundButton.setText("OFF");
                //c.stop();
            } else {
                soundButton.setText("ON");
                //c.resume();
            }
        });
        startFrame.add(soundButton);
        //num of opponents combo box
        String[] numOp = {"1", "2", "3"};
        JComboBox op = new JComboBox(numOp);
        op.setBounds(200, 390, 200, 50);
        op.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ops) {
                NUM_OPPONENTS = Integer.parseInt((String) op.getSelectedItem());
            }
        });
        startFrame.add(op);
        //dfficulty combobox
        String[] diff = {"Easy", "Medium", "Hard"};
        JComboBox difficulty = new JComboBox(diff);
        difficulty.setBounds(200, 350, 200, 50);
        difficulty.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent d) {
                DIFFICULTY = difficulty.getSelectedItem().toString();
            }
        });
        startFrame.add(difficulty);
        //End game options
        JPanel panel = new JPanel(); //panel
        panel.setBounds(180, 220, 200, 200); //bounds
        //radio buttons
        JRadioButton roundNum = new JRadioButton("Number of Rounds", true);
        JRadioButton time = new JRadioButton("Time");
        JRadioButton numPoints = new JRadioButton("Number of points");
        //adds action listener to raio buttons
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof JRadioButton) {
                    JRadioButton radioButton = (JRadioButton) e.getSource();
                    END_GAME = radioButton.getText();
                }
            }
        };
        roundNum.addActionListener(actionListener);
        time.addActionListener(actionListener);
        numPoints.addActionListener(actionListener);

        //Group the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(roundNum);
        group.add(time);
        group.add(numPoints);

        //add buttons to panel
        panel.add(roundNum);
        panel.add(time);
        panel.add(numPoints);
        //add panel to startframe
        startFrame.add(panel);
        //get window attributes 
        getAttributes(startFrame);
        return startFrame;
    }

    public JFrame howToPlay() {
        Scanner inputFile = null;
        String inputLine = "";
        File file = new File("howto.txt");
        try {
            inputFile = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.getMessage();
        }
        JFrame frame = new JFrame();
        frame.setTitle("How To Play");
        frame.setBounds(400, 80, 610, 700);
        frame.setBackground(Color.gray.darker().darker());
        frame.setLayout(new BorderLayout());
        frame.setAlwaysOnTop(true);
        JPanel panel = new JPanel();
        JButton button = new JButton("Close");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                getStartMenu();
            }
        });
        while (inputFile.hasNext()) {
            inputLine = inputFile.nextLine();
            JLabel label = new JLabel(inputLine);
            label.setForeground(Color.black);
            panel.add(label);
        }
        panel.add(button);
        frame.add(panel);
        frame.setResizable(false);
        inputFile.close();
        frame.setVisible(true);
        return frame;
    }
    /*
     simple attribues for windows
     */

    public void getAttributes(JFrame frame) {
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }

}
