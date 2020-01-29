package Games;

import Games.Card;
import Games.Deck;

import java.util.ArrayList;
import java.util.HashMap;

public class Hand {

	private Deck deck;
	private ArrayList<Card> hand;
	
	public Hand(int startingCount, Deck d) {
		deck = d;
		hand = startHand(startingCount,true);
	}
	public Hand(int startingCount, Deck d, boolean random) {
		deck = d;
		hand = startHand(startingCount,random);
	}
	
	private ArrayList<Card> startHand(int count, boolean random){
		ArrayList<Card> returnHand = new ArrayList<>();
		for(int i = 0; i < count; i++) {
			if(random) {
				returnHand.add(deck.randomDraw());
			} else {
				returnHand.add(deck.draw());
			}
		}
		return returnHand;
	}
	
	public Card draw() {
		Card c = deck.draw();
		hand.add(c);
		return c;
	}

	public int size(){
		return hand.size();
	}

	public boolean add(Card c){return hand.add(c);}

	public Card get(int index){
		return hand.get(index);
	}

	public Card remove(int index){ return hand.remove(index); }

	public boolean remove(Card c){
		return hand.remove(c);
	}

	public boolean contains(Card c) {return hand.contains(c);}

	public ArrayList<Card> draw(int count) {
		ArrayList<Card> arr = deck.draw(count);
		hand.addAll(arr);
		return arr;
	}

	public void backToDeck(Card c){
		hand.remove(c);
		deck.add(c);
		deck.shuffleDeck();
	}

	public ArrayList<Card> pairSymbol() {
		ArrayList<Card> paired = new ArrayList<>();
		HashMap<String, Card> pairs = new HashMap<>();
		for (int currentCard = 0; currentCard < hand.size(); currentCard++) {
			String sym = hand.get(currentCard).symbol;
			if (!pairs.containsKey(sym)) {
				pairs.put(sym, hand.get(currentCard));
			} else {
				Card storedCard = pairs.get(sym);
				paired.add(hand.get(currentCard));
				paired.add(storedCard);
				pairs.remove(storedCard.symbol);
			}
		}
		hand.removeAll(paired);
		//Returns the cards that were paired but also removes the paired cards
		return paired;
	}

	public void changeDeck(Deck d) {
		deck = d;
	}
	
	public String toString() {
		return hand.toString();
	}

	public ArrayList<Card> getContents(){
		return (ArrayList<Card>) hand.clone();
	}
}
