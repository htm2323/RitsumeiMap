package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class CheckCreatingDialog extends DialogFragment
{
    MapsActivity mapAct;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("この場所に口コミを作成しますか？")
                .setPositiveButton("作る", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // ボタンを押した時の処理
                        System.out.println("OK Button Down");

                        mapAct.LoadReviewRegisterActivity();
                    }
                })
                .setNegativeButton("やめる", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // ボタンを押した時の処理
                        System.out.println("Cancel Button Down");

                        mapAct.DeleteInstantMarker();
                    }
                });

        AlertDialog alertDialog = builder.create();
        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;

        alertDialog.getWindow().setAttributes(lp);
        alertDialog.show();
    }

    public void SetMapAct(MapsActivity receive)
    {
        mapAct = receive;
    }
}
