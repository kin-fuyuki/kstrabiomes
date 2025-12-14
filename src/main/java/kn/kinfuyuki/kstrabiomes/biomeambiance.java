package kn.kinfuyuki.kstrabiomes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FogManager;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;

import java.awt.*;
import java.util.Map;

public class biomeambiance {
	public final Color morning,day,night,evening;
	public final float fogintensity;
	public static Map<Biome, biomeambiance> BIOMECOLORS;
	public final static FogManager fog=Minecraft.getMinecraft().worldRenderer.fogManager;

	private biomeambiance(Biome biome,
						  Color morning, Color day, Color night, Color evening, float fogintensity) {
		this.morning = morning;
		this.day = day;
		this.night = night;
		this.evening = evening;
		this.fogintensity = fogintensity;
		BIOMECOLORS.put(biome, this);
	}
}
