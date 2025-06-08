public class SimpleIntMap {
    private static class Entry {
        String key;
        int value;
        Entry next;
        Entry(String key, int value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private Entry[] buckets;
    private int capacity;

    public SimpleIntMap(int capacity) {
        this.capacity = capacity;
        this.buckets = new Entry[capacity];
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % capacity;
    }

    public void put(String key, int value) {
        int idx = hash(key);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                e.value = value;
                return;
            }
        }
        buckets[idx] = new Entry(key, value, buckets[idx]);
    }

    public int get(String key) {
        int idx = hash(key);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                return e.value;
            }
        }
        return 0;
    }

    public void increment(String key) {
        int idx = hash(key);
        for (Entry e = buckets[idx]; e != null; e = e.next) {
            if (e.key.equals(key)) {
                e.value++;
                return;
            }
        }
        put(key, 1);
    }

    public String[] keys() {
        java.util.ArrayList<String> list = new java.util.ArrayList<>();
        for (Entry head : buckets) {
            for (Entry e = head; e != null; e = e.next) {
                list.add(e.key);
            }
        }
        return list.toArray(new String[0]);
    }
}
