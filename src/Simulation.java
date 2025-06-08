import java.io.FileWriter;
import java.util.ArrayList;
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
  private int MISROUTING_RATE;
  private int TERMINAL_ROTATION_INTERVAL;
  private int MAX_TICK;
  private String[] CITY_LIST;


  // Sonuçlar için değişkenler
  private int totalProduced = 0;
  private int totalDispatched = 0;
  private int totalReturned = 0;
  private int maxQueueSize = 0;
  private java.util.HashMap<String, Integer> cityDispatched = new java.util.HashMap<>(); // Şehir bazında gönderilen paket sayısı 
  private java.util.HashMap<String, Integer> cityReturned = new java.util.HashMap<>(); // Şehir bazında geri dönen paket sayıs



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
    this.MISROUTING_RATE = misrouting;
    this.TERMINAL_ROTATION_INTERVAL = terminalRotationInterval;
    this.MAX_TICK = maxTick;
    this.CITY_LIST = cityList;

    for (String city : CITY_LIST) {
          String trimmed = city.trim();
          cityDispatched.put(trimmed, 0);
          cityReturned.put(trimmed, 0);
      }
  }

  private void logStatus() {
        Logger.log("Queue Size: " + ArrivalBuffer.getSize());
        Logger.log("ReturnStack Size: " + ReturnStack.size());
        Logger.log("Active Terminal: " + TerminalRotator.getActiveTerminal());
        Logger.log(""); // Boş satır ekler!
    }

  private void generateParcels() {
    Random rand = new Random();
    int parcelNum = rand.nextInt(PARCEL_PER_TICK_MIN, PARCEL_PER_TICK_MAX + 1);

    ArrayList<ParcelEntity> newParcels = new ArrayList<>();
    for (int i = 0; i < parcelNum; i++) {
      int arrLength = CITY_LIST.length;
      int cityIndex = rand.nextInt(0, arrLength);
      String city = CITY_LIST[cityIndex];

      int priority = rand.nextInt(1, 4);

      int sizeRnd = rand.nextInt(3);
      String size = "Small";
      switch (sizeRnd) {
        case 0: size = "Small"; break;
        case 1: size = "Medium"; break;
        case 2: size = "Large"; break;
      }

      ParcelEntity newParcel = new ParcelEntity(city, priority, size, tick);
      ArrivalBuffer.enqueue(newParcel);
      ParcelTracker.insert(newParcel.getParcelId(), newParcel);
      ParcelTracker.updateStatus(newParcel.getParcelId(), StatusEnum.InQueue);
      newParcels.add(newParcel);

      totalProduced++;
      if (ArrivalBuffer.getSize() > maxQueueSize)
          maxQueueSize = ArrivalBuffer.getSize();
    }

    if (!newParcels.isEmpty()) {
        StringBuilder sb = new StringBuilder();
        sb.append("New Parcels: ");
        for (ParcelEntity p : newParcels) {
            sb.append(p.getParcelId())
              .append(" to ")
              .append(p.getDestinationCity().trim())
              .append(" (Priority ")
              .append(p.getPriority())
              .append("), ");
        }
        sb.setLength(sb.length() - 2);
        Logger.log(sb.toString());
    } else {
        Logger.log("New Parcels: None");
    }
  }

 /* private void queueProcces() {
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
  }*/
  private void queueProcces() {
    //Logger.log("queueProcces() başladı. ArrivalBuffer size: " + ArrivalBuffer.getSize());
    ArrayList<ParcelEntity> sortedParcels = new ArrayList<>();
    //int loop = 0;
    while (!ArrivalBuffer.isEmpty()) {
      //loop++;
      //Logger.log("  Döngü " + loop + " (pre-dequeue) ArrivalBuffer size: " + ArrivalBuffer.getSize());
      ParcelEntity parcel = ArrivalBuffer.dequeue();
      //Logger.log("  Döngü " + loop + " (post-dequeue) ArrivalBuffer size: " + ArrivalBuffer.getSize());

      /*if (parcel == null) {
          Logger.log("  dequeue() null döndü! BREAK!");
          break;
      }*/

      //Logger.log("  Döngü " + loop + " insertParcel öncesi");
      DestinationSorter.insertParcel(parcel);
      //Logger.log("  Döngü " + loop + " insertParcel sonrası");

      //Logger.log("  Döngü " + loop + " updateStatus öncesi");
      ParcelTracker.updateStatus(parcel.getParcelId(), StatusEnum.Sorted);
      //Logger.log("  Döngü " + loop + " updateStatus sonrası");

      sortedParcels.add(parcel);
    }
    //Logger.log("queueProcces() BİTTİ. ArrivalBuffer size: " + ArrivalBuffer.getSize());
    if (!sortedParcels.isEmpty()) {
        StringBuilder sb = new StringBuilder();
        sb.append("Sorted to BST: ");
        for (ParcelEntity p : sortedParcels) {
            sb.append(p.getParcelId()).append(", ");
        }
        sb.setLength(sb.length() - 2);
        Logger.log(sb.toString());
    } else {
        Logger.log("Sorted to BST: None");
    }
  }

  /*private void dispatchEvaluation() {
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
      tick++; // her koşulda artıyor
      if (checkMaxTick())
        return;
      terminalRotation();
      // logging will be implemented
    } else {
      ParcelTracker.updateStatus(parcel.getParcelId(), StatusEnum.Dispatched);
      parcel.setDispatchTick(tick);
      tick++; // her koşulda artıyor, dışa alıyorum
      if (checkMaxTick())
        return;
      terminalRotation();
    }
  } */

  private void dispatchEvaluation() {
        String activeTerminal = TerminalRotator.getActiveTerminal();

        if (DestinationSorter.countCityParcels(activeTerminal) == 0) {
            Logger.log("Dispatched: None from BST to " + activeTerminal + " (no parcel found)");
            return;
        }

        Random rand = new Random();
        int misrouting = rand.nextInt(MISROUTING_RATE);
        ParcelEntity parcel = DestinationSorter.getCityParcels(activeTerminal).poll();

        if (parcel == null) {
            Logger.log("Dispatched: None from BST to " + activeTerminal + " (no parcel in queue)");
            return;
        }

        String city = parcel.getDestinationCity().trim();

        if (misrouting == 0) {
            ReturnStack.push(parcel);
            ParcelTracker.updateStatus(parcel.getParcelId(), StatusEnum.Returned);
            ParcelTracker.incrementReturnCount(parcel.getParcelId());
            Logger.log("Returned: " + parcel.getParcelId() + " misrouted -> Pushed to ReturnStack");
            totalReturned++;
            cityReturned.put(city, cityReturned.get(city) + 1);
        } else {
            ParcelTracker.updateStatus(parcel.getParcelId(), StatusEnum.Dispatched);
            parcel.setDispatchTick(tick);
            Logger.log("Dispatched: " + parcel.getParcelId() + " from BST to " + activeTerminal + " -> Success");
            totalDispatched++;
            cityDispatched.put(city, cityDispatched.get(city) + 1);
        }
    }


  /*private void terminalRotation() {

    if (TERMINAL_ROTATION_INTERVAL > tick)
      return;

    if (tick % TERMINAL_ROTATION_INTERVAL == 0) {
      TerminalRotator.advanceTerminal();
      tick++;
      if (checkMaxTick())
        return;
      // logging will be implemented
    }
  }*/

  private void terminalRotation() {
      if (TERMINAL_ROTATION_INTERVAL > tick)
          return;

      if (tick % TERMINAL_ROTATION_INTERVAL == 0) {
          TerminalRotator.advanceTerminal();
          Logger.log("Rotated to: " + TerminalRotator.getActiveTerminal());
      }
  }

  private boolean checkMaxTick() {
    return tick >= MAX_TICK;
  }

  private void writeFinalReport() {
      try {
          System.out.println("Final.txt hazırlanıyor...");
          FileWriter fw = new FileWriter("Final.txt");
          fw.write("==== Simulation Final Report ====\n");
          fw.write("Total Ticks: " + (tick-1) + "\n");
          fw.write("Total Parcels Produced: " + totalProduced + "\n");
          fw.write("Total Parcels Dispatched: " + totalDispatched + "\n");
          fw.write("Total Parcels Returned: " + totalReturned + "\n");
          fw.write("Max Queue Size: " + maxQueueSize + "\n");
          fw.write("Remaining in ReturnStack: " + ReturnStack.size() + "\n\n");

          fw.write("--- Dispatch & Return per City ---\n");
          for (String city : CITY_LIST) {
              String trimmed = city.trim();
              fw.write(trimmed + " Dispatched: " + cityDispatched.get(trimmed) +
                      ", Returned: " + cityReturned.get(trimmed) + "\n");
          }

          double dispatchRate = (totalProduced > 0) ? ((double)totalDispatched / totalProduced * 100) : 0.0;
          double returnRate = (totalProduced > 0) ? ((double)totalReturned / totalProduced * 100) : 0.0;
          fw.write("\nDispatch Success Rate: %" + String.format("%.2f", dispatchRate) + "\n");
          fw.write("Return Rate: %" + String.format("%.2f", returnRate) + "\n");
          fw.close();
          System.out.println("Final.txt oluşturuldu!");
      } catch(Exception e) {
          e.printStackTrace();
      }
  }


  /*public void runSimulation() {

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
  }*/

  public void runSimulation() {
    Logger.initialize("log.txt");
    try {
    while (!checkMaxTick()) {
        Logger.log("[Tick " + tick + "]");
        generateParcels();
        queueProcces();
        dispatchEvaluation();
        terminalRotation();
        logStatus();
        tick++; // Tick her döngünün sonunda artırıyrum.
    }
    } catch(Exception e) {
        Logger.log("!!! HATA !!!: " + e.getMessage());
        e.printStackTrace();
    }
    Logger.close();
    writeFinalReport();
  }

}
