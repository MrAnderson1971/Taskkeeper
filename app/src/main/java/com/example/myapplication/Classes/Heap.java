package com.example.myapplication.Classes;


import com.example.myapplication.MainActivity;

import java.util.*;

public class Heap<E extends Comparable>
{
    private int size;
    private HeapNode[] array;
    private Comparator comparator;

    public Heap(ArrayList<HeapNode> array, Comparator comparator)
    {
        Object[] o = array.toArray();
        this.comparator = comparator;
        this.array = Arrays.copyOf(o, o.length, HeapNode[].class);
        this.size = array.size();

        for (int i = (size - 1) / 2; i >= 0; i--)
        {
            minHeapify(i);
        }
    }

    public Heap(ArrayList<HeapNode> array)
    {
        this(array, new DefaultComparator());
    }

    /**
     * Heapify.
     *
     * @param center The index of the min value of the heap.
     */
    private void minHeapify(int center)
    {
        int left = getLeft(center);
        int right = getRight(center);
        int min = center;

        /*if (left < size && array[left].compareTo(array[min]) < 0)
        {
            min = left;
        }

        if (right < size && array[right].compareTo(array[min]) < 0)
        {
            min = right;
        }*/


        if (left < size && comparator.compare(array[left].getValue(), array[min].getValue()) < 0)
        {
            min = left;
        }


        if (right < size && comparator.compare(array[right].getValue(), array[min].getValue()) < 0)
        {
            min = right;
        }

        if (min != center)
        {
            //Collections.swap(array, min, center);
            // Put the min value where it's supposed to be.
            HeapNode<E> temp = array[min];
            array[min] = array[center];
            array[center] = temp;
            minHeapify(min); // Repeat until index of min found.
        }
    }

    /**
     * For those that want to do this in reverse.
     *
     * @param i
     */
    @Deprecated
    private void maxHeapify(int i)
    {
        int left = getLeft(i);
        int right = getRight(i);
        int max = i;

        if (left < size && array[left].compareTo(array[max]) > 0)
        {
            max = left;
        }

        if (right < size && array[right].compareTo(array[max]) > 0)
        {
            max = right;
        }

        if (max != i)
        {
            //Collections.swap(array, max, i);
            HeapNode<E> temp = array[max];
            array[max] = array[i];
            array[i] = temp;
            maxHeapify(max);
        }
    }

    private HeapNode<E> getRoot()
    {
        return array[0];
    }

    /**
     * From Wikipedia:
     * https://en.wikipedia.org/wiki/Heap_(data_structure)
     * In an implicit heap data structure, the first (or last) element will contain the root.
     * The next two elements of the array contain its children.
     * The next four contain the four children of the two child nodes, etc.
     *
     * @param i index
     * @return index of right node.
     *
     */
    public int getRight(int i)
    {
        return 2 * i + 2;
    }

    public int getLeft(int i)
    {
        return 2 * i + 1;
    }

    private void replaceRoot(HeapNode<E> newNode)
    {
        array[0] = newNode;
        minHeapify(0);
    }

    public int getSize()
    {
        return array.length;
    }

    public boolean isEmpty()
    {
        return array.length == 0;
    }

    public static ArrayList<? extends Comparable> merge(ArrayList<ArrayList<? extends Comparable>> arr)
    {
        return merge(arr, new DefaultComparator());
    }

    /**
     * Merges k sorted arrays.
     *
     * @param arr Array of sorted arrays of Comparable objects.
     * @return Sorted array.
     */
    public static ArrayList<? extends Comparable> merge(ArrayList<ArrayList<? extends Comparable>> arr, Comparator comparator)
    {
        // https://medium.com/outco/how-to-merge-k-sorted-arrays-c35d87aa298e
        if (arr.isEmpty())
        {
            return new ArrayList<>();
        }

        ArrayList<HeapNode> nodes = new ArrayList<>();
        ArrayList<Comparable> result = new ArrayList<>();
        int length = 0;

        // Fill heap with smallest item of each array.
        for (int i = 0; i < arr.size(); i++)
        {
            if (!arr.get(i).isEmpty())
            {
                nodes.add(new HeapNode<>(i, 0, arr.get(i).get(0)));
                length += arr.get(i).size();
            }
        }

        Heap heap = new Heap<>(nodes, comparator);

        for (int i = 0; i < length; i++)
        {
            HeapNode root = heap.getRoot();
            result.add(root.getValue());
            Comparable nextValue = null; // infinity if reached end of an array

            if (root.getIndex() < arr.get(root.getArrayIndex()).size() - 1) // hasn't reached end of an array yet
            {
                nextValue = arr.get(root.getArrayIndex()).get(root.getIndex() + 1); // get the next item in the array the rode was found in
            }

            heap.replaceRoot(new HeapNode<>(root.getArrayIndex(), root.getIndex() + 1, nextValue)); // put next node in place
        }
        return result;
    }

    public static Class<? extends Comparator> getComparator(String comparator)
    {
        if (comparator.equals(MainActivity.dict.get(Keys.NAME)))
        {
            return SortByName.class;
        }

        if (comparator.equals(MainActivity.dict.get(Keys.DATE)))
        {
            return SortByDate.class;
        }

        return DefaultComparator.class;
    }

    public static class DefaultComparator implements Comparator<Reminder>
    {

        @Override
        public int compare(Reminder o1, Reminder o2)
        {
            if (o1 == null)
            {
                return 1;
            }

            if (o2 == null)
            {
                return -1;
            }
            return o1.compareTo(o2);
        }
    }

    public static class SortByDate implements Comparator<Reminder>
    {

        @Override
        public int compare(Reminder o1, Reminder o2)
        {
            if (o1 == null)
            {
                return 1;
            }

            if (o2 == null)
            {
                return -1;
            }
            return o1.getDateAndTime().compareTo(o2.getDateAndTime());
        }

    }

    public static class SortByName implements Comparator<Reminder>
    {

        @Override
        public int compare(Reminder o1, Reminder o2)
        {

            if (o1 == null)
            {
                return 1;
            }

            if (o2 == null)
            {
                return -1;
            }
            return o1.getName().compareTo(o2.getName());
        }

    }

}

