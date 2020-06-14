package com.ryanantkowiak.sudokuboard.sm;

import java.util.Stack;

public class SudokuStateStack
{
    private static final int MAX_UNDO_COUNT = 100;
    
    private static SudokuStateStack instance = new SudokuStateStack();

    private SudokuState currentState = new SudokuState();
    private Stack<SudokuState> undoStack = new Stack<SudokuState>();;
    private Stack<SudokuState> redoStack = new Stack<SudokuState>();;
    
    private SudokuStateStack()
    {
    }
    
    public static SudokuStateStack getInstance()
    {
        return instance;
    }
    
    public SudokuState getCurrentState()
    {
        return currentState;
    }
    
    public void saveState()
    {
        if (undoStack.isEmpty() || !undoStack.peek().equals(currentState))
        {
            undoStack.push(currentState.clone());
            redoStack.clear();
        }
        
        if (undoStack.size() > MAX_UNDO_COUNT)
            undoStack.remove(undoStack.size() - 1);
    }
    
    public void undo()
    {
        if (!undoStack.isEmpty())
        {
            redoStack.push(currentState.clone());
            currentState = undoStack.pop();
        }
    }
    
    public void redo()
    {
        if (!redoStack.isEmpty())
        {
            undoStack.push(currentState.clone());
            currentState = redoStack.pop();
        }
    }

}
