

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Library {

    int noOfBooks;
    Book[] books;
    int noOfStudent, noOfFaculty;
    Student[] students;
    Faculty[] faculties;

    Library(int noOfStudent, int noOfFaculty) {
        this.noOfFaculty = noOfFaculty;
        this.noOfStudent = noOfStudent;
        this.noOfBooks = 0;
        Student[] students = new Student[noOfStudent];
        Faculty[] faculties = new Faculty[noOfFaculty];
        int i, k;
        String s;
        Scanner t = new Scanner(System.in);
        for (i = 0; i < noOfFaculty; i++) {
            System.out.println("Enter name of faculty " + i);
            s = t.nextLine();
            faculties[i] = new Faculty(s, i + 1);
        }
        for (i = 0; i < noOfStudent; i++) {
            System.out.println("Enter name of student " + i);
            s = t.nextLine();
            students[i] = new Student(s, i + 1);
        }

        //init books
        books = new Book[200];

    }

    public Faculty[] getFaculties() {
        return faculties;
    }

    public int getNoOfBooks() {
        return noOfBooks;
    }

    public int getNoOfFaculty() {
        return noOfFaculty;
    }

    public int getNoOfStudent() {
        return noOfStudent;
    }

    public Student[] getStudents() {
        return students;
    }

    public void setFaculties(Faculty[] faculties) {
        this.faculties = faculties;
    }

    public void setNoOfBooks(int noOfBooks) {
        this.noOfBooks = noOfBooks;
    }

    public void setNoOfFaculty(int noOfFaculty) {
        this.noOfFaculty = noOfFaculty;
    }

    public void setNoOfStudent(int noOfStudent) {
        this.noOfStudent = noOfStudent;
    }

    public void setStudents(Student[] students) {
        this.students = students;
    }


    public void addBook() {

        String temp;
        //no of books = 0, means 0 book 1 book means latest book is of index 0
        noOfBooks++;
        books[noOfBooks - 1] = new Book();
        Scanner q = new Scanner(System.in);

//        books[noOfBooks-1].setId(noOfBooks-1);
        System.out.println("Enter book id :");
        books[noOfBooks - 1].setId(q.nextInt());
        temp = q.nextLine();

        System.out.println("Enter book name :");
        books[noOfBooks - 1].setName(q.nextLine());

        System.out.println("Enter book author :");
        books[noOfBooks - 1].setAuthor(q.nextLine());

        System.out.println("Enter book publisher :");
        books[noOfBooks - 1].setPublisher(q.nextLine());

        System.out.println("Enter date of addition :");
        String date = q.next();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            books[noOfBooks - 1].setDateOfAddition(simpleDateFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        books[noOfBooks-1].setDateOfAddition();

    }

    public void deleteBook() {
        String temp;
        int y;
        System.out.println("Enter id of book to be deleted: ");
        Scanner w = new Scanner(System.in);
        y = w.nextInt();
        temp = w.nextLine();

        for (int i = 0; i < noOfBooks; i++) {
            if (books[i].id == y) {
                //shift books from j=i to j=
                for (int j = i; j < noOfBooks - 1; j++) {
                    books[j] = books[j + 1];
//                            books[i]=books[i+1];
                }
                break;
            }
        }
        noOfBooks--;
    }

    public void viewBook() {
        int y;
        String temp;
        System.out.println("Enter id of book to be viewed: ");
        Scanner w = new Scanner(System.in);
        y = w.nextInt();
        temp = w.nextLine();
        for (int i = 0; i <= noOfBooks - 1; i++) {
            if (books[i].id == y) {
                books[i].displayDetails();
                break;
            }

        }

    }

    public void issueBook() {
        String temp;
        int x, y, z;
        System.out.println("Enter id of book to be issued: ");
        Scanner w = new Scanner(System.in);
        y = w.nextInt();
        temp = w.nextLine();
        System.out.println("Enter TYPE OF BORROWER  1student 0faculty : ");
        z = w.nextInt();
        temp = w.nextLine();
        System.out.println("Enter id of borrower: ");
        x = w.nextInt();
        temp = w.nextLine();

        System.out.println("Enter date of issue :");
        String date = w.next();
        Date r = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            r = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < noOfBooks; i++) {
            if (books[i].id == y) {
                if (books[i].isIssued) {
                    System.out.println("book already issued");
                } else {

                    if (z == 1) {
                        //student
                        if (students[x].countOfBooks <= 5) {
                            students[x].bookIssuedId[students[x].countOfBooks] = books[i].id;
                            students[x].countOfBooks++;
                        } else {
                            System.out.println("book limit reached");
                            break;

                        }
                    } else {
                        //faculty
                        if (faculties[x].countOfBooks <= 5) {
                            faculties[x].bookIssuedId[faculties[x].countOfBooks] = books[i].id;
                            faculties[x].countOfBooks++;
                        } else {
                            System.out.println("book limit reached");
                            break;

                        }

                    }
                    books[i].setBorrowerId(x);
                    books[i].setBorrowerType(z);
                    books[i].setDateOfIssue(r);
                    if (z == 1) {
                        r.setTime(r.getTime() + 1296000000);

                        books[i].setDueDate(r);
                    } else {
                        long h = 1296000000;

                        r.setTime(r.getTime() + h);
                        r.setTime(r.getTime() + h);
                        books[i].setDueDate(r);
                    }

                    books[i].isIssued = true;

                }
                break;

            }
        }
        //setduedate

    }


    public void notice() {


        Scanner w = new Scanner(System.in);

        System.out.println("Enter todAYS date :");
        String date = w.next();
        Date r = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            r = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < noOfBooks; i++) {

                if (books[i].isIssued) {
                    if (books[i].borrowerType == 1) {
                        //add fine

                        if (books[i].dueDate.getTime() - r.getTime() > 1296000000) {
                            long h = books[i].dueDate.getTime() - r.getTime() - 1296000000;
                            //increase fine
                            if (h < 604800000) {
                                System.out.println("first notice to "+students[books[i].borrowerId].name);
                            } else {
                                System.out.println("additional notice to "+students[books[i].borrowerId].name);

                            }
                        }

                    } else {
                        //add fine


//                   long pp=2592000000;
                        if (books[i].dueDate.getTime() - r.getTime() - 1296000000 > 1296000000)
//                    if(books[i].dueDate.getTime()-r.getTime()-1296000000>pp)
                        {
                            long h = books[i].dueDate.getTime() - r.getTime() - 1296000000;
                            h -= 1296000000;
                            //increase fine
                            if (h < 604800000) {
                                System.out.println("first notice to "+faculties[books[i].borrowerId].name);
                            } else {
                                System.out.println("additional notice to "+faculties[books[i].borrowerId].name);

                            }
                        }


                    }
                    books[i].isIssued = false;
                    books[i].borrowerId = -1;
                    books[i].borrowerType = -1;
//            books[y-1].dateOfIssue
//            books[y-1].dueDate=
//            set date of issue and duedate
                }
                break;

        }
    }

    public void viewAllIssuedBooks(){
        for(int i=0;i<noOfBooks;i++){
            if(books[i].isIssued) {
                books[i].displayDetails();

            }
        }

    }
    public void viewDelayedBooks(){
        Scanner w=new Scanner(System.in);
        System.out.println("Enter todAYS date :");
        String date=w.next();
        Date r=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
        try {
            r=simpleDateFormat.parse(date);

        }catch(ParseException e){
            e.printStackTrace();
        }

        for(int i=0;i<noOfBooks;i++){
            if(books[i].isIssued) {
                if((books[i].borrowerType==0 && (books[i].dateOfIssue.getTime()-r.getTime()-1296000000>1296000000))){
                   long h=books[i].dateOfIssue.getTime()-r.getTime()-1296000000;
                   h-=1296000000;
                   h=h/86400000;
                    books[i].displayDetails();
                    System.out.println("\ndelayed days:  "+h);
                }else if ((books[i].borrowerType==1 && (books[i].dateOfIssue.getTime()-r.getTime()>1296000000))){
                    long h=books[i].dateOfIssue.getTime()-r.getTime()-1296000000;
                    h=h/86400000;
                    books[i].displayDetails();
                    System.out.println("\ndelayed days:  "+h);
                }
            }
        }
    }


    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public void viewBookByAuthor(){
System.out.println("enter author: ");
Scanner s=new Scanner(System.in);
String p=s.nextLine();
        for(int i=0;i<noOfBooks;i++){
            if(books[i].author.compareToIgnoreCase(p)==0)
                books[i].displayDetails();
        }

    }

    public void viewBookByPublisher(){


        System.out.println("enter publisher: ");
        Scanner s=new Scanner(System.in);
        String p=s.nextLine();
        for(int i=0;i<noOfBooks;i++){
            if(books[i].publisher.compareToIgnoreCase(p)==0)
                books[i].displayDetails();
        }

    }
    public void mostRecentBooks(){

    Scanner w=new Scanner(System.in);
        System.out.println("Enter todAYS date :");
        String date=w.next();
        Date r=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
        try {
            r=simpleDateFormat.parse(date);

            for(int i=0;i<noOfBooks;i++){
                if(books[i].dateOfAddition.getTime()-r.getTime()<604800001)
                    books[i].displayDetails();
            }
        }catch(ParseException e){
            e.printStackTrace();
        }

    }
}
