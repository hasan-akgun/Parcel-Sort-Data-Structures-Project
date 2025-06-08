public class Queue {

  private static class Node {
    ParcelEntity parcel;
    Node next;

    public Node(ParcelEntity parcel) {
      this.parcel = parcel;
      this.next = null;
    }
  }


  private Node head;
  private Node tail;
  private int queueCapacity;
  private int size;


  public Queue(int queueCapacity) {
    head = null;
    tail = null;
    size = 0;
    this.queueCapacity = queueCapacity;
  }


  public int getSize() {
    return size;
  }


  public boolean isEmpty() { return size == 0; }


  public boolean isFull() {
    if (queueCapacity == -1) {
      return false;
    }
    return size >= queueCapacity;
  }


  public void enqueue(ParcelEntity parcel) {
    if (isFull()) {
      System.out.println("Capacity is full");
      return;
    }
    Node newNode = new Node(parcel);
    if (isEmpty()) {
      head = newNode;
      tail = newNode;
    } else {
      tail.next = newNode;
      tail = newNode;
    }
    size++;
  }


  public ParcelEntity dequeue() {
    if (!isEmpty()) {
        ParcelEntity temp = head.parcel;
        head = head.next;
        size--;
        if (head == null) tail = null;
        return temp;
    } else {
        tail = null;
        return null;
    }
  }


  public ParcelEntity peek() {
    if (isEmpty()) {
      System.out.println("Queue is empty, cannot peek element.");
      return null;
    }
    return head.parcel;
  }
}