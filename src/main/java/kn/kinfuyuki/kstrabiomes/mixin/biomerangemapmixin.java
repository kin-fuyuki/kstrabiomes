package kn.kinfuyuki.kstrabiomes.mixin;

import kn.kinfuyuki.kstrabiomes.biomerangemapacessor;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.data.BiomeRange;
import net.minecraft.core.world.biome.data.BiomeRangeMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
@Mixin(BiomeRangeMap.class)
public class biomerangemapmixin implements biomerangemapacessor {
	@Shadow @Final
	private Map<Biome, Set<BiomeRange>> ranges;
	@Override
	public Map<Biome, Set<BiomeRange>> kstrabiomes$getRanges() {
		return ranges;
	}
}
