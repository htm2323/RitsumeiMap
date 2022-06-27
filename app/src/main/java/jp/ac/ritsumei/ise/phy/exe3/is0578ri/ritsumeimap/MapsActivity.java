package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private Marker instantMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                                                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng bkc = new LatLng(34.981984561994466, 135.9623099219572);
        //mMap.addMarker(new MarkerOptions().position(bkc).title("Marker in BKC").snippet("補足"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bkc, 16));

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng tapLocation)
            {
                LatLng location;
                location = new LatLng(tapLocation.latitude, tapLocation.longitude);
                String str = String.format(Locale.US, "%f, %f", tapLocation.latitude, tapLocation.longitude);
                //mMap.addMarker(new MarkerOptions().position(location).title(str).snippet("TapTest"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 16));
            }
        });

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
}