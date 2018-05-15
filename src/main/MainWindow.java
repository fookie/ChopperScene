package main;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import GraphicsObjects.Arcball;
import GraphicsObjects.Utils;
import GraphicsObjects.Vector4f;
import objects3D.DogSign;
import objects3D.LandPad;
import objects3D.apache.Apache;
import objects3D.land.Ground;

//Main windows class controls and creates the 3D virtual world , please do not change this class but edit the other classes to complete the assignment. 
// Main window is built upon the standard Helloworld LWJGL class which I have heavily modified to use as your standard openGL environment. 
// 

// Do not touch this class, I will be making a version of it for your 3rd Assignment 
public class MainWindow {

	private boolean MouseOnepressed = true;
	private boolean dragMode = false;
	/** position of pointer */
	float x = 400, y = 300;
	/** angle of rotation */
	float rotation = 0;
	/** time at last frame */
	long lastFrame;
	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	long myDelta = 0; // to use for animation
	float Alpha = 0; // to use for animation
	long StartTime; // beginAnimiation

	Arcball MyArcball = new Arcball();

	boolean DRAWGRID = false;
	boolean waitForKeyrelease = true;
	/** Mouse movement */
	int LastMouseX = -1;
	int LastMouseY = -1;

	float pullX = 0.0f; // arc ball X cord.
	float pullY = 0.0f; // arc ball Y cord.

	float gunAngle = 0f;

	int OrthoNumber = 650; // using this for screen size, making a window of
							// 1200 x 800 so aspect ratio 3:2 // do not change
							// this for assignment 3 but you can change
							// everything for your project

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

	// static GLfloat light_position[] = {0.0, 100.0, 100.0, 0.0};

	// support method to aid in converting a java float array into a Floatbuffer
	// which is faster for the opengl layer to process

	private Apache apache;
	private Ground land;
	private DogSign dogSign = new DogSign();
	private LandPad landPad = new LandPad();
	private float viewShift = 0;

