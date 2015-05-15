package com.company;

/**
 * Ka Wing Fong
 * 109794011
 * HW 3
 * CSE 214-R03
 * Recitation TA: Sun Lin
 * Grading TA: Ke Ma
 * @author Ka Wing Fong
 */

/**
 * A card object that contains information of a card including the suit and value of the card.
 */
public class Card {

    private int suitOfCard, valueOfCard,position=0;
    private boolean isFaceUp;

    /**
     *
     * @param suit value from 1 to 4, represents the suit of the card
     * @param value value from 1 to 13 represents the value of the card
     * @param isFace whether the card is facing up
     */
    public Card(int suit, int value, boolean isFace){
        suitOfCard = suit;
        valueOfCard = value;
        isFaceUp = isFace;
    }

    /**
     *
     * @return the suit of the card
     */
    public int getSuitOfCard() {
        return suitOfCard;
    }

    /**
     * set the suit of the card to the parameter
     * @param suitOfCard the suit of the card to be set
     */
    public void setSuitOfCard(int suitOfCard) {
        this.suitOfCard = suitOfCard;
    }

    /**
     *
     * @return the value of the card ranging from 1 to 13
     */
    public int getValueOfCard() {
        return valueOfCard;
    }

    /**
     * change the value of a card
     * @param valueOfCard the value of the card that needs to be changed
     */
    public void setValueOfCard(int valueOfCard) {
        this.valueOfCard = valueOfCard;
    }

    /**
     *
     * @return whether the card is facing up or not
     */
    public boolean isFaceUp() {
        return isFaceUp;
    }

    /**
     *
     * @param isFaceUp true means the card is facing up, false otherwise
     */
    public void setFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
    }

    /**
     *
     * @return true if the card is red and false otherwise
     */
    public boolean isRed(){
        boolean result = (suitOfCard%2==0)?true:false;
        return result;
    }

    /**
     *
     * @return a string of the card
     */
    public String toString(){
        String values[] = {" ","A","2","3","4","5","6","7","8","9","10","J","Q","K"};
        char suits[]    = {' ', '\u2666', '\u2663','\u2665', '\u2660'};   // {' ', '♦', '♣','♥', '♠'}
        String s = "[" +values[valueOfCard]+suits[suitOfCard]+"]";
        return s;
    }
}
