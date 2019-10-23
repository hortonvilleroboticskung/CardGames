package Games;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUI {

	private JFrame guiFrame = new JFrame();
	private JPanel guiPanel = new JPanel(new GridLayout(3, 1));
	private boolean fourSingle = false;
	private boolean fourCoolChip = false;

	public GUI() {
		JButton fish = new JButton("Go Fish");
		fish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiPanel.removeAll();
				guiFrame.dispose();
				new Fish();

			}

		});

		JButton tacs = new JButton("Tic-Tac-Toe");
		tacs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				guiFrame.remove(guiPanel);
				JPanel decide = new JPanel(new GridLayout(2, 1));
				JButton one = new JButton("Single Player");
				one.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						guiFrame.dispose();
						new TicTac(true);
					}

				});
				JButton two = new JButton("Double player");
				two.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						guiFrame.dispose();
						new TicTac(false);
					}

				});
				decide.add(one);
				decide.add(two);
				guiFrame.add(decide);
				guiFrame.validate();
				guiFrame.repaint();
			}

		});

		JButton fours = new JButton("Connect Four");
		fours.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiFrame.remove(guiPanel);
				JPanel playerCount = new JPanel(new GridLayout(2, 1));
				JButton singlePlayer = new JButton("Single Player");
				JButton multiPlayer = new JButton("Multi Player");
				singlePlayer.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						fourSingle = true;
						guiFrame.remove(playerCount);
						fourChips();

					}

				});
				multiPlayer.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						guiFrame.remove(playerCount);
						fourChips();
					}

				});
				playerCount.add(singlePlayer);
				playerCount.add(multiPlayer);
				guiFrame.add(playerCount);
				guiFrame.validate();
			}

		});

		guiPanel.add(fish);
		guiPanel.add(tacs);
		guiPanel.add(fours);
		guiFrame.add(guiPanel);
		guiFrame.setSize(300, 300);
		guiFrame.setLocation(800, 200);
		guiFrame.setVisible(true);
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setResizable(false);
	}
	private void fourChips() {

		JPanel chips = new JPanel(new GridLayout(2, 1));
		JButton norm = new JButton("Normal Chips");
		JButton cool = new JButton("Awesome Chips");
		norm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiFrame.dispose();
				new ConnectFour(fourSingle,fourCoolChip);
			}

		});
		cool.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fourCoolChip = true;
				guiFrame.dispose();
				new ConnectFour(fourSingle,fourCoolChip);
			}

		});
		chips.add(norm);
		chips.add(cool);
		guiFrame.add(chips);
		guiFrame.validate();
	}
	
	public static boolean playAgain(JFrame f) {
		String[] arr = { "Yes", "No" };
		int choice = JOptionPane.showOptionDialog(f, "Wanna Play Again?", "Game Finished!",
				JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, arr, "No");

		return choice == 0;
	}



	public static void main(String[] args) {
		new GUI();
	}

}
