package fun.scoring.location;

import fun.grid.Pair;
import fun.grid.ValueGrid;

public abstract class LocationScorer {
	
	public ValueGrid produceLocationScores(ValueGrid baseGrid) {
		int nx = baseGrid.getNX();
		int ny = baseGrid.getNY();
		ValueGrid grid = new ValueGrid(nx, ny);
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				grid.setLoc(new Pair(i,j), calculateValue(i, j));
			}
		}
		return grid;
	}
	
	public abstract int calculateValue(int x, int y);
}
