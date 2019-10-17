package proj2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TreeMain {

	/** Array containing the Pretraversal order from input */
	public static char pretrav[];

	/** Array containing the Posttraversal order from input */
	public static char posttrav[];

	/** Queue to hold nodes in level order traversal for printing */
	public static Queue<Tree.Node> nodeQueue;

	public static void main(String[] args) {
		// Setup BufferedReader for input
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

		// Create string of relationship queries, print answers
		String queries = getQueryString(input);
		tree1.printRelationships(queries);

		// Create level order queue and print
		nodeQueue = treeMain.new Queue<Tree.Node>();
//		System.out.println("Level-Order Traversal:");
		tree1.getRoot().levelOrder();
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
	 * Parses the input file past the traversal orders to convert all relationship
	 * queries into a single string of letters
	 * 
	 * @param input BufferedReader object parsing the input file
	 * @return String containing letters of all the relationship queries in the
	 *         input
	 */
	public static String getQueryString(BufferedReader input) {
		String queryString = "";
		try {
			char symbol = ' ';
			int charNumber = 0;
			while (charNumber != -1) {
				symbol = (char) charNumber;
				while (!Character.isLetter(symbol) && charNumber != -1) {
					symbol = (char) charNumber;
					charNumber = input.read();
				}
				if (Character.isLetter(symbol)) {
					queryString += symbol;
				}
				charNumber = input.read();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return queryString;
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
		while (postindex < postindex + size) {
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
		 * Loops through the string of relationship queries and prints the relationship
		 * for each node pair
		 * 
		 * @param queryString The string of letters representing the nodes to compare
		 *                    for relationships
		 */
		public void printRelationships(String queryString) {
			for (int i = 0; i < queryString.length(); i += 2) {
				System.out.println(this.getRelationship(queryString.charAt(i), queryString.charAt(i + 1)));
			}
		}

		/**
		 * Takes two Node char values and returns a string representing their
		 * relationship to each other. Resets relationship markers before returning.
		 * 
		 * @param char1 Char value of node 1
		 * @param char2 Char value of node 2
		 * @return String representing the relationship between the two nodes
		 */
		public String getRelationship(char char1, char char2) {
			Node commonAncestor = null;

			// Get pointers to target nodes, mark node1 ancestry chain
			Node node1 = this.lookup(char1);
			Node node2 = this.lookup(char2);
			node1.markAncestry();

			// Pointers to current node in each ancestry chain
			Node current1 = node1;
			Node current2 = node2;

			// Move through node2's ancestry chain to find common ancestor
			while (current2.getParent() != null && current2.getMark() != true) {
				current2 = current2.getParent();
			}
			commonAncestor = current2;
//			while (current1.getParent() != null) {
//				while (current2.getParent() != null) {
//					if (current2.getData() == current1.getData()) {
//						// This is the lowest common ancestor
//						commonAncestor = current2;
//						break;
//					}
//					current2 = current2.getParent();
//				}
//				if (current2.getData() == current1.getData()) {
//					// This is the lowest common ancestor
//					break;
//				}
//				current1 = current1.getParent();
//			}

			// Count marks to common ancestor
			int marks1 = 0;
			int marks2 = 0;
//			if (node1.getParent() == null) {
//				// node1 is tree root
//				while (current2.getParent() != null) {
//					current2 = current2.getParent();
//					marks2++;
//				}
//			} else {
//				current1 = node1;
//				current2 = node2;
//				while (current1.getData() != commonAncestor.getData()) {
//					current1 = current1.getParent();
//					marks1++;
//				}
//				while (current2.getData() != commonAncestor.getData()) {
//					current2 = current2.getParent();
//					marks2++;
//				}
//			}
			current1 = node1;
			current2 = node2;
			
			while (current1.getData() != commonAncestor.getData()) {
				current1 = current1.getParent();
				marks1++;
			}
			while (current2.getData() != commonAncestor.getData()) {
				current2 = current2.getParent();
				marks2++;
			}
			
			// Reset relationship markers
			node1.resetMarks();
			node2.resetMarks();
			
			// Determine relationship from mark counts and print
			if (marks1 == 0) {
				if (marks2 == 0) {
					return char1 + " is " + char2 + ".";
				} else if (marks2 == 1) {
					return char1 + " is " + char2 + "'s parent.";
				} else if (marks2 == 2) {
					return char1 + " is " + char2 + "'s grandparent.";
				} else if (marks2 == 3) {
					return char1 + " is " + char2 + "'s great-grandparent.";
				} else if (marks2 > 3) {
					String output = char1 + " is " + char2 + "'s ";
					while (marks2 >= 3) {
						output += "great-";
						marks2--;
					}
					output += "grandparent.";
					return output;
				}
			} else if (marks1 == 1) {
				if (marks2 == 0) {
					return char1 + " is " + char2 + "'s child.";
				} else if (marks2 == 1) {
					return char1 + " is " + char2 + "'s sibling.";
				} else if (marks2 == 2) {
					return char1 + " is " + char2 + "'s aunt/uncle.";
				} else if (marks2 >= 2) {
					String output = char1 + " is " + char2 + "'s ";
					while (marks2 >= 3) {
						output += "great-";
						marks2--;
					}
					output += "aunt/uncle.";
					return output;
				}
//			} else if (marks1 == 2) {
//				if (marks2 == 0) {
//					return char1 + " is " + char2 + "'s grandchild.";
//				} else if (marks2 == 1) {
//					return char1 + " is " + char2 + "'s niece/nephew.";
//				}
			} else if (marks1 >= 2) {
				if (marks1 == 2 && marks2 == 0) {
					return char1 + " is " + char2 + "'s grandchild.";
				} else if (marks1 == 2 && marks2 == 1) {
					return char1 + " is " + char2 + "'s niece/nephew.";
				} else if (marks2 == 1) {
					String output = char1 + " is " + char2 + "'s ";
					while (marks1 >= 2) {
						output += "great-";
						marks1--;
					}
					output += "niece/nephew.";
					return output;
				} else if (marks2 >= 2) {
					return char1 + " is " + char2 + "'s " + (Integer.min(marks1, marks2) - 1) + "th cousin "
							+ Math.abs(marks1 - marks2) + " times removed.";
				}
			} else if (marks1 >= 3) {
				if (marks2 == 0) {
					String output = char1 + " is " + char2 + "'s ";
					while (marks1 >= 3) {
						output += "great-";
						marks1--;
					}
					output += "niece/nephew.";
					return output;
				}
			}

			return "";
		}

		/**
		 * Performs a pre-order traversal of the tree to find the Node containing the
		 * given data. Marks nodes in the target's ancestry chain for determining
		 * relationships.
		 * 
		 * @param data The Node value to lookup
		 * @return Node containing the given data parameter.
		 */
		public Node lookup(char data) {
			String lineage = this.getLineage(data);

			Node current = this.getRoot();
//			current.setMark(true);
			int index = lineage.length() - 1;
			while (current.getData() != data) {
				if (current.getData() == lineage.charAt(index)) {
					index--;
				}
				for (int i = 0; i < current.getChildren().length; i++) {
					if (current.getChildren()[i].getData() == lineage.charAt(index)) {
						current = current.getChildren()[i];
//						current.setMark(true);
						break;
					}
				}
			}
			return current;
		}

		/**
		 * Returns a string representation of a node's lineage up to the root, for
		 * lookup traversal
		 * 
		 * @param data Char value of the node you want the lineage for
		 * @return String representation of node's lineage, starting at target node and
		 *         ending at root of tree
		 */
		public String getLineage(char data) {
			char rootChar = this.getRoot().getData();
			if (data == rootChar) {
				return "" + rootChar;
			}
			String lineage = "";
			lineage += data;

			int preindex = getIndex(pretrav, data);
			int postindex = getIndex(posttrav, data);
			char parent = ' ';
			// Keep looping through ancestors until hitting root
			while (parent != rootChar) {
				// Loop through posttrav and compare to pretrav backwards to find parent
				for (int i = postindex + 1; i < posttrav.length; i++) {
					for (int j = preindex - 1; j >= 0; j--) {
						if (pretrav[j] == posttrav[i]) {
							// It's the parent
							parent = posttrav[i];
							break;
						}
					}
					if (posttrav[i] == parent) {
						break;
					}
				}
				lineage += parent;
				preindex = getIndex(pretrav, parent);
				postindex = getIndex(posttrav, parent);
			}
			return lineage;
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
				char data = pretrav[prestart];
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
						postindex++;
					}
					// Build child subtree then move to next child
					child[i] = buildTree(subtreeSize, prestart, poststart);
					prestart += subtreeSize;
					poststart += subtreeSize;
				}
				// Make root
				Node root = new Node(data);
				root.setChildren(child);
				// Set child parent pointers
				for (int j = 0; j < root.getChildren().length; j++) {
					root.getChildren()[j].setParent(root);
				}
				return root;
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
			public Node[] getChildren() {
				return this.child;
			}

			/**
			 * Returns true if any of this node's children match the given char value
			 * 
			 * @param data Value to search this node's children for
			 * @return True if any children match the given char
			 */
			public boolean childrenContains(char data) {
				if (this.getChildren() != null) {
					for (int i = 0; i < this.getChildren().length; i++) {
						if (this.getChildren()[i].getData() == data) {
							return true;
						}
					}
				}
				return false;
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
			 * Returns a pointer to this node's parent.
			 * 
			 * @return Pointer to this node's parent.
			 */
			public Node getParent() {
				return this.parent;
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

			/**
			 * Returns this node's mark flag as a boolean value
			 * 
			 * @return Boolean value for this node's mark
			 */
			public boolean getMark() {
				return this.mark;
			}

			public char getData() {
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

			/**
			 * Traverses upwards through this node's ancestry chain and sets all mark flags
			 * to true for use in determining relationships to other nodes.
			 */
			public void markAncestry() {
				Node current = this;
				current.setMark(true);
				while (current.getParent() != null) {
					current = current.getParent();
					current.setMark(true);
				}
			}

			/**
			 * Traverses upwards through this node's ancestry chain and resets all
			 * relationship marks back to false.
			 */
			public void resetMarks() {
				Node current = this;
				current.setMark(false);
				while (current.getParent() != null) {
					current = current.getParent();
					current.setMark(false);
				}
			}

			/**
			 * Prints the level-order traversal of this tree
			 */
			public void levelOrder() {
				nodeQueue.enqueue(this);
				while (!nodeQueue.isEmpty()) {
					Node q = nodeQueue.dequeue();
					System.out.print(q.getData() + ", ");
					if (q.getChildren() != null) {
						for (int i = 0; i < q.getChildren().length; i++) {
							nodeQueue.enqueue(q.getChildren()[i]);
						}
					}
				}
			}
		}
	}

	/**
	 * Class defines state and behavior for a linked-list based Queue data
	 * structure. To be used for printing a tree's level order traversal.
	 * 
	 * @author Nick Garner, nrgarner
	 *
	 * @param <E> Generic data type
	 */
	public class Queue<E> {

		/** Node at the front of the list */
		private Node front;
		/** Number of items in the queue */
		private int size;

		/**
		 * Creates an empty queue with a null front node and a size of 0
		 */
		public Queue() {
			front = null;
			size = 0;
		}

		/**
		 * Add a new node with the given value to the end of the queue
		 * 
		 * @param data Value to create the new node with
		 */
		public void enqueue(E data) {
			if (front == null) {
				front = new Node(data);
			} else {
				Node current = front;
				while (current.next != null) {
					current = current.next;
				}
				current.next = new Node(data);
			}
			size++;
		}

		/**
		 * Removes the node at the front of the queue and returns its value
		 * 
		 * @return Value in the removed front node of the queue
		 */
		public E dequeue() {
			if (front == null || size == 0) {
				throw new IllegalArgumentException("Queue is empty");
			} else {
				E temp = front.data;
				front = front.next;
				size--;
				return temp;
			}
		}

		/**
		 * Returns number of items in the queue
		 * 
		 * @return Number of items in the queue
		 */
		public int size() {
			return size;
		}

		/**
		 * Returns true if the queue is empty
		 * 
		 * @return True if queue is empty, false otherwise
		 */
		public boolean isEmpty() {
			return size == 0;
		}

		/**
		 * Private class defining state and constructors for Nodes to be used in the
		 * linked Queue
		 * 
		 * @author Nick Garner, nrgarner
		 *
		 */
		private class Node {

			/** Value to hold in the node */
			private E data;
			/** Pointer to the next node in the Queue */
			private Node next;

			/**
			 * Constructs a new node with the given value and a null next pointer
			 * 
			 * @param data Value to construct the new node with
			 */
			public Node(E data) {
				this(data, null);
			}

			/**
			 * Constructs a new node with the given value and next pointer
			 * 
			 * @param data Value to construct the node with
			 * @param next Pointer to the next node in the queue
			 */
			public Node(E data, Node next) {
				this.data = data;
				this.next = next;
			}
		}
	}
}
