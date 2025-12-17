package kn.kinfuyuki.kstrabiomes.kstraconfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.ItemElement;
import net.minecraft.client.gui.SliderElement;
import net.minecraft.client.gui.options.components.OptionsComponent;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.OptionFloat;
import net.minecraft.client.render.Font;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.lang.I18n;
import net.minecraft.core.sound.SoundCategory;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class coloroption implements OptionsComponent {


	protected static final Minecraft mc = Minecraft.getMinecraft();
	public  String name;
	protected final ButtonElement resetButton;
	private final static ItemStack defaultStack= Items.BOWL.getDefaultStack();
	protected final ItemElement icon=new ItemElement(mc);
	Color current=new Color(0,0,0);
	SliderElement r,g,b;
	OptionFloat or,og,ob;
	short whichbutton=-4;
	public coloroption(String n) {
		name=n;
		this.resetButton = (new ButtonElement(0, 0, 0, 20, 20, "")).setTextures("minecraft:gui/misc/icon_reset", "minecraft:gui/misc/icon_reset_highlighted", "minecraft:gui/misc/icon_reset");

		GameSettings ge=mc.gameSettings;
		r=new SliderElement(1,0,0,64,16,new OptionFloat(ge,"red",1));
		g=new SliderElement(1,0,0,64,16,new OptionFloat(ge,"green",1));
		b=new SliderElement(1,0,0,64,16,new OptionFloat(ge,"blue",1));
		r.sliderValue=g.sliderValue=b.sliderValue=0;

	}


	@Override
	public void tick() {
		r.visible=g.visible=b.visible=true;
		r.enabled=g.enabled=b.enabled=true;
	}

	public void resetValue(){
		r.sliderValue=g.sliderValue=b.sliderValue=0;
		current=new Color(0);
	}

	public boolean isDefault(){
		return current.getRed()==0&&current.getGreen()==0&&current.getBlue()==0;
	}

	public int getHeight() {
		return 24;
	}

	public void render(int x, int y, int width, int relativeMouseX, int relativeMouseY) {

		Font fr = mc.font;
		String translated = this.name;
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
		this.drawRect(x +width-204, y-2, x+width-192, y+18 , new Color(255,255,255).getRGB());
		this.drawRect(x +width-202, y, x+width-194, y+16 , current.getRGB());
		r.yPosition=g.yPosition=b.yPosition=y;
		r.xPosition=x+width-192;g.xPosition=x+width-128;b.xPosition=x+width-64;
		r.displayString="red:"+Integer.toString(current.getRed());
		g.displayString="green:"+Integer.toString(current.getGreen());
		b.displayString="blue:"+Integer.toString(current.getBlue());

		this.r.drawButton(mc,x + relativeMouseX, y + relativeMouseY);
		this.g.drawButton(mc,x + relativeMouseX, y + relativeMouseY);
		this.b.drawButton(mc,x + relativeMouseX, y + relativeMouseY);
		if(hold){
			if (whichbutton==0){
				r.mouseClicked(mc, relativeMouseX + x, relativeMouseY + y);
				r.mouseDragged(mc, relativeMouseX + x, relativeMouseY + y);
			} else if (whichbutton==1) {
				g.mouseClicked(mc, relativeMouseX + x, relativeMouseY + y);
				g.mouseDragged(mc, relativeMouseX + x, relativeMouseY + y);

			}
			else if  (whichbutton==2){
				b.mouseClicked(mc, relativeMouseX + x, relativeMouseY + y);
				b.mouseDragged(mc, relativeMouseX + x, relativeMouseY + y);
			}
			float rv= (float) r.sliderValue;
			float gv= (float) g.sliderValue;
			float bv= (float) b.sliderValue;
			this.current=new Color(rv,gv,bv);
		}
	}
	boolean hold=false;
	public void onMouseClick(int mouseButton, int x, int y, int width, int relativeMouseX, int relativeMouseY) {
		hold=true;
		if (r.mouseClicked(mc, relativeMouseX + x, relativeMouseY + y))whichbutton=0;
		if (g.mouseClicked(mc, relativeMouseX + x, relativeMouseY + y))whichbutton=1;
		if (b.mouseClicked(mc, relativeMouseX + x, relativeMouseY + y))whichbutton=2;
		r.displayString=Integer.toString(current.getRed());
		g.displayString=Integer.toString(current.getGreen());
		b.displayString=Integer.toString(current.getBlue());
		if (relativeMouseX >= width - 100 && relativeMouseX <= width && relativeMouseY >= 2 && relativeMouseY <= 22) {
			mc.sndManager.playSound("random.click", SoundCategory.GUI_SOUNDS, 1.0F, 1.0F);
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
		hold=false;
		r.dragging=g.dragging=b.dragging=false;
		float rv= (float) r.sliderValue;
		float gv= (float) g.sliderValue;
		float bv= (float) b.sliderValue;
		r.displayString=Integer.toString(current.getRed());
		g.displayString=Integer.toString(current.getGreen());
		b.displayString=Integer.toString(current.getBlue());
		whichbutton=4;
		this.current=new Color(rv,gv,bv);
	}

	public void onKeyPress(int keyCode, char character) {
	}

	public boolean matchesSearchTerm(String term) {
		return I18n.getInstance().translateKey(this.name).toLowerCase().contains(term.toLowerCase());
	}

	protected void renderButton(int x, int y, int relativeButtonX, int relativeButtonY, int buttonWidth, int buttonHeight, int relativeMouseX, int relativeMouseY) {
		if (this.resetButton.enabled) {
			this.resetButton.xPosition = x + 32;
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
