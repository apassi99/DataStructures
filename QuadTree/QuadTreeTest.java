package QuadTree;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class QuadTreeTest {

	/** Reference to the AVL tree on which testing will be performed*/
	private QuadTree<Integer> mTestTree;
	
	@Before
	public void setUp(){
		mTestTree = new QuadTree<Integer>();
	}
	
	@Test
	public void testInsert() {
		
		int count = 0;
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				count++;
				boolean value = mTestTree.insert((Integer) count, i, j);
				assertTrue(value);
			}
		}
		
		count = 0;
//		for (int i = 0; i < 100; i++) {
//			for (int j = 0; j < 100; j++) {
//				count++;
//				Integer integer = mTestTree.find(i, j);
//				assertEquals(integer, (Integer) count);
//			}
//		}
		
		assertNull(mTestTree.find(20, 20));
	}

}
