public class ParcelEntity {

  private static int idCounter = 0;
  private int parcelId;
  private String destinationCity;
  private int priority;

  public ParcelEntity(String destinationCity, int priority){
    this.parcelId = idCounter;
    this.destinationCity = destinationCity;
    this.priority=priority;
    idCounter++;
  }

  public String getDestinationCity(){
    return destinationCity;
  }

  public int getParcelId(){
    return parcelId;
  }

  public int getPriority(){
    return priority;
  }

}
