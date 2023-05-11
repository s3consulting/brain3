package time;

import java.util.Random;

public class ExternalAttackInjector {

    public static Integer generateDayOfAttack(Integer day, Integer delta) {
        Random r = new Random();
        Integer beforeOrAfter = 0;
        Integer delay = 0;
        if (delta > 0) {
            delay = r.nextInt(delta);
            Double bb = r.nextDouble();
            if (bb >= .5) {
                beforeOrAfter = 1;
            } else {
                beforeOrAfter = -1;
            }
        }
        return day + beforeOrAfter * delay;
    }
}
