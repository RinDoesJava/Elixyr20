package Audio;

import ElixyrMain.Command;
import ElixyrMain.Command.ExecutorType;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;

import java.awt.*;


public class MusicCommand {

    private final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private final MusicManager manager = new MusicManager();


    @Command(name = "somebody", description = "Once told me", type = ExecutorType.USER)
    private void somebody(Guild guild, TextChannel textChannel, User user) {

        AudioSourceManagers.registerRemoteSources(playerManager);

        if (guild == null) return;


        if (!guild.getAudioManager().isConnected() && !guild.getAudioManager().isAttemptingToConnect()) {
            VoiceChannel voiceChannel = guild.getMember(user).getVoiceState().getChannel();
            if (voiceChannel == null) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("ERROR");
                builder.setColor(Color.red);
                builder.addField("Couldn't join", "You need to be in a voice chat to play a song", false);
                textChannel.sendMessage(builder.build()).queue();
                return;
            }


            guild.getAudioManager().openAudioConnection(voiceChannel);

        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("ONCE TOLD ME");
        builder.setColor(Color.red);
        builder.addField("I AIN'T", "THE SHARPEST TOOL IN THE SHED", false);
        textChannel.sendMessage(builder.build()).queue();

        String url = "ytsearch:" + "All star smash mouth";
        manager.loadTrack(textChannel, url);
    }


    @Command(name = "search", description = "Search youtube for a song!", type = ExecutorType.USER)
    private void search(Guild guild, TextChannel textChannel, User user, String command) {

        AudioSourceManagers.registerRemoteSources(playerManager);

        if (guild == null) return;


        if (!guild.getAudioManager().isConnected() && !guild.getAudioManager().isAttemptingToConnect()) {
            VoiceChannel voiceChannel = guild.getMember(user).getVoiceState().getChannel();
            if (voiceChannel == null) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("ERROR");
                builder.setColor(Color.red);
                builder.addField("Couldn't join", "You need to be in a voice chat to play a song", false);
                textChannel.sendMessage(builder.build()).queue();
                return;
            }


            guild.getAudioManager().openAudioConnection(voiceChannel);

        }

