package com.ryanantkowiak.sudokuboard;

import java.awt.Dimension;
import java.awt.Point;

public class SudokuWindowDimensions
{
    private static int m_cellHeight = 75;
    private static int m_cellWidth = 75;
    
    private static int m_thinLine = 1;
    private static int m_thickLine = 3;

    private static int m_controlWidth = 200;
        
    public static int getCellHeight() { return m_cellHeight; }
    public static void setCellHeight(int cellHeight) { m_cellHeight = cellHeight; }

    public static int getCellWidth() { return m_cellWidth; }
    public static void setCellWidth(int cellWidth) { m_cellWidth = cellWidth; }

    public static int getThinLine() { return m_thinLine; }
    public static void setThinLine(int thinLine) { m_thinLine = thinLine; }
    
    public static int getThickLine() { return m_thickLine; }
    public static void setThickLine(int thickLine) { m_thickLine = thickLine; }
    
    public static Dimension getCellDimension() { return new Dimension(m_cellWidth, m_cellHeight); }
    
    public static int getBoardWidth() { return (9 * m_cellWidth) + (4 * m_thickLine) + (6 * m_thinLine); }
    public static int getBoardHeight() { return (9 * m_cellHeight) + (4 * m_thickLine) + (6 * m_thinLine); }
    
    public static Dimension getBoardDimension() { return new Dimension(getBoardWidth(), getBoardHeight()); }    

    public static Point getCellPosition(int x, int y)
    {
        int xPos = 0;
        int yPos = 0;

        switch (x)
        {
            case 0: { xPos = 0 * m_cellWidth + 0 * m_thinLine + 1 * m_thickLine ; break; }
            case 1: { xPos = 1 * m_cellWidth + 1 * m_thinLine + 1 * m_thickLine ; break; }
            case 2: { xPos = 2 * m_cellWidth + 2 * m_thinLine + 1 * m_thickLine ; break; }
            case 3: { xPos = 3 * m_cellWidth + 2 * m_thinLine + 2 * m_thickLine ; break; }
            case 4: { xPos = 4 * m_cellWidth + 3 * m_thinLine + 2 * m_thickLine ; break; }
            case 5: { xPos = 5 * m_cellWidth + 4 * m_thinLine + 2 * m_thickLine ; break; }
            case 6: { xPos = 6 * m_cellWidth + 4 * m_thinLine + 3 * m_thickLine ; break; }
            case 7: { xPos = 7 * m_cellWidth + 5 * m_thinLine + 3 * m_thickLine ; break; }
            case 8: { xPos = 8 * m_cellWidth + 6 * m_thinLine + 3 * m_thickLine ; break; }
        }

        switch (y)
        {
            case 0: { yPos = 0 * m_cellHeight + 0 * m_thinLine + 1 * m_thickLine ; break; }
            case 1: { yPos = 1 * m_cellHeight + 1 * m_thinLine + 1 * m_thickLine ; break; }
            case 2: { yPos = 2 * m_cellHeight + 2 * m_thinLine + 1 * m_thickLine ; break; }
            case 3: { yPos = 3 * m_cellHeight + 2 * m_thinLine + 2 * m_thickLine ; break; }
            case 4: { yPos = 4 * m_cellHeight + 3 * m_thinLine + 2 * m_thickLine ; break; }
            case 5: { yPos = 5 * m_cellHeight + 4 * m_thinLine + 2 * m_thickLine ; break; }
            case 6: { yPos = 6 * m_cellHeight + 4 * m_thinLine + 3 * m_thickLine ; break; }
            case 7: { yPos = 7 * m_cellHeight + 5 * m_thinLine + 3 * m_thickLine ; break; }
            case 8: { yPos = 8 * m_cellHeight + 6 * m_thinLine + 3 * m_thickLine ; break; }
        }
        
        return new Point(xPos, yPos);
    }
    
    public static int getControlWidth() { return m_controlWidth; }
    public static void setControlWidth(int controlWidth) { m_controlWidth = controlWidth; }
    
    public static int getControlHeight() { return getBoardHeight(); }
    
    public static Dimension getControlDimension() { return new Dimension(getControlWidth(), getBoardHeight()); }
    public static Point getControlPosition() { return new Point(getBoardWidth(), 0); }

    public static int getWindowWidth() { return getBoardWidth() + getControlWidth(); }
    public static int getWindowHeight() { return getBoardHeight(); }
}
