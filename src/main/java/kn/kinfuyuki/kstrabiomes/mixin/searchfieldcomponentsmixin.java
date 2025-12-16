package kn.kinfuyuki.kstrabiomes.mixin;

import kn.kinfuyuki.kstrabiomes.searchfieldcomponentsacessor;
import net.minecraft.client.gui.options.components.OptionsComponent;
import net.minecraft.client.gui.options.components.SearchFieldComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(SearchFieldComponent.class)
public class searchfieldcomponentsmixin implements searchfieldcomponentsacessor {
	@Shadow
	@Final
	private List<OptionsComponent> components;

	@Override
	public List<OptionsComponent> getlist() {
		return components;
	}
}
