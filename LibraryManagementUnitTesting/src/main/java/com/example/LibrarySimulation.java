package com.example;

public class LibrarySimulation {
    public static void main(String[] args) {
        Library library = new Library();
        
        library.addBook(new Book("Song of ice and fire", "George R R Martin"));
        library.addBook(new Book("Lord of the rings", " J. R. R. Tolkien "));
        library.addBook(new Book("Titanic", "Harper Lee"));
        library.addBook(new Book("Atomic Habits", "James Clear"));
        library.addBook(new Book("Animal Farm", "George Orwell"));
        
        int numberOfPatrons = 5;
        Thread[] patronThreads = new Thread[numberOfPatrons];
        
        for (int i = 0; i < numberOfPatrons; i++) {
            Patron patron = new Patron("Patron " + (i + 1), library);
            library.addPatron(patron);
            Thread thread = new Thread(patron);
            patronThreads[i] = thread;
            thread.start();
        }
        
        for (Thread thread : patronThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Library simulation completed.");
    }
}