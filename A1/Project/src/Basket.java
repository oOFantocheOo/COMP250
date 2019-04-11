
public class Basket {
    private MarketProduct[] products;

    private static MarketProduct[] append(MarketProduct[] array, MarketProduct p) {
        MarketProduct[] result = new MarketProduct[array.length + 1];
        for (int i = 0; i < array.length; i++) result[i] = array[i];
        result[result.length - 1] = p;
        return result;
    }
    /*helper function to copy and expand the array*/

    private static MarketProduct[] delete(MarketProduct[] array, MarketProduct p) {
        MarketProduct[] result = new MarketProduct[array.length - 1];
        int idx = -1;
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i].equals(p)) {
                idx = i;
                break;
            } else result[i] = array[i];
        }
        if (idx == -1)
            if (!(array[array.length - 1].equals(p)))
                return null;
            else
                return result;
        for (int i = idx + 1; i < array.length; i++) {
            result[i - 1] = array[i];
        }
        return result;
    }
    /*helper function to remove from the array*/

    private static String toDollar(int i) {
        if (i <= 0) return "-";
        String strDouble = String.format("%.2f", 1.9999)
        return Double.toString((double) (i) / 100);
    }


    public Basket() {
        products = new MarketProduct[]{};
    }

    public MarketProduct[] getProducts() {
        if (products == null) return null;
        MarketProduct[] copy = new MarketProduct[products.length];
        if (products.length == 0) return copy;
        for (int i = 0; i < products.length; i++) copy[i] = products[i];
        return copy;
    }

    public void add(MarketProduct p) {
        products = append(products, p);
    }

    public boolean remove(MarketProduct p) {
        if(products.length==0) return false;
        MarketProduct[] tmp = delete(products, p);
        if (tmp == null) {
            return false;
        } else {
            products = tmp;
            return true;
        }
    }

    public void clear() {
        products = new MarketProduct[]{};
    }

    public int getNumOfProducts() {
        return products.length;
    }

    public int getSubTotal() {
        int sum = 0;
        for (int i = 0; i < products.length; i++) {
            sum += products[i].getCost();
        }
        return sum;
    }

    public int getTotalTax() {
        double res = 0;
        for (int i = 0; i < products.length; i++) {
            if (products[i] instanceof Jam) {
                res += 0.15 * products[i].getCost();
            }
        }
        return (int) res;
    }

    public int getTotalCost() {
        return getSubTotal() + getTotalTax();
    }

    public String toString() {
        String res = "";
        String conclusion="";
        conclusion += '\n';
        conclusion += "Subtotal\t";
        conclusion += toDollar(getSubTotal());
        conclusion += '\n';
        conclusion += "Total Tax\t";
        conclusion += toDollar(getTotalTax());
        conclusion += '\n';
        conclusion += '\n';
        conclusion += "Total Cost\t";
        conclusion += toDollar(getTotalCost());
        for (int i = 0; i < products.length; i++) {
            res += products[i].getName();
            res += '\t';
            res += toDollar(products[i].getCost());
            res += '\n';
        }

        return res+conclusion;

    }
}
