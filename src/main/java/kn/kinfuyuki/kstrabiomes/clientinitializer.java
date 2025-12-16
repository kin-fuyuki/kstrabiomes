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

import static kn.kinfuyuki.kstrabiomes.main.*;
@Environment(EnvType.CLIENT)
public class clientinitializer implements ClientModInitializer {
	public static OptionBoolean AMBIANCE;
	public static OptionBoolean MUSIC;
	public static OptionsPage SETTINGS;
	public static OptionsPage GENERATOR;
	public static blockoption TOPBLOCK;
	public static blockoption FILLERBLOCK;
	public static coloroption DAYCOLOR;
	public static coloroption MORNINGCOLOR;
	public static coloroption NIGHTCOLOR;

	public static OptionFloat MINTEMPERATURE;
	public static OptionFloat MINHUMIDITY;
	public static OptionFloat MINALTITUDE;
	public static OptionFloat MINVARIETY;

	public static OptionFloat MAXTEMPERATURE;
	public static OptionFloat MAXHUMIDITY;
	public static OptionFloat MAXALTITUDE;
	public static OptionFloat MAXVARIETY;

	public static OptionFloat CHANCECUSTOMMUSIC;
	public static OptionFloat FOG;

	@Override
	public void onInitializeClient() {

	}
	public static File getmc(){
		return Minecraft.getMinecraft().getMinecraftDir();
	}
	public static void init(){
		gamesettingsaccessor s = (gamesettingsaccessor) Minecraft.getMinecraft().gameSettings;
		GameSettings se=Minecraft.getMinecraft().gameSettings;
		MINTEMPERATURE = new OptionFloat(se,"mintemperature",0.0f);
		MINHUMIDITY = new OptionFloat(se,"minhumidity",0.0f);
		MINALTITUDE = new OptionFloat(se,"minaltitude",0.0f);
		MINVARIETY = new OptionFloat(se,"minvariety",0.0f);

		MAXTEMPERATURE = new OptionFloat(se,"maxtemperature",1.0f);
		MAXHUMIDITY = new OptionFloat(se,"maxhumidity",1.0f);
		MAXALTITUDE = new OptionFloat(se,"maxaltitude",1.0f);
		MAXVARIETY = new OptionFloat(se,"maxvariety",1.0f);

		CHANCECUSTOMMUSIC = new OptionFloat(se,"chancecustommusic",0.0f);
		FOG = new OptionFloat(se,"fog",0.0f);

		TOPBLOCK=new blockoption("");
		FILLERBLOCK=new blockoption("");
		AMBIANCE = s.kstrabiomes$getAmbiance();
		MUSIC = s.kstrabiomes$getMusice();
		DAYCOLOR=new coloroption("day");MORNINGCOLOR=new coloroption("morning");NIGHTCOLOR=new coloroption("night");
		SETTINGS = new OptionsPage(
			"kstrabiomes", Items.MAP.getDefaultStack()
		)
			.withComponent(new BooleanOptionComponent(AMBIANCE))
			.withComponent(new BooleanOptionComponent(MUSIC));
		GENERATOR = new OptionsPage(
			"biomegen",Items.BOOK.getDefaultStack())
			.withComponent(new OptionsCategory("top block:"))
			.withComponent(TOPBLOCK)
			.withComponent(new blocksearchfieldcomponent(TOPBLOCK))
			.withComponent(new OptionsCategory("filler block:"))
			.withComponent(FILLERBLOCK)
			.withComponent(new blocksearchfieldcomponent(FILLERBLOCK))
			.withComponent(new OptionsCategory("sky colors:"))
			.withComponent(DAYCOLOR).withComponent(MORNINGCOLOR).withComponent(NIGHTCOLOR)
			.withComponent(new OptionsCategory("generation settings:"))
			.withComponent(new FloatOptionComponent(MINTEMPERATURE))
			.withComponent(new FloatOptionComponent(MINHUMIDITY))
			.withComponent(new FloatOptionComponent(MINALTITUDE))
			.withComponent(new FloatOptionComponent(MINVARIETY))
			.withComponent(new FloatOptionComponent(MAXTEMPERATURE))
			.withComponent(new FloatOptionComponent(MAXHUMIDITY))
			.withComponent(new FloatOptionComponent(MAXALTITUDE))
			.withComponent(new FloatOptionComponent(MAXVARIETY))

		;
		Minecraft.getMinecraft().sndManager.ticksBeforeMusic = 10;
		OptionsPages.register(SETTINGS);
		OptionsPages.register(GENERATOR);
		biomes.registermusic(LOGGER);
	}
}
