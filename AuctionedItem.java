import java.util.ArrayList;
import java.util.List;

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

    /**
     * temporary return for name
     * @return first 15 characters in json
     */
    public String tempName(){
        return jsonData.substring(0,15);
    }

    /**
     * dumps json, maybe use later idk
     * @return guess bro
     */
    public String dumpJSON(){
        return jsonData;
    }

    /**
     * creates auctionItems from api string dump
     * @param data api string dump
     */
    public static ArrayList<AuctionedItem> createAuctionedItemsFromApi(String data) {
        ArrayList<AuctionedItem> toReturn = new ArrayList<>();
        String[] lines = cleanStringArray( data.split("\\r?\\n"));
        for (String line : lines) {
            AuctionedItem item = createAuctionedItem(line);
            toReturn.add(item);
            System.out.println("Created AuctionedItem: " + item.tempName());
        }

        return toReturn;
    }

    /**
     * automatically creates auctionedItem
     * @param line String to input
     * @return item
     */
    private static AuctionedItem createAuctionedItem(String line) {
        return new AuctionedItem(line);
    }

    /**
     * deletes empty lines in an array of strings, chatGPT cooked
     * @param array to be cleaned
     * @return cleaned array
     */
    public static String[] cleanStringArray(String[] array) {
        List<String> cleanedList = new ArrayList<>();

        boolean responseFound = false; // Flag to track if "response from api" is found

        // Iterate through the array and add non-empty strings to the cleaned list
        for (String str : array) {
            if (!responseFound && str.trim().startsWith("Response from API")) {
                responseFound = true; // Set the flag to true
                continue; // Skip adding this line
            }

            if (!str.isEmpty()) {
                cleanedList.add(str);
            }
        }

        // Convert the list back to an array
        String[] cleanedArray = cleanedList.toArray(new String[0]);
        return cleanedArray;
    }
}
