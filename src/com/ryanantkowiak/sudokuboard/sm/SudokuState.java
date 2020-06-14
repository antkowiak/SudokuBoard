package com.ryanantkowiak.sudokuboard.sm;

public class SudokuState
{
    // fields
    public BoardState boardState;
    
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
