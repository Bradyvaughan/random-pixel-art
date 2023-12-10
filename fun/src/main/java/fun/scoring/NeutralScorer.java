package fun.scoring;

import fun.grid.Pair;

public class NeutralScorer extends Scorer {

	@Override
	public int valueForPixel(Pair loc) {
		return 1;
	}

}
