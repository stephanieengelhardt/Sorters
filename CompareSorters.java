/**
 *  
 * @author Stephanie Engelhardt
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random; 


public class CompareSorters {
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Perform the four sorting algorithms over each sequence of integers, comparing 
	 * points by x-coordinate or by polar angle with respect to the lowest point.  
	 * 
	 * @param args
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException 
	 **/
	public static void main(String[] args) throws InputMismatchException, FileNotFoundException {
		System.out.println("Comparison of Four Sorting Algorithms");
		System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
		System.out.println("order: 1 (by x-coordinate) 2 (by polar angle)");
		int trial=1;
		Scanner in=new Scanner(System.in);
		System.out.print("Trial "+ trial+": ");
		int key=in.nextInt();
		while(key!=3){
			trial++;
			Point[] pts;
			if(key==1){
				System.out.print("Enter number of random points: ");
				Random generator=new Random();
				pts= generateRandomPoints(in.nextInt(), generator);
			}
			else{
				System.out.println("Points from a file");
				System.out.print("File name: ");
				AbstractSorter a= new InsertionSorter(in.next());
				pts=new Point[a.points.length];
				for(int i=0; i<a.points.length; i++){
					pts[i]=a.points[i];
				}
			}
			AbstractSorter[] sorters=new AbstractSorter[4];
			sorters[0]=new SelectionSorter(pts);
			sorters[1]=new InsertionSorter(pts);
			sorters[2]=new MergeSorter(pts);
			sorters[3]=new QuickSorter(pts);
			System.out.print("Order used in sorting: ");
			int order=in.nextInt();
			for(int i=0; i<sorters.length; i++){
				sorters[i].sort(order);
				sorters[i].draw();
			}
			System.out.println(sorters[0].stats());
			System.out.print("Trial "+ trial+": ");
			key=in.nextInt();
		}
		
	}
	
	
	/**
	 * This method generates a given number of random points to initialize randomPoints[].
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException{ 
		Point[] p=new Point[numPts];
		for(int i=0; i<numPts; i++){
			int x=rand.nextInt(101)-50;
			int y=rand.nextInt(101)-50;
			p[i]=new Point(x,y);
		}
		return p; 
	}
}
