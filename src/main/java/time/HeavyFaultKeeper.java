package time;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HeavyFaultKeeper {
    private Map<Integer, HeavyFaultData> heavyFaults = new HashMap<>();

    public HeavyFaultKeeper(){

    }

    public Map<Integer, HeavyFaultData> getHeavyFaults() {
        return heavyFaults;
    }

    public void loadHeavyFaultsFromFile(String dir, String heavyFaultsFileName) throws IOException {
        String fileName = dir + "/" + heavyFaultsFileName;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;

        while ((line = reader.readLine()) != null) {
            if(!line.equalsIgnoreCase("")) {
                String[] data = line.split(",");
                Integer day = Integer.valueOf(data[0]);
                HeavyFaultData heavyFaultData = new HeavyFaultData(data[1], data[2]);
                heavyFaults.put(day, heavyFaultData);
            }
        }
        reader.close();
    }

    public class HeavyFaultData {
        private String type;
        private String name;

        public HeavyFaultData(String type, String name) {
            this.type = type;
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }
}
