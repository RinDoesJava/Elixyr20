package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PingPong extends ListenerAdapter {

        public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

            String[] args = event.getMessage().getContentRaw().split(" ");

            if (args[0].equalsIgnoreCase(Info.PREFIX + "ping")) {
                if (!event.getMember().getUser().isBot()) {




                event.getChannel().sendMessage("pong!").queue();


                }
            }
        }
}
