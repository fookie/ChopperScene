package objects3D;

import org.lwjgl.opengl.GL11;

public class Cylinder {

	public Cylinder() {
	}

	public void DrawCylinder(float radius, float height, int nSegments) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (float i = 0.0f; i < nSegments; i += 1.0) { /*
														 * a loop around
														 * circumference of a
														 * tube
														 */
			float angle = (float) (Math.PI * i * 2.0f / nSegments);
			float nextAngle = (float) (Math.PI * (i + 1.0f) * 2.0f / nSegments);
			/* compute sin & cosine */
			float x1 = (float) Math.sin(angle) * radius, y1 = (float) Math.cos(angle) * radius;
			float x2 = (float) Math.sin(nextAngle) * radius, y2 = (float) Math.cos(nextAngle) * radius;

			/* draw the top round */
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glVertex3f(0.0f, 0.0f, 1.0f * height);
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glVertex3f(x1, y1, 1.0f * height);
			GL11.glNormal3f(0.0f, 0.0f, 1.0f);
			GL11.glVertex3f(x2, y2, 1.0f * height);

			/* draw the bottom round */
			GL11.glNormal3f(0.0f, 0.0f, -1.0f);
			GL11.glVertex3f(0.0f, 0.0f, 0.0f);
			GL11.glNormal3f(0.0f, 0.0f, -1.0f);
			GL11.glVertex3f(x1, y1, 0.0f);
			GL11.glNormal3f(0.0f, 0.0f, -1.0f);
			GL11.glVertex3f(x2, y2, 0.0f);

			/* draw top (green) triangle */
			
			GL11.glNormal3d(x1, y1, 0.0f);
			GL11.glVertex3f(x1, y1, 0.0f);
			GL11.glNormal3d(x2, y2, 0.0f);
			GL11.glVertex3f(x2, y2, 1.0f * height);
			GL11.glNormal3d(x1, y1, 0.0f);
			GL11.glVertex3f(x1, y1, 1.0f * height);
			/* draw bottom (red) triangle */
			
			GL11.glNormal3d(x1, y1, 0.0f);
			GL11.glVertex3f(x1, y1, 0.0f);
			GL11.glNormal3d(x2, y2, 0.0f);
			GL11.glVertex3f(x2, y2, 0.0f);
			GL11.glNormal3d(x2, y2, 0.0f);
			GL11.glVertex3f(x2, y2, 1.0f * height);
		} /* a loop around circumference of a tube */

		GL11.glEnd();

	}
}
