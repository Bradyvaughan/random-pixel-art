package fun.coloring.rgb;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

import fun.grid.ColorGrid;
import fun.grid.Pair;
import fun.grid.ValueGrid;

public class RadicalColorizer {
	
	public BufferedImage produceImage(ValueGrid redGrid, ValueGrid greenGrid, ValueGrid blueGrid, int inflationFactor) {
		int nx = redGrid.getNX();
		int ny = redGrid.getNY();
		
		ColorGrid colors = new ColorGrid(nx, ny);
		
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				
				Pair loc = new Pair(i,j);
				
				int red = redGrid.get(loc);
				int green = greenGrid.get(loc);
				int blue = blueGrid.get(loc);
				colors.setLoc(loc, new int[] {red, blue, green});
			}
		}
		
		ColorGrid imageGrid = colors.inflate(inflationFactor);
		
		BufferedImage bi = new BufferedImage(imageGrid.getNX(), imageGrid.getNY(), ColorSpace.TYPE_RGB);
		for (int i = 0; i < imageGrid.getNX(); i++) {
			for (int j = 0; j < imageGrid.getNY(); j++) {
				
				
				bi.getRaster().setPixel(i, j, imageGrid.get(new Pair(i, j)));
			}
		}
		
		return bi;
	}

}
