import java.util.ArrayList;
import java.util.List;

class Point {
	float x,y;
	
	Point(float a, float b) {
		x = a;
		y = b;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point toCompare = (Point) obj;
			int x1 = Math.round(toCompare.x);
			int y1 = Math.round(toCompare.y);
			int x2 = Math.round(x);
			int y2 = Math.round(y);
			
			if (x1 == x2 && y1 == y2) {
				return true;
			}
		}
		
		return false;
	}
}

public class Main {

	public static void main(String[] args) {
		
		List<Point> points = new ArrayList<Point>();

		points.add(new Point(1,2));
		points.add(new Point(1.1f,2.2f));
		
		System.out.println(points.contains(new Point(1.4f,2.2f)));
		
	}

}
