package com.example.loic.sudo_clue;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

/**
 * Created by loic on 01/06/2017.
 */

public class ViewActivity extends AppCompatActivity {

    int[][] initialGrid = new int[9][9];
    int[][] gridSolution = new int[9][9];
    int myInt = 0;
    boolean eraser = false;
    boolean clueMode = false;
    Button[][] listButtons = new Button[9][9];
    Button[] listInteractionButtons = new Button[4];
    Button[] listNumberButton = new Button[9];
    private String userViewChoice;
    boolean[][] badlyPlacedNumbered = new boolean[9][9];

    // We need a bundle to stock the value of initial Grid before the app crashes

    // Creating the animation
    protected ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            for (int recreateLines = 0; recreateLines < 9; recreateLines++) {
                for (int recreateColumns = 0; recreateColumns < 9; recreateColumns++) {
                    initialGrid[recreateLines][recreateColumns] = savedInstanceState.getInt("STATE_INITIAL_GRID" + recreateLines + recreateColumns);
                    gridSolution[recreateLines][recreateColumns] = savedInstanceState.getInt("STATE_GRID_SOLUTION" + recreateLines + recreateColumns);
                }
            }
        }
        // Definition of how many gaps you will need
        int column = 9;
        int row = 9;

        // We analyse first of all where the user just clicked and what view he has chosen
        userViewChoice = getIntent().getStringExtra("userViewChoice");

        if (userViewChoice.equals("Landscape")) {

            //View Initialization in the landscape mode
            setContentView(R.layout.landscape_view);
            GridLayout gridLayout = (GridLayout) findViewById(R.id.landscapeMainGridLayout);
            gridLayout.removeAllViews();
            GridLayout gridLayout2 = (GridLayout) findViewById(R.id.landscapeAuxiliaryGridLayout);
            gridLayout2.removeAllViews();

            // Grid Layout Initialization
            gridLayout.setColumnCount(column + 1); // column +1 for numbered buttons
            gridLayout.setRowCount(row);
            gridLayout2.setColumnCount(1);
            gridLayout2.setRowCount(row);

            // Button colored of the grid
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < (column + 2); j++) {

                    // We fill the grid
                    if (j < 9) {
                        addColoredButton(i, j, gridLayout);
                    }

                    // At the right of the sudoku grid we add some interaction button like erase or solve
                    else if (j == 10) {
                        addInteractionButton(i, gridLayout2);
                    }

                    // Between the interaction button and the grid we add numbered buttons to fill the grid
                    else {
                        addNumberButton(i, gridLayout);
                    }
                }
            }

        } else if (userViewChoice.equals("Portrait")) {

            //View Initialization in the portrait mode
            setContentView(R.layout.portrait_view);
            GridLayout gridLayout = (GridLayout) findViewById(R.id.portraitMainGridLayout);
            gridLayout.removeAllViews();
            GridLayout gridLayout2 = (GridLayout) findViewById(R.id.portraitAuxiliaryGridLayout);
            gridLayout2.removeAllViews();

            gridLayout.setColumnCount(column);
            gridLayout.setRowCount(row + 1); // 1 for the number bottom buttons

            // Button colored of the grid
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    addColoredButton(i, j, gridLayout);
                }
            }

            // Button in the bottom to fill the grid
            for (int j = 0; j < column; j++) {
                addNumberButton(j, gridLayout);
            }

            // Solver and Eraser
            for (int j = 0; j < 4; j++) {
                addInteractionButton(j, gridLayout2);
            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
        // Save the user's current game state
        for (int saveLines = 0; saveLines < 9; saveLines++) {
            for (int saveColumns = 0; saveColumns < 9; saveColumns++) {
                savedInstanceState.putInt("STATE_INITIAL_GRID" + saveLines + saveColumns, initialGrid[saveLines][saveColumns]);
                savedInstanceState.putInt("STATE_GRID_SOLUTION" + saveLines + saveColumns, gridSolution[saveLines][saveColumns]);
            }
        }
    }

    public void addColoredButton(int i, int j, GridLayout gridLayout) {
        GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        GridLayout.Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colSpan);
        Button button = new Button(this);
        button.setLayoutParams(gridParam);
        setColoredButtonShape(button, i, j);
        setColoredButton(button, i, j);
        gridLayout.addView(listButtons[i][j], gridParam);
    }

    public void setColoredButton(Button button, int i, int j) {
        final int x = i;
        final int y = j;
        // We recreate the previous button if it was saved
        if (initialGrid[i][j] != 0) {
            button.setText("" + initialGrid[i][j]);
        }
        // Set the action when you push a button
        listButtons[i][j] = button;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonSolveText = listInteractionButtons[1].getText().toString();
                // In the global case where badly placed number are hidden or we have already reached the SOLVING step
                if (!badlyPlacedNumbered[x][y]) {

                    // We normally erase if we still haven't solved the grid
                    if (eraser && (!buttonSolveText.equals(getString(R.string.clue))) ) {
                        listButtons[x][y].setText("");
                        initialGrid[x][y] = 0;
                        listButtons[x][y].setTextColor(Color.BLACK);

                    }
                    // We can't erase initial numbers from the grid especially when the solution is calculated from these numbers : no impact on the initial grid in this case
                    else if (eraser && (buttonSolveText.equals(getString(R.string.clue)))&& (initialGrid[x][y] == 0)){
                        listButtons[x][y].setText("");
                        listButtons[x][y].setTextColor(Color.BLACK);
                    }
                    else if (clueMode && (myInt == 0)) {
                        if ((gridSolution[x][y] != 0) && (initialGrid[x][y] == 0)) { // There is a solution and we will not "change" the initials numbers filled by users
                            listButtons[x][y].setText("" + gridSolution[x][y]);
                            listButtons[x][y].setTextColor(Color.RED);
                        }

                    } else if ((myInt != 0) && !buttonSolveText.equals(getString(R.string.clue))) {     // Initialization of the grid
                        listButtons[x][y].setText("" + myInt);
                        initialGrid[x][y] = myInt;
                        listButtons[x][y].setTextColor(Color.BLACK);
                    } else if ((myInt != 0) && (initialGrid[x][y] == 0) ){            // Users can fill normally even though they could have the solution
                        listButtons[x][y].setText("" + myInt);
                        listButtons[x][y].setTextColor(Color.parseColor("#707070")); // Gray to see the difference between initial grid and filling grid
                    }
                }
                // When the user can see the wrong placement of certain numbers
                else{
                    if (eraser) {
                        listButtons[x][y].setText("");
                        initialGrid[x][y] = 0;
                        listButtons[x][y].setTextColor(Color.BLACK);
                        changeColorBadlyFilled(initialGrid);
                    }
                    else if ((myInt != 0) && !buttonSolveText.equals(getString(R.string.clue))) {     // Initialization of the grid
                        listButtons[x][y].setText("" + myInt);
                        initialGrid[x][y] = myInt;
                        listButtons[x][y].setTextColor(Color.BLACK);
                        changeColorBadlyFilled(initialGrid);
                    }

                }
            }
        });
    }

    public void setColoredButtonShape(Button button, int i, int j) {

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(5);
        gd.setStroke(1, 0xFF000000);
        getAreaColored(i, j, gd);
        button.setBackgroundDrawable(gd);
        if (userViewChoice.equals("Landscape")) {
            setLandscapeButtonSizeRestricted(button);
        } else if (userViewChoice.equals("Portrait")) {
            setPortraitButtonSizeRestricted(button);
        }
    }

    public void addNumberButton(int j, GridLayout gridLayout) {
        final int z = j + 1;
        GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        GridLayout.Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colSpan);
        Button button = new Button(this);
        button.setText("" + (j + 1));
        button.setLayoutParams(gridParam);
        if (userViewChoice.equals("Landscape")) {
            setLandscapeButtonSizeRestricted(button);
        } else if (userViewChoice.equals("Portrait")) {
            setPortraitButtonSizeRestricted(button);
        }
        listNumberButton[j] = button;
        // Set Action On Click : if push, erase disabled and fill with the number which is indicated
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If push one of these you add a number even if you pushed previously eraser's button
                if (eraser) {
                    eraser = false;
                    clueMode = false;
                    myInt = z;
                    setButtonsDefaultBackground();
                    listNumberButton[(z - 1)].setBackgroundResource(R.drawable.button_pressed); // We have z-1 the index of the Number button z=j+1
                } else if (myInt == z) { // if the number is already selected we deselected it
                    setButtonsDefaultBackground();
                    myInt = 0;
                } else {
                    clueMode = false;
                    myInt = z;
                    setButtonsDefaultBackground();
                    listNumberButton[(z - 1)].setBackgroundResource(R.drawable.button_pressed); // We have z-1 the index of the Number button z=j+1
                }
            }
        });

        gridLayout.addView(listNumberButton[j], gridParam);
    }

    public void addInteractionButton(int j, GridLayout gridLayout) {
        final int z = j;
        GridLayout.Spec rowSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        GridLayout.Spec colSpan = GridLayout.spec(GridLayout.UNDEFINED, 1);
        GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan, colSpan);
        Button button = new Button(this);

        if (j < 4) {
            if (j == 0) {
                button.setText(R.string.erase);
                button.setLayoutParams(gridParam);

                //Set the actions of Eraser
                listInteractionButtons[j] = button;
                button.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  // If we already are in eraser mode : we disable this mode clicking on the button
                                                  if (eraser) {
                                                      setButtonsDefaultBackground();
                                                      eraser = false;
                                                  }
                                                  // else we disable all the others modes and we activate the eraser/rubber
                                                  else {
                                                      setButtonsDefaultBackground();
                                                      listInteractionButtons[z].setBackgroundResource(R.drawable.button_pressed);
                                                      eraser = true;
                                                      myInt = 0;
                                                      clueMode = false;
                                                  }
                                              }
                                          }
                );

                if (userViewChoice.equals("Landscape")) {
                    setLandscapeInteractionButtonSize(listInteractionButtons[j]);
                }

                gridLayout.addView(listInteractionButtons[j], gridParam);
            }

            if (j == 1) {
                // If we have backed up the solution
                if (gridSolution[0][0] != 0 ){
                    button.setText(R.string.clue);
                    button.setTextColor(Color.RED);
                    button.setLayoutParams(gridParam);
                    listInteractionButtons[j] = button;
                }
                // Else
                else {
                    button.setText(R.string.solve);
                    button.setLayoutParams(gridParam);
                    listInteractionButtons[j] = button;
                }
                button.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
//                    Put the calculation of the solution here
                                                  String buttonText = listInteractionButtons[z].getText().toString();
//                        We define here the solution button on CLUE
                                                  if ((buttonText.equals(getString(R.string.clue))) && !clueMode) { // We would like to have clues
                                                      clueMode = true;
                                                      eraser = false;
                                                      setButtonsDefaultBackground();
                                                      listInteractionButtons[z].setBackgroundResource(R.drawable.button_pressed);
                                                      myInt = 0;
                                                  } else if ((buttonText.equals(getString(R.string.clue))) && clueMode) { // We already are in clue mode. We disable this mode
                                                      setButtonsDefaultBackground();
                                                      clueMode = false;
                                                      myInt = 0;
                                                  } else if (countNumber(initialGrid) < 17) { // Not enough numbers are imputed
                                                      Context context = getApplicationContext();
                                                      CharSequence text = getString(R.string.solve_not_enough);
                                                      int duration = Toast.LENGTH_LONG;
                                                      Toast toast = Toast.makeText(context, text, duration);
                                                      toast.show();
                                                  } else if (testFillingGridTotal(initialGrid)) {
                                                      // We launch the process to solve the grid
                                                      launchAction(z);

                                                  } else {
                                                      Context context = getApplicationContext();
                                                      CharSequence text = getString(R.string.solve_error);
                                                      int duration = Toast.LENGTH_LONG;
                                                      Toast toast = Toast.makeText(context, text, duration);
                                                      toast.show();
                                                      changeColorBadlyFilled(initialGrid);
                                                  }
                                              }
                                          }
                );
                if (userViewChoice.equals("Landscape")) {
                    setLandscapeInteractionButtonSize(listInteractionButtons[j]);
                }
                gridLayout.addView(listInteractionButtons[j], gridParam);
            }

            if (j == 2) {
                button.setText(R.string.home);
                button.setLayoutParams(gridParam);
                listInteractionButtons[j] = button;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(ViewActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                if (userViewChoice.equals("Landscape")) {
                    setLandscapeInteractionButtonSize(listInteractionButtons[j]);
                }
                gridLayout.addView(listInteractionButtons[j], gridParam);

            }
            if (j == 3) {
                button.setText(R.string.reset);
                button.setLayoutParams(gridParam);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // We erase all the contents of the grid
                        for (int ii = 0; ii < 9; ii++) {
                            for (int jj = 0; jj < 9; jj++) {
                                listButtons[ii][jj].setText("");
                                initialGrid[ii][jj] = 0;
                                gridSolution[ii][jj] = 0;
                                badlyPlacedNumbered[ii][jj] = false;
                            }

                        }
                        listInteractionButtons[1].setText(R.string.solve); // We reset clue into solve : new grid so new solution
                        listInteractionButtons[1].setTextColor(Color.BLACK);
                        setButtonsDefaultBackground();
                        // Return to the beginning
                        clueMode = false;
                        eraser = false;
                        myInt = 0;
                    }
                });
                // Application of the modification to the view

                listInteractionButtons[j] = button;
                if (userViewChoice.equals("Landscape")) {
                    setLandscapeInteractionButtonSize(listInteractionButtons[j]);
                }

                gridLayout.addView(listInteractionButtons[j], gridParam);

            }
        }
    }

    public void getAreaColored(int i, int j, GradientDrawable gd) {

        //Three First Columns
        if ((i >= 0 && i < 3) && (j >= 0 && j < 3)) {
            gd.setColor(Color.WHITE);
        }
        if ((i >= 3 && i < 6) && (j >= 0 && j < 3)) {
            gd.setColor(Color.parseColor("#bebebe"));
        }
        if ((i >= 6 && i < 9) && (j >= 0 && j < 3)) {
            gd.setColor(Color.WHITE);
        }

        //Three Second Column
        if ((i >= 0 && i < 3) && (j >= 3 && j < 6)) {
            gd.setColor(Color.parseColor("#bebebe"));
        }
        if ((i >= 3 && i < 6) && (j >= 3 && j < 6)) {
            gd.setColor(Color.WHITE);
        }
        if ((i >= 6 && i < 9) && (j >= 3 && j < 6)) {
            gd.setColor(Color.parseColor("#bebebe"));
        }

        //Three Third Column
        if ((i >= 0 && i < 3) && (j >= 6 && j < 9)) {
            gd.setColor(Color.WHITE);
        }
        if ((i >= 3 && i < 6) && (j >= 6 && j < 9)) {
            gd.setColor(Color.parseColor("#bebebe"));
        }
        if ((i >= 6 && i < 9) && (j >= 6 && j < 9)) {
            gd.setColor(Color.WHITE);
        }
        //}
    }

    public boolean testFilling(int[][] grid, int value, int line, int column) {
        boolean linearTest = true;
        boolean columnTest = true;
        boolean areaTest = true;
        int lineLocation = line % 3;
        int columnLocation = column % 3;

        if (value == 0) {
            return true;
        } else {
            // Test on lines : if there is only once the value in the line.
            for (int i = 0; i < 9; i++) {
                if (i != line) {
                    linearTest = (grid[i][column] != value) && linearTest;
                }
            }
            // Test on columns : if there is only once the value in the column
            for (int j = 0; j < 9; j++) {
                if (j != column) {
                    columnTest = (grid[line][j] != value) && columnTest;
                }
            }

            // Test on a square area : if there is only once the value in the area

            for (int i = 0 - lineLocation; i < 3 - lineLocation; i++) {
                for (int j = 0 - columnLocation; j < 3 - columnLocation; j++) {
                    if (((i + line) != line) && ((j + column) != column)) {
                        areaTest = (grid[line + i][column + j] != value) && areaTest;
                    }
                }
            }
            return (linearTest && columnTest && areaTest);
        }
    }

    public boolean testFillingGridTotal(int[][] grid) {
        boolean totalTest = true;
        boolean testOnLineOnColumn;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                testOnLineOnColumn = testFilling(grid, grid[i][j], i, j);
                totalTest = testOnLineOnColumn && totalTest;
            }
        }
        return totalTest;
    }

    public void setButtonsDefaultBackground() {
        Button defaultButton = new Button(this);
        final Drawable defaultBackground = defaultButton.getBackground();

        for (int number = 0; number < 9; number++) {
            listNumberButton[number].setBackgroundDrawable(defaultBackground);
        }
        for (int interactionIndex = 0; interactionIndex < 4; interactionIndex++) {
            listInteractionButtons[interactionIndex].setBackgroundDrawable(defaultBackground);
        }
    }

    public int countNumber(int[][] grid) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] != 0) {
                    count += 1;
                }
            }
        }
        return count;
    }

    public void setLandscapeButtonSizeRestricted(Button button) {
        ViewGroup.LayoutParams params = button.getLayoutParams();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        params.width = screenWidth / 11;
        params.height = screenHeight / 11;
        button.setTextSize(screenHeight / 65); // With test we have this value /65 which well correspond with the screen size
        button.setLayoutParams(params);
    }

    // With this method interaction buttons will take vertical place in this view
    public void setLandscapeInteractionButtonSize(Button button) {
//        ViewGroup.LayoutParams params = button.getLayoutParams();
//        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
//        params.width = screenWidth / 9; // To fill correctly the screen of all devices we needed to test the values hence the /19
//        button.setTextSize(screenWidth / 88); // With test we have fixed /88
//        button.setLayoutParams(params);
    }

    // All the buttons need to occupy the screen space. Users won't need to scroll anymore
    public void setPortraitButtonSizeRestricted(Button button) {
        ViewGroup.LayoutParams params = button.getLayoutParams();
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        int screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        params.width = screenWidth / 9;
        params.height = screenHeight / 12;
        button.setLayoutParams(params);
    }

    public void changeColorBadlyFilled(int[][] grid) {
        boolean testOnLineOnColumn;
        // Numbers Color change when there is a mistake in their filling
        for (int badLine = 0; badLine < 9; badLine++) {
            for (int badColumn = 0; badColumn < 9; badColumn++) {
                testOnLineOnColumn = testFilling(grid, grid[badLine][badColumn], badLine, badColumn);
                if (!testOnLineOnColumn) {
                    badlyPlacedNumbered[badLine][badColumn] = true;
                }
                else {
                    badlyPlacedNumbered[badLine][badColumn] = false;
                }
                // We reset completely the text color of all buttons before show where is the problem
                listButtons[badLine][badColumn].setTextColor(Color.BLACK);
                // If there is a mistake we signal it
                // we will reset the list of badly Placed Number when there will not be badly placed anymore
                if (badlyPlacedNumbered[badLine][badColumn]) {
                    listButtons[badLine][badColumn].setTextColor(Color.parseColor("#cd661d"));
                }
            }
        }
    }

    public void launchAction(final int z) {

        mProgressDialog = ProgressDialog.show(this, getString(R.string.wait) , getString(R.string.action) , true);

        // Task Definition
        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                // starts the first long operation
                gridSolution = Grid.solve(initialGrid);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {

                mProgressDialog.dismiss();

                if (gridSolution[0][0] != 0) { // If the grid has been solved
                    Context contextSolve = getApplicationContext();
                    CharSequence textSolve = getString(R.string.solve_text);
                    int durationSolve = Toast.LENGTH_LONG;
                    Toast toastSolve = Toast.makeText(contextSolve, textSolve, durationSolve);
                    toastSolve.show();
                    listInteractionButtons[z].setText(R.string.clue);
                    listInteractionButtons[z].setTextColor(Color.RED);
                } else { // Else : the grid hasn't been solved because there isn't any solution
                    Context contextNoSolution = getApplicationContext();
                    CharSequence textNoSolution = getString(R.string.solve_no_text);
                    int durationNoSolution = Toast.LENGTH_LONG;
                    Toast toastNoSolution = Toast.makeText(contextNoSolution, textNoSolution, durationNoSolution);
                    toastNoSolution.show();
                }
            }

        };
        //Launch the thread
        task.execute((Void[]) null);

    }


}
