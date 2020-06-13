package com.ryanantkowiak.sudokuboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class SudokuBoardComponent extends JComponent implements KeyListener, SudokuListener
{
    
    private static final long serialVersionUID = 1L;
    
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
    public void keyPressed(KeyEvent arg0)
    {
    }

    @Override
    public void keyReleased(KeyEvent arg0)
    {
    }

    @Override
    public void keyTyped(KeyEvent event)
    {
        char c = event.getKeyChar();
        
        if (c >= '0' && c <= '9')
            GlobalState.fireEventNumberKeyTyped(c - '0');
        else
            GlobalState.fireEventNonNumberKeyTyped(c);

        repaint();       
    }

    @Override
    public void handleEventAboutButton()
    {
        JOptionPane.showMessageDialog(this,
                "Written by Ryan Antkowiak" + "\n" +
                "antkowiak@gmail.com" + "\n" +
                "Version: " + GlobalState.APP_VERSION_NUMBER,
                "About " + GlobalState.APP_TITLE,
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void handleEventImportButton()
    {
        String importText = JOptionPane.showInputDialog(this, "Import Sudoku Board:", GlobalState.importText);
        
        if (importText != null && !importText.isEmpty())
        {            
            GlobalState.importText = importText;
            
            List<Integer> input = new ArrayList<Integer>();
            
            for (char c : GlobalState.importText.toCharArray())
            {
                int n = c - '0';
                
                if (n >= 0 && n <= 9)
                    input.add(n);
            }
            
            int inputIdx = 0;
            
            for (int y = 0 ; y < 9 ; ++y)
            {
                for (int x = 0 ; x < 9 ; ++x)
                {
                    if (inputIdx < input.size())
                        GlobalState.fireEventImportCellValue(x, y, input.get(inputIdx));
                    ++inputIdx;
                    
                    if (inputIdx >= input.size())
                        break;
                }
                
                if (inputIdx >= input.size())
                    break;
            }
        }
    }

    @Override
    public void handleEventResetButton()
    {
    }

    @Override
    public void handleEventCellModeButton(CellMode newCellMode)
    {
    }

    @Override
    public void handleEventClearTopButton()
    {
    }

    @Override
    public void handleEventClearBottomButton()
    {
    }

    @Override
    public void handleEventClearCenterButton()
    {
    }

    @Override
    public void handleEventClearAllButton()
    {
    }

    @Override
    public void handleEventClearTopBottomButton()
    {
    }

    @Override
    public void handleEventCheckBoardButton()
    {
    }

    @Override
    public void handleEventNumberKeyTyped(int n, boolean forceClear)
    {
    }

    @Override
    public void handleEventLetterKeyTyped(char c)
    {
    }

    @Override
    public void handleRepaintRequest()
    {
        repaint();
        setFocusable(true);
        requestFocus();
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
