package Games;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	private ArrayList<Integer> yellowChips = new ArrayList<>();
	private int turn = 0;
	private ImageIcon empty;
	private ImageIcon red;
	private ImageIcon yellow;
	private boolean single;
	private boolean stop = false;

	public ConnectFour(boolean playerCount, boolean chipChoice) {
		single = playerCount;
		empty = chipChoice ? new ImageIcon(".\\ConnectFour\\emptyPoke.png") : new ImageIcon(".\\ConnectFour\\emptyTwo.png");
		red = chipChoice ? new ImageIcon(".\\ConnectFour\\redUltraBall.png") : new ImageIcon(".\\ConnectFour\\redBlueTwo.png");
		yellow = chipChoice ? new ImageIcon(".\\ConnectFour\\yellowMasterBall.png") : new ImageIcon(".\\ConnectFour\\yellowBlueTwo.png");
		startGame();
		
	}

	private void startGame() {
		for (int i = 0; i < 42; i++) {
			JLabel openChip = new JLabel(empty);
			openChip.setName(i + "");
			frame.setTitle("Red's Turn");
			panel.add(openChip);
			frame.add(panel);
			openChip.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					int thisChip = stackedRow(Integer.parseInt(openChip.getName()));

					if (thisChip < 0) {
						JOptionPane.showMessageDialog(frame, "Chip Stack Overflow Error!", "Stop That",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JLabel replace = (JLabel) panel.getComponent(thisChip);

						if (turn % 2 == 0) {
							replace.setIcon(red);
							redChips.add(thisChip);
							frame.setTitle("Yellow's Turn");
						} else {
							replace.setIcon(yellow);
							yellowChips.add(thisChip);
							frame.setTitle("Red's Turn");
						}
						turn++;
							
						if (single) {
							thisChip = computerChoice();
							replace = (JLabel) panel.getComponent(thisChip);
							replace.setIcon(yellow);
							yellowChips.add(thisChip);
							frame.setTitle("Red's Turn");
							turn++;
						}
						
					}

					if(winCondition(redChips)) {
						winMessage("Red");
						stop = true;
					} else if(!stop && winCondition(yellowChips)) {
						winMessage("Yellow");
						stop = true;
					}
					
					if (!stop && turn == 42) {
						JOptionPane.showMessageDialog(frame, "Didn't know this was possible", "No Winner",
								JOptionPane.ERROR_MESSAGE);
						frame.dispose();
					}
				} @Override public void mouseEntered(MouseEvent arg0) {} @Override public void mouseExited(MouseEvent arg0) {} @Override public void mousePressed(MouseEvent arg0) {} @Override public void mouseReleased(MouseEvent arg0) {}

			});
		}
		frame.setLocation(700, 200);
		frame.setSize(710, 640);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
	}

	/*
	 * | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 
	 * | 7 | 8 | 9 | 10| 11| 12| 13| 
	 * | 14| 15| 16| 17| 18| 19| 20| 
	 * | 21| 22| 23| 24| 25| 26| 27| 
	 * | 28| 29| 30| 31| 32| 33| 34| 
	 * | 35| 36| 37| 38| 39| 40| 41|
	 */

	private int stackedRow(int index) {
		int labelCheck = -1;
		if (index == -1) return -1;
		if (index % 7 == 0)
			labelCheck = 35;
		if (index % 7 == 1)
			labelCheck = 36;
		if (index % 7 == 2)
			labelCheck = 37;
		if (index % 7 == 3)
			labelCheck = 38;
		if (index % 7 == 4)
			labelCheck = 39;
		if (index % 7 == 5)
			labelCheck = 40;
		if (index % 7 == 6)
			labelCheck = 41;

		while (!((JLabel) panel.getComponent(labelCheck)).getIcon().equals(empty)) {
			labelCheck = labelCheck - 7;
			if (labelCheck < 0) {
				return -1;
			}
		}
		return labelCheck;
	}

	private void winMessage(String color) {
		JOptionPane.showMessageDialog(frame, color + " has won the game", "Congrats!",
				JOptionPane.PLAIN_MESSAGE);
		if(GUI.playAgain(frame)) {
			frame.dispose();
			new GUI();
		} else {
			frame.dispose();
			System.exit(0);
		}
	}
	
	private boolean rowCheck(int currentChip, int row) {
		return (currentChip - currentChip % 7) / 7 == row;
	}
	
	private boolean boundsCheck(int currentChip) {
		return currentChip > -1 && currentChip < 42;
	}
	
	private boolean winCondition(ArrayList<Integer> chip) {

		for (int currentChip : chip) {

			int row = (currentChip - currentChip % 7) / 7;

			if ((chip.contains(currentChip + 1) && rowCheck(currentChip + 1, row))
					&& (chip.contains(currentChip + 2) && rowCheck(currentChip + 2, row))
					&& (chip.contains(currentChip + 3) && rowCheck(currentChip + 3, row))) { // -
					return true;
			}
			if (chip.contains(currentChip + 7) && chip.contains(currentChip + 14) && chip.contains(currentChip + 21)) { // |
					return true;
			}
			if ((chip.contains(currentChip + 8) && rowCheck(currentChip + 8,row+1))
					&& (chip.contains(currentChip + 16) && rowCheck(currentChip + 16, row+2))
					&& (chip.contains(currentChip + 24) && rowCheck(currentChip + 24, row+3))) { // \
					return true;
			}
			if ((chip.contains(currentChip + 6) && rowCheck(currentChip + 6, row+1))
					&& (chip.contains(currentChip + 12) && rowCheck(currentChip + 12, row+2))
					&& (chip.contains(currentChip + 18) && rowCheck(currentChip + 18, row+3))) { // /
					return true;
			}
		}
		return false;
	}

	private int computerChoice() {
		int yellowMove = counterPlay(yellowChips);
		int redCounter = counterPlay(redChips);
		//System.out.println("Yellow Move: "+yellowMove+"\nRed Counter:"+redCounter);
		int choice = yellowMove == -1 ? (redCounter == -1 ? -1 : redCounter) : yellowMove;
		//System.out.println("Choice: "+choice);
		if(choice == -1 ) { //Give random moves until red has 2 chips
			if(((JLabel) panel.getComponent(38)).getIcon().equals(empty)) {
				return 38;
			} else if(((JLabel) panel.getComponent(36)).getIcon().equals(empty)) {
				return 36;
			} else if(((JLabel) panel.getComponent(40)).getIcon().equals(empty)) {
				return 40;
			}
			int ran = stackedRow(new Random().nextInt(42));
			while(stackedRow(ran)==-1 || !(((JLabel) panel.getComponent(stackedRow(ran))).getIcon().equals(empty)) ) {
				ran = stackedRow(new Random().nextInt(42));
			}
			//System.out.println("Randomized: "+ ran);
			return ran;
		} 
		//System.out.println("Final Decision: "+choice+"\n------------");
		return choice;
	}
	
	private int counterPlay(ArrayList<Integer> chipHolder) {
		for(int currentChip : chipHolder) {
			int row = (currentChip - currentChip % 7) / 7;
			if( (chipHolder.contains(currentChip+1) && rowCheck(currentChip+1,row)) // -
					&& (chipHolder.contains(currentChip+2) && rowCheck(currentChip+2,row))
					&& (boundsCheck(currentChip+3) && rowCheck(stackedRow(currentChip+3),row) && ((JLabel) panel.getComponent(currentChip+3)).getIcon().equals(empty))  ){
						return currentChip+3;
			}
			if( (chipHolder.contains(currentChip+2) && rowCheck(currentChip+2,row)) // -
					&& (chipHolder.contains(currentChip+3) && rowCheck(currentChip+3,row))
					&& (boundsCheck(currentChip+1) && rowCheck(stackedRow(currentChip+1),row) && ((JLabel) panel.getComponent(currentChip+1)).getIcon().equals(empty))  ){
						return currentChip+1;
			}
			if( (chipHolder.contains(currentChip+1) && rowCheck(currentChip+1,row)) // -
					 && (chipHolder.contains(currentChip+3) && rowCheck(currentChip+3,row))
					 && (boundsCheck(currentChip+2) && rowCheck(stackedRow(currentChip+2),row) && ((JLabel) panel.getComponent(currentChip+2)).getIcon().equals(empty))  ){
						return currentChip+2;
			}
			if( (chipHolder.contains(currentChip-1) && rowCheck(currentChip-1,row)) // -
					&& (chipHolder.contains(currentChip-2) && rowCheck(currentChip-2,row))
					&& (boundsCheck(currentChip-3) && rowCheck(stackedRow(currentChip-3),row) && ((JLabel) panel.getComponent(currentChip-3)).getIcon().equals(empty))
					&& (rowCheck(currentChip-3,row)) ){
						return currentChip-3;
			}
			if( (chipHolder.contains(currentChip-7)) // |
					&& (chipHolder.contains(currentChip-14))
					&& (boundsCheck(currentChip-21) && ((JLabel) panel.getComponent(currentChip-21)).getIcon().equals(empty)) ){
						return currentChip-21;
			}
			if( (chipHolder.contains(currentChip-6) && rowCheck(currentChip-6,row-1)) // /
					&& (chipHolder.contains(currentChip-12) && rowCheck(currentChip-12,row-2))
					&& (boundsCheck(currentChip-18) && rowCheck(stackedRow(currentChip-18),row-3) && ((JLabel) panel.getComponent(currentChip-18)).getIcon().equals(empty)) ){
						return currentChip-18;
			}
			if( (chipHolder.contains(currentChip-18) && rowCheck(currentChip-18,row-3)) // /
					&& (chipHolder.contains(currentChip-12) && rowCheck(currentChip-12,row-2))
					&& (boundsCheck(currentChip-6) && rowCheck(stackedRow(currentChip-6),row-1) && ((JLabel) panel.getComponent(currentChip-6)).getIcon().equals(empty)) ){
						return currentChip-6;
			}
			if( (chipHolder.contains(currentChip-6) && rowCheck(currentChip-6,row-1)) // /
					&& (chipHolder.contains(currentChip-18) && rowCheck(currentChip-18,row-3))
					&& (boundsCheck(currentChip-12) && rowCheck(stackedRow(currentChip-12),row-2) && ((JLabel) panel.getComponent(currentChip-12)).getIcon().equals(empty)) ){
						return currentChip-12;
			}
			if( (chipHolder.contains(currentChip-8) && rowCheck(currentChip-8,row-1))// \
					&& (chipHolder.contains(currentChip-16) && rowCheck(currentChip-16,row-2))
					&& (boundsCheck(currentChip-24) && rowCheck(stackedRow(currentChip-24),row-3) && ((JLabel) panel.getComponent(currentChip-24)).getIcon().equals(empty)) ){
						return currentChip-24;
			}
			if( (chipHolder.contains(currentChip-24) && rowCheck(currentChip-24,row-3))// \
					&& (chipHolder.contains(currentChip-16) && rowCheck(currentChip-16,row-2))
					&& (boundsCheck(currentChip-8) && rowCheck(stackedRow(currentChip-8),row-1) && ((JLabel) panel.getComponent(currentChip-8)).getIcon().equals(empty)) ){
						return currentChip-8;
			}
			if( (chipHolder.contains(currentChip-8) && rowCheck(currentChip-8,row-1))// \
					&& (chipHolder.contains(currentChip-24) && rowCheck(currentChip-24,row-3))
					&& (boundsCheck(currentChip-16) && rowCheck(stackedRow(currentChip-16),row-2) && ((JLabel) panel.getComponent(currentChip-16)).getIcon().equals(empty)) ){
						return currentChip-16;
			}
		}
		return -1;
	}
	
}
