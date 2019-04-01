
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class test {

    public static void main(String args[]){
        //recent book mean within past 7 days
        Scanner t=new Scanner(System.in);
        String temp;
        String y="";
//
//
////        books[noOfBooks-1].setPublisher(q.nextLine());
//        Date date2=new Date();
//        Scanner q=new Scanner(System.in);
//        System.out.println("Enter date of addition :");
//        String date=q.next();
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
//        try {
//            date2=simpleDateFormat.parse(date);
//        }catch(ParseException e){
//            e.printStackTrace();
//        }
//        System.out.println(date2);
//        Date date4=new Date();
//        System.out.println("Enter date of deletion  :");
//        String date3=q.next();
////        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
//        try {
//            date4=simpleDateFormat.parse(date3);
//        }catch(ParseException e){
//            e.printStackTrace();
//        }
//        System.out.println(date4);
//
//        System.out.println(date2.getTime()-date4.getTime());
////    System.out.println(date2.compareTo(date4));


        int noOfStudent,noOfFaculty;
        System.out.println("Enter no. of students: ");
        noOfStudent=t.nextInt();
        temp=t.nextLine();
        System.out.println("Enter no. of faculty: ");
        noOfFaculty=t.nextInt();
        temp=t.nextLine();
        Library library=new Library(noOfStudent,noOfFaculty);
        //library init with students and faculties init



        System.out.println("Library management system (Library capacity is 200 books)");
        int outer=1,inner=1;
//        String temp="";
        int alpha=0,beta=0;
//        Scanner t=new Scanner(System.in);
        while(outer==1) {

            System.out.println("Library Options:\n1.Be librarian\n2.Be borrower");
            alpha=t.nextInt();
            temp=t.nextLine();
            if(alpha==1) {
                inner=1;
                while (inner == 1) {
                    System.out.println("Menu Options:\n1.Add Book\n2.Delete Book\n3.View Book\n4.Issue Book\n5.View All Issued Books\n6.Return Book\n7.view delayed Books\n8.Send Notice");
                    beta=t.nextInt();
                    temp=t.nextLine();
                    switch(beta){
                        case 1: System.out.println("Add Book");
                                library.addBook();
                            break;

                        case 2:System.out.println("Delete Book");
                            library.deleteBook();
                            break;

                        case 3:System.out.println("View Book");
                            library.viewBook();
                            break;

                        case 4:System.out.println("Issue Book");
                            library.issueBook();
                            break;

                        case 5:System.out.println("View All Issued Books");
                            library.viewAllIssuedBooks();
                            break;

                        case 6:System.out.println("Return Book");
                            library.returnBook();
                            break;
                        case 7 :System.out.println("view delayed Books");
                            library.viewDelayedBooks();
                            break;
                        case 8:   System.out.println("send notice");
                            library.notice();
                            break;
                        default: System.out.println("Invalid input");
                    }
                    System.out.println("Continue being librarian?(1/0) : ");
                    inner=t.nextInt();
                    temp=t.nextLine();
                }
            }
            else{
                int type;


                    //student id is type i.e. index type-1 in students[] array

                    inner = 1;
                    while (inner == 1) {

                        //now give menu
                        System.out.println("Menu Options:\n1.View details of books by specific author\n2.View details of books by specific publisher\n3.View list of most recently books in library");
                        beta = t.nextInt();
                        temp = t.nextLine();
                        switch (beta) {
                            case 1:
                                    library.viewBookByAuthor();

                                break;

                            case 2:library.viewBookByPublisher();
                                break;

                            case 3:library.mostRecentBooks();
                                break;



                            default:
                                System.out.println("Invalid input");
                        }
                        System.out.println("Continue being borrower?(1/0) : ");
                        inner = t.nextInt();
                        temp = t.nextLine();
                    }

            }



            System.out.println("Continue in the library?(1/0) : ");
            outer=t.nextInt();
            temp=t.nextLine();
        }
    }
}
