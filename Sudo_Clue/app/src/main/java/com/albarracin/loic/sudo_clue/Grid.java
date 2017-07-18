package com.albarracin.loic.sudo_clue;

//Definition :
//A domain is a line, a column or a region of the grid

//Importing libraries
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Integer;
//import java.lang.*;
//Creating the Grid class

/**
 * Created by loic albarracin on 15/05/2017.
 */

public abstract class Grid{
    //Method which returns the index of the "value" which is in the "line" (will only be used while we know that "value" is in "line")
    private static int getIndex(int[] line, int value){
        int cursor = 0;
        boolean stop = false;
        while (cursor != line.length && stop == false){
            if (line[cursor] == value){
                stop = true;
            }
            else{
                cursor += 1;
            }
        }
        return cursor;
    }

    //Method which returns the number of occurrences of "value" in "list"
    private static int number(int[] list, int value) {
        int counter = 0;
        for (int testValue : list){
            if (testValue==value){
                counter += 1;
            }
        }
        return(counter);
    }

    private static int numberMatrix(ArrayList<ArrayList<Integer>> list, int value) {
        int counter = 0;
        for (int i=0;i<list.size();i++){
            if(list.get(i).size()!=0){
                for (int j=0;j<list.get(i).size();j++){
                    if (list.get(i).get(j) == value){
                        counter += 1;
                    }
                }
            }
        }
        return(counter);
    }

    //Method which transforms an int[][] grid into an ArrayList<ArrayList<Integer>> grid
    private static ArrayList<ArrayList<Integer>> changeType(int[][] grid){
        ArrayList<ArrayList<Integer>> aGrid = new ArrayList<ArrayList<Integer>>();
        for(int a=0;a<9;a++){
            ArrayList<Integer> lineA = new ArrayList<Integer>(Arrays.asList(grid[a][0],grid[a][1],grid[a][2],grid[a][3],grid[a][4],grid[a][5],grid[a][6],grid[a][7],grid[a][8]));
            aGrid.add(lineA);
        }
        return aGrid;
    }

    //Method which transforms an int[] grid into an ArrayList<Integer> grid
    private static ArrayList<Integer> changeTypeForLines(int[] list){
        ArrayList<Integer> aList = new ArrayList<Integer>();
        for(int a=0;a<list.length;a++){
            aList.add(list[a]);
        }
        return aList;
    }

