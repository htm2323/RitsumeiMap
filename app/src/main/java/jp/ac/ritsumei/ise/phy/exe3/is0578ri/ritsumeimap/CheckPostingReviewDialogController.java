package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBObject;

import java.util.Arrays;

public class CheckPostingReviewDialogController extends DialogFragment
{
    ReviewData data;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("口コミを投稿してよろしいですか？")
                .setPositiveButton("投稿", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // ボタンを押した時の処理
                        System.out.println("OK Button Down");


                    }
                })
                .setNegativeButton("キャンセル", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // ボタンを押した時の処理
                        System.out.println("Cancel Button Down");
                    }
                });

        return  builder.create();
    }

    private void SaveReviewToDatabase()
    {
        NCMBObject obj = new NCMBObject("ReviewData");

        try
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
                    System.out.println("save process failed");
                }
                else
                {
                    //保存に成功した場合の処理
                    System.out.println("save process success");
                }
            }
        });
    }

    public void ServeData(ReviewData inputData)
    {
        data = inputData;
    }
}
