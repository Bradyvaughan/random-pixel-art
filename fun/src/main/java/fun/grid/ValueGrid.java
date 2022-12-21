package fun.grid;

public class ValueGrid {
	private final int nx;
	private final int ny;
	private int[] data;
	
	public static int BLOCKED_VALUE = -1;
	
	public ValueGrid(int nx, int ny) {
		this.nx = nx;
		this.ny = ny;
		this.data = new int[nx * ny + 1];
		
		for (int i = 0; i < nx * ny; i++) {
			data[i] = 0;
		}
	}
	
	public int get(Pair pair) {
		if (!isValid(pair)) {
			return 0;
		}
		return data[getIndex(pair)];
	}
	
	public boolean isValid(Pair pair) {
		if (pair.x < 0 || pair.x > nx || pair.y < 0 || pair.y >= ny)  {
			return false;
		}
		
		return true;
	}
	
	public void setLoc(Pair loc, int value) {
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
	
	public boolean isBlocked(Pair pair) {
		return isBlocked(getIndex(pair));
	}
	
	public boolean isBlocked(int i) {
		if (i < 0 || i >= data.length) { return true; }
		return data[i] == BLOCKED_VALUE;
	}
}
