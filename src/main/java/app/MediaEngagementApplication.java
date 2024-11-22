package app;

import model.PostEngagement;
import utils.*;

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

        // Part 1.4:
        // Create a map to cluster PostEngagements in Sets based on post ID
        PostEngagementMap postEngagements = new PostEngagementMap();

        for (int i = 0; i < engagements.length; i++) {
            // Setting up solution to part 1.3
            addToUserEngagement(engagements, i, engagementMap);
            // Setting up solution to part 1.4
            updatePostEngagements(postEngagements, engagements[i]);
        }

        // Part 1.3 final solution:
        String mostEngagedPost = PostUtils.findMax(engagementMap);
        System.out.println("The post with the most engagements is: " + mostEngagedPost);
        System.out.println();

        // Part 1.4 final solution:
        String postToBeFound = "P004";
        getEngagementsForPost(postEngagements, postToBeFound);

        // Part 2.1 Read in new file of data
        PostEngagement[] engagementDataset2 = FileHandlingUtilities.readPostEngagementFile("datasets/engagements_2" +
                ".txt");

        // Setting up solution to part 2.2 & 2.3
        PostEngagementMap postEngagements2 = new PostEngagementMap();
        for (int i = 0; i < engagementDataset2.length; i++) {
            updatePostEngagements(postEngagements2, engagementDataset2[i]);
        }

        // Part 2.2 solution:
        // Find intersection between first and second datasets
        String[] intersectionPosts = getIntersection(postEngagements2, postEngagements);
        if (intersectionPosts.length == 0) {
            System.out.println("No posts from dataset2 appear in dataset1.");
        } else {
            // Display the information from the array
            System.out.println("--------------------------------------");
            System.out.println("\tPosts in both Datasets:");
            System.out.println("--------------------------------------");
            for (int i = 0; i < intersectionPosts.length; i++) {
                System.out.println(intersectionPosts[i]);
            }
            System.out.println("--------------------------------------\n");
        }
    }

    private static String[] getIntersection(PostEngagementMap postEngagements2, PostEngagementMap postEngagements) {
        String[] intersectionPosts = new String[postEngagements2.size()];
        int intersectionCount = 0;
        String[] postIds = postEngagements2.getKeys();
        for (int i = 0; i < postIds.length; i++) {
            if (postEngagements.get(postIds[i]) != null) {
                intersectionPosts[intersectionCount++] = postIds[i];
            }
        }

        return PostUtils.shrink(intersectionPosts, intersectionCount);
    }

    private static void getEngagementsForPost(PostEngagementMap postEngagements, String postToBeFound) {
        EngagementSet relatedEngagements = postEngagements.get(postToBeFound);
        if (relatedEngagements == null) {
            System.out.println("No engagements for post " + postToBeFound + ".");
        } else {
            // Display the information from the array
            System.out.println("--------------------------------------");
            System.out.println("\t\tEngagements for Post " + postToBeFound + ":");
            System.out.println("--------------------------------------");
            for (int i = 0; i < relatedEngagements.size(); i++) {
                PostEngagement engagement = relatedEngagements.get(i);
                System.out.println(engagement.format());
            }
            System.out.println("--------------------------------------\n");
        }
    }

    private static void updatePostEngagements(PostEngagementMap postEngagements, PostEngagement engagements) {
        // Get each engagement's post and user info
        String currentPost = engagements.getPostId();
        // Find the map of all users engaging with that post from the map
        EngagementSet engagementsForPost = postEngagements.get(currentPost);

        // If the post hasn't been engaged with before, add it to the map and give it a blank set
        if (engagementsForPost == null) {
            engagementsForPost = new EngagementSet();
            postEngagements.put(currentPost, engagementsForPost);
        }

        // Add post engagement to set
        engagementsForPost.add(engagements);
    }

    private static void addToUserEngagement(PostEngagement[] engagements, int i, PostUserMap engagementMap) {
        // Get each engagement's post and user info
        String currentPost = engagements[i].getPostId();
        String currentUser = engagements[i].getUserId();
        // Find the map of all users engaging with that post from the map
        UserEngagementMap postUsers = engagementMap.get(currentPost);

        // If the post hasn't been engaged with before, add it to the map and give it a blank user map
        if (postUsers == null) {
            postUsers = new UserEngagementMap();
            engagementMap.put(engagements[i].getPostId(), postUsers);
        }

        // If this user hasn't already been added
        Integer engagementCount = postUsers.get(currentUser);
        if (engagementCount == null) {
            postUsers.put(currentUser, 1);
        } else {
            postUsers.put(currentUser, (engagementCount + 1));
        }
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
