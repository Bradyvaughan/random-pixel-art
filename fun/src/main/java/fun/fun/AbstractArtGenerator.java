package fun.fun;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import fun.coloring.Colorizer;
import fun.coloring.hsv.HSVColorizer;
import fun.coloring.rgb.GBColorizer;
import fun.coloring.rgb.RBColorizer;
import fun.coloring.rgb.RadicalColorizer;
import fun.coloring.rgb.DestructiveColorizer;
import fun.grid.ColorGrid;
import fun.grid.Pair;
import fun.grid.ValueGrid;
import fun.grid.populator.AdjImageGridPopulator;
import fun.grid.populator.ContourGridPopulator;
import fun.grid.populator.GridPopulator;
import fun.grid.populator.ShapeGridPopulator;
import fun.scoring.NeutralScorer;
import fun.scoring.Scorer;
import fun.scoring.UnblockedScorer;
import fun.scoring.location.DiagonalLocationScorer;
import fun.scoring.location.DiamondDistanceLocationScorer;
import fun.scoring.location.DistanceLocationScorer;
import fun.scoring.location.LocationScorer;
import fun.scoring.location.RadialLocationScorer;

public class AbstractArtGenerator {

	public static void main(String[] args) {
//		intensityGiffer();
//		imageDarkGiffer();
//		singleImageProduction();
//		exponentGiffer();
//		radicalImageProduction();
//		positionGiffer();
		destructiveImageProducer();
	}

