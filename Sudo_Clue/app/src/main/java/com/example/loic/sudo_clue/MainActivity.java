package com.example.loic.sudo_clue;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;


public class MainActivity extends AppCompatActivity {

    int myInt = 0;
    boolean eraser = false;
    Button[][] listButtons = new Button[9][9];
    int[][] matrice = new int[9][9];
    Button [] listLastButtons = new Button [2];

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);
        gridLayout.removeAllViews();


        int total = 81;
        int column = 9;
        int row = total / column;
        gridLayout.setColumnCount(column);
        gridLayout.setRowCount(row + 2); //row + 1 --> normal, row +2 with bottom buttons
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                final int x = i;
                final int y = j;
                GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
                GridLayout.Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
                GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colSpan);
                Button button = new Button(this);
                GradientDrawable gd = new GradientDrawable();
                gd.setCornerRadius(5);
                gd.setStroke(1, 0xFF000000);
                getAreaColored(i, j, gd);
                button.setBackgroundDrawable(gd);
                button.setLayoutParams(gridParam);
                matrice[i][j] = 0;
                // Set the action when you push a button
                listButtons[i][j] = button ;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (eraser){
                            listButtons[x][y].setText("");
                            matrice[x][y] = 0;
                        }
                        else {
                            if (myInt != 0) {
                                listButtons[x][y].setText("" + myInt);
                                matrice[x][y] = myInt;
                            }
                        }
                    }
                });
                button = listButtons[i][j];
                gridLayout.addView(button, gridParam);

            }
        }
        // Button in the bottom to fill the grid

        for (int j = 0; j < 9; j++) {
            final int z = j+1;
            GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
            GridLayout.Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colSpan);
            Button button = new Button(this);
            button.setText("" + (j+1));
            button.setLayoutParams(gridParam);
 // Set Action On Click : if push, erase disabled and fill with the number which is indicated
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myInt = z;
                    if (eraser){
                        eraser = false;
                    }
                }
            });

            gridLayout.addView(button, gridParam);
        }
        // Solver and Eraser
        for (int j = 0; j < 2; j++) {
            final int z= j ;
            GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
            GridLayout.Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
            GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colSpan);
            Button button = new Button(this);
            if (j == 0) {
                button.setText("Eraser");
                button.setLayoutParams(gridParam);

                //Set the actions of Eraser
                listLastButtons[j] = button;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         if (eraser){
                             eraser = false;
                             listLastButtons[z].setSelected(false);
                         }
                         else {
                             eraser = true;
                             listLastButtons[z].setSelected(true);
                         }
                      }
                  }
                );}

            else {
                button.setText("Solve");
                button.setLayoutParams(gridParam);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
    //                    Put the calculation of the solution here
                }
                }
            );}
            gridLayout.addView(button, gridParam);
        }
    }


    public void getAreaColored(int i, int j, GradientDrawable gd) {
        // Put color even if editText is empty
        //if(("").equals(gd.getText().toString())) {
            //Three First Columns
            if ((i >= 0 && i < 3) && (j >= 0 && j < 3)) {
                gd.setColor(Color.RED);
            }
            if ((i >= 3 && i < 6) && (j >= 0 && j < 3)) {
                gd.setColor(getResources().getColor(R.color.brown));
            }
            if ((i >= 6 && i < 9) && (j >= 0 && j < 3)) {
                gd.setColor(Color.BLUE);
            }

            //Three Second Column
            if ((i >= 0 && i < 3) && (j >= 3 && j < 6)) {
                gd.setColor(Color.CYAN);
            }
            if ((i >= 3 && i < 6) && (j >= 3 && j < 6)) {
                gd.setColor(Color.WHITE);
            }
            if ((i >= 6 && i < 9) && (j >= 3 && j < 6)) {
                gd.setColor(Color.YELLOW);
            }

            //Three Third Column
            if ((i >= 0 && i < 3) && (j >= 6 && j < 9)) {
                gd.setColor(Color.GREEN);
            }
            if ((i >= 3 && i < 6) && (j >= 6 && j < 9)) {
                gd.setColor(Color.GRAY);
            }
            if ((i >= 6 && i < 9) && (j >= 6 && j < 9)) {
                gd.setColor(Color.MAGENTA);
            }
        //}
    }


}
