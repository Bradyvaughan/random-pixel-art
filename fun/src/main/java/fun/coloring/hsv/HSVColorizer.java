package fun.coloring.hsv;

import java.awt.Color;
import java.awt.color.ColorSpace;

import fun.coloring.Colorizer;

public class HSVColorizer extends Colorizer {
	
	private int huePeriod;
	private float saturationPeriod;
	private int valuePeriod;
	
	
	public HSVColorizer(double intensity, int huePeriod, float saturationPeriod, int valuePeriod) {
		super(intensity);
		this.huePeriod = huePeriod;
		this.saturationPeriod = saturationPeriod;
		this.valuePeriod = valuePeriod;
	}

	@Override
	public int[] getColorForValue(int val) {
		float hue = ((val % huePeriod)) / ((float) huePeriod);
//		float sat = 1f - ((val % saturationPeriod)) / ((float) saturationPeriod);
		float sat = 1;
		float value = ((val % valuePeriod)) / ((float) valuePeriod);
		return rgbArrayFromHSV(hue, sat, value);
	}
	
	private int[] rgbArrayFromHSV(float hue, float sat, float value) {
		Color col = new Color(Color.HSBtoRGB(hue, sat, value));
		return new int[] {col.getRed(), col.getGreen(), col.getBlue()};
	}

	@Override
	public int getColorStyle() {
		return ColorSpace.TYPE_RGB;
	}

	@Override
	protected int[] getBlack() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
