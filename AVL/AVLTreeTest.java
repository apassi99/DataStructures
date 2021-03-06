package AVL;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * This is a test class for AVLTree
 * @author Arjun Passi
 *
 */
public class AVLTreeTest {

	/** Reference to the AVL tree on which testing will be performed*/
	private AVLTree<Integer> mTestTree;
	
	@Before
	public void setUp(){
		mTestTree = new AVLTree<Integer>();
	}
	
	/**
	 * Test method for {@link AVLTree#size()}
	 */
	@Test
	public void testSize(){
		assertEquals(mTestTree.size(), 0);
		mTestTree.insert(100);
		assertEquals(mTestTree.size(), 1);
		mTestTree.insert(100);
		assertEquals(mTestTree.size(), 1);
		mTestTree.insert(150);
		assertEquals(mTestTree.size(), 2);
		mTestTree.insert(200);
		assertEquals(mTestTree.size(), 3);
		mTestTree.insert(125);
		assertEquals(mTestTree.size(), 4);
	}
	
	/**
	 * Test method for {@link AVLTree#insert(Comparable)}
	 */
	@Test
	public void testInsert(){
		assertFalse(mTestTree.insert(null));
		assertEquals(mTestTree.size(), 0);
		assertTrue(mTestTree.insert(100));
		assertEquals(mTestTree.size(), 1);
		assertEquals(mTestTree.find(100), (Integer) 100);
		
		assertFalse(mTestTree.insert(100));
		assertEquals(mTestTree.size(), 1);
		assertEquals(mTestTree.find(100), (Integer) 100);
		
		assertTrue(mTestTree.insert(150));
		assertEquals(mTestTree.size(), 2);
		assertEquals(mTestTree.find(150), (Integer) 150);
		
		assertTrue(mTestTree.insert(200));
		assertEquals(mTestTree.size(), 3);
		assertEquals(mTestTree.find(200), (Integer) 200);
		
		mTestTree.insert(125);
		assertEquals(mTestTree.size(), 4);
		assertEquals(mTestTree.find(125), (Integer) 125);
		
		for(int i = 0; i < 50; i++){
			assertTrue(mTestTree.insert(i));
			assertEquals(mTestTree.size(), 5 + i);
			assertEquals(mTestTree.find(i), (Integer) i);
		}
	}
	
	/**
	 * Test method for {@link AVLTree#find(Comparable)}
	 */
	@Test
	public void testFind(){
		for(int i = 0; i < 100; i++){
			assertEquals(mTestTree.find(i), null);
			assertTrue(mTestTree.insert(i));
			assertEquals(mTestTree.find(i), (Integer) i);
		}
		
		assertEquals(mTestTree.find(null), null);
	}
	
	/**
	 * Test method for {@link AVLTree#remove(Comparable)}
	 */
	@Test
	public void testRemove(){
		assertFalse(mTestTree.remove(100));
		assertFalse(mTestTree.remove(200));
		assertFalse(mTestTree.remove(150));
		assertFalse(mTestTree.remove(null));
		
		assertTrue(mTestTree.insert(150));
		assertTrue(mTestTree.insert(100));
		assertTrue(mTestTree.insert(200));
		
		assertTrue(mTestTree.remove(150));
		assertEquals(mTestTree.find(150), null);
		assertEquals(mTestTree.size(), 2);
		assertEquals(mTestTree.find(100), (Integer) 100);
		assertEquals(mTestTree.find(200), (Integer) 200);
		
		assertTrue(mTestTree.remove(100));
		assertEquals(mTestTree.find(100), null);
		assertEquals(mTestTree.size(), 1);
		assertEquals(mTestTree.find(200), (Integer) 200);
		
		assertTrue(mTestTree.remove(200));
		assertEquals(mTestTree.find(200), null);
		assertEquals(mTestTree.size(), 0);
		
		for(int i = 0; i < 100; i++)
			assertTrue(mTestTree.insert(i));
		
		for(int j = 0; j < 100; j++){
			assertEquals(mTestTree.size(), 100 - j);
			assertTrue(mTestTree.remove(j));
			assertEquals(mTestTree.find(j), null);
		}
		
	}
	
	/**
	 * Test method for {@link AVLTree#removeAll()}
	 */
	@Test
	public void testRemoveAll(){
		for(int i = 0; i < 100; i++)
			assertTrue(mTestTree.insert(i));
		
		mTestTree.removeAll();
		
		assertEquals(mTestTree.size(), 0);
		
		for(int j = 0; j < 100; j++)
			assertEquals(mTestTree.find(j), null);
	}
	/**
	 * Test method for {@link AVLTree#insert(Comparable)}
	 */
	@Test
	public void testInOrderTraversal(){
		assertTrue(mTestTree.insert(1));
		assertTrue(mTestTree.insert(15));
		assertTrue(mTestTree.insert(10));
		assertTrue(mTestTree.insert(9));
		assertTrue(mTestTree.insert(4));
		assertTrue(mTestTree.insert(8));
		assertTrue(mTestTree.insert(14));
		assertTrue(mTestTree.insert(6));
		assertTrue(mTestTree.insert(3));
		assertTrue(mTestTree.insert(12));
		assertTrue(mTestTree.insert(2));
		assertTrue(mTestTree.insert(13));
		assertTrue(mTestTree.insert(5));
		assertTrue(mTestTree.insert(7));
		assertTrue(mTestTree.insert(11));
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i = 1; i <= 15; i++)
			list.add(i);
		
