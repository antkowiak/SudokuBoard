package com.ryanantkowiak.sudokuboard;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
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
    private JButton m_BtnClearTop = new JButton("Clear Top (O)");
    private JButton m_BtnClearBottom = new JButton("Clear Bottom (L)");
    private JButton m_BtnClearCenter = new JButton("Clear Center (0)");
    private JButton m_BtnClearAll = new JButton("Clear All (C)");
    private JButton m_BtnClearTopBottom = new JButton("Clear Top & Bottom (P)");
    
    // check boxes
    private JButton m_BtnCheckBoard = new JButton("Check Board (=)");
    private JCheckBox m_ChkKnightConstraint = new JCheckBox("Knight Constraint (N)", false);
    private JCheckBox m_ChkKingConstraint = new JCheckBox("King Constraint (K)", false);
        
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
    public void handleEventResetButton()
    {
        setCellMode(CellMode.GIVEN);
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
    public void handleEventNumberKeyTyped(int n, boolean forceClear)
    {
        if (n == '0')
            m_BtnClearAll.doClick();
    }

    @Override
    public void handleEventLetterKeyTyped(char c)
    {
        c = Character.toUpperCase(c);
        
        if (c == '?' || c == '/')
            m_BtnAbout.doClick();
        else if (c == 'I')
            m_BtnImport.doClick();
        else if (c == '~')
            m_BtnReset.doClick();
        else if (c == 'G')
            m_BtnModeGiven.doClick();
        else if (c == 'T')
            m_BtnModeTop.doClick();
        else if (c == 'B')
            m_BtnModeBottom.doClick();
        else if (c == 'E')
            m_BtnModeCenter.doClick();
        else if (c == 'O')
            m_BtnClearTop.doClick();
        else if (c == 'L')
            m_BtnClearBottom.doClick();
        else if (c == '0' || c == ' ')
             m_BtnClearAll.doClick();
        else if (c == 'C')
            m_BtnClearAll.doClick();
        else if (c == 'P')
            m_BtnClearTopBottom.doClick();
        else if (c == '=' || c == '+')
            m_BtnCheckBoard.doClick();
        else if (c == 'N')
            m_ChkKnightConstraint.doClick();
        else if (c == 'K')
            m_ChkKingConstraint.doClick();    
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
