import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;

public class CombinationChecker {

    List<Card> cardSet;             /** Collection of 5 cards which are evaluated **/

    private int prize = 0;
    private String combination;     /** Title of combination **/

    public void setPrize(int prize) {
        this.prize = prize;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    public int getPrize() {
        return prize;
    }

    public String getCombination() {
        return combination;
    }

    public int evaluate(List<Card> tableCards) {

        cardSet = new ArrayList<>(tableCards);

        orderCards();

        if (royalFlush()) {
            setPrize(800);
            setCombination("Royal Flush");
        } else if (straightFlush()) {
            setPrize(50);
            setCombination("Straight Flush");
        } else if (fourOfAKind()) {
            setPrize(25);
            setCombination("Four of a kind");
        } else if (fullHouse()) {
            setPrize(9);
            setCombination("Full House");
        } else if (flush()) {
            setPrize(6);
            setCombination("Flush");
        } else if (straight()) {
            setPrize(4);
            setCombination("Straight");
        } else if (threeOfAKind()) {
            setPrize(3);
            setCombination("Three of a kind");
        } else if (twoPairs()) {
            setPrize(2);
            setCombination("Two Pairs");
        } else if (jacksOrBetter()) {
            setPrize(1);
            setCombination("Jacks or Better");
        } else {
            setPrize(0);
            setCombination("No win");
        }

        printWin();
        return prize;
    }

    private boolean royalFlush() {
        if (straightFlush()) {
            reorderCards();
            if (cardSet.get(0).getWeight() == 10
                    && cardSet.get(1).getWeight() == 11
                    && cardSet.get(2).getWeight() == 12
                    && cardSet.get(3).getWeight() == 13
                    && cardSet.get(4).getWeight() == 14) {
                return true;
            }
        }
        return false;
    }

    private boolean straightFlush() {
        if (straight() && flush()) {
            return true;
        }
        return false;
    }

    private boolean fourOfAKind() {
        if (someEqual(4, cardSet.get(0).getWeight(),
                cardSet.get(1).getWeight(),
                cardSet.get(2).getWeight(),
                cardSet.get(3).getWeight(),
                cardSet.get(4).getWeight())) {
            return true;
        }
        return false;
    }

    private boolean fullHouse() {
        if (someEqual(32, cardSet.get(0).getWeight(),
                cardSet.get(1).getWeight(),
                cardSet.get(2).getWeight(),
                cardSet.get(3).getWeight(),
                cardSet.get(4).getWeight())) {
            return true;
        }
        return false;
    }

    private boolean flush() {
        if(allEqualColor('D') || allEqualColor('C') || allEqualColor('H') || allEqualColor('S')) {
            return true;
        }
        return false;
    }

    private boolean straight() {
        if (cardSet.get(0).getWeight() == 1
                && cardSet.get(1).getWeight() == 2
                && cardSet.get(2).getWeight() == 3
                && cardSet.get(3).getWeight() == 4
                && cardSet.get(4).getWeight() == 5) {
            return true;
        }
        reorderCards();
        for(int i = 2; i < 11; i++) {
            if (cardSet.get(0).getWeight() == i
                    && cardSet.get(1).getWeight() == i + 1
                    && cardSet.get(2).getWeight() == i + 2
                    && cardSet.get(3).getWeight() == i + 3
                    && cardSet.get(4).getWeight() == i + 4) {
                return true;
            }
        }
        return false;
    }

    private boolean threeOfAKind() {
        if(someEqual(3, cardSet.get(0).getWeight(),
                cardSet.get(1).getWeight(),
                cardSet.get(2).getWeight(),
                cardSet.get(3).getWeight(),
                cardSet.get(4).getWeight())) {
            return true;
        }
        return false;
    }

    private boolean twoPairs() {
        if(someEqual(22, cardSet.get(0).getWeight(),
                cardSet.get(1).getWeight(),
                cardSet.get(2).getWeight(),
                cardSet.get(3).getWeight(),
                cardSet.get(4).getWeight())) {
            return true;
        }
        return false;    }

    private boolean jacksOrBetter() {
        if(someEqual(2, cardSet.get(0).getWeight(),
                cardSet.get(1).getWeight(),
                cardSet.get(2).getWeight(),
                cardSet.get(3).getWeight(),
                cardSet.get(4).getWeight())) {
            return true;
        }
        return false;
    }

    public void printWin() {
        System.out.println(getCombination());
        System.out.println("Prize: " + getPrize());
    }

    public void orderCards() {
        for(var card : cardSet) {
            if(card.getWeight() == 14) {
                card.setWeight(1);
            }
        }
        cardSet.sort(Comparator.comparing(Card::getWeight));
    }

    public void reorderCards() {
        for(var card : cardSet) {
            if(card.getWeight() == 1) {
                card.setWeight(14);
            }
        }
        cardSet.sort(Comparator.comparing(Card::getWeight));
    }

    boolean allEqual(int x1, int x2, int x3, int x4) {
        if(x1 == x2 && x1 == x3 && x1 == x4) {
            return true;
        }
        return false;
    }

    boolean allEqual(int x1, int x2, int x3) {
        if(x1 == x2 && x1 == x3) {
            return true;
        }
        return false;
    }

    boolean allEqual(int x1, int x2) {
        if(x1 == x2) {
            return true;
        }
        return false;
    }

    boolean allEqualHigh(int x1, int x2) {
        if(x1 == x2 && (x1 >= 10 || x1 == 1)) {
            return true;
        }
        return false;
    }

    boolean allEqualColor(char s) {
        boolean isColor = true;
        for(var card : cardSet) {
            if(card.getColor() != s) {
                isColor = false;
                break;
            }
        }
        return isColor;
    }

    boolean someEqual(int n, int x1, int x2, int x3, int x4, int x5) {
        if (n == 4) {
            if (allEqual(x1, x2, x3, x4) || allEqual(x2, x3, x4, x5)) {
                return true;
            }
        } else if (n == 3) {
            if (allEqual(x1, x2, x3) || allEqual(x2, x3, x4) || allEqual(x3, x4, x5)) {
                return true;
            }
        } else if (n == 2) {
            if (allEqualHigh(x1, x2) || allEqualHigh(x2, x3) || allEqualHigh(x3, x4) || allEqualHigh(x4, x5)) {
                return true;
            }
        } else if (n == 22) {
            if ((allEqual(x1, x2) && allEqual(x3, x4))
                    || (allEqual(x1, x2) && allEqual(x4, x5))
                    || (allEqual(x2, x3) && allEqual(x4, x5))) {
                return true;
            }
        }
        else if (n == 32) {
            if ((allEqual(x1, x2) && allEqual(x3, x4, x5))
                    || (allEqual(x1, x2, x3) && allEqual(x4, x5))) {
                return true;
            }
        }
        return false;
    }
}
