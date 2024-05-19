
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class PricePredictor {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        String name;
        String reforge;
        int starLevel;
        int numFBPs;
        boolean isRecombobulated;
        String tempEnchant;
        ArrayList<String> enchants;

        System.out.println("Enter the qualities of the item to predict the prices of");
        System.out.println("Enter the name of the item:");
        name = in.nextLine();
        System.out.println("Enter the reforge on the item:");
        reforge = in.nextLine();
        System.out.println("Enter the number of stars on the item (0-9):");
        starLevel = in.nextInt();
        System.out.println("Enter the number of fuming potato books on the item (0-5):");
        numFBPs = in.nextInt();
        System.out.println("Is your item recombobulated (true/false):");
        isRecombobulated = in.nextBoolean();
        System.out.println("Enter the enchants on your item, seperated by commas (Sharpness V, Looting IV, Scavenger II):");
        tempEnchant = in.nextLine();
        tempEnchant = in.nextLine();
        enchants = Utilities.convertToArrayList(tempEnchant);

        WeaponArmor predict = new WeaponArmor(name, reforge, starLevel, numFBPs, enchants, isRecombobulated);
        System.out.println(predict.getPredictedPrice());

    }
}
