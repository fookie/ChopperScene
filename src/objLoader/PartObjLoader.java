package objLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;

public class PartObjLoader {
	private ArrayList<Point4f> v = new ArrayList<>();
	private ArrayList<Vector4f> vn = new ArrayList<>();
	private ArrayList<Face> f = new ArrayList<>();
	private ArrayList<TextureCoords> vt = new ArrayList<>();
	private Model m = null;
	private ArrayList<Model> models = new ArrayList<>();
	private int currentHeight = -1;

	public boolean LoadFile(String path) {
		File file = new File(path);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] elementofaline = line.split(" ");
				if (elementofaline[0].equals("#")) {

					if (elementofaline.length < 2) {
						continue;
					}

					if (elementofaline[1].equals("object")) {
						if (m != null) {
							m.assignFaces(f);
							models.add(m);
							f.clear();
						}
						m = new Model(elementofaline[2]);
					} else if (elementofaline[1].equals("h")) {
						m.assignHeight(Integer.parseInt(elementofaline[2]));
						//TODO children distribute is necessary
					}

				} else if (elementofaline[0].equals("v")) {// coordinate of a
															// vertex
					v.add(new Point4f(Float.parseFloat(elementofaline[2]), Float.parseFloat(elementofaline[3]),
							Float.parseFloat(elementofaline[4]), 0));
				} else if (elementofaline[0].equals("vn")) {// coordinate of
															// normals
					vn.add(new Vector4f(Float.parseFloat(elementofaline[1]), Float.parseFloat(elementofaline[2]),
							Float.parseFloat(elementofaline[3]), 0));
				} else if (elementofaline[0].equals("vt")) {// texture
															// coordinates
					vt.add(new TextureCoords(Float.parseFloat(elementofaline[1]), Float.parseFloat(elementofaline[2]),
							Float.parseFloat(elementofaline[3])));
				} else if (elementofaline[0].equals("f")) {// faces
					String[][] dimens = { elementofaline[1].split("/"), elementofaline[2].split("/"),
							elementofaline[3].split("/") };
					int[][] intdimens = new int[3][3];
					for (int i = 0; i < 3; i++) {
						for (int j = 0; j < 3; j++) {
							intdimens[j][i] = Integer.parseInt(dimens[i][j]);
						}
					}
					f.add(new Face(intdimens[0], intdimens[1], intdimens[2]));
				}

			}
			v.trimToSize();
			vn.trimToSize();
			f.trimToSize();
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.err.println(path + " not found.");
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println(path + " reading failed.");
			return false;
		}
		return true;
	}

	public void draw(ArrayList<Face> f, Texture texture) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int i = 0; i < f.size(); i++) {
			for (int j = 0; j < 3; j++) {
				GL11.glTexCoord3f(vt.get(f.get(i).getTexture()[j] - 1).getU(),
						vt.get(f.get(i).getTexture()[j] - 1).getV(), vt.get(f.get(i).getTexture()[j] - 1).getW());
				GL11.glNormal3f(vn.get(f.get(i).getNormal()[j] - 1).x, vn.get(f.get(i).getNormal()[j] - 1).y,
						vn.get(f.get(i).getNormal()[j] - 1).z);
				GL11.glVertex3f(v.get(f.get(i).getVertex()[j] - 1).x, v.get(f.get(i).getVertex()[j] - 1).y,
						v.get(f.get(i).getVertex()[j] - 1).z);
			}
		}
		GL11.glEnd();
	}

	public void drawMatrix(Texture texture) {
		Iterator<Model> modeliter = models.iterator();
		while (modeliter.hasNext()) {
			Model tm = modeliter.next();
			if (currentHeight == -1) {
				currentHeight = tm.getHeight();
			}
			if (tm.getHeight() > currentHeight) {
				GL11.glPushMatrix();
				draw(tm.getFace(), texture);
				currentHeight--;
			} else if (tm.getHeight() < currentHeight && tm.getHeight() != 0) {
				draw(tm.getFace(), texture);
				GL11.glPopMatrix();
				currentHeight++;
			}else if(tm.getHeight() == 0){
				
			}
		}
	}

	public ArrayList<Point4f> getVertex() {
		return v;
	}

	public ArrayList<Vector4f> getNormal() {
		return vn;
	}

	public ArrayList<Face> getFace() {
		return f;
	}
}
