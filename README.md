# HawyCore
HawyCore is an essentials plugin made for my community vanilla Minecraft server, which includes homes, sign-shops, teleportation features, and more!

## Features
* Highly configurable using config.yml
* Essential features including player homes, teleportation, sign shops, and half player sleeping!
* Anti cheat cooldown periods on teleportation features (teleporting home or to a player)
* Colourful live MOTD


<img src="https://github.com/Tezk/TezkCore/blob/58c2fe214d24307f42b3e45425f9560ab853d187/tezkcoremotd.gif" width="450" height="300" /><img src="https://user-images.githubusercontent.com/13305898/145488121-a99e5513-91b9-4368-8bca-cf45f8470e31.gif" width="500" height="300" />
<img src="https://user-images.githubusercontent.com/13305898/145489758-eb5775ae-7f50-416e-a0e7-5335338180a9.gif" width="500" height="300" />
<img src="https://user-images.githubusercontent.com/13305898/145491331-deee2638-c565-4e3b-acf2-6fffa3dc277b.gif" width="450" height="300" />

## Commands
* /sethome - set a home to teleport back to!
* /home - teleport to your home!
* /tpa <player> - send a teleport request to an online player
* /tpaaccept - accept an open teleport request

## Configuration (config.yml)
```yaml
  motd:
  message: Welcome to my Minecraft server!
  colourful: true
  colours:
    - RED
    - LIGHT_PURPLE
    - DARK_PURPLE
    - WHITE
    - YELLOW
    - AQUA
    - BLUE
    - GOLD
    - GREEN
    - DARK_GREEN
    - DARK_RED
teleporting-home-cost:
  - AIR;0
sound-effect:
  type: ENDERDRAGON_GROWL
  duration: 20
particle:
  type: PORTAL
  size: 100000
shop:
  #must be on the second line
  activating-click-text: "&l-- [SHOP] --"
  items:
    elytra:
      name: '&dEly&4tra'
      buying-item: ELYTRA
      amount: 1
      cost:
        - DIAMOND;60
        - BLAZE_ROD;12
    zombie-spawner:
      name: '&fZombie Spawner'
      buying-item: SPAWNER
      # options include 'Zombie Spawner' or 'Skeleton Spawner'
      spawner-type: Zombie Spawner
      amount: 1
      cost:
        - DIAMOND;60
        - EMERALD;32
    skeleton-spawner:
      name: '&fSkele Spawner'
      buying-item: SPAWNER
      # options include 'Zombie Spawner' or 'Skeleton Spawner'
      spawner-type: 'Skeleton Spawner'
      amount: 1
      cost:
        - DIAMOND;60
        - EMERALD;32
```

  
 
 
