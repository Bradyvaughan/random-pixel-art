package fun.coloring;
import java.awt.color.ColorSpace;

import fun.grid.ValueGrid;

public class NullColorizer extends Colorizer {

	public NullColorizer(double intensity) {
		super(intensity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int[] getColorForValue(int value) {
		if (value == ValueGrid.BLOCKED_VALUE) {
			return new int[] {0,0,0};
		} else {
			return new int[] {255, 255, 255};
		}
	}

	@Override
	protected int getColorStyle() {
		return ColorSpace.TYPE_RGB;
	}

	@Override
	protected int[] getBlack() {
		// TODO Auto-generated method stub
		return null;
	}

}
