package com.example;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PatronTest {
    
    // TC-PATRON-001
    @Test
    public void testPatronCreation() {
        Patron patron = new Patron("John Doe");
        
        assertEquals("John Doe", patron.getName());
        assertTrue(patron.getCheckedOutBooks().isEmpty());
    }
    
    // TC-PATRON-002
    @Test
    public void testCheckOutBook() {
        Patron patron = new Patron("Alice Smith");
        Book book = new Book("1984", "George Orwell");
        
        patron.checkOutBook(book);
        
        assertTrue(patron.getCheckedOutBooks().contains(book));
        assertEquals(1, patron.getCheckedOutBooks().size());
    }
    
 
    
}