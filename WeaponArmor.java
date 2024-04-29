import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeaponArmor extends AuctionedItem{
    ArrayList<String> enchants = new ArrayList<>();


    public WeaponArmor(String jsonData, boolean sold){
        super(jsonData, sold);
        enchants = returnEnchants();
    }


    public ArrayList<String> getEnchants(){
        return enchants;
    }
    private ArrayList<String> returnEnchants(){
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

    public void writeArrayListToCSV(String csvFilePath) throws IOException {
        FileWriter writer = new FileWriter(csvFilePath,true);
        for (String enchant : enchants) {
            writer.append(enchant);
            writer.append(',');
        }

        writer.append(Integer.toString(getAuctionPrice()));
        writer.append(',');
        writer.append(getAuctionID());
        writer.append('\n');
        writer.close();
    }
}
