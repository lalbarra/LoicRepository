package com.albarracin.loic.sudo_clue;

        import android.content.Intent;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;

/**
 * Created by loic albarracin on 15/05/2017.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      We declare two buttons in order to access at two different views : landscape or portrait view
        Button portraitButton = (Button) findViewById(R.id.portraitView);
        portraitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              We use the same View for portrait and landscape view, we just store a variable to signal which one has been chosen
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("userViewChoice","Portrait");
                startActivity(intent);
            }
        });

        Button landscapeButton = (Button) findViewById(R.id.landscapeView);
        landscapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              The second possibility, the landscape view
                Intent intent = new Intent(MainActivity.this, ViewActivity.class);
                intent.putExtra("userViewChoice","Landscape");
                startActivity(intent);
            }
        });

    }

}

