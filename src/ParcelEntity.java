

public class ParcelEntity {

  private static int idCounter = 0;
  private int parcelId;
  private String destinationCity;
  private int priority;
  private String size;
  private int returnCount;

  private StatusEnum status;

  public ParcelEntity(String destinationCity, int priority, String size) {
    this.parcelId = idCounter;
    this.destinationCity = destinationCity;
    this.priority = priority;
    this.size = size;
    this.returnCount = 0;
    this.status = StatusEnum.InQueue;
    idCounter++;
  }

  public String getDestinationCity() {
    return destinationCity;
  }

  public int getParcelId() {
    return parcelId;
  }

  public int getPriority() {
    return priority;
  }

  public void incrementReturnCount() {
    returnCount++;
  }

  public StatusEnum getStatus() {
    return status;
  }

  public void setStatus(StatusEnum status) {
    this.status = status;
  }

}
