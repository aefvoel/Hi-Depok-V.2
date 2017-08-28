package tiregdev.hi_depok.model;

/**
 * Created by TiregDev on 28/08/2017.
 */

public class itemObject_karya {

    private String username, location, likeTxt, commentTxt, postTxt, shareTxt;
    private int avatar, imagePost;

    public itemObject_karya(String username, String location, String likeTxt, String commentTxt, String postTxt, String shareTxt, int avatar, int imagePost) {
        this.username = username;
        this.location = location;
        this.likeTxt = likeTxt;
        this.commentTxt = commentTxt;
        this.postTxt = postTxt;
        this.shareTxt = shareTxt;
        this.avatar = avatar;
        this.imagePost = imagePost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLikeTxt() {
        return likeTxt;
    }

    public void setLikeTxt(String likeTxt) {
        this.likeTxt = likeTxt;
    }

    public String getCommentTxt() {
        return commentTxt;
    }

    public void setCommentTxt(String commentTxt) {
        this.commentTxt = commentTxt;
    }

    public String getPostTxt() {
        return postTxt;
    }

    public void setPostTxt(String postTxt) {
        this.postTxt = postTxt;
    }

    public String getShareTxt() {
        return shareTxt;
    }

    public void setShareTxt(String shareTxt) {
        this.shareTxt = shareTxt;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public int getImagePost() {
        return imagePost;
    }

    public void setImagePost(int imagePost) {
        this.imagePost = imagePost;
    }
}
