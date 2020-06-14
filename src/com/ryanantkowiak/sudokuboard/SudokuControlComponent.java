package com.ryanantkowiak.sudokuboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.ryanantkowiak.sudokuboard.sm.CellMode;
import com.ryanantkowiak.sudokuboard.sm.CellState;
import com.ryanantkowiak.sudokuboard.sm.GlobalState;
import com.ryanantkowiak.sudokuboard.sm.SudokuStateStack;

public class SudokuControlComponent extends JComponent implements ActionListener, KeyListener, SudokuListener
{
    private static final long serialVersionUID = 1L;
    private static final Color SELECTED_COLOR = new Color(144, 202, 249);
    private static final Color NOT_SELECTED_COLOR = new Color(220, 220, 220);
        
    // import/reset buttons
    private JButton m_BtnAbout = new JButton("About (?)");
    private JButton m_BtnImport = new JButton("Import... (I)");
    private JButton m_BtnReset = new JButton("Reset (~)");
    
    // mode buttons
    private JButton m_BtnModeGiven = new JButton("Given Numbers (G)");
    private JButton m_BtnModeTop = new JButton("Top (T)");
    private JButton m_BtnModeBottom = new JButton("Bottom (B)");
    private JButton m_BtnModeCenter = new JButton("Center (E)");
    
    // clear buttons
    private JButton m_BtnClearTop = new JButton("Clear Top ([)");
    private JButton m_BtnClearBottom = new JButton("Clear Bottom (])");
    private JButton m_BtnClearCenter = new JButton("Clear Center (0)");
    private JButton m_BtnClearAll = new JButton("Clear All (C)");
    private JButton m_BtnClearTopBottom = new JButton("Clear Top & Bottom (P)");
    
    // check boxes
    private JButton m_BtnCheckBoard = new JButton("Check Board (=)");
    private JCheckBox m_ChkKnightConstraint = new JCheckBox("Knight Constraint (N)", false);
    private JCheckBox m_ChkKingConstraint = new JCheckBox("King Constraint (M)", false);
        
