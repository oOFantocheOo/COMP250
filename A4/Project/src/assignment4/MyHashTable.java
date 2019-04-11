//260832483 Yuyao Zhang

package assignment4;

import com.sun.xml.internal.ws.api.ha.HaInfo;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyHashTable<K, V> implements Iterable<HashPair<K, V>> {
    // num of entries to the table
    private int numEntries;
    // num of buckets 
    private int numBuckets;
    // load factor needed to check for rehashing 
    private static final double MAX_LOAD_FACTOR = 0.75;
    // ArrayList of buckets. Each bucket is a LinkedList of HashPair
    private ArrayList<LinkedList<HashPair<K, V>>> buckets;

    ///empty cases
    // constructor
    public MyHashTable(int initialCapacity) {
        // ADD YOUR CODE BELOW THIS
        numEntries = 0;
        numBuckets = initialCapacity;
        buckets = new ArrayList<>(initialCapacity);
        for (int i = 0; i < initialCapacity; i++)
            buckets.add(new LinkedList<>());
        //ADD YOUR CODE ABOVE THIS
    }

    public int size() {
        return this.numEntries;
    }

    public int numBuckets() {
        return this.numBuckets;
    }

    /**
     * Returns the buckets vairable. Useful l for testing  purposes.
     */
    public ArrayList<LinkedList<HashPair<K, V>>> getBuckets() {
        return this.buckets;
    }

    /**
     * Given a key, return the bucket position for the key.
     */
    public int hashFunction(K key) {
        int hashValue = Math.abs(key.hashCode()) % this.numBuckets;
        return hashValue;
    }
    /**
     * Takes a key and a value as input and adds the corresponding HashPair
     * to this HashTable. Expected average run time  O(1)
     */
    public V put(K key, V value) {
        //  ADD YOUR CODE BELOW HERE
        if ((double)numEntries/(double)numBuckets>MAX_LOAD_FACTOR)
            rehash();
        V v = remove(key);
        buckets.get(hashFunction(key)).add(new HashPair<>(key, value));
        numEntries++;
        return v;
        //  ADD YOUR CODE ABOVE HERE
    }


    /**
     * Get the value corresponding to key. Expected average runtime = O(1)
     */

    public V get(K key) {
        //ADD YOUR CODE BELOW HERE
        for (HashPair<K, V> HP : buckets.get(hashFunction(key)))
            if (HP.getKey().equals(key))
                return HP.getValue();
        return null;
        //ADD YOUR CODE ABOVE HERE
    }

    /**
     * Remove the HashPair correspoinding to key . Expected average runtime O(1)
     */
    public V remove(K key) {
        //ADD YOUR CODE BELOW HERE
        V v = get(key);
        if (v == null)
            return null;
        int idx = 0;
        for (HashPair<K, V> HP : buckets.get(hashFunction(key)))
            if (HP.getKey().equals(key))
                break;
            else idx++;
        buckets.get(hashFunction(key)).remove(idx);
        numEntries--;
        return v;
        //ADD YOUR CODE ABOVE HERE
    }

    // Method to double the size of the hashtable if load factor increases
    // beyond MAX_LOAD_FACTOR.
    // Made public for ease of testing.

    public void rehash() {
        //ADD YOUR CODE BELOW HERE
        ArrayList<LinkedList<HashPair<K, V>>> newBuckets = new ArrayList<>(numBuckets * 2);
        for (int i = 0; i < numBuckets * 2; i++)
            newBuckets.add(new LinkedList<>());
        for (K k : keys()) {
            V v = get(k);
            newBuckets.get(Math.abs(k.hashCode()) % (2 * this.numBuckets)).add(new HashPair<>(k, v));
        }
        numBuckets *= 2;
        buckets = newBuckets;
        //ADD YOUR CODE ABOVE HERE
    }


    /**
     * Return a list of all the keys present in this hashtable.
     */

    public ArrayList<K> keys() {
        //ADD YOUR CODE BELOW HERE
        ArrayList<K> arr = new ArrayList<>();
        Iterator itr = iterator();
        while (itr.hasNext()) {
            HashPair<K, V> tmp = (HashPair<K, V>) itr.next();
            arr.add(tmp.getKey());
        }
        return arr;
        //ADD YOUR CODE ABOVE HERE
    }
///test remove 0 etc
    /**
     * Returns an ArrayList of unique values present in this hashtable.
     * Expected average runtime is O(n)
     */
    public ArrayList<V> values() {
        //ADD CODE BELOW HERE
        ArrayList<V> arr = new ArrayList<V>();
        Iterator itr = iterator();
        while (itr.hasNext()) {
            HashPair<K, V> tmp = (HashPair<K, V>) itr.next();
            arr.add(tmp.getValue());
        }
        MyHashTable<V, Integer> tmp = new MyHashTable<>(size());
        for (V v : arr)
            tmp.put(v, 0);
        return tmp.keys();
        //ADD CODE ABOVE HERE
    }


    @Override
    public MyHashIterator iterator() {
        return new MyHashIterator();
    }


    private class MyHashIterator implements Iterator<HashPair<K, V>> {
        private LinkedList<HashPair<K, V>> entries;
        private Iterator itr;

        private MyHashIterator() {
            //ADD YOUR CODE BELOW HERE
            entries = new LinkedList<>();
            Iterator itrArr = buckets.iterator();
            while (itrArr.hasNext()) {
                Iterator itrLL = ((LinkedList<HashPair<K, V>>) itrArr.next()).iterator();
                while (itrLL.hasNext())
                    entries.add(((HashPair<K, V>) itrLL.next()));
            }
            itr = entries.iterator();
            //ADD YOUR CODE ABOVE HERE
        }

        @Override
        public boolean hasNext() {
            //ADD YOUR CODE BELOW HERE
            return itr.hasNext();
            //ADD YOUR CODE ABOVE HERE
        }

        @Override
        public HashPair<K, V> next() {
            //ADD YOUR CODE BELOW HERE
            return (HashPair<K, V>) itr.next();
            //ADD YOUR CODE ABOVE HERE
        }

    }
}
