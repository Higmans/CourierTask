import java.util.*;

public class MainClass {
    public static final String[] STATION_NAMES_RED = {"Лісова", //0
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
    public static final String[] STATION_NAMES_BLUE = {"Теремки",
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
    public static final String[] STATION_NAMES_GREEN = {"Червоний Хутір",
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
    public static final int MINUTES_IN_HOUR = 60;
    public static final int RIDE_COST = 2; // жетон - 2 грн
    public static final int STARTING_TIME = 9 * MINUTES_IN_HOUR; // начало работы - 9 часов утра
    public static final int WORKING_DAY_DURATION = 8 * MINUTES_IN_HOUR; // 8 часов рабочий день
    public static final int TIME_BETWEEN_STATIONS = 2; // время между станциями в минутах
    public static final int KRESCHATIK_INDEX = 7;
    public static final int MAIDAN_INDEX = 10;
    public static final int TEATRALNA_INDEX = 8;
    public static final int ZOLOTI_VOROTA_INDEX = 13;
    public static final int LVA_TOLSTOGO_INDEX = 9;
    public static final int PALATS_SPORTU_INDEX = 12;
    public static final String[][] METROPOLITAN = {STATION_NAMES_RED, STATION_NAMES_BLUE, STATION_NAMES_GREEN};
    public static final String STARTING_STATION = METROPOLITAN[0][0]; // начальная станция - Лысова
    public static final int SHOW_FULL_INFO = 1;
    public static final int SHOW_EXPENSES = 2;
    public static final int SHOW_STATION_INFO = 3;
    public static final int EXIT = 4;
    public static int time = STARTING_TIME;
    public static String currentStation = STARTING_STATION;
    public static int counter = 0;
    public static int[] previousStation = {0,0};
    public static int[] nextStation;
    public static String currentTime;
    public static HashMap<String, RideInfo> ridesInfo = new HashMap<String, RideInfo>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        generateRides();
        while (running) {
            switch (askAction()){
                case SHOW_FULL_INFO:
                    showFullInfo();
                    break;
                case SHOW_EXPENSES:
                    showExpenses();
                    break;
                case SHOW_STATION_INFO:
                    showStationInfo();
                    break;
                case EXIT:
                    running = false;
                    break;
                default:
                    System.out.println("Ошибка! Неверный пункт меню");
                    break;
            }
        }
    }

    private static void showStationInfo() {
        ArrayList<String> allStations = new ArrayList<String>();
        allStations.addAll(Arrays.asList(METROPOLITAN[0]));
        allStations.addAll(Arrays.asList(METROPOLITAN[1]));
        allStations.addAll(Arrays.asList(METROPOLITAN[2]));
        System.out.println("Введите название станции");
        String input = sc.next();
        if (allStations.contains(input) && ridesInfo.containsKey(input)){
            showStationInfo(input);
        }
        else if (allStations.contains(input)){
            System.out.println("Курьер ни разу не был на станции " + input);
        }
        else {
            System.out.println("Такой станции не существует в природе!");
        }
    }

    private static void showStationInfo(String input) {
        System.out.println(input + " - посещений: " + ridesInfo.get(input).count + ", время: " + ridesInfo.get(input).time);
    }

    private static void showExpenses() {
        System.out.println("Всего поездок - " + counter);
        System.out.println("Всего потратил на проезд - " + counter * RIDE_COST + " грн.");
    }

    private static int askAction() {
        System.out.println();
        System.out.println("Что вы хотите сделать дальше?");
        System.out.println("1. Вывести полную информацию о поездках курьера за сегодня");
        System.out.println("2. Вывести количество и стоимость поездок за сегодня");
        System.out.println("3. Узнать информацию по конкретной станции");
        System.out.println("4. Выйти из программы");
        int input;
        try {
            input = sc.nextInt();
        } catch (Exception e) {
            System.out.println("Ошибка! Введите только номер пункта меню!");
            input = -1;
        }
        return input;
    }

    private static void generateRides() {
        while (time < STARTING_TIME + WORKING_DAY_DURATION){
            if (counter == 0){
                currentTime = time/60 + ":" + (((time - (time/60)*60) < 10 ? "0" : "") + (time - (time/60)*60));
                System.out.println(currentTime + " - " + "Курьер начал работу. Зашел в метро на станции " + currentStation);
                counter++;
                saveInfo(currentStation, currentTime);
                continue;
            }
            if (time > STARTING_TIME){
                currentTime = time/60 + ":" + (((time - (time/60)*60) < 10 ? "0" : "") + (time - (time/60)*60));
                waitALittle(500);
                System.out.println(currentTime + " - " + "Курьер зашел в метро на станции " + currentStation);
                counter++;
            }
            nextStation = nextStationRandom();
            int stationsTraveledCounter;
            if (nextStation[0] != previousStation[0]){
                waitALittle(500);
                stationsTraveledCounter = changeLine();
            }
            else{
                stationsTraveledCounter = Math.abs(nextStation[1] - previousStation[1]);
            }
            time += stationsTraveledCounter * TIME_BETWEEN_STATIONS;
            currentTime = time/60 + ":" + (((time - (time/60)*60) < 10 ? "0" : "") + (time - (time/60)*60));
            currentStation = METROPOLITAN[nextStation[0]][nextStation[1]];
            waitALittle(500);
            System.out.println(currentTime + " - " + "Курьер вышел на станции " + currentStation);
            int randomTimeOnStation = (int)(20 * Math.random() + 10);
            time += randomTimeOnStation;
            if (time >= STARTING_TIME + WORKING_DAY_DURATION){
                currentTime = time/60 + ":" + (((time - (time/60)*60) < 10 ? "0" : "") + (time - (time/60)*60));
                waitALittle(500);
                System.out.println(currentTime + " - " + "Курьер зашел в метро на станции " + currentStation + " и поехал домой.");
                counter++;
            }
            saveInfo(currentStation, currentTime);
            previousStation = nextStation;
        }
    }

    private static void showFullInfo() {
        System.out.println("Красная ветка:");
        for (int j = 0; j < METROPOLITAN[0].length; j++){
            if (ridesInfo.containsKey(METROPOLITAN[0][j])){
                System.out.println(METROPOLITAN[0][j] + " - посещений: " + ridesInfo.get(METROPOLITAN[0][j]).count + ", время: " + ridesInfo.get(METROPOLITAN[0][j]).time);
            }
        }
        System.out.println("Синяя ветка:");
        for (int j = 0; j < METROPOLITAN[1].length; j++){
            if (ridesInfo.containsKey(METROPOLITAN[1][j])){
                System.out.println(METROPOLITAN[1][j] + " - посещений: " + ridesInfo.get(METROPOLITAN[1][j]).count + ", время: " + ridesInfo.get(METROPOLITAN[1][j]).time);
            }
        }
        System.out.println("Зеленая ветка:");
        for (int j = 0; j < METROPOLITAN[2].length; j++){
            if (ridesInfo.containsKey(METROPOLITAN[2][j])){
                System.out.println(METROPOLITAN[2][j] + " - посещений: " + ridesInfo.get(METROPOLITAN[2][j]).count + ", время: " + ridesInfo.get(METROPOLITAN[2][j]).time);
            }
        }
        System.out.println("----------------------------------------------");
        showExpenses();
    }

    private static void saveInfo(String currentStation, String currentTime) {
        ridesInfo.put(currentStation, new RideInfo(currentStation, currentTime));
    }

    private static void waitALittle(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static int changeLine() {
        int startLine = previousStation[0];
        int finishLine = nextStation[0];
        int startStation = previousStation[1];
        int finishStation = previousStation[1];
        int stationsCounter = 0;
        int timeChange;
        if (startLine == 0 && finishLine == 1){
            timeChange = time + Math.abs(startStation - KRESCHATIK_INDEX) * TIME_BETWEEN_STATIONS;
            String timeChangeString = timeChange/60 + ":" + (((timeChange - (timeChange/60)*60) < 10 ? "0" : "") + (timeChange - (timeChange/60)*60));
            System.out.println(timeChangeString + " - Курьер пересел со станции Хрещатик на станцию Майдан Незалежності");
            stationsCounter = Math.abs(startStation - KRESCHATIK_INDEX) + Math.abs(finishStation - MAIDAN_INDEX);
        }
        else if (startLine == 0 && finishLine == 2){
            timeChange = time + Math.abs(startStation - TEATRALNA_INDEX) * TIME_BETWEEN_STATIONS;
            String timeChangeString = timeChange/60 + ":" + (((timeChange - (timeChange/60)*60) < 10 ? "0" : "") + (timeChange - (timeChange/60)*60));
            System.out.println(timeChangeString + " - Курьер пересел со станции Театральна на станцию Золоті Ворота");
            stationsCounter = Math.abs(startStation - TEATRALNA_INDEX) + Math.abs(finishStation - ZOLOTI_VOROTA_INDEX);
        }
        else if (startLine == 1 && finishLine == 0){
            timeChange = time + Math.abs(startStation - MAIDAN_INDEX) * TIME_BETWEEN_STATIONS;
            String timeChangeString = timeChange/60 + ":" + (((timeChange - (timeChange/60)*60) < 10 ? "0" : "") + (timeChange - (timeChange/60)*60));
            System.out.println(timeChangeString + " - Курьер пересел со станции Майдан Незалежності на станцию Хрещатик");
            stationsCounter = Math.abs(startStation - MAIDAN_INDEX) + Math.abs(finishStation - KRESCHATIK_INDEX);
        }
        else if (startLine == 1 && finishLine == 2){
            timeChange = time + Math.abs(startStation - LVA_TOLSTOGO_INDEX) * TIME_BETWEEN_STATIONS;
            String timeChangeString = timeChange/60 + ":" + (((timeChange - (timeChange/60)*60) < 10 ? "0" : "") + (timeChange - (timeChange/60)*60));
            System.out.println(timeChangeString + " - Курьер пересел со станции Площа Льва Толстого на станцию Палац Спорту");
            stationsCounter = Math.abs(startStation - LVA_TOLSTOGO_INDEX) + Math.abs(finishStation - PALATS_SPORTU_INDEX);
        }
        else if (startLine == 2 && finishLine == 0){
            timeChange = time + Math.abs(startStation - ZOLOTI_VOROTA_INDEX) * TIME_BETWEEN_STATIONS;
            String timeChangeString = timeChange/60 + ":" + (((timeChange - (timeChange/60)*60) < 10 ? "0" : "") + (timeChange - (timeChange/60)*60));
            System.out.println(timeChangeString + " - Курьер пересел со станции Золоті Ворота на станцию Театральна");
            stationsCounter = Math.abs(startStation - ZOLOTI_VOROTA_INDEX) + Math.abs(finishStation - TEATRALNA_INDEX);
        }
        else if (startLine == 2 && finishLine == 1){
            timeChange = time + Math.abs(startStation - PALATS_SPORTU_INDEX) * TIME_BETWEEN_STATIONS;
            String timeChangeString = timeChange/60 + ":" + (((timeChange - (timeChange/60)*60) < 10 ? "0" : "") + (timeChange - (timeChange/60)*60));
            System.out.println(timeChangeString + " - Курьер пересел со станции Палац Спорту на станцию Площа Льва Толстого");
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