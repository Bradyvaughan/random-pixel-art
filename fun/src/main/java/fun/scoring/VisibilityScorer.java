package fun.scoring;

import fun.grid.Pair;
import fun.grid.ValueGrid;

public class VisibilityScorer extends Scorer {
	
	private static int VIS_RANGE = 20;

	public VisibilityScorer(ValueGrid baseGrid) {
		super(baseGrid);
	}

	protected int visibilityScore(Pair pair1, Pair pair2) {
		if (!baseGrid.isValid(pair1) || !baseGrid.isValid(pair2)) { return 0; }
		if (baseGrid.isBlocked(pair1) || baseGrid.isBlocked(pair2)) {return 0; }
		
		int xdist = pair2.x - pair1.x;
		int ydist = pair2.y - pair1.y;
		
		int over = 0;
		int up = 0;
		
		while (baseGrid.isValid(new Pair(pair1.x + over, pair1.y + up)) && (pair1.x + over) != pair2.x && (pair1.y + up) != pair2.y) {
			if (baseGrid.isBlocked(new Pair(pair1.x + over, pair1.y + up))) { return 0; }
			if (shouldOver(xdist, ydist, over, up)) {
				over += 1 * Math.signum(xdist);
			} else {
				up += 1 * Math.signum(ydist);
			}
		}
		
		return 255;
	}
	
	protected boolean shouldOver(int xdist, int ydist, int over, int up) {
		double pureR = (ydist + 0.) / xdist;
		double appR = (up + 0.) / over;
		return (Math.abs(appR) > Math.abs(pureR));
	}
	
	@Override
	public int valueForPixel(Pair loc) {
		int totScore = 0;
		for (int xinc = -VIS_RANGE; xinc <= VIS_RANGE; xinc++) {
			for (int yinc = -VIS_RANGE; yinc <= VIS_RANGE; yinc++) {
				Pair newPair = new Pair(xinc, yinc);
				totScore += visibilityScore(loc, loc.add(newPair));
			}
		}

		return totScore / ((2 * VIS_RANGE + 1) * (2 * VIS_RANGE + 1));
	}
	
}
