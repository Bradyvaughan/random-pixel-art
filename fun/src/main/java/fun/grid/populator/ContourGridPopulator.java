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
import fun.scoring.NeutralScorer;

public class ContourGridPopulator implements GridPopulator {
	
	protected BufferedImage img;
	
	public ContourGridPopulator(String path) {
		try {
		    img = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		
		String inputFileLoc = "./src/main/resources/input-pics/mushroom.png";
		String outputFileLoc = "./src/main/resources/output-pics/mushroom.png";
		
		ContourGridPopulator pop = new ContourGridPopulator(inputFileLoc);
		int h = pop.img.getHeight();
		int w = pop.img.getWidth();
		ValueGrid baseGrid = new ValueGrid(w,h);
		pop.populateGrid(baseGrid);
		Colorizer colorizer = new NullColorizer(1);
		ValueGrid scoredGrid = (new NeutralScorer()).scoreGrid(baseGrid);
		BufferedImage outputImg = colorizer.produceImage(baseGrid, baseGrid, scoredGrid, 1);
		
		try {
			ImageIO.write(outputImg, "PNG", new File(outputFileLoc));
		} catch (IOException e) {
			System.out.print("Couldn't write file because all fucked up");
			e.printStackTrace();
		}
	}

	@Override
	public void populateGrid(ValueGrid grid) {
	    int w = img.getWidth();
	    int h = img.getHeight();
	    int band = 1;
	    int diff = 100;
	    
	    
	    int ownGray = 0;
	    int otherGray = 0;
	    for (int i = 0; i < w; i++) {
	    	for (int j = 0; j < h; j++) {
	    		ownGray = grayLevel(new Color(img.getRGB(i, j)));
	    		
	    		for (int k = -band; k < band + 1; k++) {
	    			if (i + k < 0 || i + k >= w) { break; }
	    			for (int l = -band; l < band + 1; l++) {
	    				if (j + l < 0 || j + l >= h) { break; }
		    			otherGray = grayLevel(new Color(img.getRGB(i + k, j + l)));
		    			if (Math.abs(ownGray - otherGray) > diff) {
		    				grid.setLoc(new Pair(i,j), ValueGrid.BLOCKED_VALUE);
		    				break;
		    			}
	    			}
	    		}
	    	}
	    }
	}
	
	private int grayLevel(Color col) {
		int gray = 0;
		gray += col.getRed();
		gray += col.getGreen();
		gray += col.getBlue();
		
		return gray;
	}
	
	public int getWidth() {
		return img.getWidth();
	}
	
	public int getHeight() {
		return img.getHeight();
	}
 }
