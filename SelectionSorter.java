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
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter{

	/**
	 * The two constructors below invoke their corresponding superclass constructors. They
	 * also set the instance variables algorithm and outputFileName in the superclass.
	 */

	/**
	 * Constructor takes an array of points.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  {
		super(pts);
		super.algorithm="selection sort";
		super.outputFileName="select.txt";
	}	

	
	/**
	 * Constructor reads points from a file. 
	 * 
	 * @param inputFileName  name of the input file
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 */
	public SelectionSorter(String inputFileName) throws InputMismatchException, FileNotFoundException {
		super(inputFileName);
		super.algorithm="selection sort";
		super.outputFileName="select.txt";
	}
	
	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
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
		for(int i=0; i<points.length-1;i++){
			int minIndex=i;
			for(int j=i+1; j<points.length;++j){
				if(pointComparator.compare(points[j], points[minIndex])<0)
					minIndex=j;
			}
			Point temp=points[i];
			points[i]=points[minIndex];
			points[minIndex]=temp;
		}
		long end=System.nanoTime();
		super.sortingTime=(end-start);
	}	
}
