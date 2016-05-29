package QuadTree;

import java.util.LinkedList;
import java.util.List;

/**
 * A implementation of QuadTree that partitions a two dimensional
 * domain by dividing it into four quadrants. The keys associated
 * with the quad tree are stored in X-Y coordinate pairs.
 * 
 * This is not a thread safe implementation.
 * 
 * @author Arjun Passi
 *
 */
public class QuadTree <T>{

	
	/** Represents a quadrant direction. */
	private enum Direction {NW, SW, SE, NE, NOQUADRANT};
	
	/**
	 * Interface defining a spatial domain of x-y coordinates. 
	 *  
	 * @author Arjun Passi
	 *
	 */
	private static interface Domain {
		
		/**
		 * Method returns true if domain intersects with the
		 * provided RectangularDomain
		 *
		 * @param rectDomain - rectangular domain
		 * @return true if intersection exists otherwise false.
		 */
		public boolean intersects(RectangularDomain rectDomain);
		
		/**
		 * Returns true if the provided x-y coordinate is
		 * present in the domain.
		 * 
		 * @param x
		 * @param y
		 * @return
		 */
		public boolean contains(long x, long y);
	}
	
	/**
	 * This class defines a rectangular domain of x-y
	 * coordinates.
	 * 
	 * @author Arjun Passi
	 *
	 */
	private static class RectangularDomain implements Domain {
		
		/** Reference to the upper/lower x/y bounds of the domain. */
		long m_xMin, m_xMax, m_yMin, m_yMax;

		/**
		 * Constructor for the RectangularDomain
		 * 
		 * @param xMin - lower x-coordinate bound
		 * @param xMax - upper x-coordinate bound
		 * @param yMin - lower y-coordinate bound
		 * @param yMax - upper y-coordinate bound
		 */
		RectangularDomain(long xMin, long xMax, long yMin, long yMax) {
			m_xMin = xMin;
			m_xMax = xMax;
			m_yMin = yMin;
			m_yMax = yMax;
		}
		
		public Direction inQuadrant(long xcord, long ycord) {
			assert contains(xcord, ycord);
			
			double xmax = m_xMax;
			double xmin = m_xMin;
			double ymax = m_yMax;
			double ymin = m_yMin;
			
			double xmid = (xmax + xmin) / 2;
			double ymid = (ymax + ymin) / 2;
			double x = xcord;
			double y = ycord;

			
			if (x >= xmid && y >= ymid)
				return Direction.NE;
			else if (x >= xmid && y < ymid)
				return Direction.SE;
			else if (x < xmid && y >= ymid)
				return Direction.NW;
			else if (x < xmid && y < ymid)
				return Direction.SW;
			else 
				return Direction.NOQUADRANT;
		}

		@Override
		public boolean intersects(RectangularDomain rectDomain) {
			assert rectDomain != null;
			
			return m_xMin < rectDomain.m_xMax && m_xMax > rectDomain.m_xMin
			          && m_yMin < rectDomain.m_yMax && m_yMax > rectDomain.m_yMin;
		}

		@Override
		public boolean contains(long x, long y) {
			return (x <= m_xMax && x>= m_xMin && y<= m_yMax && y >= m_yMin);
		}
	}
	
	private class QuadTreeEntry {
		
		/** Reference to the x-coordinate. */
		private long m_x;
		
		/** Reference to the y-coordinate. */
		private long m_y;
		
		/** Reference to the data element. */
		private T m_element;
		
		/** Reference to flag indicating whether the
		 *  entry was placed in the QuadTree. */
		private boolean m_placed;
		
		/**
		 * Constructs a QuadTreeEntry object.
		 * 
		 * @param elem - data element
		 * @param x - x-coordinate
		 * @param y - y-coordinate
		 */
		QuadTreeEntry(T elem, long x, long y) {
			m_x = x;
			m_y = y;
			m_element = elem;
			m_placed = false;
		}
		
		public boolean sameCoordinate(QuadTreeEntry entry) {
			assert entry != null;
			
			return (m_x == entry.m_x && m_y == entry.m_y);
		}
		
		@Override
		public String toString() {
			StringBuilder build = new StringBuilder();
			build.append("X: " + m_x + "  Y: " + m_y + "  Element: " + m_element);
			return build.toString();
		}
	}
	
	// You must use a hierarchy of node types with an abstract base
	// class.  You may use different names for the node types if
	// you like (change displayHelper() accordingly).
	private abstract class QuadNode { }
	
	/**
	 * This class represents a leaf node in the quad tree.
	 * It does not store any references to the 4 child nodes.
	 * 
	 * @author Arjun Passi
	 *
	 */
	private class QuadLeafNode extends QuadNode {
		
