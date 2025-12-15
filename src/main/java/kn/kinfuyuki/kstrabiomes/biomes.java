package kn.kinfuyuki.kstrabiomes;

import kn.kinfuyuki.kstrabiomes.mixin.biomeprovideroverworldaccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.enums.MobCategory;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.biome.data.BiomeRange;
import net.minecraft.core.world.biome.data.BiomeRangeMap;
import net.minecraft.core.world.biome.provider.BiomeProviderOverworld;
import net.minecraft.core.world.weather.Weather;
import net.minecraft.core.world.weather.Weathers;
import org.slf4j.Logger;
import tiny.TDF_FILE;
import tiny.tdf.TDF_ERR;
import tiny.tdf.TDF_TYPE;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.List;
import java.util.function.BiConsumer;

import static kn.kinfuyuki.kstrabiomes.biomeambiance.BGMUSIC;
import static kn.kinfuyuki.kstrabiomes.main.getmc;
import static tiny.TERM.fatal;

public class biomes {
	public static Map<String,Map<String,biomeambiance>> XTRABIOMES=new HashMap<>();
	public static Map<String,Map<String, Icon>> XTRABIOMESICONS=new HashMap<>();
	public static class biomedata{
		public final String generator;
		public final boolean custombiomeclass;
		public final double mintemperature, minhumidity, minaltitude, minvariety;
		public final double maxtemperature, maxhumidity, maxaltitude, maxvariety;
		public final double fog,chancecustommusic;

