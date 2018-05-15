package objects3D.land;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import objLoader.ObjLoader;

public class Sky {

	private ObjLoader land = new ObjLoader();
	private Texture texture;

	public void loadFile() throws IOException {
		land.LoadFile("res/land/land.obj");
		texture = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/brightSky.png"));
	}

	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslatef(0, -0.25f, 0);
		GL11.glColor3f(1, 1, 1);
		GL11.glScalef(5000, 5000, 5000);
		texture.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		land.draw(texture);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}

}