		/** Reference to a data entry in the leaf node. */
		QuadTreeEntry m_entry;
		
		/**
		 * Constructs a QuadLeafNode.
		 * 
		 * @param elem - data element
		 * @param x - x-coordinate
		 * @param y - y-coordinate
		 */
		QuadLeafNode(T elem, long x, long y) {
			m_entry = new QuadTreeEntry(elem, x, y);
		}
		
		/**
		 * Constructs a QuadLeafNode.
		 * 
		 * @param entry - QuadTreeEntry
		 */
		QuadLeafNode(QuadTreeEntry entry) {
			m_entry = entry;
		}

		/**
		 * Method returns string representation
		 * of the leaf node.
		 * 
		 * @return string representation of the leaf node.
		 */
		@Override
		public String toString() {
			StringBuilder build = new StringBuilder();
			build.append(m_entry.toString());
			return build.toString();
		}
	}

	/**
	 * The QuadInternalNode class stores the references to the
	 * North West, North East, South West, South East
	 * 
	 * @author Arjun Passi
	 */
	private class QuadInternalNode extends QuadNode {
		
		/** Reference to the four child nodes. */
		QuadNode NW, NE, SE, SW;
	      
		/**
		 * Constructor of QuadInternalNode
		 * 
		 * @param NW
		 * @param NE
		 * @param SE
		 * @param SW
		 */
		QuadInternalNode(QuadNode NW, QuadNode NE, QuadNode SE, QuadNode SW) {
	    	  this.NW = NW;
	    	  this.NE = NE;
	    	  this.SE = SE;
	    	  this.SW = SW;
		}
	}

	
	/** Reference to the root of the QuadTree */
	private QuadNode m_Root;
	
	/** Reference to the max boundaries of the QuadTree */
	private RectangularDomain m_QuadTreeDomain;
	
	/** Initializing boundary constants for the QuadTree region */
	private static final long XMAX_DEFAULT = 100;
	private static final long XMIN_DEFAULT = 0;
	private static final long YMAX_DEFAULT = 100;
	private static final long YMIN_DEFAULT = 0;
	
	/**
	 * Constructs a QuadTree object with
	 * default domain size.
	 */
	QuadTree() {
		m_Root = null;
		m_QuadTreeDomain = new RectangularDomain(
						XMIN_DEFAULT, XMAX_DEFAULT,
						YMIN_DEFAULT, YMAX_DEFAULT);
	}
	
	/**
	 * Constructs a QuadTree object with the provided
	 * domain size.
	 * 
	 * @param xlo - lower x-coordinate
	 * @param xhi - upper x-coordinate
	 * @param ylo - lower y-coordinate
	 * @param yhi - upper y-coordinate
	 */
	QuadTree(long xlo, long xhi, long ylo, long yhi) {
		
		if (xlo > xhi) {
			throw new IllegalArgumentException(
					"xlo must be less than or equal to xhi.");
		}
		
		if (ylo > yhi) {
			throw new IllegalArgumentException(
					"ylo must be less than or equal to yhi.");
		}
		    
		m_Root = null;
		m_QuadTreeDomain = new RectangularDomain(
				xlo, xhi, ylo, yhi);
	}
	
	/**
	 * Method to insert a data item in the QuadTree.
	 * 
	 * @param elem - data element
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @return True if insertion was successful otherwise False.
	 * 		   Insertion fails if there exists a mapping at x-y.
	 */
	public boolean insert(T elem, long x, long y) {
		
		if(elem == null || !m_QuadTreeDomain.contains(x, y))
			return false;

		RectangularDomain curDomain = m_QuadTreeDomain;
		QuadTreeEntry entry = new QuadTreeEntry(elem, x, y);
		m_Root = insert(m_Root, entry, curDomain);
		return entry.m_placed;
	}
	
