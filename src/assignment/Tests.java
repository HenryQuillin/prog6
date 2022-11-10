
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
    TreapMap<Integer,String> t;
    @Before
    public void setup() {
        t = new TreapMap<Integer,String>();
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
        // Asserts that the rotations are performed correctly
        t.print();
        Assert.assertTrue(t.getRoot().key ==6);
        Assert.assertTrue(t.getRoot().left.key == 4);
        Assert.assertTrue(t.getRoot().right.key == 8);
        Assert.assertTrue(t.getRoot().left.left.key== 3);
        Assert.assertTrue(t.getRoot().left.right.key== 5);
        Assert.assertTrue(t.getRoot().left.left.left.key==1);
        Assert.assertTrue(t.getRoot().right.right.key== 9);
    }
    @Test
    public void testInsert_DuplicateKeys() {
        // assert that nodes with a duplicate key are not added to the tree
        t.insert(5,"H",1000);
//        t.print();
        Assert.assertTrue(t.getRoot().key ==6);
        Assert.assertTrue(t.getRoot().left.key == 4);
        Assert.assertTrue(t.getRoot().right.key == 8);
        Assert.assertTrue(t.getRoot().left.left.key== 3);
        Assert.assertTrue(t.getRoot().left.right.key== 5);
        Assert.assertTrue(t.getRoot().left.left.left.key==1);
        Assert.assertTrue(t.getRoot().right.right.key== 9);
    }
    @Test
    public void testLookup() {

        // assert that when a valid key is provided, the correct value is returned
        String value = t.lookup(5);
//        t.print();
        Assert.assertEquals("E", value);

        // assert that when an invalid key is provided, the null is returned
        value = t.lookup(10);
        Assert.assertEquals(null, value);
    }
    @Test
    public void testDelete() {

        // assert that when a valid key is provided, the correct value is returned
        String value = t.remove(4);
        t.print();
//        System.out.println(value);
//        Assert.assertEquals("G", value);

//        Assert.assertTrue(t.getRoot().key== 6);
//        Assert.assertTrue(t.getRoot().left.key== 3);
//        Assert.assertTrue(t.getRoot().right.key== 8);
//        Assert.assertTrue(t.getRoot().left.left.key==1);
//        Assert.assertTrue(t.getRoot().left.right.key== 5);
//        Assert.assertTrue(t.getRoot().left.right.left== null);
//        Assert.assertTrue(t.getRoot().right.right.key== 9);
//        t = new TreapMap<Integer,String>();
//        t.insert(6, "A", 9403);
//        t.insert(8, "A", 7336);
//        t.insert(9, "A", 1936);
//        t.remove(6);
//        t.print();

        t = new TreapMap<Integer,String>();
        t.insert(50, "A", 15);
        t.insert(30, "B", 5);
        t.insert(20, "C", 2);
        t.insert(40, "D", 4);
        t.insert(70, "E", 10);
//        t.remove(50);
//        t.print();

        TreapMap<String,String> t1 = new TreapMap<String,String>();
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
//        t1.remove("K");
//        t1.print();



    }
    @Test
    public void testInorderTraversal() {
        List<Integer> nodes = t.inOrderTraversalOnlyKeys();
        List<Integer> expected = Arrays.asList(1, 3, 4, 5, 6, 8, 9);
        Assert.assertEquals(nodes,expected);
    }
    @Test
    public void testIterator() {
        Iterator<Integer> iterator = t.iterator();
        List<Integer> expected = Arrays.asList(1, 3, 4, 5, 6, 8, 9);
        Assert.assertEquals(expected.get(0),iterator.next());
        Assert.assertEquals(expected.get(1),iterator.next());
        Assert.assertEquals(expected.get(2),iterator.next());
        Assert.assertEquals(expected.get(3),iterator.next());
        Assert.assertEquals(expected.get(4),iterator.next());
        Assert.assertEquals(expected.get(5),iterator.next());
        Assert.assertEquals(expected.get(6),iterator.next());
    }
    @Test
    public void testNodeLookup() {
        Assert.assertEquals("C",t.nodeLookup(8).value);
    }
    @Test
    public void testSplit() {
        System.out.println("-----------T-----------");
        t.print();
        System.out.println("-----------TL-----------");
        TreapMap<Integer,String> tl =  (TreapMap<Integer,String>) t.split(4)[0];
        tl.print();
        System.out.println("-----------TR-----------");
        TreapMap<Integer,String> tr = (TreapMap<Integer,String>) t.split(4)[1];
        tr.print();
    }
    @Test
    public void testJoin() {
        System.out.println("------TR-------");

        TreapMap<Integer,String> tr = new TreapMap<Integer,String>();
        tr.insert(6,"A",9403);
        tr.insert(8,"B",7336);
        tr.insert(9,"C",1936);
        tr.print();
        System.out.println("------TL-------");
        TreapMap<Integer,String> tl = new TreapMap<Integer,String>();
        tl.insert(3,"D",4407);
        tl.insert(1,"E",2486);
        tl.insert(5,"F",1059);
        tl.print();
        tl.join(tr);
        System.out.println("------TR + TL-------");
        tl.print();


    }

//    @Test
//    public void testSplit() {
//        TreapMap<Integer,String> t = new TreapMap<Integer,String>();
//        t.insert(6, "A", 9403);
//        t.insert(3, "B", 4407);
//        t.insert(8, "C", 7336);
//        t.insert(1, "D", 2486);
//        t.insert(5, "E", 1059);
//        t.insert(9, "F", 1936);
//        t.insert(4, "G", 4743);
//        // assert that when a valid key is provided, the correct value is returned
//        t.print();
//        TreapMap<K, V>[] splitRes = t.split(6);
//        System.out.println("---------------------------");
//
//        splitRes[0].print();
//        System.out.println("---------------------------");
//        splitRes[1].print();
//
//    }
}