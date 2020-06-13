package com.ryanantkowiak.sudokuboard;

public interface SudokuListener
{
    public abstract void handleEventAboutButton();
    public abstract void handleEventImportButton();
    public abstract void handleEventResetButton();
    public abstract void handleEventReset();
    public abstract void handleEventCellModeButton(CellMode newCellMode);
    public abstract void handleEventClearTopButton();
    public abstract void handleEventClearBottomButton();
    public abstract void handleEventClearCenterButton();
    public abstract void handleEventClearAllButton();
    public abstract void handleEventClearTopBottomButton();
    public abstract void handleEventCheckBoardButton();
    public abstract void handleEventControlNumberKeyTyped(int n);
    public abstract void handleEventNumberKeyTyped(int n, boolean forceClear);
    public abstract void handleEventLetterKeyTyped(char c);
    public abstract void handleRepaintRequest();
    public abstract void handleImportCellValue(int x, int y, int n);
    public abstract void handleHighlightAllCells(boolean highlighted);
}
