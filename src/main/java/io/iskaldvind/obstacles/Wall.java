package io.iskaldvind.obstacles;

import io.iskaldvind.participants.Participant;

public class Wall implements Obstacle {

    private final int HEIGHT;

    public Wall(int height) {
        this.HEIGHT = height;
    }

    @Override
    public void pass(Participant participant) {
        participant.jump(this.HEIGHT);
    }
}
