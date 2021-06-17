/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package game;

import java.awt.*;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Game extends JPanel implements ActionListener {

    Dimension d; // Dimensions for the background of the screen
    private final Font script = new Font("Times new Roman", Font.BOLD, 14);
    
    boolean running = false; // Checks if the game is running 
    boolean done = false;   // Checks if the pacman is alive

    int DOT_AREA = 24; // Size for the white dots
    int N_DOTS = 15;  // Number of white dots in one row
    int Screen = N_DOTS * DOT_AREA; // Screen size using the area of the white dots and the number of white dots
    
    int ghosts = 12; // Maximum number of ghosts
    int speed = 6; // Speed of the pacman

    int nghosts = 6; // Number of ghosts at the beggining
    int chances, points; // Number of lives and the total score
    int[] xdirection, ydirection;  // Used for the position of the ghosts
    
    int[] Xghost, Yghost, ghost_X, ghost_Y, ghostRate; // Determines number and position of the ghosts

    private Image heart, ghost;    
    private Image up, down, left, right;

    int Xpacman, Ypacman, Hpacman, Vpacman; // First two variables store the x and y coordinates. The last two variables are the changes in horizontal and vertical directions.
    
    int xkey, ykey; // Used for the keyboard arrow keys in the class TAdapter extends KeyAdapter

    private final short gameboard[] = { // Used to create the background screen, each number represents a specific point on the gameboard.
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

    private final int rangeSpeeds[] = {1, 2, 3, 4, 6, 8}; // available speeds for the ghost
    private int nowSpeed = 3; // Current speed for the ghost
    
    private short[] screeninfo; // Screen information
    private Timer clock;  

    public Game() {

        ImageFill(); // Will load the images
        Variableload(); // Initializes the variables
        addKeyListener(new TAdapter()); // For Keyboard commands
        setFocusable(true);
        beginGame();  // Starts the game
    }
    
    
    private void ImageFill() {  // All the gif files and images needed for the game. 
        
        down = new ImageIcon(this.getClass().getResource("/images/down.gif")).getImage();
        up = new ImageIcon(this.getClass().getResource("/images/up.gif")).getImage();
        left = new ImageIcon(this.getClass().getResource("/images/left.gif")).getImage();
        right = new ImageIcon(this.getClass().getResource("/images/right.gif")).getImage();
        ghost = new ImageIcon(this.getClass().getResource("/images/ghost.gif")).getImage();
        heart = new ImageIcon(this.getClass().getResource("/images/heart.png")).getImage();
        
        
        
    }
       private void Variableload() {

        screeninfo = new short [N_DOTS * N_DOTS];  // screen data
        d = new Dimension(400, 400);
        
        Xghost = new int[ghosts];      // Data for the movement of the ghosts.
        ghost_X = new int[ghosts];
        Yghost = new int[ghosts];
        
        ghost_Y = new int[ghosts];
        ghostRate = new int[ghosts];
        
        xdirection = new int[4];
        ydirection = new int[4];
        
        clock = new Timer(40, this);
        clock.start();
    }

    private void gameplay(Graphics2D g2d) { // 2-D graphics for the pac-man icon

        if (done) {

            finished();

        } else {

            goPacman();
            paintPacman(g2d);
            ghostmotion(g2d);
            checkPath(g2d);
        }
    }

    private void showIntroScreen(Graphics2D g2d) {  // Text that will be shown on the introduction screen.
 
    	String start = "Press the SPACE key to start the game!"; // Text with the colour yellow
        String lives = "You have three lives to win the game!";
        String watch = "Watch out for ghosts, they have a random speed!";
        g2d.setColor(Color.yellow);
        g2d.drawString(start, (Screen)/4, 150);
        g2d.drawString(lives, 90, 220);
        g2d.drawString(watch, 47, 265);
        
    }

    private void drawpoints(Graphics2D g) {
        g.setFont(script);
        g.setColor(new Color(5, 181, 79)); // Used to keep track of the score
        String score = "Score: " + points;
        g.drawString(score, Screen / 2 + 96, Screen + 16);

        for (int i = 0; i < chances; i++) { // In a loop we check how many lives are left to display the correct numeber of hearts on the screen.
            g.drawImage(heart, i * 28 + 8, Screen + 1, this);
        }
    }

    public void checkPath(Graphics2D g2d) { // Checks if there are anymore points for the pacman to eat.

        int c = 0;
        boolean gameover = true;

        while (c < N_DOTS * N_DOTS && gameover) {

            if ((screeninfo[c] & 48) != 0) {
                gameover = false;
            }
            

            c++;
        }

        if (gameover) { // If all points are consumed then the congratulations message is outputted. 
            boolean working = true;
            
            if (working){
            
            timeDelay(1000);
            Font myFont = new Font ("Courier New", 1, 13);  // Font for congratulations message.
            
            g2d.setFont(myFont);
            String end = "Congratulations! You have won the game!";
            g2d.setColor(Color.yellow);
            g2d.drawString(end, 30, 150);
            g2d.dispose();
             
            timeDelay(2000); 
            finished();
        }
        
        
        
    }
    }
    
    public void timeDelay(long t) {
    try {
        Thread.sleep(t);   // This method is used to create a delay.
    } catch (InterruptedException e) {}
    }

    private void finished() { // If the pacman dies by touching a ghost it looses one life

    	chances--;

        if (chances == 0) { // The game continues until the pac man has no more lives
            running = false;
        }

        continueGame(); // Ghost and pac man are put back onto the screen
    }

    public void ghostmotion(Graphics2D g2d) { 

        int position;
        int counter;

        for (int i = 0; i < nghosts; i++) { // Ghosts move from one square and decide if they need to change direction
            if (Xghost[i] % DOT_AREA == 0 && Yghost[i] % DOT_AREA == 0) { // They move only if they finished moving one square.
                position = Xghost[i] / DOT_AREA + N_DOTS * (int) (Yghost[i] / DOT_AREA); // Determines where the ghost is located.

                counter = 0;

                if ((screeninfo[position] & 1) == 0 && ghost_X[i] != 1) { // We use the border information 1,2,4,8 to determine how the ghost moves.
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
                        ghost_X[i] = -ghost_X[i]; // Determines where the ghost is located on which position on the square
                        ghost_Y[i] = -ghost_Y[i];
                    }

                } else {

                    counter = (int) (Math.random() * counter); // If there is no obstacle on the left and the ghost is not moving to the right then it will move to the left

                    if (counter > 3) {
                        counter = 3;
                    }

                    ghost_X[i] = xdirection[counter];
                    ghost_Y[i] = ydirection[counter];
                }

            }

            Xghost[i] = Xghost[i] + (ghost_X[i] * ghostRate[i]); // Will make the ghost move at a constant speed
            Yghost[i] = Yghost[i] + (ghost_Y[i] * ghostRate[i]); // Reloads the image of the ghost we want to draw
            paintGhost(g2d, Xghost[i] + 1, Yghost[i] + 1);

            if (Xpacman > (Xghost[i] - 12) && Xpacman < (Xghost[i] + 12) && Ypacman > (Yghost[i] - 12) && Ypacman < (Yghost[i] + 12) && running) { // If there is a collision between the ghost and the pacman.

                done = true; // One life in the game is gone
            }
        }
    }

    private void paintGhost(Graphics2D g2d, int x, int y) {
    	g2d.drawImage(ghost, x, y, this); // The gif file of the ghost will be outputted
        }

    private void goPacman() {

        int position; // Used to determine the position
        short scrn;  // This will connect to the screen info function

        if (Xpacman % DOT_AREA == 0 && Ypacman % DOT_AREA == 0) {// Starting position of the pacman
            position = Xpacman / DOT_AREA + N_DOTS * (int) (Ypacman / DOT_AREA); // Calculation to determine location of the pacman
            scrn = screeninfo[position];

            if ((scrn & 16) != 0) { // 16 is a point on the game board which the pacman can eat
                screeninfo[position] = (short) (scrn & 15);
                points++;
            }

            if (xkey != 0 || ykey != 0) { // The pacman stops if it cannot move further in the current direction.
                if (!((xkey == -1 && ykey == 0 && (scrn & 1) != 0) // Checks if the pacman is on the borders then the pacman cannot move in the corresponding direction.
                        || (xkey == 1 && ykey == 0 && (scrn & 4) != 0)
                        || (xkey == 0 && ykey == -1 && (scrn & 2) != 0)
                        || (xkey == 0 && ykey == 1 && (scrn & 8) != 0))) {
                    Hpacman = xkey;
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

    private void paintPacman(Graphics2D g2d) { // To check which key is pressed so that it can use the appropriate gif file for the pacman icon

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

    private void drawscreen(Graphics2D g2d) {

        short i = 0;
        int x, y;

        for (y = 0; y < Screen; y += DOT_AREA) { // Used for screen size and block size
            for (x = 0; x < Screen; x += DOT_AREA) {

                g2d.setColor(new Color(0,72,251)); // Blue colour
                g2d.setStroke(new BasicStroke(5));  // Thickness of the border
                
                if ((gameboard[i] == 0)) {  // If the section of the gameboard is zero it is coloured with blue
                	g2d.fillRect(x, y, DOT_AREA, DOT_AREA);
                 }

                if ((screeninfo[i] & 1) != 0) {  // 1 is for the left border
                    g2d.drawLine(x, y, x, y + DOT_AREA - 1);
                }

                if ((screeninfo[i] & 2) != 0) {  // 2 is for the top border
                    g2d.drawLine(x, y, x + DOT_AREA - 1, y);
                }

                if ((screeninfo[i] & 4) != 0) {  // 4 is for the right border
                    g2d.drawLine(x + DOT_AREA - 1, y, x + DOT_AREA - 1,
                            y + DOT_AREA - 1);
                }

                if ((screeninfo[i] & 8) != 0) {  // 8 is for the bottom border
                    g2d.drawLine(x, y + DOT_AREA - 1, x + DOT_AREA - 1,
                            y + DOT_AREA - 1);
                }

                if ((screeninfo[i] & 16) != 0) {  // 16 is for the white dots
                    g2d.setColor(new Color(255,255,255));
                    g2d.fillOval(x + 10, y + 10, 6, 6);
               }

                i++;
            }
        }
    }

    private void beginGame() {

    	chances = 3; // Number of lives reamaining
        points = 0; // Score wil begin at zero
        startLevel();
        nghosts = 6; // Number of ghosts
        nowSpeed = 1; // Speed of ghosts
    }

    private void startLevel() {

        int x;
        for (x = 0; x < N_DOTS * N_DOTS; x++) {
            screeninfo[x] = gameboard[x]; // Will make sure that the gameboard will fit in the screen size(400 by 400)   
        }

        continueGame();
    }

    private void continueGame() {

    	int xdirection = 1;
        int random; // Will be used for the random speed for the ghost

        for (int i = 0; i < nghosts; i++) {

            Yghost[i] = 4 * DOT_AREA; //start position
            Xghost[i] = 4 * DOT_AREA;
            ghost_Y[i] = 0;
            ghost_X[i] = xdirection;
            xdirection = -xdirection;
            random = (int) (Math.random() * (nowSpeed + 1)); // Will generate a random speed for the ghost.


            if (random > nowSpeed) {
                random = nowSpeed;
            }

            ghostRate[i] = rangeSpeeds[random]; // Use of an array to create a random speed.
        }

        Xpacman = 7 * DOT_AREA;  //Starting position
        Ypacman = 11 * DOT_AREA;
        Hpacman  = 0;	
        Vpacman = 0;
        xkey = 0;		// reset the keypad controls
        ykey = 0;
        done = false; // This means the game is still running
    }

 @Override
    public void paintComponent(Graphics g) {  
        
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black); // The background for the game is black
        g2d.fillRect(0, 0, d.width, d.height);

        drawscreen(g2d); // The gameboard will be drawn on top of the background
        drawpoints(g2d); // The score will also be shown

        if (running) {
            gameplay(g2d);
        } else {
            showIntroScreen(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }


    //controls
    class TAdapter extends KeyAdapter { // Keyboard control

        @Override
        public void keyPressed(KeyEvent e) {

            int input = e.getKeyCode();

            if (running) { // The game is running it is controlled by the keypad
                if (input == KeyEvent.VK_LEFT) {  // For each condition the xkey and ykey values represent the gif file that will be outputted
                    xkey = -1;
                    ykey = 0;
                } else if (input == KeyEvent.VK_RIGHT) {
                    xkey = 1;
                    ykey = 0;
                } 
                 else if (input == KeyEvent.VK_DOWN) {
                    xkey = 0;
                    ykey = 1;
                }else if (input == KeyEvent.VK_UP) {
                    xkey = 0;
                    ykey = -1;
                } else if (input == KeyEvent.VK_ESCAPE && clock.isRunning()) {
                    running = false;
                } 
            } else {
                if (input == KeyEvent.VK_SPACE) { // The spacebar will begin the game.
                    running = true;
                    beginGame();
                }
            }
        }
}

	
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); // performs a request to erase and redraw the components after a small delay of time
    }
    
		
	}
