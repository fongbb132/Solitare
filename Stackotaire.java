package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
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
 *
 * first, it initializes the game with 52 different card and put them into different stack. Then, it prompts the user for further action
 * draw - to draw the card from stock to waste
 * restart - to restart the game
 * quit - to terminate the game
 * move - to move a card from one stock to another (takes 2 arguments, the stack moving from and the stack moving to )
 * moveN - to move more than 1 card from one to another (takes 3 arguments, the stack moving from, the stack moving to and how many cards are moving)
 * undo - to undo previous step
 */

public class Stackotaire {

    public static Stack<Card> initialStack = new Stack<Card>();
    public static ArrayList<Card> allCards = new ArrayList<Card>();
    public static CardStack stock = new CardStack('s');
    public static CardStack waste = new CardStack('w');
    public static CardStack f1 = new CardStack('f');
    public static CardStack f2 = new CardStack('f');
    public static CardStack f3 = new CardStack('f');
    public static CardStack f4 = new CardStack('f');
    public static CardStack t1 = new CardStack('t');
    public static CardStack t2 = new CardStack('t');
    public static CardStack t3 = new CardStack('t');
    public static CardStack t4 = new CardStack('t');
    public static CardStack t5 = new CardStack('t');
    public static CardStack t6 = new CardStack('t');
    public static CardStack t7 = new CardStack('t');

    public static Stack<undoInfo> undo = new Stack<undoInfo>();
    public static CardStack from, to;

    public static Scanner in= new Scanner(System.in);

    public static ArrayList<CardStack> tableau = new ArrayList<CardStack>();
    public static ArrayList<CardStack> foundation = new ArrayList<CardStack>();

    public static boolean playing = true;


    public static void main(String args[]){
        initializeGame();
        while(playing) {
            String input = in.next();
            input = input.toLowerCase();
            if (input.equals("print")) {
                print();
                playing = !winOrNot();
            } else if (input.equals("draw")) {
                draw();
                print();
                playing = !winOrNot();
            } else if(input.equals("quit")) {
                quit();
            } else if(input.equals("restart")){
                reStart();
            } else if(input.equals("move")){
                move();
                playing = !winOrNot();
                print();
            } else if(input.equals("moven")){
                moveN();
                playing = !winOrNot();
                print();
            } else if(input.equals("undo")){
                undo();
                print();
            }
            else{
                System.out.println("INCORRECT INPUT!!");
            }
        }
    }

    /**
     * the move more than 1 card from one tableau stock to another tableau stock
     */
    public static void moveN(){
        String moveFrom, moveTo;
        char fromStack,toStack;
        int fromPos,toPos,numCards;
        moveFrom = in.next();
        moveTo = in.next();
        numCards = in.nextInt();
        moveFrom = moveFrom.toLowerCase();
        moveTo = moveTo.toLowerCase();
        fromStack = moveFrom.substring(0).toCharArray()[0];
        fromPos = Integer.parseInt(moveFrom.substring(1,2));
        toStack = moveTo.substring(0).toCharArray()[0];
        toPos = Integer.parseInt(moveTo.substring(1,2));


        if(fromStack=='t'||toStack=='t'){
            Stack<Card> tempFrom = new Stack<Card>();
            for(int i = 0; i <numCards; i++){
                if(!tableau.get(fromPos-1).isEmpty()) {
                    tempFrom.push(tableau.get(fromPos - 1).pop());
                }
            }

            if(!tempFrom.isEmpty()) {
                if(!tableau.get(toPos-1).isEmpty()) {
                    if (tempFrom.peek().getValueOfCard() + 1 == tableau.get(toPos - 1).peek().getValueOfCard()) {
                        while (!tempFrom.isEmpty()) {
                            tableau.get(toPos - 1).push(tempFrom.pop());
                            undo.push(new undoInfo(tableau.get(fromPos-1),tableau.get(toPos-1),numCards));
                        }
                        if(!tableau.get(fromPos-1).isEmpty()) {
                            tableau.get(fromPos - 1).peek().setFaceUp(true);
                        }
                    } else if(tableau.get(toPos-1).isEmpty()&&tempFrom.peek().getValueOfCard()==13){
                        while (!tempFrom.isEmpty()) {
                            tableau.get(toPos - 1).push(tempFrom.pop());
                            undo.push(new undoInfo(tableau.get(fromPos-1),tableau.get(toPos-1),numCards));
                        }
                        if(!tableau.get(fromPos-1).isEmpty()) {
                            tableau.get(fromPos - 1).peek().setFaceUp(true);
                        }
                    } else {
                        while (!tempFrom.isEmpty()) {
                            tableau.get(fromPos - 1).push(tempFrom.pop());
                        }
                        System.out.println("The move is not allowed");
                    }
                }else if(tableau.get(toPos-1).isEmpty()&&tempFrom.peek().getValueOfCard()==13){
                    while (!tempFrom.isEmpty()) {
                        tableau.get(toPos - 1).push(tempFrom.pop());
                        undo.push(new undoInfo(tableau.get(fromPos-1),tableau.get(toPos-1),numCards));
                    }
                    tableau.get(fromPos - 1).peek().setFaceUp(true);
                }else{
                    System.out.println("Can't move sorry");
                }
            }
        }else{
            System.out.println("The move is not allowed");
        }



    }

