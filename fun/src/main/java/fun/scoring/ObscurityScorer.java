package fun.scoring;

import fun.grid.Pair;

public class ObscurityScorer extends Scorer {
	
	private static final int RANGE = 5;
	
	@Override
	public int valueForPixel(Pair loc) {
		int totScore = 0;
		
		
		for (int xinc = -RANGE; xinc <= RANGE; xinc++) {
			for (int yinc = -RANGE; yinc <= RANGE; yinc++) {
				Pair newPair = new Pair(xinc, yinc);
				if (!baseGrid.isBlocked(loc.add(newPair))) {
					totScore += 255;
				}
			}
		}

		return totScore / ((2 * RANGE + 1) * (2 * RANGE + 1));
	}

}
