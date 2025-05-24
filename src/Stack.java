
/**
 * - push(ParcelEntity): Paketi yığının tepesine ekler.
 * - pop(): Tepe paketini yığından çıkarıp döner.
 * - peek(): Tepe paketini yığından çıkarmadan döner.
 * - isEmpty(): Yığının boş olup olmadığını kontrol eder.
 * - size(): Yığındaki paket sayısını döner.
 */
public class Stack {
    private static class Node {
        ParcelEntity ParcelEntity;
        Node next;

        Node(ParcelEntity p, Node n) {
            this.ParcelEntity = p;
            this.next = n;
        }
    }

    private Node top;
    private int size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    public void push(ParcelEntity ParcelEntity) {
        top = new Node(ParcelEntity, top);
        size++;
    }

    public ParcelEntity pop() {
        if (isEmpty()) {
            return null;
        }
        ParcelEntity result = top.ParcelEntity;
        top = top.next;
        size--;
        return result;
    }

    public ParcelEntity peek() {
        return isEmpty() ? null : top.ParcelEntity;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
