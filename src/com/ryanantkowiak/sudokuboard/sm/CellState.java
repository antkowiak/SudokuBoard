package com.ryanantkowiak.sudokuboard.sm;

public class CellState
{
    // fields
    public final int x;
    public final int y;
    
    public CellMode mode = CellMode.CENTER;
    public boolean isGiven = false;
    public boolean isHighlighted = false;
    public int centerNumber = 0;
    public SortedArrayList<Integer> topNumbers = new SortedArrayList<Integer>();
    public SortedArrayList<Integer> bottomNumbers = new SortedArrayList<Integer>();
    
    public CellState(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void reset()
    {
        mode = CellMode.CENTER;
        isGiven = false;
        isHighlighted = false;
        centerNumber = 0;
        topNumbers = new SortedArrayList<Integer>();
        bottomNumbers = new SortedArrayList<Integer>();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        
        if (obj == null)
            return false;
        
        if (getClass() != obj.getClass())
            return false;
        
        CellState cs = (CellState) obj;
        
        return x == cs.x
                && y == cs.y
                && mode == cs.mode
                && isGiven == cs.isGiven
                // && isHighlighted == cs.isHighlighted // Ignore highlight state, for the purpose of undo/redo
                && centerNumber == cs.centerNumber
                && topNumbers.equals(cs.topNumbers)
                && bottomNumbers.equals(cs.bottomNumbers);
    }
    
    public CellState clone()
    {
        CellState cs = new CellState(x, y);
        cs.mode = mode;
        cs.isGiven = isGiven;
        cs.isHighlighted = isHighlighted;
        cs.centerNumber = centerNumber;
        cs.topNumbers = new SortedArrayList<Integer>(topNumbers);
        cs.bottomNumbers = new SortedArrayList<Integer>(bottomNumbers);
        return cs;
    }
}