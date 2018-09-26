package Commands;

import ElixyrMain.Command;
import ElixyrMain.CommandMap;
import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;


public class Help extends ListenerAdapter {


    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "help")) {
            if (!event.getMember().getUser().isBot()) {


                Help(event.getChannel());

            }

        }

    }

    public void Help(TextChannel channel) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Written by Rin#6969", "https://cdn.discordapp.com/avatars/280664130476572673/a_d71d8af5ae7d24797103206bc784cc04.gif?size=2048", "https://cdn.discordapp.com/avatars/280664130476572673/a_d71d8af5ae7d24797103206bc784cc04.gif?size=2048");
        builder.setTitle("Help Page", "https://discord.gg/Ar2Ckee");
        builder.setColor(Color.decode("#273c75"));
        String Online = " Show you who's online!";
        String Mute = " Mute someone!";
        String Cutest = " Tell you who the cutest person in the world is!";
        String Invite = " To invite me to your Server!";
        String Clear = "Clear a specific amount of messages!";
        String MusicHelp = "get help for the Music Commands, It's still in early development though!";
        String Ban = "Ban someone!";
        builder.addField("ely;online", Online, false);
        builder.addField("ely;mute", Mute, false);
        builder.addField("ely;purge", Clear, false);
        builder.addField("ely;ban", Ban, false);
        builder.addField("ely;cutestgirl", Cutest, false);
        builder.addField("ely;invite", Invite, false);
        builder.addField("ely;musichelp", MusicHelp, false);
        builder.setFooter("Click the title of this embed to join the development server!", "https://cdn.discordapp.com/avatars/487288677677006848/e84b37bee91759d2755c7237a35b0451.png?size=2048");
        channel.sendMessage(builder.build()).queue();
    }
}




