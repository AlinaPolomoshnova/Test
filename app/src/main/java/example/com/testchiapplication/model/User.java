package example.com.testchiapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("karma")
    @Expose
    private Integer karma;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("avg")
    @Expose
    private Object avg;
    @SerializedName("delay")
    @Expose
    private Object delay;
    @SerializedName("submitted")
    @Expose
    private Integer submitted;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("submission_count")
    @Expose
    private Integer submissionCount;
    @SerializedName("comment_count")
    @Expose
    private Integer commentCount;
    @SerializedName("created_at_i")
    @Expose
    private Integer createdAtI;
    @SerializedName("objectID")
    @Expose
    private String objectID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Integer getKarma() {
        return karma;
    }

    public void setKarma(Integer karma) {
        this.karma = karma;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Object getAvg() {
        return avg;
    }

    public void setAvg(Object avg) {
        this.avg = avg;
    }

    public Object getDelay() {
        return delay;
    }

    public void setDelay(Object delay) {
        this.delay = delay;
    }

    public Integer getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Integer submitted) {
        this.submitted = submitted;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getSubmissionCount() {
        return submissionCount;
    }

    public void setSubmissionCount(Integer submissionCount) {
        this.submissionCount = submissionCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getCreatedAtI() {
        return createdAtI;
    }

    public void setCreatedAtI(Integer createdAtI) {
        this.createdAtI = createdAtI;
    }

    public String getObjectID() {
        return objectID;
    }

    public void setObjectID(String objectID) {
        this.objectID = objectID;
    }

}
