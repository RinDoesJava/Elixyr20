package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Ban extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split(" ");
        if (args[0].equalsIgnoreCase(Info.PREFIX + "ban")) {
            if (event.getMember().hasPermission(Permission.ADMINISTRATOR) || (event.getMember().hasPermission(Permission.BAN_MEMBERS))) {
                if (args.length <= 1) {
                    sendErrorMessage(event.getChannel(), event.getMember());
                } else {
                    Member target = event.getMessage().getMentionedMembers().get(0);
                    GuildController controller = event.getGuild().getController();

                    if (args.length >= 3) {
                        String reason = "";
                        for (int i = 2; i < args.length; i++) {
                            reason += args[i] + " ";
                        }
                        log(target, event.getMember(), reason, event.getChannel());
                        controller.ban(target, 0, reason).queue();
                    } else {
                        log(target, event.getMember(), "", event.getChannel());
                        controller.ban(target, 0, "").queue();
                    }
                }
            }
            else{
                no(event.getChannel());

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
        builder.addField("Proper usage: ely;ban {@user} [reason]", "", false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);


    }

    public void log(Member muted, Member muter, String reason, TextChannel channel) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Ban Report");
        builder.setColor(Color.decode("#273c75"));
        builder.addField("Banned User", muted.getAsMention(), false);
        builder.addField("Banner", muter.getAsMention(), false);
        builder.addField("Date", sdf.format(date), false);
        builder.addField("Time", stf.format(date), false);
        builder.addField("Reason", reason, false);
        channel.sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);


    }



}