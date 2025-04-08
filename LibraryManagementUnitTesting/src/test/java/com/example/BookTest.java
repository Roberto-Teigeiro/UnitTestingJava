package com.example;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {
    
    // TC-BOOK-001
    @Test
    public void testBookCreation() {
        Book book = new Book("Brave New World", "Aldous Huxley");
        
        assertEquals("Brave New World", book.getTitle());
        assertEquals("Aldous Huxley", book.getAuthor());
        assertFalse(book.isCheckedOut());
        assertNull(book.getDueDate());
    }
    
    // TC-BOOK-002
    @Test
    public void testCheckOut() {
        Book book = new Book("1984", "George Orwell");
        book.checkOut(7);
        
        assertTrue(book.isCheckedOut());
        assertEquals(LocalDate.now().plusDays(7), book.getDueDate());
    }
    

}