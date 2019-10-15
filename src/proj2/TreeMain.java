package proj2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import proj2.Queue;

public class TreeMain {

	/** Array containing the Pretraversal order from input */
	public static char pretrav[];

	/** Array containing the Posttraversal order from input */
	public static char posttrav[];

	/** Current spot in the preorder traversal */
	public static int preindex = 0;

	/** Current spot in the postorder traversal */
	public static int postindex = -1;
	
	/** Queue to hold nodes in level order traversal for printing */
	public static Queue<Character> levelQueue;

	public static void main(String[] args) {
		// Setup Scanner
		// Scanner input = new Scanner(System.in);
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		String pretravString = getInputString(input);
		String posttravString = getInputString(input);
		int numNodes = pretravString.length();

		// Initialize arrays with proper length
		pretrav = new char[numNodes];
		posttrav = new char[numNodes];

		// Fill Pretraversal array
		for (int i = 0; i < numNodes; i++) {
			pretrav[i] = pretravString.charAt(i);
		}

		// Fill Posttraversal array
		for (int i = 0; i < numNodes; i++) {
			posttrav[i] = posttravString.charAt(i);
		}

		// Create tree
		TreeMain treeMain = new TreeMain();
		Tree tree1 = treeMain.new Tree();
		tree1.setRoot(tree1.buildTree(numNodes, 0, 0));
		
		// Create level order queue, print level order
		levelQueue = new Queue<Character>();
	}

