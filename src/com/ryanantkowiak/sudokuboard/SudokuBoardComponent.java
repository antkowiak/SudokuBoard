package com.ryanantkowiak.sudokuboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

import com.ryanantkowiak.sudokuboard.sm.GlobalState;

public class SudokuBoardComponent extends JComponent implements KeyListener, SudokuListener
{
    
    private static final long serialVersionUID = 1L;
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);     
        setFocusable(true);
        requestFocus();
    }

    private void addCells()
    {
        for (int x = 0 ; x < 9 ; ++x)
        {
            for (int y = 0 ; y < 9 ; ++y)
            {
                SudokuBoardCell cell = new SudokuBoardCell(x, y);
                GlobalState.cells[x][y] = cell;
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
     
        GlobalState.addSudokuListener(this);
        addKeyListener(this);
    }
   
    @Override
    public void keyPressed(KeyEvent event)
    {
        GlobalState.controlComponent.keyPressed(event);
        repaint();
    }
    
    @Override
    public void keyReleased(KeyEvent event)
    {
        GlobalState.controlComponent.keyReleased(event);
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent event)
    {
        GlobalState.controlComponent.keyTyped(event);
        repaint();       
    }
    
    @Override
    public void handleEventNumberKeyTyped(int n, boolean forceClear)
    {
    }

    @Override
    public void handleImportCellValue(int x, int y, int n)
    {
    }

    @Override
    public void handleHighlightAllCells(boolean highlighted)
    {
    }

}
