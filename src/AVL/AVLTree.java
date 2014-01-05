package AVL;
import java.util.ArrayList;
import java.util.List;

/**
 * AVL tree is an implementation of a self-balancing
 * binary search tree. The data elements inserted in
 * the tree must be comparable.
 * 
 * @author Arjun Passi
 *
 */
public class AVLTree <T extends Comparable<? super T>>{
	
	/**
	 * This class represents an AVLTreeNode that stores
	 * the data element, both its children, and the height.
	 * @author Arjun Passi
	 *
	 */
	private class AVLTreeNode{
		
		/** Reference to the data element stored in the node. */
		private T mData;
		
		/** Reference to the left child of the node */
		private AVLTreeNode mLeft;
		
		/** Reference to the right child of the node*/
		private AVLTreeNode mRight;
		
		/** Reference to the height of the node*/
		private int mHeight;
		
		/**
		 * Constructs a new AVL tree node.
		 * @param element
		 * @param leftChild
		 * @param rightChild
		 */
		public AVLTreeNode(T data, AVLTreeNode left, AVLTreeNode right){
			mData = data;
			mLeft = left;
			mRight = right;
			mHeight = 0;
		}
	}
	
	/** Reference to the root of the tree*/
	private AVLTreeNode mRoot;
	
	/** Reference to the size of the tree*/
	private int mSize;
	
	/**
	 * Constructs a new AVL tree
	 */
	public AVLTree(){
		mRoot = null;
		mSize = 0;
	}
	
	/**
	 * The "find" method allows to search a desired
	 * data element in the tree.
	 * @param data
	 * @return the reference to the specified data element
	 * otherwise null.
	 */
	public T find(T data){
		if(data == null)
			return null;
		return find(mRoot, data);
	}
	
	/**
	 * This method recursively finds the provided
	 * data element in the tree.
	 * @param n
	 * @param data
	 * @return
	 */
	private T find(AVLTreeNode n, T data){
		//Provided node is null
		if(n == null)
			return null;
		if(n.mData.compareTo(data) == 0)
			return n.mData;
		else if(data.compareTo(n.mData) > 0)
			return find(n.mRight, data);
		else
			return find(n.mLeft, data);
	}
	
	/**
	 * Method to retrieve the max data element in
	 * the AVL tree.
	 * 
	 * @return : reference to the maximum element
	 * in the AVL Tree
	 */
	public T max(){
		
		return max(mRoot);
	}
	
	/**
	 * Helper method to recursively find the maximum 
	 * element in the AVL tree.
	 * 
	 * @param n : AVLTreeNode
	 * @return : reference to the max data element.
	 */
	private T max(AVLTreeNode n){
		if(n == null)
			return null;
		if(n.mRight == null)
			return n.mData;
		
		return max(n.mRight);
	}
	
	/**
	 * Method to retrieve the minimum data element
	 * in the AVL tree.
	 * 
	 * @return : reference to the minimum element.
	 */
	public T min(){
		
		return min(mRoot);
	}
	
	/**
	 * Helper method to recursively find the minimum
	 * data element in the AVL tree.
	 * 
	 * @param n : AVLTreeNode
	 * @return : reference to the minimum element.
	 */
	private T min(AVLTreeNode n){
		if(n == null)
			return null;
		if(n.mLeft == null)
			return n.mData;
		
		return min(n.mLeft);
	}

	/**
	 * Method to find out whether the provided data
	 * is in the tree.
	 * 
	 * @param data : data element to be found.
	 * @return true if the element exists in the tree
	 * otherwise false.
	 */
	public boolean contains(T data){
		if(data == null)
			return false;
		
		return contains(mRoot, data);
	}
	
	/**
	 * Helper method to recursively find the provided
	 * data in the tree.
	 * @param n : AVLTreeNode
	 * @param data : data element to be found
	 * @return true if the element exists otherwise false.
	 */
	private boolean contains(AVLTreeNode n, T data){
		if(n == null)
			return false;
		if(data.compareTo(n.mData) == 0)
			return true;
		else if(data.compareTo(n.mData) > 0)
			return contains(n.mRight, data);
		else
			return contains(n.mLeft, data);
	}
	
	/**
	 * This method allows to insert a data element
	 * in the AVL tree.
	 * @param data
	 * @return true if the insertion was sucesfull else
	 * false
	 */
	public boolean insert(T data){
		if(data == null)
			return false;
		try{
			mRoot = insert(mRoot, data);
			mSize++;
			return true;
		} catch(IllegalArgumentException e){
			return false;
		}
	}
	
