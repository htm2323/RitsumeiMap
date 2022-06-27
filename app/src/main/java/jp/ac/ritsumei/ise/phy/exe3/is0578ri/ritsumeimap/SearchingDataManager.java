package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

import android.location.Location;

import com.nifcloud.mbaas.core.FindCallback;
import com.nifcloud.mbaas.core.NCMBException;
import com.nifcloud.mbaas.core.NCMBObject;
import com.nifcloud.mbaas.core.NCMBQuery;

import java.util.List;

public class SearchingDataManager
{
    NCMBQuery<NCMBObject> query;

    public SearchingDataManager()
    {
        query = new NCMBQuery<>("ReviewLatitude");
    }

    public List<NCMBObject> SearchDataNearFromCenterOfMap(double centerLat, double centerLong, double limitationKilometer) throws NCMBException
    {
        Location centerOfMap = new Location("CenterOfMap");
        centerOfMap.setLatitude(centerLat);
        centerOfMap.setLongitude(centerLong);

        query.whereWithinKilometers("Location", centerOfMap, limitationKilometer);
        List<NCMBObject> result = query.find();

        return  result;
    }
}
