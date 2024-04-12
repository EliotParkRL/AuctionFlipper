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

        System.out.println(AuctionedItem.decompressGzipString("H4sIAAAAAAAAAHVVz4/aRhSe/ZGERa0210iROqoSdSmBGhsDbtWDFxPwBpsssLD4shrswQz4B7LHC+x/0EPvOfTQW6Ree+ylyp+SP6Tqs2E3aasi4RnP+773vvfmjSeP0Ak6YHmE0MEhOmTOwc8H6FEzTAJ+kEdHnLgn6JgG9hylvyN00mEOfe0RN4bXv/IoP1gmntdbBzTKoUPdQS8aZCbTqkNLtYZcK0m2QktkVq2ValRpNBxFEKszBXhvo3BFI85ofIJynG54EtE4k5FDj0bES+jBO7oOXb15IZBxxbOl/nx6rTJdC11jOFkbw0vJ1C5lU7uSes31G72pMrtzcWv5XmxdeUudqTXgzg1/UjXuLiu94WjR05bCZKhLk3F/adypQm9ob0zfnPeGrfVkq8dNprp6cL6ditZq2h71JhB374dddUbJRHRZV8003ZGxk0yu+xXbHw2s69cVcn3hWU3d7bFzwQ5G3h4nWNdzwQGbvc1s9SwGaL0S+MWQ/WMtxYO/dZaj7gOvo9a6W+UzHzInY9mbSBdzK7hMpv5I6Ep9j3ZSHVe35tAWTb+1tjR9A3/RGBtV886VzIW5mAzdrbnoLyztYmlp58wYOt5EtOaTO0MwFpcbY9xnhmZXe5qVjdbQWkwW6sbY6m8ybR0BxnOlGQg/wi7l0ROHxSuPbKE/umFEc7D4DH314X29TUmEBzasfY8/vHeqigBD46xSlxsF9C0ABjyigcvnqdkuypWyDBPlrCjJhQxZrChiWS6gEmCbEeO4OSeBTXfwivxyh4bJHi4C+mUBfXcP14hP3D1ckvZwmNx7F+tl4Z7QocTbKSEQVoGRnhWrQmFHqu8pdUkpiwVUAYJGZzSI6Y7RkDPtQBH/TZEqcrleQK+Aogeceh5z6T4JUhT3GYv3GSu1MszR1w+KcJ8Cfq9L2LtspFVBqAFV/fDe2z3HLHZCH4/A36vUaTsK10B/eIdzxqnNWRjgETqFhT5dJLc0IJzCAkJlCNmbcsIC6uBZBK6WDMQGblq+AXFIAJM6S5/2cE5REcYm4cQO/WmcmryQxzicYc58GpdxOyIBTw1E3KTZE4DEnKSYex/4E/+1F4YRHukAKoOYAoyQqkltEAJ7vsVrEmPirck2xjGPQpAFUuYsRmeAmxGfedsyHqRm8BtCgfGUEo4JHjM+pxGg0TNAEuwk1CvjLlvSGPMQTyPiphGfQkXUwGY04Pg8DJI40/ApiWIlLeLHd7/iz1orTRs+Xqnn+n/KQW8hEHh+DhbYn8HbVlNXu1i7Mtutnok7ra7RGubQsUl8in4A0H34dug5NMC7mkMXOOCu9vGX3/7vCWfwtLXhEVE5j9g04TQ+Qk/nIb9ZhVDw8MZOv+LpWc2hnB86bMZA8xOyC3eEvkxWbkQcepNJBtyjXHoBoNN2r6vdDFRNNW86LVXLoy/SCwAq4gMPYpwmHmw2NNDNOmu+lHqE8tFDY+0WHrtZK+6tq4c+TBcQFCBJINiLuiLUZUFSSlJlWi1Vp41KqQH3R8kRZ5QKdCoIU/sYnWTdxYm/ggvip2/++P1PhA7R4912wLWE/gZh2bTdyAYAAA"));
    }
}
