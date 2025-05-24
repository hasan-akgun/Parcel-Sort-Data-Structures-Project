public class ParcelTracker {

    public enum Status {
        InQueue, Sorted, Dispatched, Returned
    }


    public static class ParcelRecord {
        private Status status;
        private final int arrivalTick;
        private Integer dispatchTick;
        private int returnCount;
        private final String destinationCity;
        private final int priority;
        private final int size;

        public ParcelRecord(Status status, int arrivalTick,
                             String destinationCity, int priority, int size) {
            this.status = status;
            this.arrivalTick = arrivalTick;
            this.destinationCity = destinationCity;
            this.priority = priority;
            this.size = size;
            this.returnCount = 0;
        }

        public Status getStatus() {
            return status;
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public int getArrivalTick() {
            return arrivalTick;
        }

        public Integer getDispatchTick() {
            return dispatchTick;
        }

        public void setDispatchTick(int dispatchTick) {
            this.dispatchTick = dispatchTick;
        }

        public int getReturnCount() {
            return returnCount;
        }

        public void incrementReturnCount() {
            this.returnCount++;
        }

        public String getDestinationCity() {
            return destinationCity;
        }

        public int getPriority() {
            return priority;
        }

        public int getSize() {
            return size;
        }
    }

    private static class Entry {
        String key;
        ParcelRecord value;
        Entry next;

        Entry(String key, ParcelRecord value, Entry next) {
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


    public ParcelTracker() {
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


    public void insert(String parcelID, ParcelRecord record) {
        if (exists(parcelID)) {
            return;
        }
        resizeIfNeeded();
        int idx = hash(parcelID);
        buckets[idx] = new Entry(parcelID, record, buckets[idx]);
        size++;
    }


    public void updateStatus(String parcelID, Status newStatus) {
        ParcelRecord record = get(parcelID);
        if (record != null) {
            record.setStatus(newStatus);
        }
    }


    public ParcelRecord get(String parcelID) {
        int idx = hash(parcelID);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key.equals(parcelID)) {
                return e.value;
            }
        }
        return null;
    }


    public void incrementReturnCount(String parcelID) {
        ParcelRecord record = get(parcelID);
        if (record != null) {
            record.incrementReturnCount();
            record.setStatus(Status.Returned);
        }
    }


    public boolean exists(String parcelID) {
        return get(parcelID) != null;
    }


    public void exportState(String filename) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(filename)) {
            writer.println("parcelID,status,arrivalTick,dispatchTick,returnCount,destinationCity,priority,size");
            for (Entry head : buckets) {
                for (Entry e = head; e != null; e = e.next) {
                    ParcelRecord r = e.value;
                    writer.printf(
                        "%s,%s,%d,%s,%d,%s,%d,%d%n",
                        e.key,
                        r.getStatus(),
                        r.getArrivalTick(),
                        r.getDispatchTick() == null ? "" : r.getDispatchTick(),
                        r.getReturnCount(),
                        r.getDestinationCity(),
                        r.getPriority(),
                        r.getSize()
                    );
                }
            }
        } catch (java.io.IOException ex) {
            System.err.println("Error exporting state: " + ex.getMessage());
        }
    }


    public int size() {
        return size;
    }
}

