package Games;

import Games.Card;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

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
				orderedDeck.add(new Card(Card.SYMBOLS[currentSymbol],Card.SUITS[currentSuit]));
			}
		}
		return shuffle ? shuffleDeck(orderedDeck) : orderedDeck;
	}

	public boolean shuffleDeck() {
		ArrayList<Card> check = new ArrayList<>();
		check.addAll(contents);
		ArrayList<Card> holder = new ArrayList<>();
		while(contents.size() != 0) {
			int index = new Random().nextInt(contents.size());
			holder.add(contents.get(index));
			contents.remove(index);
		}
		contents = holder;
		return contents!=check;
	}

	private ArrayList<Card> shuffleDeck(ArrayList<Card> deck) {
		ArrayList<Card> holder = new ArrayList<Card>();
		while(deck.size() != 0) {
			int index = (int)(Math.random()*deck.size());
			holder.add(deck.get(index));
			deck.remove(index);
		}
		return holder;
	}

	public int indexOf(String name) {
		for(int i = 0; i < contents.size(); i++) {
			if(contents.get(i).toString().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public Card draw() {
		if(contents.size()>0) {
			Card drawCard = contents.get(0);
			contents.remove(0);
			return drawCard;
		}else {
			return null;
		}
	}

	public Card randomDraw() {
		if(contents.size()>0) {
			int ran = (int) (Math.random() * contents.size());
			Card randomDrew = contents.get(ran);
			contents.remove(ran);
			return randomDrew;
		} else {
			return null;
		}
	}

	public Card pickCard(String name) {
		if(contents.size()>0) {
			Card picked = contents.get(indexOf(name));
			contents.remove(indexOf(name));
			return picked;
		} else {
			return null;
		}
	}

	public ArrayList<Card> draw(int count){
		ArrayList<Card> drew = new ArrayList<>();
		for(int i = 0; i < count; i++) {
			if(contents.size()>0) {
				drew.add(contents.get(0));
				contents.remove(0);
			}
		}
		return drew;
	}

	public String toString(){
		return contents.toString();
	}

	public ArrayList<Card> getContents(){
		return (ArrayList<Card>)contents.clone();
	}

	public Card get(int index) {
		return contents.get(index);
	}

	public Card get(String name) {
		return contents.get(indexOf(name));
	}

	public Card remove(int index) {
		return contents.remove(index);
	}

	public int size() {
		return contents.size();
	}

	public void add(Card c) {
		contents.add(c);
	}

	public void add(int index,Card c) {
		contents.add(index, c);
	}

	public boolean addAll(ArrayList<Card> c) {
		ArrayList<Card> check = new ArrayList<>();
		check.addAll(contents);
		contents.addAll(c);
		return contents!=check;
	}
}
