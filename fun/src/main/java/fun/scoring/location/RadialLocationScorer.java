package fun.scoring.location;

import fun.grid.ValueGrid;

public class RadialLocationScorer extends LocationScorer {
	private final double centerX;
	private final double centerY;

	public RadialLocationScorer(ValueGrid grid) {
		super(grid);
		centerX = grid.getNX() / 2.;
		centerY = grid.getNY() / 2.;
	}

	@Override
	public int calculateValue(int x, int y) {
		return (int) ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY));
	}

}
