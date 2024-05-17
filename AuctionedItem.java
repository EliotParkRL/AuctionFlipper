import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.*;
import java.util.zip.GZIPInputStream;


/**
 * class that represents a BIN auctioned item that was bought
 */
public abstract class AuctionedItem {
    String jsonData;
    HashMap<String, String> reasonableJsonData;
    boolean sold;


    public AuctionedItem(String jsonData, boolean sold){
        this.sold = sold;
        this.jsonData = jsonData;
        reasonableJsonData = returnReasonableJSON();
        this.jsonData = jsonData;
    }

    /**
     * Returns the reasonable JSON data
     * @return the readable JSON data
     */
    public HashMap<String, String> getReasonableJSON(){
        return reasonableJsonData;
    }



    /**
     * Gets translated JSON data
     * @return readable json data
     */
    HashMap<String, String> returnReasonableJSON(){
        HashMap<String, String> info = new HashMap<>();
        if(sold){
            info.put("auction_id", grabInfo("auction_id"));
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
        } else{
            info.put("auction_id", grabInfo("uuid"));
            info.put("item_name", grabInfo("item_name").replace("\\u0027", "'"));
            int[] priceLocation = new int[2];
            for(int i = jsonData.indexOf("starting_bid"); i < jsonData.length(); i++){
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
            info.put("item_name", getName(info));
        }
        return info;
//        if(sold){
//            return ApiCaller.auctionDetails(grabInfo("auction_id"));
//        } else {
//            return ApiCaller.auctionDetails(grabInfo("uuid"));
//        }

    }

    String getName(HashMap<String, String> info){
        int nameLocation = info.get("item_bytes").indexOf("item_name");
        String itemName = info.get("item_bytes").substring(nameLocation + "item_bytes".length() + 1, info.get("item_bytes").length() - 1);
        return itemName;
    }

    /**
     * Grabs info from the JSON data
     * @param typeInfo the info you're looking for
     * @return the String that is the data
     */
    public String grabInfo(String typeInfo){
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


    /**
     * deletes empty lines in an array of strings, chatGPT cooked
     * @param array to be cleaned
     * @return cleaned array
     */
    static String[] cleanStringArray(String[] array) {
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
    String decompressGzipString(String base64CompressedString) {
        base64CompressedString = base64CompressedString.replace("\\u003d", "=");
        jsonData = base64CompressedString;

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

    String cleanString(String inputString) {
        // Define the regular expression pattern to match Latin characters, spaces, and ✪
        Pattern pattern = Pattern.compile("[^a-zA-Z\\s✪]+");
        // Use the pattern to replace non-matching characters with an empty string
        String cleanedString = inputString.replaceAll(pattern.pattern(), " ");
        return cleanedString;
    }

    /**
     * gets actual auction price
     * @return price
     */
    public long getAuctionPrice(){
        return Long.parseLong(reasonableJsonData.get("price"));
    }

    /** TODO
     * gets predicted auction price
     * @return price
     */
    public int getPredictedPrice(){
        return 0;
    }

    /**
     * gets auction id of the AuctionedItem
     * @return auction id
     */
    public String getAuctionID(){
        return reasonableJsonData.get("auction_id");
    }

    /**
     * gets item name of the AuctionedItem
     * @return name
     */
    public String getName(){
        return reasonableJsonData.get("item_name");
    }

    /**
     * helper method for writing to csv
     * @param csvFilePath csv to write to
     * @throws IOException
     */
    public void writeArrayListToCSV(String csvFilePath) throws IOException{
        FileWriter writer = new FileWriter(csvFilePath,true);

        writer.append(Long.toString(getAuctionPrice()));
        writer.append(',');
        writer.append(getAuctionID());
        writer.append('\n');
        writer.close();
    }
//    public static ArrayList<String> getEnchants(String input){
//
//
//    }
}