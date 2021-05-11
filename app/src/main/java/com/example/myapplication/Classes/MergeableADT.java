package com.example.myapplication.Classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MergeableADT<E extends List>
{
    private Node<E> front;
    private Node<E> last;

    public MergeableADT(E front)
    {
        this.front = new Node(front);
        this.last = this.front;
    }

    private void push(E item)
    {
        last.setNext(item);
        last = last.getNext();
    }

    private E peek()
    {
        return front.getData();
    }

    private E pop()
    {
        E remove = front.getData();
        front = front.getNext();
        return remove;
    }

    /**
     * True if size of this is strictly equal to one;
     * @return
     */
    private boolean sizeIsOne()
    {
        if (isEmpty())
        {
            return false;
        }
        return front.getNext() == null;
    }

    private boolean isEmpty()
    {
        return front == null;
    }

    public void merge()
    {
        if (isEmpty())
        {
            throw new IllegalArgumentException("ADT is empty");
        }
        if (!sizeIsOne())
        {
            List a = this.pop();
            List b = this.pop();
            E result = (E) merge(a, b);
            this.push(result);
            this.merge();
        }
    }

    private List merge(List<? extends Comparable> a, List<? extends  Comparable> b)
    {
        LinkedList<Object> merged = new LinkedList();
        while (!(a.isEmpty() || b.isEmpty()))
        {
            if (a.get(0).compareTo(b.get(0)) < 0)
            {
                merged.add(a.remove(0));
            } else
            {
                merged.add(b.remove(0));
            }
        }

        while (!a.isEmpty())
        {
            merged.add(a.remove(0));
        }

        while (!b.isEmpty())
        {
            merged.add(b.remove(0));
        }

        return merged;
    }
}
