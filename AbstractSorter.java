import java.util.ArrayList;

/**
 *  
 * @author Stephanie Engelhardt
 *
 */

import java.util.Comparator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 * This abstract class is extended by SelectionSort, InsertionSort, MergeSort, and QuickSort.
 * It stores the input (later the sorted) sequence and records the employed sorting algorithm, 
 * the comparison method, and the time spent on sorting. 
 *
 */


public abstract class AbstractSorter{
	
	protected Point[] points;    // Array of points operated on by a sorting algorithm. 
	                             // The number of points is given by points.length.
	
	protected String algorithm = null; // "selection sort", "insertion sort", "mergesort", or
	                                   // "quicksort". Initialized by a subclass. 
									   // constructor.
	protected boolean sortByAngle;     // true if the last sorting was done by polar angle and  
									   // false if by x-coordinate 
	
	protected String outputFileName;   // "select.txt", "insert.txt", "merge.txt", or "quick.txt"
	
	protected long sortingTime; 	   // execution time in nanoseconds. 
	 
	protected Comparator<Point> pointComparator;  // comparator which compares polar angle if 
												  // sortByAngle == true and x-coordinate if 
												  // sortByAngle == false 
	
	private Point lowestPoint; 	    // lowest point in the array, or in case of a tie, the
									// leftmost of the lowest points. This point is used 
									// as the reference point for polar angle based comparison.

	
	// Add other protected or private instance variables you may need. 
	
	protected AbstractSorter(){
		// No implementation needed. Provides a default super constructor to subclasses. 
		// Removable after implementing SelectionSorter, InsertionSorter, MergeSorter, and QuickSorter.
	}
	
	
	/**
	 * This constructor accepts an array of points as input. Copy the points into the array points[]. 
	 * Sets the instance variable lowestPoint.
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	protected AbstractSorter(Point[] pts) throws IllegalArgumentException{
		if(pts==null|| pts.length==0){
			throw new IllegalArgumentException();
		}
		points=pts;
		lowestPoint=pts[0];
		for(int i=0; i<pts.length; i++){
			if(pts[i].compareTo(lowestPoint)<0)
				lowestPoint=pts[i];	
		}
		
	}

	
	/**
	 * This constructor reads points from a file. Sets the instance variables lowestPoint and 
	 * outputFileName.
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   when the input file contains an odd number of integers
	 */
	protected AbstractSorter(String inputFileName) throws FileNotFoundException, InputMismatchException{
		 try {
			 	ArrayList<Point> result=new ArrayList<Point>();
	            File file = new File(inputFileName);
	            Scanner input = new Scanner(file);
	            while (input.hasNextLine()) {
	            	String s = input.nextLine();
	            	Scanner scan=new Scanner(s);
	            	scan.useDelimiter(" ");
	                while(scan.hasNext()){
	                	int x=scan.nextInt();
	                	if(!scan.hasNext())
	                		throw new InputMismatchException(); 
	                	int y=scan.nextInt();
	                	result.add(new Point(x,y));
	                }
	                scan.close();
	            }
	            input.close();
	            points= new Point[result.size()];
	            lowestPoint= result.get(0);
	            for(int i=0; i<result.size(); i++){
	            	points[i]=result.get(i);
	            	if(points[i].compareTo(lowestPoint)<0)
	            		lowestPoint=points[i];
	            }
	        } 
	           catch (Exception FileNotFoundException) {
	            
	        }

		}
	

	/**
	 * Sorts the elements in points[]. 
	 * 
	 *     a) in the non-decreasing order of x-coordinate if order == 1
	 *     b) in the non-decreasing order of polar angle w.r.t. lowestPoint if order == 2 
	 *        (lowestPoint will be at index 0 after sorting)
	 * 
	 * Sets the instance variable sortByAngle based on the value of order. Calls the method 
	 * setComparator() to set the variable pointComparator and use it in sorting.    
	 * Records the sorting time (in nanoseconds) using the System.nanoTime() method. 
	 * (Assign the time spent to the variable sortingTime.)  
	 * 
	 * @param order  1   by x-coordinate 
	 * 			     2   by polar angle w.r.t lowestPoint 
	 *
	 * @throws IllegalArgumentException if order is less than 1 or greater than 2
	 */
	public abstract void sort(int order) throws IllegalArgumentException; 
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats(){
		//some setup that will be used throughout
		String size="";
		size+=points.length;
		int spaces=helper(points.length);
		while(spaces!=0){
			size+=" ";
			spaces--;
		}
		int order=0;
		if(sortByAngle)
			order=2;
		else
			order=1;
		
		//beginning of table
		String result="";
		result+="algorithm        ";
		result+="size     ";
		result+="time(ns) \n";
		result+="------------------------------------\n";

		//selection sort row
		SelectionSorter s=new SelectionSorter(points);
		result+="selection sort   ";
		result+=size;
		s.sort(order);
		result+=s.sortingTime;
		result+="\n";
		
		//insertion sort row
		InsertionSorter i=new InsertionSorter(points);
		result+="insertion sort   ";
		result+=size;
		i.sort(order);
		result+=i.sortingTime;
		result+="\n";
		
		//merge sort row
		MergeSorter m=new MergeSorter(points);
		result+="mergesort        ";
		result+=size;
		m.sort(order);
		result+=m.sortingTime;
		result+="\n";
		
		//quick sort row
		QuickSorter q=new QuickSorter(points);
		result+="quicksort        ";
		result+=size;
		q.sort(order);
		result+=q.sortingTime;
		result+="\n";
		
		result+="------------------------------------\n";
		return result; 
	}
	
