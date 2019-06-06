public class Card {

    private char color;     /** D, C, H, S **/
    private String card;    /** 2-10, Jack, Queen, King, Ace **/
    private int weight;     /** 1-13 (needed for sorting and comparison) **/

    public Card(int colorNr, int cardNr) throws IllegalArgumentException {

        if(colorNr < 0 || colorNr > 3 || cardNr < 0 || cardNr > 12) {
            throw new IllegalArgumentException("Color number values are from 0 to 3, card number values are from 0 to 12");
        }

        setWeight(cardNr + 1);
        setColor(colorNr);
        setCard(cardNr);
    }

   private void setColor(int colorNr) {

        switch (colorNr) {
            case 0:
                color = 'D';
                break;

            case 1:
                color = 'C';
                break;

            case 2:
                color = 'H';
                break;

            case 3:
                color = 'S';
                break;
        }
    }

    private void setCard(int cardNr) {

        if (cardNr == 0) {
            card = "Ace";
        }
        else if (cardNr < 10) {
            card = String.valueOf(cardNr + 1);
        }
        else if (cardNr == 10) {
            card = "Jack";
        }
        else if (cardNr == 11) {
            card = "Queen";
        }
        else if (cardNr == 12) {
            card = "King";
        }
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public char getColor() {
        return color;
    }

    public String getCard() {
        return card;
    }

    public int getWeight() {
        return weight;
    }

    public void printCard() {
        System.out.println(getColor() + "." + getCard());
    }
}

/**************

Card colors:
    D - diamonds
    C - clubs
    H - hearts
    S - Spades


 ************/
