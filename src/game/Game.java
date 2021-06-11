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
    
    int req_dx, req_dy;
    
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
    
    //private void gameplay(){
        //Collection of other functions to run the game  
    //}
    
    
    
    
    
    
    
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
                    req_dx = -1;
                    req_dy = 0;  
                }
                else if (input == KeyEvent.VK_RIGHT){
                    req_dx = 1;
                    req_dy = 0;  
                }
                else if (input == KeyEvent.VK_UP){
                    req_dx = 0;
                    req_dy = -1;  
                }
                else if (input == KeyEvent.VK_DOWN){
                    req_dx = 0;
                    req_dy = 1;  
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
