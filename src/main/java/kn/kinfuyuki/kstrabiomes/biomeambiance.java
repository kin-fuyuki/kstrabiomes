package kn.kinfuyuki.kstrabiomes;

import net.betterthanadventure.sound.LibraryLWJGL3OpenAL;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.FogManager;
import net.minecraft.client.sound.SoundEntry;
import net.minecraft.client.sound.SoundEvent;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.item.ItemDiscMusic;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import javax.sound.sampled.AudioSystem;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class biomeambiance extends Biome{
	public final Color morning,day,night,evening;
	public final float fogintensity;

	public static ArrayList<biomeambiance> BIOMECOLORS=new ArrayList<>();
	public final static FogManager fog=Minecraft.getMinecraft().worldRenderer.fogManager;
	public static Map<String,Map<String, File>> BGMUSIC=new HashMap<>();
	public ArrayList<File> musics=new ArrayList<>();
	public biomeambiance(String key,
						  Color morning, Color day, Color night, Color evening, float fogintensity) {
		super(key);
		this.morning = morning;
		this.day = day;
		this.night = night;
		this.evening = evening;
		this.fogintensity = fogintensity;
		BIOMECOLORS.add(this);
	}

}