    //Method which transforms an ArrayList<ArrayList<Integer>> into an int[][]
    private static int[][] changeTypeReturn(ArrayList<ArrayList<Integer>> aGrid){
        int[][] grid = new int[9][9];
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                grid[i][j] = aGrid.get(i).get(j);
            }
        }
        return grid;
    }

    private static int[] changeTypeReturnForLines(ArrayList<Integer> aList){
        int[] list = new int[9];
        for(int i=0;i<9;i++){
            list[i] = aList.get(i);
        }
        return list;
    }

    //Method which selects index' of Position and reduces the associate values to 0.
    private static int[] removePositionIndex(String howToSolve, int[][] grid, int[] position, int shift){
        if (howToSolve == "onColumns"){
            position[0+shift] = 0;
            position[3+shift] = 0;
            position[6+shift] = 0;
        }
        else if(howToSolve =="onLines"){
            position[0+3*shift] = 0;
            position[1+3*shift] = 0;
            position[2+3*shift] = 0;
        }
        else if(howToSolve == "onAnyLine"){
            ArrayList<ArrayList<Integer>> aGrid = changeType(grid);
            for (int z=0;z<9;z++){
                if ((aGrid.get(z)).contains(shift)){
                    position[z] = 0;
                }
            }
        }
        return position;
    }


    //Method which returns the grid with a trivial number we just founded
    private static int[][] fillGridWithATrivialNumberForRegion(int[][] grid, int[] position, int trivialNumber, int shiftLine, int shiftColumn){
        int p = getIndex(position,1);
        if(p == 0){
            grid[0+3*shiftLine][0+3*shiftColumn] = trivialNumber;
        }
        else if(p == 1){
            grid[0+3*shiftLine][1+3*shiftColumn] = trivialNumber;
        }
        else if(p == 2){
            grid[0+3*shiftLine][2+3*shiftColumn] = trivialNumber;
        }
        else if(p == 3){
            grid[1+3*shiftLine][0+3*shiftColumn] = trivialNumber;
        }
        else if(p == 4){
            grid[1+3*shiftLine][1+3*shiftColumn] = trivialNumber;
        }
        else if(p == 5){
            grid[1+3*shiftLine][2+3*shiftColumn] = trivialNumber;
        }
        else if(p == 6){
            grid[2+3*shiftLine][0+3*shiftColumn] = trivialNumber;
        }
        else if(p == 7){
            grid[2+3*shiftLine][1+3*shiftColumn] = trivialNumber;
        }
        else{
            grid[2+3*shiftLine][2+3*shiftColumn] = trivialNumber;
        }
        return grid;
    }

    //Method which returns the grid with a trivial number we just founded
    private static int[][] fillGridWithATrivialNumberForColumns(int[][] grid, int[] position, int trivialNumber, int shift){
        int p = getIndex(position,1);
        if(p == 0){
            grid[0][shift] = trivialNumber;
        }
        else if(p == 1){
            grid[1][shift] = trivialNumber;
        }
        else if(p == 2){
            grid[2][shift] = trivialNumber;
        }
        else if(p == 3){
            grid[3][shift] = trivialNumber;
        }
        else if(p == 4){
            grid[4][shift] = trivialNumber;
        }
        else if(p == 5){
            grid[5][shift] = trivialNumber;
        }
        else if(p == 6){
            grid[6][shift] = trivialNumber;
        }
        else if(p == 7){
            grid[7][shift] = trivialNumber;
        }
        else{
            grid[8][shift] = trivialNumber;
        }
        return grid;
    }

    //Method which returns the i_th column of the grid
    private static ArrayList<Integer> column(int[][] grid, int i){
        ArrayList<Integer> col = new ArrayList<Integer>(Arrays.asList(grid[0][i-1],grid[1][i-1],grid[2][i-1],grid[3][i-1],grid[4][i-1],grid[5][i-1],grid[6][i-1],grid[7][i-1],grid[8][i-1]));
        return col;
    }

    //Method which returns the i_th region of the grid
    private static ArrayList<Integer> region(int[][] grid, int i){
        int quo = (i-1)/3;
        int rest = 3*(i-1-3*quo);
        ArrayList<Integer> reg = new ArrayList<Integer>(Arrays.asList(grid[3*quo][rest],grid[3*quo][rest+1],grid[3*quo][rest+2],grid[3*quo+1][rest],grid[3*quo+1][rest+1],grid[3*quo+1][rest+2],grid[3*quo+2][rest],grid[3*quo+2][rest+1],grid[3*quo+2][rest+2]));
        return reg;
    }

    //Method which returns the i_th "region" of a 9x9 matrix of possibilities
    private static ArrayList<ArrayList<Integer>> getRegion(ArrayList<ArrayList<ArrayList<Integer>>> totalPossibilities, int i){
        int quo = (i-1)/3;
        int rest = 3*(i-1-3*quo);
        ArrayList<ArrayList<Integer>> reg = new ArrayList<ArrayList<Integer>>(Arrays.asList(totalPossibilities.get(3*quo).get(rest),totalPossibilities.get(3*quo).get(rest+1),totalPossibilities.get(3*quo).get(rest+2),totalPossibilities.get(3*quo+1).get(rest),totalPossibilities.get(3*quo+1).get(rest+1),totalPossibilities.get(3*quo+1).get(rest+2),totalPossibilities.get(3*quo+2).get(rest),totalPossibilities.get(3*quo+2).get(rest+1),totalPossibilities.get(3*quo+2).get(rest+2)));
        return reg;
    }

    //Method which returns the values of a polynomial function (useful for semiRegion method)
    private static int polynomial1(int x){
        int p1 = (x-2)*(x-3)/2;
        return p1; //p1(1) = 1, p1(2) = 0, p1(3) = 0
    }

    //Method which returns the values of a polynomial function (useful for semiRegion method)
    private static int polynomial2(int x){
        int p2 = (-x*x+3*x)/2+1;
        return p2; //p2(1) = 2, p2(2) = 2, p2(3) = 1
    }

    //Method which creates the colums, but without certain cells (i represents the number of the region)
    private static ArrayList<ArrayList<Integer>> semiColumn(int[][] grid, int i){
        ArrayList<ArrayList<Integer>> set = new ArrayList<ArrayList<Integer>>();
        int j = 3*(i-1)-9*((i-1)/3);
        for (int m=j;m<(j+3);m++){
            if(i<=3){
                ArrayList<Integer> cjk = new ArrayList<Integer>(Arrays.asList(grid[3][m], grid[4][m], grid[5][m], grid[6][m], grid[7][m], grid[8][m]));
                set.add(cjk);
            }
            else if(i<=6){//then i in {4,5,6}
                ArrayList<Integer> cjk = new ArrayList<Integer>(Arrays.asList(grid[0][m], grid[1][m], grid[2][m], grid[6][m], grid[7][m], grid[8][m]));
                set.add(cjk);
            }
            else{//i in {7,8,9}
                ArrayList<Integer> cjk = new ArrayList<Integer>(Arrays.asList(grid[0][m], grid[1][m], grid[2][m], grid[3][m], grid[4][m], grid[5][m]));
                set.add(cjk);
            }
        }
        return set;
    }

    //Method which creates the regions, but without certains celles (i represents the number of the column)
    private static ArrayList<ArrayList<Integer>> semiRegion(int[][] grid, int i){
        ArrayList<ArrayList<Integer>> set = new ArrayList<ArrayList<Integer>>();
        for (int m=0;m<3;m++){
            if(i<=3){
                int p = polynomial1(i);
                int q = polynomial2(i);
                ArrayList<Integer> rjk = new ArrayList<Integer>(Arrays.asList(grid[3*m][p], grid[3*m][q], grid[3*m+1][p], grid[3*m+1][q], grid[3*m+2][p], grid[3*m+2][q]));
                set.add(rjk);
            }
            else if(i<=6){//then i in {4,5,6}
                int p = polynomial1(i-3)+3;
                int q = polynomial2(i-3)+3;
                ArrayList<Integer> rjk = new ArrayList<Integer>(Arrays.asList(grid[3*m][p], grid[3*m][q], grid[3*m+1][p], grid[3*m+1][q], grid[3*m+2][p], grid[3*m+2][q]));
                set.add(rjk);
            }
            else{//i in {7,8,9}
                int p = polynomial1(i-6)+6;
                int q = polynomial2(i-6)+6;
                ArrayList<Integer> rjk = new ArrayList<Integer>(Arrays.asList(grid[3*m][p], grid[3*m][q], grid[3*m+1][p], grid[3*m+1][q], grid[3*m+2][p], grid[3*m+2][q]));
                set.add(rjk);
            }
        }
        return set;
    }

    //Method which search trivial numbers on regions
    private static int[][] trivialNumbersOnRegions(int[][] grid, int switchK, int nbOfRegion){
        int quo = (nbOfRegion-1)/3;
        int rest = nbOfRegion-3*quo-1;
        while (switchK == 1){//Let us find the trivial numbers in the selected region
            switchK = 0;
            for (int i=1;i<=9;i++){
                ArrayList<Integer> regionNum = region(grid, nbOfRegion);
                if (!(regionNum.contains(i))){
                    int position[] = {1,1,1,1,1,1,1,1,1};   //This list allows the detection of potential positions of trivial numbers
                    for (int u=0;u<9;u++){
                        if (regionNum.get(u) != 0){
                            position[u] = 0; //Cells that already have a number can not receive additional numbers
                        }
                    }
                    ArrayList<ArrayList<Integer>> semiColumnSet = semiColumn(grid, nbOfRegion);
                    for (int p=0;p<=2;p++){
                        if ((semiColumnSet.get(p)).contains(i)){
                            position = removePositionIndex("onColumns", grid, position, p);
                        }
                    }
                    ArrayList<ArrayList<Integer>> aGrid = changeType(grid);
                    int partGrid = 3*((nbOfRegion-1)/3);//partGrid = (1,2,3) if nbOfRegion in ({1,2,3},{4,5,6},{7,8,9})
                    for(int p=0;p<=2;p++){
                        if((aGrid.get(partGrid+p)).contains(i)){
                            position = removePositionIndex("onLines", grid, position, p);
                        }
                    }
                    if (number(position,1) == 1){//If a number has only one possible cell, it is placed in
                        grid = fillGridWithATrivialNumberForRegion(grid, position, i, quo, rest);
                        switchK = 1;
                    }
                }
            }
        }
        return grid;
    }

    //Method which search trivial numbers on columns
    private static int[][] trivialNumbersOnColumns(int[][] grid, int switchK, int nbOfColumn){
        while (switchK == 1){//Let us find the trivial numbers in columns 1
            switchK = 0;
            for (int i=1;i<=9;i++){
                ArrayList<Integer> columnNum = column(grid, nbOfColumn);
                if (!(columnNum.contains(i))){
                    int position[] = {1,1,1,1,1,1,1,1,1};   //This list allows the detection of potential positions of trivial numbers
                    for (int u=0;u<9;u++){
                        if (columnNum.get(u) != 0){
                            position[u] = 0; //Cells that already have a number can not receive additional numbers
                        }
                    }
                    ArrayList<ArrayList<Integer>> semiRegionSet = semiRegion(grid, nbOfColumn);
                    for (int p=0;p<=2;p++){
                        if ((semiRegionSet.get(p)).contains(i)){
                            position = removePositionIndex("onLines", grid, position, p);
                        }
                    }
                    position = removePositionIndex("onAnyLine", grid, position,i);
                    if (number(position,1) == 1){
                        grid = fillGridWithATrivialNumberForColumns(grid, position, i, nbOfColumn-1);
                        switchK = 1;
                    }
                }
            }
        }
        return grid;
    }

    //Method which returns true if two ArrayLists<Integer> are equal
    private static boolean areEqualFirstOrder(ArrayList<Integer> list1, ArrayList<Integer> list2){
        if (list1.size() != list2.size()){
            return false;
        }
        for(int i=0;i<list1.size();i++){
            if (list1.get(i) != list2.get(i)){
                return false;
            }
        }
        return true;
    }

    //Method which returns true if two ArrayLists<ArrayLists<Integer>> are equal
    private static boolean areEqualSecondOrder(ArrayList<ArrayList<Integer>> grid1, ArrayList<ArrayList<Integer>> grid2){
        if(grid1.size() != grid2.size()){
            return false;
        }
        for(int i=0;i<grid1.size();i++){
            for(int j=0;j<grid1.get(i).size();j++){
                if(grid1.get(i).get(j) != grid2.get(i).get(j)){
                    return false;
                }
            }
        }
        return true;
    }

    //Method which places the trivial numbers into the grid
    private static int[][] trivialNumbers(int[][] grid){
        int j = 1;  //Initialization of the main loop fill sensor
        //The variable j allows the "dynamic" search for trivial numbers, presented in the project report
        while (j == 1){  //Loops of Filling trivial numbers
            j = 0;
            for (int nbOfRegion=1;nbOfRegion<=9;nbOfRegion++){
                ArrayList<ArrayList<Integer>> memoryGrid = changeType(grid);
                grid = trivialNumbersOnRegions(grid, 1, nbOfRegion);
                if (!(areEqualSecondOrder(memoryGrid,changeType(grid)))){
                    j = 1;
                }
            }
            for (int nbOfColumn=1;nbOfColumn<=9;nbOfColumn++){
                ArrayList<ArrayList<Integer>> memoryGrid = changeType(grid);
                grid = trivialNumbersOnColumns(grid, 1, nbOfColumn);
                if (!(areEqualSecondOrder(memoryGrid,changeType(grid)))){
                    j = 1;
                }
            }

            //Lines that only have one empty cell, get the missed trivial number
            for(int nbOfLine=0;nbOfLine<9;nbOfLine++){//We affect this idea to each line of the grid
                if (number(grid[nbOfLine],0) == 1){ //If only one cell is empty...
                    int indX = getIndex(grid[nbOfLine],0); //...the algorithm searches the exact position of this cell...
                    int c = 1;//c if the value of the number ('c' like 'chiffre')
                    ArrayList<ArrayList<Integer>> aGrid = changeType(grid);
                    while ((aGrid.get(nbOfLine)).contains(c)){ //...and then search the value of the number which has to be in that cell
                        c += 1;
                    }
                    grid[nbOfLine][indX] = c; //Once found, this value is put in the empty cell concerned
                    j = 1;
                }
            }

        }
        return grid;
    }

    //Method which returns n! if the input is the integer n >=0
    private static int recursiveFactorial(int n){
        if (n>1){
            return(n*recursiveFactorial(n-1));
        }
        else{
            return 1;
        }
    }

    //Method which returns the maximum of tests we have to do to find the solution of the grid, resolving on the lines
    private static float ratioR(int[][] grid){
        int p = 1;
        int n = 0;
        float ratio = 1;
        ArrayList<Integer> maxTestLine = new ArrayList<Integer>();
        ArrayList<Integer> maxTestColumn = new ArrayList<Integer>();
        int[][] tGrid = transposeGrid(grid);
        for (int i=0;i<9;i++){
            n = number(grid[i],0);
            maxTestLine.add(recursiveFactorial(n));
            n = number(tGrid[i],0);
            maxTestColumn.add(recursiveFactorial(n));
        }
        for(int i=0;i<9;i++){
            ratio *= maxTestLine.get(i);
            ratio /= maxTestColumn.get(i);
        }
        return ratio;
    }

    private static int minNbFilledCellsOnLines(int[][] grid){
        int emptyCellsOnALine = 0;
        for (int i=0;i<9;i++){
            if (number(grid[i],0) > emptyCellsOnALine){
                emptyCellsOnALine = number(grid[i],0);
            }
        }
        return(9-emptyCellsOnALine);//The returned value corresponds to the number of filled cells on the line of the grid which has the least
    }

    //Method which takes a grid (matrix 9x9) as input and returns its transpose matrix
    private static int[][] transposeGrid(int[][] gridToTranspose){
        int height = gridToTranspose.length;
        int width = gridToTranspose[0].length;
        int[][] transposedGrid = new int[height][width];
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                transposedGrid[i][j] = gridToTranspose[j][i];
            }
        }
        return transposedGrid;
    }

    //Method "cluesOfBlank" which returns the list of index of values '0' in a line
    private static ArrayList<Integer> cluesOfBlank(int[] line){
        ArrayList<Integer> clues = new ArrayList<Integer>();
        for (int i=0;i<9;i++){
            if (line[i] == 0){
                clues.add(i);
            }
        }
        return(clues);
    }

    //Method "fillingLine" which allows the ... of a line of the grid, with permutation #k of the list "permutation"
    private static int[] fillingLine(int[] line, ArrayList<Integer> blankClue, ArrayList<ArrayList<Integer>> permutation, int k){
        int j = 0;
        for (int i : blankClue){
            line[i]=(permutation.get(k)).get(j);
            j += 1;
        }
        return line;
    }

    //Method which returns the list of {1,...,9} without the elements of "line"
    private static ArrayList<Integer> theOtherNumbers(ArrayList<Integer> line){
        ArrayList<Integer> nbs = new ArrayList<Integer>();
        for (int i=1;i<=9;i++){
            if(!line.contains(i)){
                nbs.add(i);
            }
        }
        return nbs;
    }

    //Method which create the permutations list. Those permutations are based on the "list" set
    private static ArrayList<ArrayList<Integer>> generatePerm(ArrayList<Integer> list) {
        if (list.size() == 0) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
            result.add(new ArrayList<Integer>());
            return result;
        }
        Integer firstElement = list.remove(0);
        ArrayList<ArrayList<Integer>> returnValue = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<Integer>> permutations = generatePerm(list);
        for (ArrayList<Integer> smallerPermutated : permutations) {
            for (int index=0; index <= smallerPermutated.size(); index++) {
                ArrayList<Integer> temp = new ArrayList<Integer>(smallerPermutated);
                temp.add(index, firstElement);
                returnValue.add(temp);
            }
        }
        return returnValue;
    }

    private static ArrayList<ArrayList<Integer>> permutationReduction(int[][] grid, int nbLine, ArrayList<ArrayList<Integer>> permutation, ArrayList<Integer> regionSt, ArrayList<Integer> regionNd, ArrayList<Integer> regionRd, ArrayList<Integer> column1j, ArrayList<Integer> column2j, ArrayList<Integer> column3j, ArrayList<Integer> column4j, ArrayList<Integer> column5j, ArrayList<Integer> column6j, ArrayList<Integer> column7j, ArrayList<Integer> column8j, ArrayList<Integer> column9j){
        int k = 0;//Sensor used as an index in any permutation of the List of permutations
        if(grid[nbLine-1][0] == 0){
            int j = 0;
            while (j != permutation.size()){
                if (regionSt.contains((permutation.get(j)).get(k)) || column1j.contains((permutation.get(j)).get(k))){
                    permutation.remove(j);
                }
                else {
                    j += 1;
                }
            }
            k += 1;
        }
        if(grid[nbLine-1][1] == 0){
            int j = 0;
            while (j != permutation.size()){
                if (regionSt.contains((permutation.get(j)).get(k)) || column2j.contains((permutation.get(j)).get(k))){
                    permutation.remove(j);
                }
                else {
                    j += 1;
                }
            }
            k += 1;
        }
        if(grid[nbLine-1][2] == 0){
            int j = 0;
            while (j != permutation.size()){
                if (regionSt.contains((permutation.get(j)).get(k)) || column3j.contains((permutation.get(j)).get(k))){
                    permutation.remove(j);
                }
                else {
                    j += 1;
                }
            }
            k += 1;
        }
        if(grid[nbLine-1][3] == 0){
            int j = 0;
            while (j != permutation.size()){
                if (regionNd.contains((permutation.get(j)).get(k)) || column4j.contains((permutation.get(j)).get(k))){
                    permutation.remove(j);
                }
                else {
                    j += 1;
                }
            }
            k += 1;
        }
        if(grid[nbLine-1][4] == 0){
            int j = 0;
            while (j != permutation.size()){
                if (regionNd.contains((permutation.get(j)).get(k)) || column5j.contains((permutation.get(j)).get(k))){
                    permutation.remove(j);
                }
                else {
                    j += 1;
                }
            }
            k += 1;
        }
        if(grid[nbLine-1][5] == 0){
            int j = 0;
            while (j != permutation.size()){
                if (regionNd.contains((permutation.get(j)).get(k)) || column6j.contains((permutation.get(j)).get(k))){
                    permutation.remove(j);
                }
                else {
                    j += 1;
                }
            }
            k += 1;
        }
        if(grid[nbLine-1][6] == 0){
            int j = 0;
            while (j != permutation.size()){
                if (regionRd.contains((permutation.get(j)).get(k)) || column7j.contains((permutation.get(j)).get(k))){
                    permutation.remove(j);
                }
                else {
                    j += 1;
                }
            }
            k += 1;
        }
        if(grid[nbLine-1][7] == 0){
            int j = 0;
            while (j != permutation.size()){
                if (regionRd.contains((permutation.get(j)).get(k)) || column8j.contains((permutation.get(j)).get(k))){
                    permutation.remove(j);
                }
                else {
                    j += 1;
                }
            }
            k += 1;
        }
        if(grid[nbLine-1][8] == 0){
            int j = 0;
            while (j != permutation.size()){
                if (regionRd.contains((permutation.get(j)).get(k)) || column9j.contains((permutation.get(j)).get(k))){
                    permutation.remove(j);
                }
                else {
                    j += 1;
                }
            }
            k += 1;
        }
        return permutation;
    }

    //Method which removes all de '0' of 'line'
    private static ArrayList<Integer> reduction(ArrayList<Integer> line){
        int i = 0;
        while(i < line.size()){
            if (line.get(i) != 0){
                i += 1;
            }
            else{
                line.remove(i);
            }
        }
        return line;
    }

    //Method which tests if a sensor-tuple is the solution, but it test only the n-th first positions (because of the backtracking effect)
    //It returns a couple of int variables : first the sensor (increased by one or not) and the value of the Switch
    private static int[] sensorMove(int[][] grid, int sensor, int switchK, int lineNb, ArrayList<Integer> list1, ArrayList<Integer> list2, ArrayList<Integer> list3, ArrayList<Integer> list4, ArrayList<Integer> list5, ArrayList<Integer> list6, ArrayList<Integer> list7, ArrayList<Integer> list8, ArrayList<Integer> list9){
        if (list1.contains(grid[lineNb][0])){
            sensor += 1;
            switchK = 1;
        }
        else if(list2.contains(grid[lineNb][1])){
            sensor += 1;
            switchK = 1;
        }
        else if(list3.contains(grid[lineNb][2])){
            sensor += 1;
            switchK = 1;
        }
        else if(list4.contains(grid[lineNb][3])){
            sensor += 1;
            switchK = 1;
        }
        else if(list5.contains(grid[lineNb][4])){
            sensor += 1;
            switchK = 1;
        }
        else if(list6.contains(grid[lineNb][5])){
            sensor += 1;
            switchK = 1;
        }
        else if(list7.contains(grid[lineNb][6])){
            sensor += 1;
            switchK = 1;
        }
        else if(list8.contains(grid[lineNb][7])){
            sensor += 1;
            switchK = 1;
        }
        else if(list9.contains(grid[lineNb][8])){
            sensor += 1;
            switchK = 1;
        }
        int newPosition[] = {sensor,switchK};
        return(newPosition);
    }

    //Method which returns the lines in the grid which have no numbers
    private static ArrayList<Integer> blankLines(int[][] grid){
        ArrayList<Integer> positionOfBlankLines = new ArrayList<Integer>();
        for(int line=0;line<9;line++){
            if (number(grid[line], 0) == 9){
                positionOfBlankLines.add(line);
            }
        }
        return positionOfBlankLines;
    }

    //Method which returns the number of the region in which a case is
    private static int caseInRegion(int line, int column){
        for(int i=0;i<3;i++){
            if(line<=3*(i+1)){
                for(int j=0;j<3;j++){
                    if(column<=3*(j+1)){
                        return(3*i+j+1);
                    }
                }
            }
        }
        return 0;
    }

    //Method which returns the numbers that could be placed in a case
    private static ArrayList<Integer> possibilitiesInACase(int[][] grid, int line, int column){
        ArrayList<Integer> presentNumbers = new ArrayList<Integer>();
        if (grid[line-1][column-1] != 0){
            return presentNumbers;//list without any number
        }
        for(int i=0;i<9;i++){
            if(grid[i][column-1]!=0 && !presentNumbers.contains(grid[i][column-1])){
                presentNumbers.add(grid[i][column-1]);
            }
        }
        for(int j=0;j<9;j++){
            if(grid[line-1][j]!=0 && !presentNumbers.contains(grid[line-1][j])){
                presentNumbers.add(grid[line-1][j]);
            }
        }
        ArrayList<Integer> regionConcerned = region(grid,caseInRegion(line,column));
        for(int k=0;k<9;k++){
            if(regionConcerned.get(k)!=0 && !presentNumbers.contains(regionConcerned.get(k))){
                presentNumbers.add(regionConcerned.get(k));
            }
        }
        return theOtherNumbers(presentNumbers);
    }

    //Method which merges, without any repetition, two lists
    private static ArrayList<Integer> mergeLists(ArrayList<Integer> list1, ArrayList<Integer> list2){
        ArrayList<Integer> mergeList = new ArrayList<Integer>();
        for (int i=0;i<list1.size();i++){
            if(!mergeList.contains(list1.get(i))){
                mergeList.add(list1.get(i));
            }
        }
        for (int j=0;j<list2.size();j++){
            if(!mergeList.contains(list2.get(j))){
                mergeList.add(list2.get(j));
            }
        }
        return mergeList;
    }

    //Method which returns true if a number can be placed in a region
    private static boolean regionViolatesTheRules(int[][] grid, int reg){
        ArrayList<Integer> regionConcerned = region(grid, reg);
        ArrayList<Integer> mergeL = new ArrayList<Integer>();
        int initialLine   = 3*((reg-1)/3);
        int initialColumn = 3*(reg-1)-9*((reg-1)/3);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(regionConcerned.get(3*i+j) == 0){
                    ArrayList<Integer> possib = possibilitiesInACase(grid, initialLine+1+i, initialColumn+1+j);
                    mergeL = mergeLists(mergeL,possib);
                }
            }
        }//After those loops, if the grid is Solvent, we should have in mergeL all the numbers from 1 to 9, except for the numbers that are already placed in the region
        for(int p : regionConcerned){
            if(p != 0){
                mergeL.add(p);
            }
        }
        if(mergeL.size() == 9){
            return false;
        }
        return true;
    }

    //Method which returns a column of an ArrayList<ArralyList<ArrayList<Integer>>>
    private static ArrayList<ArrayList<Integer>> getColumn(ArrayList<ArrayList<ArrayList<Integer>>> totalPossibilities, int column){
        ArrayList<ArrayList<Integer>> columnPossibilities = new ArrayList<ArrayList<Integer>>();
        for(int line=0;line<9;line++){
            columnPossibilities.add(totalPossibilities.get(line).get(column));
        }

        return columnPossibilities;
    }

    //Method which returns the first twin Numbers founded
    private static ArrayList<Integer> twinNumbers(int[][] grid){
        //The list which will be returned, will contain : {First of the twin numbers, Second of the twin numbers, First case line, First case column, Second case line, Second case column}
        //Creation of the totalPossibilities list
        ArrayList<ArrayList<ArrayList<Integer>>> totalPossibilities = new ArrayList<ArrayList<ArrayList<Integer>>>();
        for (int line=1; line<=9;line++){
            ArrayList<ArrayList<Integer>> linePossibilities = new ArrayList<ArrayList<Integer>>();
            for(int column=1;column<=9;column++){
                linePossibilities.add(possibilitiesInACase(grid, line, column));
            }
            totalPossibilities.add(linePossibilities);
        } //The list has been filled
        //if two numbers can only be placed in 2 cells of a domain.
        //On a region
        for (int region=1;region<=9;region++){
            ArrayList<ArrayList<Integer>> possibilitiesInARegion = getRegion(totalPossibilities,region);
            int quo = (region-1)/3;
            int rest = 3*(region-1-3*quo);
            ArrayList<Integer> thisRegion = new ArrayList<Integer>(Arrays.asList(grid[3*quo][rest],grid[3*quo][rest+1],grid[3*quo][rest+2],grid[3*quo+1][rest],grid[3*quo+1][rest+1],grid[3*quo+1][rest+2],grid[3*quo+2][rest],grid[3*quo+2][rest+1],grid[3*quo+2][rest+2]));
            for (int i=1;i<9;i++){
                if(!(thisRegion.contains(i)) && numberMatrix(possibilitiesInARegion,i)==2) {
                    for (int j=i+1;j<=9;j++){
                        if(!(thisRegion.contains(j)) && numberMatrix(possibilitiesInARegion,j)==2){
                            for (int position1=0;position1<8;position1++){
                                if (possibilitiesInARegion.get(position1).contains(i) && possibilitiesInARegion.get(position1).contains(j)){
                                    for (int position2=position1+1;position2<9;position2++){
                                        if (possibilitiesInARegion.get(position2).contains(i) && possibilitiesInARegion.get(position2).contains(j)){
                                            ArrayList<Integer> twin = new ArrayList<Integer>(Arrays.asList(i,j,3*quo+position1/3,rest+position1-3*(position1/3),3*quo+position2/3,rest+position2-3*(position2/3) ));
                                            return twin;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //On a column
        for (int column=0;column<9;column++){
            ArrayList<ArrayList<Integer>> possibilitiesInAColumn = getColumn(totalPossibilities,column);
            ArrayList<Integer> thisColumn = new ArrayList<Integer>(Arrays.asList(grid[0][column],grid[1][column],grid[2][column],grid[3][column],grid[4][column],grid[5][column],grid[6][column],grid[7][column],grid[8][column]));
            for (int i=1;i<9;i++){
                if(!(thisColumn.contains(i)) && numberMatrix(possibilitiesInAColumn,i)==2) {
                    for (int j=i+1;j<=9;j++){
                        if(!(thisColumn.contains(j)) && numberMatrix(possibilitiesInAColumn,j)==2){
                            for (int line1=0;line1<8;line1++){
                                if (possibilitiesInAColumn.get(line1).contains(i) && possibilitiesInAColumn.get(line1).contains(j)){
                                    for (int line2=line1+1;line2<9;line2++){
                                        if (possibilitiesInAColumn.get(line2).contains(i) && possibilitiesInAColumn.get(line2).contains(j)){
                                            ArrayList<Integer> twin = new ArrayList<Integer>(Arrays.asList(i,j,line1,column,line2,column));
                                            return twin;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //On a line
        for (int line=0;line<9;line++){
            ArrayList<ArrayList<Integer>> possibilitiesInALine = totalPossibilities.get(line);
            ArrayList<Integer> thisLine = changeTypeForLines(grid[line]);
            for (int i=1;i<9;i++){
                if(!(thisLine.contains(i)) && numberMatrix(possibilitiesInALine,i)==2) {
                    for (int j=i+1;j<=9;j++){
                        if(!(thisLine.contains(j)) && numberMatrix(possibilitiesInALine,j)==2){
                            for (int column1=0;column1<8;column1++){
                                if (possibilitiesInALine.get(column1).contains(i) && possibilitiesInALine.get(column1).contains(j)){
                                    for (int column2=column1+1;column2<9;column2++){
                                        if (possibilitiesInALine.get(column2).contains(i) && possibilitiesInALine.get(column2).contains(j)){
                                            ArrayList<Integer> twin = new ArrayList<Integer>(Arrays.asList(i,j,line,column1,line,column2));
                                            return twin;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        //if no other possibilities in 2 cells
        //On a line
        for (int line=0;line<9;line++){
            ArrayList<ArrayList<Integer>> possibilitiesInALine = totalPossibilities.get(line);
            for (int column=0;column<8;column++){
                if (possibilitiesInALine.get(column).size()==2){
                    ArrayList<Integer> testTwins = possibilitiesInALine.get(column);

                    for (int lastTests=column+1;lastTests<9;lastTests++){
                        if (areEqualFirstOrder(possibilitiesInALine.get(lastTests),testTwins)){
                            ArrayList<Integer> twin = new ArrayList<Integer>(Arrays.asList(testTwins.get(0),testTwins.get(1),line,column,line,lastTests));
                            return twin;
                        }
                    }
                }
            }
        }
        //On a column
        for (int column=0;column<9;column++){
            ArrayList<ArrayList<Integer>> possibilitiesInAColumn = getColumn(totalPossibilities,column);
            for (int line=0;line<8;line++){
                if (possibilitiesInAColumn.get(line).size()==2){
                    ArrayList<Integer> testTwins = possibilitiesInAColumn.get(line);
                    for (int lastTests=line+1;lastTests<9;lastTests++){
                        if (areEqualFirstOrder(possibilitiesInAColumn.get(lastTests),testTwins)){
                            ArrayList<Integer> twin = new ArrayList<Integer>(Arrays.asList(testTwins.get(0),testTwins.get(1),line,column,lastTests, column));
                            return twin;
                        }
                    }
                }
            }
        }
        //On a region
        for(int region=1;region<=9;region++){
            ArrayList<ArrayList<Integer>> possibilitiesInARegion = getRegion(totalPossibilities,region);
            for (int indX=0;indX<8;indX++){
                if (possibilitiesInARegion.get(indX).size() == 2){
                    ArrayList<Integer> testTwins = possibilitiesInARegion.get(indX);
                    for (int lastTests=indX+1;lastTests<9;lastTests++){
                        if (areEqualFirstOrder(possibilitiesInARegion.get(lastTests),testTwins)){
                            ArrayList<Integer> twin = new ArrayList<Integer>(Arrays.asList(testTwins.get(0),testTwins.get(1),3*((region-1)/3)+(indX/3),3*(region-1-3*((region-1)/3))+indX-3*(indX/3),3*((region-1)/3)+(lastTests/3),3*(region-1-3*((region-1)/3))+lastTests-3*(lastTests/3)));
                            return twin;
                        }
                    }
                }
            }
        }



        ArrayList<Integer> twin = new ArrayList<Integer>(Arrays.asList(10,10,10,10,10,10));//Default value given, if there aren't any twinNumbers
        return twin;
    }

    //Method which test if the Grid could be resolved
    private static boolean isSolvent(int[][] grid){
        for (int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                if(grid[i][j] != 0){
                    for(int k=1;k<=9;k++){//With hypothesis, 2 or more numbers can be in the same line, column or region
                        if(number(grid[i], k)>1 || number(transposeGrid(grid)[j],k)>1 || number(changeTypeReturnForLines(region(grid,caseInRegion(i,j))),k)>1){
                            return false;
                        }
                    }
                }
                else{//if(grid[i][j]==0)
                    if(possibilitiesInACase(grid, i+1, j+1).size()==0){
                        return false;
                    }
                    int reg = caseInRegion(i+1,j+1);
                    if(regionViolatesTheRules(grid,reg)){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //Method which returns the index of the cell which has possibilitiesInCase that is minimal, comparing to the other cells in the same line
    private static int minPossibilitiesIndex(int[][] grid, int line){
        int mini = 10;
        int idX = 10;
        for(int col=1;col<=9;col++){
            if(possibilitiesInACase(grid,line,col).size()<=mini){
                mini = possibilitiesInACase(grid,line,col).size();
                idX = col;
            }
        }
        return idX;
    }

    //Method which returns the grid with hypothesis on blank lines, to reduce the time of resolution
    private static int[][] fillGridWithHypothesis(int[][] grid, int[] hyp){
        //How many blank lines are there in the grid ?
        ArrayList<Integer> positionBlankLines = blankLines(grid);
        for(int line=0;line<9;line++){
            if (positionBlankLines.contains(line)){
                grid[line][minPossibilitiesIndex(grid,line+1)-1] = possibilitiesInACase(grid,line+1,minPossibilitiesIndex(grid,line+1)).get(hyp[line]-1);
            }
        }
        return grid;
    }

    //Method which returns the minimum of two int values
    private static int min(int a, int b){
        if(a<b){
            return a;
        }
        return b;
    }

    //Method "solve" of Sudo-clue application
    //Variables a1, ..., a9 make it possible to define which permutation of which line should be treated at a certain point of time. Solve the grid is equivalent to find the right 9-tuple (a1, a2, ..., a9)
    public static int[][] solve(int[][] initialGrid){

        int[][] grid = new int[9][9];

        //Creation of a memory of the grid
        ArrayList<ArrayList<Integer>> superMemoryGrid = changeType(initialGrid); //To recover grid using superMemoryGrid, write "grid = changeTypeReturn(superMemoryGrid);"
        grid = changeTypeReturn(superMemoryGrid);
        //Calculus of the trivial numbers
        grid = trivialNumbers(grid);

        //Is the grid transposed ?
        boolean transposition = false;

        //Memory of the Initial grid (with trivial numbers)
        ArrayList<ArrayList<Integer>> memGrid = changeType(grid);
        //System.out.println(memGrid);
        //Program that searches and put twin Numbers into the grid, dynamically, before using the Backtracking
        ArrayList<ArrayList<Integer>> twins = new ArrayList<ArrayList<Integer>>(Arrays.asList(twinNumbers(grid)));
        int lastElement = 0;
        ArrayList<ArrayList<ArrayList<Integer>>> grids = new ArrayList<ArrayList<ArrayList<Integer>>>(Arrays.asList(memGrid));
        int lastGrid = 0;
        ArrayList<Integer> twinHyps = new ArrayList<Integer>(Arrays.asList(0));
        int lastTwinHyp = 0;
        while (twinHyps.size()!=0){
            grid = changeTypeReturn(grids.get(lastGrid));
            if(twins.get(lastElement).get(0)!=10 && lastTwinHyp<1){
                grid[twins.get(lastElement).get(2)][twins.get(lastElement).get(3)] = twins.get(lastElement).get(twinHyps.get(lastTwinHyp));
                grid[twins.get(lastElement).get(4)][twins.get(lastElement).get(5)] = twins.get(lastElement).get(twinHyps.get(lastTwinHyp)+1-2*((twinHyps.get(lastTwinHyp)+1)/2));
                grid = trivialNumbers(grid);
                twins.add(twinNumbers(grid));
                lastElement += 1;
                grids.add(changeType(grid));
                lastGrid += 1;
                twinHyps.add(0);
                lastTwinHyp += 1;
            }
            else{
                //Shall we transpose the grid ?
                int minLine = minNbFilledCellsOnLines(grid);
                int minColumn = minNbFilledCellsOnLines(transposeGrid(grid));
                float ratioTest = ratioR(grid);
                if (ratioTest < 1){ //We don't transpose, except...
                    if (minLine < minColumn && minLine < 2){    //...if a line has at most one digit and all columns have at least 2
                        transposition = true;
                        grid = transposeGrid(grid);
                    }
                    else{
                        transposition = false;
                    }
                }
                else if (ratioTest > 1){    //We transpose, except...
                    if (!(minLine > minColumn && minColumn < 2)){ //...if a column has at most one digit and all lines have at least 2
                        transposition = true;
                        grid = transposeGrid(grid);
                    }
                    else{
                        transposition = false;
                    }
                }
                else{
                    if (minLine < minColumn){
                        transposition = true;
                        grid = transposeGrid(grid);
                    }
                    else{
                        transposition = false;
                    }
                }
                //Now, try to resolves the grid with hypothesis, putting a number in a cell which is in a Blank list
                ArrayList<Integer> hypothesis = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0,0,0)); //hypothesis has been initialized : the '0' can be considered as sensors
                ArrayList<Integer> maxHypothesis = new ArrayList<Integer>();//list which will contain the maximum of possibilities on each line.
                for(int i=1;i<=9;i++){
                    int index = minPossibilitiesIndex(grid, i);
                    maxHypothesis.add(possibilitiesInACase(grid, i, index).size());
                }

                for(int hyp1=min(1,maxHypothesis.get(0));hyp1<=maxHypothesis.get(0);hyp1++){
                    for(int hyp2=min(1,maxHypothesis.get(1));hyp2<=maxHypothesis.get(1);hyp2++){
                        for(int hyp3=min(1,maxHypothesis.get(2));hyp3<=maxHypothesis.get(2);hyp3++){
                            for(int hyp4=min(1,maxHypothesis.get(3));hyp4<=maxHypothesis.get(3);hyp4++){
                                for(int hyp5=min(1,maxHypothesis.get(4));hyp5<=maxHypothesis.get(4);hyp5++){
                                    for(int hyp6=min(1,maxHypothesis.get(5));hyp6<=maxHypothesis.get(5);hyp6++){
                                        for(int hyp7=min(1,maxHypothesis.get(6));hyp7<=maxHypothesis.get(6);hyp7++){
                                            for(int hyp8=min(1,maxHypothesis.get(7));hyp8<=maxHypothesis.get(7);hyp8++){
                                                for(int hyp9=min(1,maxHypothesis.get(8));hyp9<=maxHypothesis.get(8);hyp9++){
                                                    int hyp[] = {hyp1,hyp2,hyp3,hyp4,hyp5,hyp6,hyp7,hyp8,hyp9};
                                                    grid = fillGridWithHypothesis(grid, hyp);
                                                    if(isSolvent(grid)){
                                                        //Calculus of the trivial numbers
                                                        grid = trivialNumbers(grid);
                                                        //Creation of the cluesOfBlank lists
                                                        ArrayList<Integer> i1 = cluesOfBlank(grid[0]);
                                                        ArrayList<Integer> i2 = cluesOfBlank(grid[1]);
                                                        ArrayList<Integer> i3 = cluesOfBlank(grid[2]);
                                                        ArrayList<Integer> i4 = cluesOfBlank(grid[3]);
                                                        ArrayList<Integer> i5 = cluesOfBlank(grid[4]);
                                                        ArrayList<Integer> i6 = cluesOfBlank(grid[5]);
                                                        ArrayList<Integer> i7 = cluesOfBlank(grid[6]);
                                                        ArrayList<Integer> i8 = cluesOfBlank(grid[7]);
                                                        ArrayList<Integer> i9 = cluesOfBlank(grid[8]);
                                                        //Definition of the regions : r1, ..., r9
                                                        ArrayList<Integer> r1 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[0][1],grid[0][2],grid[1][0],grid[1][1],grid[1][2],grid[2][0],grid[2][1],grid[2][2]));
                                                        ArrayList<Integer> r2 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[0][4],grid[0][5],grid[1][3],grid[1][4],grid[1][5],grid[2][3],grid[2][4],grid[2][5]));
                                                        ArrayList<Integer> r3 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[0][7],grid[0][8],grid[1][6],grid[1][7],grid[1][8],grid[2][6],grid[2][7],grid[2][8]));
                                                        ArrayList<Integer> r4 = new ArrayList<Integer>(Arrays.asList(grid[3][0],grid[3][1],grid[3][2],grid[4][0],grid[4][1],grid[4][2],grid[5][0],grid[5][1],grid[5][2]));
                                                        ArrayList<Integer> r5 = new ArrayList<Integer>(Arrays.asList(grid[3][3],grid[3][4],grid[3][5],grid[4][3],grid[4][4],grid[4][5],grid[5][3],grid[5][4],grid[5][5]));
                                                        ArrayList<Integer> r6 = new ArrayList<Integer>(Arrays.asList(grid[3][6],grid[3][7],grid[3][8],grid[4][6],grid[4][7],grid[4][8],grid[5][6],grid[5][7],grid[5][8]));
                                                        ArrayList<Integer> r7 = new ArrayList<Integer>(Arrays.asList(grid[6][0],grid[6][1],grid[6][2],grid[7][0],grid[7][1],grid[7][2],grid[8][0],grid[8][1],grid[8][2]));
                                                        ArrayList<Integer> r8 = new ArrayList<Integer>(Arrays.asList(grid[6][3],grid[6][4],grid[6][5],grid[7][3],grid[7][4],grid[7][5],grid[8][3],grid[8][4],grid[8][5]));
                                                        ArrayList<Integer> r9 = new ArrayList<Integer>(Arrays.asList(grid[6][6],grid[6][7],grid[6][8],grid[7][6],grid[7][7],grid[7][8],grid[8][6],grid[8][7],grid[8][8]));
                                                        //Reduction of the regions
                                                        r1 = reduction(r1);
                                                        r2 = reduction(r2);
                                                        r3 = reduction(r3);
                                                        r4 = reduction(r4);
                                                        r5 = reduction(r5);
                                                        r6 = reduction(r6);
                                                        r7 = reduction(r7);
                                                        r8 = reduction(r8);
                                                        r9 = reduction(r9);
                                                        //Definition of the pseudo columns
                                                        ArrayList<Integer> c11 = new ArrayList<Integer>(Arrays.asList(grid[3][0],grid[4][0],grid[5][0],grid[6][0],grid[7][0],grid[8][0]));
                                                        ArrayList<Integer> c12 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[1][0],grid[2][0],grid[6][0],grid[7][0],grid[8][0]));
                                                        ArrayList<Integer> c13 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[1][0],grid[2][0],grid[3][0],grid[4][0],grid[5][0]));
                                                        ArrayList<Integer> c21 = new ArrayList<Integer>(Arrays.asList(grid[3][1],grid[4][1],grid[5][1],grid[6][1],grid[7][1],grid[8][1]));
                                                        ArrayList<Integer> c22 = new ArrayList<Integer>(Arrays.asList(grid[0][1],grid[1][1],grid[2][1],grid[6][1],grid[7][1],grid[8][1]));
                                                        ArrayList<Integer> c23 = new ArrayList<Integer>(Arrays.asList(grid[0][1],grid[1][1],grid[2][1],grid[3][1],grid[4][1],grid[5][1]));
                                                        ArrayList<Integer> c31 = new ArrayList<Integer>(Arrays.asList(grid[3][2],grid[4][2],grid[5][2],grid[6][2],grid[7][2],grid[8][2]));
                                                        ArrayList<Integer> c32 = new ArrayList<Integer>(Arrays.asList(grid[0][2],grid[1][2],grid[2][2],grid[6][2],grid[7][2],grid[8][2]));
                                                        ArrayList<Integer> c33 = new ArrayList<Integer>(Arrays.asList(grid[0][2],grid[1][2],grid[2][2],grid[3][2],grid[4][2],grid[5][2]));
                                                        ArrayList<Integer> c41 = new ArrayList<Integer>(Arrays.asList(grid[3][3],grid[4][3],grid[5][3],grid[6][3],grid[7][3],grid[8][3]));
                                                        ArrayList<Integer> c42 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[1][3],grid[2][3],grid[6][3],grid[7][3],grid[8][3]));
                                                        ArrayList<Integer> c43 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[1][3],grid[2][3],grid[3][3],grid[4][3],grid[5][3]));
                                                        ArrayList<Integer> c51 = new ArrayList<Integer>(Arrays.asList(grid[3][4],grid[4][4],grid[5][4],grid[6][4],grid[7][4],grid[8][4]));
                                                        ArrayList<Integer> c52 = new ArrayList<Integer>(Arrays.asList(grid[0][4],grid[1][4],grid[2][4],grid[6][4],grid[7][4],grid[8][4]));
                                                        ArrayList<Integer> c53 = new ArrayList<Integer>(Arrays.asList(grid[0][4],grid[1][4],grid[2][4],grid[3][4],grid[4][4],grid[5][4]));
                                                        ArrayList<Integer> c61 = new ArrayList<Integer>(Arrays.asList(grid[3][5],grid[4][5],grid[5][5],grid[6][5],grid[7][5],grid[8][5]));
                                                        ArrayList<Integer> c62 = new ArrayList<Integer>(Arrays.asList(grid[0][5],grid[1][5],grid[2][5],grid[6][5],grid[7][5],grid[8][5]));
                                                        ArrayList<Integer> c63 = new ArrayList<Integer>(Arrays.asList(grid[0][5],grid[1][5],grid[2][5],grid[3][5],grid[4][5],grid[5][5]));
                                                        ArrayList<Integer> c71 = new ArrayList<Integer>(Arrays.asList(grid[3][6],grid[4][6],grid[5][6],grid[6][6],grid[7][6],grid[8][6]));
                                                        ArrayList<Integer> c72 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[1][6],grid[2][6],grid[6][6],grid[7][6],grid[8][6]));
                                                        ArrayList<Integer> c73 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[1][6],grid[2][6],grid[3][6],grid[4][6],grid[5][6]));
                                                        ArrayList<Integer> c81 = new ArrayList<Integer>(Arrays.asList(grid[3][7],grid[4][7],grid[5][7],grid[6][7],grid[7][7],grid[8][7]));
                                                        ArrayList<Integer> c82 = new ArrayList<Integer>(Arrays.asList(grid[0][7],grid[1][7],grid[2][7],grid[6][7],grid[7][7],grid[8][7]));
                                                        ArrayList<Integer> c83 = new ArrayList<Integer>(Arrays.asList(grid[0][7],grid[1][7],grid[2][7],grid[3][7],grid[4][7],grid[5][7]));
                                                        ArrayList<Integer> c91 = new ArrayList<Integer>(Arrays.asList(grid[3][8],grid[4][8],grid[5][8],grid[6][8],grid[7][8],grid[8][8]));
                                                        ArrayList<Integer> c92 = new ArrayList<Integer>(Arrays.asList(grid[0][8],grid[1][8],grid[2][8],grid[6][8],grid[7][8],grid[8][8]));
                                                        ArrayList<Integer> c93 = new ArrayList<Integer>(Arrays.asList(grid[0][8],grid[1][8],grid[2][8],grid[3][8],grid[4][8],grid[5][8]));
                                                        //Reduction of the pseudo columns
                                                        c11 = reduction(c11);
                                                        c12 = reduction(c12);
                                                        c13 = reduction(c13);
                                                        c21 = reduction(c21);
                                                        c22 = reduction(c22);
                                                        c23 = reduction(c23);
                                                        c31 = reduction(c31);
                                                        c32 = reduction(c32);
                                                        c33 = reduction(c33);
                                                        c41 = reduction(c41);
                                                        c42 = reduction(c42);
                                                        c43 = reduction(c43);
                                                        c51 = reduction(c51);
                                                        c52 = reduction(c52);
                                                        c53 = reduction(c53);
                                                        c61 = reduction(c61);
                                                        c62 = reduction(c62);
                                                        c63 = reduction(c63);
                                                        c71 = reduction(c71);
                                                        c72 = reduction(c72);
                                                        c73 = reduction(c73);
                                                        c81 = reduction(c81);
                                                        c82 = reduction(c82);
                                                        c83 = reduction(c83);
                                                        c91 = reduction(c91);
                                                        c92 = reduction(c92);
                                                        c93 = reduction(c93);
                                                        //Creation of the lists which contains the permutations
                                                        //We need the "grid" type to be ArrayList<ArrayList<Integer>>, instead of int[][]
                                                        ArrayList<ArrayList<Integer>> arGrid = changeType(grid);
                                                        ArrayList<ArrayList<Integer>> p1 = generatePerm(theOtherNumbers(reduction(arGrid.get(0))));
                                                        ArrayList<ArrayList<Integer>> p2 = generatePerm(theOtherNumbers(reduction(arGrid.get(1))));
                                                        ArrayList<ArrayList<Integer>> p3 = generatePerm(theOtherNumbers(reduction(arGrid.get(2))));
                                                        ArrayList<ArrayList<Integer>> p4 = generatePerm(theOtherNumbers(reduction(arGrid.get(3))));
                                                        ArrayList<ArrayList<Integer>> p5 = generatePerm(theOtherNumbers(reduction(arGrid.get(4))));
                                                        ArrayList<ArrayList<Integer>> p6 = generatePerm(theOtherNumbers(reduction(arGrid.get(5))));
                                                        ArrayList<ArrayList<Integer>> p7 = generatePerm(theOtherNumbers(reduction(arGrid.get(6))));
                                                        ArrayList<ArrayList<Integer>> p8 = generatePerm(theOtherNumbers(reduction(arGrid.get(7))));
                                                        ArrayList<ArrayList<Integer>> p9 = generatePerm(theOtherNumbers(reduction(arGrid.get(8))));
                                                        //Reduction of the lists of permutations
                                                        p1 = permutationReduction(grid, 1, p1, r1, r2, r3, c11, c21, c31, c41, c51, c61, c71, c81, c91);
                                                        p2 = permutationReduction(grid, 2, p2, r1, r2, r3, c11, c21, c31, c41, c51, c61, c71, c81, c91);
                                                        p3 = permutationReduction(grid, 3, p3, r1, r2, r3, c11, c21, c31, c41, c51, c61, c71, c81, c91);
                                                        p4 = permutationReduction(grid, 4, p4, r4, r5, r6, c12, c22, c32, c42, c52, c62, c72, c82, c92);
                                                        p5 = permutationReduction(grid, 5, p5, r4, r5, r6, c12, c22, c32, c42, c52, c62, c72, c82, c92);
                                                        p6 = permutationReduction(grid, 6, p6, r4, r5, r6, c12, c22, c32, c42, c52, c62, c72, c82, c92);
                                                        p7 = permutationReduction(grid, 7, p7, r7, r8, r9, c13, c23, c33, c43, c53, c63, c73, c83, c93);
                                                        p8 = permutationReduction(grid, 8, p8, r7, r8, r9, c13, c23, c33, c43, c53, c63, c73, c83, c93);
                                                        p9 = permutationReduction(grid, 9, p9, r7, r8, r9, c13, c23, c33, c43, c53, c63, c73, c83, c93);
                                                        //creation of sensors
                                                        int a1 = 0;
                                                        int a2 = 0;
                                                        int a3 = 0;
                                                        int a4 = 0;
                                                        int a5 = 0;
                                                        int a6 = 0;
                                                        int a7 = 0;
                                                        int a8 = 0;
                                                        int a9 = 0;
                                                        //Creation of the Switch
                                                        int k = 0;
                                                        //Creation of the list used to find the solution
                                                        int[] moveToSolution = new int[]{};
                                                        //Final Treatment = Backtracking using the potential permutations
                                                        while (a1 != p1.size()){
                                                            grid[0] = fillingLine(grid[0],i1,p1,a1);
                                                            a2 = 0; //Allow the reset of the p2 list
                                                            ArrayList<Integer> l11 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[0][1],grid[0][2]));
                                                            ArrayList<Integer> l12 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[0][4],grid[0][5]));
                                                            ArrayList<Integer> l13 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[0][7],grid[0][8]));
                                                            while (a2 != p2.size()){
                                                                grid[1] = fillingLine(grid[1],i2,p2,a2);
                                                                moveToSolution = sensorMove(grid,a2,0,1,l11,l11,l11,l12,l12,l12,l13,l13,l13);
                                                                a2 = moveToSolution[0];
                                                                k  = moveToSolution[1];
                                                                if (k==0){  //If no problem has been founded during the second permutation's input (in the grid)
                                                                    a3 = 0;
                                                                    ArrayList<Integer> l11l21 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[0][1],grid[0][2],grid[1][0],grid[1][1],grid[1][2]));
                                                                    ArrayList<Integer> l12l22 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[0][4],grid[0][5],grid[1][3],grid[1][4],grid[1][5]));
                                                                    ArrayList<Integer> l13l23 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[0][7],grid[0][8],grid[1][6],grid[1][7],grid[1][8]));
                                                                    while (a3 != p3.size()){
                                                                        grid[2] = fillingLine(grid[2],i3,p3,a3);
                                                                        moveToSolution = sensorMove(grid,a3,0,2,l11l21,l11l21,l11l21,l12l22,l12l22,l12l22,l13l23,l13l23,l13l23);
                                                                        a3 = moveToSolution[0];
                                                                        k  = moveToSolution[1];
                                                                        if (k==0){//If no problem has been founded during the third permutation's input (in the grid)
                                                                            a4 = 0;
                                                                            ArrayList<Integer> c110 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[1][0],grid[2][0]));
                                                                            ArrayList<Integer> c210 = new ArrayList<Integer>(Arrays.asList(grid[0][1],grid[1][1],grid[2][1]));
                                                                            ArrayList<Integer> c310 = new ArrayList<Integer>(Arrays.asList(grid[0][2],grid[1][2],grid[2][2]));
                                                                            ArrayList<Integer> c410 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[1][3],grid[2][3]));
                                                                            ArrayList<Integer> c510 = new ArrayList<Integer>(Arrays.asList(grid[0][4],grid[1][4],grid[2][4]));
                                                                            ArrayList<Integer> c610 = new ArrayList<Integer>(Arrays.asList(grid[0][5],grid[1][5],grid[2][5]));
                                                                            ArrayList<Integer> c710 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[1][6],grid[2][6]));
                                                                            ArrayList<Integer> c810 = new ArrayList<Integer>(Arrays.asList(grid[0][7],grid[1][7],grid[2][7]));
                                                                            ArrayList<Integer> c910 = new ArrayList<Integer>(Arrays.asList(grid[0][8],grid[1][8],grid[2][8]));
                                                                            while (a4 != p4.size()){
                                                                                grid[3] = fillingLine(grid[3],i4,p4,a4);
                                                                                moveToSolution = sensorMove(grid,a4,0,3,c110,c210,c310,c410,c510,c610,c710,c810,c910);
                                                                                a4 = moveToSolution[0];
                                                                                k  = moveToSolution[1];
                                                                                if (k == 0){//If no problem has been founded during the fourth permutation's input (in the grid)
                                                                                    a5 = 0;
                                                                                    ArrayList<Integer> c11l41 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[1][0],grid[2][0],grid[3][0],grid[3][1],grid[3][2]));
                                                                                    ArrayList<Integer> c21l41 = new ArrayList<Integer>(Arrays.asList(grid[0][1],grid[1][1],grid[2][1],grid[3][0],grid[3][1],grid[3][2]));
                                                                                    ArrayList<Integer> c31l41 = new ArrayList<Integer>(Arrays.asList(grid[0][2],grid[1][2],grid[2][2],grid[3][0],grid[3][1],grid[3][2]));
                                                                                    ArrayList<Integer> c41l42 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[1][3],grid[2][3],grid[3][3],grid[3][4],grid[3][5]));
                                                                                    ArrayList<Integer> c51l42 = new ArrayList<Integer>(Arrays.asList(grid[0][4],grid[1][4],grid[2][4],grid[3][3],grid[3][4],grid[3][5]));
                                                                                    ArrayList<Integer> c61l42 = new ArrayList<Integer>(Arrays.asList(grid[0][5],grid[1][5],grid[2][5],grid[3][3],grid[3][4],grid[3][5]));
                                                                                    ArrayList<Integer> c71l43 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[1][6],grid[2][6],grid[3][6],grid[3][7],grid[3][8]));
                                                                                    ArrayList<Integer> c81l43 = new ArrayList<Integer>(Arrays.asList(grid[0][7],grid[1][7],grid[2][7],grid[3][6],grid[3][7],grid[3][8]));
                                                                                    ArrayList<Integer> c91l43 = new ArrayList<Integer>(Arrays.asList(grid[0][8],grid[1][8],grid[2][8],grid[3][6],grid[3][7],grid[3][8]));
                                                                                    while (a5 != p5.size()){
                                                                                        grid[4] = fillingLine(grid[4],i5,p5,a5);
                                                                                        moveToSolution = sensorMove(grid,a5,0,4,c11l41,c21l41,c31l41,c41l42,c51l42,c61l42,c71l43,c81l43,c91l43);
                                                                                        a5 = moveToSolution[0];
                                                                                        k  = moveToSolution[1];
                                                                                        if (k == 0){//If no problem has been founded during the fifth permutation's input (in the grid)
                                                                                            a6 = 0;
                                                                                            ArrayList<Integer> c11l41l51 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[1][0],grid[2][0],grid[3][0],grid[3][1],grid[3][2],grid[4][0],grid[4][1],grid[4][2]));
                                                                                            ArrayList<Integer> c21l41l51 = new ArrayList<Integer>(Arrays.asList(grid[0][1],grid[1][1],grid[2][1],grid[3][0],grid[3][1],grid[3][2],grid[4][0],grid[4][1],grid[4][2]));
                                                                                            ArrayList<Integer> c31l41l51 = new ArrayList<Integer>(Arrays.asList(grid[0][2],grid[1][2],grid[2][2],grid[3][0],grid[3][1],grid[3][2],grid[4][0],grid[4][1],grid[4][2]));
                                                                                            ArrayList<Integer> c41l42l52 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[1][3],grid[2][3],grid[3][3],grid[3][4],grid[3][5],grid[4][3],grid[4][4],grid[4][5]));
                                                                                            ArrayList<Integer> c51l42l52 = new ArrayList<Integer>(Arrays.asList(grid[0][4],grid[1][4],grid[2][4],grid[3][3],grid[3][4],grid[3][5],grid[4][3],grid[4][4],grid[4][5]));                                   c51l42l52.add(grid[4][5]);
                                                                                            ArrayList<Integer> c61l42l52 = new ArrayList<Integer>(Arrays.asList(grid[0][5],grid[1][5],grid[2][5],grid[3][3],grid[3][4],grid[3][5],grid[4][3],grid[4][4],grid[4][5]));
                                                                                            ArrayList<Integer> c71l43l53 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[1][6],grid[2][6],grid[3][6],grid[3][7],grid[3][8],grid[4][6],grid[4][7],grid[4][8]));
                                                                                            ArrayList<Integer> c81l43l53 = new ArrayList<Integer>(Arrays.asList(grid[0][7],grid[1][7],grid[2][7],grid[3][6],grid[3][7],grid[3][8],grid[4][6],grid[4][7],grid[4][8]));
                                                                                            ArrayList<Integer> c91l43l53 = new ArrayList<Integer>(Arrays.asList(grid[0][8],grid[1][8],grid[2][8],grid[3][6],grid[3][7],grid[3][8],grid[4][6],grid[4][7],grid[4][8]));
                                                                                            while (a6 != p6.size()){
                                                                                                grid[5] = fillingLine(grid[5],i6,p6,a6);
                                                                                                moveToSolution = sensorMove(grid,a6,0,5,c11l41l51,c21l41l51,c31l41l51,c41l42l52,c51l42l52,c61l42l52,c71l43l53,c81l43l53,c91l43l53);
                                                                                                a6 = moveToSolution[0];
                                                                                                k  = moveToSolution[1];
                                                                                                if(k == 0){//If no problem has been founded during the sixth permutation's input (in the grid)
                                                                                                    a7 = 0;
                                                                                                    ArrayList<Integer> c11c12 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[1][0],grid[2][0],grid[3][0],grid[4][0],grid[5][0]));
                                                                                                    ArrayList<Integer> c21c22 = new ArrayList<Integer>(Arrays.asList(grid[0][1],grid[1][1],grid[2][1],grid[3][1],grid[4][1],grid[5][1]));
                                                                                                    ArrayList<Integer> c31c32 = new ArrayList<Integer>(Arrays.asList(grid[0][2],grid[1][2],grid[2][2],grid[3][2],grid[4][2],grid[5][2]));
                                                                                                    ArrayList<Integer> c41c42 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[1][3],grid[2][3],grid[3][3],grid[4][3],grid[5][3]));
                                                                                                    ArrayList<Integer> c51c52 = new ArrayList<Integer>(Arrays.asList(grid[0][4],grid[1][4],grid[2][4],grid[3][4],grid[4][4],grid[5][4]));
                                                                                                    ArrayList<Integer> c61c62 = new ArrayList<Integer>(Arrays.asList(grid[0][5],grid[1][5],grid[2][5],grid[3][5],grid[4][5],grid[5][5]));
                                                                                                    ArrayList<Integer> c71c72 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[1][6],grid[2][6],grid[3][6],grid[4][6],grid[5][6]));
                                                                                                    ArrayList<Integer> c81c82 = new ArrayList<Integer>(Arrays.asList(grid[0][7],grid[1][7],grid[2][7],grid[3][7],grid[4][7],grid[5][7]));
                                                                                                    ArrayList<Integer> c91c92 = new ArrayList<Integer>(Arrays.asList(grid[0][8],grid[1][8],grid[2][8],grid[3][8],grid[4][8],grid[5][8]));
                                                                                                    while(a7 != p7.size()){
                                                                                                        grid[6] = fillingLine(grid[6],i7,p7,a7);
                                                                                                        moveToSolution = sensorMove(grid,a7,0,6,c11c12,c21c22,c31c32,c41c42,c51c52,c61c62,c71c72,c81c82,c91c92);
                                                                                                        a7 = moveToSolution[0];
                                                                                                        k  = moveToSolution[1];
                                                                                                        if (k == 0){//If no problem has been founded during the seventh permutation's input (in the grid)
                                                                                                            a8 = 0;
                                                                                                            ArrayList<Integer> c11c12l71 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[1][0],grid[2][0],grid[3][0],grid[4][0],grid[5][0],grid[6][0],grid[6][1],grid[6][2]));
                                                                                                            ArrayList<Integer> c21c22l71 = new ArrayList<Integer>(Arrays.asList(grid[0][1],grid[1][1],grid[2][1],grid[3][1],grid[4][1],grid[5][1],grid[6][0],grid[6][1],grid[6][2]));
                                                                                                            ArrayList<Integer> c31c32l71 = new ArrayList<Integer>(Arrays.asList(grid[0][2],grid[1][2],grid[2][2],grid[3][2],grid[4][2],grid[5][2],grid[6][0],grid[6][1],grid[6][2]));
                                                                                                            ArrayList<Integer> c41c42l72 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[1][3],grid[2][3],grid[3][3],grid[4][3],grid[5][3],grid[6][3],grid[6][4],grid[6][5]));
                                                                                                            ArrayList<Integer> c51c52l72 = new ArrayList<Integer>(Arrays.asList(grid[0][4],grid[1][4],grid[2][4],grid[3][4],grid[4][4],grid[5][4],grid[6][3],grid[6][4],grid[6][5]));
                                                                                                            ArrayList<Integer> c61c62l72 = new ArrayList<Integer>(Arrays.asList(grid[0][5],grid[1][5],grid[2][5],grid[3][5],grid[4][5],grid[5][5],grid[6][3],grid[6][4],grid[6][5]));
                                                                                                            ArrayList<Integer> c71c72l73 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[1][6],grid[2][6],grid[3][6],grid[4][6],grid[5][6],grid[6][6],grid[6][7],grid[6][8]));
                                                                                                            ArrayList<Integer> c81c82l73 = new ArrayList<Integer>(Arrays.asList(grid[0][7],grid[1][7],grid[2][7],grid[3][7],grid[4][7],grid[5][7],grid[6][6],grid[6][7],grid[6][8]));
                                                                                                            ArrayList<Integer> c91c92l73 = new ArrayList<Integer>(Arrays.asList(grid[0][8],grid[1][8],grid[2][8],grid[3][8],grid[4][8],grid[5][8],grid[6][6],grid[6][7],grid[6][8]));
                                                                                                            while (a8 != p8.size()){
                                                                                                                grid[7] = fillingLine(grid[7],i8,p8,a8);
                                                                                                                moveToSolution = sensorMove(grid,a8,0,7,c11c12l71,c21c22l71,c31c32l71,c41c42l72,c51c52l72,c61c62l72,c71c72l73,c81c82l73,c91c92l73);
                                                                                                                a8 = moveToSolution[0];
                                                                                                                k  = moveToSolution[1];
                                                                                                                if (k == 0) {//If no problem has been founded during the eighth permutation's input (in the grid)
                                                                                                                    a9 = 0;
                                                                                                                    ArrayList<Integer> c1 = new ArrayList<Integer>(Arrays.asList(grid[0][0],grid[1][0],grid[2][0],grid[3][0],grid[4][0],grid[5][0],grid[6][0],grid[7][0]));
                                                                                                                    ArrayList<Integer> c2 = new ArrayList<Integer>(Arrays.asList(grid[0][1],grid[1][1],grid[2][1],grid[3][1],grid[4][1],grid[5][1],grid[6][1],grid[7][1]));
                                                                                                                    ArrayList<Integer> c3 = new ArrayList<Integer>(Arrays.asList(grid[0][2],grid[1][2],grid[2][2],grid[3][2],grid[4][2],grid[5][2],grid[6][2],grid[7][2]));
                                                                                                                    ArrayList<Integer> c4 = new ArrayList<Integer>(Arrays.asList(grid[0][3],grid[1][3],grid[2][3],grid[3][3],grid[4][3],grid[5][3],grid[6][3],grid[7][3]));
                                                                                                                    ArrayList<Integer> c5 = new ArrayList<Integer>(Arrays.asList(grid[0][4],grid[1][4],grid[2][4],grid[3][4],grid[4][4],grid[5][4],grid[6][4],grid[7][4]));
                                                                                                                    ArrayList<Integer> c6 = new ArrayList<Integer>(Arrays.asList(grid[0][5],grid[1][5],grid[2][5],grid[3][5],grid[4][5],grid[5][5],grid[6][5],grid[7][5]));
                                                                                                                    ArrayList<Integer> c7 = new ArrayList<Integer>(Arrays.asList(grid[0][6],grid[1][6],grid[2][6],grid[3][6],grid[4][6],grid[5][6],grid[6][6],grid[7][6]));
                                                                                                                    ArrayList<Integer> c8 = new ArrayList<Integer>(Arrays.asList(grid[0][7],grid[1][7],grid[2][7],grid[3][7],grid[4][7],grid[5][7],grid[6][7],grid[7][7]));
                                                                                                                    ArrayList<Integer> c9 = new ArrayList<Integer>(Arrays.asList(grid[0][8],grid[1][8],grid[2][8],grid[3][8],grid[4][8],grid[5][8],grid[6][8],grid[7][8]));
                                                                                                                    while(a9 != p9.size()){
                                                                                                                        grid[8] = fillingLine(grid[8],i9,p9,a9);
                                                                                                                        moveToSolution = sensorMove(grid,a9,0,8,c1,c2,c3,c4,c5,c6,c7,c8,c9);
                                                                                                                        a9 = moveToSolution[0];
                                                                                                                        k  = moveToSolution[1];
                                                                                                                        if(k == 0){//If no problem has been founded during the nineth permutation's input (in the grid)
                                                                                                                            if (transposition == true){
                                                                                                                                grid = transposeGrid(grid);
                                                                                                                            }
                                                                                                                            System.out.println("This grid has been solved !");
                                                                                                                            return(grid);
                                                                                                                        }
                                                                                                                    }
                                                                                                                    a8 += 1;
                                                                                                                }
                                                                                                            }
                                                                                                            a7 += 1;
                                                                                                        }
                                                                                                    }
                                                                                                    a6 += 1;
                                                                                                }
                                                                                            }
                                                                                            a5 += 1;
                                                                                        }
                                                                                    }
                                                                                    a4 += 1;
                                                                                }
                                                                            }
                                                                            a3 += 1;
                                                                        }
                                                                    }
                                                                    a2 += 1;
                                                                }
                                                            }
                                                            a1 += 1;
                                                        }
                                                    }
                                                    grid = changeTypeReturn(grids.get(lastGrid)); //The hypothesis was wrong, we try an other one, by restarting the grid
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                //System.out.println("Backtracking OK");
                if(twins.get(lastElement).get(0)==10){
                    twins.remove(lastElement);
                    lastElement -= 1;
                    twinHyps.remove(lastTwinHyp);
                    lastTwinHyp -= 1;
                    grids.remove(lastGrid);
                    lastGrid -= 1;
                }
                while (twinHyps.size() != 0 && twinHyps.get(lastTwinHyp) == 1){
                    twins.remove(lastElement);
                    lastElement -= 1;
                    twinHyps.remove(lastTwinHyp);
                    lastTwinHyp -= 1;
                    grids.remove(lastGrid);
                    lastGrid -= 1;
                }
                if (twinHyps.size()!=0){
                    twinHyps.remove(lastTwinHyp);
                    twinHyps.add(1);
                }
            }
        }
        System.out.println("This grid hasn't any Solution !");
        superMemoryGrid.set(0,superMemoryGrid.get(0)).set(0,0);
        return changeTypeReturn(superMemoryGrid);
    }
}