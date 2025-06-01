

public class ParcelEntity {

  private static int idCounter = 0;
  private String parcelId;
  private String destinationCity;
  private int priority;
  private int arrivalTick;
  private int dispatchTick;
  private String size;
  private int returnCount;

  private StatusEnum status;

  public ParcelEntity(String destinationCity, int priority, String size, int arrivalTick) {
    this.parcelId = String.valueOf(idCounter);
    this.destinationCity = destinationCity;
    this.priority = priority;
    this.size = size;
    this.arrivalTick=arrivalTick;
    this.returnCount = 0;
    this.status = StatusEnum.InQueue;
    idCounter++;
  }

  public String getDestinationCity() {
    return destinationCity;
  }

  public String getParcelId() {
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

  public int getArrivalTick(){
    return arrivalTick;
  }

  public int getDispatchTick(){
    return this.dispatchTick;
  }

  public void setDispatchTick(int dispatchTick){
    this.dispatchTick=dispatchTick;
  }


}
