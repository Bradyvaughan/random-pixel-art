package fun.scoring;

import fun.grid.Pair;

public class UnblockedScorer extends Scorer {
	
	private static final int DISTANCE = 30;

	private int getSupremumDistance(Pair loc) {
		for (int i = 1; i < DISTANCE; i++) {
			for (int j = -i; j < i; j++) {
				Pair pair1 = new Pair(j, i);
				Pair pair2 = new Pair(i, -j);
				Pair pair3 = new Pair(-j, i);
				Pair pair4 = new Pair(i, j);
				
				if (baseGrid.isBlocked(loc.add(pair1))) { return i; }
				if (baseGrid.isBlocked(loc.add(pair2))) { return i; }
				if (baseGrid.isBlocked(loc.add(pair3))) { return i; }
				if (baseGrid.isBlocked(loc.add(pair4))) { return i; }
			}
		}
		
		return DISTANCE;
	}

	@Override
	public int valueForPixel(Pair loc) {
		if (baseGrid.isBlocked(loc)) { return 0; }
		
		int dist = getSupremumDistance(loc);
		
		
		
		// TODO Auto-generated method stub
		return (255 * dist) / DISTANCE;
	}

}
