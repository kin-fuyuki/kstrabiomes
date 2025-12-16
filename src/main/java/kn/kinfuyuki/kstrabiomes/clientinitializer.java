package kn.kinfuyuki.kstrabiomes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.FloatOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.components.SearchFieldComponent;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionFloat;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.item.Items;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.io.File;

import static kn.kinfuyuki.kstrabiomes.biomegenerator.GENERATOR;
import static kn.kinfuyuki.kstrabiomes.main.*;
@Environment(EnvType.CLIENT)
public class clientinitializer implements ClientModInitializer {
	public static OptionBoolean AMBIANCE;
	public static OptionBoolean MUSIC;
	public static OptionsPage SETTINGS;

	@Override
	public void onInitializeClient() {

	}
	public static File getmc(){
		return Minecraft.getMinecraft().getMinecraftDir();
	}
	public static void init(){
		gamesettingsaccessor s = (gamesettingsaccessor) Minecraft.getMinecraft().gameSettings;
		biomegenerator.init();
		AMBIANCE = s.kstrabiomes$getAmbiance();
		MUSIC = s.kstrabiomes$getMusice();
		SETTINGS = new OptionsPage(
			"kstrabiomes", Items.MAP.getDefaultStack()
		)
			.withComponent(new BooleanOptionComponent(AMBIANCE))
			.withComponent(new BooleanOptionComponent(MUSIC));
		Minecraft.getMinecraft().sndManager.ticksBeforeMusic = 10;
		OptionsPages.register(SETTINGS);
		biomes.registermusic(LOGGER);
	}
}
