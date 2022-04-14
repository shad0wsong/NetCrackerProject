package libraryCatalog.TelegramBot;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StartCommand extends BotCommand {
    StatementClass statementClass = new StatementClass();
    ParseClass parseClass = new ParseClass();
    public StartCommand(String commandIdentifier, String description) throws SQLException {
        super(commandIdentifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chat.getId()));
        message.setText("Привет,это бета версия бота для управления libraryCatalog! Чтобы узнать как пользоваться командами напиши /help");
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}