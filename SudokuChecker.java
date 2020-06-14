package com.ryanantkowiak.sudokuboard;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.ryanantkowiak.sudokuboard.sm.SudokuStateStack;

public class SudokuChecker
{

    public SudokuChecker()
    {
    }
    
    public static final List<Integer> NUMBERS = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
    
    public static final List<Point> KING_DELTAS = Arrays.asList(
            new Point(-1, -1),
            new Point(-1, 0),
            new Point(-1, 1),

            new Point(0, -1),
            new Point(0, 1),
            
            new Point(1, -1),
            new Point(1, 0),
            new Point(1, 0)
            );

    public static final List<Point> KNIGHT_DELTAS = Arrays.asList(
            new Point(-2, -1),
            new Point(-2, 1),
            
            new Point(-1, -2),
            new Point(-1, 2),
            
            new Point(1, -2),            
            new Point(1, 2),
            
            new Point(2, -1),
            new Point(2, 1)
            );

    private static boolean isValidCell(int x, int y)
    {
        return x >= 0 && x < 9 && y >= 0 && y < 9;
    }
    
    private static boolean checkColumns(SudokuBoardCell [][] cells)
    {
        // check columns
        for (int x = 0 ; x < 9 ; ++x)
        {
            List<Integer> numbers = new ArrayList<Integer>(NUMBERS);
            
            for (int y = 0 ; y < 9 ; ++y)
                numbers.remove(new Integer(SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y].centerNumber));
            
            if (!numbers.isEmpty())
                return false;
        }
        
        return true;
    }
    
    private static boolean checkRows(SudokuBoardCell [][] cells)
    {
        // check rows
        for (int y = 0 ; y < 9 ; ++y)
        {
            List<Integer> numbers = new ArrayList<Integer>(NUMBERS);
            
            for (int x = 0 ; x < 9 ; ++x)
                numbers.remove(new Integer(SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y].centerNumber));
            
            if (!numbers.isEmpty())
                return false;
        }
        
        return true;
    }
    
    private static boolean check3x3Boxes(SudokuBoardCell [][] cells)
    {
        // check 3x3 boxes
        for (int boxStartX = 0 ; boxStartX <= 6 ; boxStartX += 3)
        {
            for (int boxStartY = 0 ; boxStartY <= 6 ; boxStartY += 3)
            {
                List<Integer> numbers = new ArrayList<Integer>(NUMBERS);
                
                for (int x = boxStartX ; x < boxStartX + 3 ; ++x)
                    for (int y = boxStartY ; y < boxStartY + 3 ; ++y)
                        numbers.remove(new Integer(SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y].centerNumber));
                
                if (!numbers.isEmpty())
                    return false;
            }
        }
        
        return true;
    }
    
    private static boolean checkKnightConstraint(SudokuBoardCell [][] cells)
    {
        // check knight constraint
        for (int x = 0 ; x < 9 ; ++x)
        {
            for (int y = 0 ; y < 9 ; ++y)
            {                    
                for (Point p : KNIGHT_DELTAS)
                {
                    int checkX = x + p.x;
                    int checkY = y + p.y;
                    
                    if (isValidCell(checkX, checkY))
                    {
                        if (SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y].centerNumber == 
                                SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[checkX][checkY].centerNumber)
                            return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    private static boolean checkKingConstraint(SudokuBoardCell [][] cells)
    {
        // check king constraint
        for (int x = 0 ; x < 9 ; ++x)
        {
            for (int y = 0 ; y < 9 ; ++y)
            {                    
                for (Point p : KING_DELTAS)
                {
                    int checkX = x + p.x;
                    int checkY = y + p.y;
                    
                    if (isValidCell(checkX, checkY))
                    {
                        if (SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[x][y].centerNumber ==
                                SudokuStateStack.getInstance().getCurrentState().boardState.cellStates[checkX][checkY].centerNumber)
                            return false;
                    }
                }
            }
        }

        return true;
    }
    
    public static boolean checkBoard(SudokuBoardCell [][] cells, boolean knightConstraint, boolean kingConstraint)
    {
        if (cells == null)
            return false;
        
        if (!checkColumns(cells))
            return false;
        
        if (!checkRows(cells))
            return false;
        
        if (!check3x3Boxes(cells))
            return false;
               
        if (knightConstraint && !checkKnightConstraint(cells))
            return false;
            
        if (kingConstraint && !checkKingConstraint(cells))
            return false;
        
        return true;
    }

}
