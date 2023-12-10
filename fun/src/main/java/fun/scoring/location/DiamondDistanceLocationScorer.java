package fun.scoring.location;

import java.util.ArrayList;
import java.util.List;

import fun.grid.Pair;
import fun.grid.ValueGrid;

public class DiamondDistanceLocationScorer extends LocationScorer {
	
	protected List<Pair> distancePoints;
	protected double exponent;
	
	public DiamondDistanceLocationScorer(int n, double exponent, ValueGrid grid) {
		distancePoints = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			distancePoints.add(generatePair(grid.getNX(), grid.getNY()));
		}
		this.exponent = exponent;
	}
	
	protected Pair generatePair(int nx, int ny) {
		int x = (int) (Math.random() * nx);
		int y = (int) (Math.random() * ny);
		return new Pair(x, y);
	}

	public DiamondDistanceLocationScorer(List<Pair> distancePoints, double exponent) {
		this.distancePoints = distancePoints;
		this.exponent = exponent;
	}

	@Override
	public int calculateValue(int x, int y) {
		int val = Integer.MAX_VALUE;
		
		for (Pair point : this.distancePoints) {
			int pointScore = pointScore(point, x, y);
			if (pointScore < val) {
				val = pointScore;
			}
		}
		
		return val;
	}
	
	protected int pointScore(Pair point, int x, int y) {
		int dist = Math.abs(point.x - x) + Math.abs(point.y - y);
		int ret = (int) (Math.pow(dist, exponent));
		return ret;
	}

}
