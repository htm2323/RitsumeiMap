package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ReviewRegister extends AppCompatActivity
{
    double latitude;
    double longtude;
    String placeName;
    String postedPersonName;
    String reviewText;

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
        CheckPostingReviewDialogController dialog = new CheckPostingReviewDialogController();
        dialog.show(getSupportFragmentManager(), "CheckPostDialog");
    }

    public void OnCancelButtonDown(View view)
    {
        CheckCancelEditingDialogController dialog = new CheckCancelEditingDialogController();
        dialog.show(getSupportFragmentManager(), "CheckCancelDialog");
    }
}