package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("unused")
public class PostEngagement implements Comparable<PostEngagement>{
    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String postId;
    private final String userId;
    private final LocalDateTime timestamp;
    private final String type;

    public PostEngagement(String postId, String userId, LocalDateTime timestamp, String type) {
        this.postId = postId;
        this.userId = userId;
        this.timestamp = timestamp;
        this.type = type;
    }

    public PostEngagement(String postId, String userId, String type) {
        this(postId, userId, LocalDateTime.now(), type);
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "PostEngagement{" +
                "postId='" + postId + '\'' +
                ", userId='" + userId + '\'' +
                ", timestamp=" + timestamp +
                ", type='" + type + '\'' +
                '}';
    }

    /**
     * Provides the PostEngagement's data in a text format suitable for user consumption.
     * @return The contents of the PostEngagement in a text format suitable for user consumption.
     */
    public String format(){
        return timestamp.format(DATE_FORMATTER) + ": " +postId + " - " + userId + " : " + type;
    }

    /**
     * Compares this PostEngagement to the supplied object.
     * <br/>
     * This returns true if and only if the other object is not null and all values match the data in this object.
     *
     * @param o The object to compare this PostEngagement to.
     * @return True if the supplied object is equivalent to this object, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostEngagement that = (PostEngagement) o;
        return postId.equals(that.postId) && userId.equals(that.userId) && timestamp.equals(that.timestamp) && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = postId.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    public int compareTo(PostEngagement other){
        return this.timestamp.compareTo(other.timestamp);
    }
}
