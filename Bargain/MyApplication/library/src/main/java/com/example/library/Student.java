

public class Student {
    String name;

    int countOfBooks,id;
    int[] bookIssuedId;
    double totalFine;

    public Student(String name,int id ) {
        this.name = name;
        this.id=id;
        this.totalFine=0.0;
        this.countOfBooks = 0;
        this.bookIssuedId = new int[5];
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCountOfBooks() {
        return countOfBooks;
    }

    public void setCountOfBooks(int countOfBooks) {
        this.countOfBooks = countOfBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getBookIssuedId() {
        return bookIssuedId;
    }

    public void setBookIssuedId(int[] bookIssuedId) {
        this.bookIssuedId = bookIssuedId;
    }

    public double getTotalFine() {
        return totalFine;
    }

    public void setTotalFine(double totalFine) {
        this.totalFine = totalFine;
    }
    public void displayNotices(){

    }
    public void issuedBooks(){

    }
}