    /**
     * to move one card from one tableau to another
     */
    public static void move(){
        String moveFrom, moveTo;
        char fromStack,toStack;
        int fromPos,toPos;
        moveFrom = in.next();
        moveTo = in.next();
        moveFrom = moveFrom.toLowerCase();
        moveTo = moveTo.toLowerCase();
        fromStack = moveFrom.substring(0).toCharArray()[0];
        fromPos = Integer.parseInt(moveFrom.substring(1,2));
        toStack = moveTo.substring(0).toCharArray()[0];
        toPos = Integer.parseInt(moveTo.substring(1,2));
        char toInString = 'n';

        switch (fromStack){
            case 'w':
                from = waste;
                break;
            case 'f':
                from = foundation.get(fromPos-1);
                break;
            case 't':
                from = tableau.get(fromPos-1);
                break;
            default:
                System.out.println("Moving from the wrong stack");
        }

        switch (toStack){
            case 'f':
                to = foundation.get(toPos-1);
                toInString = 'f';
                break;
            case 't':
                to = tableau.get(toPos-1);
                toInString = 't';
                break;
            default:
                System.out.println("Moving to the wrong stack");
        }

        //for tableau
        //move the card to tableau following with the king rule and number rule
        if(toInString=='t') {
            if (!to.isEmpty()) {
                if (!from.isEmpty() && from.peek().isRed() != to.peek().isRed() && to.peek().getValueOfCard() - 1 == from.peek().getValueOfCard()) {
                    Card a = from.pop();
                    if(fromStack=='t'){
                        if(!from.isEmpty()) {
                            from.peek().setFaceUp(true);
                        }
                    }
                    to.push(a);
                    undo.push(new undoInfo(tableau.get(fromPos-1),tableau.get(toPos-1)));
                } else {
                    System.out.println("Action cannot be taken");
                }
            } else {
                Card b = from.peek();
                if (b.getValueOfCard() == 13) {
                    Card a = from.pop();
                    if(fromStack=='t'){
                        if(!from.isEmpty()) {
                            from.peek().setFaceUp(true);
                        }
                    }
                    to.push(a);
                    undo.push(new undoInfo(tableau.get(fromPos-1),tableau.get(toPos-1)));
                }else {
                    System.out.println("The move is not allowed");
                }
            }
        }
        //for moving to foundation
        else if (toInString == 'f' && !from.isEmpty()){
            if(to.isEmpty()&&from.peek().getValueOfCard()==1){
                to.push(from.pop());
                undo.push(new undoInfo(tableau.get(fromPos-1),foundation.get(toPos-1)));
                if(fromStack=='t'){
                    if(!from.isEmpty()) {
                        from.peek().setFaceUp(true);
                    }
                }
            }else if(!to.isEmpty()){
                Card a = to.peek();
                Card b = from.peek();

                if(a.getValueOfCard()+1==b.getValueOfCard()&&a.getSuitOfCard()==b.getSuitOfCard()){
                    to.push(from.pop());
                    undo.push(new undoInfo(tableau.get(fromPos-1),foundation.get(toPos-1)));
                    if(fromStack=='t'){
                        if(!from.isEmpty()) {
                            from.peek().setFaceUp(true);
                        }
                    }
                }else{
                    System.out.println("Can't move the card to foundation");
                }
            }
        }
    }

    /**
     * initialize the game with 52 cards and distribute the cards to different stack
     */
    public static void initializeGame(){
        for(int i = 1; i<=13; i++){
            for(int j = 1; j<= 4; j++){
                initialStack.push(new Card(j,i,false));
            }
        }
        Collections.shuffle(initialStack);
        for(int i = 1; i <= 24; i++){
            Card a = initialStack.pop();
            allCards.add(a);
            stock.push(a);
        }

        tableau.add(t1);
        tableau.add(t2);
        tableau.add(t3);
        tableau.add(t4);
        tableau.add(t5);
        tableau.add(t6);
        tableau.add(t7);

        for(int i = 0 ; i <7 ; i++){
            for(int j = 1; j<=i+1 ;j++){
                Card b = initialStack.pop();
                allCards.add(b);
                tableau.get(i).push(b);
            }
        }

        for(int i = 0 ; i <7 ; i++){
            tableau.get(i).peek().setFaceUp(true);
        }
        foundation.add(f1);
        foundation.add(f2);
        foundation.add(f3);
        foundation.add(f4);

        print();
    }

