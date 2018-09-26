package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class Invite extends ListenerAdapter {


    public void onGuildMessageReceived (GuildMessageReceivedEvent event){

        String[] args1 = event.getMessage().getContentRaw().split(" ");

        if(args1[0].equalsIgnoreCase(Info.PREFIX + "invite")){
            if (!event.getMember().getUser().isBot()){

                inviteme(event.getChannel());
            }


        }

    }

    public void inviteme(TextChannel channel){
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode("#6ab04c"));
        builder.setAuthor("Invite me to your server!", "https://discordapp.com/api/oauth2/authorize?client_id=487288677677006848&permissions=8&scope=bot", "https://cdn.discordapp.com/avatars/487288677677006848/e84b37bee91759d2755c7237a35b0451.png?size=2048");
        builder.setTitle("Click this to invite me to your Server!", "https://discordapp.com/api/oauth2/authorize?client_id=487288677677006848&permissions=8&scope=bot");
        channel.sendMessage(builder.build()).queue();
    }
}


