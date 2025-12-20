package kn.kinfuyuki.kstrabiomes.biome;

import net.minecraft.core.block.Blocks;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.provider.BiomeProvider;
import net.minecraft.core.world.biome.provider.BiomeProviderOverworld;
import net.minecraft.core.world.config.season.SeasonConfig;
import net.minecraft.core.world.season.Seasons;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.overworld.WorldTypeOverworld;

public class biggerbiomestype extends WorldTypeOverworld {
	public biggerbiomestype() {
		super(defaultProperties("worldType.overworld.sparse"));
	}
	@Override
	public BiomeProvider createBiomeProvider(World world) {
		return new biggerbiomesoverworld(world.getRandomSeed(), this);

	}

	public static WorldType.Properties defaultProperties(String translationKey) {
		return Properties.of(translationKey).brightnessRamp(createLightRamp()).seasonConfig(SeasonConfig.builder().withSeasonInCycle(Seasons.OVERWORLD_SPRING, 14).withSeasonInCycle(Seasons.OVERWORLD_SUMMER, 14).withSeasonInCycle(Seasons.OVERWORLD_FALL, 14).withSeasonInCycle(Seasons.OVERWORLD_WINTER, 14).build()).oceanBlock(Blocks.FLUID_WATER_STILL).fillerBlock(Blocks.STONE).allowRespawn();
	}
	public static float[] createLightRamp() {return WorldTypeOverworld.createLightRamp();}
}
