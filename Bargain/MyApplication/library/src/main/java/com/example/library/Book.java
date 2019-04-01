
import java.util.Date;

public class Book {

    String publisher, author, name;
    int borrowerId;
    int id;
    //1 for student 0 for faculty
    int borrowerType;
    boolean isIssued;
    Date dateOfIssue,dueDate,dateOfAddition;

    Book(){
        this.isIssued=false;
        this.borrowerId=-1;
        this.borrowerType=-1;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(int borrowerId) {
        this.borrowerId = borrowerId;
    }

    public int getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(int borrowerType) {
        this.borrowerType = borrowerType;
    }

    public boolean isIssued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDateOfAddition() {
        return dateOfAddition;
    }

    public void setDateOfAddition(Date dateOfAddition) {
        this.dateOfAddition = dateOfAddition;
    }
    public void displayDetails(){
        System.out.println("Book name : "+this.name);
        System.out.println("Book id : "+this.id);
        System.out.println("Book author : "+this.author);
        System.out.println("Book publisher : "+this.publisher);
        System.out.println("Date added : "+this.dateOfAddition.toString());
        if(this.isIssued){
            System.out.println("issued to (1student 0faculty): "+this.borrowerType);
            System.out.println("issued to id : "+this.borrowerId);
            System.out.println("Date of issue : "+this.dateOfIssue.toString());
            System.out.println("Date due : "+this.dueDate.toString());

        }

    }
}
