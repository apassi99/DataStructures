package HashTable;

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
    	
    	put(new Entry(key, value));
    }
    
    private void put(Entry entry) {
    	
    }
    
    public V remove(K key) {
    	return null;
    }
    
    public V get(K key) {
    	return null;
    }
    
    public boolean containsValue(V value) {
    	return false;
    }
    
    public boolean containsKey(K key) {
    	return false;
    }
    
    

}
