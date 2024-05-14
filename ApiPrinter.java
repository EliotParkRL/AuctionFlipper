import java.io.IOException;
import java.util.ArrayList;

public class ApiPrinter {
    public static void main(String[] args) throws IOException {
        ArrayList<WeaponArmor> test = new ArrayList<>();
        ArrayList<WeaponArmor> test2 = new ArrayList<>();

        ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
        test = WeaponArmor.createAuctionedItemsFromApi(MainCaller.CallFinishedAuctions(), true);
        test2 = WeaponArmor.createAuctionedItemsFromApi(MainCaller.CallNewAuctions(), false);

        for(WeaponArmor item : test2){
//          System.out.println(item.dumpJSON());
            System.out.println();
            System.out.println("----------------------------");
            System.out.println();
            System.out.println(item.getAuctionPrice());
            System.out.println(item.getEnchants());
            System.out.println(item.getAuctionID());
            item.writeArrayListToCSV("output.csv");
//            System.out.println(item.getReasonableJSON());
//            System.out.println(item.getEnchants());
        }
        System.out.println(test.size());

        System.out.println(ApiCaller.auctionDetails("cdae7cc791de420ca8fe4855dcd2fef6"));
    }
}
