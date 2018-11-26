
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class Insert
{
    private Statement stmt;
    public Insert(Statement stmt)
    {
        this.stmt = stmt;
    }
    public Insert()
    {

    }
    public void fillTable()
    {
        try
        {
            // Randomly generate words for authors and the such
            String[] firstName = generateRandomWords(19);
            String[] lastName = generateRandomWords(19);
            String[] titles = generateRandomWords(19);
            String[] publisherName = generateRandomWords(19);
            
            try
            {
                for (int i = 1; i < 19; i++)
                {
                    // Inserting values in relation Authors=
                    String authors = "INSERT INTO AUTHORS(authorID, firstname, lastname) values("
                            + i + ", '" + firstName[i] + "', '" + lastName[i]
                            + "' )";
                    // Inserting values in relation Titles
                    String title = "INSERT INTO TITLES(ISBN, TITLE, EDITIONNUMBER, YEAR, PUBLISHERID, PRICE) VALUES ("+i+" ,' " 
                    		+ titles[i] + " ' , " + (int)(Math.random()*3+1) + " , " + (int)(Math.random()*68+1950) + " , " + i + " , " 
                    		+ (int)(Math.random()*200+50) + ") ";
                    String publisher = "INSERT INTO PUBLISHERS(publisherId, publisherName) Values("
                            + i + ", '" + publisherName[i] + "') ";
                    String authorIsbn = "INSERT INTO authorIsbn(authorId, Isbn) values("+ i + " , " + i + " )";
                    stmt.execute(authors);
                    stmt.execute(authorIsbn);
                    stmt.execute(publisher);
                    stmt.execute(title);

                }
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
            // creating more titles to insert into the titles table, since an author/publisher can write/publish more than one book
            titles = generateRandomWords(25);
            try {
            	for(int i = 0; i < titles.length; i++)
            	{
            
            		int x = (int) (Math.random()*18+1);
            		String title = "INSERT INTO TITLES(ISBN, TITLE, EDITIONNUMBER, YEAR, PUBLISHERID, PRICE) VALUES ("
                    + (19+i) + " ,' " + titles[i] + " ' , " + (int)(Math.random()*3+1)
                    + " , " + (int)(Math.random()*68+1950) + " , " + x + " , " + (int)(Math.random()*200+50)+ ") ";
            		
            		x = (int)(Math.random()*18+1);
            		String authorIsbn = "INSERT INTO authorIsbn(authorId, Isbn) values("
                            + x + " , " + (19+i) + " )";
            		
                    stmt.execute(authorIsbn);
            		stmt.execute(title);
            	}
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    // Generate Random words to populate the tables
    public static String[] generateRandomWords(int numberOfWords)
    {
        String[] randomStrings = new String[numberOfWords];
        Random random = new Random();
        for (int i = 0; i < numberOfWords; i++)
        {
            char[] word = new char[random.nextInt(8) + 3]; // words of length 3
            // through 10
            for (int j = 0; j < word.length; j++)
            {
                word[j] = (char) ('a' + random.nextInt(26));
            }
            randomStrings[i] = new String(word);
        }
        return randomStrings;
    }
}
