package assignment;

import java.util.ArrayList;
import java.util.Iterator;




public class TreapMap<K extends Comparable<K>,V>  implements Treap<K,V>  {
    // helper method to rotate with left child
    private TreapNode<K,V> globalRoot;
    //getter for the globalRoot. used for testing
    public TreapNode<K,V> getRoot(){
        return globalRoot;
    }
    public TreapMap() {
        globalRoot = null;
    }
    public TreapMap(TreapNode<K,V> root) {
        this.globalRoot = root;
    }


    private TreapNode<K,V> rotateLeft(TreapNode<K,V> root) {
        TreapNode<K,V> right = root.right;
        TreapNode<K,V> node2 = right.left;

        right.left = root;
        root.right = node2;

        return right;
    }

    // helper method to rotate with right child
    private TreapNode<K,V> rotateRight(TreapNode<K,V> root) {
        TreapNode<K,V> left = root.left;
        TreapNode<K,V> node2 = left.right;

        left.right = root;
        root.left = node2;

        return left;
    }


    @Override
    public V lookup(K key) {
        return recursiveLookup(globalRoot,key);
    }
    // private method to recusively find the node with the specified key
    private V recursiveLookup(TreapNode<K,V> root, K key){
        // BASE CASE: key not found
        if (root == null) {
            return null;
        }

        // BASE CASE: key is found
        if (root.key == key) {
            return root.value;
        }
        int compared = key.compareTo(root.key);
        // if the key is less than the root node, search in the left subtree
        if (compared < 0) {
            return recursiveLookup(root.left, key);
        }
        // otherwise, search in the right subtree
        return recursiveLookup(root.right, key);
    }

    public TreapNode<K,V> nodeLookup(K key) {
        return recursiveNodeLookup(globalRoot,key);
    }
    private TreapNode<K,V> recursiveNodeLookup(TreapNode<K,V> root, K key){
        // BASE CASE: key not found
        if (root == null) {
            return null;
        }

        // BASE CASE: key is found
        if (root.key == key) {
            return root;
        }
        int compared = key.compareTo( root.key);
        // if the key is less than the root node, search in the left subtree
        if (compared < 0) {
            return recursiveNodeLookup(root.left, key);
        }
        // otherwise, search in the right subtree
        return recursiveNodeLookup(root.right, key);
    }

    @Override
    public void insert(K key, V value) {
        TreapNode<K,V> newNode = new TreapNode<>(key, value);
        globalRoot = recursiveInsert(newNode, globalRoot);
    }
    public void insert(K key, V value, int priority) {
        TreapNode<K,V> newNode = new TreapNode<>(key, value, priority);
        globalRoot = recursiveInsert(newNode, globalRoot);
    }


    private TreapNode<K,V> recursiveInsert(TreapNode<K,V> node, TreapNode<K,V> root) {
        // base case
        if (root == null) {
            return node;
        }

        int compared = node.key.compareTo(root.key);

        // if the new node is less than the root, insert in the left substree
        if (compared < 0) {
            root.left = recursiveInsert(node, root.left);
            // if the heap property is violated, rotate right
            if (root.left.priority > root.priority) {
                root = rotateRight(root);
            }
        } // otherwise, insert in the right subtree
        else if (compared > 0) {
            root.right = recursiveInsert(node, root.right);
            // if the heap property is violated, rotate right
            if (root.right.priority > root.priority) {
                root = rotateLeft(root);
            }
        } else {
            System.err.println("The inserted node was not added to the tree because there exists node with the same key");
        }
        // otherwise, the added node is a duplicate, so do nothing.
        return root;
    }
    // global variable used in remove()
    private V removedKeyValue = null;
    @Override
    public V remove(K key) {
        removedKeyValue = null;
        recursiveRemove(globalRoot,key);
        return removedKeyValue;
    }

        private TreapNode<K,V> recursiveRemove(TreapNode<K,V> root, K key){
            // base case: key not in tree


            if (root == null) {
                return null;
            }
            int compared = key.compareTo( root.key);

            // if the key is less than the root node, traverse left subtree
            if (compared < 0) {
                root.left = recursiveRemove(root.left, key);
            }
            // if the key is more than the root node,traverse right subtree
            else if (compared > 0) {
                root.right = recursiveRemove(root.right, key);
            }
            else { // if the else statement is entered, we know the key is found
                // set the removedKeyValue to root.value, so we can return the correct value in remove() while still
                // allowing recursiveRemove to return a TreapNode
                removedKeyValue = root.value;
                // if node is a leaf node
                if (root.left == null && root.right == null)
                {
                    root = null;
                }
                // if node has two children
                else if (root.left != null && root.right != null)
                {
                    // left child priority is less than right child
                    if (root.left.priority < root.right.priority)
                    {
                        root = rotateLeft(root);
                        // delete the left child
                        root.left = recursiveRemove(root.left, key);
                    }
                    else {
                        root = rotateRight(root);
                        // elete the right child
                        root.right = recursiveRemove(root.right, key);
                    }
                }

                // if node has only one child
                else {
                    root = (root.left != null) ? root.left: root.right;
                }
            }
            this.print();
            System.out.println("------------");
            return root;
    }

