package Audio;

import com.google.gdata.data.youtube.YouTubeMediaContent;

import java.util.List;

public class youtubeVideo {

    private List<String> thumbnails;
    private List<youtubeMedia> medias;
    private String webPlayerUrl;
    private String embeddedWebPlayerUrl;

    public List<String> getThumbnails() {
        return thumbnails;
    }
    public void setThumbnails(List<String> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public List<youtubeMedia> getMedias() {
        return medias;
    }
    public void setMedias(List<youtubeMedia> medias) {
        this.medias = medias;
    }

    public String getWebPlayerUrl() {
        return webPlayerUrl;
    }
    public void setWebPlayerUrl(String webPlayerUrl) {
        this.webPlayerUrl = webPlayerUrl;
    }

    public String getEmbeddedWebPlayerUrl() {
        return embeddedWebPlayerUrl;
    }
    public void setEmbeddedWebPlayerUrl(String embeddedWebPlayerUrl) {
        this.embeddedWebPlayerUrl = embeddedWebPlayerUrl;
    }

    public String retrieveHttpLocation() {
        if (medias==null || medias.isEmpty()) {
            return null;
        }
        for (youtubeMedia media : medias) {
            String location = media.getLocation();
            if (location.startsWith("http")) {
                return location;
            }
        }
        return null;
    }

}