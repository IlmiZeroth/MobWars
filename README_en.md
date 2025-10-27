# MobWars
<img width="700" height="420" alt="image" src="https://github.com/user-attachments/assets/baf6a798-41e7-4a1f-9e0d-6d363ac04095" />


**MobWars** is a **Paper minigame plugin** where players are divided into two teams.  
Each team summons waves of mobs that march toward the enemy base — if a mob reaches the opponent’s base, it deals damage to it.

Players can earn in-game currency by killing enemy mobs.  
Between waves, this currency can be spent on **armor, weapons, and upgrades**, allowing teams to strengthen their defenses or boost their offense.

> ⚠️ Currently, the plugin only works on **Paper 1.21.7**.   It has **no
> configuration options** and is **not well-balanced yet**.   I plan to
> improve balance and add customization features in future updates — if
> players show interest in the project.


## 🧩 Installation

1.  Download the **latest version** of the MobWars plugin.
    
2.  Place the `.jar` file into your `plugins` folder.
    
3.  Download the required dependency plugin — [**BetterModels**](https://github.com/toxicity188/BetterModel) — and put it in the same `plugins` folder.
    
4.  Download the **BlockMobs resource pack** from the latest release to display the mob models correctly in-game.
    
5.  Install the resource pack manually **or** use [**MCPacks**](https://mc-packs.net) to host it and configure it via your `server.properties` file:
    

`require-resource-pack=true`  
`resource-pack=<Add your generated URL from MCPacks>`
`resource-pack-sha1=<Add your generated SHA1 from MCPacks>`

## 🚀 How to start
After installation, you need to prepare a location for your game map.

Start by using the command:
`/mwa addmap <maxPlayers> <name>`
Like in the image below:
<img width="621" height="144" alt="image" src="https://github.com/user-attachments/assets/915f8253-3ad1-460d-905e-a20f73b01146" />

This will start a map creation session.
Next, use these four commands in any order to define the map positions:
- `/mwa fteamspawn`
- `/mwa steamspawn`
- `/mwa fteambase` 
- `/mwa steambase`

> TeamSpawn is the location where mobs spawn — not players.
> Players spawn at teamBase locations.
> Mobs also need to reach the enemy teamBase to deal damage.

If you want to cancel the map creation process, use:
`/mwa cancel`

Once the map is ready, you can create a game sign.

Place a sign (look directly at it) and run:
`/mwa createsign <name>`
<img width="1000" height="800" alt="2025-10-28_01 33 37_4K" src="https://github.com/user-attachments/assets/33c5af4d-1549-4849-852a-b65709ef144f" />
That’s it — your map is ready, and players can now join and play!

## 🔑 Permissions

- `mobwars.admin`
Allows using the `/mwa` command and breaking game signs

## 💬 Commands
 - `/mwa addmap <maxPlayers> <name>` Start creating a new map
 - `/mwa fteamspawn` Set the first team’s spawn location
 - `/mwa steamspawn` Set the second team’s spawn location
 - `/mwa fteambase` Set the first team’s base location (where mobs need to arrive)
 - `/mwa steambase` Set the second team’s base location
 - `/mwa cancel` Cancel the latest map creation session
 - `/mwa createsign <name>` Create a game sign for the specified map
