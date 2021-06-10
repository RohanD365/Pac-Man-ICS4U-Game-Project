/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.ImageIcon;

import javax.swing.Timer;
import java.awt.Image;

public class Game extends JPanel implements ActionListener{
    
    Dimension s;
    Font script = new Font("Times new Roman", Font.BOLD, 14);
    boolean running = false;
    boolean done = false;
    
    int DOT_AREA = 23;
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
    
    int rangeSpeeds[] = {1,2,3,4,6,8};
    int bigSpeed = 6;
    int nowSpeed = 3;
    
    short [] scrninfo;
    Timer t;
    
    private final short screeninformation[] = {   // Used to create the background screen
        
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
        addKeyListener(new TAdapter());
        inital(true);
        beginGame();
        
    }
    
    private void ImageFill() {
        down = new ImageIcon(this.getClass().getResource("/images/down.gif")).getImage();
        up = new ImageIcon(this.getClass().getResource("/images/up.gif")).getImage();
        left = new ImageIcon(this.getClass().getResource("/images/left.gif")).getImage();
        right = new ImageIcon(this.getClass().getResource("/images/right.gif")).getImage();
        ghost = new ImageIcon(this.getClass().getResource("/images/ghost.gif")).getImage();
        heart = new ImageIcon(this.getClass().getResource("/images/heart.png")).getImage();
    }
    
    
    

    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
