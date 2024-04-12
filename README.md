# AuctionFlipper
contact calvin if you get error 403, or figure out how to fix the api key yourself https://developer.hypixel.net/dashboard
you could also leave an issue

DELETE OUT BEFORE PUSHING

Steps:
1. API call
2. Parse API output
3. a) Take an item
   b) Parse the enchants, rarity, stars, gems etc.
   c) Find auction ID and store it
4. Find price of the selected item
5. Compare the prices (threshold)
6. Return auction ID
7. Type command in chat with a forge mod
8. Sell maybe?

To Do:
1. Write data collector
2. Start collecting data
3. Write price calc
4. Write incoming auction reader
5. Write purchase decision maker
6. Write minecr-side automation
7. Run the thing

Blacklisted Items list:
- Skins
- Dyes
- Farming tools (Hoes, Dicers, Choppers)
- Obscure items (minion skins, potion brews, seasonal cosmetics)
^ Can avoid this by requiring a minimum number of sales per day or checking the avg price of previous sales
