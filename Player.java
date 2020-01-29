package Games;

import javax.swing.*;
import java.awt.event.MouseListener;

public class Player {

    public JPanel display = new JPanel();
    public Hand hand;

    public Player(Hand h) {
        hand = h;
    }

    public void changeJLabelIcon(int index, Card c){
        ((JLabel)display.getComponent(index)).setIcon(c.icon.getIcon());
    }

}
