public class App {
    public static void main(String[] args) throws Exception {
        ConfigReader configReader = new ConfigReader();
        int maxTick=Integer.parseInt(configReader.values[0]);
        int queueCapacity=Integer.parseInt(configReader.values[1]);
        int terminalRotation = Integer.parseInt(configReader.values[2]);
        int minParcel = Integer.parseInt(configReader.values[3]);
        int maxParcel = Integer.parseInt(configReader.values[4]);
        int misrouting = (int)(Double.parseDouble(configReader.values[5])*100);
        String[] cityList = configReader.values[6].split(",");

        
        Simulation simulation = new Simulation( queueCapacity, minParcel, maxParcel, misrouting, terminalRotation, maxTick, cityList);
        simulation.runSimulation();

    }
}