	private static void intensityGiffer() {
		ContourGridPopulator pop = new ContourGridPopulator("./src/main/resources/input-pics/mushroom.png");
		ValueGrid baseGrid = new ValueGrid(pop.getWidth(), pop.getWidth());
		pop.populateGrid(baseGrid);

		ValueGrid locationScoreGrid = (new DiagonalLocationScorer()).produceLocationScores(baseGrid);
		// ValueGrid locationScoreGrid = (new
		// RadialLocationScorer(baseGrid)).produceLocationScores();
		ValueGrid valueScoreGrid = (new UnblockedScorer()).scoreGrid(baseGrid);
		// ValueGrid valueScoreGrid = (new ObscurityScorer(baseGrid)).scoreGrid();

		try {

			ImageOutputStream output = new FileImageOutputStream(
					new File(String.format("./experiments/gif-%s.gif", System.currentTimeMillis())));
			GifManufacturer giffer = new GifManufacturer(output, 1, 100, true);
			for (int i = 0; i < 20; i++) {
				double intense = 1 + (i / 50.);
				Colorizer colorizer = new HSVColorizer(intense, 200000, 1000000, 5000);
				// Colorizer colorizer = new GBColorizer(intense);
				BufferedImage bi = colorizer.produceImage(baseGrid, locationScoreGrid, valueScoreGrid, 2);
				giffer.writeToSequence(bi);
			}

			for (int i = 20; i > 0; i--) {
				double intense = 1 + (i / 50.);
				Colorizer colorizer = new HSVColorizer(intense, 200000, 1000000, 5000);
				// Colorizer colorizer = new GBColorizer(intense);
				BufferedImage bi = colorizer.produceImage(baseGrid, locationScoreGrid, valueScoreGrid, 2);
				giffer.writeToSequence(bi);
			}
			giffer.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void imageDarkGiffer() {

		try {
			ImageOutputStream output = new FileImageOutputStream(
					new File(String.format("./experiments/gif-%s.gif", System.currentTimeMillis())));
			GifManufacturer giffer = new GifManufacturer(output, 1, 100, true);
			LocationScorer locationScorer = new DiagonalLocationScorer();
			Scorer valueScorer = new UnblockedScorer();
			double intense = 1;

			for (int i = 0; i < 40; i++) {
				double darkProp = .2 + (i * (.7 / 40.));
				AdjImageGridPopulator pop = new AdjImageGridPopulator("./src/main/resources/input-pics/lady.jfif",
						darkProp);
				ValueGrid baseGrid = new ValueGrid(pop.getWidth(), pop.getHeight());
				pop.populateGrid(baseGrid);
				ValueGrid locationScoreGrid = locationScorer.produceLocationScores(baseGrid);
				ValueGrid valueScoreGrid = valueScorer.scoreGrid(baseGrid);

				Colorizer colorizer = new HSVColorizer(intense, 20000, 100000, 500);
//				Colorizer colorizer = new RBColorizer(intense);
				BufferedImage bi = colorizer.produceImage(baseGrid, locationScoreGrid, valueScoreGrid, 4);
				giffer.writeToSequence(bi);
			}

			giffer.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void exponentGiffer() {
		try {
			ImageOutputStream output = new FileImageOutputStream(
					new File(String.format("./experiments/gif-%s.gif", System.currentTimeMillis())));
			GifManufacturer giffer = new GifManufacturer(output, 1, 100, true);
			Scorer valueScorer = new UnblockedScorer();
			double intense = 3;
			Colorizer colorizer = new GBColorizer(intense);
			List<Pair> places = new ArrayList<>();
			for (int i = 0; i < 30; i++) {
				places.add(new Pair((int) (Math.random() * 725), (int) (Math.random() * 450)));
			}

			List<BufferedImage> bis = new ArrayList<>();
			for (int i = 0; i < 20; i++) {
				ValueGrid baseGrid = new ValueGrid(725, 450);
				LocationScorer locationScorer = new DistanceLocationScorer(places, 1 + (i / 20.));
				ValueGrid locationScoreGrid = locationScorer.produceLocationScores(baseGrid);
				ValueGrid valueScoreGrid = valueScorer.scoreGrid(baseGrid);

//				Colorizer colorizer = new HSVColorizer(intense, 20000, 100000, 500);
				
				BufferedImage bi = colorizer.produceImage(baseGrid, locationScoreGrid, valueScoreGrid, 1);
				bis.add(bi);
				giffer.writeToSequence(bi);
			}
			
			for (int i = 20; i > 0; i--) {
				giffer.writeToSequence(bis.get(i-1));
			}

			giffer.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void positionGiffer() {
		try {
			ImageOutputStream output = new FileImageOutputStream(
					new File(String.format("./experiments/gif-%s.gif", System.currentTimeMillis())));
			GifManufacturer giffer = new GifManufacturer(output, 1, 100, true);
			RadicalColorizer colorizer = new RadicalColorizer();
			
			int k = 10;
			int nx = 1000;
			int ny = 1000;
			int offset = 50;
			
			int centX = nx / 2;
			int centY = ny / 2;
			
			int initial = centX - offset;
			int increment = (2 * offset) / k;

			LocationScorer greenScorer = new DistanceLocationScorer(List.of(new Pair(centX, centY)), 1.6);
			for (int i = 0; i < k; i++) {
				ValueGrid baseGrid = new ValueGrid(nx, ny);
				LocationScorer blueScorer = new DistanceLocationScorer(List.of(new Pair(initial + i * increment, initial), new Pair(centX + offset - i * increment, centY + offset)), 1.6);
				
				
				ValueGrid redGrid = (new NeutralScorer()).scoreGrid(baseGrid);
				ValueGrid greenGrid = greenScorer.produceLocationScores(baseGrid);
				ValueGrid blueGrid = blueScorer.produceLocationScores(baseGrid);
				
				BufferedImage bi = colorizer.produceImage(redGrid, greenGrid, blueGrid, 1);
				giffer.writeToSequence(bi);
			}
			
			for (int i = 0; i < k - 1; i++) {
				ValueGrid baseGrid = new ValueGrid(nx, ny);
				LocationScorer blueScorer = new DistanceLocationScorer(List.of(new Pair(initial + 2 * offset, initial + i * increment), new Pair(initial, centY + offset - i * increment)), 1.6);
				
				ValueGrid redGrid = (new NeutralScorer()).scoreGrid(baseGrid);
				ValueGrid greenGrid = greenScorer.produceLocationScores(baseGrid);
				ValueGrid blueGrid = blueScorer.produceLocationScores(baseGrid);
				
				BufferedImage bi = colorizer.produceImage(redGrid, greenGrid, blueGrid, 1);
				giffer.writeToSequence(bi);
			}
			

			giffer.close();
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void singleImageProduction() {
//		ContourGridPopulator pop = new ContourGridPopulator("./src/main/resources/input-pics/mushroom.png");
		double intensity = 10.0;
//		LocationScorer locScorer = new RadialLocationScorer();
//		LocationScorer locScorer = new DiagonalLocationScorer();
		Scorer valueScorer = new UnblockedScorer();
//		Colorizer colorizer = new HSVColorizer(intensity, 200000, 10000000, 500000);
		Colorizer colorizer = new GBColorizer(intensity);
		String path = String.format("./experiments/img-%s.png", System.currentTimeMillis());

		ValueGrid baseGrid = new ValueGrid(1450, 900);
		LocationScorer locScorer = new DistanceLocationScorer(3, 1.3, baseGrid);
//		ValueGrid baseGrid = new ValueGrid(pop.getWidth(), pop.getHeight());
//		pop.populateGrid(baseGrid);
		ValueGrid locationScoreGrid = locScorer.produceLocationScores(baseGrid);
		ValueGrid valueScoreGrid = valueScorer.scoreGrid(baseGrid);

		BufferedImage bi = colorizer.produceImage(baseGrid, locationScoreGrid, valueScoreGrid, 1);
		writeImage(bi, path);
	}
	
	private static void radicalImageProduction() {
		RadicalColorizer colorizer = new RadicalColorizer();
		String path = String.format("./experiments/img-%s.png", System.currentTimeMillis());

		ValueGrid baseGrid = new ValueGrid(1450, 900);
		LocationScorer redScorer = new DistanceLocationScorer(List.of(new Pair(725, 350), new Pair(725, 550)), 1.7);
		LocationScorer greenScorer = new DistanceLocationScorer(List.of(new Pair(525, 450), new Pair(925, 450)), 1.7);
		LocationScorer blueScorer = new DistanceLocationScorer(List.of(new Pair(725, 450)), 1.7);

//		LocationScorer redScorer = new DiamondDistanceLocationScorer(10, 1.7, baseGrid);
////		LocationScorer greenScorer = new DiamondDistanceLocationScorer(10, 1.4, baseGrid);
//		LocationScorer blueScorer = new DiamondDistanceLocationScorer(10, 1.7, baseGrid);
		
		ValueGrid redGrid = redScorer.produceLocationScores(baseGrid);
		ValueGrid greenGrid = (new NeutralScorer()).scoreGrid(baseGrid);
		ValueGrid blueGrid = blueScorer.produceLocationScores(baseGrid);

		BufferedImage bi = colorizer.produceImage(redGrid, blueGrid, greenGrid, 1);
		writeImage(bi, path);
	}
	
	private static void destructiveImageProducer() {
		DestructiveColorizer colorizer = new DestructiveColorizer();
		String path = String.format("./experiments/img-%s.png", System.currentTimeMillis());

		ValueGrid baseGrid = new ValueGrid(1500, 900);
		List<ValueGrid> grids = List.of(
				(new DistanceLocationScorer(List.of(new Pair(750, 300)), 1.9)).produceLocationScores(baseGrid),
				(new DistanceLocationScorer(List.of(new Pair(750, 600)), 1.9)).produceLocationScores(baseGrid)
//				(new DistanceLocationScorer(List.of(new Pair(1000, 300)), 1.3)).produceLocationScores(baseGrid),
//				(new DistanceLocationScorer(List.of(new Pair(1000, 600)), 1.3)).produceLocationScores(baseGrid)
				);

		BufferedImage bi = colorizer.produceImage(grids, 1);
		writeImage(bi, path);
	}

	private static void produceFractalImage(int factor, int times, int intensity, String path) {
		ColorGrid colorGrid = new ColorGrid(2, 2);
		colorGrid.setLoc(new Pair(0, 0), new int[] { 0, 0, 0 });
		colorGrid.setLoc(new Pair(0, 1), new int[] { 0, 255 * intensity, 0 });
		colorGrid.setLoc(new Pair(1, 0), new int[] { 0, 255 * intensity, 0 });
		colorGrid.setLoc(new Pair(1, 1), new int[] { 0, 0, 0 });

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
