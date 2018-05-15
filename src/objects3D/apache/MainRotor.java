package objects3D.apache;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import objLoader.ObjLoader;

public class MainRotor extends ObjLoader {

	public MainRotor() {
		super();
	}

	@Override
	public void draw(Texture texture) {
		GL11.glTranslatef(0.000005f, -0.0012135f, -0.16995f);
		super.draw(texture);
	}
}