	//helper method to determine how many spaces should be put after the number of points
	private int helper(int p){
		int numberDigits=0;
		while(p/10!=0){
			p=p/10;
			numberDigits++;
		}
		//the total space it should take up is 9 chars
		return 9-numberDigits;
	}
	
	/**
	 * Write points[] to a string.  When printed, the points will appear in order of increasing
	 * index with every point occupying a separate line.  The x and y coordinates of the point are 
	 * displayed on the same line with exactly one blank space in between. 
	 */
	@Override
	public String toString(){
		String result="";
		for(int x=0; x<points.length; x++){
			result+=points[x].toString();
			result+="\n";
		}
		return result;
	}
	
	 /**
	  * This method, called after sorting, writes point data into a file by outputFileName.<br>
	  * The format of data in the file is the same as printed out from toString().<br>
	  * The file can help you verify the full correctness of a sorting result and debug the underlying algorithm.
	  * 
	  * @throws FileNotFoundException
	  */
	 public void writePointsToFile() throws FileNotFoundException {
		 BufferedWriter writer = null;
	        try {
	            File file = new File(outputFileName);
	            writer = new BufferedWriter(new FileWriter(file));
	            writer.write(points.toString());
	        } 
	        catch (Exception e) {
	            e.printStackTrace();
	        } 
		try {
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	 }


	/**
	 * This method is called after sorting for visually check whether the result is correct.  You  
	 * just need to generate a list of points and a list of segments, depending on the value of 
	 * sortByAngle, as detailed in Section 4.1. Then create a Plot object to call the method myFrame().  
	 */
	public void draw(){	
		int numSegs;
		Segment[] segments;
		ArrayList<Segment> segs=new ArrayList<Segment>();
		if(sortByAngle){
			int count=0;
			int i=1;
			while(i<points.length){
				segs.add(new Segment(points[i],lowestPoint));
				if((i+1)!=points.length){
					if(points[i].compareTo(points[i+1])!=0){
						segs.add(new Segment(points[i],points[i+1]));
					}
					else
						i++;
				}
				i++;	
			}
		}
		else{
			for(int i=0; i<points.length; i++){
				if((i+1)!=points.length){
					if(points[i].compareTo(points[i+1])==0)
						i++;
					if((i+1!=points.length))
						segs.add(new Segment(points[i],points[i+1]));
				}	
			}
		}
		segments=new Segment[segs.size()];
		for(int x=0; x<segs.size(); x++){
			segments[x]=segs.get(x);
		}
		// The following statement creates a window to display the sorting result.
		Plot.myFrame(points, segments, getClass().getName());
		
	}
		
	/**
	 * Generates a comparator on the fly that compares by polar angle if sortByAngle == true
	 * and by x-coordinate if sortByAngle == false. Set the protected variable pointComparator
	 * to it. Need to create an object of the PolarAngleComparator class and call the compareTo() 
	 * method in the Point class, respectively for the two possible values of sortByAngle.  
	 * 
	 * @param order
	 */
	protected void setComparator(int order) {
		if(order==2)
			pointComparator=new PolarAngleComparator(lowestPoint);
		else{
			pointComparator= new Comparator<Point>(){
				@Override
				public int compare(Point p, Point q){
					return p.compareTo(q);
				}
			};
		}
	}

	
	/**
	 * Swap the two elements indexed at i and j respectively in the array points[]. 
	 * 
	 * @param i
	 * @param j
	 */
	protected void swap(int i, int j){
		Point temp=points[i];
		points[i]=points[j];
		points[j]=temp;
	}	
}
