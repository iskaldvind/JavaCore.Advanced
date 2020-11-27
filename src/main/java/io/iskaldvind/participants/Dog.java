package io.iskaldvind.participants;

public class Dog implements Participant {

    static final String TYPE = "Dog";
    private final int RUN_LIMIT;
    private final int JUMP_LIMIT;
    private final String NICKNAME;
    private Boolean lastResult;

    public Dog(String nickname) {
        this.NICKNAME = nickname;
        this.lastResult = true;
        this.RUN_LIMIT = 4200 + (int) Math.round(Math.random() * 1600);
        this.JUMP_LIMIT = 160 + (int) Math.round(Math.random() * 80);
    }

    @Override
    public void run(int distance) {
        if (lastResult) {
            if (distance <= RUN_LIMIT) {
                System.out.println(TYPE + " " + NICKNAME + " runs " + distance);
            } else {
                System.out.println(TYPE + " " + NICKNAME + " fails to run " + distance);
                lastResult = false;
            }
        }
    }

    @Override
    public void jump(int height) {
        if (lastResult) {
            if (height <= JUMP_LIMIT) {
                System.out.println(TYPE + " " + NICKNAME + " jumps " + height);
            } else {
                System.out.println(TYPE + " " + NICKNAME + " fails to jump " + height);
                lastResult = false;
            }
        }
    }
}
