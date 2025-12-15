package kn.kinfuyuki.kstrabiomes.mixin;

import net.minecraft.core.world.biome.data.BiomeRangeMap;
import net.minecraft.core.world.biome.provider.BiomeProviderOverworld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BiomeProviderOverworld.class)
public interface biomeprovideroverworldaccessor {

	@Accessor("brm")
	static BiomeRangeMap getBrm(){
		throw new AssertionError();
	};

}
