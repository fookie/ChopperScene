package objects3D;

import org.lwjgl.opengl.GL11;

import GraphicsObjects.Point4f;

public class Sphere {

	public Sphere() {

	}

	public void DrawSphere(float radius, float nSlices, float nSegments) {

		float inctheta = (float) ((2.0d * Math.PI) / nSlices); // set the
																// accuracy
		float incphi = (float) (Math.PI / nSegments);

		GL11.glBegin(GL11.GL_QUADS);// draw with rectangles
		for (float theta = (float) -Math.PI; theta < Math.PI; theta += inctheta) {
			for (float phi = (float) -(Math.PI / 2.0f); phi < (Math.PI / 2.0f); phi += incphi) {

				Point4f vertices[][] = new Point4f[2][2];

				for (int i = 0; i < 2; i++) {// find another 4 points to set a
												// rectangle
					for (int j = 0; j < 2; j++) {
						vertices[i][j] = new Point4f(
								(float) (radius * Math.cos(phi + i * incphi) * Math.cos(theta + j * inctheta)),
								(float) (radius * Math.cos(phi + i * incphi) * Math.sin(theta + j * inctheta)),
								(float) (radius * Math.sin(phi + i * incphi)), 0);
					}
				}

				// draw the rectangle, there's a normal for every point so that
				// the sphere will be smooth enough
				GL11.glNormal3f(vertices[0][0].x, vertices[0][0].y, vertices[0][0].z);
				GL11.glVertex3f(vertices[0][0].x, vertices[0][0].y, vertices[0][0].z);
				
				GL11.glNormal3f(vertices[0][1].x, vertices[0][1].y, vertices[0][1].z);
				GL11.glVertex3f(vertices[0][1].x, vertices[0][1].y, vertices[0][1].z);

				GL11.glNormal3f(vertices[1][1].x, vertices[1][1].y, vertices[1][1].z);
				GL11.glVertex3f(vertices[1][1].x, vertices[1][1].y, vertices[1][1].z);
				
				GL11.glNormal3f(vertices[1][0].x, vertices[1][0].y, vertices[1][0].z);
				GL11.glVertex3f(vertices[1][0].x, vertices[1][0].y, vertices[1][0].z);
			}
		}
		GL11.glEnd();
	}
}
