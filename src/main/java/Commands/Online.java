package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

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
            event.getChannel().sendMessage("There are " + online + " users online, and there are " + event.getGuild().getMembers().size() + " Members in this server total").queue();


        }

    }


}
