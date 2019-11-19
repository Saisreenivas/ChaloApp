package Model;

public class StopWiseData {
    int stopId;
    String stopName;
    int sequence;
    float latitude;
    float longitude;

    public StopWiseData(int stopId, String stopName, int sequence, float latitude, float longitude) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.sequence = sequence;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
