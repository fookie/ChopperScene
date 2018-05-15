package objLoader;

import java.util.ArrayList;

public class Model {
	private ArrayList<Face> faces;
	private int height = 0;
	private ArrayList<Model> children;
	private String name;

	public Model(String n) {
		name = n;
	}

	public void assignHeight(int h) {
		height = h;
	}

	public void assignFaces(ArrayList<Face> f){
		faces = f;
	}
	
	public String getName() {
		return name;
	}

	public void addChild(Model c) {
		children.add(c);
	}

	public ArrayList<Model> getChildren() {
		return children;
	}

	public ArrayList<Face> getFace() {
		return faces;
	}

	public int getHeight() {
		return height;
	}
}
