package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

public class Online extends ListenerAdapter {


    public void onGuildMessageReceived (GuildMessageReceivedEvent event){

        String[] args = event.getMessage().getContentRaw().split(" ");

        if(args[0].equalsIgnoreCase(Info.PREFIX + "online")){
            int online = 0;
            for(int i = 0; i < event.getGuild().getMembers().size(); i++){
                if(event.getGuild().getMembers().get(i).getOnlineStatus() == OnlineStatus.ONLINE || event.getGuild().getMembers().get(i).getOnlineStatus() == OnlineStatus.DO_NOT_DISTURB){
                    online++;
                }
            }

            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("User list");
            builder.setColor(Color.cyan);
            builder.addField("People who are online", String.valueOf(online), false);
            builder.addField("Total user count", String.valueOf(event.getGuild().getMembers().size()), false);
            event.getChannel().sendMessage(builder.build()).queue();


        }

    }


}
