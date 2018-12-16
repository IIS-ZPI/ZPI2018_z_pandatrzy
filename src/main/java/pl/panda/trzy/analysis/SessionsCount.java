package pl.panda.trzy.analysis;

public class SessionsCount {
    private Integer rise;

    public SessionsCount(Integer rise, Integer fall, Integer noChange) {
        this.rise = rise;
        this.fall = fall;
        this.noChange = noChange;
    }

    private Integer fall;
    private Integer noChange;

    public Integer getRise() {
        return rise;
    }

    public void setRise(Integer rise) {
        this.rise = rise;
    }

    public Integer getFall() {
        return fall;
    }

    public void setFall(Integer fall) {
        this.fall = fall;
    }

    public Integer getNoChange() {
        return noChange;
    }

    public void setNoChange(Integer noChange) {
        this.noChange = noChange;
    }
}
