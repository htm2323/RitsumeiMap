package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import com.nifcloud.mbaas.core.FetchCallback;
import com.nifcloud.mbaas.core.FindCallback;
import com.nifcloud.mbaas.core.NCMB;
import com.nifcloud.mbaas.core.NCMBBase;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBObject;
import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.NCMBQuery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView putText = (TextView) findViewById(R.id.putText);
        TextView getText = (TextView) findViewById(R.id.getText);

        System.out.println("put:" + putText);
        System.out.println("get:" + getText);

        NCMB.initialize(this.getApplicationContext(),
                "242065d2599016f30f6c72b88582430b94e97efe9b9aacdfe55875e16f740f9f",
                "da8cbfbb576f6c3ef0205be62528c7effe0fb5b9b7464d4604ccff0b004a67aa");

        // クラスのNCMBObjectを作成
        NCMBObject obj = new NCMBObject("TestClass");

        // オブジェクトの値を設定
        /*try
        {
            obj.put("datalist", Arrays.asList("date", "name", "place", "message"));
        }
        catch (NCMBException e)
        {
            e.printStackTrace();
        }

        // データストアへの登録
        obj.saveInBackground(new DoneCallback()
        {
            @Override
            public void done(NCMBException e)
            {
                if(e != null)
                {
                    //保存に失敗した場合の処理
                    //System.out.println("save process failed");
                    putText.setText("save process failed");
                }
                else
                {
                    //保存に成功した場合の処理
                    //System.out.println("save process success");
                    putText.setText("save process success");
                }
            }
        });*/

        /*NCMBQuery<NCMBObject> query = new NCMBQuery<>("TestClass");
        query.whereEqualTo("message", "Hello, NCMB!");
        List<NCMBObject> result = null;
        try
        {
            result = query.find();
        }
        catch (NCMBException e)
        {
            getText.setText("Searching Object Failed");
            e.printStackTrace();
        }

        try
        {
            obj.setObjectId(result.get(0).getObjectId());

            obj.fetchInBackground(new FetchCallback()
            {
                @Override
                public void done(NCMBBase object, NCMBException e)
                {
                    if (e != null)
                    {
                        //System.out.println("Getting Object Failed");
                        getText.setText("Getting Object Failed");
                    }
                    else
                    {
                        //System.out.println("Getting Object Succeed");
                        getText.setText(obj.getString("message"));
                    }
                }
            });
        }
        catch (NCMBException e)
        {
            e.printStackTrace();
        }*/
    }

    public void OnButtonDown(View view)
    {
        Intent intent = new Intent(getApplication(), MapsActivity.class);
        startActivity(intent);
    }
}