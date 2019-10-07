package proj2;

public class Stack <E> {
	
	private Node top;
	private int size;
	
	public Stack() {
		top = null;
		size = 0;
	}
	
	public void push(E data) {
		top = new Node (data, top);
		size++;
	}
	
	public E pop() {
		E temp = top.data;
		top = top.next;
		size--;
		return temp;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	private class Node {
		private E data;
		private Node next;
		
		public Node (E data) {
			this(data, null);
		}
		
		public Node (E data, Node next) {
			this.data = data;
			this.next = next;
		}
	}
}
