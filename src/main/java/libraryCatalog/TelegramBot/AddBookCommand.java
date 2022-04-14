package libraryCatalog.TelegramBot;

import libraryCatalog.models.Location;
import libraryCatalog.repoInterfaces.AuthorManagerInterface;
import libraryCatalog.repoInterfaces.LocationManagerInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.Optional;


public class AddBookCommand extends BotCommand {
    StatementClass statementClass = new StatementClass();
    ParseClass parseClass = new ParseClass();
    public AddBookCommand(String commandIdentifier, String description) throws SQLException {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        Long locationId;
        Long authorId;
        Long maxBookId;
        //Book
        String bookName= parseClass.bookNameParse(strings);
        maxBookId=statementClass.maxBookIdForBot();
        //ISBN
        String ISBN = strings[1];
        //Author
        String authorName = parseClass.authorNameParse(strings);
        authorId = statementClass.authorIdByName(authorName);

        //Location
        String locationName= parseClass.locationNameParse(strings);
        locationId = statementClass.locationIdByName(locationName);


        java.sql.Date sqlAddedDate
                = parseClass.addedDateParseToSqlDate(strings);
        java.sql.Date sqlPubDate
                = parseClass.pubDateParseToSqlDate(strings);
        java.sql.Date sqlModDate
                = parseClass.modDateParseToSqlDate(strings);

        SendMessage sendMessage = new SendMessage();
        Long chatId = chat.getId();
        sendMessage.setChatId(String.valueOf(chatId));
        try {
            statementClass.addBook(ISBN,sqlAddedDate,sqlModDate,sqlPubDate,bookName,locationId,authorId,maxBookId);
            sendMessage.setText("Книга успешно добавлена!");
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
