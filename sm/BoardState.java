package com.ryanantkowiak.sudokuboard.sm;

public class BoardState
{
    // fields
    public CellState[][] cellStates;
    
    public BoardState()
    {
        cellStates = new CellState[9][9];
        
        for (int y = 0 ; y < 9 ; ++y)
            for (int x = 0 ; x < 9 ; ++x)
                cellStates[x][y] = new CellState(x, y);
    }
    
    public void reset()
    {
        for (int y = 0 ; y < 9 ; ++y)
            for (int x = 0 ; x < 9 ; ++x)
                cellStates[x][y].reset();
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