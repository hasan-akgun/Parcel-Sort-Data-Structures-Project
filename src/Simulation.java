import java.util.Random;

public class Simulation {

  private int tick;
  private Queue ArrivalBuffer;
  private BinaryTree DestinationSorter;
  private HashTable ParcelTracker;
  private CircularLinkedList TerminalRotator;
  private Stack ReturnStack;

  private int PARCEL_PER_TICK_MIN;
  private int PARCEL_PER_TICK_MAX;
  private int MISROUTİNG_RATE;
  private int TERMINAL_ROTATION_INTERVAL;
  private int MAX_TICK;
  private String[] CITY_LIST;

  public Simulation(int queueCapacity, int minParcel, int maxParcel, int misrouting, int terminalRotationInterval,
      int maxTick, String[] cityList) {
    this.tick = 1;
    this.ArrivalBuffer = new Queue(queueCapacity);
    this.DestinationSorter = new BinaryTree(queueCapacity);
    this.ParcelTracker = new HashTable();
    this.TerminalRotator = new CircularLinkedList(cityList);
    this.ReturnStack = new Stack();
    this.PARCEL_PER_TICK_MIN = minParcel;
    this.PARCEL_PER_TICK_MAX = maxParcel;
    this.MISROUTİNG_RATE = misrouting;
    this.TERMINAL_ROTATION_INTERVAL = terminalRotationInterval;
    this.MAX_TICK = maxTick;
    this.CITY_LIST = cityList;
  }

  private void generateParcels() {
    Random rand = new Random();
    int parcelNum = rand.nextInt(PARCEL_PER_TICK_MIN, PARCEL_PER_TICK_MAX + 1);

    int arrLength = CITY_LIST.length;
    int cityIndex = rand.nextInt(0, arrLength);
    String city = CITY_LIST[cityIndex];

    int priority = rand.nextInt(1, 4);

    int sizeRnd = rand.nextInt(3);
    String size = "Small";

    switch (sizeRnd) {
      case 0:
        size = "Small";
        break;
      case 1:
        size = "Medium";
        break;
      case 2:
        size = "Large";
        break;

    }

    for (int i = 0; i < parcelNum; i++) {
      ParcelEntity newParcel = new ParcelEntity(city, priority, size, tick);
      ArrivalBuffer.enqueue(newParcel);
      ParcelTracker.insert(newParcel.getParcelId(), newParcel);
      ParcelTracker.updateStatus(newParcel.getParcelId(), StatusEnum.InQueue);
      tick++;
      if (checkMaxTick())
        return;
      terminalRotation();
      // Logging will be implemented later
    }
  }

  private void queueProcces() {
    while (!ArrivalBuffer.isEmpty()) {
      ParcelEntity parcel = ArrivalBuffer.dequeue();
      DestinationSorter.insertParcel(parcel);
      ParcelTracker.updateStatus(parcel.getParcelId(), StatusEnum.Sorted);
      tick++;
      if (checkMaxTick())
        return;
      terminalRotation();
      // Logging will be implemented
    }
  }

  private void dispatchEvaluation() {
    String activeTerminal = TerminalRotator.getActiveTerminal();

    if (DestinationSorter.countCityParcels(activeTerminal) == 0) {
      return;
    }

    Random rand = new Random();
    int misrouting = rand.nextInt(MISROUTİNG_RATE);
    ParcelEntity parcel = DestinationSorter.getCityParcels(activeTerminal).poll();

    if (misrouting == 0) {
      ReturnStack.push(parcel);
      ParcelTracker.updateStatus(parcel.getParcelId(), StatusEnum.Returned);
      ParcelTracker.incrementReturnCount(parcel.getParcelId());
      tick++;
      if (checkMaxTick())
        return;
      terminalRotation();
      // logging will be implemented
    } else {
      ParcelTracker.updateStatus(parcel.getParcelId(), StatusEnum.Dispatched);
      parcel.setDispatchTick(tick);
      tick++;
      if (checkMaxTick())
        return;
      terminalRotation();
    }
  }

  private void terminalRotation() {

    if (TERMINAL_ROTATION_INTERVAL > tick)
      return;

    if (tick % TERMINAL_ROTATION_INTERVAL == 0) {
      TerminalRotator.advanceTerminal();
      tick++;
      if (checkMaxTick())
        return;
      // logging will be implemented
    }
  }

  private boolean checkMaxTick() {
    return tick == MAX_TICK;
  }

  public void runSimulation() {

    while (!checkMaxTick()) {


      generateParcels();
      if (checkMaxTick())
        return;

      queueProcces();
      if (checkMaxTick())
        return;

      dispatchEvaluation();
      if (checkMaxTick())
        return;

    }
  }

}
