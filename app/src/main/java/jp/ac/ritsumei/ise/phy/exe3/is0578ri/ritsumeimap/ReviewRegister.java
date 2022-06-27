package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ReviewRegister extends AppCompatActivity
{
    double latitude;
    double longtude;

    EditText inputPlaceName;
    EditText inputPostedPersonName;
    EditText inputReview;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_register);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("Latitude", 0);
        longtude = intent.getDoubleExtra("Longtude", 0);

        inputPlaceName = (EditText) findViewById(R.id.editPlaceName);
        inputPostedPersonName = (EditText) findViewById(R.id.editPostPersonName);
        inputReview = (EditText) findViewById(R.id.editReview);
    }

    public void OnPostButtonDown(View view)
    {
        ReviewData data = new ReviewData(latitude, longtude,
                                            inputPlaceName.getText().toString(),
                                            inputPostedPersonName.getText().toString(),
                                            inputReview.getText().toString());
        CheckPostingReviewDialogController dialog = new CheckPostingReviewDialogController();
        dialog.ServeData(data);
        dialog.show(getSupportFragmentManager(), "CheckPostDialog");
    }

    public void OnCancelButtonDown(View view)
    {
        CheckCancelEditingDialogController dialog = new CheckCancelEditingDialogController();
        dialog.show(getSupportFragmentManager(), "CheckCancelDialog");
    }
}