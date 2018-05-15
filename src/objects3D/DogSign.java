package objects3D;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import objLoader.ObjLoader;

public class DogSign {

	private ObjLoader sign = new ObjLoader();
	private ObjLoader columns = new ObjLoader();

	private Texture dogSign;

	public DogSign() {
	}

	public void loadFile() throws IOException {
		sign.LoadFile("res/dogSign/dogSign.obj");
		columns.LoadFile("res/dogSign/dogSign1.obj");

		dogSign = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/dogSign/dogsign.png"));
	}

	public void draw() {
		GL11.glPushMatrix();
		GL11.glColor3f(1, 1, 1);
		GL11.glRotatef(-90, 1, 0, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		GL11.glScalef(0.25f, 0.25f, 0.25f);
		dogSign.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		sign.draw(dogSign);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		columns.draw(dogSign);
		GL11.glPopMatrix();
	}
}
