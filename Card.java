package Games;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Card{

	public String symbol;
	public String suit; // Hearts Diamonds...
	public JLabel icon;
	public String color;
	
	public static final String[] SYMBOLS = { "Ace", "Two", "Three", "Four", "Five", "Six", 
			"Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King" };
	public static final String[] SUITS = { "Spades", "Clover", "Hearts", "Diamond" };

	public Card(String symbol, String suit) {
		this.symbol = symbol;
		this.suit = suit;
		this.icon = new JLabel(new ImageIcon(".\\Cards\\"+symbol+suit+".PNG"));
		this.color = (suit.equals("Spades") || suit.equals("Clover")) ? "Black":"Red";
		
	}

	public String toString() {
		return symbol + " of " + suit;
	}
}
