package io.iskaldvind.participants;

public class Robot implements Participant {

    static final String TYPE = "Robot";
    private final int RUN_LIMIT;
    private final int JUMP_LIMIT;
    private final String MODEL;
    private Boolean lastResult;

    public Robot(String model) {
        this.MODEL = model;
        this.lastResult = true;
        this.RUN_LIMIT = 8000 + (int) Math.round(Math.random() * 4000);
        this.JUMP_LIMIT = 40 + (int) Math.round(Math.random() * 20);
    }

    @Override
    public void run(int distance) {
        if (lastResult) {
            if (distance <= RUN_LIMIT) {
                System.out.println(TYPE + " " + MODEL + " runs " + distance);
            } else {
                System.out.println(TYPE + " " + MODEL + " fails to run " + distance);
                lastResult = false;
            }
        }
    }

    @Override
    public void jump(int height) {
        if (lastResult) {
            if (height <= JUMP_LIMIT) {
                System.out.println(TYPE + " " + MODEL + " jumps " + height);
            } else {
                System.out.println(TYPE + " " + MODEL + " fails to jump " + height);
                lastResult = false;
            }
        }
    }
}
