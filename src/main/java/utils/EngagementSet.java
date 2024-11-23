package utils;

import model.PostEngagement;

// Create a Set implemented as an ArrayList
public class EngagementSet {
    private PostEngagement[] data;
    private int count;
    private final int expansionFactor;

    public EngagementSet() {
        this(10);
    }

    public EngagementSet(int expansionFactor) {
        if(expansionFactor < 0){
            throw new IllegalArgumentException("Expansion factor must be greater than 0");
        }
        this.expansionFactor = expansionFactor;
        data = new PostEngagement[10];
        count = 0;
    }

    public boolean add(PostEngagement pe) {
        validateEngagement(pe);

        if(contains(pe)){
            return false;
        }

        if (data.length == count) {
            grow();
        }
        // Add the new element to the end of the list (first available space in the array)
        data[count] = pe;
        // Increase the number of elements in the list
        count++;
        return true;
    }

    private static void validateEngagement(PostEngagement pe) {
        if(pe == null){
            throw new IllegalArgumentException("Null object cannot be added to Set");
        }
    }

    public int indexOf(PostEngagement pe){
        validateEngagement(pe);
        for (int i = 0; i < count; i++) {
            if(data[i].equals(pe)){
                return i;
            }
        }
        return -1;
    }

    public boolean contains(PostEngagement pe){
        validateEngagement(pe);
        return indexOf(pe) != -1;
    }

    public PostEngagement get(int index) {
        // Check if the position is valid, i.e. not AFTER the end of the list or BEFORE the start
        if (index >= count || index < 0) {
            // If it's outside the bounds of the list, throw an exception
            throw new IndexOutOfBoundsException("Position supplied must be between 0 and size of set.");
        }
        // If the requested position is valid, return the data at that position
        return data[index];
    }

    public int size() {
        // Return the number of elements in the list
        return count;
    }

    private void grow() {
        PostEngagement[] enlargedArray = new PostEngagement[data.length + expansionFactor];

        for (int i = 0; i < data.length; i++) {
            enlargedArray[i] = data[i];
        }

        data = enlargedArray;
    }

    public PostEngagement remove(int pos) {
        validatePosition(pos);

        PostEngagement deleted = data[pos];

        for (int i = pos; i < count - 1; i++) {
            data[i] = data[i + 1];
        }

        data[data.length - 1] = null;
        count--;

        return deleted;
    }

    private void validatePosition(int pos) {
        if (pos < 0 || pos >= count) {
            throw new ArrayIndexOutOfBoundsException("Supplied position must be within the boundary of the array");
        }
    }
}
