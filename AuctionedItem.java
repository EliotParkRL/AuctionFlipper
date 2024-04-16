import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Base64;
import java.util.regex.Pattern;
import java.util.zip.*;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;


/**
 * class that represents a BIN auctioned item that was bought
 */
public class AuctionedItem {
    String jsonData;
    HashMap<String, String> reasonableJsonData;

    /**
     * makes the json data coherent
     * @param aJsonData JSON data pulled from the api
     */
    public AuctionedItem(String aJsonData){
        jsonData = aJsonData;
        reasonableJsonData = getReasonableJSON();
        //add something here that pulls everything apart
    }

    /**
     * Returns the reasonable JSON data
     * @return the readable JSON data
     */
    public HashMap<String, String> returnReasonableJSON(){
        return reasonableJsonData;
    }

    /**
     * Gets translated JSON data
     * @return readable json data
     */
    private HashMap<String, String> getReasonableJSON(){
        HashMap<String, String> info = new HashMap<>();
        info.put("auction_id", grabInfo("auction_id"));
//        info.put("item_name", grabInfo("item_name").replace("\\u0027", "'"));
        int[] priceLocation = new int[2];
        for(int i = jsonData.indexOf("price"); i < jsonData.length(); i++){
            if((String.valueOf(jsonData.charAt(i))).equals(":")){
                priceLocation[0] = i+1;
            } else if((String.valueOf(jsonData.charAt(i))).equals(",")){
                priceLocation[1] = i;
                break;
            }
        }
        info.put("price", jsonData.substring(priceLocation[0], priceLocation[1]));
        String itemBytesStr = grabInfo("item_bytes");
        String itemBytesClean = decompressGzipString(itemBytesStr);
        itemBytesClean = cleanString(itemBytesClean);

        info.put("item_bytes", itemBytesClean);


        return info;
    }

    /**
     * Grabs info from the JSON data
     * @param typeInfo the info you're looking for
     * @return the String that is the data
     */
    private String grabInfo(String typeInfo){
        int infoLocation = jsonData.indexOf(typeInfo);
        int[] quoteLocations = new int[3];
        int j = 0;
        for(int i = infoLocation; i < jsonData.length(); i++){
            if((String.valueOf(jsonData.charAt(i))).equals("\"")){
                quoteLocations[j] = i;
                j++;
            }
            if(j == 3){
                break;
            }
        }
        return jsonData.substring(quoteLocations[1]+1, quoteLocations[2]);
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
        String[] lines = cleanStringArray( data.split("},\\{"));

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

    /**
     * parses item_bytes, chatgpt cooked with gas
     * @param base64CompressedString MUST NOT HAVE FORWARD SLASHES
     * @return decompressed string
     */
    public static String decompressGzipString(String base64CompressedString) {
        base64CompressedString = base64CompressedString.replace("\\u003d", "=");
//        int blackslashIndex = base64CompressedString.indexOf("\\");
//        if(blackslashIndex != -1){
//            base64CompressedString = base64CompressedString.substring(0, blackslashIndex);
//        }
        try {
            byte[] compressedData = Base64.getDecoder().decode(base64CompressedString);

            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(compressedData);
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                 GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream)) {

                byte[] buffer = new byte[1024];
                int bytesRead;

                while ((bytesRead = gzipInputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                return outputStream.toString();
            }
        } catch (ZipException e) {
            // Handle case where input string is not in GZIP format
            return "Not in GZIP format";
        } catch (IOException e) {
            e.printStackTrace();
            return "something broke";
        }
    }

    public static String cleanString(String inputString) {
        // Define the regular expression pattern to match Latin characters, spaces, and ✪
        Pattern pattern = Pattern.compile("[^a-zA-Z\\s✪]+");
        // Use the pattern to replace non-matching characters with an empty string
        String cleanedString = inputString.replaceAll(pattern.pattern(), " ");
        return cleanedString;
    }
}
