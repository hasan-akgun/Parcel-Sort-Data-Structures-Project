
public class HashTable {


    private static class Entry {
        String key;
        ParcelEntity value;
        Entry next;

        Entry(String key, ParcelEntity value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry[] buckets;
    private int capacity;
    private int size;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;
    private static final int INITIAL_CAPACITY = 16;

    public HashTable() {
        this.capacity = INITIAL_CAPACITY;
        this.buckets = new Entry[capacity];
        this.size = 0;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    private void resizeIfNeeded() {
        if ((double) size / capacity <= LOAD_FACTOR_THRESHOLD) {
            return;
        }
        int newCapacity = capacity * 2;

        Entry[] newBuckets = new Entry[newCapacity];

        for (Entry head : buckets) {
            for (Entry e = head; e != null; e = e.next) {
                int idx = Math.abs(e.key.hashCode()) % newCapacity;
                newBuckets[idx] = new Entry(e.key, e.value, newBuckets[idx]);
            }
        }

        this.buckets = newBuckets;
        this.capacity = newCapacity;
    }

    public void insert(String parcelID, ParcelEntity record) {
        if (exists(parcelID)) {
            return;
        }
        resizeIfNeeded();
        int idx = hash(parcelID);
        buckets[idx] = new Entry(parcelID, record, buckets[idx]);
        size++;
    }

    public void updateStatus(String parcelID, StatusEnum newStatus) {
        ParcelEntity record = get(parcelID);
        if (record != null) {
            record.setStatus(newStatus);
        }
    }

    public ParcelEntity get(String parcelID) {
        int idx = hash(parcelID);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key.equals(parcelID)) {
                return e.value;
            }
        }
        return null;
    }

    public void incrementReturnCount(String parcelID) {
        ParcelEntity record = get(parcelID);
        if (record != null) {
            record.incrementReturnCount();
            record.setStatus(StatusEnum.Returned);
        }
    }

    public boolean exists(String parcelID) {
        return get(parcelID) != null;
    }

    /*
    public void exportState(String filename) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            writer.println("parcelID,status,arrivalTick,dispatchTick,returnCount,destinationCity,priority,size");
            for (Entry head : buckets) {
                for (Entry e = head; e != null; e = e.next) {
                    ParcelEntity r = e.value;
                    writer.printf(
                            "%s,%s,%d,%s,%d,%s,%d,%d%n",
                            e.key,
                            r.getStatus(),
                            r.getArrivalTick(),
                            r.getDispatchTick() == null ? "" : r.getDispatchTick(),
                            r.getReturnCount(),
                            r.getDestinationCity(),
                            r.getPriority(),
                            r.getSize());
                }
            }
        } catch (java.io.IOException ex) {
            System.err.println("Error exporting state: " + ex.getMessage());
        }
    }

    */
    public int size() {
        return size;
    }
}
