package utils;

import model.PostEngagement;

import java.time.LocalDateTime;

public class PostUtils {
    public static PostEngagement parsePostEngagement(String line){
        String [] components = line.split("%%");
        if(components.length == 4){
            String postId = components[0];
            String userId = components[1];
            LocalDateTime timestamp = LocalDateTime.parse(components[2], PostEngagement.DATE_FORMATTER);
            String type = components[3];
            return new PostEngagement(postId, userId, timestamp, type);
        }
        else{
            return null;
        }
    }
}
