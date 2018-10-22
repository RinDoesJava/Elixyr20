package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class Images extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "nsfwhelp")) {
            if (!event.getMember().getUser().isBot()) {

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Help for NSFW");
                builder.setColor(Color.cyan);
                builder.addField("ely;danbooru", "get images from Danbooru!", false);
                builder.addField("ely;rule34", "get images from Rule34!", false);
                builder.addField("ely;e621", "get images from e621, you furry!", false);
                event.getChannel().sendMessage(builder.build()).queue();

            }
        }
    }
}
