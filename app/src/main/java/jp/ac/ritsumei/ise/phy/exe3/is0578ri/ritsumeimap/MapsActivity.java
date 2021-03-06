package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nifcloud.mbaas.core.NCMB;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {
    private GoogleMap mMap;
    private SearchingDataManager searchingDataManager;

    private LatLng bkc = new LatLng(34.981984561994466, 135.9623099219572);

    private Marker instantMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NCMB.initialize(this.getApplicationContext(),
                "242065d2599016f30f6c72b88582430b94e97efe9b9aacdfe55875e16f740f9f",
                "da8cbfbb576f6c3ef0205be62528c7effe0fb5b9b7464d4604ccff0b004a67aa");

        searchingDataManager = new SearchingDataManager();

        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        UiSettings settings = mMap.getUiSettings();
        settings.setScrollGesturesEnabled(true);
        settings.setZoomControlsEnabled(true);
        settings.setZoomGesturesEnabled(true);
        settings.setRotateGesturesEnabled(true);
        settings.setMapToolbarEnabled(false);
        //settings.setCompassEnabled(true);

        ShowMap(bkc);

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()
        {
            @Override
            public void onMapLongClick(LatLng longTapLocation)
            {
                LatLng newLocation = new LatLng(longTapLocation.latitude, longTapLocation.longitude);

                instantMarker = mMap.addMarker(new MarkerOptions().position(newLocation)
                        .title("" + longTapLocation.latitude + " :" + longTapLocation.longitude));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 18));
                CreateCheckDialog();
            }
        });

        mMap.setOnInfoWindowClickListener(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            RequestPermisson();
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        settings.setMyLocationButtonEnabled(true);
    }

    public void ShowMap(LatLng nowLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nowLocation, 16));

        try
        {
            List<NCMBObject> pins = searchingDataManager.SearchDataNearFromCenterOfMap(nowLocation.latitude, nowLocation.longitude, 2);
            if (pins.size() > 0)
            {
                for (NCMBObject obj: pins)
                {
                    NCMBObject contents = new NCMBObject("ReviewContents");
                    contents.setObjectId(obj.getString("ReviewObjID"));
                    System.out.println("ReviewObjID: " + contents.getObjectId());
                    contents.fetch();
                    System.out.println("Fetching OK");
                    Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(obj.getGeolocation("Location").getLatitude(),
                            obj.getGeolocation("Location").getLongitude()))
                            .title(contents.getString("PlaceName"))
                            .snippet("??????????????????????????????")
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_fukidasi)));
                    marker.setTag(contents.getObjectId());
                }
            }
        }
        catch (NCMBException e)
        {
            System.out.println("Error at pin creating");
            e.printStackTrace();
        }
    }

    public void LoadReviewRegisterActivity()
    {
        Intent intent = new Intent(getApplication(), ReviewRegister.class);
        intent.putExtra("Latitude", instantMarker.getPosition().latitude);
        intent.putExtra("Longtude", instantMarker.getPosition().longitude);
        startActivity(intent);
    }

    public void DeleteInstantMarker()
    {
        instantMarker.remove();
    }

    @Override
    public boolean onMyLocationButtonClick()
    {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location)
    {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permission, grantResults);

        if (grantResults.length <= 0) { return; }
        switch(requestCode)
        {
            case 1:
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    /// ????????????????????????
                    ShowMap(bkc);
                }
                else
                {
                    /// ?????????????????????????????????
                    Toast.makeText(this,
                            "????????????????????????????????????????????????????????????????????????????????????????????????????????????",
                            Toast.LENGTH_LONG).show();
                    ShowMap(bkc);
                }
            }
            return;
        }
    }

    private void RequestPermisson()
    {
        ActivityCompat.requestPermissions(this,
                new String[]
                {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                }, 1);
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker)
    {
        Intent intent = new Intent(getApplication(), ReviewShower.class);
        intent.putExtra("ContentsID", marker.getTag().toString());
        startActivity(intent);
    }

    private void CreateCheckDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("????????????????????????????????????????????????")
                .setPositiveButton("??????", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // ?????????????????????????????????
                        System.out.println("OK Button Down");

                        DeleteInstantMarker();
                        LoadReviewRegisterActivity();
                    }
                })
                .setNegativeButton("?????????", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        // ?????????????????????????????????
                        System.out.println("Cancel Button Down");

                        DeleteInstantMarker();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener()
                {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface)
                    {
                        // ???????????????????????????????????????
                        System.out.println("Cancel Button Down");
                        DeleteInstantMarker();
                    }
                });

        AlertDialog alertDialog = builder.create();
        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
        lp.gravity = Gravity.BOTTOM;

        alertDialog.getWindow().setAttributes(lp);
        alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        alertDialog.show();
    }
}