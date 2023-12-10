package fun.coloring.rgb;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.util.List;

import fun.grid.ColorGrid;
import fun.grid.Pair;
import fun.grid.ValueGrid;

public class DestructiveColorizer {
	
	public BufferedImage produceImage(List<ValueGrid> grids, int inflationFactor) {
		int nx = grids.get(0).getNX();
		int ny = grids.get(0).getNY();
		
		ColorGrid colors = new ColorGrid(nx, ny);
		
		for (int i = 0; i < nx; i++) {
			for (int j = 0; j < ny; j++) {
				
				Pair loc = new Pair(i,j);
				
				int tot = 0;
				for (ValueGrid grid : grids) {
					tot += grid.get(loc);
				}
				colors.setLoc(loc, new int[] {tot, 0, 0});
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
