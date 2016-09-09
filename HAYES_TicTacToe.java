/** This program uses a simple java swing GUI to implement a TicTacToe game.
 *  The game can be played against the computer, or against another player.
 * 
 *  @author Michael Hayes
 *  @version 1.1
 * 
 *  Revision History:
 *        9/8/16  - Added JavaDoc introductory comments to conform with industry standards
 *        5/27/15 - Initial release
 */

import java.awt.*; //for gui
import java.awt.event.*; //for events
import javax.swing.*; //for gui
import java.util.*; //for random

public class HAYES_TicTacToe extends JFrame implements ActionListener {

     //set up class variables
     private static boolean playersTurn = true, vsComputer = true;
     private static String token = "X";
     private static int turns = 0;

     //fonts
     private static Font theFont = new Font("Arial", Font.BOLD, 75);
     private static Font labelFont = new Font("Arial", Font.BOLD, 28);
     private static Font littleFont = new Font("Arial", Font.PLAIN, 15);
     private static Font activeFont = new Font("Arial", Font.BOLD, 15);
     
     //random
     private static Random rand = new Random();
     
     //components
     private static JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, bReset, bType;
     private static JLabel label1;
        
     
     //*************************************************
     
     //Constructor
     public HAYES_TicTacToe(){
     
         //set up the frame
         setLayout(new GridLayout(4, 3, 20, 20));
         setSize(400, 400);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
         //to make sure the buttons don't get grayed out when disabled
         UIManager.getDefaults().put("Button.disabledText", Color.BLACK);
         
         //buttons
         b1 = new JButton ("");
         b2 = new JButton ("");
         b3 = new JButton ("");
         b4 = new JButton ("");
         b5 = new JButton ("");
         b6 = new JButton ("");
         b7 = new JButton ("");
         b8 = new JButton ("");
         b9 = new JButton ("");
         bReset = new JButton ("Reset Game");
         bType = new JButton("Vs. Computer");
         
         bReset.setFont(littleFont);
         bType.setFont(activeFont);
         bType.setForeground(Color.blue);
         
         //label
         label1 = new JLabel("X's Turn", SwingConstants.CENTER);
         label1.setFont(labelFont);        
         
         //add components to frame
         add(b1);
         add(b2);
         add(b3);
         add(b4);
         add(b5);
         add(b6);
         add(b7);
         add(b8);
         add(b9);
         add(bReset);
         add(label1);
         add(bType);         
         
         //add action listeners
         b1.addActionListener(this);
         b2.addActionListener(this);
         b3.addActionListener(this);
         b4.addActionListener(this);
         b5.addActionListener(this);
         b6.addActionListener(this);
         b7.addActionListener(this);
         b8.addActionListener(this);
         b9.addActionListener(this);
         bReset.addActionListener(this);
         bType.addActionListener(this);
   
     }//end constructor 
     
     //Main Method
     public static void main (String[] args){
         
         //create the frame
         HAYES_TicTacToe myGame = new HAYES_TicTacToe();
         myGame.setSize(400,533);
         myGame.setVisible(true);  
         
              
      }//end main
           
     public void actionPerformed(ActionEvent e){
      
        //reset the game if cliked on reset
        if (e.getSource() == bReset){
           
               resetGame();
        }
        
        //swap the opponent if clicked on swap
        if (e.getSource() == bType){
        
            swapOpponent();
        }
        
        //else, place the token on the button clicked
        if(playersTurn == true){
        
           if (e.getSource() == b1){
           
               buttonClicked(b1);              
           }
           else if (e.getSource() == b2){
           
               buttonClicked(b2);               
           } 
           else if (e.getSource() == b3){
           
               buttonClicked(b3);                
           }
           else if (e.getSource() == b4){
           
               buttonClicked(b4);                
           }
           else if (e.getSource() == b5){
           
               buttonClicked(b5);                
           }
           else if (e.getSource() == b6){
           
               buttonClicked(b6);                
           }
           else if (e.getSource() == b7){
           
               buttonClicked(b7);                
           }
           else if (e.getSource() == b8){
           
               buttonClicked(b8);                
           }
           else if (e.getSource() == b9){
           
               buttonClicked(b9);                
           } 
                      
        }//end player's turn = yes           
      
     }//end action performed
     
