package com.ryanantkowiak.sudokuboard.sm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.UnaryOperator;

public class SortedArrayList<E extends Comparable<? super E>> extends ArrayList<E>
{

    private static final long serialVersionUID = 1L;

    public SortedArrayList()
    {
        super();
    }

    public SortedArrayList(Collection<? extends E> c)
    {
        super(c);
        Collections.sort(this);
    }

    public SortedArrayList(int initialCapacity)
    {
        super(initialCapacity);
    }
    
    @Override
    public SortedArrayList<E> clone()
    {
        return new SortedArrayList<E>(this);
    }
    
    @Override
    public boolean add(E e)
    {
        boolean ret = super.add(e);
        Collections.sort(this);
        return ret;
    }
    
    @Override
    public void add(int index, E element)
    {
        super.add(index, element);
        Collections.sort(this);
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c)
    {
        boolean ret = super.addAll(c);
        Collections.sort(this);
        return ret;
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends E> c)
    {
        boolean ret = super.addAll(index, c);
        Collections.sort(this);
        return ret;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator)
    {
        super.replaceAll(operator);
        Collections.sort(this);
    }
    
    @Override
    public boolean retainAll(Collection<?> c)
    {
        boolean ret = super.retainAll(c);
        Collections.sort(this);
        return ret;
    }
    
    @Override
    public E set(int index, E element)
    {
        E e = super.set(index,  element);
        Collections.sort(this);
        return e;
    }   
}
