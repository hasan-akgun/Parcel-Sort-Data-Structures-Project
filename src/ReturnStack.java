import Parcel;  // Parcel sınıfı buradan gelecek.

/**
 *  - push(parcel): Paketi yığının tepesine ekler.
 *  - pop(): Tepe paketini yığından çıkarıp döner.
 *  - peek(): Tepe paketini yığından çıkarmadan döner.
 *  - isEmpty(): Yığının boş olup olmadığını kontrol eder.
 *  - size(): Yığındaki paket sayısını döner.
 */
public class ReturnStack {
    private static class Node {
        Parcel parcel;
        Node next;
        Node(Parcel p, Node n) {
            this.parcel = p;
            this.next = n;
        }
    }
    
    private Node top;
    private int count;


    public ReturnStack() {
        this.top = null;
        this.count = 0;
    }


    public void push(Parcel parcel) {
        top = new Node(parcel, top);
        count++;
    }


    public Parcel pop() {
        if (isEmpty()) {
            return null;
        }
        Parcel result = top.parcel;
        top = top.next;
        count--;
        return result;
    }


    public Parcel peek() {
        return isEmpty() ? null : top.parcel;
    }


    public boolean isEmpty() {
        return top == null;
    }


    public int size() {
        return count;
    }
}