     //****************************************************
     
     private static void buttonClicked(JButton b){
         
         bType.setEnabled(false);
         bType.setFont(littleFont);
         b.setText(token);
         b.setFont(theFont);
         b.setEnabled(false);
         prepNextTurn();
         
         if(endGame() == false && vsComputer == true){
         
            computerTurn();
         }
         else if (endGame() == true){
         
            bReset.setFont(activeFont);
            bReset.setForeground(Color.red);

         }
     
     }//end button clicked 
     
     private static void computerMove(JButton b){
         
         //similar to button clicked, but doesn't prep for the next turn
         b.setText(token);
         b.setFont(theFont);
         b.setEnabled(false);
         
         if(endGame() == false){
         
            prepNextTurn();
         }
         else{
         
            bReset.setFont(activeFont);
            bReset.setForeground(Color.red);
         }         
     
     }//end computer clicked 

     //prep the next turn by swapping players info
     private static void prepNextTurn(){
     
         if(playersTurn == true && vsComputer == true){
         
            playersTurn = false;
         }
         else{
         
            playersTurn = true;        
         }
         
         if(token.equals("X") == true){
         
            token = "O";
            label1.setText("O's Turn");         
         }
         else if(token.equals("O") == true){
         
            token = "X";
            label1.setText("X's Turn");
         } 
         
         turns++;    
     
     }//end prepNextTurn 
     
     private static void computerTurn(){
     
         //If there is a winning move
         if(checkMove("O") != null){
         
            computerMove(checkMove("O"));           
         }
         
         //else if there is a blocking move
         else if(checkMove("X") != null){
         
            computerMove(checkMove("X"));
         }
         
         //else if the center is open
         else if(spaceOpen(b5) == true){
         
            computerMove(b5);         
         }
         
         //otherwise make a random move
         else if (randomMove() != null){
            
            computerMove(randomMove());
         }
     
     }//end computer move
     
     //*****************************************
     //************HELPER METHODS***************
     //*****************************************
          
     //Checks if a given space is open
     private static boolean spaceOpen(JButton b){
     
         return b.getText().equals("");
     
     }//end centerOpen
     
     //returns a random move
     private static JButton randomMove(){
     
         JButton[] buttons = { b1, b2, b3, b4, b5, b6, b7, b8, b9 };
         int i;
         
         //If the board is full then return null
         if(turns == 9){
         
            return null;
         }
         
         //else find a random number whos space is open
         do{
         
            i = rand.nextInt(9);
         
         } while(spaceOpen(buttons[i]) == false);
         
         
         return buttons[i];
     
     }//end randomMove
     
     //Checks if there is a winning or blocking move and returns the button
     //cooresponding to the space that would win the game or block
     //takes a string s that corresponds to the token to search for
     //I.E. Takes an X if looking to block or an O if looking to win
     //returns null if there is not a space matching the criteria
     //Ok, so this looks repetitive, but the only reason I decided
     //not to use a loop is because there is no logical pattern to the
     //button order that is passed to the 'checkOpen' method
     //also, there are only 8 test cases to test for
     private static JButton checkMove(String s){
     
         JButton winningSpot;
         
         winningSpot = checkOpen(b1, b2, b3, s);         
         if(winningSpot != null) { return winningSpot; }
         winningSpot = checkOpen(b4, b5, b6, s);
         if(winningSpot != null) { return winningSpot; }
         winningSpot = checkOpen(b7, b8, b9, s);
         if(winningSpot != null) { return winningSpot; }
         winningSpot = checkOpen(b1, b4, b7, s);
         if(winningSpot != null) { return winningSpot; }
         winningSpot = checkOpen(b2, b5, b8, s);
         if(winningSpot != null) { return winningSpot; }
         winningSpot = checkOpen(b3, b6, b9, s);
         if(winningSpot != null) { return winningSpot; }
         winningSpot = checkOpen(b1, b5, b9, s);
         if(winningSpot != null) { return winningSpot; }
         winningSpot = checkOpen(b3, b5, b7, s);
         
         //if null by the end then no winning spot
         
         return winningSpot;    
         
     }//end checkmove
     
