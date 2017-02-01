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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

// This class keeps a track of the time and the timer
public class TimerPanel implements ActionListener{
    // declare the required variables
    int seconds =0;
    int delay = 1000; 
    QuizApp parentTimer = null;
    Timer timer1; // have a timer object to keep a track of the timer
    // constructor
    public TimerPanel (QuizApp inParent)
    {
        this.parentTimer = inParent;
        timer1 = new Timer(delay,  this); // new up timer object
    }
    // this method starts the timer. 
    public void startTimer(int durationMinutes)
    {
         seconds = durationMinutes *60; // convert it into seconds
         // display seconds in the text field
        parentTimer.durationTF.setText(Integer.toString(seconds));
        timer1.start(); // start the timer
    }
 // the action performed is called every  sec when the timer is ultimately started
  @Override
    public void actionPerformed(ActionEvent e) {
        seconds--; // decrement the seconds
       // if the timer is over, end the quiz
        if(seconds <= 0)
        {
            parentTimer.questionPanel.quizOverFlag = true;
            parentTimer.questionPanel.timerUp = true;
        }
        // if the quiz is over, end the quiz
        if(parentTimer.questionPanel.quizOverFlag)
        {
            seconds = 0;
            parentTimer.questionPanel.timerUp=true;
            parentTimer.questionPanel.quizOverFlag = true;
        }
        else
        {
            // or display the seconds on the screen
            parentTimer.durationTF.setText(Integer.toString(seconds));
        
        }
  
    } // end actionperformed
    
} // end Class 
 