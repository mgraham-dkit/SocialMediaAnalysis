package utils;

import model.PostEngagement;

import java.time.LocalDateTime;
@SuppressWarnings("unused")
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
        else {
            return null;
        }
    }

    public static void sort(PostEngagement[] data){
        if(data == null){
            throw new IllegalArgumentException("Array to be sorted cannot be null");
        }

        boolean swapped = true;
        int i = 0;
        while(swapped){
            swapped = false;
            for (int j = 0; j < data.length-i-1; j++) {
                if(data[j].compareTo(data[j+1]) < 0){
                    swap(data, j, j+1);
                    swapped = true;
                }
            }
            i++;
        }
    }

    private static void swap(PostEngagement[] data, int pos1, int pos2) {
        PostEngagement temp = data[pos1];
        data[pos1] = data[pos2];
        data[pos2] = temp;
    }

    public static String findMax(PostUserMap engagementMap){
        if(engagementMap == null || engagementMap.size() == 0){
            throw new IllegalArgumentException("Map to be analysed cannot be null or empty");
        }

        String [] postIds = engagementMap.getKeys();

        int maxEngagements = engagementMap.get(postIds[0]).size();
        String mostEngagedPost = postIds[0];
        for (int i = 1; i < postIds.length; i++) {
            int postCount = engagementMap.get(postIds[i]).size();
            if(postCount > maxEngagements){
                maxEngagements = postCount;
                mostEngagedPost = postIds[i];
            }
        }

        return mostEngagedPost;
    }

    public static String [] shrink(String [] data, int count){
        if(data == null){
            throw new IllegalArgumentException("Array cannot be null");
        }

        if(count < 0 || count > data.length){
            throw new IndexOutOfBoundsException("Number of elements to be retained cannot be less than 0 or greater " +
                    "than the length of the supplied array.");
        }

        String [] reduced = new String[count];
        for (int i = 0; i < reduced.length; i++) {
            reduced[i] = data[i];
        }

        return reduced;
    }
}
