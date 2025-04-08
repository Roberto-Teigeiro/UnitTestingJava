package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryTest {
    private Library library;
    private Book book1;
    private Book book2;
    private Patron patron;
    
    @BeforeEach
    public void setup() {
        library = new Library();
        book1 = new Book("1984", "George Orwell");
        book2 = new Book("Brave New World", "Aldous Huxley");
        patron = new Patron("Alice Smith");
        
        library.addBook(book1);
        library.addPatron(patron);
    }

    @Test
    public void testAddBook() {
        library.addBook(book2);
        assertTrue(library.listAvailableBooks().contains(book2));
    }
    
    // TC-LIB-001
    @Test
    public void testAddDuplicateBook() {
        int initialSize = library.listAvailableBooks().size();
        Book duplicateBook = new Book("1984", "George Orwell");
        
        library.addBook(duplicateBook);
        
        assertEquals(initialSize + 1, library.listAvailableBooks().size(),
            "Adding a book with the same title and author should still add it as we don't have duplicate detection");
        // Note: Current implementation allows duplicates. To prevent this, we'd need to modify Library.addBook()
    }
    
    // TC-LIB-002
    @Test
    public void testRemoveBook() {
        library.removeBook(book1);
        assertFalse(library.listAvailableBooks().contains(book1));
    }
    
    // TC-LIB-003
    @Test
    public void testCheckOutNonexistentBook() {
        Book nonExistentBook = new Book("Not in Library", "Unknown Author");
        boolean result = library.checkOutBook(patron, nonExistentBook, 7);
        assertFalse(result, "Checking out a nonexistent book should fail");
    }
  
}