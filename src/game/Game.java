/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Dimension;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.ImageIcon;

import javax.swing.Timer;
import java.awt.Image;
import java.applet.*;

public class Game extends JPanel implements ActionListener{
    
    Dimension d;
    Font script = new Font("Times new Roman", Font.BOLD, 14);
    boolean running = false;
    boolean done = false;
    
    int DOT_AREA = 24;
    int N_DOTS = 15;
    int Screen = DOT_AREA * N_DOTS;
    
    int ghosts = 12;
    int speed = 6;
    
    int nghosts = 6;
    int chances , points;
    int [] xdirection, ydirection;
    
    int [] Xghost, Yghost, ghost_X, ghost_Y, ghostRate;
    
    private Image heart, ghost;
    private Image up, down, left, right;
    
    int Xpacman, Ypacman, Hpacman, Vpacman;
    
    int xkey, ykey;
    
    int rangeSpeeds[] = {1, 2, 3, 4, 6, 8};
    int bigSpeed = 6;
    int nowSpeed = 3;
    
    short [] screeninfo; // Screen information
    Timer clock;
    
    private final short gameboard[] = {   // Used to create the background screen
        
        19, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
        17, 16, 16, 16, 16, 24, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        25, 24, 24, 24, 28, 0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
        0,  0,  0,  0,  0,  0, 17, 16, 16, 16, 16, 16, 16, 16, 20,
        19, 18, 18, 18, 18, 18, 16, 16, 16, 16, 24, 24, 24, 24, 20,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 16, 24, 16, 16, 16, 16, 20, 0,  0,  0,   0, 21,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 18, 18, 18, 18, 20,
        17, 24, 24, 28, 0, 25, 24, 24, 16, 16, 16, 16, 16, 16, 20,
        21, 0,  0,  0,  0,  0,  0,   0, 17, 16, 16, 16, 16, 16, 20,
        17, 18, 18, 22, 0, 19, 18, 18, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        17, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 20,
        25, 24, 24, 24, 26, 24, 24, 24, 24, 24, 24, 24, 24, 24, 28
    
    
}; 
    public Game(){
        ImageFill(); // Will load the images
        Variableload(); // Initializes the variables
        addKeyListener(new TAdapter());  // For Keyboard commands
        inital(true);
        beginGame();
        
    }
    
    private void ImageFill() {  // All the images needed for the game 
        down = new ImageIcon(this.getClass().getResource("/images/down.gif")).getImage();
        up = new ImageIcon(this.getClass().getResource("/images/up.gif")).getImage();
        left = new ImageIcon(this.getClass().getResource("/images/left.gif")).getImage();
        right = new ImageIcon(this.getClass().getResource("/images/right.gif")).getImage();
        ghost = new ImageIcon(this.getClass().getResource("/images/ghost.gif")).getImage();
        heart = new ImageIcon(this.getClass().getResource("/images/heart.png")).getImage();
    }
    
    
    private void Variableload(){
        screeninfo = new short [N_DOTS*N_DOTS];  // screen data
        d = new Dimension(400,400);
        Xghost = new int [ghosts];
        ghost_X = new int [ghosts];
        Yghost = new int [ghosts];
        ghost_Y = new int [ghosts];
        
        xdirection = new int[4];
        ydirection = new int [4];
        
        clock = new Timer (40, this);
        clock.start();
        
    }
    
    
    private void beginGame(){
        chances = 3;  // Number of lives remaining
        points = 0;   // Score will begin at zero
        startLevel();
        nghosts = 6; // Number of ghosts
        nowSpeed = 3;   // Speed of ghosts
    }
    
    private void startLevel(){
        int x;
        for (x = 0; x < N_DOTS * N_DOTS; x++){
            screeninfo[x] = gameboard[x]; // Will make sure that the gameboard will fit in the screen size(400 by 400)   
        }
        
    }
    
