package assignment;

import java.util.Random;

public class TreapNode<K extends Comparable<K>,V> {
    public K key;
    public V value;
    int priority;
    public TreapNode<K,V> left;
    public TreapNode<K,V> right;

    //defult constructor
    public TreapNode(K key, V value) {
        this.priority = new Random().nextInt(Treap.MAX_PRIORITY);
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }
    // overloaded constructor that accepts the priority as a param inputs
    // useful for testing
    public TreapNode(K key, V value, int priority) {
        this.priority = priority;
        this.key = key;
        this.value = value;
        this.left = null;
        this.right = null;
    }



}
