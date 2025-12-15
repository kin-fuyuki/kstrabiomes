package kn.kinfuyuki.kstrabiomes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionBoolean;
import turniplabs.halplibe.util.GameStartEntrypoint;

public class config implements GameStartEntrypoint {
	public static GameSettings globalSettings = Minecraft.getMinecraft().gameSettings;
	public static OptionBoolean AMBIANCE=new OptionBoolean(globalSettings,"customambiance",true);
	public static OptionBoolean MUSIC=new OptionBoolean(globalSettings,"biomemusic",true);
	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}
}
