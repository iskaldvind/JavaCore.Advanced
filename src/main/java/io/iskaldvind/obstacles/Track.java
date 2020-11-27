package io.iskaldvind.obstacles;

import io.iskaldvind.participants.Participant;

public class Track implements Obstacle{

    private final int LENGTH;

    public Track(int length) {
        this.LENGTH = length;
    }

    @Override
    public void pass(Participant participant) {
        participant.run(this.LENGTH);
    }
}
