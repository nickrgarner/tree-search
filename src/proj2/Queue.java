package proj2;

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
