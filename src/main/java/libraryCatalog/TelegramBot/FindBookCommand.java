package libraryCatalog.TelegramBot;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FindBookCommand extends BotCommand {
    StatementClass statementClass = new StatementClass();
    ParseClass parseClass = new ParseClass();
    public FindBookCommand(String commandIdentifier, String description) throws SQLException {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String bookName=parseClass.bookNameParse(strings);
        ResultSet resultSet = null;
        try {
            resultSet=statementClass.findBookByName(bookName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
                while (resultSet.next()) {
                    SendMessage message = new SendMessage();
                    message.setChatId(String.valueOf(chat.getId()));
                    String text = "";
                    text += "Name:" + resultSet.getString(5) + "\n";
                    text += "ISBN:" + resultSet.getString(2) + "\n";
                    text += "Added to library Date:" + resultSet.getDate(3) + "\n";
                    text += "Modification Date:" + resultSet.getDate(4) + "\n";
                    text += "Publication Date:" + resultSet.getDate(6) + "\n";
                    String locationName = statementClass.locationNameById(resultSet.getLong(7));
                    text += "Location Name:" + locationName + "\n";
                    String authorName = statementClass.authorNameById(resultSet.getLong(8));
                    text += "Author Name:" + authorName + "\n";
                    message.setText(text);
                    absSender.execute(message);

            }
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
}