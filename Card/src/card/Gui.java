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
    
    /*
    Constructor to set window
    */
    public Gui() {
        super.setTitle("Rat-A-Tat CAT");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Gui();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
