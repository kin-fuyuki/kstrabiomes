package kn.kinfuyuki.kstrabiomes;

import kn.kinfuyuki.kstrabiomes.biome.biggerbiomesoverworld;
import kn.kinfuyuki.kstrabiomes.biome.biggerbiomestype;
import kn.kinfuyuki.kstrabiomes.mixin.biomeprovideroverworldaccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.impl.game.minecraft.MinecraftGameProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.type.WorldType;
import net.minecraft.core.world.type.WorldTypeGroups;
import net.minecraft.core.world.type.WorldTypes;
import net.minecraft.server.MinecraftServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.io.File;

import static kn.kinfuyuki.kstrabiomes.clientinitializer.*;

public class main implements ModInitializer, RecipeEntrypoint {
	public static final String MOD_ID = "kstrabiomes";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final boolean ISSERVER= FabricLoader.getInstance().getEnvironmentType()==EnvType.SERVER;
	public static WorldType SPARSE;

	@Override
	public void onInitialize() {


	}
	@Override
	public void onRecipesReady() {

		if (!ISSERVER) {
			clientinitializer.init();
		}
		biomes.registerbiomes(LOGGER);

		LOGGER.error("setting up world type");

		SPARSE= WorldTypes.register("kstrabiomes:overworld.sparse",new biggerbiomestype());
		WorldTypeGroups.GROUPS.add(new WorldTypeGroups.Group(SPARSE));
		biomeprovideroverworldaccessor.getBrm().lock();
		biggerbiomesoverworld.lock();
	}
	public static File getmc(){

		if (ISSERVER) {
			return serverinitializer.getmc();
		}else {
			return clientinitializer.getmc();
		}
	}

	@Override
	public void initNamespaces() {

	}
	static {

	}

}
