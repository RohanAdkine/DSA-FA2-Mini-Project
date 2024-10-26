let books = [ 
    "Introduction to Java", "Data Structures and Algorithms using C++", 
    "Database Management Systems", "Operating System", "C++ OOPS Concepts",
    "Java OOPS Concepts", "Science", "Maths", "Python Basics", "Data Science",
    "Microprocessor Architecture", "Statistical Data Analysis Using R",
    "Neural Network and Fuzzy Logic Design", "Android Development",
    "Data Structures and Algorithms using Java", "System Design"
];

let borrowRequests = [];
let returnedBooks = [];
let searchResultsDisplayed = false;

// Function to return a book
function returnBook() {
    const bookTitle = document.getElementById("bookTitle").value;
    if (!bookTitle.trim()) {
        output("Please enter a valid book title.");
        return;
    }
    returnedBooks.push(bookTitle);
    books.push(bookTitle);
    output("Book returned: " + bookTitle);
}

// Function to get the most recently returned book
function getMostRecentlyReturnedBook() {
    if (returnedBooks.length === 0) {
        output("No books returned recently.");
    } else {
        output("Most recently returned book: " + returnedBooks[returnedBooks.length - 1]);
    }
}

// Function to request to borrow a book
function requestToBorrowBook() {
    const bookTitle = document.getElementById("bookTitle").value;
    if (!bookTitle.trim()) {
        output("Please enter a valid book title.");
        return;
    }
    if (books.includes(bookTitle)) {
        borrowRequests.push(bookTitle);
        output("Borrow request added for: " + bookTitle);
    } else {
        output("Book not available: " + bookTitle);
    }
}

// Function to process the next borrow request
function processBorrowRequest() {
    if (borrowRequests.length === 0) {
        output("No borrow requests to process.");
    } else {
        const requestedBook = borrowRequests.shift(); // FIFO
        books = books.filter(book => book !== requestedBook); // Remove from available books
        output("Processing borrow request for: " + requestedBook);
    }
}

// Function to display all borrow requests
function displayBorrowRequests() {
    if (borrowRequests.length === 0) {
        output("No pending borrow requests.");
    } else {
        let requests = "Current borrow requests:\n" + borrowRequests.join("\n - ");
        output(requests);
    }
}

// Function to display all available books
function displayAllBooks() {
    if (books.length === 0) {
        output("No books available.");
        return;
    }

    document.getElementById("allBooksTitle").style.display = "block";
    document.getElementById("bookList").style.display = "block";
    document.getElementById("bookList").innerHTML = books.map(book => `<li>${book}</li>`).join("");
}

// Function to search for a book by title
function searchBook() {
    const query = document.getElementById("searchQuery").value;
    if (!query.trim()) {
        output("Please enter a valid search query.");
        return;
    }
    
    // Clear previous search results and reset flag
    searchResultsDisplayed = false;
    document.getElementById("searchResultList").innerHTML = ""; // Clear previous results
    document.getElementById("searchResultsTitle").style.display = "none"; // Hide title if no results
    
    const results = books.filter(book => book.toLowerCase().includes(query.toLowerCase()));
    if (results.length === 0) {
        output("No books found.");
    } else {
        searchResultsDisplayed = true;
        document.getElementById("searchResultsTitle").style.display = "block";
        document.getElementById("searchResultList").style.display = "block";
        document.getElementById("searchResultList").innerHTML = results.map(book => `<li>${book}</li>`).join("");
    }
}

// Helper function to display output
function output(message) {
    document.getElementById("output").innerText = message;
}
