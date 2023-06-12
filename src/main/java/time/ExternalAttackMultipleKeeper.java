package time;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExternalAttackMultipleKeeper {

    private Map<Integer, List<MultipleAttackData>> attacks = new HashMap<>();

    public ExternalAttackMultipleKeeper() {
    }

    public Map<Integer, List<MultipleAttackData>> getAttacks() {
        return attacks;
    }

    public void loadAttackFromFile(String dir, String attackFileName) throws IOException {
        String fileName = dir + "/" + attackFileName;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String line;

        while ((line = reader.readLine()) != null) {
            if(!line.trim().equalsIgnoreCase("")) {
                String[] data = line.split("\t");
                Integer day = Integer.valueOf(data[0]);
                MultipleAttackData attackData = new MultipleAttackData(data[1], data[2]);
                if(attacks.get(day)==null) {
                    attacks.put(day, new ArrayList<>());
                }
                attacks.get(day).add(attackData);
            }
        }
        reader.close();
    }


}
