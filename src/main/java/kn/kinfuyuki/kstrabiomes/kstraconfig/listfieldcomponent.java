package kn.kinfuyuki.kstrabiomes.kstraconfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.OptionsComponent;

public class listfieldcomponent extends classoption implements OptionsComponent {
	Minecraft mc;
	com.formdev.flatlaf.
	public listfieldcomponent(String translationKey, Class type) {
		super(translationKey, type);
	}

	public listfieldcomponent(String translationKey, Class type, classsearchfieldcomponent s) {
		super(translationKey, type, s);
	}


	@Override
	public void init(Minecraft mc) {
		this.mc=mc;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public void render(int x, int y, int width, int relativeMouseX, int relativeMouseY) {

	}

	@Override
	public void tick() {
	}

	@Override
	public void onMouseClick(int mouseButton, int x, int y, int width, int relativeMouseX, int relativeMouseY) {

	}

	@Override
	public void onMouseMove(int x, int y, int width, int relativeMouseX, int relativeMouseY) {

	}

	@Override
	public void onMouseRelease(int mouseButton, int x, int y, int width, int relativeMouseX, int relativeMouseY) {

	}

	@Override
	public void onKeyPress(int i, char c) {

	}

	@Override
	public void onClose() {
	}

	@Override
	public boolean matchesSearchTerm(String string) {
		return false;
	}
}
