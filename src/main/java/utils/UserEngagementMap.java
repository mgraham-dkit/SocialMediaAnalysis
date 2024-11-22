package utils;

import java.util.ArrayList;

public class UserEngagementMap {
    private static final double LOAD_FACTOR = 0.75;
    private static final int EXPANSION_FACTOR = 3;
    private ArrayList<Entry>[] map = new ArrayList[103];
    private int count = 0;

    public UserEngagementMap() {

    }

    public int size() {
        return count;
    }

    private boolean hasCapacity() {
        return count < map.length * LOAD_FACTOR;
    }

    protected int hash(String key) {
        return Math.abs(key.hashCode()) % map.length;
    }

    private void updateMap() {
        java.util.ArrayList<Entry>[] oldMap = map;
        map = new ArrayList[map.length * EXPANSION_FACTOR];

        for (int i = 0; i < oldMap.length; i++) {
            if (oldMap[i] == null) {
                continue;
            }

            for (int j = 0; j < oldMap[i].size(); j++) {
                Entry currentEntry = oldMap[i].get(j);
                String key = currentEntry.key;
                Integer value = currentEntry.value;

                put(key, value);
            }
        }
    }

    public Integer put(String key, Integer value) {
        // Validation
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        if (!hasCapacity()) {
            updateMap();
        }
        // Calculate appropriate slot/bucket for use based on key
        int pos = hash(key);

        // If slot/bucket is null, it has never been used.
        // Add a new list and add new Entry
        if (map[pos] == null) {
            map[pos] = new ArrayList<Entry>();
        }

        Entry newEntry = new Entry(key, value);
        int index = map[pos].indexOf(newEntry);
        // If it's already here, update the associated value and return the old version
        if (index != -1) {
            Entry toBeUpdated = map[pos].get(index);
            Integer replaced = toBeUpdated.value;
            toBeUpdated.value = value;
            return replaced;
        }

        // If this is a new key, add the entire entry to the map and increase the count
        map[pos].add(newEntry);
        count++;
        return null;
    }

    public Integer get(String key) {
        // Validation
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        int pos = hash(key);
        if (map[pos] == null) {
            return null;
        }

        int index = map[pos].indexOf(new Entry(key, null));
        if (index == -1) {
            return null;
        }

        Entry match = map[pos].get(index);
        return match.value;
    }

    public String[] getKeys() {
        String [] keys = new String[count];
        int keyCount = 0;

        for (int i = 0; i < map.length && keyCount < count; i++) {
            if (map[i] != null) {
                ArrayList<Entry> currentSlot = map[i];
                for (int j = 0; j < currentSlot.size() && keyCount < count; j++) {
                    Entry entry = currentSlot.get(j);
                    keys[keyCount++] = entry.key;
                }
            }
        }
        return keys;
    }

    public Integer[] getValues() {
        Integer[] values = new Integer[count];
        int valueCount = 0;

        for (int i = 0; i < map.length && valueCount < count; i++) {
            if (map[i] != null) {
                ArrayList<Entry> currentSlot = map[i];
                for (int j = 0; j < currentSlot.size() && valueCount < count; j++) {
                    Entry entry = currentSlot.get(j);
                    values[valueCount++] = entry.value;
                }
            }
        }
        return values;
    }

    public void display() {
        for (int i = 0; i < map.length; i++) {
            if (map[i] == null) {
                System.out.println("Slot " + i + ") Null");
                continue;
            }

            System.out.println("Slot " + i + ")");
            for (int j = 0; j < map[i].size(); j++) {
                Entry currentEntry = map[i].get(j);
                System.out.println("Entry " + j + " -> " + currentEntry);
            }
        }
    }

    private static class Entry{
        String key;
        Integer value;

        public Entry(String key, Integer value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry entry = (Entry) o;
            return key.equals(entry.key);
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
