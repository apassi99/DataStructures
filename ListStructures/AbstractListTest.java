package ListStructures;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests SingleLinkedList and DoubleLinkedList.
 * 
 * @author Arjun Passi
 *
 */
public abstract class AbstractListTest {

	/** Reference to the list testing will be perfomed on*/
	private List<Integer> mTestList;
	
	// ----------------------------------------------------------
	/**
	 * Method to construct an appropriate List
	 * on which testing will be performed on.
	 * @return
	 */
	public abstract List<Integer> create();
	
	// ----------------------------------------------------------
	/**
	 * Method to set up the list.
	 */
	@Before
	public void setUp(){
		mTestList = create();
	}
	
	// ----------------------------------------------------------
	/**
	 * Method to test {@link List#size()}
	 */
	@Test
	public void testSize(){
		assertEquals(mTestList.size(), 0);
		
		for(int i = 1; i <= 1000; i++){
			mTestList.insertFront(i);
			assertEquals(mTestList.size(), i);
		}
	}
	
	// ----------------------------------------------------------
	/**
	 * Method to test {@link List#isEmpty()}
	 */
	@Test
	public void testIsEmpty(){
		assertTrue(mTestList.isEmpty());
		
		for(int i = 0; i < 100; i++)
			mTestList.insertFront(i);
		
		assertFalse(mTestList.isEmpty());
		
		mTestList.clear();
		
		assertTrue(mTestList.isEmpty());
	}
	
	// ----------------------------------------------------------
	/**
	 * Method to test {@link List#insert(int, Comparable)}
	 */
	@Test
	public void testInsert(){
		
		Exception exception = null;
		
		for(int i = 0; i < 100; i++){
			try{
				mTestList.insert(i, i);
			} catch (IllegalArgumentException e){
				exception = e;
			} catch (IndexOutOfBoundsException e){
				exception = e;
			}
			assertEquals(mTestList.size(), i + 1);
			assertNull(exception);
		}
		
		for(int i = 0; i < 100; i++)
			assertEquals(mTestList.get(i), (Integer) i);
		
		assertEquals(mTestList.getFirst(), (Integer) 0);
		assertEquals(mTestList.getLast(), (Integer) 99);
		
		mTestList.insert(50, -1);
		
		assertEquals(mTestList.get(50), (Integer) (-1));
		assertEquals(mTestList.get(51), (Integer) (50));
		assertEquals(mTestList.get(49), (Integer) (49));
		
		//PROVIDING negative Index
		try {
			mTestList.insert(-1, (Integer) 0);
		} catch (IllegalArgumentException e){
			exception = e;
		} catch (IndexOutOfBoundsException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IndexOutOfBoundsException);
		
		exception = null;
		
		//PROVIDING INDEX > size
		try {
			mTestList.insert(100000, (Integer) 0);
		} catch (IllegalArgumentException e){
			exception = e;
		} catch (IndexOutOfBoundsException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IndexOutOfBoundsException);
		
		//PROVIDING A NULL DATA ELEMENT
		try {
			mTestList.insert(0, null);
		} catch (IllegalArgumentException e){
			exception = e;
		} catch (IndexOutOfBoundsException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalArgumentException);
	}
	
	// ----------------------------------------------------------
	/**
	 * Method to test for {@link List#insertFront(Comparable)}
	 */
	@Test
	public void testInserFront(){
		
		Exception exception = null;
		
		for(int i = 0; i < 100; i++){
			try {
				mTestList.insertFront(i);
				assertNull(exception);
			} catch (IllegalArgumentException e){
				exception = e;
			}
		}
		
		for(int i = 0; i < 100; i++)
			assertEquals(mTestList.get(i), (Integer) (99 - i));
		
		//PROVIDING null argument
		try {
			mTestList.insertFront(null);
		} catch (IllegalArgumentException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalArgumentException);
	}
	
	// ----------------------------------------------------------
	/**
	 * Test method for {@link List#insertLast(Comparable)}
	 */
	@Test
	public void testInsertLast(){
		
		Exception exception = null;
		
		for(int i = 0; i < 100; i++){
			try {
				mTestList.insertLast(i);
				assertNull(exception);
			} catch (IllegalArgumentException e){
				exception = e;
			}
		}
		
		for(int i = 0; i < 100; i++)
			assertEquals(mTestList.get(i), (Integer) i);
		
		//PROVIDING null argument
		try {
			mTestList.insertLast(null);
		} catch (IllegalArgumentException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalArgumentException);
	}
	
