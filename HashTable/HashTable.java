package HashTable;

import java.util.NoSuchElementException;

/**
 * This class implements a Hash Table data structure.
 * It maps identifying keys to their associated values.
 * This class implements the Hash Table using quadratic probing.
 * 
 * @author Arjun Passi
 *
 * @param <K>
 * @param <V>
 */
public class HashTable<K, V> {
	
	// Nested Classes -------------------------------------
	/**
	 * Entry is a nested class that implements the HashTable
	 * data storing key and value pairs.
	 * 
	 * @author Arjun Passi
	 *
	 */
	private class Entry {
		
		/** Reference to the key that maps the value*/
		private K key;
		
		/** Reference to the value stored in the hash table*/
		private V value;
		
		/** Reference to flag to keep track of the entry in the table*/
		private boolean isActive;
		
		/**
		 * Constructs a new instance of Entry.
		 */
		public Entry() {
			key = null;
			value = null;
			isActive = false;
		}
		
		/**
		 * Constructs a new instance of Entry
		 * 
		 * @param key : key that maps the value
		 * @param value : value stored in the hash table
		 */
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
			isActive = false;
		}
		
		/**
		 * Method that returns string representation of
		 * the Entry object.
		 */
		public String toString() {
			return "Key: " + key.toString() + " Value: " + value.toString();
		}
	}
	
	// -------------------------------------------------------------------------
	
	/** Reference to an array containing the key/value pairs */
	private Entry [] elements;
	
	/** Reference to the number of key/value pairs stored in the table */
	private int currentItems;
	
	/** Reference to the size of the table*/
	private int size;
	
	/** Reference to the load factor. It is used to resize the table
	 * and rehash the key/value pairs 
	 */
	private float loadFactor = 0.7f;
	
	/** Reference to precomputed values for to increase the size of the table */
    private int primeSizeValues[] = {1019, 2027, 4079, 8123, 16267, 32503,
    									65011, 130027, 260111, 520279, 1040387, 
    									2080763, 4161539, 8323151, 16646323};
    
    /**
     * Constructs a hash table object.
     */
    public HashTable() {
    	currentItems = 0;
    	size = primeSizeValues[0];
    	elements = new HashTable.Entry[size];
    }
    
    /**
     * Method to inserts key/value pair in the table.
     * 
     * Throws an IllegalArgumentExcpetion if the key or value 
     * is null.
	 * 
     * @param key
     * @param value
     */
    public void put(K key, V value) throws IllegalArgumentException{
    	
    	if (key == null)
    		throw new IllegalArgumentException("Key is null");
    	if (value == null)
    		throw new IllegalArgumentException("Value is null");
    	
    	if (isReHashable())
    		reHash();
    	
    	Entry e = new Entry(key, value);
    	e.isActive = true;
    	
    	put(e);
    	currentItems++;
    }
    
    /**
     * Helper method that inserts the Entry object
     * in the hash table.
     * 
     * @param entry
     */
    private void put(Entry entry) {
    	
    	int index = index(entry.key);
    	elements[index] = entry;
    }
    
    /**
     * Helper method to find the index location
     * of the Entry associated with the provided key.
     * 
     * @param key
     * @return index where the key maps to.
     */
    private int index(K key) {
    	
    	int offset = 1;
    	int hash = key.hashCode() % size;
    	
    	while (elements[hash] != null && elements[hash].isActive) {
    		hash = (key.hashCode() + ((offset * (offset + 1)) / 2)) % size;
    		offset++;
    	}
    	
    	return hash;
    }
    
    /**
     * Helper method to determine whether the hash table
     * needs to be resized.
     * 
     * @return true if table needs to be resized otherwise false
     */
    private boolean isReHashable() {
    	return currentItems >= loadFactor * size;
    }
    
    /**
     * Helper method that resizes/rehashes the table.
     */
    private void reHash(){
    	
    	Entry[] oldElements = elements;
    	
    	for (int i = 0; i < primeSizeValues.length - 1; i++) {
    		if (size == primeSizeValues[i]) {
    			size = primeSizeValues[i+1];
    			break;
    		}
    	}
    	
    	elements = new HashTable.Entry[size];
    	
    	for (int i = 0; i < oldElements.length; i++) {
    		if (oldElements[i] != null) {
    			put(oldElements[i]);
    		}
    	}
    }
    
    /**
     * Method to remove a specific key/value pair from the table.
     * 
     * Throws an illegal argument exception if the key provided
     * is null.
     * 
     * Throws a No such element exception if the key is not present
     * in the table.
     * 
     * @param key
     * @return
     */
    public V remove(K key) {
    	
    	if (key == null)
    		throw new IllegalArgumentException("Key is null");
    	
    	int index = index(key);
    	
    	if (elements[index] == null || !elements[index].isActive)
    		throw new NoSuchElementException("Key doesn't map to any value.");
    	
    	currentItems--;
    	elements[index].isActive = false;
    	return elements[index].value;
    }
    
    /**
     * Method to find what value does the provided key map to.
     * 
     * Throws an illegal argument exception if the key provided
     * is null.
     * 
     * Throws a No such element exception if the key is not present
     * in the table.
     *  
     * @param key
     * @return
     */
    public V get(K key) {
    	
    	if (key == null)
    		throw new IllegalArgumentException("Key is null");
    	
    	int index = index(key);
    	
    	if (elements[index] == null || !elements[index].isActive)
    		throw new NoSuchElementException("Key doesn't map to any value.");
    	
    	return elements[index].value;
    }
    
    /**
     * Method returns true if the provided value is in the table
     * otherwise it returns false.
     * 
     * @param value : value to search for in the hash table
     * @return : true if the value is in the table otherwise false.
     */
    public boolean containsValue(V value) {
    	
    	if (value == null)
    		return false;
    	
    	for (int i = 0; i < elements.length; i++) {
    		if (elements[i] != null && elements[i].isActive) {
    			if (elements[i].value.equals(value))
    				return true;
    		}
    	}
    	
    	return false;
    }
    
    /**
     * Method returns true if the provided key is in the table
     * otherwise it returns false.
     * 
     * @param key : key to search for in the hash table
     * @return : true if the key is in the table otherwise false
     */
    public boolean containsKey(K key) {
    	
    	if (key == null)
    		return false;
    	
    	return get(key) != null;
    }
    
    /**
     * Method that returns a string representation of the hash table.
     */
    public String toString() {
    	
    	StringBuilder build = new StringBuilder();
    	
    	for (int i = 0; i < size; i++) {
    		if(elements[i] != null) {
    			if(elements[i].isActive)
    				build.append(i + ":  " + elements[i].toString() + "\n");
    				
    		}
    	}
    	
    	return build.toString();
    }

}
