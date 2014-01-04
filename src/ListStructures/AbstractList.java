package ListStructures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractList<T extends Comparable<? super T>> implements List<T> {

	// ----------------------------------------------------------
    /**
     * This method return the size of the list.
     * @return size of the list.
     */
    public abstract int size();
    
    // ----------------------------------------------------------
    /**
	 * Method to insert a desired data element given
	 * an index.
	 * 
	 * Throws an IndexOutOfBoundsException if the
	 * provided index is out of range.
	 * 
	 * Throws an IllegalArgumentExcpetion if a null
	 * data element is being added to the list.
	 * 
	 * @param index : location the data element will be inserted
	 * @param data : data element to be inserted.
	 * @throws IndexOutOfBoundsException
	 * @throws IllegalArgumentException
	 */
    public abstract void insert(int index, T data) throws IndexOutOfBoundsException, IllegalArgumentException;
    
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
    public abstract void insertFront(T data) throws IllegalArgumentException;
    
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
    public abstract void insertLast(T data) throws IllegalArgumentException;
    
    // ----------------------------------------------------------
    /**
	 * Method to remove the element provided an index.
	 * 
	 * Throws an IndexOutOfBoundsException if the
	 * provided index is out of range.
	 * 
	 * @param index : location of the data element to be removed.
	 * @return data element that was removed from the list.
	 * @throws IndexOutOfBoundsException
	 */
    public abstract T delete(int index) throws IndexOutOfBoundsException;
    
    // ----------------------------------------------------------
    /**
     * Method to delete the provided data element from
     * the list.
     * 
     * Throws an IllegalStateException if the list is
     * empty and deletion is called.
     * 
     * Throws a NoSuchElementException if the data element
     * desired to be deleted is not present in the list.
     * 
     * @param data : data element to be deleted.
     * @return : data element that got deleted.
     * @throws IllegalStateException
     * @throws NoSuchElementException
     */
    public abstract T delete(T data) throws IllegalStateException, NoSuchElementException;
    
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
    public abstract T deleteFirst() throws IllegalStateException;
    
    // ----------------------------------------------------------  
    /**
     * Method to delete the last data element in the list.
     * 
     * Throws an IllegalStateException if the list is empty.
     * 
     * @return : data element that got removed.
     * @throws IllegalStateException
     */
    public abstract T deleteLast() throws IllegalStateException;
    
    // ----------------------------------------------------------
    /**
	 * Method to retrieve the element at the desired
	 * index.
	 * 
	 * Throws an IndexOutOfBounds exception if the 
	 * provided index is out of range.
	 * 
	 * @param index : location from where the data element
	 * 				  retrieved.
	 * @return the data element provided the index.
	 * @throws IndexOutOfBoundsException
	 */
    public abstract T get(int index) throws IndexOutOfBoundsException;
    
    // ----------------------------------------------------------
    /**
     * Method to retrieve the first element in the list.
     * 
     * Throws an IllegalStateException if the list
     * is empty.
     * 
     * @return last data element in the list.
     * @throws IllegalStateException
     */
    public abstract T getFirst() throws IllegalStateException;
    
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
    public abstract T getLast() throws IllegalStateException;
    
    // ----------------------------------------------------------
    /**
     * This method sorts the list in ascending order. 
     */
    public abstract void sort();
    
    // ----------------------------------------------------------
    /**
     * This method reverses the lists.
     */
    public abstract void reverse();
    
    // ----------------------------------------------------------
    /**
     * Method to remove all the elements in the list.
     */
    public abstract void clear();
    
    // ----------------------------------------------------------
    /**
     * Method returns a new Iterator.
     * @return iterator to go through the list.
     */
    public abstract Iterator<T> iterator();
    
    // ----------------------------------------------------------
    /**
     * Method to compare the two list objects.
     * @param object - other list to be compared.
     * @return true if the two lists are equal
     * otherwise false.
     */
    @SuppressWarnings("rawtypes")
	public boolean equals(Object object){
    	
    	//Checking if the passed object is a List
    	if(object instanceof List){
    		//Matching size.
    		if(size() != ((List) object).size())
    			return false;
    		
    		Iterator<T> current1 = iterator();
    		@SuppressWarnings("unchecked")
			Iterator<T> current2 = ((List) object).iterator();
    		
    		//Since the list does not allow insertion of null
    		//data elements it is ok to assume that a correct
    		//implementation of the .next() method would not
    		//return a null data element
    		while(current1.hasNext() && current2.hasNext()){
    			if(!current1.next().equals(current2.next()))
    				return false;
    		}
    		
    		return true;
    	}
    	
    	return false;
    }
    
    // ----------------------------------------------------------
    /**
     * This method returns a copy of this list.
     * @return copy of this list.
     */
    public abstract List<T> clone();
    
    // ----------------------------------------------------------
    /**
     * This method returns the hash code of the list.
     * @return hash code of the list.
     */
    public int hashCode(){
    	int hashCode = 0;
    	Iterator<T> iterator = iterator();
    	
    	//Since the list does not allow insertion of null
		//data elements it is ok to assume that a correct
		//implementation of the .next() method would not
		//return a null data element
    	while(iterator.hasNext())
    		hashCode += iterator.next().hashCode();
    	
    	return hashCode;
    }
    
    // ----------------------------------------------------------
    /**
     * Method to retrieve the string representation of the
     * list.
     * 
     * @return string representation of the list.
     */
    public String toString(){
    	StringBuilder build = new StringBuilder();
    	Iterator<T> iterator = iterator();
    	build.append("[");
    	
    	while(iterator.hasNext())
    		build.append(iterator.next() + ", ");
    	
    	build.deleteCharAt(build.length() - 1);
		build.deleteCharAt(build.length() - 1);
    	build.append("]");
    	return build.toString();
    }
	
}
