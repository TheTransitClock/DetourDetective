// This class serves as an extension to DiscreteFrechetDistance.java. A point is
// just a pair of values.
//
// @author - Stephen Bahr (sbahr@bu.edu)

package sbahr;

import java.util.Arrays;

public class Point {
	/** The dimensions of this point */
	protected int[] dimensions;

	public Point(int[] dims) {
		this.dimensions = dims;
	}

	@Override
	public String toString() {
		return "Point [dimensions=" + Arrays.toString(dimensions) + "]";
	}
	
}