	/**
	 * Scans the first line of input and returns a string of chars representing the
	 * node labels in the traversal order
	 * 
	 * @param input BufferedReader object parsing the traversal input
	 * @return String containing just the Node ideas in the traversal
	 */
	public static String getInputString(BufferedReader input) {
		String inputString = "";
		try {
			char temp = (char) input.read();
			if (temp == '.') {
				temp = (char) input.read();
			}
			while (temp != '.') {
				while (!Character.isLetter(temp)) {
					temp = (char) input.read();
				}
				inputString += temp;
				temp = (char) input.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputString;
	}

	/**
	 * Linearly traverses the given traversal array to find the target Node label
	 * and returns its index
	 * 
	 * @param travArray The traversal array you want to search
	 * @param target    Node label you are looking for
	 * @return Index of the Node label in the given array
	 */
	public static int getIndex(char[] travArray, char target) {
		for (int i = 0; i < travArray.length; i++) {
			if (travArray[i] == target) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the number of children of a node based on its subtree's size,
	 * pretraversal start and posttraversal start
	 * 
	 * @param size      Size of the node's subtree
	 * @param prestart  Starting index of the subtree in the pretraversal array
	 * @param poststart Starting index of the subtree in the posttraversal array
	 * @return Number of children the node has
	 */
	public static int numChildren(int size, int prestart, int poststart) {
		char rootChar = pretrav[prestart];
		int preindex = prestart + 1;
		int postindex = poststart;
		int numChildren = 0;
		while (postindex < size) {
			int subtreeSize = 1;
			while (posttrav[postindex] != pretrav[preindex]) {
				postindex++;
				subtreeSize++;
			}
			numChildren++;
			postindex++;
			if (posttrav[postindex] == rootChar) {
				break;
			}
			preindex += subtreeSize;
		}
		return numChildren;
	}

	public class Tree {

		/** Root of tree */
		private Node root;
		/** Number of nodes in the tree */
		private int size;

		/**
		 * Null constructor
		 */
		public Tree() {
			this(null);
//			root = new Node(pretrav[0], null, new Node[pretrav.length]);
//			size = 1;
		}

		/**
		 * Constructor with given root
		 */
		public Tree(Node root) {
			this.root = root;
			size = 1;
		}

		/**
		 * Performs a pre-order traversal of the tree to find the Node containing the
		 * given data.
		 * 
		 * @param data The Node value to lookup
		 * @return Node containing the given data parameter.
		 */
		public Node lookup(char data) {
			// TODO
			return null;
		}

		/**
		 * Returns true if tree is empty.
		 * 
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
		 * 
		 * @return Total number of nodes in the tree.
		 */
		public int size() {
			return size;
		}

		/**
		 * Sets root of this tree to the given node
		 * 
		 * @param root Node to set root to
		 */
		public void setRoot(Node root) {
			this.root = root;
		}

		/**
		 * Returns the root of this tree
		 * 
		 * @return Root node of this tree
		 */
		public Node getRoot() {
			return this.root;
		}

		/**
		 * Traverses the array in preorder and resets all relationship markers back to
		 * false after a relationship inquiry is performed.
		 */
		public void resetMarks() {
			// TODO Reset relationship markers of all nodes
		}

		/**
		 * Recursively builds the tree from preorder and postorder traversal input
		 * 
		 * @param size      Number of nodes in subtree to be built
		 * @param prestart  Starting point of subtree in pretrav array
		 * @param poststart Starting point of subtree in postrav array
		 */
		public Node buildTree(int size, int prestart, int poststart) {
			// Base case
			if (size == 1) {
				return new Node(pretrav[prestart]);
			} else {
				// Create child array
				Node child[] = new Node[numChildren(size, prestart, poststart)];
				prestart++;
				// Loop through all children of current node
				for (int i = 0; i < child.length; i++) {
					int preindex = prestart;
					int postindex = poststart;
					int subtreeSize = 1;
					// Find subtreeSize of current child
					while (posttrav[postindex] != pretrav[preindex]) {
						subtreeSize++;
					}
					// Build child subtree then move to next child
					child[i] = buildTree(subtreeSize, prestart, poststart);
					prestart += subtreeSize;
					poststart += subtreeSize;
				}
				// Make root
				Node root = new Node(pretrav[prestart]);
				root.setChildren(child);
				// Set child parent pointers
				for (int j = 0; j < root.getChildren().length; j++) {
					root.getChildren()[j].setParent(root);
				}
				return root;
//				Attempt #2	
//				// Root with null child array
//				Node root = new Node(pretrav[prestart]);
//				Node child[] = new Node[size];
//				preindex = postindex + 1;
//				// Find next child postindex
//				int nextChildPostIndex = getIndex(posttrav, pretrav[preindex]);
//				int subtreeSize = nextChildPostIndex - postindex;
//				postindex = nextChildPostIndex;
//				int i = 0;
//				while (postindex < size) {
//					child[i] = buildTree(subtreeSize, preindex, postindex);
//				}
//				root.setChildren(child);
//				// Set parent pointers of children to root
//				for (int j = 0; j < root.child.length; j++) {
//					try {
//						root.getChildren()[j].parent = root;
//					} catch (NullPointerException e) {
//						// No more children
//						break;
//					}
//				}
//				return root;
//				
//				Attempt #1
//				Node[] child = new Node[size - 1];
//				char nextNode = pretrav[prestart + 1];
//				int subtreeSize = TreeMain.getIndex(posttrav, nextNode) - prestart + 1;
//				child[1] = buildTree(subtreeSize, prestart + 1, TreeMain.getIndex(posttrav, nextNode));
//				// Repeat for all children
//				Node subRoot = new Node(pretrav[prestart], null, child);
//				// Set parent pointers for all children
//				for (int i = 0; i < child.length; i++) {
//					try {
//						subRoot.child[i].parent = subRoot;
//					} catch (NullPointerException e) {
//						// You've hit all children, break out of loop
//						break;
//					}
//				}
//				return subRoot;
			}
		}
		
		/**
		 * Prints the level-order traversal of this tree
		 */
		public void printLevelOrder() {
			
			
		}

		private class Node {

			/** Data contained in the Node */
			private char data;
			/** Pointer to parent of this Node */
			private Node parent;
			/** Array of pointers to child(ren) of this Node */
			private Node child[];
			/** Marker to follow ancestry line for relationships */
			private boolean mark;

			/**
			 * Null constructor
			 */
			public Node() {
				this(' ', null, null);
			}

			public Node(char data) {
				this(data, null, null);
			}

			/**
			 * Constructs a new node from the given data and parent and child pointers.
			 * 
			 * @param data   Data to contain in the node.
			 * @param parent Pointer to node's parent
			 * @param child  Array of pointers to node's child(ren)
			 */
			public Node(char data, Node parent, Node child[]) {
				this.data = data;
				this.parent = parent;
				this.child = child;
				mark = false;
			}

			/**
			 * Sets the children of this node to the given array of pointers
			 * 
			 * @param children Array of pointers to children of this node
			 */
			private void setChildren(Node[] children) {
				this.child = children;
			}

			/**
			 * Returns this node's array of child pointers
			 * 
			 * @return This node's array of child pointers
			 */
			private Node[] getChildren() {
				return this.child;
			}

			/**
			 * Sets the parent pointer of this node to the given parameter
			 * 
			 * @param parent Node to set this node's parent pointer to
			 */
			private void setParent(Node parent) {
				this.parent = parent;
			}

			/**
			 * Sets this node's mark to the given boolean value. Used for determining
			 * relationships and resetting marks afterwards.
			 * 
			 * @param flag Value to set this node's mark to
			 */
			private void setMark(boolean flag) {
				this.mark = flag;
			}
			
			private char getData() {
				return this.data;
			}

			/**
			 * Returns true if this node contains no data
			 * 
			 * @return True if this node contains no data
			 */
			public boolean isEmpty() {
				if (data == ' ') {
					return true;
				}
				return false;
			}

			/**
			 * Returns true if this node is the root of the tree
			 * 
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
			 * 
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
			 * 
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
}
