package com.company;

/**
 * Created by Ka Wing Fong on 3/13/15.
 */

/**
 * The object that stores all the information of past moves
 */
public class undoInfo {
    private CardStack fromStack, toStack;
    private int numCard=0;

    public undoInfo(CardStack fromStack, CardStack toStack, int num){
        this.fromStack = fromStack;
        this.toStack = toStack;
        this.numCard = num;
    }
    public undoInfo(CardStack fromStack, CardStack toStack) {
        this.fromStack = fromStack;
        this.toStack = toStack;
    }

    /**
     *
     * @return which stack the card is from
     */
    public CardStack getFromStack() {
        return fromStack;
    }

    /**
     *
     * @return which stack the card is to
     */
    public CardStack getToStack() {
        return toStack;
    }

    /**
     *
     * @return how many cards has been moved in 1 action. Applicable to only moveN
     */
    public int getNumCard() {
        return numCard;
    }
}
