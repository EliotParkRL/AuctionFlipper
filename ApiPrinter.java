import java.io.IOException;
import java.util.ArrayList;

public class ApiPrinter {
    public static void main(String[] args) throws IOException {
        ArrayList<WeaponArmor> test = new ArrayList<>();
        ArrayList<WeaponArmor> test2 = new ArrayList<>();
        StopWatch timer = new StopWatch();

        ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
        String finishedAuctions = "";
        String temp = "";
//        test2 = WeaponArmor.createAuctionedItemsFromApi(MainCaller.CallNewAuctions(), false);
        int count = 0;
        StopWatch watch = new StopWatch();
        watch.startWatch();

        while(true){
            while(watch.getTimeSeconds() < 8){}
            finishedAuctions = MainCaller.CallFinishedAuctions();
            if(!finishedAuctions.equals(temp)){
                temp = finishedAuctions;
                test = WeaponArmor.createAuctionedItemsFromApi(finishedAuctions, true);
                for(WeaponArmor item : test){
                    if(!item.getName().isEmpty()){
//                    count++;
//                    System.out.println(item.getName());
//                System.out.println(item.getReasonableJSON());
//                    System.out.println(item.numFPBS);
//                    System.out.println("/viewauction " + item.getAuctionID());
//                    System.out.println(item.apiData);
//                    System.out.println(item.getStarLevel());
                        item.writeArrayListToCSV("output.csv");
//                    System.out.println();
//                    System.out.println();
                    }
                }
            }
        }

    }
}
