package com.example.loic.sudo_clue;

/**
 * Created by loic on 09/05/2017.
 */

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Dimension;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

    public class TroimsActivity extends Activity {
        RelativeLayout layout = null;
        TextView text = null;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            layout = (RelativeLayout) RelativeLayout.inflate(this, R.layout.activity_main, null);

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    EditText jb = new EditText(this);
                    jb.setInputType(InputType.TYPE_CLASS_NUMBER);
                    getAreaColored(i, j, jb);

                }
            }
            for (int j = 0; j < 9; j++) {
                Button button = new Button(this);
            }
            setVisible(true);

            setContentView(R.layout.activity_main);
            }



        public void getAreaColored(int i, int j , EditText jb)
        {

            //First Column
            if ((i>=0 && i<3) && (j>=0 && j<3))
            {
                jb.setBackgroundColor(Color.RED);
            }
            if ((i>=3 && i<6) && (j>=0 && j<3))
            {
                jb.setBackgroundColor(Color.YELLOW);
            }
            if ((i>=6 && i<9) && (j>=0 && j<3))
            {
                jb.setBackgroundColor(Color.BLUE);
            }

            //Second Column
            if ((i>=0 && i<3) && (j>=3 && j<6))
            {
                jb.setBackgroundColor(Color.CYAN);
            }
            if ((i>=3 && i<6) && (j>=3 && j<6))
            {
                jb.setBackgroundColor(Color.WHITE);
            }
            if ((i>=6 && i<9) && (j>=3 && j<6))
            {
                jb.setBackgroundColor(Color.YELLOW);
            }

            //Third Column
            if ((i>=0 && i<3) && (j>=6 && j<9))
            {
                jb.setBackgroundColor(Color.GREEN);
            }
            if ((i>=3 && i<6) && (j>=6 && j<9))
            {
                jb.setBackgroundColor(Color.GRAY);
            }
            if ((i>=6 && i<9) && (j>=6 && j<9))
            {
                jb.setBackgroundColor(Color.MAGENTA);
            }

        }
    }


