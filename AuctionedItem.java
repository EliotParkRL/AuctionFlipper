/**
 * class that represents a BIN auctioned item that was bought
 */
public class AuctionedItem {
    String jsonData;

    /**
     * makes the json data coherent
     * @param aJsonData JSON data pulled from the api
     */
    public AuctionedItem(String aJsonData){
        jsonData = aJsonData;
        //add something here that pulls everything apart
    }
}
