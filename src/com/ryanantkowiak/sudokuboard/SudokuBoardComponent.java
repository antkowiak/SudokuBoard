package com.ryanantkowiak.sudokuboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;

import com.ryanantkowiak.sudokuboard.sm.GlobalState;
import com.ryanantkowiak.sudokuboard.sm.SudokuStateStack;

public class SudokuBoardComponent extends JComponent implements KeyListener
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
    
    public void highlightAllCells(boolean highlighted)
    {
        for (int y = 0 ; y < 9 ; ++y)
        {
            for (int x = 0 ; x < 9 ; ++x)
            {
                SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y].isHighlighted = highlighted;
            }
        }
    }

}
