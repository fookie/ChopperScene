package objects3D.apache;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import objLoader.ObjLoader;

public class TailRotor extends ObjLoader {

//	private float y = 0f, z = 0f;

	public TailRotor() {
		super();
	}

	@Override
	public void draw(Texture texture) {
		//coordinate keys
//		float offset = 0.001f;
//		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)) {
//			y += offset;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {
//			y -= offset;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
//			z += offset;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
//			z -= offset;
//		}
//		if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5)) {
//			System.out.println("y: " + y + " z: " + z);
//		}
		GL11.glTranslatef(0, -0.90999186f, -0.13200009f);
		super.draw(texture);
	}
}
