package kn.kinfuyuki.kstrabiomes.biome;

import net.minecraft.core.world.biome.BiomeDesert;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class biomeambiance extends BiomeDesert {
	public final Color morning,day,night;
	public final float fogintensity;
	public static Map<String,Map<String, File>> BGMUSIC=new HashMap<>();
	public ArrayList<File> musics=new ArrayList<>();
	public final float musicchance;
	public biomeambiance(String key,
						  Color morning, Color day, Color night, float fogintensity,float musicchance) {
		super(key);
		this.morning = morning;
		this.day = day;
		this.night = night;
		this.fogintensity = fogintensity;
		this.musicchance=musicchance;
	}

}
