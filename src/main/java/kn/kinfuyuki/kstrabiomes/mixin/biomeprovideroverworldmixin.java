package kn.kinfuyuki.kstrabiomes.mixin;

import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.data.BiomeRange;
import net.minecraft.core.world.biome.data.BiomeRangeMap;
import net.minecraft.core.world.biome.provider.BiomeProviderOverworld;
import org.spongepowered.asm.mixin.*;

@Mixin(BiomeProviderOverworld.class)
public class biomeprovideroverworldmixin {
	@Shadow
	@Final
	private static BiomeRangeMap brm;

	@Overwrite
	public static void init() {
		brm.clear();
		brm.addRange(Biomes.OVERWORLD_GLACIER, new BiomeRange[]{new BiomeRange((double)0.0F, 0.3, (double)0.0F, 0.01, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_TUNDRA, new BiomeRange[]{new BiomeRange((double)0.0F, 0.3, 0.01, 0.1, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_TAIGA, new BiomeRange[]{new BiomeRange((double)0.0F, 0.3, 0.1, (double)1.0F, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_BOREAL_FOREST, new BiomeRange[]{new BiomeRange(0.3, (double)0.5F, 0.05, (double)1.0F, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_GRASSLANDS, new BiomeRange[]{new BiomeRange((double)0.5F, 0.98, (double)0.0F, 0.05, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_MEADOW, new BiomeRange[]{new BiomeRange(0.3, (double)0.5F, (double)0.0F, 0.05, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_BIRCH_FOREST, new BiomeRange[]{new BiomeRange((double)0.5F, (double)0.75F, 0.3, (double)0.5F, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_SHRUBLAND, new BiomeRange[]{new BiomeRange((double)0.5F, (double)0.75F, 0.05, 0.3, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_SEASONAL_FOREST, new BiomeRange[]{new BiomeRange((double)0.5F, (double)0.75F, (double)0.5F, (double)1.0F, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_SWAMPLAND, new BiomeRange[]{new BiomeRange((double)0.75F, 0.95, 0.85, (double)1.0F, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_FOREST, new BiomeRange[]{new BiomeRange((double)0.75F, 0.95, 0.4, 0.85, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F), new BiomeRange(0.95, (double)1.0F, 0.4, 0.85, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_PLAINS, new BiomeRange[]{new BiomeRange((double)0.75F, (double)1.0F, 0.1, 0.4, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_DESERT, new BiomeRange[]{new BiomeRange(0.85, (double)1.0F, (double)0.0F, 0.01, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_OUTBACK, new BiomeRange[]{new BiomeRange((double)0.75F, 0.9, (double)0.0F, 0.2, (double)0.0F, (double)1.0F, (double)0.0F, 0.95)});
		brm.addRange(Biomes.OVERWORLD_OUTBACK_GRASSY, new BiomeRange[]{new BiomeRange((double)0.75F, 0.9, (double)0.0F, 0.2, (double)0.0F, (double)1.0F, 0.95, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_CAATINGA, new BiomeRange[]{new BiomeRange(0.9, (double)1.0F, 0.01, 0.2, (double)0.0F, (double)1.0F, 0.1, (double)1.0F)});
		brm.addRange(Biomes.OVERWORLD_CAATINGA_PLAINS, new BiomeRange[]{new BiomeRange(0.9, (double)1.0F, 0.01, 0.2, (double)0.0F, (double)1.0F, (double)0.0F, 0.1)});
		brm.addRange(Biomes.OVERWORLD_RAINFOREST, new BiomeRange[]{new BiomeRange(0.95, (double)1.0F, 0.85, (double)1.0F, (double)0.0F, (double)1.0F, (double)0.0F, (double)1.0F)});

	}
}
