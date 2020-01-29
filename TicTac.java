package Games;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TicTac {

	private JFrame ticFrame = new JFrame();
	private JPanel ticPanel = new JPanel(new GridLayout(3, 3));
	private ArrayList<Integer> xPos = new ArrayList<>();
	private ArrayList<Integer> oPos = new ArrayList<>();
	private boolean single;
	private int turn = 0;

	public TicTac(boolean singlePlayer) {
		single = singlePlayer;
		for (int i = 0; i < 9; i++) {
			JButton but = new JButton();
			but.setName(i + "");
			click(but);
			ticPanel.add(but);
		}
		ticFrame.add(ticPanel);
		GUI.defaultFrameWork(ticFrame,400,400);
	}

	private boolean winCheck(ArrayList<Integer> arr) {
		for (int currentBox : arr) {
			int row = (currentBox - currentBox % 3) / 3;
			int[][] addChecks = {
					{1, 0, 2, 0}, // -
					{3, 1, 6, 2}, // |
					{4, 1, 8, 2}, // /
					{2, 1, 4, 2}  // \
			};
			for (int[] a : addChecks) {
				if ((arr.contains(currentBox + a[0]) && rowCheck(currentBox + a[0], row + a[1]))
						&& (arr.contains(currentBox + a[2]) && rowCheck(currentBox + a[2], row + a[3]))) {
					return true;
				}
			}
		}
		return false;
	}

	public void click(JButton b) {
		b.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (b.getIcon() == null) {

					if (turn % 2 == 0) {
						b.setIcon(new ImageIcon(".\\tictac\\x.png"));
						xPos.add(Integer.parseInt(b.getName()));
					} else {
						b.setIcon(new ImageIcon(".\\tictac\\o.png"));
						oPos.add(Integer.parseInt(b.getName()));
					}

					turn++;

					if (winCheck(xPos)) {
						finished("X");
					} else if (winCheck(oPos)) {
						finished("O");
					} else if (turn == 9) {
						finished("Nobody :(");
					}

					if (single) {
						int choice = comChoice();
						JButton but = (JButton) ticPanel.getComponent(choice);
						but.setIcon(new ImageIcon(".\\tictac\\o.png"));
						oPos.add(Integer.parseInt(but.getName()));
						turn++;
						if (winCheck(oPos)) finished("O");
					}

				}
			}

		});
	}

	private int comChoice() {
		int comWin = counterPlay(oPos);
		int counterWin = counterPlay(xPos);
		int choice = comWin == -1 ? (counterWin == -1 ? -1 : counterWin) : comWin;
		while (xPos.contains(choice) || oPos.contains(choice) || choice == -1) {
			choice = (int)(Math.random()*9);
		}
		return choice;
	}

	private int counterPlay(ArrayList<Integer> player) {
		for (int currentBox : player) {
			int row = (currentBox - currentBox % 3) / 3;
			int[][] addCheck = {
					{1, 0, 2, 0}, // -
					{2, 0, 1, 0},
					{-1, 0, -2, 0},
					{3, 1, 6, 2}, // |
					{6, 2, 3, 1},
					{-3, -1, -6, -2},
					{4, 1, 8, 2}, // \
					{-4, -1, -8, -2},
					{8, 2, 4, 1},
					{2, 1, 4, 2},// /
					{-2, -1, -4, -2},
					{4, 2, 2, 1}
			};
			for (int[] a : addCheck) {
				if ((player.contains(currentBox + a[0]) && rowCheck(currentBox + a[0], row + a[1]))
						&& (boundsCheck(currentBox + a[2]) && rowCheck(currentBox + a[2], row + a[3])
						&& (((JButton) ticPanel.getComponent(currentBox + a[2])).getIcon()) == null)) {
					return currentBox + a[2];
				}
			}

		}
		return -1;
	}

	private boolean rowCheck(int currentBox, int row) {
		return ((currentBox - currentBox % 3) / 3) == row;
	}

	private boolean boundsCheck(int currentChip) {
		return currentChip > -1 && currentChip < 9;
	}

	private void finished(String winner) {
		JOptionPane.showMessageDialog(ticFrame, "Winner is " + winner, "The Winner is...",
				JOptionPane.INFORMATION_MESSAGE);
		GUI.playAgain(ticFrame);
	}
}