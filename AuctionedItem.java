import java.util.ArrayList;
import java.util.HashMap;
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

    public HashMap<String, String> printReasonableJSON(){
        HashMap<String, String> info = new HashMap<>();
        info.put("uuid", jsonData.substring(9, 41));
        int itemLocation = jsonData.indexOf("item_name");
        int[] quoteLocations = new int[3];
        int j = 0;
        for(int i = itemLocation; i < jsonData.length(); i++){
            if((String.valueOf(jsonData.charAt(i))).equals("\"")){
                quoteLocations[j] = i;
                j++;
            }
            if(j == 3){
                break;
            }
        }
        int priceLocation = jsonData.indexOf("starting_bid");
        info.put("price", jsonData.substring(priceLocation+14, priceLocation+21));
        return info;
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
            if(item.dumpJSON().contains("\"bin\":true")){
                toReturn.add(item);
            }
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
