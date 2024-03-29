package edu.uprm.ece.icom4035.list.junit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import edu.uprm.ece.icom4035.list.ReverseIterator;
import edu.uprm.ece.icom4035.list.SortedCircularDoublyLinkedList;

public class MyMFTest {

	// My Master's Final Test (there's absolutely no reason for you to think it means anything else)
	private SortedCircularDoublyLinkedList<String> L;

	@Before
	public void setUp() throws Exception {
		L = new SortedCircularDoublyLinkedList<String>();
	}

	@Test
	public void theMFTest() {
		
		// Chapter 1 - The beginning of Programming
		assertTrue(L.isEmpty());
		L.add("Juan");
		L.add("Heidy");
		assertTrue(L.first() == "Heidy");
		assertTrue(L.last() == "Juan");
		
		// Chapter 2 - Bienve implements Cloneable
		assertFalse(L.remove("Bienve"));
		L.add("Bienve");
		assertFalse(L.isEmpty());
		L.add("Bienve");
		L.add("Bienve");
		L.add("Bienve");
		L.add("Bienve");
		assertTrue(L.size() == 7);
		assertTrue(L.get(0) == "Bienve");
		assertTrue(L.get(1) == "Bienve");
		assertTrue(L.get(2) == "Bienve");
		assertTrue(L.get(3) == "Bienve");
		assertTrue(L.get(4) == "Bienve");
		assertTrue(L.remove("Bienve"));
		assertFalse(L.get(4) == "Bienve");
		assertTrue(L.contains("Bienve"));
		assertTrue(L.removeAll("Bienve") == 4);
		assertFalse(L.contains("Bienve"));
		assertTrue(L.add("Bienve"));
		
		// Chapter 3 - Pedrito implements Comparable
		L.add("");
		assertTrue(L.first() == "");
		L.add("Pedro");
		L.add("PedroA");
		L.add("PedroB");
		assertTrue(L.get(4) == "Pedro");
		assertTrue(L.get(5) == "PedroA");
		assertTrue(L.get(6) == "PedroB");
	
		// This only works with JUnit 4	
//		try {
//			L.get(L.size());
//			fail(); // Exception wasn't thrown properly
//		} catch (IndexOutOfBoundsException e) {
//			assertTrue(true); // Exception properly thrown
//		}
		
		// This only works with JUnit 5
		assertThrows(IndexOutOfBoundsException.class, () -> L.get(L.size()));
		
		
		L.add(" ");
		assertTrue(L.firstIndex(" ") == 1);
		L.add("  ");
		L.add("   ");
		assertTrue(L.lastIndex("  ") == 2);
		assertTrue(L.lastIndex("   ") == 3);
		
		assertTrue(L.remove("PedroA"));
		assertTrue(L.remove("PedroB"));
		L.remove(0);
		L.remove(0);
		L.remove(0);
		L.remove(0);
		assertFalse(L.contains("   "));
		
		// Chapter 4 - Manuel implements Iterable

		for(String s : L) {
			System.out.println(s);
		}
		
		System.out.println("");
		System.out.println("Printing using the Reverse Iterator:");
		for (ReverseIterator<String> iter = L.reverseIterator(); iter.hasPrevious(); ){
			System.out.println(iter.previous());
			if(!iter.hasPrevious()) {
				assertThrows(NoSuchElementException.class, () -> iter.previous());
			}
		}
		
		// Chapter 5 - The approval
		
		L.clear();
		assertTrue(L.size() == 0);
		
		System.out.println("");
		System.out.println("Project thoroughly tested (hopefully)!");
		
	}

}
