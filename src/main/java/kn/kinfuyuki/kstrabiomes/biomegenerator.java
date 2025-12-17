package kn.kinfuyuki.kstrabiomes;

import kn.kinfuyuki.kstrabiomes.kstraconfig.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.BooleanOptionComponent;
import net.minecraft.client.gui.options.components.FloatOptionComponent;
import net.minecraft.client.gui.options.components.OptionsCategory;
import net.minecraft.client.gui.options.data.OptionsPage;
import net.minecraft.client.gui.options.data.OptionsPages;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionFloat;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobWaterAnimal;
import net.minecraft.core.entity.monster.MobMonster;
import net.minecraft.core.item.Items;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.generate.feature.WorldFeature;

import static kn.kinfuyuki.kstrabiomes.main.LOGGER;

public class biomegenerator {
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
	public static OptionBoolean SNOWY;
	public static classoption<WorldFeature> CUSTOMTREE;
	public static classoption<Biome> CUSTOMBIOMECLASS;
	public static classoption<MobMonster> MONSTERS;
	public static classoption<MobWaterAnimal> WATERMOBS;

	public static classoption<Mob> OTHERMOBS;
	public static classoption<MobAnimal> AMBIENTMOBS;

	public static void init(){try{

		CUSTOMTREE=new classoption("", WorldFeature.class);
		CUSTOMBIOMECLASS=new classoption("", Biome.class);
		GameSettings se= Minecraft.getMinecraft().gameSettings;
		SNOWY=new OptionBoolean(se,"snows",false);
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
triggeroption done=new triggeroption("gui.achievements.button.done",biomegenerator.class.getDeclaredMethod("generate",triggeroption.class));
done.args.add(0);
		TOPBLOCK=new blockoption("");
		FILLERBLOCK=new blockoption("");
		DAYCOLOR=new coloroption("day");MORNINGCOLOR=new coloroption("morning");NIGHTCOLOR=new coloroption("night");
			GENERATOR = new OptionsPage(
				"biomegen", Items.BOOK.getDefaultStack())
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
				.withComponent(new OptionsCategory("tree class:"))
				.withComponent(CUSTOMTREE)
				.withComponent(new classsearchfieldcomponent(CUSTOMTREE))
				.withComponent(new OptionsCategory("biome class:"))
				.withComponent(CUSTOMBIOMECLASS)
				.withComponent(new classsearchfieldcomponent(CUSTOMBIOMECLASS))
				.withComponent(new OptionsCategory("do snow:"))
				.withComponent(new BooleanOptionComponent(SNOWY))
				.withComponent(new OptionsCategory(""))
				.withComponent(new OptionsCategory("what to spawn"))
				.withComponent(new OptionsCategory("mobs:"))
				.withComponent(new OptionsCategory("ambient mobs:"))
				.withComponent(new OptionsCategory("aquatic mobs:"))
				.withComponent(new OptionsCategory("monsters:"))



				.withComponent(done)
			;
		OptionsPages.register(GENERATOR);
	}catch (Exception e){
		LOGGER.error("Exception "+e.getMessage()+" of type "+e.getClass().getSimpleName()+" cuz of: "+e.getCause());
	}
	}
	public static void generate(triggeroption it){

	}
}
