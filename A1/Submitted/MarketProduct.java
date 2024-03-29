import java.util.Objects;

/**
 * Created by Zhang Yuyao on 2018/9/24.
 */
public abstract class MarketProduct {
    private String name;

    public MarketProduct(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    public abstract int getCost();

    public abstract boolean equals(Object o);
}
