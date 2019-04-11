/**
 * Created by Zhang Yuyao on 2018/9/26.
 */
public class Jam extends MarketProduct {
    private int numOfJars;
    private int pricePerJar;

    public Jam(String name, int numOfJars, int pricePerJar) {
        super(name);
        this.numOfJars = numOfJars;
        this.pricePerJar = pricePerJar;
    }

    public int getCost() {
        return numOfJars * pricePerJar;
    }

    public boolean equals(Object o) {
        if (o.getClass().equals(getClass()) &&
                getName().equals(((Jam) o).getName()) &&
                getCost() == ((Jam) o).getCost() &&
                numOfJars == ((Jam) o).numOfJars) {
            return true;
        } else return false;
    }
}
