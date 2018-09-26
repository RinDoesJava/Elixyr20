package ElixyrMain;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

public class BotListener implements EventListener {

    private CommandMap commandMap;

    public BotListener(CommandMap commandMap){
        this.commandMap = commandMap;
    }



    @Override
    public void onEvent(Event event) throws NullPointerException {
        if(event instanceof MessageReceivedEvent) onMessage((MessageReceivedEvent)event);
        else if(event instanceof GuildMemberJoinEvent) onGuildMemberJoin((GuildMemberJoinEvent) event);
        else if(event instanceof GuildMemberLeaveEvent) onGuildMemberLeave((GuildMemberLeaveEvent) event);
    }

    private void onMessage(MessageReceivedEvent event) throws NullPointerException{
        if(event.getAuthor().equals(event.getJDA().getSelfUser())) return;

        String message = event.getMessage().getContentRaw();
        if(message.startsWith(commandMap.getTag())){
            message = message.replaceFirst(commandMap.getTag(), "");
            if(commandMap.commandUser(event.getAuthor(), message, event.getMessage())){
            }
        }
    }


    private void onGuildMemberJoin(GuildMemberJoinEvent event){
        System.out.println("lol someone joined");
    }

    private void onGuildMemberLeave(GuildMemberLeaveEvent event){
        System.out.println("kek bye");
    }

}