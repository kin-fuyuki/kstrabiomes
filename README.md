# KSTRABIOMES
extra biomes + custom biomes configuration + ambiance mod

the biomes must be stored under:
.minecraft/kstrabiomes/\<choose a name for your biome package\>/*.tdf

[tiny data format reference](https://plugins.jetbrains.com/plugin/29360-tiny-lang/)

biome example (all variables must exist and be customized):

    # the generator class for example
    " generator net.minecraft.core.world.biome
    B custombiomeclass
    
    # generation settings
    f maxtemperature 0.01
    f maxhumidity 0.01
    f maxbiomeness 0.01
    f maxveriety 0.01
    
    f mintemperature 0.0
    f minhumidity 0.0
    f minbiomeness 0.0
    f minveriety 0.0
    
    # the grass and leaves color
    i colorR 200
    i colorG 255
    i colorB 150
    i skycolorR 0
    i skycolorG 0
    i skycolorB 150
    i fogcolorR 0
    i fogcolorG 0
    i fogcolorB 150
    
    # block generators
    " topblock minecraft:sand
    " fillerblock minecraft:sandstone
    
    # not allowed weathers like rain and snow and stuff
    S blockedweathers
    OVERWORLD_STORM
    \
    B hassnow T
    
    # the tree class, you can pass any class here
    B customtree F
    " customtreeclass nah
    
    # what can spawn
    S spawnablemonsters
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
