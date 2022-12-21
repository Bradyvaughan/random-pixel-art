package fun.grid;

public class Pair {
	public int x;
	public int y;
	
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Pair translateX(int newX) {
		return new Pair(x + newX, y);
	}
	
	public Pair TranslateY(int newY) {
		return new Pair(x, y + newY);
	}
	
	public Pair add(Pair pair) {
		return new Pair(x + pair.x, y + pair.y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
}
