import java.io.IOException;
import java.util.ArrayList;

public class ApiPrinter {
    public static void main(String[] args) throws IOException {
        ArrayList<WeaponArmor> test = new ArrayList<>();
        ArrayList<WeaponArmor> test2 = new ArrayList<>();
        StopWatch timer = new StopWatch();

        ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
        test = WeaponArmor.createWeaponArmorFromApi(MainCaller.CallFinishedAuctions(), true);
//        test2 = WeaponArmor.createWeaponArmorFromApi(MainCaller.CallNewAuctions(), false);
        timer.startWatch();
        int count = 0;

        for(WeaponArmor item : test){
//          System.out.println(item.dumpJSON());
//            System.out.println();
//            System.out.println();
//            System.out.println();
//            if(!item.getName().isEmpty()){
//                System.out.println(item.getName());
//                System.out.println(item.getAuctionID());
//            }
//            System.out.println(item.getReasonableJSON());

            if(!item.getName().isEmpty()){
                count += 1;
                System.out.println(item.getName());
                System.out.println(item.getReasonableJSON());
                System.out.println("/viewauction " + item.getAuctionID());
                System.out.println(item.apiData);
                System.out.println();
                System.out.println();
            }

//            System.out.println(item.getAuctionPrice());
//            System.out.println(item.getName());
//            System.out.println(item.getAuctionID());
//            while(timer.getTimeSeconds() < 1){}
//            timer.endWatch();
//            timer.startWatch();
//            System.out.println(ApiCaller.auctionDetails(item.getAuctionID()));
//            if(!item.getName().isEmpty()){
//                item.writeArrayListToCSV("output.csv");
//            }
//            System.out.println(item.getReasonableJSON());
//            System.out.println(item.getEnchants());
        }
        System.out.println(count);
        System.out.println(test.size());
        timer.endWatch();
        timer.startWatch();
    }
}
