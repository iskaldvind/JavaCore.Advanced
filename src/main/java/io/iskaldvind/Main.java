package io.iskaldvind;

import io.iskaldvind.obstacles.*;
import io.iskaldvind.participants.*;

public class Main {

    public static void main(String[] args) {

        Participant[] participants = {
                new Human("Barak Obama"),
                new Dog("Muhtar"),
                new Robot("Terminator"),
                new Human("Ivan Susanin"),
                new Dog("Sharik"),
                new Robot("Electronik")
        };

        Obstacle[] obstacles = {
                new Track(100),
                new Wall(50),
                new Track(300),
                new Wall(60),
                new Wall(70),
                new Track(1000)
        };

        for (Obstacle obstacle: obstacles) {
            for (Participant participant: participants) {
                obstacle.pass(participant);
            }
            System.out.println("---");
        };
    }
}
