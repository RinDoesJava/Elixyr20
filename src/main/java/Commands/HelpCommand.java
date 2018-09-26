package Commands;

import java.awt.Color;

import ElixyrMain.Command;
import ElixyrMain.Command.ExecutorType;
import ElixyrMain.CommandMap;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

public class HelpCommand {

    private CommandMap commandMap;

    public HelpCommand(CommandMap commandMap) {
        this.commandMap = commandMap;

    }


    @Command(name = "musichelp", type = ExecutorType.USER, description = "List of Music Commands")
    private void musichelp(User user, MessageChannel channel, Guild guild) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("List of Music Commands");
        builder.setColor(Color.CYAN);

        for (SimpleCommand command : commandMap.getCommands()) {
            if (command.getExecutorType() == ExecutorType.CONSOLE) continue;

            if (guild != null && command.getPower() > commandMap.getPowerUser(guild, user)) continue;

            builder.addField(command.getName(), command.getDescription(), false);
        }

        channel.sendMessage(builder.build()).queue();

    }

}
