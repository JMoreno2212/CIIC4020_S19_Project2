package edu.uprm.ece.icom4035.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedCircularDoublyLinkedList<E extends Comparable<E>> implements SortedList<E> {
	private Node<E> header;
	private int currentSize;

	public SortedCircularDoublyLinkedList() {
		this.header = new Node<E>();
		header.setPrev(header);
		header.setNext(header);
		this.currentSize = 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new SortedListForwardIterator();
	}

	@Override
	public boolean add(E obj) {
		Node<E> curr = header.getNext();
		if(currentSize > 0 && obj.compareTo(curr.getElement()) <= 0) {
			addNodeBetween(obj, header, curr);
		} else {
			while(curr.getNext() != header) {
				if(obj.compareTo(curr.getNext().getElement()) <= 0) break;
				curr = curr.getNext();
			}
			addNodeBetween(obj, curr, curr.getNext());
		}
		currentSize++;
		return true;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean remove(E obj) {
		Node<E> curr = header.getNext();
		while(curr != null) {
			if(curr.getElement().equals(obj)) {
				curr.getPrev().setNext(curr.getNext());
				curr.getNext().setPrev(curr.getPrev());
				currentSize--;
				return true;
			}
			curr = curr.getNext();
		}
		return false;
	}

	@Override
	public boolean remove(int index) {
		int validIdx = isValid(index);
		int currIdx = 0;
		Node<E> curr = header.getNext();
		while(currIdx < validIdx) {
			curr = curr.getNext();
			currIdx++;
		}
		curr.getPrev().setNext(curr.getNext());
		curr.getNext().setPrev(curr.getPrev());
		curr.clear();
		currentSize--;
		return true;
	}

	@Override
	public int removeAll(E obj) {
		int removed = 0;
		Node<E> curr = header.getNext();
		while(curr != header) {
			if(curr.getElement().equals(obj)) {
				curr.getPrev().setNext(curr.getNext());
				curr.getNext().setPrev(curr.getPrev());
				removed++;
			}
			curr = curr.getNext();
		}
		currentSize -= removed;
		return removed;
	}

	@Override
	public E first() {
		return header.getNext().getElement();
	}

	@Override
	public E last() {
		return header.getPrev().getElement();
	}

	@Override
	public E get(int index) {
		int validIdx = isValid(index);
		int currIdx = 0;
		Node<E> currNode = header.getNext();
		while(currIdx < validIdx) {
			currNode = currNode.getNext();
			currIdx++;
		}
		return currNode.getElement();
	}

	@Override
	public void clear() {
		Node<E> curr = header.getNext();
		while(curr != header) { // Header is also the tail
			curr = curr.getNext();
			curr.getPrev().clear();
		}
		header.setNext(header);
		header.setPrev(header);
		currentSize = 0;

	}

	@Override
	public boolean contains(E e) {
		Node<E> curr = header.getNext();
		while(curr != header) {
			if(curr.getElement().equals(e)) return true;
		}
		return false;
	}

	@Override
	public boolean isEmpty() {
		return currentSize == 0;
	}

	@Override
	public Iterator<E> iterator(int index) {
		return new SortedListForwardIterator(index);
	}

	@Override
	public int firstIndex(E e) {
		int idx = 0;
		for(E element : this) {
			if(element.equals(e)) return idx;
			idx++;
		}
		return -1;
	}

	@Override
	public int lastIndex(E e) {
		int idxToReturn = -1;
		int idx = 0;
		for(E element : this) {
			if(element.equals(e)) idxToReturn = idx;
			idx++;
		}
		return idxToReturn;
	}

	@Override
	public ReverseIterator<E> reverseIterator() {
		return new SortedListReverseIterator();
	}

	@Override
	public ReverseIterator<E> reverseIterator(int index) {
		return new SortedListReverseIterator(index);
	}

	/**
	 * This method verifies that the given index is valid within the list
	 * that it will be worked on.
	 * @param index - the index that will be verified
	 * @throws IndexOutOfBoundsException when index is not valid within the list
	 */
	private int isValid(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index > this.size()) throw new IndexOutOfBoundsException("Number " + index + " is not a valid index.");
		return index;
	}

	/**
	 * This method allows you to locate a Node<E> within the list.
	 * @param index - The index of the Node you want
	 * @return The Node<E> at the specified index
	 */
	private Node<E> getNode(int index) {
		int validIdx = isValid(index);
		int currIdx = 0;
		Node<E> currNode = header.getNext();
		while(currIdx < validIdx) {
			currNode = currNode.getNext();
			currIdx++;
		}
		return currNode;
	}

	/**
	 * This method adds a Node with the specified object in between the two specified Nodes.
	 * @param obj - The element of the new Node to be added
	 * @param previous - The previous Node of the new one
	 * @param next - The following Node of the new one
	 */
	private void addNodeBetween(E obj, Node<E> previous, Node<E> next) {
		Node<E> newNode = new Node<>(obj, previous, next);
		previous.setNext(newNode);
		next.setPrev(newNode);
	}

	///////////////////////////////// INTERNAL NODE CLASS /////////////////////////////////
	/**
	 * Node used for the Sorted Doubly Linked List.
	 * @param <E> - the type of data stored in the node
	 */
	public static class Node<E> {
		E element;
		Node<E> prev, next;

		Node(){
			element = null;
			prev = next = null;
		}

		Node(E element){
			this.element = element;
			prev = next = null;
		}

		Node(E element, Node<E> prev, Node<E> next){
			this.element = element;
			this.prev = prev;
			this.next = next;
		}

		//Helper function to clear Node Data
		private void clear() {
			element = null;
			prev = next = null;
		}

		//Getters
		public E getElement() {return element;}
		public Node<E> getPrev() {return prev;}
		public Node<E> getNext() {return next;}

		//Setters
		public void setPrev(Node<E> newPrev) {this.prev = newPrev;}
		public void setNext(Node<E> newNext) {this.next = newNext;}
	}

	///////////////////////////// INTERNAL FORWARD ITERATOR CLASS /////////////////////////////
	/**
	 * This class will traverse through the list moving in the forward direction.
	 */

	public class SortedListForwardIterator implements Iterator<E> {
		private Node<E> start;
		private Node<E> current;

		/**
		 * This iterator starts traversing the list forwardly at the beginning.
		 */
		public SortedListForwardIterator() {
			this.start = header;
			this.current = header.getNext();
		}
		/**
		 * This iterator allows you to choose the starting index of the forward iterator.
		 * @param index - the initial index of your iterator
		 */
		public SortedListForwardIterator(int index) {
			this.start = getNode(index).getPrev();
			this.current = getNode(index);
		}

		@Override
		public boolean hasNext() {
			if(isEmpty()) return false;
			return current != start;
		}

		@Override
		public E next() {
			if(!this.hasNext()) throw new NoSuchElementException("There are no more elements");
			current = current.getNext();
			return current.getPrev().getElement();
		}
	}

	///////////////////////////// INTERNAL REVERSE ITERATOR CLASS /////////////////////////////
	/**
	 * This class will traverse through the list moving in the reverse direction.
	 */

	public class SortedListReverseIterator implements ReverseIterator<E> {
		private Node<E> start;
		private Node<E> current;

		/**
		 * This iterator starts traversing the list forwardly at the beginning.
		 */
		public SortedListReverseIterator() {
			this.start = header;
			this.current = header.getPrev();
		}
		/**
		 * This iterator allows you to choose the starting index of the forward iterator.
		 * @param index - the initial index of your iterator
		 */
		public SortedListReverseIterator(int index) {
			this.start = getNode(index).getNext();
			this.current = getNode(index);
		}

		@Override
		public boolean hasPrevious() {
			if(isEmpty()) return false;
			return current != start;
		}

		@Override
		public E previous() {
			if(!this.hasPrevious()) throw new NoSuchElementException("There are no more elements");
			current = current.getPrev();
			return current.getNext().getElement();
		}
	}
}
