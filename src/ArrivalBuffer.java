public class ArrivalBuffer<T> {

  private static class Node<T> {
    T data;
    Node<T> next;

    // constructor for Node
    public Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

  private Node<T> head;
  private Node<T> tail;
  private int queueCapacity=30;
  private int size;

  public ArrivalBuffer() {
      head = null;
      size = 0;
  }

  public int size() {
    return size;
  }

  public boolean isEmpty() {
    if (head == null) {
      return true;
    } else
      return false;
  }

  public boolean isFull(){
    return size >= queueCapacity;
  }

  public void enqueue(T data) {
    Node<T> newNode = new Node<>(data);
    if (isEmpty()) {
      head = newNode;
      tail = newNode;
      size++;
    } 
    else if(!isFull()) {
      tail.next = newNode;
      tail = newNode;
      size++;
    }
    else{
      System.out.println("Capacity is full");
    }
    
  }

  public T dequeue() {
    if (!isEmpty()) {
      T temp = head.data;
      head = head.next;
      size--;
      return temp;
    } else {
      tail = null;
      return null;
    }

  }

  public T peek() {
    if (!isEmpty()) {
      return head.data;
    } else {
      System.out.println("Queue is empty, cannot peek element.");
      return null;
    }
  }
}