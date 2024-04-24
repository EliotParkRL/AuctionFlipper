import java.util.ArrayList;

public class AutoFlipper {
    public static void main(String[] args) {
        while(true){
            //get recent auctions
            //create auctioneditems objects
            ArrayList<AuctionedItem> mainArrayList = new ArrayList<>();

            ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
            mainArrayList = AuctionedItem.createAuctionedItemsFromApi(MainCaller.CallFinishedAuctions(), true);

            for(AuctionedItem item : mainArrayList){
                if (item.getAuctionPrice() < .85*item.getPredictedPrice()){
                    //CommandSender.executeCommand("/viewauction " + item.getAuctionID());
                }
                if (true){
                    //CommandSender.executeCommand("/viewauction " + item.getAuctionID());
                }
            }
            //compare prices
            //buy anything with a 15% discrepancy - get ah id, create pastable string
            //get sell prices
            //post auction
            //wait a second or two
        }
    }
}
