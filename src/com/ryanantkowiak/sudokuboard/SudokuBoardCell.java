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

public class SudokuBoardCell extends JPanel implements MouseListener
{
    private static final long serialVersionUID = 1L;
    
    private SudokuBoardComponent m_boardComponent;
    
    private static Color GIVEN_COLOR = Color.BLACK;
    private static Color PLAYER_COLOR = Color.BLUE;
    private static Color TOP_COLOR = new Color(0, 80, 0);
    private static Color BOTTOM_COLOR = new Color(180, 0, 180);
    
    private static Font CENTER_FONT = new Font("Arial", Font.PLAIN, 42);
    private static Font TOP_FONT = new Font("Arial", Font.PLAIN, 12);
    private static Font BOTTOM_FONT = new Font("Arial", Font.PLAIN, 12);
    
    private static Color HL_BG_COLOR = new Color(255, 255, 150);
    private static Color NOT_HL_BG_COLOR = Color.WHITE;
    
    public void resetCell()
    {
        m_isGiven = false;
        m_isHighlighted = false;
        m_centerNumber = 0;
        m_topNumbers.clear();
        m_bottomNumbers.clear();
        repaint();
    }
    
    public void importGiven(int n)
    {
        if (isValidNumber(n))
        {
            m_isGiven = true;
            m_centerNumber = n;
        }
    }
    
    private static boolean isValidNumber(int n)
    {
        return n > 0 && n < 10;
    }
        
    private boolean m_isGiven = false;
    private boolean m_isHighlighted = false;
    private int m_centerNumber = 0;
    private List<Integer> m_topNumbers = new ArrayList<Integer>();
    private List<Integer> m_bottomNumbers = new ArrayList<Integer>();
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);     
        
        if (m_isHighlighted)
            setBackground(HL_BG_COLOR);
        else
            setBackground(NOT_HL_BG_COLOR);
            
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
    
    private String listToString(List<Integer> data)
    {
        String s = "";
        
        for (Integer i : data)
            s += i.toString();
        
        return s;
    }

    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font)
    {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = (rect.width - metrics.stringWidth(text)) / 2;
        int y = ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    private void drawTopString(Graphics g, String text, Rectangle rect, Font font)
    {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = 0;
        int y = metrics.getAscent();
        g.setFont(font);
        g.drawString(text, x, y);
    }

    private void drawBottomString(Graphics g, String text, Rectangle rect, Font font)
    {
        int x = 0;
        int y = rect.height - 2;
        g.setFont(font);
        g.drawString(text, x, y);
    }
    
    public SudokuBoardCell(SudokuBoardComponent boardComponent, int x, int y)
    {
        m_boardComponent = boardComponent;
        
        setSize(SudokuWindowDimensions.getCellDimension());
        setPreferredSize(SudokuWindowDimensions.getCellDimension());
        setBackground(NOT_HL_BG_COLOR);
        setLocation(SudokuWindowDimensions.getCellPosition(x, y));
        addMouseListener(this);
        setOpaque(true);
        setVisible(true);
    }

    public void toggleHighlighted()
    {
        m_isHighlighted = !m_isHighlighted;
        repaint();
    }
    
    public void setHighlighted(boolean highlighted)
    {
        m_isHighlighted = highlighted;
        repaint();
    }
    
    public boolean getHighlighted()
    {
        return m_isHighlighted;
    }
    
    public void setNumber(CellMode mode, int n)
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
                 if (m_topNumbers.contains(new Integer(n)))
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
                 if (m_bottomNumbers.contains(new Integer(n)))
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
        {
            if (GlobalState.isDragging)
            {
                setHighlighted(GlobalState.isSelecting);
            }
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
            toggleHighlighted();
        }
        
        if (SwingUtilities.isLeftMouseButton(arg0))
        {
            setHighlighted(true);
            repaint();
            m_boardComponent.repaint();
            GlobalState.isDragging = false;
            GlobalState.isSelecting = false;
            
            m_boardComponent.setHighlightAllCells(false);
            setHighlighted(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0)
    {
        GlobalState.isDragging = false;
    }
}
