package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class Say extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "quote")) {
            if (!event.getMember().getUser().isBot()) {

                Member info = event.getMember();
                String message = event.getMessage().getContentRaw().replace("ely;quote ", "");
                String pfp = info.getUser().getAvatarUrl().toLowerCase();
                String realname = event.getMember().getEffectiveName() + "#" + info.getUser().getDiscriminator();

                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor(realname);
                builder.setColor(Color.cyan);
                builder.addField("Quoted Message:", message, false);
                builder.setFooter("Quoted from " + realname, pfp);
                event.getChannel().sendMessage(builder.build()).queue();




            }
        }
    }
}
