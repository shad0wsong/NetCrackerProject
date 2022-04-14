package libraryCatalog.TelegramBot;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.IManCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.ManCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collection;

public class HelpCommand extends ManCommand {
    private static final String COMMAND_IDENTIFIER = "help";
    private static final String COMMAND_DESCRIPTION = "Показывает все команды. Нажмите /помощь [команда] для большей информации";
    private static final String EXTENDED_DESCRIPTION = "Эта команда отображает все команды бота.\n /помощь [команда] показывает информацию о конкретной команде";

    public static String getHelpText(IBotCommand... botCommands) {
        StringBuilder reply = new StringBuilder();
        IBotCommand[] var2 = botCommands;
        int var3 = botCommands.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            IBotCommand com = var2[var4];
            reply.append(com.toString()).append(System.lineSeparator()).append(System.lineSeparator());
        }

        return reply.toString();
    }

    public static String getHelpText(Collection<IBotCommand> botCommands) {
        return getHelpText((IBotCommand[])botCommands.toArray(new IBotCommand[botCommands.size()]));
    }

    public static String getHelpText(ICommandRegistry registry) {
        return getHelpText(registry.getRegisteredCommands());
    }

    public static String getManText(IBotCommand command) {
        return IManCommand.class.isInstance(command) ? getManText((IManCommand)command) : command.toString();
    }

    public static String getManText(IManCommand command) {
        return command.toMan();
    }

    public HelpCommand() {
        super("help", "Показывает все команды. Нажмите /help [command] для большей информации", "Эта команда отображает все команды бота.\n /help [command] показывает информацию о конкретной команде");
    }

    public HelpCommand(String commandIdentifier, String description, String extendedDescription) {
        super(commandIdentifier, description, extendedDescription);
    }

    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        if (ICommandRegistry.class.isInstance(absSender)) {
            ICommandRegistry registry = (ICommandRegistry)absSender;
            if (arguments.length > 0) {
                IBotCommand command = registry.getRegisteredCommand(arguments[0]);
                String reply = getManText(command);

                try {
                    absSender.execute(SendMessage.builder().chatId(chat.getId().toString()).text(reply).parseMode("HTML").build());
                } catch (TelegramApiException var10) {
                    var10.printStackTrace();
                }
            } else {
                String reply = getHelpText(registry);

                try {
                    absSender.execute(SendMessage.builder().chatId(chat.getId().toString()).text(reply).parseMode("HTML").build());
                } catch (TelegramApiException var9) {
                    var9.printStackTrace();
                }
            }
        }

    }
}
