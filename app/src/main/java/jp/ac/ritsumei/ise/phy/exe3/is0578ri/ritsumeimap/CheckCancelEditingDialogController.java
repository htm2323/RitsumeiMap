package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CheckCancelEditingDialogController extends DialogFragment
{
    ReviewRegister revAct;

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("編集を中止してよろしいですか？")
                .setPositiveButton("編集をやめる", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // ボタンを押した時の処理
                        System.out.println("OK Button Down");

                        revAct.ShowToastMessage("編集を中止しました。");
                        revAct.BackToMapActivity();
                    }
                })
                .setNegativeButton("編集を続ける", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // ボタンを押した時の処理
                        System.out.println("Cancel Button Down");
                    }
                });

        return builder.create();
    }

    public  void ServeActivity(ReviewRegister reseiveAct)
    {
        revAct = reseiveAct;
    }
}
