package time;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ExternalAttackKeeper {
    private Map<Integer, AttackData> attacks = new HashMap<>();
    private Integer delta;

    public ExternalAttackKeeper(Integer delta) {
        this.delta = delta;
    }

    public Map<Integer, AttackData> getAttacks() {
        return attacks;
    }

    public Integer getDelta() {
        return delta;
    }

    public void loadAttackFromFile(String dir, String attackFileName) throws IOException {
        String fileName = dir + "/" + attackFileName;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;

        while ((line = reader.readLine()) != null) {
            if(!line.trim().equalsIgnoreCase("")) {
                String[] data = line.split("\t");
                Integer day = Integer.valueOf(data[0]);
                AttackData attackData = new AttackData(data[1], data[2]);
                attacks.put(addRandomDelta(day), attackData);
            }
        }
        reader.close();
    }

    private Integer addRandomDelta(Integer day) {
        Random r = new Random();
        Integer dd = r.nextInt(delta);
        Integer nn = r.nextInt(10);
        Integer sign =1;
        if(nn<5){
            sign = -1;
        }
        return day + sign * dd;
    }

    public class AttackData {
        private String type;
        private String name;

        public AttackData(String type, String name) {
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

