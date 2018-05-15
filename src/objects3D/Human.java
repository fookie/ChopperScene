package objects3D;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import GraphicsObjects.Utils;

public class Human {

	// basic colours
	static float black[] = { 0.0f, 0.0f, 0.0f, 1.0f };
	static float white[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	static float grey[] = { 0.5f, 0.5f, 0.5f, 1.0f };
	static float spot[] = { 0.1f, 0.1f, 0.1f, 0.5f };

	// primary colours
	static float red[] = { 1.0f, 0.0f, 0.0f, 1.0f };
	static float green[] = { 0.0f, 1.0f, 0.0f, 1.0f };
	static float blue[] = { 0.0f, 0.0f, 1.0f, 1.0f };

	// secondary colours
	static float yellow[] = { 1.0f, 1.0f, 0.0f, 1.0f };
	static float magenta[] = { 1.0f, 0.0f, 1.0f, 1.0f };
	static float cyan[] = { 0.0f, 1.0f, 1.0f, 1.0f };

	// other colours
	static float orange[] = { 1.0f, 0.5f, 0.0f, 1.0f, 1.0f };
	static float brown[] = { 0.5f, 0.25f, 0.0f, 1.0f, 1.0f };
	static float dkgreen[] = { 0.0f, 0.5f, 0.0f, 1.0f, 1.0f };
	static float pink[] = { 1.0f, 0.6f, 0.6f, 1.0f, 1.0f };

	public Human() {

	}

	// Implement using notes in Animation lecture
	public void DrawHuman(float delta, boolean GoodAnimation, Texture face) {
		float theta = (float) (delta * 2 * Math.PI);
		float LimbRotation;
		if (GoodAnimation) {
			LimbRotation = (float) Math.cos(theta) * 45;
		} else {
			LimbRotation = 0;
		}

		Sphere sphere = new Sphere();
		Cylinder cylinder = new Cylinder();
		TexSphere texsphere = new TexSphere();

		GL11.glPushMatrix();

		{
			GL11.glTranslatef(0.0f, 0.5f, 0.0f);
			sphere.DrawSphere(0.5f, 32, 32);

			// chest
			GL11.glColor3f(green[0], green[1], green[2]);
			GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(green));
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(0.0f, 0.5f, 0.0f);
				sphere.DrawSphere(0.5f, 32, 32);

				// neck
				GL11.glColor3f(orange[0], orange[1], orange[2]);
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
				GL11.glPushMatrix();
				{
					GL11.glTranslatef(0.0f, 0.0f, 0.0f);
					GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
					// GL11.glRotatef(45.0f,0.0f,1.0f,0.0f);
					cylinder.DrawCylinder(0.15f, 0.7f, 32);

					// head
					GL11.glColor3f(white[0], white[1], white[2]);
					GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(white));
					GL11.glPushMatrix();
					{
						GL11.glTranslatef(0.0f, 0.0f, 1.0f);
						texsphere.DrawTexSphere(0.5f, 32, 32, face);
						GL11.glPopMatrix();
					}
					GL11.glPopMatrix();

					// left shoulder
					GL11.glColor3f(blue[0], blue[1], blue[2]);
					GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
					GL11.glPushMatrix();
					{
						GL11.glTranslatef(0.5f, 0.4f, 0.0f);
						sphere.DrawSphere(0.25f, 32, 32);

						// left arm
						GL11.glColor3f(orange[0], orange[1], orange[2]);
						GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
						GL11.glPushMatrix();
						{
							GL11.glTranslatef(0.0f, 0.0f, 0.0f);
							GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);

							GL11.glRotatef(-LimbRotation, 1.0f, 0.0f, 0.0f);
							// GL11.glRotatef(27.5f,0.0f,1.0f,0.0f);
							cylinder.DrawCylinder(0.15f, 0.7f, 32);

							// left elbow
							GL11.glColor3f(blue[0], blue[1], blue[2]);
							GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
							GL11.glPushMatrix();
							{
								GL11.glTranslatef(0.0f, 0.0f, 0.75f);
								sphere.DrawSphere(0.2f, 32, 32);

								// left forearm
								GL11.glColor3f(orange[0], orange[1], orange[2]);
								GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
								GL11.glPushMatrix();
								{
									GL11.glTranslatef(0.0f, 0.0f, 0.0f);
									GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
									// GL11.glRotatef(90.0f,0.0f,1.0f,0.0f);
									cylinder.DrawCylinder(0.1f, 0.7f, 32);

									// left hand
									GL11.glColor3f(blue[0], blue[1], blue[2]);
									GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,
											Utils.ConvertForGL(blue));
									GL11.glPushMatrix();
									{
										GL11.glTranslatef(0.0f, 0.0f, 0.75f);
										sphere.DrawSphere(0.2f, 32, 32);

									}
									GL11.glPopMatrix();
								}
								GL11.glPopMatrix();
							}
							GL11.glPopMatrix();
						}
						GL11.glPopMatrix();
					}
					GL11.glPopMatrix();
					// to chest

					// This part is copied from above. Just change the position
					// of the shoulder and moving direction.
					// right shoulder

