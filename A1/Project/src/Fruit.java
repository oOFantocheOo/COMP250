
public class Fruit extends MarketProduct {
    private double weight;
    private int pricePerKg;

    public Fruit(String name, double weight, int pricePerKg) {
        super(name);
        this.weight = weight;
        this.pricePerKg = pricePerKg;
    }

    public int getCost() {
        return (int) (weight * pricePerKg);
    }

    public boolean equals(Object o) {
        if (o.getClass().equals(getClass()) &&
                getName().equals(((Fruit) o).getName()) &&
                getCost() == ((Fruit) o).getCost() &&
                weight == ((Fruit) o).weight) {
            return true;
        } else return false;
    }
}
