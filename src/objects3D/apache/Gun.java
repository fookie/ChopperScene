package objects3D.apache;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import objLoader.ObjLoader;

public class Gun extends ObjLoader {
	public Gun() {
		super();
	}

	@Override
	public void draw(Texture texture) {
		GL11.glTranslatef(0, 0.25f, 0.14f);
		super.draw(texture);
	}
}
