/**
 * Created by Zhang Yuyao on 2018/9/26.
 */
public class SeasonalFruit extends Fruit {
    public SeasonalFruit(String name, double weight, int pricePerKg) {
        super(name, weight, pricePerKg);
    }

    public int getCost() {
        return (int) (super.getCost() * 0.85);
    }
}
