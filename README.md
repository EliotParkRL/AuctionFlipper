# AuctionFlipper
contact calvin if you get error 403, or figure out how to fix the api key yourself https://developer.hypixel.net/dashboard
you could also leave an issue

DELETE OUT BEFORE PUSHING

Steps:
**1. API call
2. Parse API output
3. a) Take an item
   b) Parse the enchants**
   c) Recombs
   d) Stars
   e) Ultimate Enchants
   f) Attributes

**Parsing item data:**
**1. Remove all color tags (§ + num)**
**2. Remove all non-printable characters (�)**
**3. Look at enchants through "Lor" tag not "enchants" tag**

   c) Find auction ID and store it
   
5. Find price of the selected item
6. Compare the prices (threshold)
7. Return auction ID
8. Type command in chat with a forge mod
9. Sell maybe?

To Do:
1. Write data collector
2. Start collecting data
3. Write price calc
4. Write incoming auction reader
5. Write purchase decision maker
6. Write minecraft-side automation
7. Run the thing

Blacklisted Items list:
- Skins
- Dyes
- Farming tools (Hoes, Dicers, Choppers)
- Obscure items (minion skins, potion brews, seasonal cosmetics)
^ Can avoid this by requiring a minimum number of sales per day or checking the avg price of previous sales
