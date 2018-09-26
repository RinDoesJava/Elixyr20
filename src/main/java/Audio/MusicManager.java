package Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MusicManager {

    private final AudioPlayerManager manager = new DefaultAudioPlayerManager();
    private final Map<String, MusicPlayer> players = new HashMap<>();

    public MusicManager() {
        AudioSourceManagers.registerRemoteSources(manager);
        AudioSourceManagers.registerLocalSource(manager);
    }

    public synchronized MusicPlayer getPlayer(Guild guild) {
        if (!players.containsKey(guild.getId()))
            players.put(guild.getId(), new MusicPlayer(manager.createPlayer(), guild));
        return players.get(guild.getId());
    }



    public void loadTrack(final TextChannel channel, final String source) {
        MusicPlayer player = getPlayer(channel.getGuild());

        channel.getGuild().getAudioManager().setSendingHandler(player.getAudioHandler());


        manager.loadItemOrdered(player, source, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Adding Songs");
                builder.setColor(Color.cyan);
                builder.addField("Song Name", audioTrack.getInfo().title, false);

                channel.sendMessage(builder.build()).queue();
                player.playTrack(audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("Adding Songs");
                builder.setColor(Color.CYAN);

                if (audioPlaylist.isSearchResult()) {
                    AudioTrack track = audioPlaylist.getTracks().get(0);
                    builder.addField("Song Name", "["+track.getInfo().title+"]"+"("+"https://youtube.com/watch?v="+track.getInfo().identifier+")", false);
                    builder.setThumbnail("https://img.youtube.com/vi/"+track.getInfo().identifier+"/mqdefault.jpg");
                    channel.sendMessage(builder.build()).queue();
                    player.playTrack(audioPlaylist.getTracks().get(0));

                } else {

                    for (int i = 0; i < audioPlaylist.getTracks().size() && i < 10; i++) {
                        if (audioPlaylist.isSearchResult()) {
                            AudioTrack audioTrack = audioPlaylist.getTracks().get(i);
                            builder.addField("Song Name", "["+audioTrack.getInfo().title+"]"+"("+"https://youtube.com/watch?v="+audioTrack.getInfo().identifier+")", false);
                            builder.setThumbnail("https://img.youtube.com/vi/"+audioTrack.getInfo().identifier+"/mqdefault.jpg");
                            channel.sendMessage(builder.build()).queue();
                            player.playTrack(audioPlaylist.getTracks().get(0));
                        } else {

                            AudioTrack track = audioPlaylist.getTracks().get(i);
                            builder.addField(" **->** ", "["+track.getInfo().title+"]"+"("+"https://youtube.com/watch?v="+track.getInfo().identifier+")", false);
                            player.playTrack(track);
                        }
                    }

                    channel.sendMessage(builder.build()).queue();
                }
            }

            @Override
            public void noMatches() {
                EmbedBuilder ebuilder = new EmbedBuilder();
                ebuilder.setTitle("ERROR");
                ebuilder.setColor(Color.red);
                ebuilder.addField("Failed to load track", "Reason: Couldn't find the track you are looking for!", false);


                channel.sendMessage(ebuilder.build()).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                EmbedBuilder ebuilder = new EmbedBuilder();
                ebuilder.setTitle("ERROR");
                ebuilder.setColor(Color.red);
                ebuilder.addField("Failed to load track", "Reason: " + exception.getMessage(), false);


                channel.sendMessage(ebuilder.build()).queue();
            }

        });
    }
}
