package com.ryanantkowiak.sudokuboard;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.ryanantkowiak.sudokuboard.sm.CellMode;
import com.ryanantkowiak.sudokuboard.sm.CellState;
import com.ryanantkowiak.sudokuboard.sm.GlobalState;
import com.ryanantkowiak.sudokuboard.sm.SudokuStateStack;

public class SudokuBoardCell extends JPanel implements MouseListener, SudokuListener
{
    private static final long serialVersionUID = 1L;
    
    private int m_x;
    private int m_y;
       
    private static Color GIVEN_COLOR = Color.BLACK;
    private static Color PLAYER_COLOR = Color.BLUE;
    private static Color TOP_COLOR = new Color(0, 80, 0);
    private static Color BOTTOM_COLOR = new Color(180, 0, 180);
    
    private static Font CENTER_FONT = new Font("Arial", Font.PLAIN, 42);
    private static Font TOP_FONT = new Font("Arial", Font.PLAIN, 12);
    private static Font BOTTOM_FONT = new Font("Arial", Font.PLAIN, 12);
    
    private static Color HL_BG_COLOR = new Color(255, 255, 150);
    private static Color NOT_HL_BG_COLOR = Color.WHITE;
    
    private static Color LAST_SELECTED_COLOR = Color.GRAY;
        
    public SudokuBoardCell(int x, int y)
    {
        m_x = x;
        m_y = y;
        
        GlobalState.addSudokuListener(this);
        
        setSize(SudokuWindowDimensions.getCellDimension());
        setPreferredSize(SudokuWindowDimensions.getCellDimension());
        setBackground(NOT_HL_BG_COLOR);
        setLocation(SudokuWindowDimensions.getCellPosition(x, y));
        addMouseListener(this);
        setOpaque(true);
        setVisible(true);
    }
    
    private CellState getCellState()
    {
        return SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[m_x][m_y];
    }
        
