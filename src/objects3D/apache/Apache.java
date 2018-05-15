package objects3D.apache;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import objLoader.ObjLoader;

public class Apache {
	private ObjLoader body = new ObjLoader();
	private ObjLoader mainRotor = new MainRotor();
	private ObjLoader tailRotor = new TailRotor();
	private ObjLoader dock = new ObjLoader();
	private ObjLoader gun = new Gun();
	private float gunAngle = 0f;
	private float gunDockAngle = 0f;

	private Texture universal;

	public Apache() throws IOException {

	}

	public void loadFile() throws IOException {
		body.LoadFile("res/apache/body.obj");
		tailRotor.LoadFile("res/apache/tailRotor.obj");
		mainRotor.LoadFile("res/apache/mainRotor.obj");
		dock.LoadFile("res/apache/dock.obj");
		gun.LoadFile("res/apache/gun.obj");

		universal = TextureLoader.getTexture("png", ResourceLoader.getResourceAsStream("res/apache/diffuse.png"));

	}

	public void draw(float spin) {
		FloatBuffer blueLight_ambient = BufferUtils.createFloatBuffer(4);
		blueLight_ambient.put(1).put(1).put(1).put(1).flip();
		FloatBuffer blueLight_pos = BufferUtils.createFloatBuffer(4);
		blueLight_pos.put(-0.140180f).put(0.184011f).put(0.050654f).put(0).flip();
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_AMBIENT, blueLight_ambient);
		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, blueLight_pos);
		// GL11.glEnable(GL11.GL_LIGHT0);

		GL11.glPushMatrix();
		GL11.glColor3f(1, 1, 1);
		GL11.glScalef(15, 15, -15);
		GL11.glRotatef(-90, 1, 0, 0);
		GL11.glRotatef(-90, 0, 0, 1);
		spin %= 360;
		universal.bind();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glPushMatrix();
		{
			body.draw(universal);
			{
				GL11.glPushMatrix();
				GL11.glTranslatef(-0.000005f, 0.0012135f, 0.16995f);
				GL11.glRotatef(-spin, 0, 0.000265f, -0.010879f);
				mainRotor.draw(universal);
				GL11.glPopMatrix();

				GL11.glPushMatrix();
				GL11.glTranslatef(0, 0.90999186f, 0.13200009f);
				GL11.glRotatef(spin, 1, 0, 0);
				tailRotor.draw(universal);
				GL11.glPopMatrix();

				if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
					if (gunDockAngle < 120f) {
						gunDockAngle += 1f;
					}
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
					if (gunDockAngle > -120f) {
						gunDockAngle -= 1f;
					}
				}

				GL11.glTranslatef(0, -0.25f, 0);
				GL11.glRotatef(gunDockAngle, 0, 0, 1); // dock rotation
				GL11.glTranslatef(0, 0.25f, 0);
				dock.draw(universal);

				GL11.glPushMatrix();
				{

					if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
						if (gunAngle <= 90) {
							gunAngle += 0.5f;
						}
					}

					if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
						if (gunAngle > -10f) {
							gunAngle -= 0.5f;
						}
					}

					GL11.glTranslatef(0, -0.25f, -0.14f);
					GL11.glRotated(gunAngle, 1, 0, 0); // gun rotation
					gun.draw(universal);
				}
				GL11.glPopMatrix();
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

}
