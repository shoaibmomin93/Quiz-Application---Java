/**
 * *********************************************************
 *
 * Progammer: MOMIN,SHOAIB 

 * Date : - December 4, 2016
 *
 * Purpose: This program demonstrates the concept of Graphics, Threads and Polymorphism. This implements a Quiz Application.
 ***********************************************************
 */
package asign5frame;

import java.awt.BorderLayout;
import javax.swing.JFrame;

// This class creates the frame for the application to run.
public class Asign5Frame extends JFrame{

   
    public static void main(String[] args) {
        createandshowGUI();
    }
// Frame is created in createandShowGUI. Also, a few properties are set.
    public static void createandshowGUI() {
        Asign5Frame frame = new Asign5Frame();
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 400);  // set the initial values 
        frame.setVisible(true);
       
    }
  // This is the constructoe. In this, an object of QuizApp is created which 
    // creates the actual GUI of the application
    public Asign5Frame()
    {
        super("QuizApp");
        QuizApp quizapp = new QuizApp();
        super.setLayout(new BorderLayout());  // set up a border layout and this to the center
        super.add(quizapp, BorderLayout.CENTER);
    }
    
} // end class
