package services;

import beansLab.entities.Shot;

import java.time.LocalDateTime;

public class ShotGenerator {

    public static Shot generateShot(double xIn, double yIn, double rIn){
        LocalDateTime start = LocalDateTime.now();
        long scriptTime = System.nanoTime();
        boolean GR = check(xIn, yIn, rIn);
        scriptTime = System.nanoTime() - scriptTime;

        return new Shot(xIn, yIn, rIn, GR, start, scriptTime);
    }

    private static boolean check(double x, double y, double r) {
        if ((y == 0 && Math.abs(x) <= r) || (x == 0 && Math.abs(y) <= r)) {
            return true;
        }
        if (y > 0) {
            if (x > 0) {
                return (x * x + y * y <= r * r);
            } else {
                return false;
            }
        } else {
            if (x > 0) {
                return ((x + (-y)) <= r);
            } else {
                return ((-x <= r) && (-y <= r));
            }
        }
    }
}
