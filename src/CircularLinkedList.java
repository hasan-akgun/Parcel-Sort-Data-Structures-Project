
public class CircularLinkedList {
  private static class Node {
    String cityName;
    Node next;

    // constructor for Node
    public Node(String cityName) {
      this.cityName = cityName;
      this.next = null;
    }
  }

  private Node head;
  private Node tail;
  private Node activeTerminal;

  private int size = 0;

  public CircularLinkedList(String[] cityList) {
    head = null;
    tail = null;
    size = 0;

    for (String city : cityList) {
      addLast(city);
    }

  }

  private boolean isEmpty() {
    if (head == null) {
      return true;
    } else
      return false;
  }

  private void addFirst(String cityName) {
    Node newNode = new Node(cityName);
    if (isEmpty()) {
      head = newNode;
      tail = newNode;
      newNode.next = head;
      activeTerminal = head;
    } else {
      newNode.next = head;
      head = newNode;
      tail.next = head;
    }
    size++;
  }

  private void addLast(String cityName) {
    Node newNode = new Node(cityName);
    if (isEmpty()) {
      addFirst(cityName);
    } else {
      tail.next = newNode;
      tail = newNode;
      tail.next = head;
      size++;
    }
  }

  public void initializeFromCityList(String[] cityArray) {

    for (String city : cityArray) {
      addLast(city);
    }
  }

  public void advanceTerminal() {
    activeTerminal = activeTerminal.next;
  }

  public String getActiveTerminal() {
    return activeTerminal.cityName;
  }

  public void printElements() {
    Node current = head;
    for (int i = 0; i < size; i++) {
      System.out.print(current.cityName + " ");
      current = current.next;
    }
  }
}
