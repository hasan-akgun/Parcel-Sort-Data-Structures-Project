public class Stack {
    private static class Node {
        ParcelEntity parcel;
        Node next;

        Node(ParcelEntity parcel, Node next) {
            this.parcel = parcel;
            this.next = next;
        }
    }

    private Node top;
    private int count;

    public Stack() {
        this.top = null;
        this.count = 0;
    }
  

    public void push(ParcelEntity parcel) {
        top = new Node(parcel, top);
        count++;
    }

  
    public ParcelEntity pop() {
        if (isEmpty()) {
            return null;
        }
        ParcelEntity result = top.parcel;
        top = top.next;
        count--;
        return result;
    }


    public ParcelEntity peek() {
        return isEmpty() ? null : top.parcel;
    }
  

    public boolean isEmpty() {
        return top == null;
    }
  

    public int size() {
        return count;
    }
}
