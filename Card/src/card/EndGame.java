package card;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.*;

/**
 * Class to represent end of game as UI
 */


public class EndGame extends JFrame {

	private static String winnerS;
	private static String diff;
	private static int nAI;
	private static int trns;


	public static JFrame getEndGame() {

		final JFrame endMenu = new JFrame();
		JButton newGameButton = new JButton("New Game");
        JButton closeButton = new JButton("Exit");

        endMenu.setLayout(null);

        JLabel title = new JLabel("End-Game");
        title.setBounds(100, 0, 300, 150);
        endMenu.add(title);
        JLabel winner = new JLabel("Winner: " +  winnerS);
        winner.setBounds(100, 150, 300, 100);
        endMenu.add(winner);
        JLabel difficult = new JLabel("Difficulty:" + diff);
        difficult.setBounds(100, 250, 300, 100);
        endMenu.add(difficult);
        endMenu.setVisible(true);
        return endMenu;
	}
	public static void setEndGame(String win, String difficulty, int numAI, int turns) {
		winnerS = win;
		diff = difficulty;
		nAI = numAI;
		trns = turns;

	}
	 public static void getAttributes(JFrame frame) {
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[]args){
    	setEndGame("You", "Easy", 3, 20);
    	EndGame end = new EndGame();

        end.getEndGame();
    }
}