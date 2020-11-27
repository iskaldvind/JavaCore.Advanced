package io.iskaldvind.participants;

public class Human implements Participant {

    static final String TYPE = "Human";
    private final int RUN_LIMIT;
    private final int JUMP_LIMIT;
    private final String NAME;
    private Boolean lastResult;

    public Human(String name) {
        this.NAME = name;
        this.lastResult = true;
        this.RUN_LIMIT = 800 + (int) Math.round(Math.random() * 400);
        this.JUMP_LIMIT = 80 + (int) Math.round(Math.random() * 40);
    }

    @Override
    public void run(int distance) {
        if (lastResult) {
            if (distance <= RUN_LIMIT) {
                System.out.println(TYPE + " " + NAME + " runs " + distance);
            } else {
                System.out.println(TYPE + " " + NAME + " fails to run " + distance);
                lastResult = false;
            }
        }
    }

    @Override
    public void jump(int height) {
        if (lastResult) {
            if (height <= JUMP_LIMIT) {
                System.out.println(TYPE + " " + NAME + " jumps " + height);
            } else {
                System.out.println(TYPE + " " + NAME + " fails to jump " + height);
                lastResult = false;
            }
        }
    }
}