	/**
	 * Helper method to recursively insert a data element
	 * in the tree. Throws an illegal argument exception
	 * when duplicate is being added.
	 * @param n
	 * @param data
	 * @return AVLTreeNode
	 */
	private AVLTreeNode insert(AVLTreeNode n, T data){
		if(n == null)
			return new AVLTreeNode(data, null, null);
		
		if(n.mData.compareTo(data) == 0)
			throw new IllegalArgumentException("Duplicates not Allowed!");
		else if(data.compareTo(n.mData) > 0)
			n.mRight = insert(n.mRight, data);
		else
			n.mLeft = insert(n.mLeft, data);
		
		//Max Allowed height difference in an AVL tree is 1
		if(Math.abs(difference(n)) != 1)
			n = performRotation(n);
		
		//update the height of the node
		n.mHeight = Math.max(height(n.mLeft), height(n.mRight)) + 1;
		return n;
	}
	
	/**
	 * Method removes only one occurrence o
	 * data element.
	 * @param data
	 * @return
	 */
	public boolean remove(T data){
		if(data == null)
			return false;
		try{
			mRoot = remove(mRoot, data);
			mSize--;
			return true;
		} catch(IllegalArgumentException e){
			return false;
		}
	}
	
	/**
	 * Helper function to recursively find and remove
	 * the element in the AVL tree. Throws an illegal
	 * argument exception if the element is not in the
	 * AVL tree.
	 * @param n
	 * @param data
	 * @return AVLTreeNode
	 */
	private AVLTreeNode remove(AVLTreeNode n, T data){
		if(n == null)
			throw new IllegalArgumentException("Element Not Found!");
		
		if(n.mData.compareTo(data) == 0)
			return remove(n);
		else if(data.compareTo(n.mData) > 0)
			n.mRight = remove(n.mRight, data);
		else
			n.mLeft = remove(n.mLeft, data);
		
		//Max Allowed height difference in an AVL tree is 1
		if(Math.abs(difference(n)) != 1)
			n = performRotation(n);
				
		//update the height of the node
		n.mHeight = Math.max(height(n.mLeft), height(n.mRight)) + 1;
		return n;
	}
	
	/**
	 * Helper method to remove the desired AVLTreeNode
	 * from the AVL tree.
	 * @param n
	 * @return
	 */
	private AVLTreeNode remove(AVLTreeNode n){
		if(n == null)
			return null;
		
		if(n.mLeft == null && n.mRight == null)
			return null;
		else if(n.mLeft == null && n.mRight != null)
			return n.mRight;
		else if(n.mLeft != null && n.mRight == null)
			return n.mLeft;
		else {
			T data = findMin(n.mRight);
			n = remove(n, data);
			n.mData = data;
			return n;
		}
	}
	
	/**
	 * Helper method to find the minimum data element
	 * provided an AVL node.
	 * @param subRoot
	 * @return minimum element in the provided sub-tree
	 */
	private T findMin(AVLTreeNode subRoot){
		if(subRoot == null)
			return null;
		if(subRoot.mLeft == null)
			return subRoot.mData;
		return findMin(subRoot.mLeft);
	}
	
	/**
	 * Method to find out if the provided subtree is a
	 * subtree of this tree.
	 * 
	 * @param tree : AVLTree passed.
	 * @return true if the AVLTree passed is a subtree.
	 */
	public boolean isSubTree(AVLTree<T> tree){
		if(tree == null)
			return false;
		if(tree.mSize == 0)
			return true;
		
		AVLTreeNode n = mRoot;
		
		while(n != null){
			if(n.mData.equals(tree.mRoot.mData))
				break;
			else if(tree.mRoot.mData.compareTo(n.mData) > 0)
				n = n.mRight;
			else
				n = n.mLeft;
		}
		
		return equals(n, (AVLTreeNode) tree.mRoot);
	}
	
	/**
	 * Method to retrieve the size of the tree.
	 * @return size
	 */
	public int size(){
		return mSize;
	}
	
	/**
	 * Removes all the elements in the AVL tree.
	 */
	public void removeAll(){
		mSize = 0;
		mRoot = null;
	}
	/**
	 * Method to insert all the elements in the tree to
	 * a list ordered in inOrderTraversal.
	 * @return list of elements in InOrder
	 */
	public List<T> inOrderTraversal(){
		ArrayList<T> list = new ArrayList<T>();
		inOrderTraversal(mRoot, list);
		return list;
	}
	
