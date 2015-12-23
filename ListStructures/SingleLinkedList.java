package ListStructures;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of Singly linked list.
 * @author Arjun Passi
 *
 */
public class SingleLinkedList <T extends Comparable<? super T>> extends AbstractList<T>{

	/**
	 * This class represents a node in a singly linked list.
	 * Contains pointers to the next node only.
	 * @author Arjun Passi
	 *
	 */
	private class Node{
		
		/** Reference to the data element of the node*/
		private T mData;
		
		/** Reference to the next node*/
		private Node mNext;
		
		/**
		 * Constructs a node provided the data element
		 * and the reference to the next node.
		 * @param data
		 * @param next
		 */
		public Node(T data, Node next){
			mData = data;
			mNext = next;
		}
		
		/**
		 * Method to retrieve the string representation
		 * of this node
		 * 
		 * @return string representation of this node.
		 */
		@Override
		public String toString(){
			StringBuilder build = new StringBuilder();
			String data = (mData == null) ? "" : mData.toString();
			build.append("Data: " + data);
			build.append("\nNext: ");
			if(mNext != null) build.append(mNext.mData.toString());
			build.append("\n");
			
			return build.toString();
		}
	}
	
	/**
	 * SingleLinkedListIterator over the Linked List.
	 * 
	 * @author Arjun Passi
	 *
	 */
	@SuppressWarnings("hiding")
	private class SingleLinkedListIterator<T> implements Iterator<T>{

		/** Reference to the current node */
		private Node current;
		
		/** Reference to the pointer to node before current*/
		private Node prev;
		
		/**
		 * Constructs a new SingleLinkedListIterator.
		 */
		public SingleLinkedListIterator(){
			current = mHead;
			prev = null;
		}
		
		@Override
		public boolean hasNext() {
			return current == null;
		}

		@Override
		public T next() {
			if(!hasNext())
				throw new NoSuchElementException("There is no next!");
			
			@SuppressWarnings("unchecked")
			T data = (T) current.mData;
			prev = current;
			current = current.mNext;
			return data;
		}

		@Override
		public void remove() {
			if(current == mHead){
				mHead = mHead.mNext;
				if(mSize == 1) mTail = null;
			}
			
			else if (current == mTail){
				prev.mNext = null;
				mTail = prev;
			}
			
			else {
				prev.mNext = current.mNext;
			}
		}
		
	}
	
	/** Reference to the head of the linked list*/
	private Node mHead;
	
	/** Reference to the last node of the linked list.
	 *  Useful if a data element is being inserted at the end of the list. */
	private Node mTail;
	
	/** Reference to the size of the linked list*/
	private int mSize;
	
	/**
	 * Constructs an empty linked list.
	 */
	public SingleLinkedList(){
		mHead = null;
		mTail = null;
		mSize = 0;
	}
	
	/**
	 * Method to insert a desired data element given
	 * an index.
	 * 
	 * Throws an IndexOutOfBoundsException if the
	 * provided index is out of range.
	 * 
	 * Throws an IllegalArgumentExcpetion if the null
	 * data element is being added to the list.
	 * 
	 * @param index : location the data element will be inserted
	 * @param data : data element to be inserted.
	 */
	@Override
	public void insert(int index, T data) throws IndexOutOfBoundsException, IllegalArgumentException{
		if(index < 0 || index > mSize)
			throw new IndexOutOfBoundsException();
		if(data == null)
			throw new IllegalArgumentException("Null data elements not allowed!");
		
		Node newNode = new Node(data, null);
		
		if(mHead == null && index == 0){
			mHead = newNode;
			mTail = mHead;
		}
		
		else if(mHead != null && index == 0){
			newNode.mNext = mHead;
			mHead = newNode;
		}
		
		else if(index == mSize){
			mTail.mNext = newNode;
			mTail = mTail.mNext;
		} 
		
		else {
			int position = 0;
			Node current = mHead;
			
			while(position != index - 1){
				current = current.mNext;
				position++;
			}
			
			Node newNext = current.mNext;
			current.mNext = newNode;
			newNode.mNext = newNext;
		}
		
		mSize++;
	}
	
	// ----------------------------------------------------------
    /**
     * Method to insert a desired data element in
     * front of the list.
     * 
     * Throws an IllegalArgumentException if a null
     * data element is being added to the list.
     * 
     * @param data : data element to be inserted.
     * @throws IllegalArgumentException
     */
	@Override
	public void insertFront(T data) throws IllegalArgumentException {
		
		insert(0, data);
	}

