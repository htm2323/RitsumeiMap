package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBObject;

public class ReviewShower extends AppCompatActivity
{
    String contentsObjID;
    NCMBObject contents;

    TextView placeNameTxt;
    TextView postPersonNameTxt;
    TextView postDateTxt;
    TextView reviewTxt;
    TextView goodCountTxt;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_shower);

        Intent intent = getIntent();
        contentsObjID = intent.getStringExtra("ContentsID");

        placeNameTxt = (TextView) findViewById(R.id.PlaceNameTxt);
        postPersonNameTxt = (TextView) findViewById(R.id.postPersonNameTxt);
        postDateTxt = (TextView) findViewById(R.id.postDateTxt);
        reviewTxt = (TextView) findViewById(R.id.reviewTxt);
        goodCountTxt = (TextView) findViewById(R.id.goodCountTxt);

        contents = new NCMBObject("ReviewContents");
        try
        {
            contents.setObjectId(contentsObjID);
            contents.fetch();

            placeNameTxt.setText(contents.getString("PlaceName"));
            postPersonNameTxt.setText(contents.getString("PostPerson"));
            postDateTxt.setText(contents.getString("createDate"));
            reviewTxt.setText(contents.getString("Review"));
            goodCountTxt.setText(contents.getInt("GoodCount") + "いいね");
        }
        catch (NCMBException e)
        {
            Toast.makeText(this,
                    "口コミデータが取得できませんでした。もう一度お試しください。",
                    Toast.LENGTH_LONG).show();
            finish();
            e.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    public void OnGoodButtonClick(View view) throws NCMBException
    {
        AppCompatActivity thisAct = this;

        contents.increment("GoodCount", 1);
        contents.saveInBackground(new DoneCallback()
        {
            @Override
            public void done(NCMBException e)
            {
                if(e != null)
                {
                    //保存に失敗した場合の処理
                    System.out.println("save process failed at contents save");
                    try
                    {
                        contents.increment("GoodCount", -1);
                    }
                    catch (NCMBException ncmbException)
                    {
                        ncmbException.printStackTrace();
                    }
                }
                else
                {
                    //保存に成功した場合の処理
                    System.out.println("save process succeed");

                    Toast.makeText(thisAct,
                            "いいねしました！",
                            Toast.LENGTH_LONG).show();

                    try
                    {
                        contents.fetch();
                    }
                    catch (NCMBException ncmbException)
                    {
                        ncmbException.printStackTrace();
                    }

                    goodCountTxt.setText(contents.getInt("GoodCount") + "いいね");
                    Button goodButton = (Button) findViewById(R.id.goodButton);
                    goodButton.setEnabled(false);
                }
            }
        });
    }

    public void OnBackButtonClick(View view)
    {
        finish();
    }
}