        String url = "ytsearch:" + command.replaceFirst("search", "");
        manager.loadTrack(textChannel, url);

    }


    @Command(name = "play", description = "Play music!", type = ExecutorType.USER)
    private void play(Guild guild, TextChannel textChannel, User user, String command) {

        if (guild == null) return;


        if (!guild.getAudioManager().isConnected() && !guild.getAudioManager().isAttemptingToConnect()) {
            VoiceChannel voiceChannel = guild.getMember(user).getVoiceState().getChannel();
            if (voiceChannel == null) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("ERROR");
                builder.setColor(Color.red);
                builder.addField("Couldn't join", "You need to be in a voice chat to play a song", false);
                textChannel.sendMessage(builder.build()).queue();
                return;
            }

            guild.getAudioManager().openAudioConnection(voiceChannel);

        }

        manager.loadTrack(textChannel, command.replaceFirst("play ", ""));
    }

    @Command(name = "skip", description = "skip the current song.", type = ExecutorType.USER)
    private void skip(Guild guild, TextChannel textChannel) {
        if (!guild.getAudioManager().isConnected() && !guild.getAudioManager().isAttemptingToConnect()) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("ERROR");
            builder.setColor(Color.red);
            builder.addField("Failed to skip", "There are no songs in the queue", false);
            textChannel.sendMessage(builder.build()).queue();
            return;
        }

        manager.getPlayer(guild).skipTrack();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Success");
        builder.setColor(Color.cyan);
        builder.addField("Skipped this track", "Loading next in line", false);
        textChannel.sendMessage(builder.build()).queue();
    }

    @Command(name = "clear", description = "clear the current queue", type = ExecutorType.USER)
    private void clear(TextChannel textChannel) {
        MusicPlayer player = manager.getPlayer(textChannel.getGuild());

        if (player.getListener().getTracks().isEmpty()) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("ERROR");
            builder.setColor(Color.red);
            builder.addField("No songs to clear", "", false);


            textChannel.sendMessage(builder.build()).queue();
            return;
        }

        player.getListener().getTracks().clear();
        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Success");
        builder.setColor(Color.cyan);
        builder.addField("Queue cleared", "Queue is now empty", false);


        textChannel.sendMessage(builder.build()).queue();
    }

    @Command(name = "stop", description = "stop the current song and leave the vc", type = ExecutorType.USER)
    private void stop(Guild guild, TextChannel textChannel) {
        MusicPlayer player = manager.getPlayer(textChannel.getGuild());

        if (player.getListener().getTracks().isEmpty()) {
            player.getListener().getTracks().clear();
            player.getAudioPlayer().stopTrack();
            guild.getAudioManager().closeAudioConnection();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Success");
            builder.setColor(Color.cyan);
            builder.addField("Stopped playback", "Leaving Channel", false);


            textChannel.sendMessage(builder.build()).queue();
            return;
        }

        player.getListener().getTracks().clear();
        player.getAudioPlayer().stopTrack();
        guild.getAudioManager().closeAudioConnection();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Success");
        builder.setColor(Color.cyan);
        builder.addField("Stopped playback", "Leaving Channel", false);


        textChannel.sendMessage(builder.build()).queue();

    }

    @Command(name = "playing", description = "Get the current track playing!", type = ExecutorType.USER)
    private void playing(AudioTrack audioTrack, TextChannel textChannel, Guild guild) {
        MusicPlayer player = manager.getPlayer((textChannel.getGuild()));

        if (player.getAudioPlayer().isPaused() || player.getAudioPlayer().getPlayingTrack() == null) {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("ERROR");
            builder.setColor(Color.red);
            builder.addField("Failed to load info", "Reason: Bot isn't playing anything!", false);


            textChannel.sendMessage(builder.build()).queue();
        } else {

            String title = player.getAudioPlayer().getPlayingTrack().getInfo().title;
            String duration = formatTiming(player.getAudioPlayer().getPlayingTrack().getDuration(), player.getAudioPlayer().getPlayingTrack().getDuration());
            String position = formatTiming(player.getAudioPlayer().getPlayingTrack().getPosition(), player.getAudioPlayer().getPlayingTrack().getDuration());
            String thumbnail = "https://img.youtube.com/vi/"+player.getAudioPlayer().getPlayingTrack().getIdentifier()+"/mqdefault.jpg";
            String link = "https://www.youtube.com/watch?v="+player.getAudioPlayer().getPlayingTrack().getIdentifier();


            EmbedBuilder builder = new EmbedBuilder();
            builder.setTitle("Now playing");
            builder.addField("Title", "["+title+"]"+"("+link+")", false);
            builder.addField("Duration", duration, false);
            builder.addField("Timestamp", position + "/" + duration, false);
            builder.setThumbnail(thumbnail);
            textChannel.sendMessage(builder.build()).queue();


        }
    }

    private static String formatTiming(long timing, long maximum) {
        timing = Math.min(timing, maximum) / 1000;

        long seconds = timing % 60;
        timing /= 60;
        long minutes = timing % 60;
        timing /= 60;
        long hours = timing;

        if (maximum >= 3600000L) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%d:%02d", minutes, seconds);
        }
    }

    @Command(name = "queue", description = "show the current queue!", type = ExecutorType.USER)
    private void queue(Guild guild, TextChannel channel, AudioPlaylist playlist) {

        MusicPlayer musicPlayer = manager.getPlayer((channel.getGuild()));
        EmbedBuilder builder = new EmbedBuilder();

        String thumbnail = "https://img.youtube.com/vi/"+musicPlayer.getAudioPlayer().getPlayingTrack().getIdentifier()+"/mqdefault.jpg";

        if (musicPlayer.getAudioPlayer().isPaused() || musicPlayer.getAudioPlayer().getPlayingTrack() == null) {
            builder.setTitle("ERROR");
            builder.setColor(Color.red);
            builder.addField("Failed to load info", "Reason: Bot isn't playing anything!", false);


            channel.sendMessage(builder.build()).queue();
        } else {

            StringBuilder trackList = new StringBuilder();
            for (AudioTrack track : manager.getPlayer(guild).getListener().getTracks()) {
                trackList.append(track.getInfo().title).append("\n");
                builder.setTitle("Queue List");
                builder.setColor(Color.cyan);
                builder.addField("Name", "["+track.getInfo().title+"]"+"("+"https://youtube.com/watch?v="+track.getInfo().identifier+")", false);
                builder.setFooter("Only showing top 5 to not make it too cluttered", "https://cdn.discordapp.com/avatars/487288677677006848/fcb580e0e5305d9bdea37122773fbcbb.png?size=2048");
            }


                channel.sendMessage(builder.build()).queue();

        }
    }
}
