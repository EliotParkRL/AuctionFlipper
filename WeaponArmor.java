import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Extension of the AuctionedItem abstract class that works with weapons and armor
 */
public class WeaponArmor extends AuctionedItem{
    ArrayList<String> enchants = new ArrayList<>();
    String name;
    String apiData = "";
    int numFPBS;
    String reforge;
    int starLevel;
    boolean isRecombobulated = false;
    double predictedPrice = 0.0;

    /**
     * constructor
     * @param jsonData jsonData outputted by the apicaller
     * @param sold whether the item has been sold or not
     */
    public WeaponArmor(String jsonData, boolean sold){
        super(jsonData, sold);
        name = findName();
        isWeaponArmor();
        enchants = returnEnchants();
    }

    /**
     *
     * @param aItemName
     * @param aReforge
     * @param aStarLevel
     * @param aFBPs
     * @param aEnchants
     * @param aIsRecomb
     */
    public WeaponArmor(String aItemName, String aReforge, int aStarLevel, int aFBPs, ArrayList<String> aEnchants, boolean aIsRecomb) throws IOException {
        name = aItemName;
        reforge = aReforge.toLowerCase();
        starLevel = aStarLevel;
        numFPBS = aFBPs;
        enchants = aEnchants;
        isRecombobulated = aIsRecomb;
        this.writeArrayListToCSV("model_predict_input.csv");
        //nathan can you write this, assigns a price to predictedPrice double
    }

    /**
     * gets the predicted price
     * @return the predicted price
     */
    public int getPredictedPrice(){
        return (int)predictedPrice;
    }

    /**
     * checks if the item a weapon or armor
     */
    public void isWeaponArmor(){
        if(!name.isEmpty()){
            apiData = ApiCaller.auctionDetails(getAuctionID());
            numFPBS = countFBPS();
            findReforge();
            findStarLevel();
            findRecomb();
        }
    }

//    private boolean findArt(){
//
//    }

    /**
     * gets the number of FPBS
     * @return the number of FPBS
     */
    public int getNumFPBS(){
        return numFPBS;
    }

    /**
     * finds the reforge status of the item
     */
    private void findReforge(){
        if(apiData.contains("reforge")) {
            reforge = (grabInfo("reforge", apiData));
            reforge = reforge.toLowerCase();
        }else {
            reforge = "none";
        }
    }

    /**
     * finds whether the item has been recombobulated
     */
    private void findRecomb(){
        isRecombobulated = apiData.contains("rarity_upgrades");
    }

    /**
     * returns whether the item has been recombobulated
     * @return whether the item has been recombobulated
     */
    public boolean getRecomb(){
        return isRecombobulated;
    }

    /**
     * finds the star level of the item
     */
    private void findStarLevel(){
        int start = apiData.indexOf("upgrade_level")+15;
        int end = apiData.indexOf("upgrade_level")+16;
        if (apiData.contains("upgrade_level")){
            starLevel = Integer.parseInt(apiData.substring(start, end));
        } else {
            starLevel = 0;
        }
    }

    /**
     * counts the FBPS
     * @return the number of FBPS
     */
    private int countFBPS(){
        int fpbs = 0;
        String itemBytes = getItemBytes();
        int hpbs = itemBytes.indexOf("e(+");
        if(hpbs != -1){
            String firstAdder = itemBytes.substring(hpbs, itemBytes.indexOf(" ", hpbs));
            int secondhpbs = itemBytes.indexOf("e(+", hpbs+1);
            String secondAdder = itemBytes.substring(secondhpbs, itemBytes.indexOf(" ", secondhpbs));
            int firstGain = Integer.parseInt(firstAdder.replaceAll("[^0-9]+", ""));
            int secondGain = Integer.parseInt(secondAdder.replaceAll("[^0-9]+", ""));
            if(firstGain == secondGain && firstGain > 20){
                fpbs = (firstGain - 20)/2;
            } else if(firstGain > secondGain && firstGain > 40){
                fpbs = ((firstGain-40) - (secondGain-20))/2;
            }
        }
        return fpbs;
    }

    /**
     * getter method for the item starLevel
     * @return item starLevel
     */
    public int getStarLevel(){
        return starLevel;
    }

    /**
     * getter method for the item name
     * @return item name ie "Necron's Chestplate"
     */
    public String getName(){
        return name;
    }

    /**
     * getter method for the item reforge
     * @return item reforge ie "ancient"
     */
    public String getReforge(){
        return reforge;
    }

