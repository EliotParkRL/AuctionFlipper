import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ApiPrinter {
    public static void main(String[] args) throws IOException {
        ArrayList<AuctionedItem> test = new ArrayList<>();
        ArrayList<AuctionedItem> test2 = new ArrayList<>();

        ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
        test = AuctionedItem.createAuctionedItemsFromApi(MainCaller.CallFinishedAuctions(), true);
        test2 = AuctionedItem.createAuctionedItemsFromApi(MainCaller.CallNewAuctions(), false);

        for(AuctionedItem item : test2){
          System.out.println(item.dumpJSON());
            System.out.println();
            System.out.println();
            System.out.println();

            item.writeArrayListToCSV("output.csv",item.getAuctionPrice());
            System.out.println(item.getAuctionID());
//            System.out.println(item.getReasonableJSON());
//            System.out.println(item.getEnchants());
        }
        System.out.println(test.size());
    }
}
