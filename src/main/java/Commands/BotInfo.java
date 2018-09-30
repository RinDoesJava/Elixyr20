package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class BotInfo extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "info")) {
            if (!event.getMember().getUser().isBot()) {

                int guilds = event.getJDA().getGuilds().size();

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Info for ElixyrBot!");
                builder.setColor(Color.CYAN);
                builder.setAuthor("Bot written by Rin#6969");
                builder.addField("Bot Name", "Elixyr", false);
                builder.addField("Bot Prefix", Info.PREFIX, false);
                builder.addField("Number of Guilds", String.valueOf(guilds), false);
                builder.addField("Libraries", "JDA, JDA Utils, Lavaplayer", false);
                event.getChannel().sendMessage(builder.build()).queue();


            }

        }

    }


}
