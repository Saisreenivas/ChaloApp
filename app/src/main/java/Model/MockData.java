package Model;

import java.util.ArrayList;

public class MockData {
    int routeId;
    String routeName;
    ArrayList<StopWiseData> stopDataList = new ArrayList<>();

    public MockData(int routeId, String routeName, ArrayList<StopWiseData> stopDataList) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.stopDataList = stopDataList;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public ArrayList<StopWiseData> getStopDataList() {
        return stopDataList;
    }

    public void setStopDataList(ArrayList<StopWiseData> stopDataList) {
        this.stopDataList = stopDataList;
    }

    public String[] getStopNamesList(ArrayList<StopWiseData> stopDataList){
        String[] stopNames = new String[stopDataList.size()];
        for(int i=0;i<stopDataList.size();i++){
            stopNames[i] = stopDataList.get(i).getStopName();
        }

        return stopNames;
    }
}
