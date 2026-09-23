package lab13;

//----------------------------------------------------------------------------
// Sorts.java               by Dale/Joyce/Weems                     Chapter 10
//
// Test harness used to run sorting algorithms.
//----------------------------------------------------------------------------

import java.util.*;


import lab11.Artist;
import lab11.ArtistQueue;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;

public class Sorts
{
  static int SIZE = 1000;            // size of array to be sorted
  static int[] values; // values to be sorted
  static int [] copy;

  static void initValues()
  // Initializes the values array with random integers from 0 to 99.
  {
    Random rand = new Random();
    for (int index = 0; index < SIZE; index++)
      values[index] = Math.abs(rand.nextInt()) % 100;
  }

  static public boolean isSorted()
  // Returns true if the array values are sorted and false otherwise.
  {
    boolean sorted = true;
    for (int index = 0; index < (SIZE - 1); index++)
      if (values[index] > values[index + 1])
        sorted = false;
    return sorted;
  }

  static public void swap(int index1, int index2)
  // Precondition: index1 and index2 are >= 0 and < SIZE.
  //
  // Swaps the integers at locations index1 and index2 of the values array. 
  {
    int temp = values[index1];
    values[index1] = values[index2];
    values[index2] = temp;
  }

  static public void printValues()
  // Prints all the values integers.
  {
    int value;
    DecimalFormat fmt = new DecimalFormat("00");
    System.out.println("The values array is:");
    for (int index = 0; index < SIZE; index++)
    {
      value = values[index];
      if (((index + 1) % 10) == 0)
        System.out.println(fmt.format(value));
      else
        System.out.print(fmt.format(value) + " ");
    }
    System.out.println();
  }
  static public void clearValues() {
	  for (int i=0;i<values.length;i++) {
		  values[i] = 0;
	  }
  }


  /////////////////////////////////////////////////////////////////
  //
  //  Selection Sort

  static int minIndex(int startIndex, int endIndex)
  // Returns the index of the smallest value in
  // values[startIndex]..values[endIndex].
  {
    int indexOfMin = startIndex;
    for (int index = startIndex + 1; index <= endIndex; index++)
      if (values[index] < values[indexOfMin])
        indexOfMin = index;
    return indexOfMin;
  }

  static void selectionSort()
  // Sorts the values array using the selection sort algorithm.
  {
    int endIndex = SIZE - 1;
    for (int current = 0; current < endIndex; current++)
      swap(current, minIndex(current, endIndex));
  }


  /////////////////////////////////////////////////////////////////
  //
  //  Bubble Sort

  static void bubbleUp(int startIndex, int endIndex)
  // Switches adjacent pairs that are out of order 
  // between values[startIndex]..values[endIndex] 
  // beginning at values[endIndex].
  {
    for (int index = endIndex; index > startIndex; index--)
      if (values[index] < values[index - 1])
        swap(index, index - 1);
  }
 
  static void bubbleSort()
  // Sorts the values array using the bubble sort algorithm.
  {
    int current = 0;
 
    while (current < (SIZE - 1))
    {
      bubbleUp(current, SIZE - 1);
      current++;
    }
  }


  /////////////////////////////////////////////////////////////////
  //
  //  Short Bubble Sort

  static boolean bubbleUp2(int startIndex, int endIndex)
  // Switches adjacent pairs that are out of order 
  // between values[startIndex]..values[endIndex] 
  // beginning at values[endIndex].
  //
  // Returns false if a swap was made; otherwise, returns true.
  {
    boolean sorted = true;
    for (int index = endIndex; index > startIndex; index--)
      if (values[index] < values[index - 1])
      {
        swap(index, index - 1);
        sorted = false;
      }
    return sorted;
  }
 
  static void shortBubble()
  // Sorts the values array using the bubble sort algorithm.
  // The process stops as soon as values is sorted.
  {
    int current = 0;
    boolean sorted = false;
    while ((current < (SIZE - 1)) && !sorted)
    {
      sorted = bubbleUp2(current, SIZE - 1);
      current++;
    }
  }


  /////////////////////////////////////////////////////////////////
  //
  //  Insertion Sort

  static void insertItem(int startIndex, int endIndex)
  // Upon completion, values[0]..values[endIndex] are sorted.
  {
    boolean finished = false;
    int current = endIndex;
    boolean moreToSearch = true;
    while (moreToSearch && !finished)
    {
      if (values[current] < values[current - 1])
      {
        swap(current, current - 1);
        current--;
        moreToSearch = (current != startIndex);
      }
      else
        finished = true;
    }
  }
 
  static void insertionSort()
  // Sorts the values array using the insertion sort algorithm.
  {
   for (int count = 1; count < SIZE; count++)
      insertItem(0, count);
  }


