package kn.kinfuyuki.kstrabiomes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ButtonElement;
import net.minecraft.client.gui.options.OptionsButtonElement;
import net.minecraft.client.gui.options.components.ButtonComponent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static kn.kinfuyuki.kstrabiomes.main.LOGGER;

public class triggeroption extends ButtonComponent {
	final Method callable;
	public final ArrayList<Object> args;
	final String name;
	final Object caller;
	public triggeroption( String text, Method callable) {
		super(text);
		name=text;
		this.callable = callable;
		this.args = new ArrayList<>();
		caller=null;
	}
	@Override
	public boolean isDefault(){return  true;}
	public triggeroption( String text, Method callable,Object obj) {
		super(text);
		name=text;
		this.callable = callable;
		this.args = new ArrayList<>();
		caller=obj;
	}

	@Override
	public void resetValue() {

	}


	@Override
	protected void buttonClicked(int mouseButton, int x, int y, int width, int height, int relativeMouseX, int relativeMouseY) {
			try {
				callable.invoke(caller,this);
			} catch (Exception e) {
				LOGGER.error("could not call function of button "+this.name);
				LOGGER.error(e.toString());
			}

	}
}
