package edu.uprm.ece.icom4035.list;

import java.util.Iterator;

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
		// TODO Auto-generated method stub
		return null;
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
		isValid(index);
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
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReverseIterator<E> reverseIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void isValid(int index) throws IndexOutOfBoundsException {
		if(index < 0 || index >= this.size()) throw new IndexOutOfBoundsException("Number " + index + " is not a valid index.");
	}
	
	//////////////// INTERNAL NODE CLASS ////////////////
	public static class Node<E extends Comparable<E>> {
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
}
