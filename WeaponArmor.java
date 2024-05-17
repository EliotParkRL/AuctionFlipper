import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extension of the AuctionedItem abstract class that works with weapons and armor
 */
public class WeaponArmor extends AuctionedItem{
    ArrayList<String> enchants = new ArrayList<>();
    String name;
    String apiData = "";
    String reforge;

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

    public void isWeaponArmor(){
        if(!name.isEmpty()){
            apiData = ApiCaller.auctionDetails(getAuctionID());
            reforge = (grabInfo("reforge", apiData));
            reforge = reforge.toLowerCase();
        }
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
     * @return item name ie "ancient"
     */
    public String getReforge(){
        return reforge;
    }

    /**
     * finds the name of the item
     * @return a found item name or an empty string
     */
    private String findName(){
        String item_bytes = reasonableJsonData.get("item_bytes");
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
        } else if(item_bytes.contains("Goldor s Helmet")) {
            return "Goldor's Helmet";
        } else if(item_bytes.contains("Goldor s Boots")) {
            return "Goldor's Boots";
        } else if(item_bytes.contains("Goldor s Chestplate")) {
            return "Goldor's Chestplate";
        } else if(item_bytes.contains("Goldor s Leggings")) {
            return "Goldor's Leggings";
        } else if(item_bytes.contains("Maxor s Helmet")) {
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
//        String input = getReasonableJSON().get("item_bytes");
//        int displayIndex = input.indexOf("display");
//        if (displayIndex != -1) {
//            int loreIndex = input.indexOf("Lore", displayIndex);
//            if (loreIndex != -1) {
//                int endOfLoreIndex = input.indexOf("b ", loreIndex); // assuming 'b ' is the end delimiter
//                if (endOfLoreIndex != -1) {
//                    String loreSection = input.substring(loreIndex, endOfLoreIndex);
//                    ArrayList<String> enchants = new ArrayList<>();
//
//                    // Define regex pattern to match enchantments
//                    String enchantPattern = "(Bank|Bobbin’ Time|Chimera|Combo|Duplex|Fatal Tempo|Flash|Habanero Tactics|Inferno|Last Stand|Legion|No Pain No Gain|One For All|Refrigerate|Rend|Soul Eater|Swarm|The One|Ultimate Jerry|Ultimate Wise|Wisdom|" +
//                            "Bane of Arthropods|Champion|Cleave|Critical|Cubism|Divine Gift|Dragon Hunter|" +
//                            "Ender Slayer|Execute|Experience|Fire Aspect|First Strike|Giant Killer|Impaling|Knockback|" +
//                            "Lethality|Life Steal|Looting|Luck|Mana Steal|Prosecute|Scavenger|Sharpness|Smite|Smoldering|" +
//                            "Syphon|Tabasco|Thunderlord|Titan Killer|Triple-Strike|Vampirism|Venomous|Vicious)(X|IX|VIII|VII|VI|V|IV|III|II|I)";
//
//                    //Not all ultimate enchants are probably worth tracking but i just added them for stat tracking.
//
//
//
//                    Pattern pattern = Pattern.compile(enchantPattern);
//                    Matcher matcher = pattern.matcher(loreSection);
//
//                    while (matcher.find()) {
//                        String enchantName = matcher.group(1);
//                        String romanNumeral = matcher.group(2);
//                        enchants.add(enchantName + " " + romanNumeral);
//                    }
//
//                    return enchants;
//                }
//            }
//        }
//        return new ArrayList<String>();
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

        writer.append(getAuctionID());
        writer.append(',');
        writer.append(Long.toString(getAuctionPrice()));

        for (String enchant : enchants) {
            writer.append(enchant);
            writer.append(',');
        }

        writer.append('\n');
        writer.close();
    }
}
