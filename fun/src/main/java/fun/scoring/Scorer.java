package fun.scoring;

import fun.grid.Pair;
import fun.grid.ValueGrid;

public abstract class Scorer {
	
	protected ValueGrid baseGrid;
	
	public Scorer(ValueGrid baseGrid) {
		this.baseGrid = baseGrid;
	}
	
	public ValueGrid scoreGrid() {
		int nx = baseGrid.getNX();
		int ny = baseGrid.getNY();
		ValueGrid returnGrid = new ValueGrid(nx, ny);
		
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				Pair loc = new Pair(i, j);
				returnGrid.setLoc(loc, valueForPixel(loc));
			}
		}
		return returnGrid;
	}
	
	public abstract int valueForPixel(Pair loc);

}
