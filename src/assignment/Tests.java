
package assignment;

import assignment.Treap;
import assignment.TreapMap;
import assignment.TreapNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import java.util.*;

public class Tests<K extends Comparable<K>,V> {
    TreapMap<Integer, String> t;

    @Before
    public void setup() {
        // Set the default treap used by most tests
        t = new TreapMap<>();
        t.insert(6, "A", 9403);
        t.insert(3, "B", 4407);
        t.insert(8, "C", 7336);
        t.insert(1, "D", 2486);
        t.insert(5, "E", 1059);
        t.insert(9, "F", 1936);
        t.insert(4, "G", 4743);
    }

    @Test
    public void testInsert() {
        // Assert that the rotations are performed correctly
        t.print();
        Assert.assertTrue(t.getRoot().key == 6);
        Assert.assertTrue(t.getRoot().left.key == 4);
        Assert.assertTrue(t.getRoot().right.key == 8);
        Assert.assertTrue(t.getRoot().left.left.key == 3);
        Assert.assertTrue(t.getRoot().left.right.key == 5);
        Assert.assertTrue(t.getRoot().left.left.left.key == 1);
        Assert.assertTrue(t.getRoot().right.right.key == 9);

        //  //Attempting to insert a duplicate key should update the value mapped to that key with the new value passed in.
        t.insert(5, "H");
        Assert.assertTrue(t.lookup(5) == "H");
//        t.print();
        Assert.assertTrue(t.getRoot().key == 6);
        Assert.assertTrue(t.getRoot().left.key == 4);
        Assert.assertTrue(t.getRoot().right.key == 8);
        Assert.assertTrue(t.getRoot().left.left.key == 3);
        Assert.assertTrue(t.getRoot().left.right.key == 5);
        Assert.assertTrue(t.getRoot().left.left.left.key == 1);
        Assert.assertTrue(t.getRoot().right.right.key == 9);




        // Check that insert works for character keys
        TreapMap<Character, String> t1 = new TreapMap<Character, String>();
        t1.insert('F', "A", 9403);
        t1.insert('C', "B", 4407);
        t1.insert('H', "C", 7336);
        t1.insert('A', "D", 2486);
        t1.insert('E', "E", 1059);
        t1.insert('I', "F", 1936);
        t1.insert('D', "G", 4743);
        t1.print();


    }

    @Test
    public void testLookup() {

        // assert that when a valid key is provided, the correct value is returned
        String value = t.lookup(5);
        Assert.assertEquals("E", value);
        value = t.lookup(3);
        Assert.assertEquals("B", value);
        value = t.lookup(8);
        Assert.assertEquals("C", value);

        // assert that when an invalid key is provided, the null is returned
        value = t.lookup(10);
        Assert.assertEquals(null, value);

        //Check that lookup works for non int types
        TreapMap<Character, String> t1 = new TreapMap<Character, String>();
        t1.insert('F', "A", 9403);
        t1.insert('C', "B", 4407);
        t1.insert('H', "C", 7336);
        t1.insert('A', "D", 2486);
        t1.insert('E', "E", 1059);
        t1.insert('I', "F", 1936);
        t1.insert('D', "G", 4743);
        Assert.assertEquals("B", t1.lookup('C'));
    }

    @Test
    public void testDelete() {

        // assert that when a valid key is provided, the correct value is returned
        String value = t.remove(4);
//        t.print();
        Assert.assertEquals("G", value);
        Assert.assertTrue(t.getRoot().key == 6);
        Assert.assertTrue(t.getRoot().left.key == 3);
        Assert.assertTrue(t.getRoot().right.key == 8);
        Assert.assertTrue(t.getRoot().left.left.key == 1);
        Assert.assertTrue(t.getRoot().left.right.key == 5);
        Assert.assertTrue(t.getRoot().left.right.left == null);
        Assert.assertTrue(t.getRoot().right.right.key == 9);

        t = new TreapMap<>();
        t.insert(6, "A", 9403);
        t.insert(8, "A", 7336);
        t.insert(9, "A", 1936);
        t.remove(6);
//        t.print();

        t = new TreapMap<>();
        t.insert(50, "A", 15);
        t.insert(30, "B", 5);
        t.insert(20, "C", 2);
        t.insert(40, "D", 4);
        t.insert(70, "E", 10);
//        t.print();

        // check that delete works for string keys
        TreapMap<String, String> t1 = new TreapMap<String, String>();
        t1.insert("G", null, 50);
        t1.insert("F", null, 40);
        t1.insert("H", null, 29);
        t1.insert("C", null, 35);
        t1.insert("I", null, 25);
        t1.insert("B", null, 24);
        t1.insert("E", null, 33);
        t1.insert("L", null, 16);
        t1.insert("A", null, 21);
        t1.insert("D", null, 8);
        t1.insert("J", null, 13);
        t1.insert("K", null, 9);
        System.out.println(t1.remove("C"));
//        t1.print();

    }

