package kn.kinfuyuki.kstrabiomes;

import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.data.BiomeRange;

import java.util.Map;
import java.util.Set;

public interface biomerangemapacessor {
	Map<Biome, Set<BiomeRange>> kstrabiomes$getRanges();

}
