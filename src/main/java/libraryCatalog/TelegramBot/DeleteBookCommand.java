package libraryCatalog.TelegramBot;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;

public class DeleteBookCommand extends BotCommand {
    StatementClass statementClass = new StatementClass();
    ParseClass parseClass = new ParseClass();
    public DeleteBookCommand(String commandIdentifier, String description) throws SQLException {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String bookName =parseClass.bookNameParse(strings);
        Long bookId= statementClass.bookIdByName(bookName);
        statementClass.deleteBookById(bookId);
        SendMessage sendMessage = new SendMessage();
        Long chatId = chat.getId();
        sendMessage.setChatId(String.valueOf(chatId));
        try {
            sendMessage.setText("Книга успешно удалена!");
            absSender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
