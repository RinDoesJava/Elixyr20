package Commands;

import ElixyrMain.Info;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;


public class UserInfo extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Info.PREFIX + "userinfo")) {
            if (!event.getMember().getUser().isBot()) {

                Member info = event.getMessage().getMentionedMembers().get(0);
                String pfp = info.getUser().getAvatarUrl().toLowerCase();
                boolean bot = info.getUser().isBot();
                String creation = String.valueOf(info.getUser().getCreationTime().toLocalDate());
                Guild guild = info.getGuild();
                String userid = info.getUser().getId();
                String jointime = String.valueOf(guild.getMemberById(userid).getJoinDate().toLocalDate());
                String name = info.getAsMention();
                String realname = event.getMessage().getMentionedMembers().get(0).getEffectiveName() + "#" + info.getUser().getDiscriminator();
                String status = String.valueOf(info.getOnlineStatus().toString().toLowerCase());
                String rich;
                String roles;




                if(info.getGame() == null){

                    rich = "Not playing anything";
                } else {
                    rich = info.getGame().getName();
                }
                    StringBuilder builder1 = new StringBuilder();
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setFooter(realname, info.getUser().getEffectiveAvatarUrl());
                    builder.setTitle("Userinfo");
                    builder.setColor(Color.CYAN);
                    builder.addField("User", name, true);
                    builder.addField("Online Status", status, false);
                    builder.addField("Is Bot", String.valueOf(bot), false);
                    builder.addField("Rich Presence", rich, false);


                    for(int i = 0; i < info.getRoles().size(); i++) {

                            Role role = info.getRoles().get(i);
                            roles = role.getName();

                            builder1.append(roles + ", ");
                            builder1.toString();


                    }
                    builder.addField("Roles", String.valueOf(builder1), false);
                    builder.addField("Creation Date", creation, false);
                    builder.addField("Joined Guild", jointime, false);
                    event.getChannel().sendMessage(builder.build()).queue();
            }
        }
    }
}
