package objects3D;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import objLoader.ObjLoader;

public class LandPad {

	private ObjLoader landPad = new ObjLoader();

	private Texture pad;

	public void loadFile() throws IOException {
		landPad.LoadFile("res/landPad/landPad.obj");
		pad = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/landPad/landPad.png"));
	}

	public void draw() {
		GL11.glPushMatrix();
		GL11.glColor3f(1, 1, 1);
		GL11.glScalef(2f, 2f, 2f);
		pad.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		landPad.draw(pad);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}

}
