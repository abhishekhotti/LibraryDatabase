
import java.sql.*;
import java.util.Random;

public class Tables
{

    // Statement and connection from Java.sql to execute the tables
    private Statement stmt;
    // Initalizing the statement and connection with our localhost
    public Tables(Statement stmt)
    {
        this.stmt = stmt;
    }

    // Function to create the four table provided
    public void createtable()
    {
        // Creating table Author with primary key equaling authorID
        String authors = "CREATE TABLE authors(authorID integer not null, firstName varchar(200), lastName varchar(200), PRIMARY KEY(authorID))";
        
        // Creating the authorISBN table which is the main relationship between the titles and authors table
        String authorISBN = "CREATE TABLE authorISBN(authorID INTEGER, isbn INTEGER, FOREIGN KEY(authorID) REFERENCES authors(authorID)) "; 
        //this.authorID references authors(authorID) since it is the many side of many to one relationship. 
        
        // Creating table Publishers with publishedID being primary key, so cannot be null
        String publishers = "CREATE TABLE publishers(publisherID integer not NULL, publisherName char(100), PRIMARY KEY (publisherID))";
        
        // Creating table Titles
        String titles = "CREATE TABLE titles(isbn INTEGER, title VARCHAR(100), editionNumber integer, year CHAR(4) NOT NULL, " 
        + " publisherID integer default 1, price INTEGER, PRIMARY KEY (isbn))";
        // Creating table AuthorISBN
        // Creating table titles before authorISBN so that foreign key constraint
        // can be applied to authorISBN
        // or else error will be produced
        
        String drop = "drop table if exists authorISBN;";
        String drop1 = "drop table if exists authors;";
        String drop2 = "drop table if exists titles;";
        String drop3 = "drop table if exists publishers;";
        try
        {
            // Executes the given tables to add tables to Database
        	stmt.executeUpdate(drop);
        	stmt.executeUpdate(drop1);
        	stmt.executeUpdate(drop2);
        	stmt.executeUpdate(drop3);
            stmt.executeUpdate(authors);
            stmt.executeUpdate(publishers);
            stmt.executeUpdate(titles);
            stmt.executeUpdate(authorISBN);
        } catch (SQLException se)
        {
            // Catches if there are exceptions raised by SQL
            se.printStackTrace();
        } catch (Exception e)
        {
            // Catches any Java Exceptions
            e.printStackTrace();
        }
    }

}