    @Test
    public void testInorderTraversal() {
        List<Integer> nodes = t.inOrderTraversalOnlyKeys();
        List<Integer> expected = Arrays.asList(1, 3, 4, 5, 6, 8, 9);
        Assert.assertEquals(nodes, expected);
    }

    @Test
    public void testIterator() {
        Iterator<Integer> iterator = t.iterator();
        List<Integer> expected = Arrays.asList(1, 3, 4, 5, 6, 8, 9);
        Assert.assertEquals(expected.get(0), iterator.next());
        Assert.assertEquals(expected.get(1), iterator.next());
        Assert.assertEquals(expected.get(2), iterator.next());
        Assert.assertEquals(expected.get(3), iterator.next());
        Assert.assertEquals(expected.get(4), iterator.next());
        Assert.assertEquals(expected.get(5), iterator.next());
        Assert.assertEquals(expected.get(6), iterator.next());
    }

    @Test
    public void testNodeLookup() {
        Assert.assertEquals("C", t.lookupNode(8).value);
    }

    @Test
    public void testSplit() {
        System.out.println("-----------T (split 4)-----------");
        t.print();
        Treap<Integer, String>[] tArr = t.split(4);
        TreapMap<Integer, String> tl = (TreapMap<Integer, String>) tArr[0];
        TreapMap<Integer, String> tr = (TreapMap<Integer, String>) tArr[1];
        System.out.println("-----------TL-----------");
        tl.print();
        System.out.println("-----------TR-----------");
        tr.print();


        t = new TreapMap<>();
        t.insert(50, "A", 15);
        t.insert(30, "B", 5);
        t.insert(20, "C", 2);
        t.insert(40, "D", 4);
        t.insert(70, "E", 10);
        System.out.println("------------T------------");
        t.print();
        tArr = t.split(40);
        tl = (TreapMap<Integer, String>) tArr[0];
        tr = (TreapMap<Integer, String>) tArr[1];
        System.out.println("------------TL (K=40)------------");
        tl.print();
        System.out.println("------------TR (K=40)------------");
        tr.print();
    }

    @Test
    public void testJoin() {
        System.out.println("-----------T (split 4)-----------");
        t.print();
        Treap<Integer, String>[] tArr = t.split(4);
        TreapMap<Integer, String> tl = (TreapMap<Integer, String>) tArr[0];
        TreapMap<Integer, String> tr = (TreapMap<Integer, String>) tArr[1];
        System.out.println("-----------TL-----------");
        tl.print();
        System.out.println("-----------TR-----------");
        tr.print();
        System.out.println("------TR + TL-------");
        tl.join(tr);
        tl.print();
        setup();
        System.out.println("-----------T (split 6)-----------");
        t.print();
        tArr = t.split(6);
        tl = (TreapMap<Integer, String>) tArr[0];
        tr = (TreapMap<Integer, String>) tArr[1];
        System.out.println("-----------TL-----------");
        tl.print();
        System.out.println("-----------TR-----------");
        tr.print();
        System.out.println("------TR + TL-------");
        tl.join(tr);
        tl.print();

        t = new TreapMap<>();
        t.insert(50, "A", 15);
        t.insert(30, "B", 5);
        t.insert(20, "C", 2);
        t.insert(40, "D", 4);
        t.insert(70, "E", 10);
        System.out.println("------------T------------");
        t.print();
        tArr = t.split(40);
        tl = (TreapMap<Integer, String>) tArr[0];
        tr = (TreapMap<Integer, String>) tArr[1];
        System.out.println("------------TL (K=40)------------");
        tl.print();
        System.out.println("------------TR (K=40)------------");
        tr.print();
        tl.join(tr);
        System.out.println("------------TL + TR------------");
        tl.print();

    }

    @Test
    public void testToString() {
        System.out.println("TREE VIEW ----------");
        t.print();
        System.out.println("TOSTRING() VIEW ----------");
        String s = t.toString();
        System.out.println(s);
    }

}
