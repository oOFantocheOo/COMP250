
public class Customer {
    private String name;
    private int balance;
    private Basket basket;

    public Customer(String n, int b) {
        name = n;
        balance = b;
        basket = new Basket();
    }

    public String getName() {
        return name;
    }


    public int getBalance() {
        return balance;
    }

    public Basket getBasket() {
        return basket;
    }

    public int addFunds(int f) {
        if (f < 0) throw new IllegalArgumentException("Cannot add negative funds!");
        balance += f;
        return balance;
    }

    public void addToBasket(MarketProduct p) {
        basket.add(p);
    }

    public boolean removeFromBasket(MarketProduct p) {
        return basket.remove(p);
    }

    public String checkOut() {
        if (balance < basket.getTotalCost()) throw new IllegalStateException("No enough money");
        balance -= (basket.getTotalCost());
        String res=basket.toString();
        basket.clear();
        return res;
    }
}