    /**
     * draw the card from stock piles to waste pile
     */
    public static void draw(){
        if(!stock.isEmpty()) {
            Card newCard = stock.pop();
            undo.push(new undoInfo(stock,waste));
            newCard.setFaceUp(true);
            waste.push(newCard);
        }else{
            while(!waste.isEmpty()){
                Card newCard = waste.pop();
                newCard.setFaceUp(false);
                stock.push(newCard);
            }/*
            Card newCard = stock.pop();
            undo.push(new undoInfo(stock,waste));
            newCard.setFaceUp(true);
            waste.push(newCard);
            */
        }
    }

    /**
     * to print all cards in a formatted way
     */
    public static void print(){
        for(int i = 0; i<4 ; i++){
            CardStack a = foundation.get(i);
            if(a.isEmpty()){
                String s = "[F"+(i+1)+"]";
                System.out.print(s);
            }else {
                a.printStack();
            }
        }


        System.out.print("      ");

        waste.printStack();
        stock.printStack();
        System.out.print("  " + stock.size());
        System.out.println();

        for(int i = 6; i>=0 ; i--){
            String t = "T";
            t+= (i+1)+" ";
            System.out.print(t);
            tableau.get(i).printStack();
        }
    }

    /**
     * to terminate the game
     */
    public static void quit(){
        System.out.println("Do you want to quit? (Y/N): ");
        String input = in.next();
        input = input.toUpperCase();
        if(input.equals("Y")){
            playing = false;
            System.out.println();
            System.out.println("Sorry, you lose.");
        }else{
            print();
        }
    }

    /**
     * restart the game
     */
    public static void reStart(){
        System.out.print("Do you want to quit? (Y/N): ");
        String input = in.next();
        input = input.toUpperCase();
        if(input.equals("Y")){
            stock.clear();
            waste.clear();
            f1.clear();
            f2.clear();
            f3.clear();
            f4.clear();
            for(int i = 0; i<7; i++){
                tableau.get(i).clear();
            }
            System.out.println();
            System.out.println("Sorry, you lose.");
            initializeGame();
        }else{
            print();
        }

    }

    /**
     * keep tracking whether player wins the game or not
     * @return true if the player wins the game false otherwise
     */
    public static boolean winOrNot(){
        for(Card a : allCards){
            if(!a.isFaceUp())
                return false;
        }
        System.out.println("Congratulation! You won the game!");
        return true;
    }

    /**
     * undo the previous action
     */
    public static void undo(){
        if(!undo.isEmpty()){
            if(undo.peek().getNumCard() == 0){
                boolean isFromTableau=false;
                boolean isFromFoundation = false;
                for(int i = 0; i < 4; i++){
                    if(undo.peek().getFromStack() == foundation.get(i)){
                        isFromFoundation = true;
                    }
                }
                for(int i = 0; i < 7; i++){
                    if(undo.peek().getFromStack()==tableau.get(i)){
                        isFromTableau = true;
                    }
                }
                if(isFromTableau){
                    undoInfo a = undo.pop();
                    if(!a.getFromStack().isEmpty()) {
                        a.getFromStack().peek().setFaceUp(false);
                    }
                    a.getFromStack().push(a.getToStack().pop());
                }else if(undo.peek().getFromStack()==waste){
                    undoInfo a = undo.pop();
                    if(!a.getFromStack().isEmpty()) {
                        a.getFromStack().peek().setFaceUp(false);
                    }
                    a.getFromStack().push(a.getToStack().pop());
                }else if(undo.peek().getFromStack()==stock){
                    if(undo.peek().getToStack().isEmpty()&&!undo.peek().getFromStack().isEmpty()){
                        undoInfo a = undo.pop();
                        while(!a.getFromStack().isEmpty()){
                            a.getFromStack().peek().setFaceUp(true);
                            a.getToStack().push(a.getFromStack().pop());
                        }
                    }else{
                        undoInfo a = undo.pop();
                        if(!a.getFromStack().isEmpty()) {
                            a.getFromStack().peek().setFaceUp(false);
                        }
                        a.getFromStack().push(a.getToStack().pop());
                    }
                }else if(isFromFoundation){
                    undoInfo a = undo.pop();
                    a.getFromStack().push(a.getToStack().pop());
                }
            }else {
                Stack<Card> temp = new Stack<Card>();
                undoInfo a = undo.peek();
                if(!undo.peek().getFromStack().isEmpty()) {
                    undo.peek().getFromStack().peek().setFaceUp(false);
                }
                for(int i = 1; i <= a.getNumCard();i++){
                    temp.push(undo.pop().getToStack().pop());
                }
                while (!temp.isEmpty()){
                    a.getFromStack().push(temp.pop());
                }
            }
        }else {
            System.out.println("Sorry, you already undo the first action that you took");
        }
    }

}
