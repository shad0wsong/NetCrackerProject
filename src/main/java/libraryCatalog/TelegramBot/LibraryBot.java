package libraryCatalog.TelegramBot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.SQLException;


public class LibraryBot extends TelegramLongPollingCommandBot {
    private final String LIBRARY_BOT_NAME;
    private final String LIBRARY_BOT_TOKEN;


    public LibraryBot() throws SQLException {
        super();
        this.LIBRARY_BOT_NAME = System.getenv("LIBRARY_BOT_NAME");
        this.LIBRARY_BOT_TOKEN = System.getenv("LIBRARY_BOT_TOKEN");;

        register(new StartCommand("start", "Запускает бота"));
        register(new ShowBooksCommand("showbooks", "Показать все книги"));
        register(new AddBookCommand("addbook","Добавляет книгу.\n1)Имя книги 2)ISBN 3)Имя автора 4)Название библиотеки 5)Дата публикации 6)Дата добавления 7)Дата изменения\nПараметры пишутся строго в этом порядке через пробел.Если в параметре несколько слов,они пишутся слитно,каждое слово с большой буквы.\n Пример:/addbook ПролетаяНадГнездомКукушки ISBN ТолкинДжон БиблиотекаКультуры 2020-10-11 2013-4-1 2019-1-20"));
        register(new DeleteBookCommand("deletebook", "Удалить книгу по имени.Если в имени несколько слов,они пишутся слитно ,каждое с большой буквы"));
        register(new FindBookCommand("findbook", "Найти книгу по имени.Если в имени несколько слов,они пишутся слитно ,каждое с большой буквы"));
        register(new HelpCommand());
    }
    @Override
    public String getBotUsername() {
        return LIBRARY_BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return LIBRARY_BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        SendMessage sendMessage = new SendMessage();
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);
        sendMessage.setText("Извините,"+userName+",я вас не понимаю. Напишите /help если вам нужна помощь по командам");
        sendMessage.setChatId(String.valueOf(chatId));
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }
    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    private void setAnswer(Long chatId, String userName, String text) throws TelegramApiException {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            SendMessage answerError = new SendMessage();
            answer.setText("Что-то пошло не так,попробуйте позже, " +userName);
            answer.setChatId(chatId.toString());
            execute(answerError);
        }
    }
}
