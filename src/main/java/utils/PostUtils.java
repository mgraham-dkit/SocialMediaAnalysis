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

    public static String findMaxUniqueUsers(PostUserMap engagementMap){
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

    public static String findMaxEngagements(PostEngagementMap dataset1Posts, PostEngagementMap dataset2Posts,
                                            String [] intersection){
        if(dataset1Posts == null || dataset2Posts == null){
            throw new IllegalArgumentException("Map to be analysed cannot be null");
        }

        if(intersection == null){
            throw new IllegalArgumentException("Intersection array cannot be null");
        }

        if(dataset1Posts.size() == 0 && dataset2Posts.size() == 0){
            throw new IllegalArgumentException("Maps to be analysed cannot both be empty.");
        }

        // If the first one is empty, replace it with the second one & empty the second one
        if(dataset1Posts.size() == 0){
            dataset1Posts = dataset2Posts;
            dataset2Posts = new PostEngagementMap();
        }

        String [] postIds = dataset1Posts.getKeys();

        int maxEngagements = 0;
        String mostEngagedPost = null;

        for (int i = 0; i < postIds.length; i++) {
            EngagementSet engagements = dataset1Posts.get(postIds[i]);
            int currentCount = engagements.size();
            if(contains(intersection, postIds[i])){
                engagements = dataset2Posts.get(postIds[i]);
                currentCount += engagements.size();
            }

            if(currentCount >= maxEngagements){
                mostEngagedPost = postIds[i];
                maxEngagements = currentCount;
            }
        }

        if(dataset2Posts.size() > 0) {
            postIds = dataset2Posts.getKeys();
            for (int i = 0; i < postIds.length; i++) {
                if (!contains(intersection, postIds[i])) {
                    EngagementSet engagements = dataset2Posts.get(postIds[i]);
                    int currentCount = engagements.size();

                    if (currentCount >= maxEngagements) {
                        mostEngagedPost = postIds[i];
                        maxEngagements = currentCount;
                    }
                }
            }
        }

        return mostEngagedPost;
    }

    public static boolean contains(String [] data, String text){
        if(data == null){
            throw new IllegalArgumentException("Array cannot be null");
        }

        if(text == null){
            throw new IllegalArgumentException("Text to be found cannot be null");
        }

        for (int i = 0; i < data.length; i++) {
            if(text.equals(data[i])){
                return true;
            }
        }
        return false;
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
