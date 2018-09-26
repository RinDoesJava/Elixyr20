package Audio;

public class youtubeMedia{

    private String location;
    private String type;

    public youtubeMedia(String location, String type){
        super();
        this.location = location;
        this.type = type;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }
}
