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
		return new SortedListForwardIterator<>();
	}

	@Override
	public boolean add(E obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		return currentSize;
	}

	@Override
	public boolean remove(E obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int removeAll(E obj) {
		// TODO Auto-generated method stub
		return 0;
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
		this.isValid(index);
		int currIdx = 0;
		Node<E> currNode = header.getNext();
		while(currIdx < index) {
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
		this.currentSize = 0;

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
		return new SortedListForwardIterator<>(index);
	}

	@Override
	public int firstIndex(E e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndex(E e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReverseIterator<E> reverseIterator() {
		return new SortedListReverseIterator<>();
	}

	@Override
	public ReverseIterator<E> reverseIterator(int index) {
		return new SortedListReverseIterator<>(index);
	}

	/**
	 * This method verifies that the given index is valid within the list
	 * that it will be worked on.
	 * @param index - the index that will be verified
	 * @throws IndexOutOfBoundsException when index is not valid within the list
	 */
	private void isValid(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index > this.size()) throw new IndexOutOfBoundsException("Number " + index + " is not a valid index.");
	}

	///////////////////////////////// INTERNAL NODE CLASS /////////////////////////////////
	/**
	 * Node used for the Doubly Linked List.
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
	@SuppressWarnings("hiding")
	public class SortedListForwardIterator<E extends Comparable<E>> implements Iterator<E> {
		private SortedList<E> list;
		private int start;
		private int current;

		/**
		 * This constructor starts traversing the list forwardly at the beginning.
		 */
		public SortedListForwardIterator() {
			this.start = 0;
			this.current = 0;
		}
		/**
		 * This constructor allows you to choose the starting index of the forward iterator.
		 * @param index - the initial index of your iterator
		 */
		public SortedListForwardIterator(int index) {
			this.start = index;
			this.current = index;
		}

		@Override
		public boolean hasNext() {
			return current != ((size() + start - 1) % size());
		}

		@Override
		public E next() {
			if(!this.hasNext()) throw new NoSuchElementException("There are no more elements");
			E etr = list.get(current % size());
			current = (current + 1) % size();
			return etr;
		}
	}

	///////////////////////////// INTERNAL REVERSE ITERATOR CLASS /////////////////////////////
	/**
	 * This class will traverse through the list moving in the reverse direction.
	 */
	@SuppressWarnings("hiding")
	public class SortedListReverseIterator<E extends Comparable<E>> implements ReverseIterator<E> {
		private SortedList<E> list;
		private int start;
		private int current;

		/**
		 * This constructor starts traversing the list reversely at the beginning if it.
		 */
		public SortedListReverseIterator() {
			this.start = 0;
			this.current = 0;
		}
		/**
		 * This constructor allows you to choose the starting index of the reverse iterator.
		 * @param index - the initial index of your iterator
		 */
		public SortedListReverseIterator(int index) {
			this.start = index;
			this.current = index;
		}

		@Override
		public boolean hasPrevious() {
			return current != ((size() + start + 1) % size());
		}

		@Override
		public E previous() {
			if(!this.hasPrevious()) throw new NoSuchElementException("There are no more elements");
			E etr = list.get(current % size());
			current = (current - 1) % size();
			return etr;
		}
	}
}
