import java.util.*;

class Node<T> {
    T data;
    Node<T> next;

    Node(T data) {
        this.data = data;
        this.next = null;
    }
}

class MyStack<T> {
    private Node<T> top;

    public MyStack() {
        top = null;
    }

    // Push an element onto the stack
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
    }

    // Pop an element from the stack
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        T data = top.data;
        top = top.next;
        return data;
    }

    // Peek at the top element without removing it
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        return top.data;
    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return top == null;
    }

    // Size of the stack
    public int size() {
        int size = 0;
        Node<T> current = top;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }
}

class MyQueue<T> {
    private Node<T> front, rear;

    public MyQueue() {
        front = rear = null;
    }

    // Enqueue an element at the end of the queue
    public void enqueue(T data) {
        Node<T> newNode = new Node<>(data);
        if (rear == null) {
            front = rear = newNode;
            return;
        }
        rear.next = newNode;
        rear = newNode;
    }

    // Dequeue an element from the front of the queue
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        T data = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        return data;
    }

    // Peek at the front element without removing it
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }
        return front.data;
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return front == null;
    }

    // Size of the queue
    public int size() {
        int size = 0;
        Node<T> current = front;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }
}

public class LibraryManagementSystem {
    private List<String> books;
    private MyQueue<String> borrowRequests; // Use custom Queue
    private MyStack<String> returnedBooks;  // Use custom Stack
    private boolean booksDisplayed = false;
    private boolean searchResultsDisplayed = false;

    public LibraryManagementSystem() {
        // Initialize the list of books, borrow requests (Queue), and returned books (Stack)
        books = new ArrayList<>(Arrays.asList(
            "Introduction to Java", "Data Structures and Algorithms using C++", 
            "Database Management Systems", "Operating System", "C++ OOPS Concepts",
            "Java OOPS Concepts", "Science", "Maths", "Python Basics", "Data Science",
            "Microprocessor Architecture", "Statistical Data Analysis Using R",
            "Neural Network and Fuzzy Logic Design", "Android Development",
            "Data Structures and Algorithms using Java", "System Design"
        ));
        borrowRequests = new MyQueue<>();  // Use custom Queue
        returnedBooks = new MyStack<>();    // Use custom Stack
    }

    // Function to return a book
    public void returnBook(String bookTitle) {
        if(bookTitle == null || bookTitle.trim().isEmpty()) {
            System.out.println("Please enter a valid book title.");
            return;
        }
        returnedBooks.push(bookTitle); // Add book to returned books stack
        books.add(bookTitle); // Add book back to the list of available books
        System.out.println("Book returned: " + bookTitle);
    }

    // Function to get the most recently returned book
    public void getMostRecentlyReturnedBook() {
        if (returnedBooks.isEmpty()) {
            System.out.println("No books returned recently.");
        } else {
            System.out.println("Most recently returned book: " + returnedBooks.peek());
        }
    }

    // Function to request to borrow a book
    public void requestToBorrowBook(String bookTitle) {
        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            System.out.println("Please enter a valid book title.");
            return;
        }
        if (books.contains(bookTitle)) {
            borrowRequests.enqueue(bookTitle); // Add to the borrow queue
            System.out.println("Borrow request added for: " + bookTitle);
        } else {
            System.out.println("Book not available: " + bookTitle);
        }
    }

    // Function to process the next borrow request
    public void processBorrowRequest() {
        if (borrowRequests.isEmpty()) {
            System.out.println("No borrow requests to process.");
        } else {
            String requestedBook = borrowRequests.dequeue(); // Remove the first request (FIFO)
            books.remove(requestedBook); // Remove book from available books
            System.out.println("Processing borrow request for: " + requestedBook);
        }
    }

    // Function to display all borrow requests
    public void displayBorrowRequests() {
        if (borrowRequests.isEmpty()) {
            System.out.println("No pending borrow requests.");
        } else {
            System.out.println("Current borrow requests: ");
            int size = borrowRequests.size();
            for (int i = 0; i < size; i++) {
                String request = borrowRequests.dequeue();
                System.out.println(" - " + request);
                borrowRequests.enqueue(request); // Re-enqueue to maintain the order
            }
        }
    }

    // Function to display all available books
    public void displayAllBooks() {
        if (booksDisplayed) {
            System.out.println("Books are already displayed.");
            return;
        }
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            booksDisplayed = true; // Set flag to true once the books are displayed
            System.out.println("Available books:");
            books.forEach(book -> System.out.println(" - " + book));
        }
    }

    // Function to search for a book by title
    public void searchBook(String query) {
        if (query == null || query.trim().isEmpty()) {
            System.out.println("Please enter a valid search query.");
            return;
        }
        if (searchResultsDisplayed) {
            System.out.println("Search results are already displayed.");
            return;
        }
        List<String> results = new ArrayList<>();
        for (String book : books) {
            if (book.toLowerCase().contains(query.toLowerCase())) {
                results.add(book);
            }
        }
        if (results.isEmpty()) {
            System.out.println("No books found.");
        } else {
            searchResultsDisplayed = true; // Set flag to true after displaying search results
            System.out.println("Search results:");
            results.forEach(book -> System.out.println(" - " + book));
        }
    }

    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();
        Scanner scanner = new Scanner(System.in);
        String command;

        System.out.println("Welcome to the PCCOE Library Management System!");
        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Return a Book");
            System.out.println("2. Get Most Recently Returned Book");
            System.out.println("3. Request to Borrow a Book");
            System.out.println("4. Process Borrow Request");
            System.out.println("5. Display Borrow Requests");
            System.out.println("6. Display All Books");
            System.out.println("7. Search for a Book");
            System.out.println("8. Exit");

            command = scanner.nextLine();

            switch (command) {
                case "1":
                    System.out.print("Enter book title to return: ");
                    String returnBookTitle = scanner.nextLine();
                    library.returnBook(returnBookTitle);
                    break;

                case "2":
                    library.getMostRecentlyReturnedBook();
                    break;

                case "3":
                    System.out.print("Enter book title to borrow: ");
                    String borrowBookTitle = scanner.nextLine();
                    library.requestToBorrowBook(borrowBookTitle);
                    break;

                case "4":
                    library.processBorrowRequest();
                    break;

                case "5":
                    library.displayBorrowRequests();
                    break;

                case "6":
                    library.displayAllBooks();
                    break;

                case "7":
                    System.out.print("Enter search query: ");
                    String searchQuery = scanner.nextLine();
                    library.searchBook(searchQuery);
                    break;

                case "8":
                    System.out.println("Exiting the system. Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please choose again.");
                    break;
            }
        }
    }
}
