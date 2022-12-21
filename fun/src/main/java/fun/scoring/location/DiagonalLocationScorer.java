package fun.scoring.location;

import fun.grid.ValueGrid;

public class DiagonalLocationScorer extends LocationScorer {
	
	private int intensity;
	private int offset;
	private double ratio;
	
	public DiagonalLocationScorer(ValueGrid grid) {
		super(grid);
		this.intensity = (int) (Math.random() * 10);
		this.offset = (int) (Math.random() * 1000);
		this.ratio = .5 + Math.random();
	}
	
	public DiagonalLocationScorer(int intensity, int offset, double ratio, ValueGrid grid) {
		super(grid);
		this.intensity = intensity;
		this.offset = offset;
		this.ratio = ratio;
	}

	@Override
	public int calculateValue(int x, int y) {
		return (int) (intensity * (offset + ratio * x + y));
	}

}
