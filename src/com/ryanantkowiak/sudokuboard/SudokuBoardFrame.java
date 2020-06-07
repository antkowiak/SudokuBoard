package com.ryanantkowiak.sudokuboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

public class SudokuBoardFrame extends JFrame
{
    private static final long serialVersionUID = 1L;

    private SudokuBoardComponent m_boardComponent;
    private SudokuControlComponent m_controlComponent;   
    
    private void Init()
    {       
        m_boardComponent = new SudokuBoardComponent();
        m_controlComponent = new SudokuControlComponent(m_boardComponent);
        
        setTitle("Sudoku Board");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(false);
        setLocationRelativeTo(null);

        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(m_boardComponent, BorderLayout.CENTER);
        getContentPane().add(m_controlComponent, BorderLayout.EAST);
        setResizable(false);
        pack();
        
        setVisible(true);        
    }
    
    public SudokuBoardFrame() throws HeadlessException
    {
        Init();
    }

    public SudokuBoardFrame(GraphicsConfiguration arg0)
    {
        super(arg0);
        Init();
    }

    public SudokuBoardFrame(String arg0) throws HeadlessException
    {
        super(arg0);
        Init();
    }

    public SudokuBoardFrame(String arg0, GraphicsConfiguration arg1)
    {
        super(arg0, arg1);
        Init();
    }
}
