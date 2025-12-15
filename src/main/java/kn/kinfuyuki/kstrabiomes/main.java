package kn.kinfuyuki.kstrabiomes;

import kn.kinfuyuki.kstrabiomes.mixin.biomeprovideroverworldaccessor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.ConfigHandler;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;

public class main implements ModInitializer, RecipeEntrypoint, GameStartEntrypoint {
	public static final String MOD_ID = "kstrabiomes";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static GameSettings globalSettings = Minecraft.getMinecraft().gameSettings;


	public static ConfigHandler config;

	public static OptionBoolean AMBIANCE;
	public static OptionBoolean MUSIC;
	public static OptionsPage SETTINGS;

	@Override
	public void onInitialize() {
	}
	public static void updatecfg(){

	}
	@Override
	public void onRecipesReady() {
		gamesettingsaccessor s=(gamesettingsaccessor)Minecraft.getMinecraft().gameSettings;
		AMBIANCE= s.kstrabiomes$getAmbiance();
		MUSIC=s.kstrabiomes$getMusice();
		SETTINGS=new OptionsPage(
			"kstrabiomes", Items.MAP.getDefaultStack()
		)
			.withComponent(new BooleanOptionComponent(AMBIANCE))
			.withComponent(new BooleanOptionComponent(MUSIC));
		Minecraft.getMinecraft().sndManager.ticksBeforeMusic=10;
		OptionsPages.register(SETTINGS);
		biomes.registermusic(LOGGER);
		biomes.registerbiomes(LOGGER);
		biomeprovideroverworldaccessor.getBrm().lock();
	}

	@Override
	public void initNamespaces() {

	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}
}
