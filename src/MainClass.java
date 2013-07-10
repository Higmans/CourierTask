public class MainClass {
    public static String[] stationNamesRed = {"Лісова", //0
                                            "Чернігівська",
                                            "Дарниця",
                                            "Лівобережна",
                                            "Гідропарк",
                                            "Дніпро",
                                            "Арсенальна",
                                            "Хрещатик",
                                            "Театральна",
                                            "Університет",
                                            "Вокзальна",
                                            "Політехнічний інститут",
                                            "Шулявська",
                                            "Берестейська",
                                            "Нивки",
                                            "Святошин", // 15 112
                                            "Житомирська",// 16
                                            "Академмістечко"}; //17 110
    public static String[] stationNamesBlue = {"Теремки",
                                            "Іподром",
                                            "Виставковий центр",
                                            "Васильківська",
                                            "Голосіївська",
                                            "Деміївська",
                                            "Либідьська",
                                            "Палац Україна",
                                            "Олімпійська",
                                            "Площа Льва Толстого",
                                            "Майдан Незалежності",
                                            "Поштова площа",
                                            "Контрактова площа",
                                            "Тараса Шевченка",
                                            "Петрівка",
                                            "Оболонь",
                                            "Мінська",
                                            "Героїв Дніпра"};
    public static String[] stationNamesGreen = {"Червоний Хутір",
                                            "Бориспільска",
                                            "Вирлиця",
                                            "Харківська",
                                            "Позняки",
                                            "Осокорки",
                                            "Славутич",
                                            "Теличка",
                                            "Видубичі",
                                            "Дружби народів",
                                            "Печерська",
                                            "Кловська",
                                            "Палац Спорту",
                                            "Золоті Ворота",
                                            "Львівська Брама",
                                            "Лук'янівська",
                                            "Дорогожичі",
                                            "Сирець"};
    public static final int RIDE_COST = 2; // жетон - 2 грн
    public static final int STARTING_TIME = 540; // начало работы - 9 часов утра
    public static final int WORKING_DAY_DURATION = 480; // 8 часов рабочий день
    public static final int TIME_BETWEEN_STATIONS = 2; // время между станциями
    public static final int KRESCHATIK_INDEX = 7;
    public static final int MAIDAN_INDEX = 10;
    public static final int TEATRALNA_INDEX = 8;
    public static final int ZOLOTI_VOROTA_INDEX = 13;
    public static final int LVA_TOLSTOGO_INDEX = 9;
    public static final int PALATS_SPORTU_INDEX = 12;
    public static int time = STARTING_TIME;
    public static final String[][] METROPOLITAN = {stationNamesRed, stationNamesBlue, stationNamesGreen};
    public static final String STARTING_STATION = METROPOLITAN[0][0]; // начальная станция - Лысова
    public static int[] nextStation;
    public static int[] previousStation = {0,0};
    public static String currentTime;
    public static String currentStation = STARTING_STATION;

    public static void main(String[] args) {
        int counter = 0;
        while (time < STARTING_TIME + WORKING_DAY_DURATION){
            if (counter == 0){
                currentTime = time/60 + ":" + (((time - (time/60)*60) < 10 ? "0" : "") + (time - (time/60)*60));
                System.out.println(currentTime + " - " + "Курьер начал работу. Зашел в метро на станции " + currentStation);
                counter++;
                continue;
            }
            if (time > STARTING_TIME){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTime = time/60 + ":" + (((time - (time/60)*60) < 10 ? "0" : "") + (time - (time/60)*60));
                System.out.println(currentTime + " - " + "Курьер зашел в метро на станции " + currentStation);
                counter++;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nextStation = nextStationRandom();
            int stationsTraveledCounter;
            if (nextStation[0] != previousStation[0]){
                stationsTraveledCounter = changeLine();
            }
            else{
                stationsTraveledCounter = Math.abs(nextStation[1] - previousStation[1]);
            }
            time += stationsTraveledCounter * TIME_BETWEEN_STATIONS;
            currentTime = time/60 + ":" + (((time - (time/60)*60) < 10 ? "0" : "") + (time - (time/60)*60));
            currentStation = METROPOLITAN[nextStation[0]][nextStation[1]];
            System.out.println(currentTime + " - " + "Курьер вышел на станции " + currentStation);
            int randomTimeOnStation = (int)(20 * Math.random() + 10);
            time += randomTimeOnStation;
            if (time >= STARTING_TIME + WORKING_DAY_DURATION){
                currentTime = time/60 + ":" + (((time - (time/60)*60) < 10 ? "0" : "") + (time - (time/60)*60));
                System.out.println(currentTime + " - " + "Курьер зашел в метро на станции " + currentStation + " и поехал домой.");
                counter++;
                System.out.println("Всего потратил на проезд " + counter * RIDE_COST + " грн.");
            }
            previousStation = nextStation;
        }
    }

    private static int changeLine() {
        int startLine = previousStation[0];
        int finishLine = nextStation[0];
        int startStation = previousStation[1];
        int finishStation = previousStation[1];
        int stationsCounter = 0;
        if (startLine == 0 && finishLine == 1){
            stationsCounter = Math.abs(startStation - KRESCHATIK_INDEX) + Math.abs(finishStation - MAIDAN_INDEX);
        }
        else if (startLine == 0 && finishLine == 2){
            stationsCounter = Math.abs(startStation - TEATRALNA_INDEX) + Math.abs(finishStation - ZOLOTI_VOROTA_INDEX);
        }
        else if (startLine == 1 && finishLine == 0){
            stationsCounter = Math.abs(startStation - MAIDAN_INDEX) + Math.abs(finishStation - KRESCHATIK_INDEX);
        }
        else if (startLine == 1 && finishLine == 2){
            stationsCounter = Math.abs(startStation - LVA_TOLSTOGO_INDEX) + Math.abs(finishStation - PALATS_SPORTU_INDEX);
        }
        else if (startLine == 2 && finishLine == 0){
            stationsCounter = Math.abs(startStation - ZOLOTI_VOROTA_INDEX) + Math.abs(finishStation - TEATRALNA_INDEX);
        }
        else if (startLine == 2 && finishLine == 1){
            stationsCounter = Math.abs(startStation - PALATS_SPORTU_INDEX) + Math.abs(finishStation - LVA_TOLSTOGO_INDEX);
        }
        return stationsCounter;
    }

    private static int[] nextStationRandom() {
        int[] output = new int[2];
        while (true) {
            output[0] = (int) (3 * Math.random());
            output[1] = (int) (18 * Math.random());
            if (output[0] == previousStation[0] && output[1] == previousStation[1])
                System.out.print("");
            else
                break;
        }
        return output;
    }
}