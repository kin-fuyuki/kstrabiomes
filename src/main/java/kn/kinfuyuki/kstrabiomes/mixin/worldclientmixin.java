package kn.kinfuyuki.kstrabiomes.mixin;

import kn.kinfuyuki.kstrabiomes.biome.biomeambiance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.camera.ICamera;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.util.phys.Vec3;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(WorldClient.class)
public class worldclientmixin {
	@Inject(method = "getSkyColor",at=@At("HEAD"),cancellable = true)
	public void getSkyColor(ICamera camera, float partialTick, CallbackInfoReturnable ci) {
		Minecraft mc=Minecraft.getMinecraft();
		Biome biome = ((WorldClient)(Object)this).getBlockBiome((int) mc.thePlayer.x, (int) mc.thePlayer.y, (int) mc.thePlayer.z);
		if (biome instanceof biomeambiance){
			biomeambiance biomee= (biomeambiance) biome;
			Color[] cols={
				biomee.day,
				biomee.morning,
				biomee.night
			};
			int time = (int) (((float)((WorldClient)(Object)this).skyDarken)/4.);
			float r=((float) cols[time].getRed())/255;
			float g=((float) cols[time].getGreen())/255;
			float b=((float) cols[time].getBlue())/255;
			ci.setReturnValue(Vec3.getPermanentVec3(r,g,b));
			ci.cancel();
		}
	}
}