					GL11.glColor3f(blue[0], blue[1], blue[2]);
					GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
					GL11.glPushMatrix();
					{
						GL11.glTranslatef(-0.5f, 0.4f, 0.0f);
						sphere.DrawSphere(0.25f, 32, 32);
						GL11.glColor3f(orange[0], orange[1], orange[2]);
						GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
						GL11.glPushMatrix();
						{
							GL11.glTranslatef(0.0f, 0.0f, 0.0f);
							GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);

							GL11.glRotatef(LimbRotation, 1.0f, 0.0f, 0.0f);
							// GL11.glRotatef(27.5f,0.0f,1.0f,0.0f);
							cylinder.DrawCylinder(0.15f, 0.7f, 32);

							// right elbow
							GL11.glColor3f(blue[0], blue[1], blue[2]);
							GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
							GL11.glPushMatrix();
							{
								GL11.glTranslatef(0.0f, 0.0f, 0.75f);
								sphere.DrawSphere(0.2f, 32, 32);

								// right forearm
								GL11.glColor3f(orange[0], orange[1], orange[2]);
								GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
								GL11.glPushMatrix();
								{
									GL11.glTranslatef(0.0f, 0.0f, 0.0f);
									GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
									// GL11.glRotatef(90.0f,0.0f,1.0f,0.0f);
									cylinder.DrawCylinder(0.1f, 0.7f, 32);

									// right hand
									GL11.glColor3f(blue[0], blue[1], blue[2]);
									GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE,
											Utils.ConvertForGL(blue));
									GL11.glPushMatrix();
									{
										GL11.glTranslatef(0.0f, 0.0f, 0.75f);
										sphere.DrawSphere(0.2f, 32, 32);

									}
									GL11.glPopMatrix();
								}
								GL11.glPopMatrix();
							}
							GL11.glPopMatrix();
						}
						GL11.glPopMatrix();
						// chest
					}
					GL11.glPopMatrix();

				}
				GL11.glPopMatrix();

				// pelvis

				// left hip
				GL11.glColor3f(blue[0], blue[1], blue[2]);
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
				GL11.glPushMatrix();
				{
					GL11.glTranslatef(-0.5f, -0.2f, 0.0f);

					sphere.DrawSphere(0.25f, 32, 32);

					// left high leg
					GL11.glColor3f(orange[0], orange[1], orange[2]);
					GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
					GL11.glPushMatrix();
					{
						GL11.glTranslatef(0.0f, 0.0f, 0.0f);
						GL11.glRotatef(10.0f, 1.0f, 0.0f, 0.0f);

						GL11.glRotatef((-LimbRotation) + 90, 1.0f, 0.0f, 0.0f);
						// GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);
						cylinder.DrawCylinder(0.15f, 0.7f, 32);

						// left knee
						GL11.glColor3f(blue[0], blue[1], blue[2]);
						GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
						GL11.glPushMatrix();
						{
							GL11.glTranslatef(0.0f, 0.0f, 0.75f);
							GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
							sphere.DrawSphere(0.25f, 32, 32);

							// left low leg
							GL11.glColor3f(orange[0], orange[1], orange[2]);
							GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
							GL11.glPushMatrix();
							{
								GL11.glTranslatef(0.0f, 0.0f, 0.0f);
								// GL11.glRotatef(120.0f,1.0f,0.0f,0.0f);
								GL11.glRotatef(-Math.abs(LimbRotation), 1.0f, 0.0f, 0.0f);
								cylinder.DrawCylinder(0.15f, 0.7f, 32);

								// left foot
								GL11.glColor3f(blue[0], blue[1], blue[2]);
								GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
								GL11.glPushMatrix();
								{
									GL11.glTranslatef(0.0f, 0.0f, 0.75f);
									sphere.DrawSphere(0.3f, 32, 32);

								}
								GL11.glPopMatrix();
							}
							GL11.glPopMatrix();
						}
						GL11.glPopMatrix();
					}
					GL11.glPopMatrix();
				}
				GL11.glPopMatrix();

				// pelvis

				// right hip
				GL11.glColor3f(blue[0], blue[1], blue[2]);
				GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
				GL11.glPushMatrix();
				{
					GL11.glTranslatef(0.5f, -0.2f, 0.0f);

					sphere.DrawSphere(0.25f, 32, 32);

					// right high leg
					GL11.glColor3f(orange[0], orange[1], orange[2]);
					GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
					GL11.glPushMatrix();
					{
						GL11.glTranslatef(0.0f, 0.0f, 0.0f);
						GL11.glRotatef(-10.0f, 0.0f, 0.0f, 0.0f);

						GL11.glRotatef((LimbRotation) + 90, 1.0f, 0.0f, 0.0f);
						// GL11.glRotatef(90.0f,1.0f,0.0f,0.0f);
						cylinder.DrawCylinder(0.15f, 0.7f, 32);

						// right knee
						GL11.glColor3f(blue[0], blue[1], blue[2]);
						GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
						GL11.glPushMatrix();
						{
							GL11.glTranslatef(0.0f, 0.0f, 0.75f);
							GL11.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
							sphere.DrawSphere(0.25f, 32, 32);

							// right low leg
							GL11.glColor3f(orange[0], orange[1], orange[2]);
							GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(orange));
							GL11.glPushMatrix();
							{
								GL11.glTranslatef(0.0f, 0.0f, 0.0f);
								GL11.glRotatef(-Math.abs(LimbRotation), 1.0f, 0.0f, 0.0f);
								// GL11.glRotatef(0.0f,0.0f,0.0f,0.0f);
								cylinder.DrawCylinder(0.15f, 0.7f, 32);

								// right foot
								GL11.glColor3f(blue[0], blue[1], blue[2]);
								GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, Utils.ConvertForGL(blue));
								GL11.glPushMatrix();
								{
									GL11.glTranslatef(0.0f, 0.0f, 0.75f);
									sphere.DrawSphere(0.3f, 32, 32);

								}
								GL11.glPopMatrix();
							}
							GL11.glPopMatrix();
						}
						GL11.glPopMatrix();
					}
					GL11.glPopMatrix();
				}
				GL11.glPopMatrix();

			}
			GL11.glPopMatrix();

		}

	}

}

/*
 * 
 * 
 * }
 * 
 */
