package ListStructures;

/**
 * This is a test class for SingleLinkedList.
 * @author Arjun Passi
 *
 */
public class SingleLinkedListTest extends AbstractListTest{
	
	/** Reference to the linked list testing will be performed on*/
	private SingleLinkedList<Integer> mTestList;


	/**
	 * Method creates and returns a SingleLinkedList.
	 * 
	 * @return instance of SingleLinkedList
	 */
	@Override
	public List<Integer> create() {
		mTestList = new SingleLinkedList<Integer>();
		return mTestList;
	}
	
}