  /////////////////////////////////////////////////////////////////
  //
  //  Merge Sort

  static void merge (int leftFirst, int leftLast, int rightFirst, int rightLast)
  // Preconditions: values[leftFirst]..values[leftLast] are sorted.
  //                values[rightFirst]..values[rightLast] are sorted.
  // 
  // Sorts values[leftFirst]..values[rightLast] by merging the two subarrays.
  {
    int[] tempArray = new int [SIZE];
    int index = leftFirst;
    int saveFirst = leftFirst;  // to remember where to copy back
 
    while ((leftFirst <= leftLast) && (rightFirst <= rightLast))
    {
      if (values[leftFirst] < values[rightFirst])
      {
        tempArray[index] = values[leftFirst];
        leftFirst++;
      }
      else
      {
        tempArray[index] = values[rightFirst];
        rightFirst++;
      }
      index++;
    }
 
    while (leftFirst <= leftLast)
    // Copy remaining items from left half.
 
    {
      tempArray[index] = values[leftFirst];
      leftFirst++;
      index++;
    }
 
    while (rightFirst <= rightLast)
    // Copy remaining items from right half.
    {
      tempArray[index] = values[rightFirst];
      rightFirst++;
      index++;
    }
 
    for (index = saveFirst; index <= rightLast; index++)
      values[index] = tempArray[index];
  }

  static void mergeSort(int first, int last)
  // Sorts the values array using the merge sort algorithm.
  {
    if (first < last)
    {
      int middle = (first + last) / 2;
      mergeSort(first, middle);
      mergeSort(middle + 1, last);
      merge(first, middle, middle + 1, last);
    }
  }


  /////////////////////////////////////////////////////////////////
  //
  //  Quick Sort

  static int split(int first, int last)
  {
    int splitVal = values[first];
    int saveF = first;
    boolean onCorrectSide;
 
    first++;
    do
    {
      onCorrectSide = true;
      while (onCorrectSide)             // move first toward last
        if (values[first] > splitVal)
          onCorrectSide = false;
        else
        {
          first++;
          onCorrectSide = (first <= last);
        }
 
      onCorrectSide = (first <= last);
      while (onCorrectSide)             // move last toward first
        if (values[last] <= splitVal)
          onCorrectSide = false;
        else
         {
          last--;
          onCorrectSide = (first <= last);
         }
   
      if (first < last)                
      {
        swap(first, last);
        first++;
        last--;
      }
    } while (first <= last);
 
    swap(saveF, last);
    return last;
  }

  static void quickSort(int first, int last)
  {
    if (first < last)
    {
      int splitPoint;
 
      splitPoint = split(first, last);
      // values[first]..values[splitPoint - 1] <= splitVal
      // values[splitPoint] = splitVal
      // values[splitPoint+1]..values[last] > splitVal
 
      quickSort(first, splitPoint - 1);
      quickSort(splitPoint + 1, last);
    }
  }


  /////////////////////////////////////////////////////////////////
  //
  //  Heap Sort

  static int newHole(int hole, int lastIndex, int item)
  // If either child of hole is larger than item this returns the index
  // of the larger child; otherwise it returns the index of hole.
  {
    int left = (hole * 2) + 1;
    int right = (hole * 2) + 2;
    if (left > lastIndex)
      // hole has no children
      return hole;         
    else
    if (left == lastIndex)
      // hole has left child only
      if (item < values[left])             
        // item < left child
        return left;
      else
        // item >= left child
        return hole;
    else
    // hole has two children 
    if (values[left] < values[right])
      // left child < right child
      if (values[right] <= item)
        // right child <= item
        return hole;
      else
        // item < right child
        return right;
    else
    // left child >= right child
    if (values[left] <= item)
      // left child <= item
      return hole;
    else
      // item < left child
      return left;
  }

  static void reheapDown(int item, int root, int lastIndex)
  // Precondition: Current root position is "empty".
  //
  // Inserts item into the tree and ensures shape and order properties.
  {
    int hole = root;   // current index of hole
    int newhole;       // index where hole should move to

    newhole = newHole(hole, lastIndex, item);   // find next hole
    while (newhole != hole)
    {
      values[hole] = values[newhole];      // move value up
      hole = newhole;                      // move hole down
      newhole = newHole(hole, lastIndex, item);     // find next hole
    }
    values[hole] = item;           // fill in the final hole
  }

