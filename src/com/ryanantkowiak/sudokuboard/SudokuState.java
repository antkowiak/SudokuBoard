package com.ryanantkowiak.sudokuboard;

class CellState
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
                && isHighlighted == cs.isHighlighted
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

class BoardState
{
    // fields
    public CellState[][] cellStates = new CellState[9][9];
    
    public BoardState()
    {
        reset();
    }
    
    public void reset()
    {
        cellStates = new CellState[9][9];
        
        for (int y = 0 ; y < 9 ; ++y)
            for (int x = 0 ; x < 9 ; ++x)
                cellStates[x][y] = new CellState(x, y);
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
        
        BoardState bs = (BoardState) obj;
        
        for (int y = 0 ; y < 9 ; ++y)
            for (int x = 0 ; x < 9 ; ++x)
                if (!bs.cellStates[x][y].equals(cellStates[x][y]))
                    return false;
        
        return true;
    }
    
    public BoardState clone()
    {
        BoardState bs = new BoardState();

        for (int y = 0 ; y < 9 ; ++y)
            for (int x = 0 ; x < 9 ; ++x)
                bs.cellStates[x][y] = cellStates[x][y].clone();

        return bs;
    }
    
}

public class SudokuState
{
    // fields
    BoardState boardState;
    
    public SudokuState()
    {
        boardState = new BoardState();
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
        
        SudokuState ss = (SudokuState) obj;
        
        return boardState.equals(ss.boardState);
    }

    public SudokuState clone()
    {
        SudokuState ss = new SudokuState();
        
        ss.boardState = boardState.clone();
        
        return ss;
    }

}
