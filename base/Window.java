package base;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Window extends JFrame implements KeyListener {
	
	public Window() {
		super("Connect 4");
		setSize(350, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		add(new MainComponent());
		addKeyListener(this);
		setVisible(true);
		
	}

	private class MainComponent extends JComponent {
		public void paint(Graphics g) {
			g.setColor(Color.GRAY);
			g.fillRect(40, 40, 225, 190);
			Main.pieces.draw(g);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void keyPressed(KeyEvent e) {
		if(Main.gameRunning) {
			int key = e.getKeyCode();
			if(key == e.VK_RIGHT) {
				Main.pieces.moveCurSelect(true);
				//System.out.println("MOVING RIGHT");
			} else if(key == e.VK_LEFT) {
				Main.pieces.moveCurSelect(false);
				//System.out.println("MOVING LEFT");
			} else if(key == e.VK_ENTER) {
				Main.pieces.placePlayerPiece();
				//System.out.println("PLACING PIECE");
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
}
