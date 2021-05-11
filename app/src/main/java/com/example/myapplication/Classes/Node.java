package com.example.myapplication.Classes;


public class Node<E>
{
    private E data;
    private Node next;

    protected Node(E data)
    {
        this.data = data;
        this.next = null;
    }

    protected E getData()
    {
        return data;
    }

    protected void setNext(Node next)
    {
        this.next = next;
    }

    protected void setNext(E next)
    {
        this.next = new Node(next);
    }

    protected Node getNext()
    {
        return next;
    }
}
