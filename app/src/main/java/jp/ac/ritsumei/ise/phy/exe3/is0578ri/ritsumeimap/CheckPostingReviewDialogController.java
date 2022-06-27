package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.nifcloud.mbaas.core.DoneCallback;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBObject;

public class CheckPostingReviewDialogController extends DialogFragment
{
    ReviewData data;
    ReviewRegister revAct;

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

                        SaveReviewToDatabase();
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
        NCMBObject latObj = new NCMBObject("ReviewLatitude");
        NCMBObject contentsObj = new NCMBObject("ReviewContents");

        try
        {
            contentsObj.put("PlaceName", data.placeName);
            contentsObj.put("PostPerson", data.postPerson);
            contentsObj.put("Review", data.review);
            contentsObj.put("GoodCount", 0);
        }
        catch (NCMBException e)
        {
            e.printStackTrace();
        }

        // データストアへの登録
        contentsObj.saveInBackground(new DoneCallback()
        {
            @Override
            public void done(NCMBException e)
            {
                if(e != null)
                {
                    //保存に失敗した場合の処理
                    System.out.println("save process failed at contents save");

                    revAct.ShowToastMessage("口コミの保存に失敗しました。もう一度お試しください。");
                }
                else
                {
                    //保存に成功した場合の処理
                    System.out.println("save process progressing");
                    String objID = contentsObj.getObjectId();

                    try
                    {
                        Location loc = new Location(data.placeName);
                        loc.setLatitude(data.latitude);
                        loc.setLongitude(data.longtude);
                        latObj.put("Location", loc);
                        latObj.put("ReviewObjID", objID);
                    }
                    catch (NCMBException er)
                    {
                        er.printStackTrace();
                    }

                    latObj.saveInBackground(new DoneCallback()
                    {
                        @Override
                        public void done(NCMBException e)
                        {
                            if (e != null)
                            {
                                //保存に失敗した場合の処理
                                System.out.println("save process failed at Latitude Save");
                                revAct.ShowToastMessage("口コミの保存に失敗しました。もう一度お試しください。");
                            }
                            else
                            {
                                //保存に成功した場合の処理
                                System.out.println("save process success");
                                revAct.ShowToastMessage("口コミを保存しました!");

                                revAct.BackToMapActivity();
                            }
                        }
                    });
                }
            }
        });
    }

    public void ServeData(ReviewData inputData)
    {
        data = inputData;
    }

    public  void ServeActivity(ReviewRegister reseiveAct)
    {
        revAct = reseiveAct;
    }
}
