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
 * This class implements insertion sort.   
 *
 */

public class InsertionSorter extends AbstractSorter {
	
	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/**
	 * Constructor takes an array of points. 
	 * 
	 * @param pts  
	 */
	public InsertionSorter(Point[] pts) {
		super(pts);
		super.algorithm="insertion sort";
		super.outputFileName="insert.txt";
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public InsertionSorter(String inputFileName) throws InputMismatchException, FileNotFoundException {
		super(inputFileName);
		super.algorithm="insertion sort";
		super.outputFileName="insert.txt";
	}
	
	
	/** 
	 * Perform insertion sort on the array points[] of the parent class AbstractSorter.  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle 
	 */
	@Override 
	public void sort(int order){
		long start=System.nanoTime();
		int n=points.length;
		this.setComparator(order);
		if(order==1)
			super.sortByAngle=false;
		else
			super.sortByAngle=true;
		for(int i=1; i<n; i++){
			Point temp= points[i];
			int j=i;
			for(j=i-1; j>=0 && pointComparator.compare(points[j],temp)>0; j--){
				swap(j+1,j);
			}
		}
		long end=System.nanoTime();
		super.sortingTime=(end-start);
	}		
}
