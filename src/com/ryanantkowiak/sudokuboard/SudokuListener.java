package com.ryanantkowiak.sudokuboard;

public interface SudokuListener
{
    public abstract void handleEventNumberKeyTyped(int n, boolean forceClear);
    public abstract void handleImportCellValue(int x, int y, int n);
    public abstract void handleHighlightAllCells(boolean highlighted);
}
