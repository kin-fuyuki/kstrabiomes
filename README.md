# KSTRABIOMES
extra biomes + custom biomes configuration + ambiance mod

the biomes must be stored under:
.minecraft/kstrabiomes/\<choose a name for your biome package\>/\<biome name\>.tdf

[tiny data format reference](https://plugins.jetbrains.com/plugin/29360-tiny-lang/)

# instructions
### requirements to run:
[download this jar file](https://github.com/kin-fuyuki/tiny-java/releases/download/0.8_1/tiny-java21-0.8_1.jar)
and on multimc/prism you click on:
edit instance -> version -> add to minecraft.jar
select the tiny library jar file.
done now you can run!
### creating biomes:
biome example (all variables must exist and be customized):

    # the generator class for example
    " generator net.minecraft.core.world.BiomeDesert
    B custombiomeclass T
    
    # generation settings
    f mintemperature 0.0
    f minhumidity 0.0
    f minaltitude 0.0
    f minvariety 0.0
    
    f maxtemperature 1.6
    f maxhumidity 1.1
    f maxaltitude 1.2
    f maxvariety 1.05
    
    #this one will at times replace the original game music depending on the chance (0.0 - 1.0 range, where 1.0 is 100%)
    f chancecustommusic 0.5
    #music must be in the folder path like that: kstrabiomes/<your namespace>/*.ogg
    #example:
    #kstrabiomes/kinfuyuki/sonicomtalanspage.ogg
    S music
    sonicomtalanspage
    sonicomtyournewhome
    mistylk
    pwidungeon15
    pwietherblade17
    tlbb
    \
    
    # the grass and leaves color
    i skycolorR_MORNING 63
    i skycolorG_MORNING 63
    i skycolorB_MORNING 0
    i skycolorR_DAY 127
    i skycolorG_DAY 127
    i skycolorB_DAY 0
    i skycolorR_NIGHT 0
    i skycolorG_NIGHT 0
    i skycolorB_NIGHT 63
    
    
    f fog 2.0
    
    # block generators
    " topblock mud.baked
    " fillerblock granite
    
    # not allowed weathers like rain and snow and stuff
    S blockedweathers
    overworld.snow
    overworld.storm
    overworld.rain
    overworld.clear
    \
    B hassnow F
    
    # the tree class, you can pass any class here
    B customtree F
    " customtreeclass nah
    
    # what can spawn
    
    #how it works: net.minecraft.core.entity.type.mob frequency
    S spawnablemonsters
    net.minecraft.core.entity.monster.MobSpider 10
    net.minecraft.core.entity.monster.MobSkeleton 10
    
    \
    S spawnablecreatures
    \
    S spawnablewatercreatures
    \
    S spawnableambientcreatures
    \



# support
please request features on [here](https://github.com/kin-fuyuki/kstrabiomes/issues/new?labels=enhancement)

please report bugs on [here](https://github.com/kin-fuyuki/kstrabiomes/issues/new?labels=bug)
