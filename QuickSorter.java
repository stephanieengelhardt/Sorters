import java.io.FileNotFoundException;
import java.lang.NumberFormatException;
import java.util.InputMismatchException;
import java.lang.IllegalArgumentException; 


/**
 *  
 * @author Stephanie Engelhardt
 *
 */

/**
 * 
 * This class implements the version of the quicksort algorithm presented in the lecture.   
 *
 */

public class QuickSorter extends AbstractSorter{
		
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/** 
	 * Constructor accepts an input array of points. 
	 *   
	 * @param pts   input array of integers
	 */
	public QuickSorter(Point[] pts){
		super(pts);
		super.algorithm="quicksort";
		super.outputFileName="quick.txt";
		
	}
		

	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public QuickSorter(String inputFileName) throws InputMismatchException, FileNotFoundException {
		super(inputFileName);
		super.algorithm="quicksort";
		super.outputFileName="quick.txt";
	}


	/**
	 * Carry out quicksort on the array points[] of the AbstractSorter class.  
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
		quickSortRec(0,points.length-1);
		long end=System.nanoTime();
		super.sortingTime=(end-start);
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last. 
	 * 
	 * @param first  starting index of the subarray
	 * @param last   ending index of the subarray
	 */
	private void quickSortRec(int first, int last){
		if(first>=last)
			return;
		int p=partition(first,last);
		quickSortRec(first,p-1);
		quickSortRec(p+1,last);
		
	}
	
	
	/**
	 * Operates on the subarray of points[] with indices between first and last.
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	private int partition(int first, int last){
		Point pivot=points[last];
		int i=first-1;
		if(sortByAngle)
			this.setComparator(2);
		else
			this.setComparator(1);
		for(int j=first; j<last;j++){
			if(pointComparator.compare(points[j], pivot)<0){
				i++;
				swap(i,j);
			}
		}
		swap(i+1,last);
		return i+1;
	}	

}
