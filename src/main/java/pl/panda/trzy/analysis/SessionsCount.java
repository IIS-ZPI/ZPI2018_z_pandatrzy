package pl.panda.trzy.analysis;

public class SessionsCount {

    private int rise;
    private int fall;
    private int noChange;

    public SessionsCount(int rise, int fall, int noChange) {
        this.rise = rise;
        this.fall = fall;
        this.noChange = noChange;
    }

    public int getRise() {
        return rise;
    }

    public void setRise(int rise) {
        this.rise = rise;
    }

    public int getFall() {
        return fall;
    }

    public void setFall(int fall) {
        this.fall = fall;
    }

    public int getNoChange() {
        return noChange;
    }

    public void setNoChange(int noChange) {
        this.noChange = noChange;
    }
}
