package app;

import model.PostEngagement;
import utils.FileHandlingUtilities;
import utils.PostUtils;

public class MediaEngagementApplication {
    public static void main(String[] args) {
        PostEngagement[] engagements = FileHandlingUtilities.readPostEngagementFile("datasets/engagements_1.txt");
        displayEngagements(engagements);
        PostUtils.sort(engagements);
        System.out.println("After sorting: ");
        displayEngagements(engagements);
    }

    private static void displayEngagements(PostEngagement[] engagements) {
        // Display the information from the array
        System.out.println("--------------------------------------");
        System.out.println("\t\tPost Engagements");
        System.out.println("--------------------------------------");
        for (PostEngagement engagement : engagements) {
            System.out.println(engagement.format());
        }
        System.out.println("--------------------------------------");
    }
}
