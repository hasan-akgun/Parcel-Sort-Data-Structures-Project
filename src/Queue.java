public class Queue {

  private static class Node {
    ParcelEntity parcel;
    Node next;

    // constructor for Node
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
    size = 0;
    this.queueCapacity = queueCapacity;
  }

  public int getSize() {
    return size;
  }

  public boolean isEmpty() {
    if (head == null) {
      return true;
    } else
      return false;
  }

  public boolean isFull() {
    if (queueCapacity == -1) {
      return false;
    }
    return size >= queueCapacity;
  }

  public void enqueue(ParcelEntity parcel) {
    Node newNode = new Node(parcel);
    if (isEmpty()) {
      head = newNode;
      tail = newNode;
      size++;
    } else if (!isFull()) {
      tail.next = newNode;
      tail = newNode;
      size++;
    } else {
      System.out.println("Capacity is full");
    }

  }

  public ParcelEntity dequeue() {
    if (!isEmpty()) {
      ParcelEntity temp = head.parcel;
      head = head.next;
      size--;
      return temp;
    } else {
      tail = null;
      return null;
    }

  }

  public ParcelEntity peek() {
    if (!isEmpty()) {
      return head.parcel;
    } else {
      System.out.println("Queue is empty, cannot peek element.");
      return null;
    }
  }
}