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
	 * Test method for size()
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
	 * Test method for insert
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
	 * Test method for find
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
	 * Test method for remove
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
	 * Test method for removeAll
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
	 * Test method for inOrderTraversal
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
	 * Test method for max()
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
	 * Test method for minimum.
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

}
