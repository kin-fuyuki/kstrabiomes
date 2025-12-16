package kn.kinfuyuki.kstrabiomes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.TextFieldElement;
import net.minecraft.client.gui.options.components.OptionsComponent;
import net.minecraft.client.gui.text.TextFieldEditor;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.sound.SoundCategory;
import org.lwjgl.input.Keyboard;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static kn.kinfuyuki.kstrabiomes.main.LOGGER;

public class classsearchfieldcomponent<T> implements OptionsComponent, TextFieldElement.ITextChangeListener{

	private static final Minecraft mc = Minecraft.getMinecraft();
	private static final int PADDING = 4;
	private final TextFieldElement textField;
	private final TextFieldEditor editor;
	private final List<OptionsComponent> components = new ArrayList();
	private OptionsComponent clickedComponent = null;
	private int clickedComponentY = 0;
	private ButtonElement clearButton;
	private int delay=10;
	classoption<T> choice;
	public classsearchfieldcomponent(classoption opt) {

		choice=opt;
		this.textField = new TextFieldElement((Screen)null, mc.font, 0, 0, 150, 18, "", "Search...");
		this.textField.setMaxStringLength(20);
		this.textField.setTextChangeListener(this);
		this.editor = new TextFieldEditor(this.textField);
		this.clearButton = (new ButtonElement(0, 0, 0, 12, 20, "")).setTextures("minecraft:gui/screen/options/clear", "minecraft:gui/screen/options/clear_highlighted", (String)null);
	}

	public void tick() {
		this.textField.updateCursorCounter();

		for(OptionsComponent component : this.components) {
			component.tick();
		}
		delay--;
		if (delay<0)delay=0;

	}

	public int getHeight() {
		int height = 4 + this.textField.height + 4;

		for(OptionsComponent component : this.components) {
			height += component.getHeight();
		}

		return height;
	}

	public void render(int x, int y, int width, int relativeMouseX, int relativeMouseY) {
		this.textField.xPosition = x + width / 2 - this.textField.width / 2;
		this.textField.yPosition = y + 4;
		this.textField.drawTextBox();
		this.clearButton.xPosition = x + width / 2 + this.textField.width / 2 + 1;
		this.clearButton.yPosition = y + 4 - 1;
		this.clearButton.drawButton(mc, x + relativeMouseX, y + relativeMouseY);
		int componentY = 4 + this.textField.height + 4;

		for(OptionsComponent component : this.components) {
			component.render(x, y + componentY, width, relativeMouseX, relativeMouseY - componentY);
			componentY += component.getHeight();
		}

	}

	public void onMouseClick(int mouseButton, int x, int y, int width, int relativeMouseX, int relativeMouseY) {
		this.textField.mouseClicked(x + relativeMouseX, y + relativeMouseY, 0);
		if (this.clearButton.mouseClicked(mc, x + relativeMouseX, y + relativeMouseY)) {
			mc.sndManager.playSound("random.click", SoundCategory.GUI_SOUNDS, 1.0F, 1.0F);
			this.textField.setText("");
			if (delay==0)
				this.updateSearchResults("");
		} else {
			int componentY = 4 + this.textField.height + 4;

			for(OptionsComponent component : this.components) {
				if (relativeMouseX >= 0 && relativeMouseX < width && relativeMouseY >= componentY && relativeMouseY < componentY + component.getHeight()) {
					component.onMouseClick(mouseButton, x, componentY, width, relativeMouseX, relativeMouseY - componentY);
					this.clickedComponent = component;
					this.clickedComponentY = componentY;
					return;
				}

				componentY += component.getHeight();
			}

		}
	}

	public void onMouseMove(int x, int y, int width, int relativeMouseX, int relativeMouseY) {
		if (this.clickedComponent != null) {
			this.clickedComponent.onMouseMove(x, this.clickedComponentY, width, relativeMouseX, relativeMouseY - this.clickedComponentY);
		}

	}

	public void onMouseRelease(int mouseButton, int x, int y, int width, int relativeMouseX, int relativeMouseY) {
		if (this.clickedComponent != null) {
			this.clickedComponent.onMouseRelease(mouseButton, x, this.clickedComponentY, width, relativeMouseX, relativeMouseY - this.clickedComponentY);
		}

	}

	public void onKeyPress(int keyCode, char character) {
		if (this.textField.isFocused && keyCode != Keyboard.KEY_ESCAPE) {
			this.textField.textboxKeyTyped(character, keyCode);
		} else if (keyCode == Keyboard.KEY_ESCAPE) {
			this.textField.setFocused(false);
		} else {
			for(OptionsComponent component : this.components) {
				component.onKeyPress(keyCode, character);
			}
		}

	}
	public void select(classoption it){
		LOGGER.error("a");
		this.choice.name=it.current.getPackageName()+"."+it.current.getSimpleName();
		this.choice.current=it.current;
		this.textField.setText("");
	}
	public boolean matchesSearchTerm(String term) {
		return false;
	}

	public void textChanged(TextFieldElement textField) {
		if (delay==0)
			this.updateSearchResults(textField.getText());
	}
	public static boolean domatch(String text,String key){
		return key.contains(text);
	}

	private void updateSearchResults(String searchTerm) {
		delay=10;
		this.components.clear();
		if (!searchTerm.isEmpty()) {
			ConfigurationBuilder cfg=new ConfigurationBuilder();
			cfg.setUrls(ClasspathHelper.forPackage(choice.current.getPackageName()));
			cfg.setScanners(new SubTypesScanner(true));

			Reflections re=new Reflections(cfg);
			Set<Class<? extends T>> set = (Set<Class<? extends T>>)(Set<?>) re.getSubTypesOf(choice.current);
			set.remove(choice.current);
			Class<? extends T>[] classes =
				set.toArray(new Class[0]);

			for(Class<? extends T> b: classes) {
				if (b!=null)
					if(domatch(searchTerm,b.toString()))
						this.components.add(
							new classoption<T>(b.getSimpleName(), (Class<T>) b,this)
						);
			}

		}
	}

}
