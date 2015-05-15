package com.company;

import java.util.Stack;

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
 * The CardStack extends the Stack class from Java API and have a char parameter
 */
public class CardStack extends Stack<Card>{

    Stack<Card> temp = new Stack<Card>();
    char types;

    /**
     * The constructor of CardStack
     * @param type indicates what type of stack that is
     */
    public CardStack(char type){
        super();
        types = type;

    }

    /**
     *
     * @param newCard the card that needs to be added to the stack
     * @return the card that is added.
     */
    public Card push(Card newCard){
       return super.push(newCard);
    }

    /**
     *
     * @return the card at the top of the stack
     */
    public Card pop(){
       return super.pop();
    }

    /**
     *
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty(){
       return super.isEmpty();
    }

    /**
     *
     * @return how many cards in a stack
     */
    public int size(){
       return super.size();
    }

    /**
     * print the stack in a specific format
     */
    public void printStack(){

        switch (types){
            case 's':
                if(!isEmpty()) {
                    System.out.print("[XX]");
                }else {
                    System.out.print("N");
                }
                break;

            case 'w':
                if(this.isEmpty()){
                    System.out.print("W1 [  ] ");
                }else {
                    Card toPrint = this.peek();
                    String toP = "W1  "+toPrint.toString()+" ";
                    System.out.print(toP);
                }
                break;

            case 't':
                if(!this.isEmpty()) {
                    String s = "";
                    temp.clear();
                    while (!this.empty()) {

                        Card current = this.pop();
                        temp.push(current);
                    }
                    while (!temp.empty()) {
                        Card current = temp.pop();
                        if (!current.isFaceUp()) {
                            s += "[XX]";
                        } else {
                            s += current.toString();
                        }
                        this.push(current);
                    }
                    System.out.println(s);
                }else{
                    System.out.println("[  ]");
                }
                break;

            case 'f':
                Card toBePrint = this.peek();
                System.out.print(toBePrint.toString());
                break;
        }
    }
}
