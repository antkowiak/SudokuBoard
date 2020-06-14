package com.ryanantkowiak.sudokuboard.sm;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.ryanantkowiak.sudokuboard.SudokuBoardCell;
import com.ryanantkowiak.sudokuboard.SudokuBoardComponent;
import com.ryanantkowiak.sudokuboard.SudokuControlComponent;
import com.ryanantkowiak.sudokuboard.SudokuListener;

public class GlobalState
{
    public static final String APP_TITLE = "Sudoku Board";
    public static final String APP_VERSION_NUMBER = "1.20200613.1";
    
    public static SudokuBoardComponent boardComponent;
    public static SudokuControlComponent controlComponent;
    public static SudokuBoardCell [][] cells = new SudokuBoardCell[9][9];
    
    public static boolean isSelecting = true;
    public static boolean isDragging = false;

    public static boolean isControlKeyPressed = false;
    
    public static CellMode cellMode = CellMode.GIVEN;

    public static String importText = "";
    
    public static Point lastHighlightedCell = new Point(0, 0);
    
    //
    
    public static boolean doHighlightedCellsContainNumber(int n)
    {
        for (int x = 0 ; x < 9 ; ++x)
            for (int y = 0 ; y < 9 ; ++y)
                if (SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y].isHighlightedAndContainsNumber(cellMode, n))
                    return true;
        return false;
    }
    
    private static List<SudokuListener> m_listeners = new ArrayList<SudokuListener>();
    
    public static void addSudokuListener(SudokuListener listener)
    {
        if (listener != null && !m_listeners.contains(listener))
            m_listeners.add(listener);
    }
    
    public static void removeSudokuListener(SudokuListener listener)
    {
        m_listeners.remove(listener);
    }
       
    public static void fireEventNumberKeyTyped(int n)
    {
        boolean forceClear = doHighlightedCellsContainNumber(n);
        
        for (SudokuListener sl : m_listeners)
            sl.handleEventNumberKeyTyped(n, forceClear);
    }
    
    public static void fireEventImportCellValue(int x, int y, int n) { for (SudokuListener sl : m_listeners) sl.handleImportCellValue(x, y, n); }
}
