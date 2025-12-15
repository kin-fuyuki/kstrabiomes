package kn.kinfuyuki.kstrabiomes.mixin;

import kn.kinfuyuki.kstrabiomes.gamesettingsaccessor;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.Option;
import net.minecraft.client.option.OptionBoolean;
import net.minecraft.client.option.OptionRange;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameSettings.class)
public class gamesettingsmixin implements gamesettingsaccessor {

	@Unique
	private final GameSettings self = (GameSettings)(Object)this;
	@Unique
	public OptionBoolean ambiance = new OptionBoolean(self, "ambiance", true);
	@Unique
	public OptionBoolean musice = new OptionBoolean(self, "musice", true);
	public OptionBoolean kstrabiomes$getAmbiance(){return ambiance;}
	public OptionBoolean kstrabiomes$getMusice(){return musice;}

}