	// ----------------------------------------------------------
    /**
     * Method to insert a desired data element at
     * the end of the list.
     * 
     * Throws an IllegalArgumentException if a null
     * data element is being added to the list.
     * 
     * @param data
     * @throws IllegalArgumentException
     */
	@Override
	public void insertLast(T data) throws IllegalArgumentException {
		
		insert(mSize, data);
	}
	
	// ----------------------------------------------------------
    /**
	 * Method to retrieve the element at the desired
	 * index.
	 * 
	 * Throws an IndexOutOfBounds exception if the 
	 * provided index is out of range.
	 * 
	 * Throws an IllegalStateException if the list
	 * is empty.
	 * 
	 * @param index : location from where the data element
	 * 				  retrieved.
	 * @return the data element provided the index.
	 * @throws IllegalStateException
	 * @throws IndexOutOfBoundsException
	 */
    public T get(int index) throws IllegalStateException, 
    		IndexOutOfBoundsException {
    	
    	if(isEmpty())
    		throw new IllegalStateException("List is empty!");
    		
		if(index < 0 || index >= mSize)
			throw new IndexOutOfBoundsException();
		
		int position = 0;
		Node current = mHead;
		
		while(position != index){
			current = current.mNext;
			position++;
		}
		
		return current.mData;
	}
	
	// ----------------------------------------------------------
    /**
     * Method to retrieve the first element in the list.
     * 
     * Throws an IllegalStateException if the list
     * is empty.
     * 
     * @return first data element in the list.
     * @throws IllegalStateException
     */
	@Override
	public T getFirst() throws IllegalStateException {
		
		if(isEmpty())
			throw new IllegalStateException("List is empty!");
		
		return mHead.mData;
	}
	
	// ----------------------------------------------------------
    /**
     * Method to retrieve the last element in the list.
     * 
     * Throws an IllegalStateException if the list
     * is empty.
     * 
     * @return last data element in the list.
     * @throws IllegalStateException
     */
	@Override
	public T getLast() throws IllegalStateException {
		
		if(isEmpty())
			throw new IllegalStateException("List is empty!");
		
		return mTail.mData;
	}
	
	// ----------------------------------------------------------
    /**
     * Method to retrieve the max element present in the list.
     * 
     * Throws an IllegalStateException if the list is
     * empty.
     * 
     * @return : reference to the maximum data element
     * @throws IllegalStateException
     */
	@Override
	public T getMax() throws IllegalStateException {
		if(isEmpty())
			throw new IllegalStateException("List is empty!");
		
		T max = mHead.mData;
		Node current = mHead.mNext;
		
		while(current != null){
			if(current.mData.compareTo(max) > 0)
				max = current.mData;
			current = current.mNext;
		}
		
		return max;
	}

	// ----------------------------------------------------------
    /**
     * Method to retrieve the minimum element present in the list.
     * 
     * Throws and IllegalStateException if the list is empty.
     * 
     * @return : reference to the minimum data element.
     * @throws IllegalStateException
     */
	@Override
	public T getMin() throws IllegalStateException {
		if(isEmpty())
			throw new IllegalStateException("List is empty!");
		
		T min = mHead.mData;
		Node current = mHead.mNext;
		
		while(current != null){
			if(current.mData.compareTo(min) < 0)
				min = current.mData;
			current = current.mNext;
		}
		
		return min;
	}
	
	// ----------------------------------------------------------
    /**
	 * Method to remove the element provided an index.
	 * 
	 * Throws an IndexOutOfBoundsException if the
	 * provided index is out of range.
	 * 
	 * @param index : location of the data element to be removed.
	 * @return data element that was removed from the list.
	 * @throws IllegalStateException
	 * @throws IndexOutOfBoundsException
	 */
	@Override
	public T delete(int index) throws IllegalStateException, 
			IndexOutOfBoundsException{
		
		if(isEmpty())
			throw new IllegalStateException("List is already empty!");
		if(index < 0 || index >= mSize)
			throw new IndexOutOfBoundsException();
		
		T dataRemoved = null;
		
		Node newHead = new Node(null, mHead);
		Node current = newHead;
		
		for(int i = 0; i < index; i++)
			current = current.mNext;
		
		dataRemoved = current.mNext.mData;
		current.mNext = current.mNext.mNext;
		mHead = newHead.mNext;
		
		if(mSize == 1) mTail = null;
		else if(index == mSize -1) mTail = current;
		
		mSize--;
		return dataRemoved;
	}
	
