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

public class SudokuControlComponent extends JComponent implements ActionListener
{
    private static final long serialVersionUID = 1L;
    private static final Color SELECTED_COLOR = new Color(144, 202, 249);
    private static final Color NOT_SELECTED_COLOR = new Color(220, 220, 220);
    
    private SudokuBoardComponent m_boardComponent;
    private CellMode m_currentCellMode = CellMode.GIVEN;
    
    // import/reset buttons
    private JButton m_BtnImport = new JButton("Import...");
    private JButton m_BtnReset = new JButton("Reset");
    
    // mode buttons
    private JButton m_BtnModeGiven = new JButton("Given Numbers");
    private JButton m_BtnModeTop = new JButton("Top");
    private JButton m_BtnModeBottom = new JButton("Bottom");
    private JButton m_BtnModeCenter = new JButton("Center");
    
    // clear buttons
    private JButton m_BtnClearTop = new JButton("Clear Top");
    private JButton m_BtnClearBottom = new JButton("Clear Bottom");
    private JButton m_BtnClearCenter = new JButton("Clear Center");
    private JButton m_BtnClearAll = new JButton("Clear All");
    private JButton m_BtnClearTopBottom = new JButton("Clear Top & Bottom");
    
    // check boxes
    private JButton m_BtnCheckBoard = new JButton("Check Board");
    private JCheckBox m_ChkKnightConstraint = new JCheckBox("Knight Constraint", false);
    private JCheckBox m_ChkKingConstraint = new JCheckBox("King Constraint", false);
    
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, SudokuWindowDimensions.getControlWidth(), SudokuWindowDimensions.getControlHeight());
    }
    
    public SudokuControlComponent(SudokuBoardComponent boardComponent)
    {
        m_boardComponent = boardComponent;
        
        setSize(SudokuWindowDimensions.getControlDimension());
        setPreferredSize(SudokuWindowDimensions.getControlDimension());
        setBackground(Color.WHITE);
        setForeground(Color.WHITE);

        setLayout(new GridLayout(20, 1));

        add(new JLabel());

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
    
    private void addListeners()
    {
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
    }
    
    private void setCellMode(CellMode cellMode)
    {
        m_currentCellMode = cellMode;
        
        m_BtnModeGiven.setBackground((cellMode == CellMode.GIVEN) ? SELECTED_COLOR : NOT_SELECTED_COLOR);
        m_BtnModeTop.setBackground((cellMode == CellMode.TOP) ? SELECTED_COLOR : NOT_SELECTED_COLOR);
        m_BtnModeBottom.setBackground((cellMode == CellMode.BOTTOM) ? SELECTED_COLOR : NOT_SELECTED_COLOR);
        m_BtnModeCenter.setBackground((cellMode == CellMode.CENTER) ? SELECTED_COLOR : NOT_SELECTED_COLOR);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == m_BtnImport)
            importBoard();
        else if (event.getSource() == m_BtnReset)
            resetBoard();
        else if (event.getSource() == m_BtnModeGiven)
            setCellMode(CellMode.GIVEN);
        else if (event.getSource() == m_BtnModeTop)
            setCellMode(CellMode.TOP);
        else if (event.getSource() == m_BtnModeBottom)
            setCellMode(CellMode.BOTTOM);
        else if (event.getSource() == m_BtnModeCenter)
            setCellMode(CellMode.CENTER);
    }
    
    private void importBoard()
    {
        m_boardComponent.importBoard();
        setCellMode(CellMode.CENTER);
    }
    
    private void resetBoard()
    {
        m_boardComponent.resetBoard();
        setCellMode(CellMode.GIVEN);
    }
    
    
}
