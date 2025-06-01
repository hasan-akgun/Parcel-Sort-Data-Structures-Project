public class BinaryTree {

  private static class Node {

    String cityName;
    PriorityQueue parcelQueue;
    Node left;
    Node right;

    public Node(ParcelEntity parcel, int queueCapacity) {

      this.parcelQueue = new PriorityQueue(queueCapacity);
      this.cityName = parcel.getDestinationCity();
      this.left = null;
      this.right = null;
    }
  }

  private Node root;
  private int queueCapacity;

  public BinaryTree(int queueCapacity) {
    this.root = null;
    this.queueCapacity = queueCapacity;
  }

  public void insertParcel(ParcelEntity parcel) {
    Node newNode = new Node(parcel, this.queueCapacity);
    String destinationCity = newNode.cityName;

    if (root == null) {
      root = newNode;
      root.parcelQueue.add(parcel);
      return;
    }

    Node current = root;
    Node parent = null;

    while (current != null) {
      parent = current;

      if (destinationCity.compareTo(current.cityName) < 0) {
        current = current.left;
      } else if (destinationCity.compareTo(current.cityName) > 0) {
        current = current.right;
      } else if(!current.parcelQueue.isFull()) {
        current.parcelQueue.add(parcel);
        return;
      }
    }

    if (destinationCity.compareTo(parent.cityName) < 0) {
      parent.left = newNode;
      parent.left.parcelQueue.add(parcel);

    } 
    else {
      parent.right = newNode;
      parent.right.parcelQueue.add(parcel);
    }
  }

  public PriorityQueue getCityParcels(String city) {
    Node current = root;

    while (current != null) {

      if (city.compareTo(current.cityName) < 0) {
        current = current.left;
      } else if (city.compareTo(current.cityName) > 0) {
        current = current.right;
      } else {
        return current.parcelQueue;
      }
    }

    return null;
  }

  public int countCityParcels(String city) {
    if (getCityParcels(city) != null) {
      return getCityParcels(city).getSize();
    } else
      return 0;
  }

  public void removeParcel(String city) {
    getCityParcels(city).poll();
  }

  private void traverseRecursive(Node node) {

    if (node != null) {
      traverseRecursive(node.left);
      System.out.println(" " + node.cityName);
      traverseRecursive(node.right);
    }
  }

  public void inOrderTraversal() {
    traverseRecursive(root);
  }
}
