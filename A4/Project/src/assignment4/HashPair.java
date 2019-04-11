//260832483 Yuyao Zhang

package assignment4;

public class HashPair<K,V> {
    private K key;
    private V value;
    private static int a=0;
    /*
     * Constructor
     */
    public HashPair(K key, V value) {
        this.key = key;
        this.value = value;
        int c=a;
    }
    
    /**
     * Returns key of this HashPair
     */
    public K getKey() {
        return this.key;
    }
    /**
     * Return Value of this HashPair
     */
    public V getValue() {
        return this.value;
    }
    
    /**
     * Set the value of this HashPair
     */
    public void setValue(V value) {
        this.value = value;
    }
}
