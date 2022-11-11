package assignment;
import java.util.ArrayList;
import java.util.Iterator;




public class TreapMap<K extends Comparable<K>,V> implements Treap<K,V>  {
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

    // helper method to perform a left rotation 
    private TreapNode<K,V> rotateLeft(TreapNode<K,V> root) {
        TreapNode<K,V> right = root.right;
        TreapNode<K,V> node2 = right.left;

        right.left = root;
        root.right = node2;

        return right;
    }

    // helper method to perform a right rotation 
    private TreapNode<K,V> rotateRight(TreapNode<K,V> root) {
        TreapNode<K,V> left = root.left;
        TreapNode<K,V> node2 = left.right;

        left.right = root;
        root.left = node2;

        return left;
    }

    @Override
    public V lookup(K key) {
        return recursiveLookup(globalRoot,key) != null ? recursiveLookup(globalRoot,key).value : null ;
    }
    public TreapNode<K,V> lookupNode(K key) {
        return recursiveLookup(globalRoot,key);
    }
    // private method helper method used to recursively find the node with the specified key
    private TreapNode<K,V> recursiveLookup(TreapNode<K,V> root, K key){
        // BASE CASE: key not found
        if (root == null) {
            return null;
        }

        // BASE CASE: key is found
        if (root.key == key) {
            return root;
        }
        int compared = key.compareTo(root.key);
        // if the key is less than that of the root node, search in the left subtree
        if (compared < 0) {
            return recursiveLookup(root.left, key);
        }
        // otherwise, search in the right subtree
        return recursiveLookup(root.right, key);
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

        // if the key of the new node is less than that of the root, insert in the left substree
        if (compared < 0) {
            root.left = recursiveInsert(node, root.left);
            // if the heap property is violated, rotate right
            if (root.left.priority > root.priority) {
                root = rotateRight(root);
            }
        } // if the new node is greater than the root, insert in the right substree
        else if (compared > 0) {
            root.right = recursiveInsert(node, root.right);
            // if the heap property is violated, rotate right
            if (root.right.priority > root.priority) {
                root = rotateLeft(root);
            }
        } else { // found a node with the same key 
            // Attempting to insert a duplicate key should update the value mapped to that key with the new value passed in.
            root.value = node.value; 
        }
        // otherwise, the added node is a duplicate, so do nothing.
        return root;
    }
    // global variable used in remove()
    private V removedKeyValue = null;
    @Override
    public V remove(K key) {
        removedKeyValue = null;
        globalRoot = recursiveRemove(globalRoot,key);
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
                else { // right child priority is less than left child
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
        return root;
}

    @Override
    public Treap<K, V> [] split(K key) {
        // Get the node that defines the split
        TreapNode<K,V> splitNode = this.lookupNode(key);
        TreapMap<K,V> tl;
        TreapMap<K,V> tr;
        // if the node doesn't exist
        if(splitNode == null ) {
            // Insert a new node with the same key and max priority to the treap
            this.insert(key,null, Treap.MAX_PRIORITY);
            tl = new TreapMap<>(this.globalRoot.left);
            tr = new TreapMap<>(this.globalRoot.right);
        } else {// if the node does
            // first remove the existing node and then insert a new node with the same key and max priority to the treap
            this.remove(key);
            this.insert(key,null, Treap.MAX_PRIORITY);
            // the left and right subtreaps of root will form the two treaps to be returned
            tl = new TreapMap<>(this.globalRoot.left);
            tr = new TreapMap<>(this.globalRoot.right);
            // insert the split node back into tr
            tr.insert(splitNode.key,splitNode.value,splitNode.priority);
        }
        return (Treap<K, V> []) new Treap[]{tl,tr};
    }

    @Override
    public void join(Treap<K, V> t) {
        // convert the passed in treap to a treap map
        TreapMap<K,V> t1 = (TreapMap<K, V>) t;
        int compared = this.getRoot().key.compareTo(t1.getRoot().key);
        TreapNode<K,V> x;
        // if the root's key is less than t1's key
        if (compared > 0) {
            // create an arbitrary node x with max p
            K arbitraryKey = this.getRoot().key;
            x = new TreapNode<K,V>(arbitraryKey, null, Treap.MAX_PRIORITY);

            //set the children of x
            x.left = t1.getRoot();
            x.right = this.getRoot();
            // if the root's key is greater than t1's key
        } else {
            // create an arbitrary node x with max p
            K arbitraryKey = t1.getRoot().key;
            x = new TreapNode<K,V>(arbitraryKey, null, Treap.MAX_PRIORITY);
            //set the children of x
            x.right = t1.getRoot();
            x.left = this.getRoot();
        }
        this.globalRoot = x;
        this.remove(x.key);
    }

    // Function to print binary tree in 2D
    // It does reverse inorder traversal
    private final int COUNT = 10;
    public void print()
    {
        recursivePrint(globalRoot, 0);
    }
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
        System.out.print("["+root.priority +"] " + "<"+root.key + ", " + root.value + ">" + "\n");

        // Process left child
        recursivePrint(root.left, space);
    }
    private final int TAB = 4;

    public String toString(){
        return recursiveToString(globalRoot,0, new StringBuilder()).toString();
    }

    private StringBuilder recursiveToString(TreapNode<K,V> root, int space, StringBuilder s)
    {
        if (root == null)
            return new StringBuilder();

        // Add spacing between levels equal to one tab
        // A tab is 4 spaces
        space += TAB;

        // Process left child
        recursiveToString(root.left, space,s);

        // Process right child
        recursiveToString(root.right, space,s);
        // Print current node after space
        s.append("\n");
        for (int i = TAB; i < space; i++) s.append(" ");
        s.append("[").append(root.priority).append("] ").append("<").append(root.key).append(", ").append(root.value).append(">").append("\n");
        return s;
    }
    @Override
    public Iterator<K> iterator() {
        ArrayList<K> list = inOrderTraversalOnlyKeys();
        return list.iterator();
    }

    protected ArrayList<K> inOrderTraversalOnlyKeys(){
        ArrayList<K> nodes = new ArrayList<K>();
        return recursiveInOrderTraversalOnlyKeys(globalRoot, nodes);
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
    public void meld(Treap<K, V> t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void difference(Treap<K, V> t) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
    @Override
    public double balanceFactor() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}


