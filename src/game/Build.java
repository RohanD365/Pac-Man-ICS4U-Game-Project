
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
                
                Build pacman = new Build();
		pacman.setVisible(true);
		pacman.setTitle("PAC-MAN");
		pacman.setSize(width,height);
		
                pacman.setDefaultCloseOperation(EXIT_ON_CLOSE);
		pacman.setLocationRelativeTo(null);
		
	}

}