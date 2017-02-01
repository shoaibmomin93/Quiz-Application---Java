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
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

// This class sets up the GUI of the application. ActionListener is being used to detect button clicks.
// A dialog box pops up for an inappropriate entry in TextFields.
public final class QuizApp extends JPanel implements ActionListener {
// declare the required variables
    JTextField durationTF = new JTextField(20);
     JTextField answerTF = new JTextField(20);
    JButton startBtn = new JButton("Start");
    JButton submitBtn = new JButton("Submit");
    JLabel durationLbl = new JLabel("Total Duration (mins)");
    JLabel answerLbl = new JLabel("Your Answer:");
    TimerPanel timerPanel = new TimerPanel(this);
    QuestionPanel questionPanel = new QuestionPanel(this);
    Boolean startBtnPressed = false; // this is used in paintComponent() to know if start Button has been pressed.  
    int duration; // keeps a track of the time entered
// This is the constructor
    public QuizApp() {
        super.setLayout(new BorderLayout(4, 4));
        // start Panel has a start label, a text field to enter time and a start button
        JPanel startPanel = new JPanel(new BorderLayout(1, 5));
        startPanel.add(durationLbl, BorderLayout.WEST);
        startPanel.add(durationTF, BorderLayout.CENTER);
        startPanel.add(startBtn, BorderLayout.EAST);
        super.add(startPanel, BorderLayout.NORTH);
        // add the startPanel 
        //the testPanel has a QuestionPanel which displays questions of various types
        JPanel testPanel = new JPanel(new BorderLayout(1, 6));
        testPanel.add(questionPanel, BorderLayout.CENTER);
        super.add(testPanel, BorderLayout.CENTER);
        // add the qquestionPanel
        // The answer panel has a label, a text field for answers and a submit button
        JPanel answerPanel = new JPanel(new BorderLayout(1, 4));
        answerPanel.add(answerLbl, BorderLayout.WEST);
        answerPanel.add(answerTF, BorderLayout.CENTER);
        answerPanel.add(submitBtn, BorderLayout.EAST);
        super.add(answerPanel, BorderLayout.SOUTH);
        // add the answerPanel
        durationTF.setText("1"); // set the default time to be 1 minute
        // adding a border to the questionPanel
        LineBorder lineBorder;
        lineBorder = (LineBorder) BorderFactory.createLineBorder(Color.blue);
        questionPanel.setBorder(lineBorder);
        // This function sets the listeners for the buttons
        addListeners();
        // set the button functionalities appropriately
        startBtn.setEnabled(true);
        submitBtn.setEnabled(false);
        
    }
    // This function adds the listeners for the two buttons
    public void addListeners()
    {
        startBtn.addActionListener(this);
        submitBtn.addActionListener(this);
    }
    // This function clears the Text Field for answers
    public  void  clearAnswer()
    {
        answerTF.setText(" ");
    }
// actionPerformed is implemented as a part of ActionListener. The actions to be performed
    // for button pressed are written here
    @Override
    public void actionPerformed(ActionEvent e) {
        // when the start button is pressed
        if(e.getSource() == startBtn) 
        {
            startBtnPressed = true; // set the flag to true
            String durationString = durationTF.getText().trim(); // get the duration from the text field
            // check if the entered string is an integer
            if(!(isInteger(durationString)))
            {
                JOptionPane.showMessageDialog(null, "An integer between 1 and 20 needs to be enetered. ", "QuizApp Error ", JOptionPane.INFORMATION_MESSAGE);
                durationTF.setText(" "); // clear the duration field to enter a new value
                return;  // do not proceed .
            }
            else
            {
                int durationInt = Integer.parseInt(durationString);
                // check if the number entered is between 1 and 20
                if(durationInt<=0 || durationInt>20)
                {
                    JOptionPane.showMessageDialog(null, "The number entered should be an integer between 1 and 20", "QuizApp Error ", JOptionPane.INFORMATION_MESSAGE);
                    durationTF.setText(" "); // clear the duration field to enter a new value
                return;
                }
                    
            }
            // if the entry is valid, proceed .
            duration = Integer.parseInt(durationString);
            timerPanel.startTimer(duration); //start the timer 
            questionPanel.startQuiz(); // start the quiz
            // set the properties of buttons and text fields
            durationTF.setEditable(false);
            submitBtn.setEnabled(true);  
           startBtn.setEnabled(false);
           
        }
        // if the button pressed is submit,
        else if(e.getSource() == submitBtn)
        {
            // get the string from text field
            String answerString = answerTF.getText().trim();
            // if the string is empty, display an error message
            if(answerString.length() == 0)
            {
                JOptionPane.showMessageDialog(null, "Submission not Successful. Please provide an Answer", "QuizApp Error ", JOptionPane.INFORMATION_MESSAGE);
              return;
            }
            // process the Answer
            questionPanel.processAnswer(answerString);
            
        }
       
    } // end actionPerformed
    // this is a method to check if a string is an integer or not
    public static boolean isInteger(String s) {
    try { 
        // if the result of parseInt is valid, it is an integer
        Integer.parseInt(s); 
    } catch(NumberFormatException | NullPointerException e) { 
        // else it is not an integer
        return false; 
    }
    // only got here if we didn't return false
    return true;
}

} // end class
