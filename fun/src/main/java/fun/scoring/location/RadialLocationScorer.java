package fun.scoring.location;

import fun.grid.ValueGrid;

public class RadialLocationScorer extends LocationScorer {
	private double centerX;
	private double centerY;

	@Override
	public ValueGrid produceLocationScores(ValueGrid baseGrid) {
		centerX = baseGrid.getNX() / 2.;
		centerY = baseGrid.getNY() / 2.;
		return super.produceLocationScores(baseGrid);
	}

	@Override
	public int calculateValue(int x, int y) {
		return (int) ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
	}

}
