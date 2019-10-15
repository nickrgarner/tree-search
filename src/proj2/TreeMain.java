package proj2;

import java.util.Scanner;
import proj2.Stack;

public class TreeMain {

	/** Array containing the Pretraversal order from input */
	public static char pretrav[];

	/** Array containing the Posttraversal order from input */
	public static char posttrav[];

	public static void main(String[] args) {
		// Setup Scanner
		Scanner input = new Scanner(System.in);
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
	}

	/**
	 * Scans the first line of input and returns the number of nodes contained
	 * 
	 * @param input Scanner object parsing the tree input
	 * @return Number of nodes contained in the input
	 */
	public static String getInputString(Scanner input) {
		String inputString = "";
		String temp = input.next();
		if (temp.equals("\n")) {
			temp = input.next();
		}
		if (temp.equals(">") || temp.equals("<")) {
			temp = input.next();
			while (!temp.equals(".")) {
				if (!temp.equals(",")) {
					inputString += temp;
				}
				temp = input.next();
			}
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

	class Tree {

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
			// TODO Update this to manually traverse array and find root automatically
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
		 * @param prestart  Starting point of preorder traversal in pretrav array
		 * @param poststart Starting point of postorder traversal in postrav array
		 */
		public Node buildTree(int size, int prestart, int poststart) {
			if (size == 1) {
				return new Node(pretrav[prestart], null, null);
			} else {
				Node[] child = new Node[size - 1];
				int subtreeSize = poststart - (prestart + 1);
				char nextNode = pretrav[prestart + 1];
				child[1] = buildTree(subtreeSize, prestart + 1, TreeMain.getIndex(posttrav, nextNode));
				// Repeat for all children
				Node subRoot = new Node(pretrav[prestart], null, child);
				// Set parent pointers for all children
				for (int i = 0; i < child.length; i++) {
					try {
						subRoot.child[i].parent = subRoot;
					} catch (NullPointerException e) {
						// You've hit all children, break out of loop
						break;
					}
				}
				return subRoot;
			}
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
