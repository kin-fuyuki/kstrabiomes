package kn.kinfuyuki.kstrabiomes;

import net.betterthanadventure.sound.LibraryLWJGL3OpenAL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FogManager;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundEvent;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.item.ItemDiscMusic;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.BiomeDesert;
import net.minecraft.core.world.biome.Biomes;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class biomeambiance extends BiomeDesert {
	public final Color morning,day,night;
	public final float fogintensity;

	public static Map<String,Map<String, File>> BGMUSIC=new HashMap<>();
	public ArrayList<AudioInputStream> musics=new ArrayList<>();
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
