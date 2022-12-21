package fun.coloring;

import java.awt.image.BufferedImage;

import fun.grid.ColorGrid;
import fun.grid.Pair;
import fun.grid.ValueGrid;

public abstract class Colorizer {
	
	private final double intensity;
	
	public Colorizer(double intensity) {
		this.intensity = intensity;
	}
	
	public BufferedImage produceImage(ValueGrid baseGrid, ValueGrid locationGrid, ValueGrid scoreGrid, int inflationFactor) {
		int nx = baseGrid.getNX();
		int ny = baseGrid.getNY();
		
		ColorGrid colors = new ColorGrid(nx, ny);
		
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				
				Pair loc = new Pair(i,j);
				colors.setLoc(loc, intensifyColor(getColorForValue(locationGrid.get(loc) * scoreGrid.get(loc))));
			}
		}
		
		ColorGrid imageGrid = colors.inflate(inflationFactor);
		
		BufferedImage bi = new BufferedImage(imageGrid.getNX(), imageGrid.getNY(), getColorStyle());
		for (int i = 0; i < imageGrid.getNX(); i++) {
			for (int j = 0; j < imageGrid.getNY(); j++) {
				bi.getRaster().setPixel(i, j, imageGrid.get(new Pair(i, j)));
			}
		}
		
		return bi;
	}
	
	public int[] intensifyColor(int[] origColor) {
		int[] newColor = new int[3];
		
		newColor[0] = (int) (intensity * origColor[0]);
		newColor[1] = (int) (intensity * origColor[1]);
		newColor[2] = (int) (intensity * origColor[2]);
		
		return newColor;
	}
	
	public abstract int[] getColorForValue(int value);
	
	protected abstract int getColorStyle();
	
	protected abstract int[] getBlack();
	

}
