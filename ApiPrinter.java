import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class ApiPrinter {
    public static void main(String[] args) throws IOException {
        ArrayList<AuctionedItem> test = new ArrayList<>();

        ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
        test = AuctionedItem.createAuctionedItemsFromApi(MainCaller.CallFinishedAuctions());

        for(AuctionedItem item : test){
//          System.out.println(item.dumpJSON());
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println(item.returnReasonableJSON());

        }
        DataAnalyzer data = new DataAnalyzer();
        data.analyzeData();
//        System.out.println(MainCaller.CallFinishedAuctions());
    }
}
