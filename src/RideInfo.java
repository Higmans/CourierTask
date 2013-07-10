class RideInfo {
    public int count = 0;
    public String time;

    public RideInfo(String station, String time){
        this.time = time;
        this.count++;
        if (MainClass.ridesInfo.containsKey(station)){
            this.time = MainClass.ridesInfo.get(station).time + ", " + time;
            this.count = MainClass.ridesInfo.get(station).count + 1;
        }
    }
}