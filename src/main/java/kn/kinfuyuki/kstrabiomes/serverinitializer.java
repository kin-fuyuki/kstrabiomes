package kn.kinfuyuki.kstrabiomes;

import kn.kinfuyuki.kstrabiomes.mixin.biomeprovideroverworldaccessor;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Final;
import turniplabs.halplibe.util.RecipeEntrypoint;

import java.io.File;

import static kn.kinfuyuki.kstrabiomes.main.LOGGER;

@Environment(EnvType.SERVER)
public class serverinitializer implements DedicatedServerModInitializer  {
	@Override
	public void onInitializeServer() {

	}
	static public File getmc(){
		return MinecraftServer.getInstance().getMinecraftDir();
	}
}