  static void heapSort()
  // Sorts the values array using the heap sort algorithm.
  {
    int index;
    // Convert the array of values into a heap.
    for (index = SIZE/2 - 1; index >= 0; index--)
      reheapDown(values[index], index, SIZE - 1);
 
    // Sort the array.
    for (index = SIZE - 1; index >=1; index--)
    {
      swap(0, index);
      reheapDown(values[0], 0, index - 1);
    }
  }
  
  public static void fromFile (String fileName, int limit) {
		
		
		InputStream file = ArtistQueue.class.getResourceAsStream(fileName);
		values = new int [limit];
		SIZE = limit;
			if (file == null) {
				System.out.println("Please enter a vaild file");
				return ;
				}
			InputStreamReader reader = new InputStreamReader(file, StandardCharsets.UTF_8);
			Scanner scanner = new Scanner(reader);
			if (scanner.hasNextLine()) {
				scanner.nextLine();
				}
			for (int i=0;i<limit;i++) {
				String temp = scanner.nextLine(); 
		        //this.add(new Artist(temp));
	        	values[i] = (new Artist(temp)).getId().hashCode();
			}
			
			copy = new int [limit];
			for (int i=0;i<limit;i++) {
		    	copy[i] = values[i];
		    }
		        scanner.close();
		        
			}
  public static void printFive () {
	  for (int i=0;i<5;i++) {
		  System.out.print(values[i] + ", ");
	  }
	  System.out.println();
  }
  
  public static void revert (int [] copy) {
	  for (int i=0;i<values.length;i++) {
		  values [i] = copy[i];
	  }
  }
  /////////////////////////////////////////////////////////////////
  //
  //  Main

  public static void main(String[] args)
  {
    //initValues();
   // printValues();
    //System.out.println("values is sorted: " + isSorted());
    //System.out.println();
    
    // make call to sorting method here (just remove //)
     //selectionSort();
    // bubbleSort();
    // shortBubble();
    // insertionSort();
    // mergeSort(0, SIZE - 1);
    // quickSort(0, SIZE - 1);
    // heapSort();

   // printValues();
   // System.out.println("values is sorted: " + isSorted());
   // System.out.println();
    
    
	
    
    
    long start;
    long end;
    double total;
    
    
    
    
    
    
    fromFile("artists.csv",100);
    System.out.println("Array of 100: \n");
    
    printFive();
    start = System.nanoTime();
    selectionSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Selection sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    bubbleSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Bubble sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    shortBubble();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Short bubble - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    insertionSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Insertion sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    mergeSort(0,SIZE-1);
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Merge sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    quickSort(0, SIZE - 1);
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Quick sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    heapSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Heap sort - " + total + "\n");
    revert(copy);
    
    
    
    
    
    fromFile("artists.csv",1000);
    System.out.println("\n\nArray of 1000: \n");
    
    printFive();
    start = System.nanoTime();
    selectionSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Selection sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    bubbleSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Bubble sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    shortBubble();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Short bubble - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    insertionSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Insertion sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    mergeSort(0,SIZE-1);
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Merge sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    quickSort(0, SIZE - 1);
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Quick sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    heapSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Heap sort - " + total + "\n");
    revert(copy);
    
    
    fromFile("artists.csv",10000);
    System.out.println("\n\nArray of 10000: \n");
    
    printFive();
    start = System.nanoTime();
    selectionSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Selection sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    bubbleSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Bubble sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    shortBubble();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Short bubble - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    insertionSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Insertion sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    mergeSort(0,SIZE-1);
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Merge sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    quickSort(0, SIZE - 1);
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Quick sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    heapSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Heap sort - " + total + "\n");
    revert(copy);
    
    
    fromFile("artists.csv",100000);
    System.out.println("\n\nArray of 100000: \n");
    
    printFive();
    start = System.nanoTime();
    selectionSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Selection sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    bubbleSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Bubble sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    shortBubble();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Short bubble - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    insertionSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Insertion sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    mergeSort(0,SIZE-1);
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Merge sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    quickSort(0, SIZE - 1);
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Quick sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    heapSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Heap sort - " + total + "\n");
    revert(copy);
    
    
    
    fromFile("artists.csv",200000);
    System.out.println("\n\nArray of 200000: \n");
    
    printFive();
    start = System.nanoTime();
    selectionSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Selection sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    bubbleSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Bubble sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    shortBubble();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Short bubble - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    insertionSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Insertion sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    mergeSort(0,SIZE-1);
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Merge sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    quickSort(0, SIZE - 1);
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Quick sort - " + total + "\n");
    revert(copy);
    
    printFive();
    start = System.nanoTime();
    heapSort();
    end = System.nanoTime();
    printFive();
    System.out.println(end-start);
    total = (end - start)/1000000000.0;
    System.out.println("Heap sort - " + total + "\n");
    revert(copy);
  
    
  }
}
