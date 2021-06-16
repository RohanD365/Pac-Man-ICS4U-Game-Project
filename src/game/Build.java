
package game;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class Build extends JFrame{

	public Build() {
		add(new Game());
	}
	
	
	public static void main(String[] args) {
		int width = 380;  // Dimensions for the screen
                int height = 420;
                
                Build pacman = new Build(); // Object pacman is created to display the main JFrame
		pacman.setVisible(true);
		pacman.setTitle("PAC-MAN");
		pacman.setSize(width,height);
		
                pacman.setDefaultCloseOperation(EXIT_ON_CLOSE); // When the X button is clicked the window will close.
		pacman.setLocationRelativeTo(null);
		
	}

}