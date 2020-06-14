package com.ryanantkowiak.sudokuboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

import com.ryanantkowiak.sudokuboard.sm.GlobalState;

public class SudokuBoardFrame extends JFrame
{
    private static final long serialVersionUID = 1L;
    
    private void Init()
    {       
        GlobalState.boardComponent = new SudokuBoardComponent();
        GlobalState.controlComponent = new SudokuControlComponent();
        
        setTitle(GlobalState.APP_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(false);
        setLocationByPlatform(true);

        getContentPane().setBackground(Color.BLACK);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(GlobalState.boardComponent, BorderLayout.CENTER);
        getContentPane().add(GlobalState.controlComponent, BorderLayout.EAST);
        setResizable(false);
        pack();
        
        setVisible(true);
        GlobalState.fireEventRepaintRequest();
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
