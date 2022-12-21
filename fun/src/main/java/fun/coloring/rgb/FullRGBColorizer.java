package fun.coloring.rgb;

public class FullRGBColorizer extends RGBColorizer {
	public FullRGBColorizer(double intensity) {
		super(intensity);
	}

	@Override
	public int[] getColorForValue(int value) {
		int phase = (value / 256) % 4;
		int remain = value % 256;
		int[] color = new int[3];
		switch(phase) {
		case 0:
			color[0] = 0;
			color[1] = 255;
			color[2] = remain;
			break;
		case 1:
			color[0] = 0;
			color[1] = 255 - remain;
			color[2] = 255;
			break;
		case 2:
			color[0] = 0;
			color[1] = remain;
			color[2] = 255;
			break;
		case 3:
			color[0] = 0;
			color[1] = 255;
			color[2] = 255 - remain;
			break;
		default:
			System.out.println("how did this happen?");
			break;
				
		}
		double scale = value / 255.;
		color[0] = (int) (color[0] * scale);
		color[1] = (int) (color[1] * scale);
		color[2] = (int) (color[2] * scale);
		
		return color;
	}

	@Override
	protected int[] getBlack() {
		return null;
	}
}
