package fun.fun;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import fun.coloring.Colorizer;
import fun.coloring.hsv.HSVColorizer;
import fun.coloring.rgb.GBColorizer;
import fun.coloring.rgb.RBColorizer;
import fun.grid.ColorGrid;
import fun.grid.Pair;
import fun.grid.ValueGrid;
import fun.grid.populator.GridPopulator;
import fun.grid.populator.ShapeGridPopulator;
import fun.scoring.ObscurityScorer;
import fun.scoring.Scorer;
import fun.scoring.UnblockedScorer;
import fun.scoring.VisibilityScorer;
import fun.scoring.location.DiagonalLocationScorer;
import fun.scoring.location.LocationScorer;
import fun.scoring.location.RadialLocationScorer;

public class AbstractArtGenerator {
	
//	private static int GRAY_SCALE_INTENSITY = 128;

	
	public static void main(String[] args) {
		Long init = System.currentTimeMillis();
//		produceFractalImage(2, 7, 10000, "./experiments/image-" + System.currentTimeMillis() + ".png");
		ValueGrid baseGrid = new ValueGrid(100, 100);
		GridPopulator pop = new ShapeGridPopulator();
		pop.populateGrid(baseGrid);
//		for (int i = 0; i < 20; i++) {
//			if (args.length > 0) {
//				giffer(baseGrid, "./experiments/image-" + System.currentTimeMillis() + ".png");
//			} else {
//				produceLOSImage(200, 200, 2, "./experiments/image-" + System.currentTimeMillis() + ".png");
//			}
//		}
		
		giffer(baseGrid, "./experiments/image-" + System.currentTimeMillis() + ".png");
		System.out.println("all done!");
		System.out.println(String.format("It took %s milliseconds to complete this run", System.currentTimeMillis() - init));
	}
	
	private static void giffer(ValueGrid baseGrid, String path) {
//		ValueGrid locationScoreGrid = (new DiagonalLocationScorer(baseGrid)).produceLocationScores();
		ValueGrid locationScoreGrid = (new RadialLocationScorer(baseGrid)).produceLocationScores();
		ValueGrid valueScoreGrid = (new UnblockedScorer(baseGrid)).scoreGrid();
		
		try {
			ImageOutputStream output = new FileImageOutputStream(new File(String.format("./experiments/gif-%s.gif", System.currentTimeMillis())));
			GifManufacturer giffer = new GifManufacturer(output, 1, 100, true);
			for (int i = 0; i < 40; i++) {
				double intense = 1 + (i / 10.);
				Colorizer colorizer = new HSVColorizer(intense, 200000, 1000000, 5000);
//				Colorizer colorizer = new RBColorizer(intense);
				BufferedImage bi = colorizer.produceImage(baseGrid, locationScoreGrid, valueScoreGrid, 8);
				giffer.writeToSequence(bi);
			}
			giffer.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private static void special(ValueGrid baseGrid, double intensity, String path) {
		LocationScorer locScorer = new RadialLocationScorer(baseGrid);
//		LocationScorer locScorer = new DiagonalLocationScorer(baseGrid);
		ValueGrid locationScoreGrid = locScorer.produceLocationScores();
		
//		Scorer valueScorer = new VisibilityScorer(baseGrid);
//		Scorer valueScorer = new ObscurityScorer(baseGrid);
		Scorer valueScorer = new UnblockedScorer(baseGrid);
		ValueGrid valueScoreGrid = valueScorer.scoreGrid();
		
//		Colorizer colorizer = new GBColorizer(intensity);
		Colorizer colorizer = new HSVColorizer(intensity, 500000, 1f, 1000);
		
		BufferedImage bi = colorizer.produceImage(baseGrid, locationScoreGrid, valueScoreGrid, 8);
		writeImage(bi, path);
	}
	
	private static void produceLOSImage(int nx, int ny, double intensity, String path) {
		ValueGrid baseGrid = new ValueGrid(nx, ny);
		
		GridPopulator pop = new ShapeGridPopulator();
		pop.populateGrid(baseGrid);
		
//		LocationScorer locScorer = new RadialLocationScorer(baseGrid);
		LocationScorer locScorer = new DiagonalLocationScorer(baseGrid);
		ValueGrid locationScoreGrid = locScorer.produceLocationScores();
		
//		Scorer valueScorer = new VisibilityScorer(baseGrid);
//		Scorer valueScorer = new ObscurityScorer(baseGrid);
		Scorer valueScorer = new UnblockedScorer(baseGrid);
		ValueGrid valueScoreGrid = valueScorer.scoreGrid();
		
		Colorizer colorizer = new GBColorizer(intensity);
//		Colorizer colorizer = new HSVColorizer(intensity, 500000, 1f, 1000);
		
		BufferedImage bi = colorizer.produceImage(baseGrid, locationScoreGrid, valueScoreGrid, 10);
		writeImage(bi, path);
	}
	
	private static void produceFractalImage(int factor, int times, int intensity, String path) {
		ColorGrid colorGrid = new ColorGrid(2, 2);
		colorGrid.setLoc(new Pair(0,0), new int[] {0, 0, 0});
		colorGrid.setLoc(new Pair(0, 1), new int [] {0, 255 * intensity, 0});
		colorGrid.setLoc(new Pair(1, 0), new int [] {0, 0, 255 * intensity});
		colorGrid.setLoc(new Pair(1, 1), new int [] {0, 255 * intensity, 255 * intensity});
		
		for (int i = 0; i < times; i++) {
			colorGrid = colorGrid.inflate(factor);
		}
		
		writeGrid(colorGrid, path);
		
	}
	
	private static void writeGrid(ColorGrid grid, String fileLoc) {
		grid = grid.inflate(20);
		BufferedImage bi = new BufferedImage(grid.getNX(), grid.getNY(), ColorSpace.TYPE_RGB);
		for (int i = 0; i < grid.getNX(); i++) {
			for (int j = 0; j < grid.getNY(); j++) {
				bi.getRaster().setPixel(i, j, grid.get(new Pair(i, j)));
			}
		}
		
		try {
			ImageIO.write(bi, "PNG", new File(fileLoc));
		} catch (IOException e) {
			System.out.print("Couldn't write file because all fucked up");
			e.printStackTrace();
		}
	}
	
	private static void writeImage(BufferedImage bi, String fileLoc) {
		try {
			ImageIO.write(bi, "PNG", new File(fileLoc));
		} catch (IOException e) {
			System.out.print("Couldn't write file because all fucked up");
			e.printStackTrace();
		}
	}
}
