package kn.kinfuyuki.kstrabiomes.mixin;

import com.mojang.logging.LogUtils;
import kn.kinfuyuki.kstrabiomes.config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.sound.*;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import paulscode.sound.SoundSystem;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Mixin(SoundEngine.class)
public class soundenginemixin {
	@Shadow
	@Final
	private static Logger LOGGER;
	@Shadow
	private static @Nullable SoundSystem soundSystem;
	@Shadow
	private static Lock lock;
	@Shadow
	private @Nullable GameSettings options;
	@Shadow
	@Final
	private Random random;
	@Shadow
	private Minecraft mc;
	@Shadow
	public int ticksBeforeMusic;
	@Shadow
	private boolean isLoaded(){return false;}
	@Inject(method = "tick",at=@At("HEAD"),cancellable = true)
	public void tick(CallbackInfo c){
		if (config.MUSIC.value){

			try {
				lock.lock();
				if (this.isLoaded() && SoundCategoryHelper.getEffectiveVolume(SoundCategory.MUSIC, this.options) != 0.0F) {
					if (soundSystem.playing("BgMusic") || soundSystem.playing("Streaming")) {
						return;
					}

					if (this.ticksBeforeMusic > 0) {
						--this.ticksBeforeMusic;
						return;
					}

					SoundEvent event;
					if (this.mc != null && this.mc.thePlayer != null && !this.mc.thePlayer.world.canBlockSeeTheSky(MathHelper.floor(this.mc.thePlayer.x), MathHelper.floor(this.mc.thePlayer.y), MathHelper.floor(this.mc.thePlayer.z))) {
						event = SoundRepository.SOUNDS.getSoundEvent("ambient.cave");
					} else {
						event = SoundRepository.SOUNDS.getRandomSoundFromCategory("music.");
					}

					if (event == null) {
						return;
					}

					SoundEntry entry = event.getRandomEntry();
					if (entry != null) {
						this.ticksBeforeMusic = this.random.nextInt(6000) + 6000;
						soundSystem.backgroundMusic("BgMusic", entry.getURL(), entry.name, false);
						soundSystem.setPitch("BgMusic", entry.pitch);
						soundSystem.setVolume("BgMusic", SoundCategoryHelper.getEffectiveVolume(SoundCategory.MUSIC, this.options) * entry.volume);
						soundSystem.play("BgMusic");
					}

					return;
				}
			} catch (Exception e) {
				LOGGER.error("Unexpected exception while ticking sound engine!", e);
				return;
			} finally {
				lock.unlock();
			}
			c.cancel();
		}
	}
}
