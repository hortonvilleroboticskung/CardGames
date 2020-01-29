package Games;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import org.w3c.dom.ls.LSOutput;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Card{

	public final String symbol;
	public final String suit; // Hearts Diamonds...
	public final JLabel icon;
	public final String color;
	public int symbolNum;

	public static final String[] SYMBOLS = { "Ace", "Two", "Three", "Four", "Five", "Six", 
			"Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King" };
	public static final String[] SUITS = { "Spades", "Clover", "Hearts", "Diamond" };

	public static HashMap<String,Integer> SYMBOL_TO_NUMBER = toNumber();

	public Card(String Symbol, String Suit) {
		symbol = Symbol;
		suit = Suit;
		symbolNum = SYMBOL_TO_NUMBER.get(symbol);
		icon = new JLabel(new ImageIcon(".\\Cards\\"+Symbol+Suit+".PNG"));
		color = (Suit.equals("Spades") || Suit.equals("Clover")) ? "Black":"Red";
	}

	private static HashMap<String,Integer> toNumber(){
		HashMap<String,Integer> ret = new HashMap<>();
		for(int i = 0 ; i < SYMBOLS.length; i++){
			ret.put(SYMBOLS[i],i+1);
		}
		return ret;
	}

	@Override
	public String toString() {
		return symbol+" of "+suit;
	}
}