	// ----------------------------------------------------------
	/**
	 * Method to test for {@link List#delete(int)}
	 */
	@Test
	public void testDelete(){
		
		Exception exception = null;
		
		try {
			mTestList.delete(0);
		} catch (IllegalStateException e){
			exception = e;
		} catch (IndexOutOfBoundsException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		
		for(int i = 0; i < 100; i++)
			mTestList.insertLast(i);
		
		try {
			mTestList.delete(-1);
		} catch (IllegalStateException e){
			exception = e;
		} catch (IndexOutOfBoundsException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IndexOutOfBoundsException);
		
		exception = null;
		
		try {
			mTestList.delete(10000);
		} catch (IllegalStateException e){
			exception = e;
		} catch (IndexOutOfBoundsException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IndexOutOfBoundsException);
		
		for(int i = 0; i < 99; i++){
			assertEquals(mTestList.delete(0), (Integer) i);
			assertEquals(mTestList.size(), 99 - i);
			assertEquals(mTestList.getFirst(), (Integer) (i + 1));
		}
		
		mTestList.clear();
		
		for(int i = 0; i < 100; i++)
			mTestList.insertLast(i);
		
		assertEquals(mTestList.delete(10), (Integer) 10);
		assertEquals(mTestList.size(), 99);
		assertEquals(mTestList.delete(20), (Integer) 21);
		assertEquals(mTestList.size(), 98);
		assertEquals(mTestList.delete(30), (Integer) 32);
		assertEquals(mTestList.size(), 97);
		
		mTestList.clear();
		
		mTestList.insert(0, (Integer) 20);
		mTestList.insert(1, (Integer) 30);
		mTestList.insert(2, (Integer) 40);
		mTestList.insert(3, (Integer) 50);
		
		assertEquals(mTestList.delete(3), (Integer) 50);
		assertEquals(mTestList.size(), 3);
		
	}
	
	// ----------------------------------------------------------
	/**
	 * Test method for {@link List#delete(Comparable)}
	 */
	@Test
	public void testDelete_givenData(){
		
		Exception exception = null;
		
		try {
			Integer integer = 1;
			mTestList.delete(integer);
		} catch (IllegalArgumentException e){
			exception = e;
		} catch (IllegalStateException e) {
			exception = e;
		} catch (NoSuchElementException e) {
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		
		try {
			Integer integer = null;
			mTestList.delete(integer);
		} catch (IllegalArgumentException e){
			exception = e;
		} catch (IllegalStateException e) {
			exception = e;
		} catch (NoSuchElementException e) {
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalArgumentException);
		
		
		for(int i = 0; i < 100; i++)
			mTestList.insert(i, i % 10);
		
		try {
			Integer integer = -9;
			mTestList.delete(integer);
		} catch (IllegalArgumentException e){
			exception = e;
		} catch (IllegalStateException e) {
			exception = e;
		} catch (NoSuchElementException e) {
			exception = e;
		}
		
		assertTrue(exception instanceof NoSuchElementException);
		
		Integer integer = 5;
		
		assertEquals(mTestList.delete(integer), integer);
		assertEquals(mTestList.size(), 99);
		assertEquals(mTestList.get(5), (Integer) 6);
		
		assertEquals(mTestList.delete(integer), integer);
		assertEquals(mTestList.size(), 98);
		
	}
	
	// ----------------------------------------------------------
	/**
	 * Method to test {@link List#deleteAll(Comparable)}
	 */
	@Test
	public void testDeleteAll(){
		
		Exception exception = null;
		
		try {
			Integer integer = 1;
			mTestList.deleteAll(integer);
		} catch (IllegalArgumentException e){
			exception = e;
		} catch (IllegalStateException e) {
			exception = e;
		} catch (NoSuchElementException e) {
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		
		try {
			Integer integer = null;
			mTestList.deleteAll(integer);
		} catch (IllegalArgumentException e){
			exception = e;
		} catch (IllegalStateException e) {
			exception = e;
		} catch (NoSuchElementException e) {
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalArgumentException);
		
		
		for(int i = 0; i < 100; i++)
			mTestList.insert(i, i % 10);
		
		try {
			Integer integer = -9;
			mTestList.deleteAll(integer);
		} catch (IllegalArgumentException e){
			exception = e;
		} catch (IllegalStateException e) {
			exception = e;
		} catch (NoSuchElementException e) {
			exception = e;
		}
		
		assertTrue(exception instanceof NoSuchElementException);
		
		assertEquals(mTestList.deleteAll(5), (Integer) 5);
		
		exception = null;
		
		try {
			Integer integer = 5;
			mTestList.deleteAll(integer);
		} catch (IllegalArgumentException e){
			exception = e;
		} catch (IllegalStateException e) {
			exception = e;
		} catch (NoSuchElementException e) {
			exception = e;
		}
		
		assertTrue(exception instanceof NoSuchElementException);
		
		mTestList.clear();
		
		for(int i = 0; i < 100; i++)
			mTestList.insertLast((Integer) i % 10);
		
		assertEquals(mTestList.deleteAll((Integer) 1), (Integer) 1);
		assertEquals(mTestList.size(), 90);
		
		assertEquals(mTestList.deleteAll((Integer) 0), (Integer) 0);
		assertEquals(mTestList.size(), 80);
	}
	
	// ----------------------------------------------------------
	/**
	 * Method to test for {@link List#deleteFirst()}
	 */
	@Test
	public void testDeleteFirst(){
		
		Exception exception = null;
		
		try {
			mTestList.deleteFirst();
		} catch (IllegalStateException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		
		for(int i = 0; i < 100; i++)
			mTestList.insertLast((Integer) i);
		
		assertEquals(mTestList.deleteFirst(), (Integer) 0);
		assertEquals(mTestList.size(), 99);
		
		assertEquals(mTestList.deleteFirst(), (Integer) 1);
		assertEquals(mTestList.size(), 98);
		
		assertEquals(mTestList.deleteFirst(), (Integer) 2);
		assertEquals(mTestList.size(), 97);
		
		assertEquals(mTestList.deleteFirst(), (Integer) 3);
		assertEquals(mTestList.size(), 96);
		
		assertEquals(mTestList.deleteFirst(), (Integer) 4);
		assertEquals(mTestList.size(), 95);
	}
	
	// ----------------------------------------------------------
	/**
	 * Method to test for {@link List#deleteLast()}
	 */
	@Test
	public void testDeleteLast(){
		
		Exception exception = null;
		
		try {
			mTestList.deleteLast();
		} catch (IllegalStateException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		
		for(int i = 0; i < 100; i++)
			mTestList.insertLast((Integer) i);
		
		assertEquals(mTestList.deleteLast(), (Integer) 99);
		assertEquals(mTestList.getLast(), (Integer) 98);
		assertEquals(mTestList.size(), 99);
		
		assertEquals(mTestList.deleteLast(), (Integer) 98);
		assertEquals(mTestList.getLast(), (Integer) 97);
		assertEquals(mTestList.size(), 98);
		
		assertEquals(mTestList.deleteLast(), (Integer) 97);
		assertEquals(mTestList.getLast(), (Integer) 96);
		assertEquals(mTestList.size(), 97);
		
		assertEquals(mTestList.deleteLast(), (Integer) 96);
		assertEquals(mTestList.getLast(), (Integer) 95);
		assertEquals(mTestList.size(), 96);
		
		assertEquals(mTestList.deleteLast(), (Integer) 95);
		assertEquals(mTestList.getLast(), (Integer) 94);
		assertEquals(mTestList.size(), 95);
	}
	
	/**
	 * Test method for {@link List#deleteDuplicates()}
	 */
	@Test
	public void testDeleteDuplicates(){
		
		Exception exception = null;
		
		try {
			mTestList.deleteDuplicates();
		} catch (IllegalStateException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		exception = null;
		
		for(int i = 0; i < 100; i++)
			mTestList.insertLast(i % 10);
		
		List<Integer> list = null;
		
		try {
			list = mTestList.deleteDuplicates();
		} catch (IllegalStateException e){
			exception = e;
		}
		
		assertFalse(list == null);
		
		assertEquals(list.size(), 10);
		assertEquals(mTestList.size(), 0);
		
		for(int i = 0; i < 10; i++)
			assertEquals(list.get(i), (Integer) i);
		
		mTestList.insertLast((Integer) 100);
		mTestList.insertLast((Integer) 50);
		mTestList.insertLast((Integer) 45);
		mTestList.insertLast((Integer) 10);
		
		list = null;
		
		try {
			list = mTestList.deleteDuplicates();
		} catch (IllegalStateException e){
			exception = e;
		}
		
		assertEquals(list.size(), 0);
		assertEquals(mTestList.size(), 4);
	}
	
	// ----------------------------------------------------------
	/**
	 * Test method for {@link List#get(int)}
	 */
	@Test
	public void testGet(){
		
		Exception exception = null;
		
		try {
			mTestList.get(0);
		} catch (IllegalStateException e){
			exception = e;
		} catch (IndexOutOfBoundsException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		
		for(int i = 0; i < 100; i++)
			mTestList.insertLast((Integer) i);
		
		for(int i = 0; i < 100; i++)
			assertEquals(mTestList.get(i), (Integer) i);
		
		
		try {
			mTestList.get(-10);
		} catch (IllegalStateException e){
			exception = e;
		} catch (IndexOutOfBoundsException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IndexOutOfBoundsException);
		
		exception = null;
		
		try {
			mTestList.get(1000);
		} catch (IllegalStateException e){
			exception = e;
		} catch (IndexOutOfBoundsException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IndexOutOfBoundsException);
	}
	
	// ----------------------------------------------------------
	/**
	 * Test method for {@link List#getFirst()}
	 */
	@Test
	public void testGetFirst(){
		
		Exception exception = null;
		
		try {
			mTestList.getFirst();
		} catch (IllegalStateException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		
		mTestList.insert(0, (Integer) 100);
		assertEquals(mTestList.getFirst(), (Integer) 100);
		
		mTestList.insertLast(20);
		assertEquals(mTestList.getFirst(), (Integer) 100);
		
		mTestList.deleteFirst();
		assertEquals(mTestList.getFirst(), (Integer) 20);
		
		
	}
	
	// ----------------------------------------------------------
	/**
	 * Test method for {@link List#getLast()}
	 */
	@Test
	public void testGetLast(){
		
		Exception exception = null;
		
		try {
			mTestList.getLast();
		} catch (IllegalStateException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		
		mTestList.insert(0, (Integer) 100);
		assertEquals(mTestList.getLast(), (Integer) 100);
		
		mTestList.insertLast(20);
		assertEquals(mTestList.getLast(), (Integer) 20);
		
		mTestList.deleteLast();
		assertEquals(mTestList.getLast(), (Integer) 100);
	}
	
	// ----------------------------------------------------------
	/**
	 * Test method for {@link List#getMax()}
	 */
	@Test
	public void testGetMax(){
		
		Exception exception = null;
		
		try {
			mTestList.getMax();
		} catch (IllegalStateException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		
		mTestList.insertFront((Integer) 100);
		assertEquals(mTestList.getMax(), (Integer) 100);
		
		mTestList.insertFront((Integer) 50);
		assertEquals(mTestList.getMax(), (Integer) 100);
		
		mTestList.insertFront((Integer) 10);
		assertEquals(mTestList.getMax(), (Integer) 100);
		
		mTestList.insertFront((Integer) 200);
		assertEquals(mTestList.getMax(), (Integer) 200);
		
		mTestList.insertFront((Integer) 220);
		assertEquals(mTestList.getMax(), (Integer) 220);
	}
	
	// ----------------------------------------------------------
	/**
	 * Test method for {@link List#getMin()}
	 */
	@Test
	public void testGetMin(){
		
		Exception exception = null;
		try {
			mTestList.getMin();
		} catch (IllegalStateException e){
			exception = e;
		}
		
		assertTrue(exception instanceof IllegalStateException);
		
		mTestList.insertFront((Integer) 100);
		assertEquals(mTestList.getMin(), (Integer) 100);
		
		mTestList.insertFront((Integer) 50);
		assertEquals(mTestList.getMin(), (Integer) 50);
		
		mTestList.insertFront((Integer) 10);
		assertEquals(mTestList.getMin(), (Integer) 10);
		
		mTestList.insertFront((Integer) 200);
		assertEquals(mTestList.getMin(), (Integer) 10);
		
		mTestList.insertFront((Integer) 220);
		assertEquals(mTestList.getMin(), (Integer) 10);
	}
	
	// ----------------------------------------------------------
	/**
	 * Test method for {@link List#sort()}
	 */
	@Test
	public void testSort(){
		mTestList.insert(0, 10);
		mTestList.insert(1, 5);
		mTestList.insert(2, 15);
		mTestList.insert(3, 0);
		mTestList.insert(4, 30);
		mTestList.insert(5, 20);
		mTestList.insert(6, 25);
		mTestList.sort();
		
		for(int i = 0; i < mTestList.size(); i++)
			assertEquals((Integer) (i * 5), mTestList.get(i));
		
		mTestList.clear();
		
		for(int i = 0; i <= 10; i++){
			mTestList.insertFront(i);
			mTestList.sort();
		}
		
		for(int i = 0; i <= 10; i++)
			assertEquals((Integer) i, mTestList.get(i));
		
		mTestList.clear();
		
		for(int i = 0; i <= 10; i++){
			mTestList.insertLast(i);
			mTestList.sort();
		}
		
		for(int i = 0; i <= 10; i++)
			assertEquals((Integer) i, mTestList.get(i));
	}
	
	// ----------------------------------------------------------
	/**
	 * Test method for {@link List#reverse()}
	 */
	@Test
	public void testReverse(){
		
		for(int i = 0; i < 10000; i++)
			mTestList.insertLast(i);
		
		mTestList.reverse();
		
		for(int i = 9999; i >= 0; i--){
			assertEquals(mTestList.get(9999 - i), (Integer) i);
		}
	}
	
	// ----------------------------------------------------------
	/**
	 * Method to test {@link List#clear()}
	 */
	@Test
	public void testClear(){
		
		assertEquals(mTestList.size(), 0);
		
		for(int i = 0; i < 10000; i++)
			mTestList.insertFront(i);
		
		mTestList.clear();
		assertEquals(mTestList.size(), 0);
	}
}