	// ----------------------------------------------------------
    /**
     * Method to delete the first occurrence of the provided data
     * element from the list.
     * 
     * Throws an IllegalArgumentException if a null data element
     * is being tried to be deleted from the list.
     * 
     * Throws an IllegalStateException if the list is
     * empty and deletion is called.
     * 
     * Throws a NoSuchElementException if the data element
     * desired to be deleted is not present in the list.
     * 
     * @param data : data element to be deleted.
     * @return : data element that got deleted.
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     * @throws NoSuchElementException
     */
	@Override
	public T delete(T data) throws IllegalArgumentException,
			IllegalStateException, NoSuchElementException {
		
		if(data == null)
			throw new IllegalArgumentException("Null argument passed!");
		if(isEmpty())
			throw new IllegalStateException("List is already empty");
		
		Node newHead = new Node(null, mHead);
		Node current = newHead;
		T dataRemoved = null;
		
		while(current.mNext != null){
			if(current.mNext.mData.equals(data)){
				dataRemoved = current.mNext.mData;
				current.mNext = current.mNext.mNext;
				mSize--;
				break;
			}
			current = current.mNext;
		}
		
		//Element not found
		if(dataRemoved == null)
			throw new NoSuchElementException();
		
		mHead = newHead.mNext;
		if(mSize == 0) mTail = null;
		
		return dataRemoved;
	}
	
	// ----------------------------------------------------------
    /**
     * Method to delete all the occurrences of the provided data
     * element from the list.
     * 
     * Throws an IllegalArgumentException if a null data element
     * is being deleted from the list.
     * 
     * Throws an IllegalStateException if the list is empty and
     * deletion is called.
     * 
     * Throws a NoSuchElementException of the data element desired
     * to be deleted is not in the list.
     *  
     * @param data : data element to be removed from the list.
     * @return : data element that was removed from the list.
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     * @throws NoSuchElementException
     */
    public T deleteAll(T data) throws IllegalArgumentException, 
    		IllegalStateException, NoSuchElementException{
    	
    	if(data == null)
    		throw new IllegalArgumentException("Null argument passed!");
    	if(isEmpty())
    		throw new IllegalStateException("List is already empty!");
    	
    	Node newHead = new Node(null, mHead);
    	Node current = newHead;
    	T dataRemoved = null;
    	
    	while(current.mNext != null){
    		if(current.mNext.mData.equals(data)){
    			dataRemoved = current.mNext.mData;
				current.mNext = current.mNext.mNext;
				mSize--;
    		}
    		
    		else
    			current = current.mNext;
    	}
    	
    	//Element not found
    	if(dataRemoved == null)
    		throw new NoSuchElementException();

    	mHead = newHead.mNext;
    	if(mSize == 0) mTail = null;
    	
    	return dataRemoved;
    }
    
	// ----------------------------------------------------------
    /**
     * Method to delete the first data element in the list.
     * 
     * Throws an IllegalStateException if the list is
     * empty.
     * 
     * @return : data element that got deleted.
     * @throws IllegalStateException
     */
	@Override
	public T deleteFirst() throws IllegalStateException {
		return delete(0);
	}
	
	// ----------------------------------------------------------  
    /**
     * Method to delete the last data element in the list.
     * 
     * Throws an IllegalStateException if the list is empty.
     * 
     * @return : data element that got removed.
     * @throws IllegalStateException
     */
	@Override
	public T deleteLast() throws IllegalStateException {
		return delete(mSize - 1);
	}
	
	// ---------------------------------------------------------- 
    /**
     * Method to delete all the duplicate elements in the list.
     * 
     * Throws an IllegalStateException if the list is empty.
     * 
     * @return : List of elements that were duplicated and got removed.
     * @throws IllegalStateException
     */
    public List<T> deleteDuplicates() throws IllegalStateException {
    	if(isEmpty())
    		throw new IllegalStateException("List is already empty");
    	
    	SingleLinkedList<T> list = new SingleLinkedList<T>();
    	HashMap<T, Integer> ocurrence = new HashMap<T, Integer>();
    	HashMap<T, Boolean> inList = new HashMap<T, Boolean>();
    	
    	Node newHead = new Node(null, mHead);
    	Node current = mHead;
    	
    	while(current != null){
    		if(!ocurrence.containsKey(current.mData))
    			ocurrence.put(current.mData, (Integer) 1);
    		else
    			ocurrence.put(current.mData, 
    					ocurrence.get(current.mData) + 1);
    		
    		current = current.mNext;
    	}
    	
    	current = newHead;
    	
    	while(current.mNext != null){
    		if(ocurrence.get(current.mNext.mData) > 1){
    			if(!inList.containsKey(current.mNext.mData)){
    				list.insertLast(current.mNext.mData);
    				inList.put(current.mNext.mData, true);
    			}
    			current.mNext = current.mNext.mNext;
    			mSize--;
    		} 
    		
    		else
    			current = current.mNext;
    	}
    	
    	mHead = newHead.mNext;
    	if(mSize == 0) mTail = null;
    	
    	return list;
    }
	
