package proj2;

public class Tree <E> {
	
	/** Root of tree */
	private Node root;
	/** Number of nodes in the tree */
	private int size;
	
	/**
	 * Null constructor
	 */
	public Tree() {
		this(null);
	}
	
	/**
	 * Constructor with given root
	 */
	public Tree(Node root) {
		this.root = root;
		size = 1;
	}
	
	/**
	 * Returns true if tree is empty.
	 * @return True if empty.
	 */
	public boolean isEmpty() {
		if (root == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns total number of nodes in the tree.
	 * @return Total number of nodes in the tree.
	 */
	public int size() {
		return size;
	}
	
	private class Node {
		
		/** Data contained in the Node */
		private E data;
		/** Pointer to parent of this Node */
		private Node parent;
		/** Pointer to child(ren) of this Node */
		private Node child;
		/** Marker to follow ancestry line for relationships */
		private boolean mark;
		
		/**
		 * Null constructor
		 */
		public Node() {
			this(null, null, null);
		}
		
		/**
		 * Constructs a new node from the given data and parent and child pointers.
		 * @param data Data to contain in the node.
		 * @param parent Pointer to node's parent
		 * @param child Pointer to node's child(ren)
		 */
		public Node (E data, Node parent, Node child) {
			this.data = data;
			this.parent = parent;
			this.child = child;
			mark = false;
		}
		
		/**
		 * Returns true if this node contains no data
		 * @return True if this node contains no data
		 */
		public boolean isEmpty() {
			if (data == null) {
				return true;
			}
			return false;
		}
		
		/**
		 * Returns true if this node is the root of the tree
		 * @return True if node is root
		 */
		public boolean isRoot() {
			if (parent == null) {
				return true;
			}
			return false;
		}
		
		/**
		 * Returns true if this node has no children
		 * @return True if node has no children
		 */
		public boolean isLeaf() {
			if (child == null) {
				return true;
			}
			return false;
		}
		
		/**
		 * Returns true if this node has both parent and child
		 * @return True if node has both a parent and child
		 */
		public boolean isInternal() {
			if (parent != null && child != null) {
				return true;
			}
			return false;
		}
	}
}