    private static boolean isValidNumber(int n)
    {
        return n > 0 && n < 10;
    }
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);     
        
        CellState cellState = getCellState();
        
        if (cellState.isHighlighted)
            setBackground(HL_BG_COLOR);
        else
            setBackground(NOT_HL_BG_COLOR);
        
        if (m_x == GlobalState.lastHighlightedCell.x && m_y == GlobalState.lastHighlightedCell.y)
        {
            g.setColor(LAST_SELECTED_COLOR);
            g.drawRect(0, 0, SudokuWindowDimensions.getCellWidth() - 1, SudokuWindowDimensions.getCellHeight() - 1);
        }
        
        if (cellState.isGiven && isValidNumber(cellState.centerNumber))
        {
            g.setColor(GIVEN_COLOR);
            paintCenteredString(g, Integer.toString(cellState.centerNumber), getBounds(), CENTER_FONT);
        }
        else if (!cellState.isGiven && isValidNumber(cellState.centerNumber))
        {
            g.setColor(PLAYER_COLOR);
            paintCenteredString(g, Integer.toString(cellState.centerNumber), getBounds(), CENTER_FONT);
        }
        
        if (!cellState.isGiven)
        {
            if (!cellState.topNumbers.isEmpty())
            {
                g.setColor(TOP_COLOR);
                paintTopString(g, listToString(cellState.topNumbers), getBounds(), TOP_FONT);
            }
            
            if (!cellState.bottomNumbers.isEmpty())
            {
                g.setColor(BOTTOM_COLOR);
                paintBottomString(g, listToString(cellState.bottomNumbers), getBounds(), BOTTOM_FONT);
            }
        }
    }
    
    private static String listToString(List<Integer> data)
    {
        String s = "";
        
        for (Integer i : data)
            s += i.toString();
        
        return s;
    }

    private static void paintCenteredString(Graphics g, String text, Rectangle rect, Font font)
    {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (rect.width - metrics.stringWidth(text)) / 2;
        int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    private static void paintTopString(Graphics g, String text, Rectangle rect, Font font)
    {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = 0;
        int y = metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    private static void paintBottomString(Graphics g, String text, Rectangle rect, Font font)
    {
        int x = 0;
        int y = rect.height - 2;
        g.setFont(font);
        g.drawString(text, x, y);
    }
       
    private void setNumber(CellMode mode, int n, boolean forceClear)
    {
        CellState cellState = getCellState();
        
        if (mode == CellMode.GIVEN)
        {
            if (n == 0)
            {
                cellState.isGiven = false;
                cellState.centerNumber = 0;
                cellState.topNumbers.clear();
                cellState.bottomNumbers.clear();
            }
            else if (isValidNumber(n))
            {
                cellState.isGiven = true;
                if (cellState.centerNumber == n)
                    cellState.centerNumber = 0;
                else
                    cellState.centerNumber = n;
                cellState.topNumbers.clear();
                cellState.bottomNumbers.clear();
            }
        }
        
        if (mode == CellMode.TOP && !cellState.isGiven)
        {
            if (n == 0)
            {
                cellState.topNumbers.clear();
            }
            else if (isValidNumber(n))
            {
                 if (cellState.topNumbers.contains(new Integer(n)) || forceClear)
                 {
                     cellState.topNumbers.remove(new Integer(n));
                 }
                 else
                 {
                     cellState.topNumbers.add(new Integer(n));
                 }
            }
            
            repaint();
        }
        
        if (mode == CellMode.BOTTOM && !cellState.isGiven)
        {
            if (n == 0)
            {
                cellState.bottomNumbers.clear();
            }
            else if (isValidNumber(n))
            {
                 if (cellState.bottomNumbers.contains(new Integer(n)) || forceClear)
                 {
                     cellState.bottomNumbers.remove(new Integer(n));
                 }
                 else
                 {
                     cellState.bottomNumbers.add(new Integer(n));
                 }
            }
        }
        
        if (mode == CellMode.CENTER && !cellState.isGiven)
        {
            if (n == 0 || isValidNumber(n))
            {
                if (cellState.centerNumber == n)
                    cellState.centerNumber = 0;
                else
                    cellState.centerNumber = n;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent arg0)
    {    
    }

    @Override
    public void mouseEntered(MouseEvent arg0)
    {
        if (SwingUtilities.isRightMouseButton(arg0))
            if (GlobalState.isDragging)
            {
                getCellState().setHighlighted(GlobalState.isSelecting);
                GlobalState.lastHighlightedCell.x = m_x;
                GlobalState.lastHighlightedCell.y = m_y;
                GlobalState.boardComponent.repaint();
            }
    }

    @Override
    public void mouseExited(MouseEvent arg0)
    {
    }

    @Override
    public void mousePressed(MouseEvent arg0)
    {
        CellState cellState = getCellState();
        
        if (SwingUtilities.isRightMouseButton(arg0))
        {
            GlobalState.isDragging = true;
            GlobalState.isSelecting = !cellState.isHighlighted;
            cellState.toggleHighlighted();
        }
        
        if (SwingUtilities.isLeftMouseButton(arg0))
        {
            cellState.setHighlighted(true);
            GlobalState.isDragging = false;
            GlobalState.isSelecting = false;
            
            GlobalState.boardComponent.highlightAllCells(false);
            cellState.setHighlighted(true);
        }
        
        GlobalState.lastHighlightedCell.x = m_x;
        GlobalState.lastHighlightedCell.y = m_y;
        GlobalState.boardComponent.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {
        GlobalState.isDragging = false;
        GlobalState.boardComponent.repaint();
    }

    @Override
    public void handleEventNumberKeyTyped(int n, boolean forceClear)
    {
        CellState cellState = getCellState();
        
        if (cellState.isHighlighted)
            setNumber(GlobalState.cellMode, n, forceClear);
    }

    @Override
    public void handleImportCellValue(int x, int y, int n)
    {
        if (x == m_x && y == m_y)
            setNumber(CellMode.GIVEN, n, false);
    }
}
