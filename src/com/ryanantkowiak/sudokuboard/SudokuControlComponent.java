package com.ryanantkowiak.sudokuboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class SudokuControlComponent extends JComponent implements ActionListener, SudokuListener
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

    private void invertSelection()
    {
        for (int x = 0 ; x < 9 ; ++x)
            for (int y = 0 ; y < 9 ; ++y)
                GlobalState.cells[x][y].toggleHighlighted(false);
        repaint();
    }
    
    private void moveKeyPressed(char c)
    {
        Point p = new Point(GlobalState.lastHighlightedCell);
        
        switch(c)
        {
            case 'h': { p.x--; break;}
            case 'j': { p.y++; break;}
            case 'k': { p.y--; break;}
            case 'l': { p.x++; break;}
            default:  { return; }
        }
        
        if (p.x >= 0 && p.x < 9 && p.y >= 0 && p.y < 9)
        {
            for (int x = 0 ; x < 9 ; ++x)
                for (int y = 0 ; y < 9 ; ++y)
                    GlobalState.cells[x][y].setHighlighted(false);
            
            GlobalState.lastHighlightedCell = p;
            GlobalState.cells[p.x][p.y].setHighlighted(true);
            GlobalState.boardComponent.repaint();
        }    
    }
    
    private void moveAndSelectKeyPressed(char c)
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
            GlobalState.cells[p.x][p.y].setHighlighted(true);
            GlobalState.boardComponent.repaint();
        }    
    }
    
    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == m_BtnAbout)
            GlobalState.fireEventAboutButton();
        else if (event.getSource() == m_BtnImport)
            GlobalState.fireEventImportButton();
        else if (event.getSource() == m_BtnReset)
            GlobalState.fireEventResetButton();
        else if (event.getSource() == m_BtnModeGiven)
            GlobalState.fireEventCellModeButton(CellMode.GIVEN);
        else if (event.getSource() == m_BtnModeTop)
            GlobalState.fireEventCellModeButton(CellMode.TOP);
        else if (event.getSource() == m_BtnModeBottom)
            GlobalState.fireEventCellModeButton(CellMode.BOTTOM);
        else if (event.getSource() == m_BtnModeCenter)
            GlobalState.fireEventCellModeButton(CellMode.CENTER);
        else if (event.getSource() == m_BtnClearTop)
            GlobalState.fireEventClearTopButton();
        else if (event.getSource() == m_BtnClearBottom)
            GlobalState.fireEventClearBottomButton();
        else if (event.getSource() == m_BtnClearCenter)
            GlobalState.fireEventClearCenterButton();
        else if (event.getSource() == m_BtnClearAll)
            GlobalState.fireEventClearAllButton();
        else if (event.getSource() == m_BtnClearTopBottom)
            GlobalState.fireEventClearTopBottomButton();
        else if (event.getSource() == m_BtnCheckBoard)
            GlobalState.fireEventCheckBoardButton();
        
        GlobalState.fireEventRepaintRequest();
    }

    @Override
    public void handleEventAboutButton()
    {
    }
    
    @Override
    public void handleEventImportButton()
    {
        setCellMode(CellMode.CENTER);
    }

    @Override
    public void handleEventReset()
    {
        setCellMode(CellMode.GIVEN);
    }
    
    @Override
    public void handleEventResetButton()
    {
    }

    @Override
    public void handleEventCellModeButton(CellMode newCellMode)
    {
        setCellMode(newCellMode);
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

    @Override
    public void handleEventControlNumberKeyTyped(int n)
    {
    }
    
    @Override
    public void handleEventNumberKeyTyped(int n, boolean forceClear)
    {
        if (n == '0')
            m_BtnClearAll.doClick();
    }

    @Override
    public void handleEventLetterKeyTyped(char c)
    {
        char ch = Character.toUpperCase(c);
        
        if (ch == '?' || ch == '/')
            m_BtnAbout.doClick();
        else if (c == 'i')
            m_BtnImport.doClick();
        else if (c == 'I')
            invertSelection();
        else if (ch == '~')
            m_BtnReset.doClick();
        else if (ch == 'G')
            m_BtnModeGiven.doClick();
        else if (ch == 'T')
            m_BtnModeTop.doClick();
        else if (ch == 'B')
            m_BtnModeBottom.doClick();
        else if (ch == 'E')
            m_BtnModeCenter.doClick();
        else if (ch == '[')
            m_BtnClearTop.doClick();
        else if (ch == ']')
            m_BtnClearBottom.doClick();
        else if (ch == ' ')
             m_BtnClearAll.doClick();
        else if (ch == 'C')
            m_BtnClearAll.doClick();
        else if (ch == 'P')
            m_BtnClearTopBottom.doClick();
        else if (ch == '=' || ch == '+')
            m_BtnCheckBoard.doClick();
        else if (ch == 'N')
            m_ChkKnightConstraint.doClick();
        else if (ch == 'M')
            m_ChkKingConstraint.doClick();
        
        else if (c == 'h' || c == 'j' || c == 'k' || c == 'l')
            moveKeyPressed(c);
        else if (c == 'H' || c == 'J' || c == 'K' || c == 'L')
            moveAndSelectKeyPressed(c);        
    }

    @Override
    public void handleRepaintRequest()
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
