package Games;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ConnectFour {

	private JFrame frame = new JFrame();
	private JPanel panel = new JPanel(new GridLayout(6, 7));
	private ArrayList<Integer> redChips = new ArrayList<>();
	private ArrayList<Integer> yellowChips  = new ArrayList<>();
	private ImageIcon empty,red,yellow;
	private String redTurn,yellowTurn;
	private boolean single;
	private int turn = 0;

	public ConnectFour(boolean playerCount, boolean chipChoice) {
		single = playerCount;
		empty = chipChoice ? new ImageIcon(".\\ConnectFour\\emptyPoke.png") : new ImageIcon(".\\ConnectFour\\emptyTwo.png");
		red = chipChoice ? new ImageIcon(".\\ConnectFour\\redUltraBall.png") : new ImageIcon(".\\ConnectFour\\redBlueTwo.png");
		yellow = chipChoice ? new ImageIcon(".\\ConnectFour\\yellowMasterBall.png") : new ImageIcon(".\\ConnectFour\\yellowBlueTwo.png");
		redTurn = chipChoice ? "Ultra Ball's Turn" : "Red's Turn";
		yellowTurn = chipChoice ? "Master Ball's Turn" : "Yellow's Turn";
		startGame();	
	}

	private void startGame() {
		frame.setTitle(redTurn);
		for (int i = 0; i < 42; i++) {
			JLabel openChip = new JLabel(empty);
			openChip.setName(i + "");
			panel.add(openChip);
			frame.add(panel);
			openChip.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					//Gets the first open slot in that column
					int thisChip = stackedRow(Integer.parseInt(openChip.getName()));
					
					if (thisChip < 0) {
						JOptionPane.showMessageDialog(frame, "Chip Stack Overflow Error!", "Stop That",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JLabel replace = (JLabel) panel.getComponent(thisChip);

						if (turn % 2 == 0) {
							replace.setIcon(red);
							redChips.add(thisChip);
							frame.setTitle(yellowTurn);
						} else {
							replace.setIcon(yellow);
							yellowChips.add(thisChip);
							frame.setTitle(redTurn);
						}
						turn++;
							
						if (single) {
							thisChip = computerChoice();
							replace = (JLabel) panel.getComponent(thisChip);
							replace.setIcon(yellow);
							yellowChips.add(thisChip);
							frame.setTitle(redTurn);
							turn++;
						}
						
					}
					if(winCondition(redChips)) {
						winMessage(redTurn.split("'")[0]);
					} else if(winCondition(yellowChips)) {
						winMessage(yellowTurn.split("'")[0]);
					} else if (turn == 42) {
						winMessage("Nobody");
					}
				} @Override public void mouseEntered(MouseEvent arg0) {} @Override public void mouseExited(MouseEvent arg0) {} @Override public void mousePressed(MouseEvent arg0) {} @Override public void mouseReleased(MouseEvent arg0) {}

			});
		}
		GUI.defaultFrameWork(frame);
	}

	/*
	 * | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 
	 * | 7 | 8 | 9 | 10| 11| 12| 13| 
	 * | 14| 15| 16| 17| 18| 19| 20| 
	 * | 21| 22| 23| 24| 25| 26| 27| 
	 * | 28| 29| 30| 31| 32| 33| 34| 
	 * | 35| 36| 37| 38| 39| 40| 41|
	 */
	private int computerChoice() {
		//Finds the slot that would let computer win
		int yellowMove = counterPlay(yellowChips);
		//Finds the slot that would not let you win
		int redCounter = counterPlay(redChips);
		//Prioritize winning over not losing
		int choice = yellowMove == -1 ? (redCounter == -1 ? -1 : redCounter) : yellowMove;
		//if cant find a good spot, picks a random one
		if(choice == -1 ) {
			if(((JLabel) panel.getComponent(38)).getIcon().equals(empty)) {
				return 38;
			} else if(((JLabel) panel.getComponent(36)).getIcon().equals(empty)) {
				return 36;
			} else if(((JLabel) panel.getComponent(40)).getIcon().equals(empty)) {
				return 40;
			}
			int ran = stackedRow((int)(Math.random()*42));
			while(stackedRow(ran)==-1 || !(((JLabel) panel.getComponent(stackedRow(ran))).getIcon().equals(empty)) ) {
				ran = stackedRow((int)(Math.random()*42));
			}
			return ran;
		}
		return choice;
	}
	
	private int counterPlay(ArrayList<Integer> chipHolder) {
		int[][] numChecks = {
				//Chip,Row,Chip,Row,Chip,Row
				{1,0,2,0,3,0},
				{2,0,3,0,1,0},
				{3,0,1,0,2,0},
				{-1,0,-2,0,-3,0},
				{-7,-1,-14,-2,-21,-3},
				{-6,-1,-12,-2,-18,-3},
				{-18,-3,-12,-2,-6,-1},
				{-6,-1,-18,-3,-12,-2},
				{-8,-1,-16,-2,-24,-3},
				{-24,-3,-16,-2,-8,-1},
				{-8,-1,-24,-3,-16,-2},
		};
		for(int currentChip : chipHolder) {
			int row = (currentChip - currentChip % 7) / 7;
			for(int[] addArr : numChecks){
				if ((chipHolder.contains(currentChip+addArr[0]) && rowCheck(currentChip+addArr[0],row+addArr[1]))
						&& (chipHolder.contains(currentChip+addArr[2]) && rowCheck(currentChip+addArr[2],row+addArr[3]))
						&& (boundsCheck(currentChip+addArr[4])
						&& rowCheck(stackedRow(currentChip+addArr[4]),row+addArr[5])
						&& ((JLabel) panel.getComponent(currentChip+addArr[4])).getIcon().equals(empty))) {
					return currentChip+addArr[4];
				}
			}
		}
		return -1;
	}

	private boolean winCondition(ArrayList<Integer> chip) {
		int[][] numChecks = {
				{1,0,2,0,3,0}, // -
				{7,1,14,2,21,3}, // |
				{8,1,16,2,24,3}, // \
				{6,1,12,2,18,3} // /
		};
		for (int currentChip : chip) {
			int row = (currentChip - currentChip % 7) / 7;
			for(int[] a : numChecks) {
				if ((chip.contains(currentChip + a[0]) && rowCheck(currentChip + a[0], row+a[1]))
						&& (chip.contains(currentChip + a[2]) && rowCheck(currentChip + a[2], row+a[3]))
						&& (chip.contains(currentChip + a[4]) && rowCheck(currentChip + a[4], row+a[5]))) { // -
					return true;
				}
			}

		}
		return false;
	}
	private boolean rowCheck(int currentChip, int row) {
		return (currentChip - currentChip % 7) / 7 == row;
	}

	private boolean boundsCheck(int currentChip) {
		return currentChip > -1 && currentChip < 42;
	}

	private int stackedRow(int index) {
		int rowInColumn = 35 + index%7;
		if (index == -1) return -1;

		while (!((JLabel) panel.getComponent(rowInColumn)).getIcon().equals(empty)) {
			rowInColumn -= 7;
			if (rowInColumn < 0) {
				return -1;
			}
		}
		return rowInColumn;
	}

	private void winMessage(String color) {
		JOptionPane.showMessageDialog(frame, color + " has won the game", "Congrats!",
				JOptionPane.PLAIN_MESSAGE);
		GUI.playAgain(frame);
	}
}