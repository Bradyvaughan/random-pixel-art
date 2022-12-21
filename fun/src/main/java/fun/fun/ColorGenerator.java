package fun.fun;

public class ColorGenerator {

	public static int[] white() {
		int[] whiteVec = new int[3];
		whiteVec[0] = 255;
		whiteVec[1] = 255;
		whiteVec[2] = 255;
		
		return whiteVec;
	}
	
	public static int[] black() {
		int[] blackVec = new int[3];
		blackVec[0] = 0;
		blackVec[1] = 0;
		blackVec[2] = 0;
		
		return blackVec;
	}
	
	public static int[] redScale(int red) {
		int[] redVec = new int[3];
		redVec[0] = red;
		redVec[1] = 0;
		redVec[2] = 0;
		
		return redVec;
	}
	
	public static int[] grayScale(int gray) {
		int [] grayVec = new int[3];
		grayVec[0] = gray;
		grayVec[1] = gray;
		grayVec[2] = gray;
		
		return grayVec;
	}
	
	public static boolean isWhite(int[] color) {
		return (color[0] == 255 && color[1] == 255 && color[2] == 255);
	}
}
