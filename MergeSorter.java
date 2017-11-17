import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.lang.IllegalArgumentException; 

/**
 *  
 * @author Stephanie Engelhardt
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter{
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/** 
	 * Constructor accepts an input array of points. 
	 * in the array. 
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) {
		super(pts);
		super.algorithm="mergesort";
		super.outputFileName="merge.txt";
	}
	
	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public MergeSorter(String inputFileName) throws InputMismatchException, FileNotFoundException {
		super(inputFileName);
		super.algorithm="mergesort";
		super.outputFileName="merge.txt";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 *
	 */
	@Override 
	public void sort(int order){
		long start=System.nanoTime();
		this.setComparator(order);
		if(order==1)
			super.sortByAngle=false;
		else
			super.sortByAngle=true;
		mergeSortRec(points);
		long end=System.nanoTime();
		super.sortingTime=(end-start);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */
	private void mergeSortRec(Point[] pts){
		int n=pts.length;
		if(n>1){
			int mid=pts.length/2;
			Point[] left= Arrays.copyOfRange(pts, 0, mid);
			Point[] right= Arrays.copyOfRange(pts, mid, pts.length);
			mergeSortRec(left);
			mergeSortRec(right);
			Merge(pts, left, right);
		}
	}

	/**
	 * Helper method to merge two arrays
	 * @param pts
	 * @param point
	 * @return merged arrays
	 */
	private Point[] Merge(Point[] result, Point[] pts, Point[] point){
		int posLeft=0;
		int posRight=0;
		int posMerge=0;
		if(sortByAngle){
			this.setComparator(2);
		}
		else{
			this.setComparator(1);
		}
		while(posLeft<pts.length && posRight<point.length){
			if(pointComparator.compare(pts[posLeft], point[posRight])<0){
				result[posMerge]=pts[posLeft];
				posLeft++;	
			}
			else{
				result[posMerge]=point[posRight];
				posRight++;
			}
			posMerge++;
		}
		while(posLeft<pts.length){
			result[posMerge]=pts[posLeft];
			posLeft++;
			posMerge++;
		}
		while(posRight<point.length){
			result[posMerge]=point[posRight];
			posRight++;
			posMerge++;
		}
		return result;
		
	}

}
