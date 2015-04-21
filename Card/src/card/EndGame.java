package card;
import java.awt.event.*;
import javax.swing.*;

/**
 * Class to represent end of game as UI
 */
public class EndGame extends JFrame {

    private String playerS;
    private String diff;
    private int score[];
    private boolean winn;

    public JFrame getEndGame() {

        final JFrame endMenu = new JFrame();
        JButton newGameButton = new JButton("New Game");
        JButton closeButton = new JButton("Exit");

        endMenu.setLayout(null);

        JLabel title = new JLabel("End-Game");
        title.setBounds(120, 0, 300, 50);
        endMenu.add(title);
        JLabel winner = new JLabel("Player: " + playerS);
        winner.setBounds(100, 100, 300, 50);
        endMenu.add(winner);
        JLabel difficult = new JLabel("Difficulty:" + diff);
        difficult.setBounds(100, 150, 300, 50);
        endMenu.add(difficult);
        JLabel opp = new JLabel("Your score: " + score[0]);
        opp.setBounds(100, 200, 300, 50);
        endMenu.add(opp);
        JLabel turn;
        if (winn) {
            turn = new JLabel("You won! ");
        } else {
            turn = new JLabel("You lost! ");
        }
        turn.setBounds(100, 250, 300, 50);
        endMenu.add(turn);
        newGameButton.setBounds(100, 300, 150, 50);
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartMenuTest a = new StartMenuTest();
                a.getStartMenu();
                endMenu.dispose();
            }
        });
        endMenu.add(newGameButton);
        closeButton.setBounds(100, 370, 150, 50);
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        endMenu.add(closeButton);
        endMenu.setBounds(300, 80, 400, 500);
        endMenu.setVisible(true);
        endMenu.setResizable(false);
        return endMenu;
    }

    public EndGame(String player, String difficulty, int scores[], boolean win) {
        playerS = player;
        diff = difficulty;
        score = scores;
        winn = win;

    }

    public void getAttributes(JFrame frame) {
        frame.setAlwaysOnTop(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
       // EndGame end = new EndGame();
        //end.setEndGame("You", "Easy", 3, 20);

        //end.getEndGame();
    }
}
