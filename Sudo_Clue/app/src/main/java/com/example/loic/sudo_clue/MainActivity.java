package com.example.loic.sudo_clue;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;

/**
 * Created by loic on 15/05/2017.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button portraitButton = (Button) findViewById(R.id.portraitView);
        portraitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, PortraitView.class);
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("userViewChoice","Portrait");
                startActivity(intent);
            }
        });

        Button landscapeButton = (Button) findViewById(R.id.landscapeView);
        landscapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, LandscapeView.class);
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("userViewChoice","Landscape");
                startActivity(intent);
            }
        });

    }

}

