package com.example.myapplication.Classes;


public class HeapNode<E extends Comparable> implements Comparable<HeapNode>
{
    private int arrayIndex;
    private int index;
    private E value;

    protected HeapNode(int arrayIndex, int index, E value)
    {
        this.arrayIndex = arrayIndex;
        this.index = index;
        this.value = value;
    }

    protected E getValue()
    {
        return value;
    }

    protected int getIndex()
    {
        return index;
    }

    protected int getArrayIndex()
    {
        return arrayIndex;
    }

    @Override
    public int compareTo(HeapNode o)
    {
        if (o.value == null)
        {
            return -1;
        }

        if (this.value == null) // deal with infinities and stuff
        {
            return 1;
        }

        return this.value.compareTo(o.value);
    }

    public String toString()
    {
        if (value == null)
        {
            return "INFINITY";
        }
        return value.toString();
    }
}
