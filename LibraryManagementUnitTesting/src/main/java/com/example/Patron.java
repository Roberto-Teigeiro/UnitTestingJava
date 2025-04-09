package com.example;

import java.util.ArrayList;  
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Patron implements Runnable {  
    private String name;  
    private List<Book> checkedOutBooks;
    private Library library;
    private Random random;
    private static final int MAX_BOOKS_TO_BORROW = 3;
    private static final int MAX_DELAY_MS = 2000;
  
    public Patron(String name, Library library) {  
        this.name = name;  
        this.checkedOutBooks = new ArrayList<>();
        this.library = library;
        this.random = new Random();
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public List<Book> getCheckedOutBooks() {  
        return checkedOutBooks;  
    }  
  
    public void checkOutBook(Book book) {  
        checkedOutBooks.add(book);  
    }  
  
    public void returnBook(Book book) {  
        checkedOutBooks.remove(book);  
    }  
  
    public boolean hasCheckedOutBook(Book book) {  
        return checkedOutBooks.contains(book);  
    }

    // Simulate a random delay to represent reading or browsing time
    private void simulateActivity() {
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(MAX_DELAY_MS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        System.out.println(name + " enters the library.");
        
        try {
            // First, browse the library
            simulateActivity();
            
            // Get available books (synchronized in the Library class)
            List<Book> availableBooks = library.listAvailableBooks();
            
            // Attempt to check out books if any are available
            if (!availableBooks.isEmpty()) {
                int booksToCheckout = Math.min(random.nextInt(MAX_BOOKS_TO_BORROW) + 1, availableBooks.size());
                
                for (int i = 0; i < booksToCheckout; i++) {
                    if (availableBooks.size() > i) {
                        Book book = availableBooks.get(i);
                        
                        // Try to check out the book (synchronized in the Library class)
                        if (library.checkOutBook(this, book, 14)) {
                            System.out.println(name + " borrowed: " + book.getTitle());
                            this.checkOutBook(book);
                            
                            // Simulate reading the book
                            simulateActivity();
                        }
                    }
                }
            }
            
            // Return all books after reading them
            List<Book> booksToReturn = new ArrayList<>(checkedOutBooks);
            for (Book book : booksToReturn) {
                // Simulate finishing reading
                simulateActivity();
                
                // Return the book (synchronized in the Library class)
                if (library.returnBook(this)) {
                    System.out.println(name + " returned: " + book.getTitle());
                    this.returnBook(book);
                }
            }
            
            System.out.println(name + " leaves the library.");
            
        } catch (Exception e) {
            System.out.println(name + " encountered an error: " + e.getMessage());
        }
    }
}