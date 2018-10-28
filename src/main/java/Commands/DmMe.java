package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class DmMe extends ListenerAdapter {


    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "dm")) {
            if (!event.getMember().getUser().isBot()) {
                if (args.length == 1) {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setTitle("Invalid usage!");
                    builder.setColor(Color.decode("#e84118"));
                    builder.setDescription("{} = Required, [] = optional");
                    builder.addField("Proper usage: ely;dm {user id} [text]", "", false);
                    builder.addField("Extra", "You may also attach an image to your message!", false);
                    event.getChannel().sendMessage(builder.build()).complete().delete().queueAfter(15, TimeUnit.SECONDS);
                }

                if (args.length >= 2) {
                    String reason = "";
                    for (int i = 2; i < args.length; i++) {
                        reason += args[i] + " ";
                    }

                    User target = event.getJDA().getUserById(args[1]);

                    EmbedBuilder builder = new EmbedBuilder();
                    if(event.getMessage().getAttachments().isEmpty()){
                        builder.setImage(null);
                    } else {
                        String image = event.getMessage().getAttachments().get(0).getUrl();
                        builder.setImage(image);

                    }

                    builder.setAuthor(event.getMember().getEffectiveName() + "#" + event.getMember().getUser().getDiscriminator(), event.getMember().getUser().getEffectiveAvatarUrl(), event.getMember().getUser().getEffectiveAvatarUrl());
                    builder.setTitle("Message from " + event.getMember().getEffectiveName() + "!");
                    builder.setColor(Color.cyan);
                    builder.addField("Message", reason, false);
                    target.openPrivateChannel().queue(privateChannel -> {
                        privateChannel.sendMessage(builder.build()).queue();
                    });


                }
            }
        }
    }
}
