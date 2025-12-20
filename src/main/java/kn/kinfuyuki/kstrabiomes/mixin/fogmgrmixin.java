package kn.kinfuyuki.kstrabiomes.mixin;

import kn.kinfuyuki.kstrabiomes.biome.biomeambiance;
import kn.kinfuyuki.kstrabiomes.clientinitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenPhotoMode;
import net.minecraft.client.gui.modelviewer.ScreenModelViewer;
import net.minecraft.client.render.FogManager;
import net.minecraft.client.render.OpenGLHelper;
import net.minecraft.client.render.camera.CameraUtil;
import net.minecraft.client.render.colorizer.Colorizers;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.Dimension;
import net.minecraft.core.world.biome.Biome;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


import java.awt.*;
import java.nio.FloatBuffer;

@Mixin(FogManager.class)
public class fogmgrmixin {

	@Shadow
	@Final
	public Minecraft mc;
	@Shadow
	public float fogRed;
	@Shadow
	public float fogGreen;
	@Shadow
	public float fogBlue;
	@Shadow
	public float fogBrightnessOld;
	@Shadow
	public float fogBrightness;
	@Shadow
	private FloatBuffer buffer(float r, float g, float b, float a){
		throw new AssertionError();
	};
	@Inject(method = "setupFog",at=@At("TAIL"),cancellable = true)
	public void setupFog(int fogMode, float farPlaneDistance, float partialTick, CallbackInfo ci) {

		WorldClient world = this.mc.currentWorld;
			Biome biome = world.getBlockBiome((int) this.mc.thePlayer.x, (int) this.mc.thePlayer.y, (int) this.mc.thePlayer.z);
			if (clientinitializer.AMBIANCE.value&&biome instanceof biomeambiance) {
				biomeambiance biomee=(biomeambiance)biome;
				GL11.glFogfv(2918, this.buffer(this.fogRed, this.fogGreen, this.fogBlue, 0.5F));
				GL11.glNormal3f(0.0F, -1.0F, 0.0F);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				if (!(Boolean)this.mc.gameSettings.fog.value && fogMode != -1) {
					GL11.glFogi(2917, 2048);
					GL11.glFogf(2914, 0.0F);
					float maxFogDistance = (float)(this.mc.gameSettings.renderDistance.highest * 16);
					GL11.glFogf(2915, maxFogDistance * 0.25F);
					GL11.glFogf(2916, maxFogDistance);
					GL11.glEnable(2903);
					GL11.glColorMaterial(1028, 4608);
				} else {
					if (CameraUtil.isUnderLiquid(this.mc.activeCamera, this.mc.currentWorld, Material.water, partialTick)) {
						GL11.glFogi(2917, 2048);
						GL11.glFogf(2914, 0.1F);
					} else if (CameraUtil.isUnderLiquid(this.mc.activeCamera, this.mc.currentWorld, Material.lava, partialTick)) {
						GL11.glFogi(2917, 2048);
						GL11.glFogf(2914, 2.0F);
					} else {
						float fogDistance = farPlaneDistance;
						float maxFogDistance = (float)(this.mc.gameSettings.renderDistance.highest * 16);
						float fogModifier = this.mc.currentWorld.getCurrentWeather() != null ? this.mc.currentWorld.getCurrentWeather().fogDistance : 1.0F;
						fogModifier = 1.0F - (1.0F - fogModifier) * this.mc.currentWorld.weatherManager.getWeatherIntensity() * this.mc.currentWorld.weatherManager.getWeatherPower();
						if (farPlaneDistance > maxFogDistance * fogModifier) {
							fogDistance = maxFogDistance * fogModifier;
						}

						if (this.mc.currentScreen instanceof ScreenPhotoMode) {
							fogDistance = farPlaneDistance * ((ScreenPhotoMode)this.mc.currentScreen).getFog(partialTick);
						}
						if (biomee.fogintensity>0.){
							fogDistance/=biomee.fogintensity;
						}
						GL11.glFogi(2917, 9729);
						GL11.glFogf(2915, fogDistance * 0.25F);
						GL11.glFogf(2916, fogDistance);
						GL11.glFogf(2914, 1.0F);
						if (fogMode == -1) {
							GL11.glFogf(2915, 0.0F);
							GL11.glFogf(2916, fogDistance * 0.8F);
						}

						if (OpenGLHelper.enableSphericalFog) {
							GL11.glFogi(34138, 34139);
						}

						if (this.mc.currentWorld.dimension == Dimension.NETHER) {
							GL11.glFogf(2915, 0.0F);
						}
					}

					GL11.glEnable(2903);
					GL11.glColorMaterial(1028, 4608);
					ci.cancel();
				}}
	}
	@Inject(method = "updateFogColor",at=@At("TAIL"),cancellable = true)
	public void updateFogColor(float partialTick, CallbackInfo ci) {
		WorldClient world = this.mc.currentWorld;
			Biome biome = world.getBlockBiome((int) this.mc.thePlayer.x, (int) this.mc.thePlayer.y, (int) this.mc.thePlayer.z);
			if (clientinitializer.AMBIANCE.value&&biome instanceof biomeambiance) {
				float renderDistanceScale = 1.0F / (float) (this.mc.gameSettings.renderDistance.highest + 8 - (Integer) this.mc.gameSettings.renderDistance.value);
				renderDistanceScale = 1.0F - (float) Math.pow((double) renderDistanceScale, (double) 0.25F);

				int time = (int) (((float)world.skyDarken)/4.);
				biomeambiance biomee=(biomeambiance)biome;
				Color[] cols={
					biomee.day,
					biomee.morning,
					biomee.night
				};
				float r=((float) cols[time].getRed())/255;
				float g=((float) cols[time].getGreen())/255;
				float b=((float) cols[time].getBlue())/255;
				float rSky = r;
				float gSky = g;
				float bSky = b;
				this.fogRed = r;
				this.fogGreen = g;
				this.fogBlue = b;
				this.fogRed += (rSky - this.fogRed) * renderDistanceScale;
				this.fogGreen += (gSky - this.fogGreen) * renderDistanceScale;
				this.fogBlue += (bSky - this.fogBlue) * renderDistanceScale;
				if (world.getCurrentWeather() != null) {
					float[] out = world.getCurrentWeather().modifyFogColor(this.fogRed, this.fogGreen, this.fogBlue, world.weatherManager.getWeatherIntensity() * world.weatherManager.getWeatherPower());
					this.fogRed = out[0];
					this.fogGreen = out[1];
					this.fogBlue = out[2];
				}

				if (CameraUtil.isUnderLiquid(this.mc.activeCamera, world, Material.water, partialTick)) {
					this.fogRed = 0.02F;
					this.fogGreen = 0.02F;
					this.fogBlue = 0.2F;
					if ((Boolean) this.mc.gameSettings.biomeWater.value) {
						int x = MathHelper.floor(this.mc.activeCamera.getX(partialTick));
						int z = MathHelper.floor(this.mc.activeCamera.getZ(partialTick));
						double temp = world.getBlockTemperature(x, z);
						double humid = world.getBlockHumidity(x, z);
						int waterColor = Colorizers.water.getColor(temp, humid);
						float red = (float) (waterColor >> 16 & 255) / 255.0F;
						float green = (float) (waterColor >> 8 & 255) / 255.0F;
						float blue = (float) (waterColor & 255) / 255.0F;
						red = MathHelper.clamp(red, 0.0F, 1.0F);
						green = MathHelper.clamp(green, 0.0F, 1.0F);
						blue = MathHelper.clamp(blue, 0.0F, 1.0F);
						this.fogRed = red * 0.5F;
						this.fogGreen = green * 0.5F;
						this.fogBlue = blue * 0.5F;
					}
				} else if (CameraUtil.isUnderLiquid(this.mc.activeCamera, world, Material.lava, partialTick)) {
					this.fogRed = 0.6F;
					this.fogGreen = 0.1F;
					this.fogBlue = 0.0F;
				}

				float brightness = this.fogBrightnessOld + (this.fogBrightness - this.fogBrightnessOld) * partialTick;
				this.fogRed *= brightness;
				this.fogGreen *= brightness;
				this.fogBlue *= brightness;
				if (this.mc.currentScreen instanceof ScreenModelViewer) {
					float[] color = ((ScreenModelViewer) this.mc.currentScreen).getBackgroundFogColor();
					this.fogRed = color[0];
					this.fogGreen = color[1];
					this.fogBlue = color[2];
				}

				GL11.glClearColor(this.fogRed, this.fogGreen, this.fogBlue, 0.0F);
				ci.cancel();

		}
	}

}
