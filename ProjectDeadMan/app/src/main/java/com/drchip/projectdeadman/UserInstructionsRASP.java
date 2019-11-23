package com.drchip.projectdeadman;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserInstructionsRASP extends AppCompatActivity {

    TextView tvFingerRasp, tvFingerRaspDescription, tvFaceReco , tvFaceRecoDescription, tvImageNumber;
    ImageView ivFingerRasp, ivFaceStart, ivImageCount;
    ProgressBar pbImageCount;
    Button btnCancel, btnConfirm;
    LinearLayout linFaceDescription, linImageCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_instructions_rasp);

        tvFingerRasp = findViewById(R.id.tvFingerRasp);
        tvFingerRaspDescription = findViewById(R.id.tvFingeRaspDescription);
        tvFaceReco = findViewById(R.id.tvFacereco);
        tvFaceRecoDescription = findViewById(R.id.tvFacerecDescription);
        tvImageNumber = findViewById(R.id.tvImageNumber);
        ivFingerRasp = findViewById(R.id.ivFingerRasp);
        ivFaceStart = findViewById(R.id.ivFaceStart);
        ivImageCount = findViewById(R.id.ivImageCount);
        pbImageCount = findViewById(R.id.pbImageCount);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);
        linFaceDescription= findViewById(R.id.linFaceDescripiton);
        linImageCount = findViewById(R.id.linImageCount);


        tvFaceRecoDescription.setVisibility(View.GONE);
        tvFingerRaspDescription.setVisibility(View.GONE);
        linFaceDescription.setVisibility(View.GONE);
        linImageCount.setVisibility(View.GONE);

        pbImageCount.setProgress(0);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.rotate);
        animation.setStartTime(10);
        ivFingerRasp.startAnimation(animation);








    }
}
