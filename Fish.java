package Games;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Fish{

	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel();
	private JFrame logFrame = new JFrame();
	private JPanel logPanel = new JPanel();
	private Deck deck = new Deck();
	private ArrayList<Card> p1 = startHand(5);
	private ArrayList<Card> com = startHand(5);
	private int turn = 1;

	public Fish() {
		JScrollPane scrollPane = new JScrollPane(logPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		BoxLayout l = new BoxLayout(logPanel, BoxLayout.Y_AXIS);
		logPanel.setLayout(l);
		for (int i = 0; i < p1.size(); i++) {
			Card c = p1.get(i);
			fishyListener(c);
			panel.add(c.icon);
		}

		frame.setContentPane(panel);
		frame.pack();
		frame.setLocation(800, 200);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setTitle("GO-Fish");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		logFrame.setContentPane(scrollPane);
		logFrame.setVisible(true);
		logFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logFrame.setBounds(400, 200, 375, 600);
		logFrame.setResizable(false);
		logFrame.setTitle("Turn Logs");
	}

	private ArrayList<Card> startHand(int startNum) {
		ArrayList<Card> hand = new ArrayList<>();
		hand.addAll(deck.draw(startNum));
		pairing(hand);
		return hand;
	}

	private void pairing(ArrayList<Card> hand) {

		HashMap<String, Card> symbols = new HashMap<>();
		for (int aa = 0; aa < hand.size(); aa++) {
			if (!symbols.containsKey(hand.get(aa).symbol)) {
				symbols.put(hand.get(aa).symbol, hand.get(aa));
			} else {
				Card storedCard = symbols.get(hand.get(aa).symbol);
				if(hand.equals(p1)) {
					logPanel.add(new JLabel("You paired the " + storedCard + " and the " + hand.get(aa)));
				}
				symbols.remove(storedCard.symbol);
				hand.remove(aa);
				hand.remove(storedCard);
				aa -= 2;
			}
		}
	}

	private void comControl() {
		boolean paired = false;
		Random ran = new Random();
		int choice = ran.nextInt(com.size());
		logPanel.add(new JLabel("Computer Asked for: " + com.get(choice).symbol));
		for (int i = 0; i < p1.size(); i++) {
			if (com.get(choice).symbol.equals(p1.get(i).symbol)) {
				com.remove(choice);
				logPanel.add(new JLabel(("and you had a " + p1.get(i))));
				p1.remove(i);
				paired = true;
				break;
			}
		}

		if ((deck.size() != 0) && !paired) {
			logPanel.add(new JLabel("No Match, so it drew a card"));
			com.add(deck.draw());
			pairing(com);
		}
	}

	private void fishyListener(Card c) {

		c.icon.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				logPanel.add(new JLabel("Turn " + turn));
				turn++;
				logPanel.add(new JLabel("You chose the " + c));
				boolean paired = false;
				for (int i = 0; i < com.size(); i++) {
					if (c.symbol.equals(com.get(i).symbol)) {
						logPanel.add(new JLabel("And they had the " + com.get(i)));
						com.remove(i);
						p1.remove(c);
						paired = true;
						break;
					}
				}

				if (!paired) {
					logPanel.add(new JLabel(("No Match, so you drew a " + deck.get(0))));
					p1.add(deck.draw());
					pairing(p1);
				}
				logPanel.validate();
				if (p1.size() == 0) {
					JOptionPane.showMessageDialog(frame, "You matched all your cards first!", "Congratulations!",
							JOptionPane.PLAIN_MESSAGE);
					if(GUI.playAgain(frame)) {
						logFrame.dispose();
						frame.dispose();
						new GUI();
					} else {
						logFrame.dispose();
						frame.dispose();
						System.exit(0);
					}
				}
				// Computer's Turn Now!
				if (com.size() != 0) {
					comControl();
				}

				if (com.size() == 0) {
					JOptionPane.showMessageDialog(frame, "You lost to a robot loser ", "Beep-Boop",
							JOptionPane.PLAIN_MESSAGE);
					if(GUI.playAgain(frame)) {
						logFrame.dispose();
						frame.dispose();
						new GUI();
					} else {
						logFrame.dispose();
						frame.dispose();
						System.exit(0);
					}
				}

				panel.removeAll();
				for (int i = 0; i < p1.size(); i++) {
					Card just = p1.get(i);
					if (just.icon.getMouseListeners().length == 0) {
						fishyListener(just);
				}
					panel.add(just.icon);
				}
				panel.repaint();
				frame.pack();
				logPanel.validate();
				logFrame.validate();
				//System.out.println(com);
			}

			@Override public void mouseEntered(MouseEvent arg0) {}@Override public void mouseExited(MouseEvent arg0) {}@Override public void mousePressed(MouseEvent arg) {}@Override public void mouseReleased(MouseEvent arg0) {}
		});
	}

	public static void main(String[] args) {
		new Fish();
	}

}
