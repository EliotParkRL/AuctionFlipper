import java.util.ArrayList;

public class ApiPrinter {
    public static void main(String[] args) {
        ArrayList<AuctionedItem> test = new ArrayList<>();

        ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
        test = AuctionedItem.createAuctionedItemsFromApi(MainCaller.CallFinishedAuctions());
        for(AuctionedItem item : test){
            System.out.println(item.dumpJSON());
            System.out.println(item.printReasonableJSON());
        }
    }
}
