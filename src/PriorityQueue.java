public class PriorityQueue {

  private ParcelEntity heap[];
  private int size;
  private int queueCapacity;

  public PriorityQueue(int queueCapacity) {
    this.size = 0;
    this.queueCapacity = queueCapacity;
    this.heap = new ParcelEntity[queueCapacity];
  }

  private void swap(int i, int j) {
    ParcelEntity temp = heap[i];
    heap[i] = heap[j];
    heap[j] = temp;
  }

  private void bubbleUp(int childIndex) {
    int parentIndex = (childIndex - 1) / 2;

    int parentPriority = heap[parentIndex].getPriority();
    int childPriority = heap[childIndex].getPriority();

    while (childIndex > 0 && childPriority > parentPriority) {
      swap(childIndex, parentIndex);
      childIndex = parentIndex;
      parentIndex = (childIndex - 1) / 2;

      parentPriority = heap[parentIndex].getPriority();
      childPriority = heap[childIndex].getPriority();
    }
  }

  private void bubbleDown(int childIndex) {
    int leftChildIndex = 2 * childIndex + 1;
    int rightChildIndex = 2 * childIndex + 2;
    int largestIndex = childIndex;

    

    // Check if left child exists and has higher priority
    if (leftChildIndex < size && heap[leftChildIndex] != null) {

      int leftChildPriority = heap[leftChildIndex].getPriority();
      int currentPriority = heap[largestIndex].getPriority();
      
      if (leftChildPriority > currentPriority) {
        largestIndex = leftChildIndex;
      }
    }

    // Check if right child exists and has higher priority
    if (rightChildIndex < size && heap[rightChildIndex] != null) {

      int rightChildPriority = heap[rightChildIndex].getPriority();
      int largestPriority = heap[largestIndex].getPriority();

      if (rightChildPriority > largestPriority) {
        largestIndex = rightChildIndex;
      }
    }

    if (largestIndex != childIndex) {
      swap(childIndex, largestIndex);
      bubbleDown(largestIndex);
    }
  }

  public void add(ParcelEntity parcel) {
    if (size == queueCapacity) {
      System.out.println("Queue dolu!");
      return;
    }

    heap[size] = parcel;
    bubbleUp(size);
    size++;
  }

  public ParcelEntity poll() {
    if (isEmpty()) {
      System.out.println("Queue boş!");
      return null; // Hata durumu
    }

    ParcelEntity max = heap[0];
    heap[0] = heap[size - 1];
    size--;
    bubbleDown(0);
    return max;
  }

  public ParcelEntity peek() {
    if (isEmpty()) {
      System.out.println("Queue boş!");
      return null;
    }
    return heap[0];
  }

  public boolean isEmpty() {
    return size == 0;
  }
}
