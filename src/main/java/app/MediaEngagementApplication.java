package app;

import model.PostEngagement;
import utils.*;

import utils.PostUtils;

public class MediaEngagementApplication {
    public static void main(String[] args) {
        // Part 1.1&2:
        PostEngagement[] engagements = FileHandlingUtilities.readPostEngagementFile("datasets/engagements_1.txt");
        displayEngagements(engagements);
        PostUtils.sort(engagements);
        System.out.println("After sorting: ");
        displayEngagements(engagements);

        // Part 1.3:
        // Break down engagement data by postId.
        // Each post id stores a map of user Ids that engaged with that post
        // The user ID map maps the user ID to a count of engagements for that post
        PostUserMap engagementMap = new PostUserMap();

        for (int i = 0; i < engagements.length; i++) {
            // Get each engagement's post and user info
            String currentPost = engagements[i].getPostId();
            String currentUser = engagements[i].getUserId();
            // Find the map of all users engaging with that post from the map
            UserEngagementMap postUsers = engagementMap.get(currentPost);

            // If the post hasn't been engaged with before, add it to the map and give it a blank user map
            if(postUsers == null){
                postUsers = new UserEngagementMap();
                engagementMap.put(engagements[i].getPostId(), postUsers);
            }

            // If this user hasn't already been added
            Integer engagementCount = postUsers.get(currentUser);
            if(engagementCount == null){
                postUsers.put(currentUser, 1);
            }else{
                postUsers.put(currentUser, (engagementCount+1));
            }
        }

        String mostEngagedPost = PostUtils.findMax(engagementMap);
        System.out.println("The post with the most engagements is: " + mostEngagedPost);
    }

    private static void displayEngagements(PostEngagement[] engagements) {
        // Display the information from the array
        System.out.println("--------------------------------------");
        System.out.println("\t\tPost Engagements");
        System.out.println("--------------------------------------");
        for (PostEngagement engagement : engagements) {
            System.out.println(engagement.format());
        }
        System.out.println("--------------------------------------\n");
    }
}
