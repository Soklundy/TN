package soklundy.tn.dtoContent;

import com.google.gson.annotations.SerializedName;

/**
 * Created by soklundy on 1/24/2016.
 */
public class ListContent {

    @SerializedName("nid")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("image_url")
    private String imgUrl;

    @SerializedName("public_date")
    private String publicDate;

    @SerializedName("discription")
    private String discription;

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(String publicDate) {
        this.publicDate = publicDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
