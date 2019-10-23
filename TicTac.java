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
	// Never make this stuff static, it breaks everything
	private JFrame ticFrame = new JFrame();
	private JPanel ticPanel = new JPanel(new GridLayout(3, 3));
	private ArrayList<Integer> xPos = new ArrayList<>();
	private ArrayList<Integer> oPos = new ArrayList<>();
	private boolean single;
	private boolean stop = false;
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
		ticFrame.setSize(400, 400);
		ticFrame.setLocation(800, 200);
		ticFrame.setVisible(true);
		ticFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ticFrame.setResizable(false);
	}

	/*
	 * 0 1 2 3 4 5 6 7 8
	 */
	private boolean rowCheck(int currentBox, int row) {
		return ((currentBox - currentBox % 3) / 3) == row;
	}

	private boolean boundsCheck(int currentChip) {
		return currentChip > -1 && currentChip < 9;
	}

	private boolean winCheck(ArrayList<Integer> arr) {
		for (int currentBox : arr) {
			int row = (currentBox - currentBox % 3) / 3;

			if ((arr.contains(currentBox + 1) && rowCheck(currentBox + 1, row))
					&& (arr.contains(currentBox + 2) && rowCheck(currentBox + 2, row))) {
				return true;
			}
			if (arr.contains(currentBox + 3) && rowCheck(currentBox + 3, row + 1)
					&& (arr.contains(currentBox + 6) && rowCheck(currentBox + 6, row + 2))) {
				return true;
			}
			if (arr.contains(currentBox + 4) && rowCheck(currentBox + 4, row + 1)
					&& (arr.contains(currentBox + 8) && rowCheck(currentBox + 8, row + 2))) {
				return true;
			}
			if (arr.contains(currentBox + 2) && rowCheck(currentBox + 2, row + 1)
					&& (arr.contains(currentBox + 4) && rowCheck(currentBox + 4, row + 2))) {
				return true;
			}
		}
		return false;
	}

	private void finished(String winner) {
		if (!stop) {
			JOptionPane.showMessageDialog(ticFrame, "Winner is " + winner, "The Winner is...",
					JOptionPane.INFORMATION_MESSAGE);
			if (GUI.playAgain(ticFrame)) {
				ticFrame.dispose();
				new GUI();
			} else {
				ticFrame.dispose();
				System.exit(0);
			}
		}
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
					if (single && turn != 9) {
						int choice = comChoice();
						JButton but = (JButton) ticPanel.getComponent(choice);
						but.setIcon(new ImageIcon(".\\tictac\\o.png"));
						oPos.add(Integer.parseInt(but.getName()));
						turn++;
					}
					if (!stop && winCheck(xPos)) {
						finished("X");
						stop = true;
					} else if (!stop && winCheck(oPos)) {
						finished("O");
						stop = true;
					}
						System.out.println(turn);
					if (!stop && turn == 9) {
						stop = true;
						JOptionPane.showMessageDialog(ticFrame, "Nobody won losers", "Sad... :(",
								JOptionPane.ERROR_MESSAGE);
						if (GUI.playAgain(ticFrame)) {
							ticFrame.dispose();
							new GUI();
						} else {
							ticFrame.dispose();
							System.exit(0);
						}
					}
				}
			}

		});
	}

	private int comChoice() {
		int comWin = counterPlay(oPos);
		int counterWin = counterPlay(xPos);
		int choice = comWin == -1 ? (counterWin == -1 ? -1 : counterWin) : comWin;
		if (xPos.contains(choice) || oPos.contains(choice) || choice == -1) {
			if (!xPos.contains(1) && !oPos.contains(1))
				return 1;
			if (!xPos.contains(3) && !oPos.contains(3))
				return 3;
			if (!xPos.contains(5) && !oPos.contains(5))
				return 5;
			if (!xPos.contains(7) && !oPos.contains(7))
				return 7;
			if (!xPos.contains(4) && !oPos.contains(4))
				return 4;
			if (!xPos.contains(0) && !oPos.contains(0))
				return 0;
			if (!xPos.contains(2) && !oPos.contains(2))
				return 2;
			if (!xPos.contains(6) && !oPos.contains(6))
				return 6;
			if (!xPos.contains(8) && !oPos.contains(8))
				return 8;
		}
		return choice;
	}

	private int counterPlay(ArrayList<Integer> player) {
		for (int currentBox : player) {
			int row = (currentBox - currentBox % 3) / 3;
			if ((player.contains(currentBox + 1) && rowCheck(currentBox + 1, row)) // X X _
					&& (boundsCheck(currentBox + 2) && rowCheck(currentBox + 2, row)
					&& (((JButton) ticPanel.getComponent(currentBox + 2)).getIcon()) == null)) {
				return currentBox + 2;
				
			} else if ((player.contains(currentBox + 2) && rowCheck(currentBox + 2, row)) // X _ X
					&& (boundsCheck(currentBox + 1) && rowCheck(currentBox + 1, row)
					&& (((JButton) ticPanel.getComponent(currentBox + 1)).getIcon()) == null)) {
				return currentBox + 1;
				
			} else if ((player.contains(currentBox - 1) && rowCheck(currentBox - 1, row)) // _ X X
					&& (boundsCheck(currentBox - 2) && rowCheck(currentBox - 2, row)
					&& (((JButton) ticPanel.getComponent(currentBox - 2)).getIcon()) == null)) {
				return currentBox - 2;
				
			} else if ((player.contains(currentBox + 3) && rowCheck(currentBox + 3, row + 1)) // | X X _
					&& (boundsCheck(currentBox + 6) && rowCheck(currentBox + 6, row + 2)
					&& (((JButton) ticPanel.getComponent(currentBox + 6)).getIcon()) == null)) {
				return currentBox + 6;
				
			} else if ((player.contains(currentBox + 6) && rowCheck(currentBox + 6, row + 2)) // | X _ X
					&& (boundsCheck(currentBox + 3) && rowCheck(currentBox + 3, row + 1)
					&& (((JButton) ticPanel.getComponent(currentBox + 3)).getIcon()) == null)) {
				return currentBox + 3;
				
			} else if ((player.contains(currentBox - 3) && rowCheck(currentBox - 3, row - 1)) // | _ X X
					&& (boundsCheck(currentBox - 6) && rowCheck(currentBox - 6, row - 2)
					&& (((JButton) ticPanel.getComponent(currentBox - 6)).getIcon()) == null)) {
				return currentBox - 6;
				
			} else if (player.contains(0) && player.contains(4) && (((JButton) ticPanel.getComponent(8)).getIcon()) == null) {
				return 8;
			} else if (player.contains(8) && player.contains(4) && (((JButton) ticPanel.getComponent(0)).getIcon()) == null) {
				return 0;
			} else if (((player.contains(8) && player.contains(0)) || (player.contains(2) && player.contains(6))) && (((JButton) ticPanel.getComponent(4)).getIcon()) == null) {
				return 4;
			} else if (player.contains(2) && player.contains(4) && (((JButton) ticPanel.getComponent(6)).getIcon()) == null) {
				return 6;
			} else if (player.contains(6) && player.contains(4) && (((JButton) ticPanel.getComponent(2)).getIcon()) == null) {
				return 2;
			}
		}
		return -1;
	}
}