package Games;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class War {

    private int wins,loses,draws = 0;
    private JFrame frame = new JFrame("THIS IS WAR!!!!");
    private JPanel panel = new JPanel(new BorderLayout());
    private JLabel winTracker = new JLabel("Wins: "+wins);
    private Deck d = new Deck();
    private Player p1 = new Player(new Hand(d.getContents().size()/2,d));
    private Player com = new Player(new Hand(d.getContents().size()/2,d));

    public War(){
        Card firstP1 = p1.hand.remove(0);
        Card firstCom = com.hand.remove(0);
        firstP1.icon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Card player1Card = p1.hand.remove(0);
                Card comCard = com.hand.remove(0);
                roundWinCheck(player1Card,comCard);
                if(p1.hand.size() == 0 || com.hand.size()==0){
                    if(wins>loses) {
                        JOptionPane.showMessageDialog(frame, "You won with,"+wins+" wins!", "You Won!", JOptionPane.PLAIN_MESSAGE);
                    } else if (wins<loses) {
                        JOptionPane.showMessageDialog(frame, "You lost with, "+loses+" loses!", "You Lost!", JOptionPane.PLAIN_MESSAGE);
                    } else{
                        JOptionPane.showMessageDialog(frame, "I don't know how this happened but...", "You both lost!", JOptionPane.PLAIN_MESSAGE);
                    }
                    GUI.playAgain(frame);
                }
                p1.changeJLabelIcon(0,player1Card);
                com.changeJLabelIcon(0,comCard);
                winTracker.setText("Wins: "+wins+" Loses: "+loses+" Draws: "+draws);
                frame.validate();
            }@Override public void mousePressed(MouseEvent e) { }@Override public void mouseReleased(MouseEvent e) { }@Override public void mouseEntered(MouseEvent e) { }@Override public void mouseExited(MouseEvent e) { }});
        roundWinCheck(firstP1,firstCom);
        winTracker.setText("Wins:"+wins+" Loses:"+loses+" Draws:"+draws);
        p1.display.add(firstP1.icon);
        com.display.add(firstCom.icon);
        panel.add(winTracker,BorderLayout.PAGE_START);
        panel.add(com.display,BorderLayout.CENTER);
        panel.add(p1.display, BorderLayout.PAGE_END);
        frame.add(panel);
        GUI.defaultFrameWork(frame);
    }

    private boolean roundWinCheck(Card p, Card c){
        if(p.symbolNum>c.symbolNum){
            wins++;
            return true;
        } else if(p.symbolNum<c.symbolNum) {
            loses++;
        } else {
            draws++;
        }
        return false;
    }

//    public static void main(String[] args){
//        new War();
//    }
}