public class BinaryTree {

  private static class Node {

    String cityName;
    ParcelEntity parcel;
    Queue parcelQueue = new Queue(-1);
    Node left;
    Node right;

    public Node(ParcelEntity parcel) {

      this.cityName = parcel.getDestinationCity();
      this.parcel = parcel;
      this.left = null;
      this.right = null;
    }
  }

  private Node root;

  public BinaryTree(){
    this.root = null;
  }

  public void insertParcel(ParcelEntity parcel) {
    Node newNode = new Node(parcel);
    String destinationCity = newNode.cityName;

    if (root == null) {
      root = newNode;
      root.parcelQueue.enqueue(parcel);
      return;
    }

    Node current = root;
    Node parent = null;

    while (current != null) {
      parent = current;

      if (destinationCity.compareTo(current.cityName) < 0) {
        current = current.left;
      } 
      else if (destinationCity.compareTo(current.cityName) > 0) {
        current = current.right;
      } 
      else {
        current.parcelQueue.enqueue(parcel);
        return;
      }
    }

    // Ekleme yapılacak yeri bulduk
    if (destinationCity.compareTo(parent.cityName) < 0) {
      parent.left = newNode;
      parent.left.parcelQueue.enqueue(parcel);
    } 
    else {
      parent.right = newNode;
      parent.right.parcelQueue.enqueue(parcel);
    }
  }

  public Queue getCityParcels(String city){
    Node current = root;


    while (current.cityName != null ) {

      if (city.compareTo(current.cityName) < 0) {
        current = current.left;
      } 
      else if (city.compareTo(current.cityName) > 0) {
        current = current.right;
      } 
      else {      
        return current.parcelQueue;
      }
    }

    return null;
  }

  public int countCityParcels(String city){
    return getCityParcels(city).getSize();
  }

  public void removeParcel(String city){
    getCityParcels(city).dequeue();
  }

  private void traverseRecursive(Node node){

    if(node!=null){
      traverseRecursive(node.left);
      System.out.println(" " + node.cityName );
      traverseRecursive(node.right);
    }
  }

  public void inOrderTraversal(){
    traverseRecursive(root);
  }
}
