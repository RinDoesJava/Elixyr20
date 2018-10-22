package Images;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.kodehawa.lib.imageboards.DefaultImageBoards;
import net.kodehawa.lib.imageboards.entities.BoardImage;
import net.kodehawa.lib.imageboards.entities.exceptions.QueryParseException;

import java.awt.*;

public class e621 extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) throws QueryParseException {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "e621")) {
            if (!event.getMember().getUser().isBot()) {

                if(!event.getChannel().isNSFW()){
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("ERROR");
                    builder.setColor(Color.RED);
                    builder.addField("Reason", "This channel is not NSFW!", false);
                    event.getChannel().sendMessage(builder.build()).queue();
                } else {

                    String search = event.getMessage().getContentRaw().replaceFirst("dev;e621", "");

                    BoardImage image = DefaultImageBoards.E621.search(search).blocking().get(0);

                    if(image.getURL() == null){
                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("ERROR");
                        builder.setColor(Color.RED);
                        builder.addField("Reason", "Nothing found", false);
                        event.getChannel().sendMessage(builder.build()).queue();
                    } else {

                        EmbedBuilder builder = new EmbedBuilder();
                        builder.setTitle("Search Result");
                        builder.addField(search, "[Image not showing up? Click here!](" + image.getURL() + ")", false);
                        builder.setImage(image.getURL());
                        builder.setFooter("Rating: " + image.getRating().toString() + ". Dimensions " + String.valueOf(image.getWidth()) + "x" + String.valueOf(image.getHeight()), image.getURL());
                        event.getChannel().sendMessage(builder.build()).queue();
                    }
                }
            }
        }
    }
}
