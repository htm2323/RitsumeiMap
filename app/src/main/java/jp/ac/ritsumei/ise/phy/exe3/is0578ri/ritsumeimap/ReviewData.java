package jp.ac.ritsumei.ise.phy.exe3.is0578ri.ritsumeimap;

public class ReviewData
{
    double latitude;
    double longtude;
    String placeName;
    String postPerson;
    String review;

    public ReviewData(double inputLat, double inputLong, String inputPlaceName, String inputPostPerson, String inputReview)
    {
        latitude = inputLat;
        longtude = inputLong;
        placeName = inputPlaceName;
        postPerson = inputPostPerson;
        review = inputReview;
    }
}
