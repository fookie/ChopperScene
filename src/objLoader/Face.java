package objLoader;

public class Face {
	private int vindex[] = new int[3];
	private int nindex[] = new int[3];
	private int tindex[] = new int[3];

	public Face(int[] v, int[] t, int[] n) {
		vindex = v;
		nindex = n;
		tindex = t;
	}

	public int[] getVertex() {
		return vindex;
	}

	public int[] getNormal() {
		return nindex;
	}

	public int[] getTexture() {
		return tindex;
	}

}