	/**
	 * Helper method to traverse the tree in InOrder.
	 * @param n
	 * @param list
	 */
	private void inOrderTraversal(AVLTreeNode n, ArrayList<T> list){
		if(n == null)
			return;
		
		inOrderTraversal(n.mLeft, list);
		list.add(n.mData);
		inOrderTraversal(n.mRight, list);
	}
	
	/**
	 * Helper method to perform a rotation on the provided
	 * node based on the height difference of the left and
	 * the right children.
	 * @param n
	 * @return new sub root
	 */
	private AVLTreeNode performRotation(AVLTreeNode n){
		if(n == null) return null;
		
		//Rotation not required
		if(Math.abs(difference(n)) != 2)
			return n;
		
		if(difference(n) == 2 && difference(n.mLeft) == 1)
			n = rightRotateLeftChild(n);
		else if(difference(n) == 2 && difference(n.mLeft) == -1){
			n.mLeft = leftRotateRightChild(n.mLeft);
			n = rightRotateLeftChild(n);
		}
		else if(difference(n) == -2 && difference(n.mRight) == -1)
			n = leftRotateRightChild(n);
		else{
			n.mRight = rightRotateLeftChild(n.mRight);
			n = leftRotateRightChild(n);
		}
		return n;
	}
	
	/**
	 * This method performs a right rotation on the left
	 * child of the provided node.
	 * @param n
	 * @return the new sub root
	 */
	private AVLTreeNode rightRotateLeftChild(AVLTreeNode n){
		if(n == null || n.mLeft == null)
			return n;
		
		AVLTreeNode newSubRoot = n.mLeft;
		n.mLeft = newSubRoot.mRight;
		newSubRoot.mRight = n;
		
		//Adjusting the heights of the old and new sub root.
		newSubRoot.mHeight = Math.max(height(newSubRoot.mLeft),
				height(newSubRoot.mRight)) + 1;
		n.mHeight = Math.max(height(n.mLeft), height(n.mRight)) +1;
		
		return newSubRoot;
	}
	
	/**
	 * This method performs a left rotation on the right
	 * child of the provided node.
	 * @param n
	 * @return the new sub root
	 */
	private AVLTreeNode leftRotateRightChild(AVLTreeNode n){
		if(n == null || n.mRight == null)
			return n;
		
		AVLTreeNode newSubRoot = n.mRight;
		n.mRight = newSubRoot.mLeft;
		newSubRoot.mLeft = n;
		
		//Adjusting the heights of the old and new sub root.
		newSubRoot.mHeight = Math.max(height(newSubRoot.mLeft), 
				height(newSubRoot.mRight)) + 1;
		n.mHeight = Math.max(height(n.mLeft), height(n.mRight)) +1;
		return newSubRoot;
	}
	
	/**
	 * Method to retrieve height of an avl node.
	 * @param n- AVL node
	 * @return height of the AVL node if provdided
	 * otherwise returns -1.
	 */
	private int height(AVLTreeNode n){
		return (n == null) ? -1 : n.mHeight;
	}
	
	/**
	 * Helper method to determine whether a rotation
	 * is required or not.
	 * @param n
	 * @return difference of the height of the left
	 * and right child of the provided node.
	 */
	private int difference(AVLTreeNode n){
		return (n == null) ? 0 : height(n.mLeft) - height(n.mRight);
	}
	
	/**
	 * Helper method to determine if the two trees are equals
	 * or not.
	 * @param n1
	 * @param n2
	 * @return
	 */
	private boolean equals(AVLTreeNode n1, AVLTreeNode n2){
		if(n1 == null && n2 == null)
			return true;
		if(n1 == null ^ n2 == null)
			return false;
		
		if(!n1.mData.equals(n2.mData))
			return false;
		
		return equals(n1.mLeft, n2.mLeft) && equals(n1.mRight, n2.mRight);
	}
	
	/**
	 * Method to find out if the tree object passed
	 * is similar to this tree object.
	 * 
	 * @param obj : AVL tree to compare with
	 * @return true if the two AVL trees are equal.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		
		if(obj instanceof AVLTree){
			AVLTree<T> tree = (AVLTree<T>) obj;
			
			if(size() != tree.size())
				return false;
			
			return equals(mRoot, tree.mRoot);
		}
		
		return false;
	}
}
