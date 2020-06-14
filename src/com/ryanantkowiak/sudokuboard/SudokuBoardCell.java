package com.ryanantkowiak.sudokuboard;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.ryanantkowiak.sudokuboard.sm.CellMode;
import com.ryanantkowiak.sudokuboard.sm.GlobalState;

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
    
    private boolean m_isGiven = false;
    private boolean m_isHighlighted = false;
    private int m_centerNumber = 0;
    private List<Integer> m_topNumbers = new ArrayList<Integer>();
    private List<Integer> m_bottomNumbers = new ArrayList<Integer>();
    
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
    
    public boolean isHighlightedAndContainsNumber(CellMode mode, int n)
    {
        if (!m_isHighlighted)
            return false;
        
        if (mode == CellMode.CENTER)
            return m_centerNumber == n;
        
        if (mode == CellMode.TOP)
            return m_topNumbers.contains(n);
        
        if (mode == CellMode.BOTTOM)
            return m_bottomNumbers.contains(n);
        
        return false;
    }
    
    private static boolean isValidNumber(int n)
    {
        return n > 0 && n < 10;
    }
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);     
        
        if (m_isHighlighted)
            setBackground(HL_BG_COLOR);
        else
            setBackground(NOT_HL_BG_COLOR);
        
        if (m_x == GlobalState.lastHighlightedCell.x && m_y == GlobalState.lastHighlightedCell.y)
        {
            g.setColor(LAST_SELECTED_COLOR);
            g.drawRect(0, 0, SudokuWindowDimensions.getCellWidth() - 1, SudokuWindowDimensions.getCellHeight() - 1);
        }
        
        if (m_isGiven && isValidNumber(m_centerNumber))
        {
            g.setColor(GIVEN_COLOR);
            drawCenteredString(g, Integer.toString(m_centerNumber), getBounds(), CENTER_FONT);
        }
        else if (!m_isGiven && isValidNumber(m_centerNumber))
        {
            g.setColor(PLAYER_COLOR);
            drawCenteredString(g, Integer.toString(m_centerNumber), getBounds(), CENTER_FONT);
        }
        
        if (!m_isGiven)
        {
            if (!m_topNumbers.isEmpty())
            {
                g.setColor(TOP_COLOR);
                drawTopString(g, listToString(m_topNumbers), getBounds(), TOP_FONT);
            }
            
            if (!m_bottomNumbers.isEmpty())
            {
                g.setColor(BOTTOM_COLOR);
                drawBottomString(g, listToString(m_bottomNumbers), getBounds(), BOTTOM_FONT);
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

    private static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font)
    {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (rect.width - metrics.stringWidth(text)) / 2;
        int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    private static void drawTopString(Graphics g, String text, Rectangle rect, Font font)
    {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = 0;
        int y = metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    private static void drawBottomString(Graphics g, String text, Rectangle rect, Font font)
    {
        int x = 0;
        int y = rect.height - 2;
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void toggleHighlighted(boolean redraw)
    {
        m_isHighlighted = !m_isHighlighted;
        
        if (redraw)
            repaint();
    }
    
    public void setHighlighted(boolean highlighted)
    {
        m_isHighlighted = highlighted;
        repaint();
    }
    
    public int getCenterNumber()
    {
        return m_centerNumber;
    }
    
    private void setNumber(CellMode mode, int n, boolean forceClear)
    {
        if (mode == CellMode.GIVEN)
        {
            if (n == 0)
            {
                m_isGiven = false;
                m_centerNumber = 0;
                m_topNumbers.clear();
                m_bottomNumbers.clear();
            }
            else if (isValidNumber(n))
            {
                m_isGiven = true;
                if (m_centerNumber == n)
                    m_centerNumber = 0;
                else
                    m_centerNumber = n;
                m_topNumbers.clear();
                m_bottomNumbers.clear();
            }
        }
        
        if (mode == CellMode.TOP && !m_isGiven)
        {
            if (n == 0)
            {
                m_topNumbers.clear();
            }
            else if (isValidNumber(n))
            {
                 if (m_topNumbers.contains(new Integer(n)) || forceClear)
                 {
                     m_topNumbers.remove(new Integer(n));
                 }
                 else
                 {
                     m_topNumbers.add(new Integer(n));
                     Collections.sort(m_topNumbers);
                 }
            }
            
            repaint();
        }
        
        if (mode == CellMode.BOTTOM && !m_isGiven)
        {
            if (n == 0)
            {
                m_bottomNumbers.clear();
            }
            else if (isValidNumber(n))
            {
                 if (m_bottomNumbers.contains(new Integer(n)) || forceClear)
                 {
                     m_bottomNumbers.remove(new Integer(n));
                 }
                 else
                 {
                     m_bottomNumbers.add(new Integer(n));
                     Collections.sort(m_bottomNumbers);
                 }
            }
        }
        
        if (mode == CellMode.CENTER && !m_isGiven)
        {
            if (n == 0 || isValidNumber(n))
            {
                if (m_centerNumber == n)
                    m_centerNumber = 0;
                else
                    m_centerNumber = n;
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
                setHighlighted(GlobalState.isSelecting);
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
        if (SwingUtilities.isRightMouseButton(arg0))
        {
            GlobalState.isDragging = true;
            GlobalState.isSelecting = !m_isHighlighted;
            toggleHighlighted(true);
        }
        
        if (SwingUtilities.isLeftMouseButton(arg0))
        {
            setHighlighted(true);
            GlobalState.fireEventRepaintRequest();
            GlobalState.isDragging = false;
            GlobalState.isSelecting = false;
            
            GlobalState.fireEventHighlightAllCells(false);
            setHighlighted(true);
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
    public void handleEventAboutButton()
    {
    }
    
    @Override
    public void handleEventImportButton()
    {
    }

    @Override
    public void handleEventResetButton()
    {
    }

    @Override
    public void handleEventReset()
    {
        m_isGiven = false;
        m_isHighlighted = false;
        m_centerNumber = 0;
        m_topNumbers.clear();
        m_bottomNumbers.clear();
        repaint();
    }
    
    @Override
    public void handleEventCellModeButton(CellMode newCellMode)
    {        
    }

    @Override
    public void handleEventClearTopButton()
    {
        if (m_isHighlighted)
            m_topNumbers.clear();
    }

    @Override
    public void handleEventClearBottomButton()
    {
        if (m_isHighlighted)
            m_bottomNumbers.clear();
    }

    @Override
    public void handleEventClearCenterButton()
    {
        if (m_isHighlighted && !m_isGiven)
            m_centerNumber = 0;
    }

    @Override
    public void handleEventClearAllButton()
    {
        if (m_isHighlighted)
        {
            m_topNumbers.clear();
            m_bottomNumbers.clear();
            
            if (!m_isGiven)
                m_centerNumber = 0;
        }
    }

    @Override
    public void handleEventClearTopBottomButton()
    {
        if (m_isHighlighted)
        {
            m_topNumbers.clear();
            m_bottomNumbers.clear();
        }
    }

    @Override
    public void handleEventCheckBoardButton()
    {
    }

    @Override
    public void handleEventControlNumberKeyTyped(int n)
    {
        // highlight this cell if any of the following is true:
        // number is set to non zero (any number)
        // 'n' is in the column
        // 'n' is in the row
        // 'n' is in the 3x3 grid
        
        boolean highlight = false;
        
        if (m_centerNumber != 0)
            highlight = true;
        
        for (int i = 0 ; i < 9 ; ++i)
        {
            if (GlobalState.cells[m_x][i].getCenterNumber() == n)
                highlight = true;
            if (GlobalState.cells[i][m_y].getCenterNumber() == n)
                highlight = true;
        }
        
        int boxStartX = (m_x / 3) * 3;
        int boxStartY = (m_y / 3) * 3;
        
        for (int x = boxStartX ; x < boxStartX + 3 ; ++x)
            for (int y = boxStartY ; y < boxStartY + 3 ; ++y)
                if (GlobalState.cells[x][y].getCenterNumber() == n)
                    highlight = true;
        
        setHighlighted(highlight);
    }

    @Override
    public void handleEventNumberKeyTyped(int n, boolean forceClear)
    {
        if (m_isHighlighted)
            setNumber(GlobalState.cellMode, n, forceClear);
    }

    @Override
    public void handleEventLetterKeyTyped(char c)
    {
    }

    @Override
    public void handleRepaintRequest()
    {
        repaint();
    }

    @Override
    public void handleImportCellValue(int x, int y, int n)
    {
        if (x == m_x && y == m_y)
            setNumber(CellMode.GIVEN, n, false);
    }

    @Override
    public void handleHighlightAllCells(boolean highlighted)
    {
        setHighlighted(highlighted);        
    }
}
