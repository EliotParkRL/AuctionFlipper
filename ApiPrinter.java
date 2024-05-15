import java.io.IOException;
import java.util.ArrayList;

public class ApiPrinter {
    public static void main(String[] args) throws IOException {
        ArrayList<WeaponArmor> test = new ArrayList<>();
        ArrayList<WeaponArmor> test2 = new ArrayList<>();
        StopWatch timer = new StopWatch();

        ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
        test = WeaponArmor.createAuctionedItemsFromApi(MainCaller.CallFinishedAuctions(), true);
        test2 = WeaponArmor.createAuctionedItemsFromApi(MainCaller.CallNewAuctions(), false);
        timer.startWatch();

        for(WeaponArmor item : test){
//          System.out.println(item.dumpJSON());
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.print(item.getName());
//            System.out.println(item.getAuctionPrice());
//            System.out.println(item.getName());
//            System.out.println(item.getAuctionID());
//            while(timer.getTimeSeconds() < 1){}
//            timer.endWatch();
//            timer.startWatch();
//            System.out.println(ApiCaller.auctionDetails(item.getAuctionID()));
//            item.writeArrayListToCSV("output.csv");
//            System.out.println(item.getReasonableJSON());
//            System.out.println(item.getEnchants());
        }
        System.out.println(test.size());

//        System.out.println(ApiCaller.auctionDetails("6629fe12f188442bb9548d35d5e42cb3"));
    }
}
