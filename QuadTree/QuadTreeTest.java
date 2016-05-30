package QuadTree;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for QuadTree.
 * 
 * @author Arjun Passi
 *
 */
public class QuadTreeTest {

	/** Reference to the AVL tree on which testing will be performed*/
	private QuadTree<Integer> mTestTree;
	
	private int DEFAULT_LENGTH = 250;
	
	@Before
	public void setUp(){
		mTestTree = new QuadTree<Integer>(0, DEFAULT_LENGTH, 0, DEFAULT_LENGTH);
	}
	
	/**
	 * Test method for {@link QuadTree#insert(Object, long, long)}
	 */
	@Test
	public void testInsert() {
		
		int count = 0;
		
		for (int i = 0; i <= DEFAULT_LENGTH; i++) {
			for (int j = 0; j <= DEFAULT_LENGTH; j++) {
				count++;
				boolean value = mTestTree.insert((Integer) count, i, j);
				assertTrue(value);
			}
		}
		
		count = 0;
		for (int i = 0; i <= DEFAULT_LENGTH; i++) {
			for (int j = 0; j <= DEFAULT_LENGTH; j++) {
				count++;
				boolean value = mTestTree.insert((Integer) count, i, j);
				assertFalse(value);
			}
		}
		
		for (int i = DEFAULT_LENGTH + 1; i < DEFAULT_LENGTH + 100; i++) {
			for (int j = DEFAULT_LENGTH + 1; j < DEFAULT_LENGTH + 100; j++) {
				boolean value = mTestTree.insert((Integer) count, i, j);
				assertFalse(value);
			}
		}
	}
	
	/**
	 * Test method for {@link QuadTree#find(long, long)}
	 */
	@Test
	public void testFind() {
		
		int count = 0;
		
		for (int i = 0; i <= DEFAULT_LENGTH + 10; i++) {
			for (int j = 0; j <= DEFAULT_LENGTH + 10; j++) {
				Integer integer = mTestTree.find(i, j);
				assertNull(integer);
			}
		}
		
		for (int i = 0; i <= DEFAULT_LENGTH; i++)
			for (int j = 0; j <= DEFAULT_LENGTH; j++)
				mTestTree.insert((Integer) count++, i, j);
		
		count = 0;
		for (int i = 0; i <= DEFAULT_LENGTH; i++) {
			for (int j = 0; j <= DEFAULT_LENGTH; j++) {
				Integer integer = mTestTree.find(i, j);
				assertEquals((Integer) count++, integer);
			}
		}
	}
	
	/**
	 * Test method for {@link QuadTree#find(long, long, long, long)
	 */
	@Test
	public void testFindInRectDomain() {
		
		
		List<Integer> list = mTestTree.find(0, DEFAULT_LENGTH, 0, DEFAULT_LENGTH);
		
		assertTrue(list.isEmpty());
				
		mTestTree.insert((Integer) 0, 0, 0);
		mTestTree.insert((Integer) 1, 5, 3);
		mTestTree.insert((Integer) 2, 5, 5);
		
		mTestTree.insert((Integer) 3, 60, 61);
		mTestTree.insert((Integer) 4, 60, 62);
		mTestTree.insert((Integer) 5, 65, 63);
		
		list = mTestTree.find(0, DEFAULT_LENGTH, 0, DEFAULT_LENGTH);
		
		
		for (int i = 0; i <= 5; i++)
			assertTrue(list.contains((Integer) i));
		
		list = mTestTree.find(5, 62, 5, 65);
		
		assertTrue(list.contains((Integer) 2));
		assertTrue(list.contains((Integer) 3));
		assertTrue(list.contains((Integer) 4));
	}

	/**
	 * Test method for {@link QuadTree#remove(long, long)}
	 */
	@Test
	public void testRemove() {
		
		int count = 0;
		
		for (int i = 0; i <= DEFAULT_LENGTH + 10; i++) {
			for (int j = 0; j <= DEFAULT_LENGTH + 10; j++) {
				Integer integer = mTestTree.remove(i, j);
				assertNull(integer);
			}
		}
		
		for (int i = 0; i <= DEFAULT_LENGTH; i++)
			for (int j = 0; j <= DEFAULT_LENGTH; j++)
				mTestTree.insert((Integer) count++, i, j);
		
		count = 0;
		for (int i = 0; i <= DEFAULT_LENGTH; i++) {
			for (int j = 0; j <= DEFAULT_LENGTH; j++) {
				Integer integer = mTestTree.remove(i, j);
				assertEquals((Integer) count++, integer);
			}
		}
		
		for (int i = 0; i <= DEFAULT_LENGTH; i++) {
			for (int j = 0; j <= DEFAULT_LENGTH; j++) {
				Integer integer = mTestTree.find(i, j);
				assertNull(integer);
			}
		}
	}
}
