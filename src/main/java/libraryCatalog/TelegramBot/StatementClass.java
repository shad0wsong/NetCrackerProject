package libraryCatalog.TelegramBot;



import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.*;

public class StatementClass {
    MyDBConnection myDBConnection = new MyDBConnection();
    Connection connection = myDBConnection.getConnection();
    public StatementClass() throws SQLException {
    }

    public  Long authorIdByName(String authorName){
        Long authorId= Long.valueOf(1);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT authorid FROM author WHERE name=?");
            preparedStatement.setString(1, authorName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                authorId=resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authorId;
    }

    public Long locationIdByName(String locationName){
        Long locationId =Long.valueOf(1);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT locationid FROM location WHERE name=?");
            preparedStatement.setString(1, locationName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                locationId=resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  locationId;
    }
    public Long bookIdByName(String bookName){
        Long bookId =Long.valueOf(1);
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT id FROM book WHERE name=?");
            preparedStatement.setString(1, bookName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookId=resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  bookId;
    }

    public Long maxBookIdForBot(){
        Long maxBookId = Long.valueOf(1);
        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT MAX(id) FROM book WHERE id>100000000");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                maxBookId=resultSet.getLong(1);
            }
            maxBookId++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  maxBookId;
    }
    public  void  addBook(String ISBN,java.sql.Date sqlAddedDate,java.sql.Date sqlModDate,java.sql.Date sqlPubDate,String bookName,Long locationId,Long authorId,Long maxBookId ){
        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO book(isbn,added_date,modification_date,name,publication_date,locationid,authorid,id) VALUES(?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, ISBN);
            preparedStatement.setDate(2, sqlAddedDate);
            preparedStatement.setDate(3, sqlModDate);
            preparedStatement.setString(4, bookName);
            preparedStatement.setDate(5, sqlPubDate);
            preparedStatement.setLong(6, locationId);
            preparedStatement.setLong(7, authorId);
            preparedStatement.setLong(8, maxBookId);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteBookById(Long id){
        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM book where id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateBookByID(String ISBN,java.sql.Date sqlAddedDate,java.sql.Date sqlModDate,java.sql.Date sqlPubDate,String bookName,Long locationId,Long authorId,Long bookId){
        try {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("Update book SET isbn=?,added_date=?,modification_date=?,name=?,publication_date=?,locationid=?,authorid=?,id=? WHERE id=?");
            preparedStatement.setString(1, ISBN);
            preparedStatement.setDate(2, sqlAddedDate);
            preparedStatement.setDate(3, sqlModDate);
            preparedStatement.setString(4, bookName);
            preparedStatement.setDate(5, sqlPubDate);
            preparedStatement.setLong(6, locationId);
            preparedStatement.setLong(7, authorId);
            preparedStatement.setLong(8, bookId);
            preparedStatement.setLong(9, bookId);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet showBooks() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM book");

        return resultSet;
    }

    public String locationNameById(Long id){
        String locationName=null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT name FROM location WHERE locationid=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                locationName=resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  locationName;
    }
    public String authorNameById(Long id){
        String authorName=null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT name FROM author WHERE authorid=?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                authorName=resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  authorName;
    }
    public ResultSet findBookByName(String bookName) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT * FROM book WHERE name=?");
        preparedStatement.setString(1, bookName);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;

    }


}
