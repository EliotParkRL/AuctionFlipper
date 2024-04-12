import java.io.IOException;
import java.util.ArrayList;

public class ApiPrinter {
    public static void main(String[] args) throws IOException {
        ArrayList<AuctionedItem> test = new ArrayList<>();

        ApiCaller MainCaller = new ApiCaller("f43b2f7b-affd-4d71-b51b-a3ee3111657f");
        test = AuctionedItem.createAuctionedItemsFromApi(MainCaller.CallFinishedAuctions());

        for(AuctionedItem item : test){
//            System.out.println(item.dumpJSON());
            System.out.println(item.printReasonableJSON());
        }

        System.out.println(AuctionedItem.decompressGzipString("H4sIAAAAAAAAAG1TXWsTQRS9aRLbRPAbBQW5ogUlxm6+Yx+EmMamEKu0sSAiZXb37mbIfoSdCZpH/0BB8EXxPb75D3zIH/A/5IeId3fbWsWHHXbOnHvOmTszRYACZGQRADIrsCLtTCkD+W44DXSmCFkt3AycfxWYEYmxMD3KZKHQlzY984SruOhXEVZtqSaemBUgNwgjWmP0FtxYzFvdSGrcEr5waRMXc6vUMNbhCi/0SXh6FGOiVK0ZCbZFDgWKUrDZgEuM7U+I7BSpVOE6IzuBJs+TLgXWMbUBVxk/kFp4Us9Oybf5p/2Gv+WXTzy+/WfKGS8s5uZzEQh8GYYeHsA6y2xHItCKuWapYhjLr0d41pAJj5KyEzfcOQA8W1Zn6+Xnb3hCSCoA7rLlUFJENj4Ng6naxP7MjgTu60iOCe8bG/UHsBFveGpZpJQz9VBEUfgOR5J13Vg+bmAlNlNaWGMMHbjJk8W8+ZfU8sPHxDPLY6LYE9YI/8P6o+SLMSmchdMI2gwmvgodbxY71oz1mOkIpSl6iCKw0ebDS06zkq75fOZQ5B87Oelj700eOmlo49QrtUGTN+bIiOKO2dV4ld5rTpdaHws0eRiEirCCaU7hcIRYsB7XtUIHg1CjK2QgAxdFyoq7jUlf2sORVCg1+WiJAE3CiJwwcsm+E3eO5b1Bb7u3u9XZe43dfm9/+HLQGfaykLdCL4zgSf77GuR2hU9wjelD4nARdkekNF92zVuGi704dkdzU82pJlWEojidZGHVp8DmbBwpx6/G57t2OOG7xvM8rMVvDS4Pe3t7L/YOz/oXzFCpQ83XJS7kDNMpM+8JYYuW2bLLZDRb5XrVqZWF87hZbjp1MsyGY7VtOwcFLX1OKPwJv+ejnz9qLLIC59JHyHrwG4n6yUrwAwAA"));
    }
}