	/**
	 * Helper recursive method to insert an item in the QuadTree.
	 * 
	 * @param n - Root node of the tree to insert the element in. 
	 * @param entry - Data Entry to be inserted.
	 * @param domain - domain of the subtree.
	 * @return The new root of the specified sub tree.
	 */
	private QuadNode insert(QuadNode n, QuadTreeEntry entry, RectangularDomain domain) {
		
		if (n == null) {
			n = new QuadLeafNode(entry);
			entry.m_placed = true;
		}
		
		else if (n instanceof QuadTree.QuadLeafNode) {
			
			QuadLeafNode temp = (QuadLeafNode) n;
			
			if (temp.m_entry.sameCoordinate(entry)) {
				return n;
			}
			
			QuadTreeEntry copyData = ((QuadLeafNode) n).m_entry;
			n = new QuadInternalNode(null, null, null, null);
			n = insert(n, copyData, domain);
			n = insert(n, entry, domain);
	   	} 
		
		else if (n instanceof QuadTree.QuadInternalNode) {
			switch (domain.inQuadrant(entry.m_x, entry.m_y)) 
			{
				case NE:
					((QuadInternalNode ) n).NE = insert(((QuadInternalNode ) n).NE,
							entry, modifyRectDomain(domain, Direction.NE));
					break;
				case NW:
					((QuadInternalNode ) n).NW = insert(((QuadInternalNode ) n).NW,
							entry, modifyRectDomain(domain, Direction.NW));
					break;
				case SE:
					((QuadInternalNode ) n).SE = insert(((QuadInternalNode ) n).SE,
							entry, modifyRectDomain(domain, Direction.SE));
					break;
				case SW:
					((QuadInternalNode ) n).SW = insert(((QuadInternalNode ) n).SW,
							entry, modifyRectDomain(domain, Direction.SW));
					break;
					
				case NOQUADRANT:
					break;
			}
		}
		
		return n;
	}
	
	/**
	 * Returns the value that is mapped by the provided
	 * x-y coordinate pair.
	 * 
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @return Reference to the value or null (if no mapping or
	 * 		   mapped value is null).
	 */
	public T find(long x, long y) {
		
		if ( !m_QuadTreeDomain.contains(x, y) ) {
			return null;
		}
		
		return find(m_Root, x, y, m_QuadTreeDomain);
	}
	
	/**
	 * Helper recursive method to search within the quadtree given
	 * x-y coordinate pairs.
	 * 
	 * @param n - Root node of the subtree to search in
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @param domain - domain of the subtree.
	 * @return Reference to the value or null (if no mapping).
	 */
	private T find(QuadNode n, long x, long y, RectangularDomain domain) {
		
		if (n == null) {
			return null;
		}
		
		else if (n instanceof QuadTree.QuadLeafNode) {
			
			QuadLeafNode temp = (QuadLeafNode) n;
			
			if (temp.m_entry.m_x == x && temp.m_entry.m_y == y) {
				return temp.m_entry.m_element;
			}
	   	} 
		
		else if (n instanceof QuadTree.QuadInternalNode) {
			switch (domain.inQuadrant(x, y)) 
			{
				case NE:
					return find(((QuadInternalNode ) n).NE, x, y,
							modifyRectDomain(domain, Direction.NE));
				case NW:
					return find(((QuadInternalNode ) n).NW, x, y,
							modifyRectDomain(domain, Direction.NW));
				case SE:
					return find(((QuadInternalNode ) n).SE, x, y,
							modifyRectDomain(domain, Direction.SE));
				case SW:
					return find(((QuadInternalNode ) n).SW, x, y,
							modifyRectDomain(domain, Direction.SW));
			}
		}
		
		return null;
	}
	
	/**
	 * Method to remove the element mapping to provided
	 * x-y coordinate.
	 * 
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @return reference to the element removed or null if there was no mapping
	 */
	public T remove(long x, long y) {
		if ( !m_QuadTreeDomain.contains(x, y) ) {
			return null;
		}
		
		QuadTreeEntry entry = new QuadTreeEntry(null, x, y);
		m_Root = remove(m_Root, entry, x, y, m_QuadTreeDomain);
		
		return entry.m_element;
	}
	
	/**
	 * Helper method to recursively remove from an entry from
	 * a QuadTree.
	 * 
	 * @param n - Root node of the subtree where entry is to be deleted
	 * @param entry - data item to be removed
	 * @param x - x-coordinate
	 * @param y - y-coordinate
	 * @param domain - current search domain
	 * @return new root of the subtree with the node removed
	 */
	private QuadNode remove(QuadNode n, QuadTreeEntry entry, long x, long y, RectangularDomain domain) {
		
		if (n == null) {
			return null;
		}
		
		if (n instanceof QuadTree.QuadLeafNode) {
			
			QuadLeafNode temp = (QuadLeafNode) n;
			
			if (temp.m_entry.m_x == x && temp.m_entry.m_y == y) {
				entry.m_element = temp.m_entry.m_element;
				return null;
			}
		}
		
		else if (n instanceof QuadTree.QuadInternalNode) {
			switch (domain.inQuadrant(x, y)) 
			{
				case NE:
					((QuadInternalNode ) n).NE = remove(((QuadInternalNode ) n).NE, entry, 
							x, y, modifyRectDomain(domain, Direction.NE));
					break;
				case NW:
					((QuadInternalNode ) n).NW = remove(((QuadInternalNode ) n).NW, entry, 
							x, y, modifyRectDomain(domain, Direction.NW));
					break;
				case SE:
					((QuadInternalNode ) n).SE = remove(((QuadInternalNode ) n).SE, entry, 
							x, y, modifyRectDomain(domain, Direction.SE));
					break;
				case SW:
					((QuadInternalNode ) n).SW = remove(((QuadInternalNode ) n).SW, entry, 
							x, y, modifyRectDomain(domain, Direction.SW));
					break;
			}
			
			n = checkAndUpdate((QuadInternalNode) n);
		}
		
		return n;
	}
	
