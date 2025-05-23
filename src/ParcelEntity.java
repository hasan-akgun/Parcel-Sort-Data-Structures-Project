public class ParcelEntity {

  private static int idCounter = 0;
  private int parcelId;
  private String destinationCity;

  public ParcelEntity(String destinationCity){
    this.parcelId = idCounter;
    this.destinationCity = destinationCity;
    idCounter++;
  }

  public String getDestinationCity(){
    return destinationCity;
  }

  public int getParcelId(){
    return parcelId;
  }

}