		assertEquals(list, mTestTree.inOrderTraversal());
		
	}
	
	/**
	 * Test method for {@link AVLTree#max()}
	 */
	@Test
	public void testMax(){
		assertEquals(mTestTree.max(), null);
		
		
		for(int i = 0; i < 1000; i++){
			mTestTree.insert(i);
			assertEquals(mTestTree.max(), (Integer) i);
		}

		mTestTree.remove(999);
		
		assertEquals(mTestTree.max(), (Integer) 998);
		mTestTree.remove(998);
		
		assertEquals(mTestTree.max(), (Integer) 997);
		
		mTestTree.remove(0);
		mTestTree.remove(80);
		mTestTree.remove(100);
		mTestTree.remove(300);
		
		assertEquals(mTestTree.max(), (Integer) 997);
		
		mTestTree.removeAll();
		
		assertEquals(mTestTree.max(), null);
	}
	
	/**
	 * Test method for {@link AVLTree#min()}.
	 */
	@Test
	public void testMin(){
		assertEquals(mTestTree.min(), null);
		
		for(int i = 1000; i >= 0; i--){
			mTestTree.insert(i);
			assertEquals(mTestTree.min(), (Integer) i);
		}
		
		mTestTree.remove(0);
		assertEquals(mTestTree.min(), (Integer) 1);
		
		mTestTree.remove(2);
		assertEquals(mTestTree.min(), (Integer) 1);
		
		mTestTree.remove(1);
		assertEquals(mTestTree.min(), (Integer) 3);
	}
	
	/**
	 * Test method for {@link AVLTree#equals(Object)}
	 */
	@Test
	public void testEquals(){
		AVLTree<Integer> tree = new AVLTree<Integer>();
		
		assertTrue(mTestTree.equals(tree));
		
		for(int i = 0; i <= 100; i++){
			mTestTree.insert(i);
			tree.insert(i);
			assertTrue(mTestTree.equals(tree));
		}
		
		for(int i = 100; i <= 50; i++){
			mTestTree.remove(i);
			tree.remove(i);
			assertTrue(mTestTree.equals(tree));
		}
		
		mTestTree.remove(49);
		assertFalse(mTestTree.equals(tree));
		
		tree.remove(49);
		assertTrue(mTestTree.equals(tree));
		
		assertFalse(mTestTree.equals(null));
		assertFalse(mTestTree.equals(1));
		
		mTestTree.removeAll();
		assertFalse(mTestTree.equals(tree));
		
		tree.removeAll();
		assertTrue(mTestTree.equals(tree));
	}
	
	/**
	 * Test method for {@link AVLTree#isSubTree(AVLTree)}
	 */
	@Test
	public void testIsSubTree(){
		AVLTree<Integer> tree = new AVLTree<Integer>();
		
		assertTrue(mTestTree.isSubTree(tree));
		assertFalse(mTestTree.isSubTree(null));
		
		mTestTree.insert(100);
		mTestTree.insert(50);
		mTestTree.insert(150);
		mTestTree.insert(25);
		mTestTree.insert(75);
		mTestTree.insert(125);
		mTestTree.insert(175);
		
		assertTrue(mTestTree.isSubTree(tree));
		
		tree.insert(100);
		assertFalse(mTestTree.isSubTree(tree));
		
		tree.insert(50);
		assertFalse(mTestTree.isSubTree(tree));
		
		tree.insert(150);
		assertFalse(mTestTree.isSubTree(tree));
		
		tree.removeAll();
		
		tree.insert(150);
		assertFalse(mTestTree.isSubTree(tree));
		tree.insert(125);
		assertFalse(mTestTree.isSubTree(tree));
		
		tree.insert(175);
		assertTrue(mTestTree.isSubTree(tree));
		
		tree.removeAll();
		
		tree.insert(25);
		assertTrue(mTestTree.isSubTree(tree));
	}

	/**
	 * Method to test {@link AVLTree#contains(Comparable)}
	 */
	public void testContains(){
		
		assertFalse(mTestTree.contains((Integer) 100));
		assertFalse(mTestTree.contains(null));
		assertFalse(mTestTree.contains((Integer) 200));
		
		mTestTree.insert(100);
		mTestTree.insert(200);
		mTestTree.insert(300);
		mTestTree.insert(400);
		mTestTree.insert(500);
		
		assertTrue(mTestTree.contains((Integer) 100));
		assertFalse(mTestTree.contains(null));
		assertTrue(mTestTree.contains((Integer) 200));
		assertTrue(mTestTree.contains((Integer) 300));
		assertTrue(mTestTree.contains((Integer) 400));
		assertTrue(mTestTree.contains((Integer) 500));
		
		mTestTree.remove((Integer) 100);
		assertFalse(mTestTree.contains((Integer) 100));
		assertTrue(mTestTree.contains((Integer) 200));
		assertTrue(mTestTree.contains((Integer) 300));
		assertTrue(mTestTree.contains((Integer) 400));
		assertTrue(mTestTree.contains((Integer) 500));
		
		mTestTree.removeAll();
		
		assertFalse(mTestTree.contains((Integer) 200));
		assertFalse(mTestTree.contains((Integer) 300));
		assertFalse(mTestTree.contains((Integer) 400));
		assertFalse(mTestTree.contains((Integer) 500));
		
	}
}
