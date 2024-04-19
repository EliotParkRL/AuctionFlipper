import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.*;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;


/**
 * class that represents a BIN auctioned item that was bought
 */
public class OngoingAuction {
    String jsonData;
    HashMap<String, String> reasonableJsonData;
    ArrayList<String> enchants = new ArrayList<>();

    /**
     * makes the json data coherent
     * @param aJsonData JSON data pulled from the api
     */
    public OngoingAuction(String aJsonData){
        jsonData = aJsonData;
        reasonableJsonData = getReasonableJSON();
        enchants = getEnchants();
        //add something here that pulls everything apart
    }

    /**
     * Returns the reasonable JSON data
     * @return the readable JSON data
     */
    public HashMap<String, String> returnReasonableJSON(){
        return reasonableJsonData;
    }

    public ArrayList<String> getEnchants(){
        String input = returnReasonableJSON().get("item_bytes");
        int displayIndex = input.indexOf("display");
        if (displayIndex != -1) {
            int loreIndex = input.indexOf("Lore", displayIndex);
            if (loreIndex != -1) {
                int endOfLoreIndex = input.indexOf("b ", loreIndex); // assuming 'b ' is the end delimiter
                if (endOfLoreIndex != -1) {
                    String loreSection = input.substring(loreIndex, endOfLoreIndex);
                    ArrayList<String> enchants = new ArrayList<>();

                    // Define regex pattern to match enchantments
                    String enchantPattern = "(Bane of Arthropods|Champion|Cleave|Critical|Cubism|Divine Gift|Dragon Hunter|" +
                            "Ender Slayer|Execute|Experience|Fire Aspect|First Strike|Giant Killer|Impaling|Knockback|" +
                            "Lethality|Life Steal|Looting|Luck|Mana Steal|Prosecute|Scavenger|Sharpness|Smite|Smoldering|" +
                            "Syphon|Tabasco|Thunderlord|Titan Killer|Triple-Strike|Vampirism|Venomous|Vicious) (V|IV|III|II|I)";
                    Pattern pattern = Pattern.compile(enchantPattern);
                    Matcher matcher = pattern.matcher(loreSection);

                    while (matcher.find()) {
                        String enchantName = matcher.group(1);
                        String romanNumeral = matcher.group(2);
                        enchants.add(enchantName + " " + romanNumeral);
                    }

                    return enchants;
                }
            }
        }
        return new ArrayList<String>();
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
     * creates OngoingAuctions from api string dump
     * @param data api string dump
     */
    public static ArrayList<OngoingAuction> createOngoingAuctionsFromApi(String data) {
        ArrayList<OngoingAuction> toReturn = new ArrayList<>();
        String[] lines = cleanStringArray( data.split("},\\{"));

        for (String line : lines) {
            OngoingAuction item = createOngoingAuction(line);
            if(item.dumpJSON().contains("\"bin\":true")){
                toReturn.add(item);
            }
        }

        return toReturn;
    }

    /**
     * automatically creates OngoingAuction
     * @param line String to input
     * @return item
     */
    private static OngoingAuction createOngoingAuction(String line) {
        return new OngoingAuction(line);
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

    /**
     * gets actual auction price
     * @return price
     */
    public int getAuctionPrice(){
        return Integer.parseInt(reasonableJsonData.get("price"));
    }

    /** TODO
     * gets predicted auction price
     * @return price
     */
    public int getPredictedPrice(){
        return 0;
    }

    public String getAuctionID(){
        return reasonableJsonData.get("auction_id");
    }

//    public static ArrayList<String> getEnchants(String input){
//
//
//    }
}