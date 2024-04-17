import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoreParser {

    public static ArrayList<String> parseLore(String lore) {
        ArrayList<String> enchants = new ArrayList<>();

        // Define regex pattern to match enchantments
        String enchantPattern = "(Bane of Arthropods|Champion|Cleave|Critical|Cubism|Divine Gift|Dragon Hunter|" +
                "Ender Slayer|Execute|Experience|Fire Aspect|First Strike|Giant Killer|Impaling|Knockback|" +
                "Lethality|Life Steal|Looting|Luck|Mana Steal|Prosecute|Scavenger|Sharpness|Smite|Smoldering|" +
                "Syphon|Tabasco|Thunderlord|Titan Killer|Triple-Strike|Vampirism|Venomous|Vicious) (V|IV|III|II|I)";
        Pattern pattern = Pattern.compile(enchantPattern);
        Matcher matcher = pattern.matcher(lore);

        while (matcher.find()) {
            String enchantName = matcher.group(1);
            String romanNumeral = matcher.group(2);
            enchants.add(enchantName + " " + romanNumeral);
        }

        return enchants;
    }

    public static void main(String[] args) {
        String input = "item_bytes= i id Count tag ench Unbreakable HideFlags display Lore Gear Score d Damage c e Strength c e d Crit Damage c d d l d lUltimate Wise V Cleave V Critical V Cubism V Ender Slayer V Execute V Experience III Fire Aspect II First Strike IV Giant Killer V Impaling III Lethality V Life Steal III Looting III Luck V Scavenger III Sharpness V Thunderlord V Vampirism V Venomous V b Spirit Rune I Ability Dragon Rage e lRIGHT CLICK All Monsters in front of you take a damage Hit monsters take large knockback Mana Cost fKills Withered Bonus Grants a c Strength per cCatacombs level d l ka r d l d lMYTHIC DUNGEON SWORD d l ka Name dWithered Aspect of the Dragons ✪ ✪ ✪ ✪ ✪ ExtraAttributes rarity upgrades stats book Q upgrade level originTag BOSS DROP enchantments impaling luck critical cleave looting telekinesis scavenger ender slayer fire aspect experience vampirism ultimate wise sharpness cubism lethality uuid e d d e e b d e e d hot potato count gems JASPER FINE spawnedFor b b b d d e e d b d id ASPECT OF THE DRAGON timestamp Z Damage , auction_id=8dab2a3fdfcd4fcda0554b3865053d8f";

        int displayIndex = input.indexOf("display");
        if (displayIndex != -1) {
            int loreIndex = input.indexOf("Lore", displayIndex);
            if (loreIndex != -1) {
                int endOfLoreIndex = input.indexOf("b ", loreIndex); // assuming 'b ' is the end delimiter
                if (endOfLoreIndex != -1) {
                    String loreSection = input.substring(loreIndex, endOfLoreIndex);
                    ArrayList<String> enchants = parseLore(loreSection);

                    System.out.println(enchants);
                }
            }
        }
    }
}