    public SudokuControlComponent()
    {
        GlobalState.addSudokuListener(this);
        
        setSize(SudokuWindowDimensions.getControlDimension());
        setPreferredSize(SudokuWindowDimensions.getControlDimension());
        setBackground(Color.WHITE);
        setForeground(Color.WHITE);

        setLayout(new GridLayout(20, 1));

        add(new JLabel());

        add(m_BtnAbout);
        add(m_BtnImport);
        add(m_BtnReset);
        
        add(new JLabel());
        
        add(m_BtnModeGiven);
        add(m_BtnModeTop);
        add(m_BtnModeBottom);
        add(m_BtnModeCenter);
        
        add(new JLabel());
        
        add (m_BtnClearTop);
        add (m_BtnClearBottom);
        add (m_BtnClearCenter);
        add (m_BtnClearAll);
        add (m_BtnClearTopBottom);
        
        add(new JLabel());
        
        add(m_BtnCheckBoard);
        add(m_ChkKnightConstraint);
        add(m_ChkKingConstraint);
        
        addListeners();
        
        setCellMode(CellMode.GIVEN);
        setOpaque(true);
        setVisible(true);
    }
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, SudokuWindowDimensions.getControlWidth(), SudokuWindowDimensions.getControlHeight());
    }
    
    private void addListeners()
    {
        m_BtnAbout.addActionListener(this);
        m_BtnImport.addActionListener(this);
        m_BtnReset.addActionListener(this);
        
        m_BtnModeGiven.addActionListener(this);
        m_BtnModeTop.addActionListener(this);
        m_BtnModeBottom.addActionListener(this);
        m_BtnModeCenter.addActionListener(this);
        
        m_BtnClearTop.addActionListener(this);
        m_BtnClearBottom.addActionListener(this);
        m_BtnClearCenter.addActionListener(this);
        m_BtnClearAll.addActionListener(this);
        m_BtnClearTopBottom.addActionListener(this);
        
        m_BtnCheckBoard.addActionListener(this);
        m_ChkKnightConstraint.addActionListener(this);
        m_ChkKingConstraint.addActionListener(this);
    }
    
    private void setCellMode(CellMode cellMode)
    {
        GlobalState.cellMode = cellMode;
        
        m_BtnModeGiven.setBackground((cellMode == CellMode.GIVEN) ? SELECTED_COLOR : NOT_SELECTED_COLOR);
        m_BtnModeTop.setBackground((cellMode == CellMode.TOP) ? SELECTED_COLOR : NOT_SELECTED_COLOR);
        m_BtnModeBottom.setBackground((cellMode == CellMode.BOTTOM) ? SELECTED_COLOR : NOT_SELECTED_COLOR);
        m_BtnModeCenter.setBackground((cellMode == CellMode.CENTER) ? SELECTED_COLOR : NOT_SELECTED_COLOR);
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == m_BtnAbout)
            handle_about();
        else if (event.getSource() == m_BtnImport)
            handle_import();
        else if (event.getSource() == m_BtnReset)
            handle_reset();
        else if (event.getSource() == m_BtnModeGiven)
            handle_cell_mode_button(CellMode.GIVEN);
        else if (event.getSource() == m_BtnModeTop)
            handle_cell_mode_button(CellMode.TOP);
        else if (event.getSource() == m_BtnModeBottom)
            handle_cell_mode_button(CellMode.BOTTOM);
        else if (event.getSource() == m_BtnModeCenter)
            handle_cell_mode_button(CellMode.CENTER);
        else if (event.getSource() == m_BtnClearTop)
            handle_clear_top();
        else if (event.getSource() == m_BtnClearBottom)
            handle_clear_bottom();
        else if (event.getSource() == m_BtnClearCenter)
            handle_clear_center();
        else if (event.getSource() == m_BtnClearAll)
            handle_clear_all();
        else if (event.getSource() == m_BtnClearTopBottom)
            handle_clear_top_bottom();
        else if (event.getSource() == m_BtnCheckBoard)
            handle_check_board();
    }   
    
    @Override
    public void keyPressed(KeyEvent event)
    {
        char c = event.getKeyChar();
        
        if (event.getKeyCode() == KeyEvent.VK_CONTROL)
            GlobalState.isControlKeyPressed = true;            


        // Don't know why, but for some reason, the '6' isn't returned from getKeyChar
        // when the CTRL button is pressed down. This should work around it...
        if (GlobalState.isControlKeyPressed && event.getKeyCode() == 54)
            c = '6';
        
        if (GlobalState.isControlKeyPressed && (c >= '1' && c <= '9'))
            handle_ctrl_and_number_key(c - '0');
    }

    @Override
    public void keyReleased(KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.VK_CONTROL)
            GlobalState.isControlKeyPressed = false;
    }

    @Override
    public void keyTyped(KeyEvent event)
    {
        char c = event.getKeyChar();
        
        if (!GlobalState.isControlKeyPressed && (c >= '0' && c <= '9'))
            GlobalState.fireEventNumberKeyTyped(c - '0');
        else
            handle_non_number_key(c);
    }
       
    @Override
    public void handleEventNumberKeyTyped(int n, boolean forceClear)
    {
        if (n == '0')
            m_BtnClearAll.doClick();
    }

    @Override
    public void handleImportCellValue(int x, int y, int n)
    {
    }

    @Override
    public void handleHighlightAllCells(boolean highlighted)
    {        
    }
    
    
    
    
    /////////////// NEW HANDLERS
    private void handle_about()
    {
        JOptionPane.showMessageDialog(GlobalState.boardComponent,
                "Written by Ryan Antkowiak" + "\n" +
                "antkowiak@gmail.com" + "\n" +
                "Version: " + GlobalState.APP_VERSION_NUMBER,
                "About " + GlobalState.APP_TITLE,
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void handle_import()
    {
        String importText = JOptionPane.showInputDialog(GlobalState.boardComponent, "Import Sudoku Board:", GlobalState.importText);
        
        if (importText != null && !importText.isEmpty())
        {   
            CellState [][] cellStates = SudokuStateStack.getInstance().getCurrentState().boardState.cellStates;
            
            for (int y = 0 ; y < 9 ; ++y)
                for (int x = 0 ; x < 9 ; ++x)
                    cellStates[x][y].reset();
            
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
            
            GlobalState.boardComponent.repaint();
            setCellMode(CellMode.CENTER);
        }        
    }
    
    private void handle_reset()
    {
        int choice = JOptionPane.showConfirmDialog(GlobalState.boardComponent,
                "Are you sure you want to reset the board?",
                "Reset Confirmation",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        
        if (choice == 0)
        {
            CellState [][] cellStates = SudokuStateStack.getInstance().getCurrentState().boardState.cellStates;
        
            for (int y = 0 ; y < 9 ; ++y)
                for (int x = 0 ; x < 9 ; ++x)
                    cellStates[x][y].reset();
        
            setCellMode(CellMode.GIVEN);
            GlobalState.boardComponent.repaint();
        }
    }
    
    private void handle_invert_selection()
    {
        for (int y = 0 ; y < 9 ; ++y)
            for (int x = 0 ; x < 9 ; ++x)
                SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y].toggleHighlighted();
        GlobalState.boardComponent.repaint();
    }
    
    private void handle_cell_mode_button(CellMode newCellMode)
    {
        setCellMode(newCellMode);
    }
    
    private void handle_clear_top()
    {
        for (int y = 0 ; y < 9 ; ++y)
        {
            for (int x = 0 ; x < 9 ; ++x)
            {
                CellState cellState = SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y];
                
                if (cellState.isHighlighted)
                    cellState.topNumbers.clear();
            }
        }
        
        GlobalState.boardComponent.repaint();
    }

    private void handle_clear_bottom()
    {
        for (int y = 0 ; y < 9 ; ++y)
        {
            for (int x = 0 ; x < 9 ; ++x)
            {
                CellState cellState = SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y];
                
                if (cellState.isHighlighted)
                    cellState.bottomNumbers.clear();
            }
        }
        
        GlobalState.boardComponent.repaint();
    }
    
    private void handle_clear_center()
    {
        for (int y = 0 ; y < 9 ; ++y)
        {
            for (int x = 0 ; x < 9 ; ++x)
            {
                CellState cellState = SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y];
                
                if (cellState.isHighlighted)
                    if (!cellState.isGiven)
                        cellState.centerNumber = 0;
            }
        }
        
        GlobalState.boardComponent.repaint();
    }

    private void handle_clear_all()
    {
        for (int y = 0 ; y < 9 ; ++y)
        {
            for (int x = 0 ; x < 9 ; ++x)
            {
                CellState cellState = SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y];
                
                if (cellState.isHighlighted)
                {
                    cellState.topNumbers.clear();
                    cellState.bottomNumbers.clear();
                    
                    if (!cellState.isGiven)
                        cellState.centerNumber = 0;
                }
            }
        }
        
        GlobalState.boardComponent.repaint();
    }

    private void handle_clear_top_bottom()
    {
        for (int y = 0 ; y < 9 ; ++y)
        {
            for (int x = 0 ; x < 9 ; ++x)
            {
                CellState cellState = SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y];
                
                if (cellState.isHighlighted)
                {
                    cellState.topNumbers.clear();
                    cellState.bottomNumbers.clear();                    
                }
            }
        }
        
        GlobalState.boardComponent.repaint();
    }

    private void handle_check_board()
    {
        boolean win = SudokuChecker.checkBoard(GlobalState.cells,
                m_ChkKnightConstraint.isSelected(),
                m_ChkKingConstraint.isSelected());

        if (win)
            JOptionPane.showMessageDialog(GlobalState.boardComponent,
                    "The Sudoku Board appears to be CORRECT!",
                    "Winner",
                    JOptionPane.INFORMATION_MESSAGE);
        else
            JOptionPane.showMessageDialog(GlobalState.boardComponent,
                    "There seems to be a problem with your solution...",
                    "Error",
                    JOptionPane.WARNING_MESSAGE);
    }
    
    private void handle_move_key(char c)
    {
        Point p = new Point(GlobalState.lastHighlightedCell);
        
        switch(c)
        {
            case 'h': { p.x--; break; }
            case 'j': { p.y++; break; }
            case 'k': { p.y--; break; }
            case 'l': { p.x++; break; }
            default:  { return; }
        }
        
        if (p.x >= 0 && p.x < 9 && p.y >= 0 && p.y < 9)
        {
            for (int x = 0 ; x < 9 ; ++x)
                for (int y = 0 ; y < 9 ; ++y)
                    SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y].setHighlighted(false);
            
            GlobalState.lastHighlightedCell = p;
            SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[p.x][p.y].setHighlighted(true);
            GlobalState.boardComponent.repaint();
        }
    }
    
    private void handle_move_and_select_key(char c)
    {
        Point p = new Point(GlobalState.lastHighlightedCell);
        
        switch(c)
        {
            case 'H': { p.x--; break;}
            case 'J': { p.y++; break;}
            case 'K': { p.y--; break;}
            case 'L': { p.x++; break;}
            default:  { return; }
        }
        
        if (p.x >= 0 && p.x < 9 && p.y >= 0 && p.y < 9)
        {            
            GlobalState.lastHighlightedCell = p;
            SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[p.x][p.y].setHighlighted(true);
            GlobalState.boardComponent.repaint();
        }    
    }

    private void handle_ctrl_and_number_key(int n)
    {
        for (int yPos = 0 ; yPos < 9 ; ++yPos)
        {
            for (int xPos = 0 ; xPos < 9 ; ++xPos)
            {
                CellState cellState = SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[xPos][yPos];
                
                boolean highlight = false;
                
                if (cellState.centerNumber != 0)
                    highlight = true;
                
                for (int i = 0 ; i < 9 ; ++i)
                {
                    if (SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[xPos][i].centerNumber == n)
                        highlight = true;
                    if (SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[i][yPos].centerNumber == n)
                        highlight = true;
                }
                
                int boxStartX = (xPos / 3) * 3;
                int boxStartY = (yPos / 3) * 3;
                
                for (int x = boxStartX ; x < boxStartX + 3 ; ++x)
                    for (int y = boxStartY ; y < boxStartY + 3 ; ++y)
                        if (SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y].centerNumber == n)
                            highlight = true;
                
                cellState.setHighlighted(highlight);
                repaint();

            }
        }
        
        GlobalState.boardComponent.repaint();
    }
    
    private void handle_non_number_key(char c)
    {       
        if (c == '?' || c == '/')
            m_BtnAbout.doClick();
        else if (c == 'i')
            m_BtnImport.doClick();
        else if (c == 'I')
            handle_invert_selection();
        else if (c == '~')
            m_BtnReset.doClick();
        else if (c == 'G' || c == 'g')
            m_BtnModeGiven.doClick();
        else if (c == 'T' || c == 't')
            m_BtnModeTop.doClick();
        else if (c == 'B' || c == 'b')
            m_BtnModeBottom.doClick();
        else if (c == 'E' || c == 'e')
            m_BtnModeCenter.doClick();
        else if (c == '[')
            m_BtnClearTop.doClick();
        else if (c == ']')
            m_BtnClearBottom.doClick();
        else if (c == ' ')
             m_BtnClearAll.doClick();
        else if (c == 'C' || c == 'c')
            m_BtnClearAll.doClick();
        else if (c == 'P' || c == 'p')
            m_BtnClearTopBottom.doClick();
        else if (c == '=' || c == '+')
            m_BtnCheckBoard.doClick();
        else if (c == 'N' || c == 'n')
            m_ChkKnightConstraint.doClick();
        else if (c == 'M' || c == 'm')
            m_ChkKingConstraint.doClick();
        else if (c == 'h' || c == 'j' || c == 'k' || c == 'l')
            handle_move_key(c);
        else if (c == 'H' || c == 'J' || c == 'K' || c == 'L')
            handle_move_and_select_key(c);        
    }

    
}