    /**
     * finds the name of the item
     * @return a found item name or an empty string
     */
    private String findName(){
        String item_bytes = getItemBytes();
        if(item_bytes.contains("Necron s Chestplate")){
            return "Necron's Chestplate";
        } else if(item_bytes.contains("Necron s Boots")){
            return "Necron's Boots";
        } else if(item_bytes.contains("Necron s Leggings")){
            return "Necron's Leggings";
        } else if(item_bytes.contains("Necron s Helmet")) {
            return "Necron's Helmet";
        }

        else if(item_bytes.contains("Storm s Helmet")) {
            return "Storm's Helmet";
        } else if(item_bytes.contains("Storm s Boots")) {
            return "Storm's Boots";
        } else if(item_bytes.contains("Storm s Chestplate")) {
            return "Storm's Chestplate";
        } else if(item_bytes.contains("Storm s Leggings")) {
            return "Storm's Leggings";
        }

        else if(item_bytes.contains("Goldor s Helmet")) {
            return "Goldor's Helmet";
        } else if(item_bytes.contains("Goldor s Boots")) {
            return "Goldor's Boots";
        } else if(item_bytes.contains("Goldor s Chestplate")) {
            return "Goldor's Chestplate";
        } else if(item_bytes.contains("Goldor s Leggings")) {
            return "Goldor's Leggings";
        }

        else if(item_bytes.contains("Maxor s Helmet")) {
            return "Maxor's Helmet";
        } else if(item_bytes.contains("Maxor s Boots")) {
            return "Maxor's Boots";
        } else if(item_bytes.contains("Maxor s Chestplate")) {
            return "Maxor's Chestplate";
        } else if(item_bytes.contains("Maxor s Leggings")) {
            return "Maxor's Leggings";
        }


        else if(item_bytes.contains("Livid Dagger")) {
            return "Livid Dagger";
        } else if(item_bytes.contains("Spirit Sceptre") && !item_bytes.contains("Bat Person Talisman")) {
            return "Spirit Sceptre";
        } else if(item_bytes.contains("Bouquet of Lies")){
            return "Bouquet of Lies";
        } else if(item_bytes.contains("Giant s Sword")) {
            return "Giant's Sword";
        }

        else if(item_bytes.contains("Giant s Sword")) {
            return "Giant's Sword";
        }
        return "";
    }

    /**
     * getter method for the enchantment list
     * @return ArrayList of the enchants
     */
    public ArrayList<String> getEnchants(){
        return enchants;
    }

    /**
     * finds the enchants on the item
     * @return ArrayList of the enchants
     */
    private ArrayList<String> returnEnchants(){
        if(!apiData.isEmpty()){
            String enchants = apiData.substring(apiData.indexOf("[") + 1, apiData.indexOf("]"));
            ArrayList<String> enchantList = new ArrayList<>();
            while(enchants.length() > 2){
                String level = enchants.substring(enchants.indexOf("level") + 7, enchants.indexOf("}"));
                enchantList.add(grabInfo("type", enchants) + " " + level);
                if(enchants.indexOf("{", 3) != -1){
                    enchants = enchants.substring(enchants.indexOf("{", 3));
                } else {
                    enchants = "";
                }
            }
            return enchantList;
        }
        return new ArrayList<String>();
    }

    /**
     * creates an arraylist of WeaponArmors from Api data
     * @param data data from the api call
     * @param sold whether the data is from the sold call or the new call
     * @return an arraylist of WeaponArmors
     */
    public static ArrayList<WeaponArmor> createAuctionedItemsFromApi(String data, boolean sold) {
        ArrayList<WeaponArmor> toReturn = new ArrayList<>();
        String[] lines;
        if(sold){
            lines = cleanStringArray( data.split("},\\{"));
        }else{
            lines = cleanStringArray( data.split("\\r?\\n"));
        }

        for (String line : lines) {
            if(line.contains("\"bin\":true")){
                toReturn.add(createAuctionedItem(line, sold));
            }
        }

        return toReturn;
    }

    /**
     * automatically creates auctionedItem
     * @param line String to input
     * @return item
     */
    private static WeaponArmor createAuctionedItem(String line, boolean sold) {
        return new WeaponArmor(line, sold);
    }



    /**
     * helper method for writing to csv
     * @param csvFilePath csv to write to
     * @throws IOException
     */
    public void writeArrayListToCSV(String csvFilePath) throws IOException {
        FileWriter writer = new FileWriter(csvFilePath,true);

        writer.append(getDate());
        writer.append(',');
        writer.append(getName());
        writer.append(',');
        writer.append(Long.toString(getAuctionPrice()));
        writer.append(',');
        writer.append(getAuctionID());
        writer.append(',');
        writer.append(getReforge());
        writer.append(',');
        writer.append(String.valueOf(getStarLevel()));
        writer.append(',');
        writer.append(String.valueOf(getNumFPBS()));
        writer.append(',');

        for (String enchant : enchants) {
            writer.append(enchant);
            writer.append('|');
        }

        writer.append(',');
        writer.append(String.valueOf(getRecomb()));
        writer.append('\n');
        writer.close();
    }
}

/** May use to add smth missing to every single line in model_train_input.csv if needed, edit within the for loop.
*/
 //    public static void appendToEachLine(String filePath) {
//        try {
//            // Read all lines from the file
//            List<String> lines = Files.readAllLines(Paths.get(filePath));
//
//            // Create a list to store modified lines
//            List<String> modifiedLines = new ArrayList<>();
//
//            // Append new data to each line
//            for (int i = 1; i < lines.size(); i++) {
//                StopWatch watch = new StopWatch();
//                watch.startWatch();
//                while(watch.getTimeSeconds() < 1){}
//                int index = -1;
//                int fromIndex = 0;
//                int occurrenceCount = 0;
//
//                while (occurrenceCount < 3) {
//                    index = lines.get(i).indexOf(",", fromIndex);
//                    occurrenceCount++;
//                    fromIndex = index + 1;
//                }
//                int index2 = lines.get(i).indexOf(",", fromIndex);
//                String auctionId = lines.get(i).substring(index+1, index2);
//                modifiedLines.add(lines.get(i) + "," + ApiCaller.auctionDetails(auctionId).contains("rarity_upgrades"));
//            }
//
//            // Write the modified lines back to the file
//            Files.write(Paths.get(filePath), modifiedLines);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }