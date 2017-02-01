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

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Scanner;
import javax.swing.JPanel;

// QuestionPanel class extends JPanel. It is where the questions are painted and the time is checked.
public class QuestionPanel extends JPanel implements Runnable {
// decalre the required variables
//    int paintCount = 0;

    ArrayList<Question> questionAL = new ArrayList<>(); // stores questions
    QuizApp parent = null;
    Thread runner = null;
    Dimension d;
    Boolean quizOverFlag, timerUp;
    int currentQuestion = -1;
    Boolean questionRead = false;
    Boolean scoreDisplayed = false;
    int actualScore=0;

    // This is the constructor
    public QuestionPanel(QuizApp inParent) {
        this.parent = inParent;
    }
// this method is from where the quiz starts

    public void startQuiz() {
        // new up a thread and start it
        scoreDisplayed = false;
        actualScore =0;
        runner = new Thread(this);
        runner.start();

    }
// run is required for threads. They start executing from this position

    @Override
    public void run() {
        quizOverFlag = false;
        timerUp = false;  // initialize the flags
        if (!(questionRead)) {
            readQuestionFile();
            // if the questions have not been read yet, fill 
            // the array list with the questions
        }
        currentQuestion++; // go to the next question
        repaint();
        // as long as the quiz and timer have not ended, perform the following
        while ((!timerUp) && (!quizOverFlag)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("exception in sleep");
            }
        }
// if the timer is not up 
        if (!timerUp) {
            timerUp = true;     // STOP THE TIMER
            quizOverFlag = true;   // quiz is over
            repaint(); // call the paintComponent()
            parent.submitBtn.setEnabled(false); // set the properties appropriately
            parent.durationTF.setEditable(true);
            parent.startBtn.setEnabled(true);
            parent.durationTF.setText("1"); // the default duration is 1, so set it 
            currentQuestion = -1; // reset the arraylist position
            runner = null;
        }
        // This means that the quiz has ended . It can end when a quiz is over or the timer has reached 0
        if (timerUp || quizOverFlag) {
            parent.submitBtn.setEnabled(false);
            parent.durationTF.setEditable(true);
            parent.startBtn.setEnabled(true);
            parent.durationTF.setText("1");
            currentQuestion = -1;
            runner = null;
            repaint();
        }

    }  // END RUN
// This paints the questions on the screen

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // call the super method
//        paintCount++;
        //       if(paintCount == 1)
        if (!(parent.startBtnPressed)) { // if the quiz has not yet started, just display the GUI
            return;
        }
        // if the quiz is over, display the score

        if (quizOverFlag) {
            int quizScore;
            

            quizScore = computeQuizScore(); // gets the score from the arrayList
            // display the end of quiz in the form of a text question
            Question endQuizMessage;
            if (!scoreDisplayed) {

                endQuizMessage = new TextQuestion("End of Quiz \n Your score is " + quizScore, "");
                actualScore = quizScore;

            } else {
                endQuizMessage = new TextQuestion("End of Quiz \n Your score is " + actualScore, "");
            }

            scoreDisplayed = true;
            d = getSize();
            endQuizMessage.draw(g, d);
        } else {
            // else, display the question on the questionPanel 
            Question oneQuestion = (Question) questionAL.get(currentQuestion);
            d = getSize();
            oneQuestion.draw(g, d);
            parent.clearAnswer(); // clears the text field to enter an answer
        }
    }  // end paintComponent
// This method processes the answer. Calls the judge answer method , increments the question and check if
    // the quiz is over.

    public void processAnswer(String answer) {
        Question oneQuestion = (Question) questionAL.get(currentQuestion);  //get the q
        oneQuestion.judgeAnswer(answer);   //judge answer is () in the question,sets int in question to 0 or1

        currentQuestion++;    //go to next q in array list
        if (currentQuestion == questionAL.size()) {
            quizOverFlag = true;  // if the arrayList is complete, the quiz is over

        }
        repaint();   //fires paint even so we draw next question

    } // end processAnswer
// This method computes the score after the end of the quiz

    public int computeQuizScore() {
        int totalScore = 0; // initially it zero
        // get an iterartor to traverse through the arrayList
        ListIterator<Question> li = questionAL.listIterator();
        while (li.hasNext()) {
            Question q = li.next();
            int sc = q.getScore();
            if (sc == 1) { // if the score is set to one, incerement the score
                totalScore++;
                q.setScore(0);
            }

        }
        return totalScore; // return the total score
    } // end ComputeScore
    // This method reads the file that has questions & answers, and stores the questions in arrayList  

    public void readQuestionFile() {
        try {
            questionRead = true; // this flag indicates that the questions have been set
            File f = new File("quiz.txt");
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String questionIn = sc.nextLine();
                String answerIn = sc.nextLine();
                // if the question ends with .jpg or .gif, it means that it is a picture question
                // and so set up the ImageQuestion
                if (questionIn.endsWith(".jpg") || questionIn.endsWith(".gif")) {
                    questionAL.add(new ImageQuestion(questionIn, answerIn, this));
                } else {
                    // else , it is a text question
                    questionAL.add(new TextQuestion(questionIn, answerIn));
                }

            } // end while

        } catch (FileNotFoundException e) {
            System.out.println("File not found exception");
        }

    } // end readQuestionFile
} // end QuestionPanel
