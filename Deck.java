package Games;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Deck{
	
	public static final int DECKSIZE = Card.SYMBOLS.length * Card.SUITS.length;
	private ArrayList<Card> contents;
	
	public Deck() {
		contents = deckMaker(true);
	}
	public Deck(boolean shuffle) {
		contents = deckMaker(shuffle);
	}
	
	private ArrayList<Card> deckMaker(boolean shuffle){
		ArrayList<Card> orderedDeck = new ArrayList<Card>();
		for (int currentSymbol = 0; currentSymbol < Card.SYMBOLS.length; currentSymbol++) {
			for (int currentSuit = 0; currentSuit < Card.SUITS.length; currentSuit++) {
				Card newCard = new Card(Card.SYMBOLS[currentSymbol],Card.SUITS[currentSuit]);
				orderedDeck.add(newCard);
			}
		}
		return shuffle ? shuffleDeck(orderedDeck) : orderedDeck;
	}
	
	public ArrayList<Card> shuffleDeck() {
		ArrayList<Card> holder = new ArrayList<Card>();
		while(contents.size() != 0) {
			int index = new Random().nextInt(contents.size());
			holder.add(contents.get(index));
			contents.remove(index);
		}
		contents = holder;
		return holder;
	}
	
	private ArrayList<Card> shuffleDeck(ArrayList<Card> deck) {
		ArrayList<Card> holder = new ArrayList<Card>();
		while(deck.size() != 0) {
			int index = new Random().nextInt(deck.size());
			holder.add(deck.get(index));
			deck.remove(index);
		}
		return holder;
	}
	
	public Card draw() {
		Card drawCard = contents.get(0);
		contents.remove(0);
		return drawCard;
	}
	
	public ArrayList<Card> draw(int count){
		ArrayList<Card> drew = new ArrayList<>();
		for(int i = 0; i < count; i++) {
			drew.add(contents.get(0));
			contents.remove(0);
		}
		return drew;
	}
	
	public String toString(){
		return contents.toString();
	}
	
	public Card get(int index) {
		return contents.get(index);
	}
	
	public Card remove(int index) {
		return contents.remove(index);
	}
	
	public int size() {
		return contents.size();
	}
	
}
