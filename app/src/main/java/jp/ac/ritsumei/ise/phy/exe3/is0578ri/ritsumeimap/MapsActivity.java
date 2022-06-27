package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
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
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap.databinding.ActivityMapsBinding;

public class MapsActivity extends AppCompatActivity implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {
    private GoogleMap mMap;
    private SearchingDataManager searchingDataManager;

    private Marker instantMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        //settings.setCompassEnabled(true);

        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
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
        settings.setMyLocationButtonEnabled(true);*/

        // Add a marker in Sydney and move the camera
        LatLng bkc = new LatLng(34.981984561994466, 135.9623099219572);
        //mMap.addMarker(new MarkerOptions().position(bkc).title("Marker in BKC").snippet("補足"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bkc, 16));

        try
        {
            List<NCMBObject> pins = searchingDataManager.SearchDataNearFromCenterOfMap(bkc.latitude, bkc.longitude, 2);
            if (pins.size() > 0)
            {
                for (NCMBObject obj: pins)
                {
                    NCMBObject contents = new NCMBObject("ReviewContents");
                    contents.setObjectId(obj.getString("ReviewObjID"));
                    contents.fetch();
                    mMap.addMarker(new MarkerOptions().position(new LatLng(obj.getGeolocation("Location").getLatitude(),
                                                                            obj.getGeolocation("Location").getLongitude()))
                            .title(contents.getString("PlaceName"))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_fukidasi)));
                }
            }
        }
        catch (NCMBException e)
        {
            e.printStackTrace();
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()
        {
            @Override
            public void onMapLongClick(LatLng longTapLocation)
            {
                LatLng newLocation = new LatLng(longTapLocation.latitude, longTapLocation.longitude);
                instantMarker = mMap.addMarker(new MarkerOptions().position(newLocation)
                        .title("" + longTapLocation.latitude + " :" + longTapLocation.longitude));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLocation, 18));

                CheckCreatingDialog dialog = new CheckCreatingDialog();
                dialog.SetMapAct(MapsActivity.this);
                dialog.show(getSupportFragmentManager(), "CheckCreatingDialog");
            }
        });
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

    private void RequestPermisson()
    {
        String[] permisson = new String[3];
        permisson[0] = Manifest.permission.ACCESS_COARSE_LOCATION;
        permisson[1] = Manifest.permission.ACCESS_FINE_LOCATION;
        permisson[2] = Manifest.permission.ACCESS_BACKGROUND_LOCATION;
        ActivityCompat.requestPermissions(this, permisson, 1234);
    }
}