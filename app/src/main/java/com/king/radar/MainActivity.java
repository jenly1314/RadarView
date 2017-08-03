package com.king.radar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.king.view.radarview.RadarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RadarView rv;

    Button btnStyle,btnRadar,btnScore;

    private boolean isAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = (RadarView)findViewById(R.id.rv);

        btnStyle = (Button)findViewById(R.id.btnStyle);
        btnRadar = (Button)findViewById(R.id.btnRadar);
        btnScore = (Button)findViewById(R.id.btnScore);

//        btnStyle.setVisibility(View.GONE);

        btnStyle.setOnClickListener(this);
        btnRadar.setOnClickListener(this);
        btnScore.setOnClickListener(this);
    }

    private void clickStyle(){
        if(isAll){
            rv.setScanColor(new int[]{Color.TRANSPARENT,Color.TRANSPARENT,rv.getCircleColor()});
        }else{
            rv.setScanColor(new int[]{Color.TRANSPARENT,rv.getCircleColor()});
        }
        isAll = !isAll;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnStyle:
                clickStyle();
                break;
            case R.id.btnRadar:
                rv.start();
                break;
            case R.id.btnScore:
                rv.showScore(98f);
                break;
        }
    }
}
