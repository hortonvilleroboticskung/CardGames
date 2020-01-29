package Games;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class GUI {

	private JFrame guiFrame = new JFrame();
	private JPanel guiPanel = new JPanel(new GridLayout(4, 1));
    boolean fourSingle = false;
    boolean fourCoolChip = false;

	public GUI() {

        JButton fish = new JButton("Go Fish");
        fish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

		JButton war = new JButton("War");
        war.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiFrame.dispose();
                new War();
            }
        });

		guiPanel.add(fish);
		guiPanel.add(tacs);
		guiPanel.add(fours);
		guiPanel.add(war);
		guiFrame.add(guiPanel);
		defaultFrameWork(guiFrame,300,300);
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
	
	public static void playAgain(JFrame f) {
		String[] arr = { "Yes", "No" };
		int choice = JOptionPane.showOptionDialog(f, "Wanna Play Again?", "Game Finished!",
				JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, arr, "No");
		if(choice == 0){
			f.dispose();
			new GUI();
		} else {
			System.exit(0);
		}
	}
	
	public static void defaultFrameWork(JFrame f, int x, int y) {
		f.setVisible(true);
		f.setLocation(500,200);
		if(x > 0 && y > 0){
		    f.setSize(x,y);
        } else {
		    f.pack();
        }
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

    public static void defaultFrameWork(JFrame f) {
        f.setVisible(true);
        f.setLocation(500,200);
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
	public static void main(String[] args) {
		new GUI();
	}
}