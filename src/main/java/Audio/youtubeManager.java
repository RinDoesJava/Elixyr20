package Audio;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import com.google.gdata.client.youtube.YouTubeQuery;
import com.google.gdata.client.youtube.YouTubeService;
import com.google.gdata.data.media.mediarss.MediaThumbnail;
import com.google.gdata.data.youtube.VideoEntry;
import com.google.gdata.data.youtube.VideoFeed;
import com.google.gdata.data.youtube.YouTubeMediaContent;
import com.google.gdata.data.youtube.YouTubeMediaGroup;

public class youtubeManager {

    private static final String YOUTUBE_URL = "http://gdata.youtube.com/feeds/api/videos";
    private static final String YOUTUBE_EMBEDDED_URL = "http://www.youtube.com/v/";

    private String clientID;

    public youtubeManager(String clientID) {
        this.clientID = clientID;
    }

    public List<youtubeVideo> retrieveVideos(String textQuery, int maxResults, boolean filter, int timeout) throws Exception {

        YouTubeService service = new YouTubeService(clientID);
        service.setConnectTimeout(timeout); // millis
        YouTubeQuery query = new YouTubeQuery(new URL(YOUTUBE_URL));

        query.setOrderBy(YouTubeQuery.OrderBy.VIEW_COUNT);
        query.setFullTextQuery(textQuery);
        query.setSafeSearch(YouTubeQuery.SafeSearch.NONE);
        query.setMaxResults(maxResults);

        VideoFeed videoFeed = service.query(query, VideoFeed.class);
        List<VideoEntry> videos = videoFeed.getEntries();

        return convertVideos(videos);

    }

    private List<youtubeVideo> convertVideos(List<VideoEntry> videos) {

        List<youtubeVideo> youtubeVideosList = new LinkedList<youtubeVideo>();

        for (VideoEntry videoEntry : videos) {

            youtubeVideo ytv = new youtubeVideo();

            YouTubeMediaGroup mediaGroup = videoEntry.getMediaGroup();
            String webPlayerUrl = mediaGroup.getPlayer().getUrl();
            ytv.setWebPlayerUrl(webPlayerUrl);

            String query = "?v=";
            int index = webPlayerUrl.indexOf(query);

            String embeddedWebPlayerUrl = webPlayerUrl.substring(index+query.length());
            embeddedWebPlayerUrl = YOUTUBE_EMBEDDED_URL + embeddedWebPlayerUrl;
            ytv.setEmbeddedWebPlayerUrl(embeddedWebPlayerUrl);

            List<String> thumbnails = new LinkedList<String>();
            for (MediaThumbnail mediaThumbnail : mediaGroup.getThumbnails()) {
                thumbnails.add(mediaThumbnail.getUrl());
            }
            ytv.setThumbnails(thumbnails);

            List<youtubeMedia> medias = new LinkedList<youtubeMedia>();
            for (YouTubeMediaContent mediaContent : mediaGroup.getYouTubeContents()) {
                medias.add(new youtubeMedia(mediaContent.getUrl(), mediaContent.getType()));
            }
            ytv.setMedias(medias);

            youtubeVideosList.add(ytv);

        }

        return youtubeVideosList;

    }

}