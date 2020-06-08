package com.ryanantkowiak.sudokuboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class SudokuBoardComponent extends JComponent implements KeyListener
{
    
    private static final long serialVersionUID = 1L;
    
    private SudokuBoardCell [][] m_cells = new SudokuBoardCell[9][9]; 
    private String m_importedText = "";
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);     
    }

    private void addCells()
    {
        for (int x = 0 ; x < 9 ; ++x)
        {
            for (int y = 0 ; y < 9 ; ++y)
            {
                SudokuBoardCell cell = new SudokuBoardCell(this, x, y);
                m_cells[x][y] = cell;
                add(cell);
            }
        }
    }
    
    public SudokuBoardComponent()
    {
        setLayout(null);
        setSize(SudokuWindowDimensions.getBoardDimension());
        setPreferredSize(SudokuWindowDimensions.getBoardDimension());
        setBackground(Color.BLACK);

        addCells();
        
        addKeyListener(this);
    }
    
    public void resetBoard()
    {
        for (int x = 0 ; x < 9 ; ++x)
            for (int y = 0 ; y < 9 ; ++y)
                m_cells[x][y].resetCell();
    }
    
    public void setHighlightAllCells(boolean highlighted)
    {
        for (int x = 0 ; x < 9 ; ++x)
            for (int y = 0 ; y < 9 ; ++y)
                m_cells[x][y].setHighlighted(highlighted);        
    }
    
    public void importBoard()
    {
        m_importedText = JOptionPane.showInputDialog("Import Sudoku Board:", m_importedText);
        
        if (m_importedText != null && !m_importedText.isEmpty())
        {
            resetBoard();
            
            List<Integer> input = new ArrayList<Integer>();
            
            for (char c : m_importedText.toCharArray())
            {
                int n = c - '0';
                
                if (n >= 0 && n <= 9)
                {
                    input.add(n);
                }
            }
            
            int inputIdx = 0;
            
            for (int y = 0 ; y < 9 ; ++y)
            {
                for (int x = 0 ; x < 9 ; ++x)
                {
                    if (inputIdx < input.size())
                        m_cells[x][y].importGiven(input.get(inputIdx));
                    ++inputIdx;
                    
                    if (inputIdx >= input.size())
                        break;
                }
                
                if (inputIdx >= input.size())
                    break;
            }
        }
    }

    public void keepFocus()
    {
        setFocusable(true);
        requestFocus();
    }
    
    @Override
    public void keyPressed(KeyEvent arg0)
    {
        keepFocus();        
    }

    @Override
    public void keyReleased(KeyEvent arg0)
    {
        keepFocus();
    }

    @Override
    public void keyTyped(KeyEvent arg0)
    {
        char c = arg0.getKeyChar();
        
        if (c >= 0 && c <= '9')
        {
            for (int x = 0 ; x < 9 ; ++x)
            {
                for (int y = 0 ; y < 9 ; ++y)
                {
                    if (m_cells[x][y].getHighlighted())
                    {
                        m_cells[x][y].setNumber(GlobalState.cellMode, c - '0'); // FIXME - Need to query for cell mode instead
                    }
                }
            }
            
            repaint();
        }

        else if (c == 'c' || c == ' ')
        {
            for (int x = 0 ; x < 9 ; ++x)
            {
                for (int y = 0 ; y < 9 ; ++y)
                {
                    if (m_cells[x][y].getHighlighted())
                    {
                        m_cells[x][y].setNumber(GlobalState.cellMode, 0); // FIXME - Need to query for cell mode instead
                    }
                }
            }
            
            repaint();
        }

        
        // TODO
        keepFocus();       
    }

}