	/**
	 * Returns true if the linked list is empty
	 * other wise false.
	 * @return
	 */
	public boolean isEmpty(){
		return mSize == 0;
	}
	
	/**
	 * Method to retrieve the size of the list.
	 * @return size of the list.
	 */
	@Override
	public int size(){
		return mSize;
	}
	
	/**
	 * Method removes all the elements in the
	 * linked list.
	 */
	@Override
	public void clear(){
		mHead = null;
		mTail = null;
		mSize = 0;
	}
	
	// ----------------------------------------------------------
    /**
     * This method sorts the list in ascending order. 
     */
	@Override
	public void sort(){
		mHead = sort(mHead);
		
		//Fixing the tail pointer.
		while(mTail.mNext != null)
			mTail = mTail.mNext;
	}
	
	/**
	 * Helper method to sort the linked list.
	 * @param head
	 */
	private Node sort(Node head){
		if(head == null) return null;
		if(head.mNext == null) return head;
		
		Node firstHalf = head;
		Node mid = getMid(head);
		
		Node secondHalf = mid.mNext;
		//Breaking the list
		mid.mNext = null;
		
		Node head1 = sort(firstHalf);
		Node head2 = sort(secondHalf);
		return merge(head1, head2);
			
	}
	
	/**
	 * Helper method to merge two sorted linked lists
	 * provided the head pointers to the two lists.
	 * 
	 * @param nodeA : head of the first linked list.
	 * @param nodeB : head of the second linked list.
	 * @return head of the merged linked list.
	 */
	private Node merge(Node nodeA, Node nodeB){
		if(nodeA == null) return nodeB;
		if(nodeB == null) return nodeA;
		
		Node head = new Node(null, null);
		Node current = head;
		
		while(nodeA != null && nodeB != null){
			if(nodeA.mData.compareTo(nodeB.mData) < 0){
				current.mNext = nodeA;
				nodeA = nodeA.mNext;
			} else {
				current.mNext = nodeB;
				nodeB = nodeB.mNext;
			}
			current = current.mNext;
		}
		
		current.mNext = (nodeA == null) ? nodeB : nodeA;
		head = head.mNext;
		
		return head;
	}
	
	/**
	 * Helper method to find the mid point provided
	 * the head node.
	 * 
	 * @param head : reference to the head of the list
	 * @return reference to the mid point node.
	 */
	private Node getMid(Node head){
		if(head == null || head.mNext == null)
			return head;
		Node slow = head;
		Node fast = head;
		
		while(fast.mNext != null && fast.mNext.mNext != null){
			slow = slow.mNext;
			fast = fast.mNext.mNext;
		}
		
		return slow;
	}

	// ----------------------------------------------------------
    /**
     * This method reverses the lists.
     */
	@Override
	public void reverse() {
		if(mSize == 0 || mSize == 1)
			return;
		
		Node first = mHead;
		Node second = mHead.mNext;
		
		//Third will never be null
		Node third = second.mNext;
		first.mNext = null;
		
		while(second != null){
			second.mNext = first;
			first = second;
			second = third;
			if(third != null) third = third.mNext;
		}
		
		Node newHead = mTail;
		Node newTail = mHead;
		
		mHead = newHead;
		mTail = newTail;
	}

	// ----------------------------------------------------------
    /**
     * Method returns a new Iterator.
     * @return iterator to go through the list.
     */
	@Override
	public Iterator<T> iterator() {
		return new SingleLinkedListIterator<T>();
	}

	// ----------------------------------------------------------
    /**
     * This method returns a copy of this list.
     * @return copy of this list.
     */
	@Override
	public List<T> clone() {
		SingleLinkedList<T> copy = new SingleLinkedList<T>();
		
		SingleLinkedListIterator<T> iterator =
				new SingleLinkedListIterator<T>();
		
		while(iterator.hasNext())
			copy.insertLast(iterator.next());
		
		return copy;
	}
}
