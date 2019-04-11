
public class Egg extends MarketProduct {
    private int numOfEggs;
    private int pricePerDozen;

    public Egg(String name, int numRequired, int price) {
        super(name);
        numOfEggs = numRequired;
        pricePerDozen = price;
    }

    public int getCost() {
        return pricePerDozen * numOfEggs / 12;
    }

    public boolean equals(Object o) {
        if (o.getClass().equals(getClass()) &&
                getName().equals(((Egg) o).getName()) &&
                getCost() == ((Egg) o).getCost() &&
                numOfEggs == ((Egg) o).numOfEggs) {
            return true;
        } else return false;
    }

}
