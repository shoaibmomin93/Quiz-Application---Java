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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ImageObserver;

// This is an abstract class . Two classes  Text and Image Question extend this class
public abstract class Question {
    private int score = 0;   // declare the required variables
    protected Object questionPtr = null;
    private final String answerPtr;
// this is the constructor. It has one parameter, the answer to the question
    public Question(String inanswer) {
        this.answerPtr = inanswer;
    }
// draw method , displays the question on the screen
    public abstract void draw(Graphics g, Dimension d);
// judgeAnswer checks to see if the answer is right.
    public void judgeAnswer(String studentAnswer) {
        if (answerPtr.equalsIgnoreCase(studentAnswer)) {
            score++; // increment the score if the answer is right
        }
    }
// this is a getter, used to get the score
    public int getScore() {
        return score;
    }
// setter to set score
    public void setScore(int score) {
        this.score = score;
    }
    
// getter to get the Question
    public Object getQuestion() {
        return questionPtr;
    }
// getter to get Answer
    public String getAnswer() {
        return answerPtr;
    }

} // end abstract class

// This class is for the TextQuestion. Unimplemented methods of Question need to be 
// implemented here
class TextQuestion extends Question {
// constructor has question and answer. 
    public TextQuestion(String inquestion, String inanswer) {
        super(inanswer); // call Question constructor and pass the answer
        questionPtr = (Object) inquestion; 
    }
// Implement the draw method for textQuestion
    @Override
    public void draw(Graphics g, Dimension d) {
        int width, x, y;
        Font font = new Font("Arial", Font.PLAIN, 20);
        FontMetrics fontmetric1 = g.getFontMetrics(font);
        width = fontmetric1.stringWidth((String) questionPtr);
        x = d.width / 2 - width / 2;
        y = d.height / 2;
        // set the position of the question on frame
        String mystringQuestion = (String) questionPtr;  //cast from obj to string
        g.drawString(mystringQuestion, x, y);     //draw the string question 

    }

}  // end TextQuestion

// This class is for the ImageQuestion. Unimplemented methods of Question need to be 
// implemented here
 class ImageQuestion extends Question {

    private final ImageObserver observer;
    //constructor

    public ImageQuestion(String inQuestion, String inAnswer , ImageObserver observer ) {
        super(inAnswer);
        this.observer = observer;  //needed to find the image when we draw it
        //get the jpeg out of toolkit..
        questionPtr = (Object) Toolkit.getDefaultToolkit().createImage(inQuestion); //fill in question pointer in base class
        //type image is here

    }
    // Implement draw for ImageQuestion
    @Override
    public void draw(Graphics g, Dimension d) {
  int width, height, x, y;
   //draw the question on graphics
         width = ((Image) questionPtr).getWidth(null); //get h and w of image
         height = ((Image) questionPtr).getHeight(null);
         x = d.width/2 - width/2;
         y = d.height/2 - height/2;
         // get the x and y to place the image at that position
         g.drawImage((Image)questionPtr, x, y, observer);
    }

} // end ImageQuestion