    private void gameplay(Graphics2D g2d){  // 2-D graphics
        if (done) { 
            finished();
        }
        else {
            goPacman();
            paintPacman();
            ghostmotion();
            checkPath();
            
        }
        
        public void goPacman(){
            int position; // Used to determine the position
            short scrn;   // This will connect to the screen info function
            
            if (Xpacman % DOT_AREA == 0 && Ypacman % DOT_AREA == 0) {// Starting position of the pacman
            position = Xpacman / DOT_AREA + N_DOTS * (int) (Ypacman / DOT_AREA); // Calculation to determine location of the pacman
            scrn = screeninfo[position];

            if ((scrn & 16) != 0) {  // 16 is a point on the game board which the pacman can eat
                screeninfo[position] = (short) (scrn & 15);
                points++; 
            }
            
            if (xkey != 0 || ykey != 0) { // Pacman keypad controls for the keyboard
                if (!((xkey == -1 && ykey == 0 && (scrn & 1) != 0) // Checks if the pacman is on the borders then the pacman cannot move in the corresponding direction.
                        || (xkey == 1 && ykey == 0 && (scrn & 4) != 0)
                        || (xkey == 0 && ykey == -1 && (scrn & 2) != 0)
                        || (xkey == 0 && ykey == 1 && (scrn & 8) != 0))) {
                    Hpacman= xkey;
                    Vpacman = ykey;
                }
            }
            
            // Check if the pacman is not moving
            if ((Hpacman == -1 && Vpacman == 0 && (scrn & 1) != 0)
                    || (Hpacman == 1 && Vpacman == 0 && (scrn & 4) != 0)
                    || (Hpacman == 0 && Vpacman == -1 && (scrn & 2) != 0)
                    || (Hpacman == 0 && Vpacman == 1 && (scrn & 8) != 0)) {
                Hpacman = 0;
                Vpacman = 0;
            }
            }
            
            // Speed of the pacman can be adjusted accordingly depending if it is in standstill or moving around
             Xpacman = Xpacman + speed * Hpacman;
             Ypacman = Ypacman + speed * Vpacman;
            
        }
        
        public void paintPacman(Graphics2D g2d){  // To check which key is pressed so that it can use the appropriate gif file for the pacman icon
            if (xkey == -1) {
        	g2d.drawImage(left, Xpacman + 1, Ypacman + 1, this); 
        } else if (xkey == 1) {
        	g2d.drawImage(right, Xpacman + 1, Ypacman + 1, this);
        } else if (ykey == -1) {
        	g2d.drawImage(up, Xpacman + 1, Ypacman + 1, this);
        } else {
        	g2d.drawImage(down, Xpacman + 1, Ypacman + 1, this);
        }
                
        }
        
        // Set the 
        public void ghostmotion(Graphics2D g2d) { 
            int position; // For the position, set the position of the ghost using block size
            int counter;
            
            for (int i = 0; i < nghosts; i++) {
            if (Xghost[i] % DOT_AREA == 0 && Yghost[i] % DOT_AREA == 0) {  // Ghosts move from one square and decide if they need to change direction
                position = Xghost[i] / DOT_AREA + N_DOTS * (int) (Yghost[i] / DOT_AREA);

                counter = 0;

                if ((screeninfo[position] & 1) == 0 && ghost_X[i] != 1) { // We use the border information 1,2,4,8 to determine how the ghost moves
                    xdirection[counter] = -1;
                    ydirection[counter] = 0;
                    counter++;
                }

                if ((screeninfo[position] & 2) == 0 && ghost_Y[i] != 1) {
                    xdirection[counter] = 0;
                    ydirection[counter] = -1;
                    counter++;
                }

                if ((screeninfo[position] & 4) == 0 && ghost_X[i] != -1) {
                    xdirection[counter] = 1;
                    ydirection[counter] = 0;
                    counter++;
                }

                if ((screeninfo[position] & 8) == 0 && ghost_Y[i] != -1) {
                    xdirection[counter] = 0;
                    ydirection[counter] = 1;
                    counter++;
                }

                if (counter == 0) {

                    if ((screeninfo[position] & 15) == 15) {
                        ghost_X[i] = 0;
                        ghost_Y[i] = 0;
                    } else {
                        ghost_X[i] = -ghost_X[i];  // Determines where the ghost is located on which position on the square
                        ghost_Y[i] = -ghost_Y[i];
                    }

                } else {

                    counter = (int) (Math.random() * counter);  // If there is no obstacle on the left and the ghost is not moving to the right then it will move to the left

                    if (counter > 3) {
                        counter = 3;
                    }

                    ghost_X[i] = xdirection[counter];
                    ghost_Y[i] = ydirection[counter];
                }

            }

            Xghost[i] = Xghost[i] + (ghost_X[i] * ghostRate[i]); // Will make the ghost move at a constant speed
            Yghost[i] = Yghost[i] + (ghost_Y[i] * ghostRate[i]);
            paintGhost(g2d, Xghost[i] + 1, Yghost[i] + 1); // Reloads the image of the ghost we want to draw

            if (Xpacman > (Xghost[i] - 12) && Xpacman < (Xghost[i] + 12) && Ypacman > (Yghost[i] - 12) && Ypacman < (Yghost[i] + 12) && running) { // If pacman touches the ghost it looses a life
                
                done = true; // One life in the game is gone
            }
        }
            
        }
        
        public void paintGhost(Graphics2D g2d, int x, int y) {
    	g2d.drawImage(ghost, x, y, this); // The gif file of the ghost will be outputted
        }
        
        public void checkPath () {  // Checks if there are anymore points for the pacman to eat 
            int c = 0;
            boolean gameover = true;

            while (c < N_DOTS * N_DOTS && gameover) {

            if ((screeninfo[c]) != 0) {
                gameover = false;
            }

            c++;
        }

        if (gameover) { // If all points are consumed then we move to the next level

            points += 50;

            if (nghosts < ghosts) {
                nghosts++;
            }

            if (nowSpeed < bigSpeed) {  // The ghost and speed increase by one
                nowSpeed++;
            }

            startLevel();
        }
    }
        
        private void finished(){ // If the pacman dies by touching a ghost it looses one life
            chances --; // The game continues until the pac man has no more lives
            if (chances == 0){
                running = false;
            }
            continueGame(); // Ghost and pac man are put back onto the screen
            
        }
        
    
        
    
    
    
    
    
    
    private void continueGame(){
        int dx = 1;
        int random;  // Will be used for the random speed for the ghost
        
        for (int x = 0; x < nghosts; x++){
            Yghost[x] = 4*DOT_AREA; 
            Xghost[x] = 4*DOT_AREA;
            ghost_Y[x] = 0;
            ghost_X[x] = dx;
            dx = -dx;
            
            random = (int) (Math.random() * (nowSpeed + 1));  // Will generate a random speed for the ghost.

            if (random > nowSpeed) {
                random = nowSpeed;
            }

            ghostRate[x] = rangeSpeeds[random]; // Use of an array to create a random speed.
        }
        
        
        Xpacman = 7 * DOT_AREA;
        Ypacman = 11 * DOT_AREA;
        Hpacman = 0;
        Vpacman = 0;
        done = false ; // This means the game is stil running
            
        }
        
        
    }

    
    
   
    public void drawComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        
}
    
    
    
    
    
    
    
    class TAdapter extends KeyAdapter{ // Keyboard control
        public void keypad(KeyEvent e){
            int input = e.getKeyCode();
            
            if (running){ // If the game is running it is controlled by the keypad
                if (input == KeyEvent.VK_LEFT){
                    xkey = -1;
                    ykey = 0;  
                }
                else if (input == KeyEvent.VK_RIGHT){
                    xkey = 1;
                    ykey = 0;  
                }
                else if (input == KeyEvent.VK_UP){
                    xkey = 0;
                    ykey = -1;  
                }
                else if (input == KeyEvent.VK_DOWN){
                    xkey = 0;
                    ykey = 1;  
                }
                else if (input == KeyEvent.VK_ESCAPE && clock.isRunning()){
                    running = false;
                    
                }
                
            } else {
                if (input == KeyEvent.VK_SPACE){   // The spacebar will begin the game. 
                    running = true;
                    beginGame();
                }
                
            }
            
        }
    } 
    
    
    

    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
