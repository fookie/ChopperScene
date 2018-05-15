package objects3D.land;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import objLoader.ObjLoader;

public class Ground extends ObjLoader {

	private Texture texture;

	public void loadFile() throws IOException {
		LoadFile("res/land/land.obj");
		texture = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/land/land.png"));
	}

	public void draw() {
		GL11.glPushMatrix();
		GL11.glColor3f(1, 1, 1);
		GL11.glScalef(50, 50, 50);
		GL11.glTranslatef(0, -0.25f, 0);
		texture.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		draw(texture);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glPopMatrix();
	}

	@Override
	public void draw(Texture texture) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int i = 0; i < f.size(); i++) {
			for (int j = 0; j < 3; j++) {
				GL11.glNormal3f(vn.get(f.get(i).getNormal()[j] - 1).x, vn.get(f.get(i).getNormal()[j] - 1).y,
						vn.get(f.get(i).getNormal()[j] - 1).z);
				GL11.glTexCoord2f(vt.get(f.get(i).getTexture()[j] - 1).getU()*50*2,
						1 - vt.get(f.get(i).getTexture()[j] - 1).getV()*50*2);
				GL11.glVertex3f(v.get(f.get(i).getVertex()[j] - 1).x, v.get(f.get(i).getVertex()[j] - 1).y,
						v.get(f.get(i).getVertex()[j] - 1).z);
			}
		}
		GL11.glEnd();
	}

}
