package kn.kinfuyuki.kstrabiomes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.PlayerLocal;
import net.minecraft.client.entity.player.PlayerLocalMultiplayer;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.gui.options.components.OptionsComponent;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.RenderBlockCache;
import net.minecraft.client.render.RenderBlocks;
import net.minecraft.client.render.TextureManager;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.block.model.BlockModelStandard;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.texture.Texture;
import net.minecraft.client.render.texture.stitcher.TextureRegistry;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.Blocks;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.player.Session;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeaturePumice;
import org.lwjgl.opengl.GL11;

import java.util.Map;
import java.util.function.BiConsumer;

import static kn.kinfuyuki.kstrabiomes.main.LOGGER;

public class classoption<T> implements OptionsComponent {

	protected static final Minecraft mc = Minecraft.getMinecraft();
	public  String name;
	protected final ButtonElement resetButton;
	private final static ItemStack defaultStack= Items.BOWL.getDefaultStack();
	protected final ItemElement icon=new ItemElement(mc);
	Class<? extends T> current;
	public String prevname="";
	public final boolean searched;
	private classsearchfieldcomponent it;

	public classoption(String translationKey,Class<T> type) {
		current= type;
		this.searched=false;
		this.name = translationKey;
		this.resetButton = (new ButtonElement(0, 0, 0, 20, 20, "")).setTextures("minecraft:gui/misc/icon_reset", "minecraft:gui/misc/icon_reset_highlighted", "minecraft:gui/misc/icon_reset");
	}

	public classoption(String translationKey,Class<T> type,classsearchfieldcomponent s) {
		current= type;
		this.searched=true;
		this.name = translationKey;
		this.resetButton = (new ButtonElement(0, 0, 0, 20, 20, "")).setTextures("minecraft:gui/misc/icon_reset", "minecraft:gui/misc/icon_reset_highlighted", "minecraft:gui/misc/icon_reset");
		it=s;
	}

	@Override
	public void tick() {
		if (!name.equals(prevname)){

			prevname=new String(name);

		}
	}

	public void resetValue(){
		name="";
	}

	public boolean isDefault(){

		return current==null||searched||"".equals(name);
	}

	public int getHeight() {
		return 24;
	}

	public void render(int x, int y, int width, int relativeMouseX, int relativeMouseY) {

		Font fr = mc.font;
		String translated = name;
		int textColor = -1;
		if (relativeMouseX >= 0 && relativeMouseX <= width && relativeMouseY >= 2 && relativeMouseY <= this.getHeight() - 2) {
			textColor = -96;
		}
		fr.drawStringWithShadow(translated, x, y + this.getHeight() / 2 - 4, textColor);
		this.resetButton.enabled = !this.isDefault();
		this.renderButton(x, y, width - 100, 2, 100, 20, relativeMouseX, relativeMouseY);
		int stringWidth = fr.getStringWidth(translated);
		int lineMaxX = x + width - 100 - 8;
		if (this.resetButton.enabled) {
			lineMaxX -= 20;
		}
		this.drawRect(x + stringWidth + 8, y + this.getHeight() / 2, lineMaxX, y + this.getHeight() / 2 + 1, 1602191231);
	}

	public void onMouseClick(int mouseButton, int x, int y, int width, int relativeMouseX, int relativeMouseY) {
		LOGGER.error("b");
		if (relativeMouseX >= width - 100 && relativeMouseX <= width && relativeMouseY >= 2 && relativeMouseY <= 22) {
			LOGGER.error("c");
			mc.sndManager.playSound("random.click", SoundCategory.GUI_SOUNDS, 1.0F, 1.0F);
			LOGGER.error("d");
			if (searched){
				LOGGER.error("e");
				it.select(this);

			}
		} else if (this.resetButton.enabled && relativeMouseX >= width - 100 - 20 && relativeMouseX <= width - 100 && relativeMouseY >= 2 && relativeMouseY <= 22) {
			mc.sndManager.playSound("random.click", SoundCategory.GUI_SOUNDS, 1.0F, 1.0F);
			this.resetValue();

		}

	}

	public void onMouseMove(int x, int y, int width, int relativeMouseX, int relativeMouseY) {
		this.buttonDragged(x, y, width, this.getHeight(), relativeMouseX - (width - 100), relativeMouseY - 2);
	}

	public void onMouseRelease(int mouseButton, int x, int y, int width, int relativeMouseX, int relativeMouseY) {
		this.buttonReleased(mouseButton, x, y, width, this.getHeight(), relativeMouseX - (width - 100), relativeMouseY - 2);
	}


	protected void buttonDragged(int x, int y, int width, int height, int relativeMouseX, int relativeMouseY) {
	}

	protected void buttonReleased(int mouseButton, int x, int y, int width, int height, int relativeMouseX, int relativeMouseY) {
	}

	public void onKeyPress(int keyCode, char character) {
	}

	public boolean matchesSearchTerm(String term) {
		return I18n.getInstance().translateKey(this.name).toLowerCase().contains(term.toLowerCase());
	}

	protected void renderButton(int x, int y, int relativeButtonX, int relativeButtonY, int buttonWidth, int buttonHeight, int relativeMouseX, int relativeMouseY) {
		if (this.resetButton.enabled) {
			this.resetButton.xPosition = x + relativeButtonX - 20;
			this.resetButton.yPosition = y + relativeButtonY;
			this.resetButton.drawButton(mc, x + relativeMouseX, y + relativeMouseY);
		}

	}

	protected void drawRect(int minX, int minY, int maxX, int maxY, int argb) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		tessellator.startDrawingQuads();
		tessellator.setColorRGBA_I(argb & 16777215, argb >> 24 & 255);
		tessellator.addVertex((double)minX, (double)maxY, (double)0.0F);
		tessellator.addVertex((double)maxX, (double)maxY, (double)0.0F);
		tessellator.addVertex((double)maxX, (double)minY, (double)0.0F);
		tessellator.addVertex((double)minX, (double)minY, (double)0.0F);
		tessellator.draw();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
	}
}
