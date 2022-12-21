package fun.grid.populator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fun.grid.Pair;
import fun.grid.ValueGrid;

public class ShapeGridPopulator implements GridPopulator {
	
	private int numShapes;
	private int minSides = 3;
	private int maxAdditionalSides = 7;
	
	public ShapeGridPopulator() {
		numShapes = (int) (30 + Math.random() * 10);
	}
	
	public ShapeGridPopulator(int numShapes) {
		this.numShapes = numShapes;
	}
	
	public void populateGrid(ValueGrid grid) {
		for (int i = 0; i < numShapes; i++){
			addShapeToGrid(createShape(grid.getNX(), grid.getNY()), grid);
		}
	}
	
	private Shape createShape(int nx, int ny) {
		int centerX = (int) (Math.random() * nx);
		int centerY = (int) (Math.random() * ny);
		
		Shape shape = new Shape();
		int maxRad = (int) (nx / 10);
		int numSides = minSides + (int) (Math.random() * (maxAdditionalSides + 1));
		List<Pair> vertices = new ArrayList<>();
		
		int maxX = Integer.MIN_VALUE;
		int minX = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		
		double angleConstant = 2 * Math.PI / numSides;
		for (int i = 0; i < numSides; i++) {
			double rad = Math.random() * maxRad;
			double ang = (i + Math.random()) * angleConstant;
			
			int newX = (int) (centerX + rad * Math.cos(ang));
			if (newX > maxX) { maxX = newX; }
			if (newX < minX) { minX = newX; }
			
			int newY = (int) (centerY + rad * Math.sin(ang));
			if (newY > maxY) { maxY = newY; }
			if (newY < minY) { minY = newY; }
			
			vertices.add(new Pair(newX, newY));
		}
		
		for (int i = minX; i <= maxX; i++) {
			for (int j = minY; j <= maxY; j++) {
				Pair thisPair = new Pair(i, j);
				if (contains(vertices, thisPair)) {
					shape.addPair(thisPair);
				}
			}
		}
		
		
		return shape;
	}
	
	private boolean contains(List<Pair> vertices, Pair pair) {
	      int i;
	      int j;
	      boolean result = false;
	      for (i = 0, j = vertices.size() - 1; i < vertices.size(); j = i++) {
	        if ((vertices.get(i).y > pair.y) != (vertices.get(j).y > pair.y) &&
	            (pair.x < (vertices.get(j).x - vertices.get(i).x) * (pair.y - vertices.get(i).y) / (vertices.get(j).y-vertices.get(i).y) + vertices.get(i).x)) {
	          result = !result;
	         }
	      }
	      return result;
	}
	
	private void addShapeToGrid(Shape shape, ValueGrid grid) {
		for (Pair loc : shape.getLocations()) {
			if (grid.isValid(loc)) {
				grid.setLoc(loc, ValueGrid.BLOCKED_VALUE);
			}
		}
	}
	
	private class Shape {
		public Set<Pair> locations = new HashSet<>();
		
		public void addPair(Pair pair) {
			this.locations.add(pair);
		}
		
		public Set<Pair> getLocations() {
			return this.locations;
		}
	}

	
}
