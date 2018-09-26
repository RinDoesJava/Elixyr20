package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Clear extends ListenerAdapter {

        public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

            String[] args = event.getMessage().getContentRaw().split(" ");

            if (args[0].equalsIgnoreCase(Info.PREFIX + "purge")) {

        if ( event.getMember().hasPermission(Permission.ADMINISTRATOR) || ( event.getMember().hasPermission(Permission.MESSAGE_MANAGE))) {
                if (args.length <= 2) {
                    sendErrorMessage( event.getChannel(),  event.getMember());
                } else {
                     event.getMessage().delete().queue();
                    TextChannel target =  event.getMessage().getMentionedChannels().get(0);
                    purgeMessages(target, Integer.parseInt(args[2]));

                    if (args.length > 3) {
                        String reason = "";

                        for (int i = 3; i < args.length; i++) {
                            reason += args[i] + " ";
                        }
                        log( event.getMember(), args[2], reason,  event.getChannel(), target);

                    } else {

                        log( event.getMember(), args[2], "",  event.getChannel(), target);

                    }
                }
            } else {
                no( event.getChannel());

            }
        }
    }

    public void no(TextChannel channel) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("ERROR");
        builder.setColor(Color.decode("#e84118"));
        String error = "You do not have permission to use this command!";
        builder.addField("LACKING PERMISSION:", error, false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }





    public void sendErrorMessage(TextChannel channel, Member member) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Invalid usage!");
        builder.setAuthor(member.getUser().getName(), member.getUser().getAvatarUrl(), member.getUser().getAvatarUrl());
        builder.setColor(Color.decode("#e84118"));
        builder.setDescription("{} = Required, [] = optional");
        builder.addField("Proper usage: ely;purge {#channel} {num} [reason]", "", false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }

    public void log(Member clearer, String num, String reason, TextChannel incident, TextChannel cleared) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Clear Report");
        builder.setColor(Color.decode("#273c75"));
        builder.addField("Cleared Channel", cleared.getAsMention(), false);
        builder.addField("Number of Messages Purged", num, false);
        builder.addField("Purger", clearer.getAsMention(), false);
        builder.addField("Date", sdf.format(date), false);
        builder.addField("Time", stf.format(date), false);
        builder.addField("Reason", reason, false);
        incident.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
    }

    private void purgeMessages(TextChannel channel, int num) {
        MessageHistory history = new MessageHistory(channel);
        List<Message> msgs;

        msgs = history.retrievePast(num).complete();
        channel.deleteMessages(msgs).queue();

    }


}