		public final Color skycolorMORNING;
		public final Color skycolorDAY;
		public final Color skycolorNIGHT;
		public final String topblock;
		public final String fillerblock;
		public final String[] blockedweathers,music;
		public final boolean hassnow;
		public final boolean customtree;
		public final String customtreeclass;
		public final String[] spawnablemonsters, spawnablecreatures,
				spawnablewatercreatures, spawnableambientcreatures;
		public biomedata(String namespace,String filepath,Logger logger) throws TDF_ERR {

			TDF_FILE file=new TDF_FILE(
				getmc().toPath()
					.resolve("kstrabiomes").resolve(namespace).resolve(filepath)
					.toString()
			);
			try {
				file.load();
			} catch (TDF_ERR e) {
				logger.error("could not import "+filepath);
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error("could not import "+filepath);
			}
			custombiomeclass=file.getbool(Arrays.asList("custombiomeclass"));
			if (custombiomeclass)generator=file.getstring(Arrays.asList("generator"));
			else generator="";
			mintemperature = file.getfloat(Arrays.asList("mintemperature"));
			minhumidity = file.getfloat(Arrays.asList("minhumidity"));
			minaltitude = file.getfloat(Arrays.asList("minaltitude"));
			minvariety = file.getfloat(Arrays.asList("minvariety"));
			fog=file.getfloat(Arrays.asList("fog"));
			maxtemperature = file.getfloat(Arrays.asList("maxtemperature"));
			maxhumidity = file.getfloat(Arrays.asList("maxhumidity"));
			maxaltitude = file.getfloat(Arrays.asList("maxaltitude"));
			maxvariety = file.getfloat(Arrays.asList("maxvariety"));
			chancecustommusic=file.getfloat(Arrays.asList("chancecustommusic"));
			skycolorMORNING=new Color(
				file.getint(Arrays.asList("skycolorR_MORNING")),
				file.getint(Arrays.asList("skycolorG_MORNING")),
				file.getint(Arrays.asList("skycolorB_MORNING"))
			);
			skycolorDAY = new Color(
				file.getint(Arrays.asList("skycolorR_DAY")),
				file.getint(Arrays.asList("skycolorG_DAY")),
				file.getint(Arrays.asList("skycolorB_DAY"))
			);

			skycolorNIGHT = new Color(
				file.getint(Arrays.asList("skycolorR_NIGHT")),
				file.getint(Arrays.asList("skycolorG_NIGHT")),
				file.getint(Arrays.asList("skycolorB_NIGHT"))
			);


			topblock = file.getstring(Arrays.asList("topblock"));
			fillerblock = file.getstring(Arrays.asList("fillerblock"));

			String blockedraw = file.getstring(Arrays.asList("blockedweathers"));
			if (blockedraw == null) blockedraw = "";
			blockedweathers = blockedraw.split("\\r?\\n");
			hassnow = file.getbool(Arrays.asList("hassnow"));

			customtree = file.getbool(Arrays.asList("customtree"));
			if (customtree) {
				customtreeclass = file.getstring(Arrays.asList("customtreeclass"));
			} else {
				customtreeclass = "";
			}
			String monstersraw = file.getstring(Arrays.asList("spawnablemonsters"));
			if (monstersraw == null) monstersraw = "";
			spawnablemonsters = monstersraw.split("\\r?\\n");
			String musicraw = file.getstring(Arrays.asList("music"));
			if (musicraw == null) musicraw = "";
			music = musicraw.split("\\r?\\n");

			String creaturesraw = file.getstring(Arrays.asList("spawnablecreatures"));
			if (creaturesraw == null) creaturesraw = "";
			spawnablecreatures = creaturesraw.split("\\r?\\n");

			String waterraw = file.getstring(Arrays.asList("spawnablewatercreatures"));
			if (waterraw == null) waterraw = "";
			spawnablewatercreatures = waterraw.split("\\r?\\n");

			String ambienttaw = file.getstring(Arrays.asList("spawnableambientcreatures"));
			if (ambienttaw == null) ambienttaw = "";
			spawnableambientcreatures = ambienttaw.split("\\r?\\n");



		}

	}
	public static void registermusic(Logger logger){
		File folder=new File(Minecraft.getMinecraft().getMinecraftDir(),"kstrabiomes");
		for (File f:folder.listFiles()
		) {
			String namespace=f.getName();
			BGMUSIC.put(namespace,new HashMap<>());
			for (File file:f.listFiles()
			) {
				String extension=file.getName().substring(
					file.getName().lastIndexOf(".")+1);
				String nam=file.getName().substring(0,
					file.getName().lastIndexOf("."));
				if (extension.equals("wav")){
					logger.warn("generating music: "+namespace+" "+nam);
					{
						try {
							AudioInputStream aud = AudioSystem.getAudioInputStream(file);
						} catch (Exception e) {
							logger.error(e.getMessage());
							continue;
						}
					}
					BGMUSIC.get(namespace).put(nam,file);
				}
			}
		}
	}
	public static void registerbiomes(Logger logger){

		File folder=new File(getmc(),"kstrabiomes");
		for (File f:folder.listFiles()
			 ) {
			String namespace=f.getName();
			XTRABIOMESICONS.put(namespace,new HashMap<>());
			XTRABIOMES.put(namespace,new HashMap<>());
			for (File file:f.listFiles()
				 ) {
				String extension=file.getName().substring(
					file.getName().lastIndexOf(".")+1);
				String nam=file.getName().substring(0,
					file.getName().lastIndexOf("."));

				if (extension.equals("tdf")) {
					logger.warn("generating biome: "+"kstrabiomes:overworld."+namespace+"_"+nam);
					biomedata data;
					try {
						data = new biomedata(namespace, file.getName(),logger);
					} catch (TDF_ERR e) {
						logger.error(e.getMessage());
						continue;
					}
					biomeambiance biome=new biomeambiance(nam,
					data.skycolorMORNING,data.skycolorDAY,data.skycolorNIGHT, (float) data.fog, (float) data.chancecustommusic
					);
					if (!main.ISSERVER){
						for (String mus : data.music
						) {
							logger.error(mus);
							File music = BGMUSIC.get(namespace).get(mus);
							if (music != null) {
								try {
									biome.musics.add(AudioSystem.getAudioInputStream(music));
								} catch (Exception e) {
									logger.error(e.getMessage());

								}
							} else
								logger.error("music " + mus + " described on file " + file.toString() + " does not exist");
						}
					}
					{
						ArrayList<Weather> blocked = new ArrayList<>();
						for (String w : data.blockedweathers
						) {
							Weather weather = Weathers.getWeatherByLanguageKey(w);
							if (w != null) blocked.add(weather);
							else
								logger.error("weather " + w + " described on file " + file.toString() + " does not exist");
						}
						biome.blockedWeathers= blocked.toArray(new Weather[0]);
					}
					biome.topBlock= Blocks.keyToIdMap.get("tile."+data.topblock).shortValue();
					biome.fillerBlock= Blocks.keyToIdMap.get("tile."+data.fillerblock).shortValue();

					{
						List<SpawnListEntry>spawnableMonsterList=biome.getSpawnableList(MobCategory.monster);
						spawnableMonsterList.clear();
						for (String m : data.spawnablemonsters
						) {
							String[] parts = m.split(" ");

							Class<? extends Mob> mob;
							try {
								mob= (Class<? extends Mob>) Class.forName(parts[0]);
							} catch (Exception e) {
								logger.error("mob " + parts[0] + " described on file " + file.toString() + " does not exist");
								continue;
							}
							spawnableMonsterList.add(new SpawnListEntry(mob,Integer.parseInt(parts[1])));
						}
					}
					{
						List<SpawnListEntry>spawnableMonsterList=biome.getSpawnableList(MobCategory.ambientCreature);
						spawnableMonsterList.clear();
						for (String m : data.spawnableambientcreatures
						) {
							String[] parts = m.split(" ");

							Class<? extends Mob> mob;
							try {
								mob= (Class<? extends Mob>) Class.forName(parts[0]);
							} catch (Exception e) {
								logger.error("mob " + parts[0] + " described on file " + file.toString() + " does not exist");
								continue;
							}
							spawnableMonsterList.add(new SpawnListEntry(mob,Integer.parseInt(parts[1])));
						}
					}
					{
						List<SpawnListEntry>spawnableMonsterList=biome.getSpawnableList(MobCategory.creature);
						spawnableMonsterList.clear();
						for (String m : data.spawnablecreatures
						) {
							String[] parts = m.split(" ");

							Class<? extends Mob> mob;
							try {
								mob= (Class<? extends Mob>) Class.forName(parts[0]);
							} catch (Exception e) {
								logger.error("mob " + parts[0] + " described on file " + file.toString() + " does not exist");
								continue;
							}
							spawnableMonsterList.add(new SpawnListEntry(mob,Integer.parseInt(parts[1])));
						}
					}
					{
						List<SpawnListEntry>spawnableMonsterList=biome.getSpawnableList(MobCategory.waterCreature);
						spawnableMonsterList.clear();
						for (String m : data.spawnablewatercreatures
						) {
							String[] parts = m.split(" ");

							Class<? extends Mob> mob;
							try {
								mob= (Class<? extends Mob>) Class.forName(parts[0]);
							} catch (Exception e) {
								logger.error("mob " + parts[0] + " described on file " + file.toString() + " does not exist");
								continue;
							}
							spawnableMonsterList.add(new SpawnListEntry(mob,Integer.parseInt(parts[1])));
						}
					}


					XTRABIOMES.get(namespace).put(nam,(biomeambiance) Biomes.register(
						"kstrabiomes:overworld."+namespace+"_"+nam,
						biome
					));
					BiomeRangeMap map = biomeprovideroverworldaccessor.getBrm();
					map.addRange(
						biome,
						new BiomeRange(
							data.mintemperature,data.maxtemperature, data.minhumidity,data.maxhumidity,
							data.minaltitude,data.maxaltitude,data.minvariety,data.maxvariety
						)
					);
					logger.warn("this biome was generated: "+"kstrabiomes:overworld."+namespace+"_"+nam);
					File pngpath=new File(f,nam+".png");
					if (pngpath.exists()){
						Icon ico;
						try {
							ico=new ImageIcon(pngpath.toURI().toURL());
						} catch (Exception e){
							continue;
						}
						XTRABIOMESICONS.get(namespace).put(nam,ico);
					}
				}

			}
		}
	}
}
