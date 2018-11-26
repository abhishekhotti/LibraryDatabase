import java.sql.*;
import java.util.Scanner;

public class Books
{
	private static Scanner scan;
	
	public static Connection getconnection()
    {
       scan = new Scanner(System.in); // make it so that you can actually read the values and see what the database is doing/reacting to inputs
       String connectionString = "jdbc:mysql://localhost:3306/Books"; // the database I created in my system to hold all the tables
       Connection conn = null;
        
       System.out.println("!!! WARNING !!!");
       System.out.println("\nPLEASE MAKE SURE YOU HAVE CREATED A DATABASE CALLED 'Books' IN YOUR MySQL\n");
       System.out.println("!!! WARNING !!!");
       System.out.println("\nPress ENTER to start the login process. ");
       scan.nextLine();
       System.out.println("Please enter the username you have created in your machine and would like to use. EX: abhishekhotti");
       String userName = scan.nextLine();
       System.out.println("Please enter the password you used when you created the user: "+ userName +" in your machine. EX: SJSUDatabase");
       String pass = scan.nextLine();
       System.out.println("Your username and password are case sensitive with any user you have created in your machine \nand will be flagged if they do not match up exactly\n");
       boolean login = false;
       while(login == false)
       {
	        try
	        {
	            try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} 
	            conn = DriverManager.getConnection(connectionString, userName, pass);	// my user which I created in my machine
	            login = true;
	        }
	        catch (SQLException e)
	        {
	        	System.out.println("\nSORRY! Either your username or password was incorrect. Please try again\n");
	        	System.out.println("Please enter the username you have created in your machine and would like to use. EX: abhishekhotti");
	            userName = scan.nextLine();
	            System.out.println("Please enter the password you used when you created the user: "+ userName +" in your machine. EX: SJSUDatabase");
	            pass = scan.nextLine();
	        }
       }
       return conn;
    }
	
    public static void main(String[] args) throws SQLException
    {
        Connection conn = getconnection();	
        try
        {
            Statement stmt = conn.createStatement();	// statement to execute queries
            Tables createTable = new Tables(stmt);		// create the base tables with the statement being passed in
            createTable.createtable();	// insert the data into the sql server and stuff

            Insert insert = new Insert(stmt);	
            insert.fillTable();	// fill the tables up with values
            
            System.out.println("Would you like to start? Press hit ENTER to continue \n(Will need to hit ENTER at all pauses)");
            scan.nextLine();
            System.out.println("Table populated with random values\n");
            String query0 = "select * from authors;";
            ResultSet resultset0 = stmt.executeQuery(query0);
            while (resultset0.next())
            {
                int authorId = resultset0.getInt(1); // resultSet.length will equal 3 since there are 3 columns total in authors table
                String firstname = resultset0.getString(2);
                String lastname = resultset0.getString(3);
                System.out.printf("AuthorId: %-2d | Last Name: %-15s | First Name: %-15s\n", authorId, lastname, firstname);
            }
            // ================================================================================
            // Select all authors from the authors table. Order the information
            // alphabetically by the author’s last name and first name.
            System.out.println("\nWould you like to see the ordered table based on authors last name (Hit ENTER)");
            scan.nextLine();
            System.out.println("Table ordered with random values based on authors last name\n");
            String query1 = "select * from authors ORDER BY lastName ASC"; // since each last name will be generated randomly, no need to sort via first name
            ResultSet resultset = stmt.executeQuery(query1);
            while (resultset.next()) // resultSet.length will equal 3 since there are 3 columns total in authors table, it will already be sorted by the last name
            {
                int authorId = resultset.getInt(1); 
                String firstname = resultset.getString(2);
                String lastname = resultset.getString(3);
                System.out.printf("AuthorId: %-2d | Last Name: %-15s | First Name: %-15s\n", authorId, lastname, firstname);
            }
            
            System.out.println("==========================================================================================");
            
            // Select all publishers from the publishers table.
            System.out.println("\nHit ENTER to see all the publishers listed below");
            scan.nextLine();
            String query2 = "select * from publishers";
            ResultSet resultset2 = stmt.executeQuery(query2);
            while (resultset2.next())
            {
                int publisherId = resultset2.getInt(1);
                String publisherName = resultset2.getString(2);
                System.out.printf("Publisher ID: %3d Publisher Name: %10s\n", publisherId, publisherName);
            }
            System.out.println("==========================================================================================");
            // Select a specific publisher and list all books published by that
            // publisher. Include the title, year and ISBN number. Order the
            // information alphabetically by title
            int x = (int) (Math.random()*18+1);
            System.out.println("Using publisher number "+ x + ", listing all the books from that publisher (Hit ENTER)");
            scan.nextLine();
            String query3 = "Select title, isbn, year from titles where publisherId= " + x + " ORDER BY title ASC";
            ResultSet resultset3 = stmt.executeQuery(query3);
            while (resultset3.next())
            {
                String title = resultset3.getString(1);
                String isbn = resultset3.getString(2);
                String year = resultset3.getString(3);
                System.out.printf("Title: %15s | ISBN: %6s | Year: %4s\n", title, isbn, year);
            }
            

            System.out.println("==========================================================================================");

            // ==========================================================================================
            // Add new author
            System.out.println("Would you like to see how a new author is added to the list");
            System.out.println("Below is the base authors table (Hit ENTER)");
            scan.nextLine();
            String sub = "Select firstname, lastname from authors";
            ResultSet rs = stmt.executeQuery(sub);
            System.out.println();
            while (rs.next())
            {
                String lastName = rs.getString(2);
                String name = rs.getString(1);
                System.out.printf("First Name: %10s | Last Name: %10s\n", name, lastName);
            }
            System.out.println("\nGoing to add new author to the table now. (Hit ENTER)");
            scan.nextLine();
            String query4 = "insert into authors(authorId, firstname, lastname) values (25, 'Abhishek', 'Hotti') ";
            stmt.execute(query4);
            sub = "Select firstname, lastname from authors";
            rs = stmt.executeQuery(sub);
            while (rs.next())
            {
                String lastName = rs.getString(2);
                String name = rs.getString(1);
                System.out.printf("First Name: %10s | Last Name: %10s\n", name, lastName);
            }
            System.out.println("\n!TADA! Added a new author to the table");
            

            System.out.println("==========================================================================================");
            // ===========================================================================================
            // Edit/Update the existing information about an author
            System.out.println("Would you like to see how to updating an author tuple looks like (Hit ENTER)");
            scan.nextLine();
            x = (int)(Math.random()*18+1);
            sub = "Select * from authors WHERE authorid="+x;
            rs = stmt.executeQuery(sub);
            System.out.println("We will be updating the authors first name for authorID"+x+"\n");
            while (rs.next())
            {
                String lastName = rs.getString(3);
                String authID = rs.getString(1);
                String name = rs.getString(2);
                System.out.printf("Author ID: %3s | First Name: %10s | Last Name: %10s\n", authID, name, lastName);
            }
            System.out.println("\nHIT ENTER to change the first name with the authorID"+x);
            scan.nextLine();
            String query5 = "Update authors set firstname='Praneet', lastname = 'Singh' where authorid="+x;
            stmt.execute(query5);
            ResultSet rs2 = stmt.executeQuery(sub);
            while (rs2.next())
            {
            	 String lastName = rs2.getString(3);
                 String authID = rs2.getString(1);
                 String name = rs2.getString(2);
                System.out.printf("Author ID: %3s | First Name: %10s | Last Name: %10s\n", authID, name, lastName);
            }
            System.out.println("\n!TADA! The author at authorID" + x +" has been updated now (Hit ENTER)");
            
            System.out.println("==========================================================================================");
            scan.nextLine();
            // ==================================================================================================
            // Add a new title for an author
            System.out.println("Now we will show you how you can add a new title to an authors list of books");
            System.out.println("This is the base table for the titles table: (Hit ENTER)");
            scan.nextLine();
            String sub30 = "Select * from titles";
            ResultSet rs30 = stmt.executeQuery(sub30);
            while (rs30.next())
            {
                String titles, year;
                int edition, publisher, price, isbn;
                isbn = rs30.getInt(1);
                titles = rs30.getString(2);
                edition = rs30.getInt(3);
                year = rs30.getString(4);
                publisher = rs30.getInt(5);
                price = rs30.getInt(6);
                System.out.printf("ISBN: %2d | Title: %15s | Edition: %1d | Year: %4s | PublisherID: %2d | Price: %4d\n", isbn, titles, 
                		edition, year, publisher, price);
            }
            System.out.println("\nNow we will add a tuple to the table. Hit enter to add the title 'DBMS Textbook' to the table: (Hit ENTER)");
            scan.nextLine();
            String query6 = "Insert into titles(isbn, title, year, editionNumber, price) values (" +50 +", 'DBMS Textbook', 2000, "+(int)(Math.random()*3+1)+", 190)";
            stmt.execute(query6);
            String sub3 = "Select * from titles";
            ResultSet rs3 = stmt.executeQuery(sub3);
            while (rs3.next())
            {
                String titles, year;
                int edition, publisher, price, isbn;
                isbn = rs3.getInt(1);
                titles = rs3.getString(2);
                edition = rs3.getInt(3);
                year = rs3.getString(4);
                publisher = rs3.getInt(5);
                price = rs3.getInt(6);
                System.out.printf("ISBN: %2d | Title: %15s | Edition: %1d | Year: %4s | PublisherID: %2d | Price: %4d\n", isbn, titles, 
                		edition, year, publisher, price);
            }
            

            System.out.println("==========================================================================================");
            // ================================================================================================
            // Add new publisher
            System.out.println("We will be adding a publisher to the publisher table");
            System.out.println("This is the base publisher table (Hit ENTER)");
            scan.nextLine();
            String sub4 = "Select * from publishers";
            ResultSet rs4 = stmt.executeQuery(sub4);
            while (rs4.next())
            {
                int id = rs4.getInt(1);
                String name = rs4.getString(2);
                System.out.printf("ID: %2d | NAME: %10s\n", id, name);
            }
            System.out.println("\nThis is the publisher table after execution of adding the new publisher with publisherId = 24 (Hit ENTER)");
            scan.nextLine();
            String query7 = "Insert into publishers(publisherId, publishername) values(40,'Manpreet')";
            stmt.execute(query7);
            sub4 = "Select * from publishers";
            rs4 = stmt.executeQuery(sub4);
            while (rs4.next())
            {
                int id = rs4.getInt(1);
                String name = rs4.getString(2);
                System.out.printf("ID: %2d | NAME: %10s\n", id, name);

            }
            System.out.println("\n!TADA! Added a new publisher to the publisher table (Hit ENTER)");
            
            System.out.println("==========================================================================================");
            // ===========================================================================================
            // Edit/ Update the existing information about a publisher
            x = (int)(Math.random()*18+1);
            System.out.println("Now we will be updating a publisher name (Hit ENTER)");
            scan.nextLine();
            System.out.println("This is the base publishers tuple for publisher ID:"+x);
            sub4 = "Select * from publishers WHERE publisherID = "+x;
            rs4 = stmt.executeQuery(sub4);
            while (rs4.next())
            {
                int id = rs4.getInt(1);
                String name = rs4.getString(2);
                System.out.printf("ID: %2d | NAME:%12s\n", id, name);
            }
            scan.nextLine();
            System.out.println("To see the publisher after the publisher with ID # " +x +" has been updated (Hit ENTER)");
            scan.nextLine();
            String query8 = "Update publishers set publishername ='Sukvhir' where publisherid="+x;
            stmt.execute(query8);
            rs4 = stmt.executeQuery(sub4);
            while (rs4.next())
            {
                int id = rs4.getInt(1);
                String name = rs4.getString(2);
                System.out.printf("ID: %2d | NAME: %12s\n", id, name);
            }
            System.out.println("\nAll done. Run me again if want to double check.");


            stmt.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        } catch (Exception e)
        {
        }
        conn.close();
    }
}
