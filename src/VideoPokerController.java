import java.util.*;

public class VideoPokerController extends Thread {

    private List<Card> deck = new ArrayList<>();                        /** Collection of 52 distinct cards **/
    private List<Card> tableCards = new ArrayList<>();                  /** Collection of 5 cards on desk **/
    private CombinationChecker evaluator = new CombinationChecker();    /** Evaluates value of combination **/

    public VideoPokerController() {
        initDeck();
        shuffleDeck();
        initTableCards();
    }

    public void initDeck() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 13; j++) {
                deck.add(new Card(i, j));
            }
        }
    }

    public void initTableCards() {
        for(int i = 0; i < 5; i++) {
            tableCards.add(deck.get(i));
        }
    }

    public void printDeck() {
        for(var card : deck) {
            card.printCard();
        }
    }

    public void printTableCards() {
        int i = 1;
        System.out.println("Table cards:");
        for(var card : tableCards) {
            System.out.print(i + ": ");
            card.printCard();
            i++;
        }
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public void discardChosenCards() {
        HashSet<Integer> cardsToDiscard = new HashSet<>();
        Scanner in = new Scanner(System.in);
        int input;
        System.out.println("Choose card position from 1 to 5 or 0 or any text to continue");
        for(int i = 0; i < 5; i++) {
            try {
                input = in.nextInt();
                if(input == 0) {
                    break;
                }
                else if(input < 0 || input > 5) {
                    System.out.println("Error: choose card position from 1 to 5 or 0 or any text to continue");
                }
                else {
                    cardsToDiscard.add(input - 1);
                }
            }
            catch(NumberFormatException e) {
                System.out.println("Error: wrong input");
            }
            catch(InputMismatchException e) {
                break;
            }
        }
        in.close();

        int index = 0;
        for(int i = 0; i < 5; i++) {
            if(cardsToDiscard.contains(i)) {
                tableCards.remove(index);
                index--;
            }
            index++;
        }
    }

    public void refillTableCards() {
        int tCardsNumber = tableCards.size();
        for(int i = 5; i < 10 - tCardsNumber; i++) {
            tableCards.add(deck.get(i));
        }
    }

    public void run() {
        //printDeck();
        printTableCards();
        discardChosenCards();
        refillTableCards();
        printTableCards();
        evaluator.evaluate(tableCards);
    }

}
