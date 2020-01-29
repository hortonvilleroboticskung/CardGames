package Games;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Fish{

	private JFrame frame = new JFrame();
	private JFrame logFrame = new JFrame();
	private JPanel logPanel = new JPanel();
	private Deck deck = new Deck();
	private Player p1 = new Player(new Hand(5,deck,false));
	private Hand com = new Hand(5,deck,false);
	private int turn = 1;

	public Fish() {
		com.pairSymbol();
		p1.hand.pairSymbol();
		JScrollPane scrollPane = new JScrollPane(logPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		BoxLayout l = new BoxLayout(logPanel, BoxLayout.Y_AXIS);
		logPanel.setLayout(l);
		for (Card c : p1.hand.getContents()) {
			clicker(c);
			p1.display.add(c.icon);
		}
		frame.setTitle("GO-Fish");
		frame.setContentPane(p1.display);
		GUI.defaultFrameWork(frame);

		logFrame.setContentPane(scrollPane);
		logFrame.setTitle("Turn Logs");
		GUI.defaultFrameWork(logFrame);
		logFrame.setBounds(130, 200, 375, 600);

	}

	private void comControl() {
		boolean paired = false;
		int choice = (int) (Math.random()*com.size()) ;
		logPanel.add(new JLabel("Computer Asked for: " + com.get(choice).symbol));
		for (Card c : p1.hand.getContents()) {
			if (com.get(choice).symbol.equals(c.symbol)) {
				com.remove(choice);
				logPanel.add(new JLabel(("and you had a " + c)));
				p1.hand.remove(c);
				paired = true;
				break;
			}
		}
		if ((deck.size() != 0) && !paired) {
			logPanel.add(new JLabel("No Match, so it drew a card"));
			com.draw();
			com.pairSymbol();
		}
	}

	private void clicker(Card c) {

		c.icon.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				boolean paired = false;
				logPanel.add(new JLabel("Turn " + turn++));
				logPanel.add(new JLabel("You chose the " + c));
				//Checks computers hand if they have card
				for (Card comCard : com.getContents()) {
					if (c.symbol.equals(comCard.symbol)) {
						logPanel.add(new JLabel("And they had the " + comCard));
						com.remove(comCard);
						p1.hand.remove(c);
						paired = true;
						break;
					}
				}
				//If no, draw a Card
				if (!paired && deck.size()!=0) {
					logPanel.add(new JLabel(("No Match, so you drew a " + deck.get(0))));
					p1.hand.draw();
					ArrayList<Card> pairArr = p1.hand.pairSymbol();
					if(pairArr.size()==2)logPanel.add(new JLabel("You matched a "+pairArr.get(0)+" with a "+pairArr.get(1)));
				}
				logPanel.validate();

				if (com.size() > 0 && p1.hand.size() > 0) comControl();

				if (p1.hand.size() == 0 || com.size() == 0) {
					if(p1.hand.size()==0) {
						JOptionPane.showMessageDialog(frame, "You matched all your cards first!", "Congratulations!",
								JOptionPane.PLAIN_MESSAGE);
					} else if (com.size()==0){
						JOptionPane.showMessageDialog(frame, "You lost to a robot loser ", "Beep-Boop",
								JOptionPane.PLAIN_MESSAGE);
					}
					GUI.playAgain(frame);
				}

				//Removes every icon and adds it back in to update the board
				p1.display.removeAll();
				for (Card c : p1.hand.getContents()) {
					if (c.icon.getMouseListeners().length == 0) {
						clicker(c);
					}
					p1.display.add(c.icon);
				}
				p1.display.repaint();
				frame.pack();
				//logPanel.validate();
				logFrame.validate();
				//System.out.println(p1);
			}

			@Override public void mouseEntered(MouseEvent arg0) {}@Override public void mouseExited(MouseEvent arg0) {}@Override public void mousePressed(MouseEvent arg) {}@Override public void mouseReleased(MouseEvent arg0) {}
		});
	}
}