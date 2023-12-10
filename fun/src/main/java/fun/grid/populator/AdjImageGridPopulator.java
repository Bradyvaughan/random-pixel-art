package fun.grid.populator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fun.coloring.Colorizer;
import fun.coloring.NullColorizer;
import fun.grid.Pair;
import fun.grid.ValueGrid;

public class AdjImageGridPopulator implements GridPopulator {
	
	protected BufferedImage img;
	protected double darkProportion;
	
	public AdjImageGridPopulator(String path, double darkProportion) {
		this.darkProportion = darkProportion;
		try {
		    img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getHeight() {
		return img.getHeight();
	}
	
	public int getWidth() {
		return img.getWidth();
	}

	@Override
	public void populateGrid(ValueGrid grid) {
		// TODO Auto-generated method stub
	    int w = img.getWidth();
	    int h = img.getHeight();
	    
	    int avg = 0;
	    double tot = 0;
	    for (int i = 0; i < w; i++) {
	    	for (int j = 0; j < h; j++) {
	    		Color col = new Color(img.getRGB(i, j));
	    		avg += col.getRed();
	    		avg += col.getGreen();
	    		avg += col.getBlue();
	    		tot +=  (1 / darkProportion);
	    	}
	    }
	    
	    double thresh = avg / tot;
	    
	    
	    
	    for (int i = 0; i < w; i++) {
	    	for (int j = 0; j < h; j++) {
	    		Color col = new Color(img.getRGB(i, j));
	    		int r = col.getRed();
	    		int g = col.getGreen();
	    		int b = col.getBlue();
	    		
	    		if (r + g + b < thresh) {
	    			grid.setLoc(new Pair(i,j), ValueGrid.BLOCKED_VALUE);
	    		}
	    	}
	    }
	}
}
