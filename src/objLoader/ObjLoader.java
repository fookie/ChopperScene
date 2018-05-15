package objLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import GraphicsObjects.Point4f;
import GraphicsObjects.Vector4f;

public class ObjLoader {
	protected ArrayList<Point4f> v = new ArrayList<>();
	protected ArrayList<Vector4f> vn = new ArrayList<>();
	protected ArrayList<Face> f = new ArrayList<>();
	protected ArrayList<TextureCoords> vt = new ArrayList<>();

	public boolean LoadFile(String path) {
		File file = new File(path);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				String[] t = temp.split(" ");
				if (t[0].equals("#")) {
					continue;
				} else if (t[0].equals("v")) {// coordinate of a vertex
					v.add(new Point4f(Float.parseFloat(t[2]), Float.parseFloat(t[3]), Float.parseFloat(t[4]), 0));

				} else if (t[0].equals("vn")) {// coordinate of normals
					vn.add(new Vector4f(Float.parseFloat(t[1]), Float.parseFloat(t[2]), Float.parseFloat(t[3]), 0));
				} else if (t[0].equals("vt")) {// texture coordinates
					if (t.length == 4) {
						vt.add(new TextureCoords(Float.parseFloat(t[1]), Float.parseFloat(t[2]),
								Float.parseFloat(t[3])));
					} else {
						vt.add(new TextureCoords(Float.parseFloat(t[1]), Float.parseFloat(t[2]), 0f));
					}

				} else if (t[0].equals("f")) {// faces
					String[][] dimens = { t[1].split("/"), t[2].split("/"), t[3].split("/") };
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
			vt.trimToSize();
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

	public void draw(Texture texture) {
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (int i = 0; i < f.size(); i++) {
			for (int j = 0; j < 3; j++) {
				GL11.glNormal3f(vn.get(f.get(i).getNormal()[j] - 1).x, vn.get(f.get(i).getNormal()[j] - 1).y,
						vn.get(f.get(i).getNormal()[j] - 1).z);
				GL11.glTexCoord2f(vt.get(f.get(i).getTexture()[j] - 1).getU(),
						1 - vt.get(f.get(i).getTexture()[j] - 1).getV());
				GL11.glVertex3f(v.get(f.get(i).getVertex()[j] - 1).x, v.get(f.get(i).getVertex()[j] - 1).y,
						v.get(f.get(i).getVertex()[j] - 1).z);
			}
		}
		GL11.glEnd();
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
