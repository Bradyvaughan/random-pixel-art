package fun.grid;

import fun.fun.ColorGenerator;

public class ColorGrid {
	private final int nx;
	private final int ny;
	private int[][] data;
	
	public ColorGrid(int nx, int ny) {
		this.nx = nx;
		this.ny = ny;
		this.data = new int[nx * ny + 1][3];
		
		for (int i = 0; i < nx * ny; i++) {
			data[i] = ColorGenerator.white();
		}
	}
	
	public int[] get(Pair pair) {
		if (!isValid(pair)) {
			return ColorGenerator.black();
		}
		return data[getIndex(pair)];
	}
	
	public boolean isValid(Pair pair) {
		if (pair.x < 0 || pair.x > nx || pair.y < 0 || pair.y >= ny)  {
			return false;
		}
		
		return true;
	}
	
	public void setLoc(Pair loc, int[] value) {
		if (isValid(loc)) {
			data[getIndex(loc)] = value;
		}
	}
	
	public int getNX() {
		return nx;
	}
	
	public int getNY() {
		return ny;
	}
	
	private int getIndex(Pair pair) {
		return pair.y * nx + pair.x;
	}
	
	public ColorGrid inflate(int n) {
		ColorGrid newGrid = new ColorGrid(nx * n, ny * n);
		
		for (int i = 0; i <= newGrid.getNX(); i++) {
			for (int j = 0; j < newGrid.getNY(); j++) {
				int subI = i / n;
				int subJ = j / n;
				
				double iScale = (i % n + 0.) / n;
				double jScale = (j % n + 0.) / n;
				
				int[] w = get(new Pair(subI, subJ));
				int[] x = get(new Pair(subI + 1, subJ));
				int[] y = get(new Pair(subI, subJ + 1));
				int[] z = get(new Pair(subI + 1, subJ + 1));
				
				int[] newColor = new int[3];
				for (int k = 0; k < 3; k++) {
					double val1 = iScale * x[k] + (1 - iScale) * w[k];
					double val2 = iScale * z[k] + (1 - iScale) * y[k];
					//TODO: fix;
					newColor[k] = (int) (jScale * val2 + (1 - jScale) * val1);
				}
				
				newGrid.setLoc(new Pair (i, j), newColor);
			}
		}
		
		return newGrid;
	}
}