    @Override
    public Treap<K, V> [] split(K key) {
        V find = this.lookup(key);
        TreapMap<K,V> tl;
        TreapMap<K,V> tr;
        if(find == null ) {
            this.insert(key,null, Treap.MAX_PRIORITY);
            tl = new TreapMap<>(this.globalRoot.left);
            tr = new TreapMap<>(this.globalRoot.right);

        } else {
            this.remove(key);
            this.insert(key,null, Treap.MAX_PRIORITY);
            tl = new TreapMap<>(this.globalRoot.left);
            tr = new TreapMap<>(this.globalRoot.right);
        }
        return (Treap<K, V> []) new Treap[]{tl,tr};
    }

    @Override
    public void join(Treap<K, V> t) {
        TreapMap<K,V> t1 = (TreapMap<K, V>) t;
        int compared = this.getRoot().key.compareTo(t1.getRoot().key);
        TreapNode<K,V> x;
        if (compared > 0) {
            K arbitraryKey = this.getRoot().key;
            x = new TreapNode<K,V>(arbitraryKey, null, Treap.MAX_PRIORITY);
            x.left = t1.getRoot();
            x.right = this.getRoot();
        } else {
            K arbitraryKey = t1.getRoot().key;
            x = new TreapNode<K,V>(arbitraryKey, null, Treap.MAX_PRIORITY);
            x.right = t1.getRoot();
            x.left = this.getRoot();
        }
        this.globalRoot = x;
    }

    @Override
    public void meld(Treap<K, V> t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void difference(Treap<K, V> t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(); 
    }

    @Override
    public Iterator<K> iterator() {
        ArrayList<K> list = inOrderTraversalOnlyKeys();
        return list.iterator();
    }


    protected ArrayList<TreapNode<K,V>> inOrderTraversal(){
        ArrayList<TreapNode<K,V>> nodes = new ArrayList<TreapNode<K,V>>();
        return recursiveInOrderTraversal(globalRoot, nodes);
    }

    protected ArrayList<K> inOrderTraversalOnlyKeys(){
        ArrayList<K> nodes = new ArrayList<K>();
        return recursiveInOrderTraversalOnlyKeys(globalRoot, nodes);
    }

    protected ArrayList<TreapNode<K,V>> recursiveInOrderTraversal(TreapNode<K,V> root, ArrayList<TreapNode<K,V>> nodes){
        if (root == null)
            return new ArrayList<TreapNode<K,V>>();

        // Traverse left subtree
        recursiveInOrderTraversal(root.left, nodes);

        // Visit node
        nodes.add(root);

        // Traverse right subtree
        recursiveInOrderTraversal(root.right, nodes);

        return nodes;
    }
    protected ArrayList<K> recursiveInOrderTraversalOnlyKeys(TreapNode<K,V> root, ArrayList<K> nodes){

        if (root == null)
            return new ArrayList<K>();

        // Traverse left subtree
        recursiveInOrderTraversalOnlyKeys(root.left, nodes);

        // Visit node
        nodes.add( root.key);

        // Traverse right subtree
        recursiveInOrderTraversalOnlyKeys(root.right, nodes);

        return nodes;
    }

    @Override
    public double balanceFactor() throws UnsupportedOperationException {
        throw new UnsupportedOperationException(); 
    }
    // Function to print binary tree in 2D
    // It does reverse inorder traversal
    private final int COUNT = 10;
    private void recursivePrint(TreapNode<K,V> root, int space)
    {
        if (root == null)
            return;

        // Add spacing between levels
        space += COUNT;

        // Process right child first
        recursivePrint(root.right, space);

        // Print current node after space
        // count
        System.out.print("\n");
        for (int i = COUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.key + ", " + root.value + " (" + root.priority + ")" + "\n");

        // Process left child
        recursivePrint(root.left, space);
    }


    public void print()
    {
        recursivePrint(globalRoot, 0);
    }
}