	private QuadNode checkAndUpdate(QuadInternalNode n) {
		if (n == null) {
			return null;
		}
		
		int count = 0;
		
		if (n.NE == null) count++;
		if (n.NW == null) count++;
		if (n.SE == null) count++;
		if (n.SW == null) count++;
		
		if (count != 3)
			return n;
		
		if(n.NE != null && n.NE instanceof QuadTree.QuadLeafNode)
			return n.NE;
		else if(n.NW != null && n.NW instanceof QuadTree.QuadLeafNode)
			return n.NW;
		else if(n.SW != null && n.SW instanceof QuadTree.QuadLeafNode)
			return n.SW;
		else if (n.SE != null && n.SE instanceof QuadTree.QuadLeafNode)
			return n.SE;
		else
			return n;
	}
	
	/**
	 * Method to return list of items in the provided
	 * rectangular domain.
	 * 
	 * @param xlo
	 * @param xhi
	 * @param ylo
	 * @param yhi
	 * @return list of items in the provided rectangular domain
	 */
	public List<T> find(long xlo, long xhi, long ylo, long yhi) {
		
		List<T> list = new LinkedList<T>();
		RectangularDomain searchDomain = new RectangularDomain(xlo, xhi, ylo, yhi);
		
		if (m_QuadTreeDomain.intersects(searchDomain)) {
			find(m_Root, m_QuadTreeDomain, searchDomain, list);
		}
		
		return list;
	}
	
	/**
	 * Helper method to recursively search for elements in a provided
	 * search domain.
	 * 
	 * @param n - Sub root node of the tree to search in 
	 * @param treeDomain - current tree domain
	 * @param searchDomain - search domain
	 * @param list - list of elements in the search domain
	 */
	private void find(QuadNode n, RectangularDomain treeDomain, Domain searchDomain, List<T> list) {
		
		if (n == null) {
			return;
		}
		
		if (n instanceof QuadTree.QuadLeafNode) {
			QuadLeafNode temp = (QuadLeafNode) n;
			list.add(temp.m_entry.m_element);
		}
		
		else if (n instanceof QuadTree.QuadInternalNode) {
			
			QuadInternalNode temp = (QuadInternalNode) n;
			RectangularDomain newDomain = modifyRectDomain(treeDomain, Direction.NE);
			
			if (searchDomain.intersects(newDomain)) {
				find(temp.NE, newDomain, searchDomain, list);
			}
			
			newDomain = modifyRectDomain(treeDomain, Direction.NW);
			
			if (searchDomain.intersects(newDomain)) {
				find(temp.NW, newDomain, searchDomain, list);
			}
			
			newDomain = modifyRectDomain(treeDomain, Direction.SW);
			
			if (searchDomain.intersects(newDomain)) {
				find(temp.SW, newDomain, searchDomain, list);
			} 
			
			newDomain = modifyRectDomain(treeDomain, Direction.SE);
			
			if (searchDomain.intersects(newDomain)) {
				find(temp.SE, newDomain, searchDomain, list);
			} 
		}
	}
	
	/**
	 * Helper method to modify the rect domain depending on the direction.
	 * This is used to traverse down the tree while inserting, finding
	 * and removing.
	 * 
	 * @param domain - rectangular domain
	 * @param d - direction
	 * @return
	 */
	private RectangularDomain modifyRectDomain(RectangularDomain domain, Direction d) {
		
		RectangularDomain new_domain = new RectangularDomain(domain.m_xMin,
									domain.m_xMax, domain.m_yMin,
									domain.m_yMax);
		
		long xmid = (domain.m_xMax + domain.m_xMin) / 2;
		long ymid = (domain.m_yMax + domain.m_yMin) / 2;
		
		
		switch (d)
		{
			case NE:
				new_domain.m_xMin = xmid;
				new_domain.m_yMin = ymid;
				break;
				
			case NW:
				new_domain.m_xMax = xmid;
				new_domain.m_yMin = ymid;
				break;
				
			case SW:
				new_domain.m_xMax = xmid;
				new_domain.m_yMax = ymid;
				break;
				
			case SE:
				new_domain.m_xMin = xmid;
				new_domain.m_yMax = ymid;
				break;
				
			case NOQUADRANT:
				break;
				
			default:
				break;
		}
		
		return new_domain;
	}
}
