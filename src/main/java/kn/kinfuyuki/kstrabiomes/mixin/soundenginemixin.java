package kn.kinfuyuki.kstrabiomes.mixin;

import kn.kinfuyuki.kstrabiomes.biome.biomeambiance;
import kn.kinfuyuki.kstrabiomes.clientinitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.sound.*;
import net.minecraft.client.world.WorldClient;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.biome.Biome;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import paulscode.sound.SoundSystem;

import java.io.File;
import java.util.Random;
import java.util.concurrent.locks.Lock;

@Mixin(SoundEngine.class)
public class soundenginemixin {
	@Shadow @Final private static Logger LOGGER;
	@Shadow private static @Nullable SoundSystem soundSystem;
	@Shadow private static Lock lock;
	@Shadow private @Nullable GameSettings options;
	@Shadow private Random random;
	@Shadow private Minecraft mc;
	@Shadow public int ticksBeforeMusic;

	@Unique private float lastmusicvolume = -1f;
	@Unique private boolean waspaused = false;

	@Shadow private boolean isLoaded() { return false; }

	@Inject(method ="stopMusic",at = @At("HEAD"))
	public void stopMusic(CallbackInfo c){
		ticksBeforeMusic=50;
	}

	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	public void tick(CallbackInfo c) {
		if (clientinitializer.MUSIC.value) {
			WorldClient world = this.mc.currentWorld;
			if (options != null) {

				float currentvol = SoundCategoryHelper.getEffectiveVolume(SoundCategory.MUSIC, options);
				if (currentvol != lastmusicvolume) {
					lastmusicvolume = currentvol;
					if (soundSystem.playing("BgMusic")) {
						soundSystem.setVolume("BgMusic", currentvol * 1.0f);
					}
				}
				if (currentvol <= 0f && soundSystem.playing("BgMusic") && !waspaused) {
					soundSystem.pause("BgMusic");
					waspaused = true;
				} else if (currentvol > 0f && waspaused) {
					soundSystem.play("BgMusic");

					waspaused = false;
				}
			}
			if (soundSystem.playing("BgMusic") || soundSystem.playing("Streaming")) {
				c.cancel();
				return;
			}
			Biome biome = world.getBlockBiome((int) this.mc.thePlayer.x, (int) this.mc.thePlayer.y, (int) this.mc.thePlayer.z);
			boolean iscustom = biome instanceof biomeambiance;
			LOGGER.error("to next music:" + Integer.valueOf(this.ticksBeforeMusic).toString());
			if (this.ticksBeforeMusic > 0) {
				--this.ticksBeforeMusic;
				c.cancel();
				return;
			}
			if (iscustom) {
				biomeambiance biomee = (biomeambiance) biome;
				if (random.nextFloat() < biomee.musicchance) {
					int which = random.nextInt(biomee.musics.size());
					File musicfile = biomee.musics.get(which);
					int lendelay = 200;
					this.ticksBeforeMusic = lendelay;
					try {
						lock.lock();
						soundSystem.backgroundMusic("BgMusic", musicfile.toURI().toURL(), musicfile.getName(), false);
						soundSystem.setPitch("BgMusic", 1.0f);
						soundSystem.setVolume("BgMusic", SoundCategoryHelper.getEffectiveVolume(SoundCategory.MUSIC, this.options) * 1.0f);
						soundSystem.play("BgMusic");
					} catch (Exception e) {
						LOGGER.error(e.getMessage());
					} finally {
						lock.unlock();
					}
					waspaused = false;
				} else {
					playvanillamusic();
				}
			} else {
				playvanillamusic();
			}
			c.cancel();
		}
	}
	@Unique
	private void playvanillamusic() {
		try {
			lock.lock();
			if (this.isLoaded() && options != null && SoundCategoryHelper.getEffectiveVolume(SoundCategory.MUSIC, options) != 0.0F) {
				if (soundSystem.playing("BgMusic") || soundSystem.playing("Streaming")) {
					return;
				}
				SoundEvent event;
				if (this.mc.thePlayer.world.canBlockSeeTheSky(MathHelper.floor(this.mc.thePlayer.x), MathHelper.floor(this.mc.thePlayer.y), MathHelper.floor(this.mc.thePlayer.z))) {
					event = SoundRepository.SOUNDS.getRandomSoundFromCategory("music.");
				} else {
					event = SoundRepository.SOUNDS.getSoundEvent("ambient.cave");
				}
				if (event == null) return;
				SoundEntry entry = event.getRandomEntry();
				if (entry != null) {
					this.ticksBeforeMusic = 3400;
					soundSystem.backgroundMusic("BgMusic", entry.getURL(), entry.name, false);
					soundSystem.setPitch("BgMusic", entry.pitch);
					soundSystem.setVolume("BgMusic", SoundCategoryHelper.getEffectiveVolume(SoundCategory.MUSIC, this.options) * entry.volume);
					soundSystem.play("BgMusic");
				}
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			lock.unlock();
		}
	}


}
