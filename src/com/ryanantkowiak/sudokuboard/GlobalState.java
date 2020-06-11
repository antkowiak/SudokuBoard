package com.ryanantkowiak.sudokuboard;

import java.util.ArrayList;
import java.util.List;

public class GlobalState
{
    public static final String APP_TITLE = "Sudoku Board";
    public static final String APP_VERSION_NUMBER = "1.20200611.0";
    
    public static SudokuBoardComponent boardComponent;
    public static SudokuControlComponent controlComponent;
    public static SudokuBoardCell [][] cells = new SudokuBoardCell[9][9];
    
    public static boolean isSelecting = true;
    public static boolean isDragging = false;

    public static CellMode cellMode = CellMode.GIVEN;

    public static String importText = "";
    
    //
    
    public static boolean doHighlightedCellsContainNumber(int n)
    {
        for (int x = 0 ; x < 9 ; ++x)
            for (int y = 0 ; y < 9 ; ++y)
                if (cells[x][y].isHighlightedAndContainsNumber(cellMode, n))
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
    
    public static void fireEventAboutButton()                        { for (SudokuListener sl : m_listeners) sl.handleEventAboutButton(); }
    public static void fireEventImportButton()                       { for (SudokuListener sl : m_listeners) sl.handleEventImportButton(); }
    public static void fireEventResetButton()                        { for (SudokuListener sl : m_listeners) sl.handleEventResetButton(); }
    public static void fireEventCellModeButton(CellMode newCellMode) { for (SudokuListener sl : m_listeners) sl.handleEventCellModeButton(newCellMode); }
    public static void fireEventClearTopButton()                     { for (SudokuListener sl : m_listeners) sl.handleEventClearTopButton(); }
    public static void fireEventClearBottomButton()                  { for (SudokuListener sl : m_listeners) sl.handleEventClearBottomButton(); }
    public static void fireEventClearCenterButton()                  { for (SudokuListener sl : m_listeners) sl.handleEventClearCenterButton(); }
    public static void fireEventClearAllButton()                     { for (SudokuListener sl : m_listeners) sl.handleEventClearAllButton(); }
    public static void fireEventClearTopBottomButton()               { for (SudokuListener sl : m_listeners) sl.handleEventClearTopBottomButton(); }
    public static void fireEventCheckBoardButton()                   { for (SudokuListener sl : m_listeners) sl.handleEventCheckBoardButton(); }

    public static void fireEventNumberKeyTyped(int n)
    {
        boolean forceClear = doHighlightedCellsContainNumber(n);
        
        for (SudokuListener sl : m_listeners)
            sl.handleEventNumberKeyTyped(n, forceClear);
    }

    
    public static void fireEventLetterKeyTyped(char c)               { for (SudokuListener sl : m_listeners) sl.handleEventLetterKeyTyped(c); }
    public static void fireEventRepaintRequest()                     { for (SudokuListener sl : m_listeners) sl.handleRepaintRequest(); }
    public static void fireEventImportCellValue(int x, int y, int n) { for (SudokuListener sl : m_listeners) sl.handleImportCellValue(x, y, n); }
    public static void fireEventHighlightAllCells(boolean highlight) { for (SudokuListener sl : m_listeners) sl.handleHighlightAllCells(highlight); }
}
