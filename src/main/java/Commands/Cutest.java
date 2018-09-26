package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class Cutest extends ListenerAdapter {




    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "cutestgirl")) {
            if (!event.getMember().getUser().isBot()) {

                cute(event.getChannel());

            }


        }

    }

    public void cute(TextChannel channel){
        EmbedBuilder builder = new EmbedBuilder();
        String reason1 = ":heart:";
        String reason2 = "so cute :revolving_hearts:";
        builder.setTitle("The Cutest Girl");
        builder.setAuthor("Why Bambi is the cutest girl alive!", "https://cdn.discordapp.com/avatars/355881345538588675/8d652add8357646f92363214362dc29c.png?size=2048", "https://cdn.discordapp.com/avatars/355881345538588675/8d652add8357646f92363214362dc29c.png?size=2048");
        builder.setImage("https://cdn.discordapp.com/avatars/355881345538588675/8d652add8357646f92363214362dc29c.png?size=2048");
        builder.setColor(Color.decode("#eb4d4b"));
        builder.setDescription("The cutest girl alive is Bambi:");
        builder.addField("She is adorable!", reason2, false);
        builder.addField("She is a very kind and considerate person!", reason1, false);
        builder.setFooter("in conclusion, she's amazing.", "https://cdn.discordapp.com/avatars/355881345538588675/8d652add8357646f92363214362dc29c.png?size=2048");
        channel.sendMessage(builder.build()).queue();
    }
}