     //Takes a set of three spaces and returns the number of the empty space if it can win or block
     //returns null if there are not two spaces filled
     private static JButton checkOpen(JButton one, JButton two, JButton three, String search){
     
         if(!one.getText().equals("") && !two.getText().equals("") && !three.getText().equals("")){
         
            return null;
         }
         if(one.getText().equals(search) && two.getText().equals(search)){
         
            return three;
         }
         if(one.getText().equals(search) && three.getText().equals(search)){
         
            return two;
         }
         if(two.getText().equals(search) && three.getText().equals(search)){
         
            return one;
         }         
         
         return null;
     
     }//end check set of three
     
     //********************************************
     
     //returns true if the game is already won
     private static boolean endGame(){
     
         if(checkWin("X") == true && turns < 9){
         
            label1.setText("X Wins!");
            playersTurn = false;
            return true;
         }
         else if(checkWin("O") == true && turns < 9){
         
            label1.setText("O Wins!");
            playersTurn = false;
            return true;

         }
         else if(turns > 8){
         
            label1.setText("Draw...");
            playersTurn = false;
            return true;
         }
         
         return false;
     
     }//end endGame
     
     //checks each possible row pattern to see if there is a win for 
     //the given search token
     private static boolean checkWin(String s){
     
         boolean didWin = false;
         
         didWin = checkRowFilled(b1, b2, b3, s);         
         if(didWin == true) { return didWin; }
         didWin = checkRowFilled(b4, b5, b6, s);
         if(didWin == true) { return didWin; }
         didWin = checkRowFilled(b7, b8, b9, s);
         if(didWin == true) { return didWin; }
         didWin = checkRowFilled(b1, b4, b7, s);
         if(didWin == true) { return didWin; }
         didWin = checkRowFilled(b2, b5, b8, s);
         if(didWin == true) { return didWin; }
         didWin = checkRowFilled(b3, b6, b9, s);
         if(didWin == true) { return didWin; }
         didWin = checkRowFilled(b1, b5, b9, s);
         if(didWin == true) { return didWin; }
         didWin = checkRowFilled(b3, b5, b7, s);
    
         //if false by end, then they didn't win
         
         return didWin;
     
     }
     
     //checks to see if a row is filled with the same token
     private static boolean checkRowFilled(JButton one, JButton two, JButton three, String s){
     
         boolean filled = (one.getText().equals(s) && two.getText().equals(s) && three.getText().equals(s));
         
         if(filled == true && s.equals("X")){
         
            setWin(one, two, three, Color.red);         
         }
         else if(filled == true && s.equals("O")){
         
            setWin(one, two, three, Color.blue);         
         }
         
         return filled;
               
     }//end check row filled 
     
     //sets the winning tokens as the color passed to it
     private static void setWin(JButton one, JButton two, JButton three, Color c){
     
            one.setEnabled(true);
            two.setEnabled(true);
            three.setEnabled(true);
            one.setForeground(c);
            two.setForeground(c);
            three.setForeground(c); 
     
     }//end set win
     
     //called when the reset button is pressed
     private static void resetGame(){
     
        playersTurn = true;
        
        resetButton(b1);
        resetButton(b2);
        resetButton(b3);
        resetButton(b4);
        resetButton(b5);
        resetButton(b6);
        resetButton(b7);
        resetButton(b8);
        resetButton(b9);
        
        bReset.setFont(littleFont);
        bReset.setForeground(Color.black);
        bType.setEnabled(true);
        bType.setFont(activeFont);
        bType.setForeground(Color.blue);

        
        turns = 0;
        token = "X";
        label1.setText("X's Turn");
              
     } //end reset Game
     
     //helper method that resets a single button
     private static void resetButton(JButton b){
     
         b.setEnabled(true);
         b.setText("");
         b.setForeground(Color.black);
     
     }//end resetButton 
     
     //called when the swap opponent button is pressed
     private static void swapOpponent(){
     
         if(vsComputer == true){
         
            vsComputer = false;
            bType.setText("Vs. Player");
         }
         else{
         
            vsComputer = true;
            bType.setText("Vs. Computer");
         }    
     
     }//end swapOpponent   
                             
}//end class
