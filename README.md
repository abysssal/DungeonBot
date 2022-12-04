# DungeonBot
![DungeonBot Logo](https://user-images.githubusercontent.com/83674439/200425189-264a84ac-db2d-4b7e-9276-eb3da76577e6.png)

DungeonBot is a bot that generate dungeons for your discord server members to crawl around. They can find weapons, enemies, traps, and so much more! Your inventory  autosaves so you can focus on getting more loot. It is inspired by Polymars's SokoBot and the mobile game CyberCode Online, with its dungeon system. 

## How to run
Sadly, you have to host the bot yourself, but there are tutorials that should teach you how to do so.
It is recommended that you used IntelliJ IDEA to run the source code, as that is what I used to actually make the bot.

To implement, do the following:
1. Download the source code
2. Make a new developer application at https://discord.com/developers/applications/
3. Make it a bot and invite it to your server
4. Make a .env file
5. In the TOKEN field, put the token of the discord bot in your application after you've made it, you may need to reset it
6. In the GUILD_ID field, right click the discord server the bot is in and place it there, this may require you to turn Developer Mode on
7. Run the DiscordBot.java file, do NOT just run the entire program folder
8. The bot should run, as far as I know, but if not, submit an issue on Github

## Commands
`/info` - gives a basic descriptions on the bot

`/createnewdungeon [rooms] [difficulty]` - Creates a new dungeon for you to go through, with the selected amount of rooms and difficulty. The harder the difficulty, the more quantity and quality of the loot, though the harder the monsters are. You can only achieve loot if you make it through the dungeon without dying.