	public void start() throws IOException {

		StartTime = getTime();
		try {
			Display.setDisplayMode(new DisplayMode(1200, 800));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		apache = new Apache();
		apache.loadFile();
		land = new Ground();
		land.loadFile();
		dogSign.loadFile();
		landPad.loadFile();

		initGL(); // init OpenGL
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		while (!Display.isCloseRequested()) {
			int delta = getDelta();
			update(delta);
			renderGL();
			Display.update();
			Display.sync(120); // cap fps to 120fps
		}

		Display.destroy();
	}

	public void update(int delta) {
		// rotate quad
		// rotation += 0.01f * delta;

		int MouseX = Mouse.getX();
		int MouseY = Mouse.getY();
		int WheelPostion = Mouse.getDWheel();

		boolean MouseButonPressed = Mouse.isButtonDown(0);

		if (MouseButonPressed && !MouseOnepressed) {
			MouseOnepressed = true;
			// System.out.println("Mouse drag mode");
			MyArcball.startBall(MouseX, MouseY, 1200, 800);
			dragMode = true;

		} else if (!MouseButonPressed) {
			// System.out.println("Mouse drag mode end ");
			MouseOnepressed = false;
			dragMode = false;
		}

		if (dragMode) {
			MyArcball.updateBall(MouseX, MouseY, 1200, 800);
		}

		if (WheelPostion > 0) {
			OrthoNumber += 1;

		}

		if (WheelPostion < 0) {
			OrthoNumber -= 1;
			if (OrthoNumber < 650) {
				OrthoNumber = 650;
			}

		}

		/** to check if key is released */
		if (Keyboard.isKeyDown(Keyboard.KEY_G) == false) {
			waitForKeyrelease = true;
		} else {
			waitForKeyrelease = false;

		}

		// keep quad on the screen
		if (x < 0)
			x = 0;
		if (x > 1200)
			x = 1200;
		if (y < 0)
			y = 0;
		if (y > 800)
			y = 800;

		updateFPS(); // update FPS Counter

		LastMouseX = MouseX;
		LastMouseY = MouseY;
	}

	/**
	 * Calculate how many milliseconds have passed since last frame.
	 * 
	 * @return milliseconds passed since last frame
	 */
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		changeOrth();
		MyArcball.startBall(0, 0, 1200, 800);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
		lightPos.put(10000f).put(1000f).put(1000).put(0).flip();
		// lightPos.put(0).put(0).put(0).put(0).flip();

		FloatBuffer lightPos2 = BufferUtils.createFloatBuffer(4);
		lightPos2.put(0f).put(1000f).put(0).put(-1000f).flip();

		FloatBuffer lightPos3 = BufferUtils.createFloatBuffer(4);
		lightPos3.put(-10000f).put(1000f).put(1000).put(0).flip();

		FloatBuffer lightPos4 = BufferUtils.createFloatBuffer(4);
		lightPos4.put(1000f).put(1000f).put(1000f).put(0).flip();

		GL11.glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, lightPos); // specify the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT0); // switch light #0 on // I've setup
		// specific materials so in real light it will look a bit strange

		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION, lightPos); // specify the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT1); // switch light #0 on
		GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, Utils.ConvertForGL(spot));

		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_POSITION, lightPos3); // specify
																	// the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT2); // switch light #0 on
		GL11.glLight(GL11.GL_LIGHT2, GL11.GL_DIFFUSE, Utils.ConvertForGL(white));

		GL11.glLight(GL11.GL_LIGHT3, GL11.GL_POSITION, lightPos4); // specify
																	// the
																	// position
																	// of the
																	// light
		GL11.glEnable(GL11.GL_LIGHT3); // switch light #0 on
		GL11.glLight(GL11.GL_LIGHT3, GL11.GL_DIFFUSE, Utils.ConvertForGL(grey));

		GL11.glEnable(GL11.GL_LIGHTING); // switch lighting on
		GL11.glEnable(GL11.GL_DEPTH_TEST); // make sure depth buffer is switched
											// on
		GL11.glEnable(GL11.GL_NORMALIZE); // normalize normal vectors for safety
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
		} // load in texture

	}

	public void changeOrth() {

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		// GL11.glOrtho(1200 - OrthoNumber, OrthoNumber, (800 - (OrthoNumber *
		// 0.66f)), (OrthoNumber * 0.66f), 100000,
		// -100000);
		GLU.gluPerspective(60, 1, 1, 9000);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		FloatBuffer CurrentMatrix = BufferUtils.createFloatBuffer(16);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, CurrentMatrix);

		// MyArcball.getMatrix(CurrentMatrix);

		GL11.glLoadMatrix(CurrentMatrix);

	}

	long totaltime = (myDelta / 1000);
	boolean viewingTop = false;
	float rotorRotation = 0f, rotorRotateRate = 0f, rotorRotateRateAcc = 0f;
	float bodyAngleX = 0f, bodyRotateRateX = 0f;
	float bodyAngleZ = 0f, bodyRotateRateZ = 0f;
	float velocityX = 0f, velocityY = 0f, velocityZ = 0f;
	float airFrictionX = 0f, airFricitonY = 0f, airFrictionZ = 0f;
	float airFriction = 0.005f;
	float gravity = -0.0004f;
	float accX = 0f, accY = gravity, accZ = 0f;// gravity
	float accLength = 0f;
	float bodyX = 0f, bodyY = 3.64f, bodyZ = 0f;

	/*
	 * You can edit this method to add in your own objects / remember to load in
	 * textures in the INIT method as they take time to load
	 * 
	 */
	public void renderGL() {
		changeOrth();
		GL11.glPushMatrix();
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
			GL11.glColor3f(0.5f, 0.5f, 1.0f);
			myDelta = getTime() - StartTime;
			totaltime = myDelta / 1000;

			// Vector4f cam_target = new Vector4f(-bodyX, bodyY, bodyZ, 0);
			// GL11.glTranslatef(600, 400, 0);
			Vector4f cam_pos;
			Vector4f cam_towards;

			// helicopter controls
			// view controls
			// hold left ctrl to switch view to left back of the chopper
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				if (viewShift < 10) {
					viewShift += 0.5f;
				} else {
					viewShift = 10;
				}
			} else {
				if (viewShift > 0) {
					viewShift -= 0.5f;
				} else {
					viewShift = 0;
				}
			}
			// view control
			if (Keyboard.isKeyDown(Keyboard.KEY_T)) {// top view
				viewingTop = true;
			} else {
				viewingTop = false;
			}
			// engine control
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				if (rotorRotateRate < 10f) {
					rotorRotateRateAcc = 0.008f;
				} else {
					rotorRotateRateAcc = 0;
				}
			} else {
				if (rotorRotateRate > 0) {
					rotorRotateRateAcc = -0.004f;
				} else {
					rotorRotateRate = 0;
				}
			}

			// angle control
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2)) {
				if (bodyAngleX > -70)
					bodyAngleX -= 0.25f;
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8)) {
				if (bodyAngleX < 70)
					bodyAngleX += 0.25f;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6)) {
				if (bodyAngleZ < 60)
					bodyAngleZ += 0.25f;
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4)) {
				if (bodyAngleZ > -60)
					bodyAngleZ -= 0.25f;
			}

			float autobalanceFactor = 0.05f;
			// auto balance
			if (bodyAngleZ < 0) {
				bodyAngleZ += autobalanceFactor;
			} else if (bodyAngleZ > 0) {
				bodyAngleZ -= autobalanceFactor;
			}
			if (bodyAngleX < 0) {
				bodyAngleX += autobalanceFactor;
			} else if (bodyAngleX > 0) {
				bodyAngleX -= autobalanceFactor;
			}

			// controlled parameters
			accLength = rotorRotateRate * 0.0001f;
			bodyAngleX += bodyRotateRateX;
			bodyAngleZ += bodyRotateRateZ;
			rotorRotateRate += rotorRotateRateAcc;

			double bodyAngleXRadian = bodyAngleX * Math.PI / 180;
			double bodyAngleZRadian = bodyAngleZ * Math.PI / 180;
			// physical calculations
			if (bodyAngleX != 0 && bodyAngleZ == 0) {
				accX = (float) (Math.sin(bodyAngleXRadian) * accLength);
				accY = (float) (Math.cos(bodyAngleXRadian) * Math.cos(bodyAngleZRadian) * accLength) + gravity;
				accZ = (float) (Math.cos(bodyAngleXRadian) * Math.sin(bodyAngleZRadian) * accLength);
			} else if (bodyAngleZ != 0 && bodyAngleX == 0) {
				accZ = (float) (Math.sin(bodyAngleZRadian) * accLength);
				accX = (float) (Math.cos(bodyAngleZRadian) * Math.sin(bodyAngleXRadian) * accLength);
				accY = (float) (Math.cos(bodyAngleZRadian) * Math.cos(bodyAngleXRadian) * accLength) + gravity;
			} else {// I don't want to implement the Slerp algorithm, it's too
					// hard, I'll use some trick to reduce the influence
				accZ = (float) (Math.sin(bodyAngleZRadian) * accLength);
				accX = (float) (Math.cos(bodyAngleZRadian) * Math.sin(bodyAngleXRadian) * accLength);
				accY = (float) (Math.cos(bodyAngleZRadian) * Math.cos(bodyAngleXRadian) * accLength) + gravity;
			}
			System.out.println(accX + " " + accY + " " + accZ + " Altitude: " + bodyY);

			velocityX += accX;
			velocityX *= 1 - airFriction;

			velocityY += accY;
			velocityY *= 1 - airFriction;
			if (bodyY <= 3.64f) {// do not get underground
				if (accY < 0) {
					velocityY = 0;
				}
			}

			velocityZ += accZ;
			velocityZ *= 1 - airFriction;

			bodyX += velocityX;
			bodyY += velocityY;
			bodyZ += velocityZ;
			rotorRotation += rotorRotateRate;

			cam_towards = new Vector4f(0, 1, 0, 0);
			if (viewingTop) {
				cam_pos = new Vector4f(0, 150, 0, 0);
				cam_towards = new Vector4f(0, 0, 1, 0);
				// OrthoNumber = 800;
			} else {
				cam_pos = new Vector4f(-100, 20, -5 + viewShift, 0);
				// OrthoNumber = 650;
			}
			Vector4f cam_target = new Vector4f(bodyX, bodyY, bodyZ, 0);
			GLU.gluLookAt(-cam_target.x - cam_pos.x, cam_target.y + cam_pos.y, cam_target.z + cam_pos.z, cam_target.x, cam_target.y, cam_target.z, cam_towards.x, cam_towards.y, cam_towards.z);
			GL11.glScalef(5, 5, 5);
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(0, 0.5f, 0);
				landPad.draw();
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{

				GL11.glTranslatef(-400, 0, 0);
				land.draw();
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(0, 0, 50);
				GL11.glRotatef(90, 0, 1, 0);
				dogSign.draw();
			}
			GL11.glPopMatrix();
			GL11.glPushMatrix();
			{
				GL11.glTranslatef(-bodyX, bodyY, bodyZ);
				if (bodyAngleX != 0 && bodyAngleZ == 0) {
					GL11.glRotated(bodyAngleX - 2.2, 0, 0, 1);
					GL11.glRotatef(bodyAngleZ, 1, 0, 0);
				} else if (bodyAngleZ != 0 && bodyAngleX == 0) {
					GL11.glRotatef(bodyAngleZ, 1, 0, 0);
					GL11.glRotated(bodyAngleX - 2.2, 0, 0, 1);
				} else {// I don't want to implement the Slerp algorithm, it's
						// too hard, I'll use some trick to reduce the influence
					GL11.glRotated(bodyAngleX - 2.2, 0, 0, 1);
					GL11.glRotatef(bodyAngleZ, 1, 0, 0);
				}
				GL11.glRotated(Math.random() / 50, 1, 0, 0); // wiggle
				apache.draw(rotorRotation);
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}

	public static void main(String[] argv) throws IOException {
		MainWindow hello = new MainWindow();
		hello.start();
	}

	public void init() throws IOException {
		// sky = (Sky) TextureLoader.getTexture("png",
		// ResourceLoader.getResourceAsStream("res/brightSky.png"));
		System.out.println("Texture loaded okay ");
	}
}
