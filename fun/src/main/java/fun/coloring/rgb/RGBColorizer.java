package fun.coloring.rgb;

import java.awt.color.ColorSpace;

import fun.coloring.Colorizer;

public abstract class RGBColorizer extends Colorizer {

	public RGBColorizer(double intensity) {
		super(intensity);
	}

	@Override
	public int getColorStyle() {
		return ColorSpace.TYPE_RGB;
	}
	
	@Override
	public abstract int[] getColorForValue(int value);
}
