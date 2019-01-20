package pl.panda.trzy.analysis;

import java.util.ArrayList;
import java.util.List;

public class SessionsCount extends AbstractOperation {

    private static final String RISE_HEADER = "RISE";
    private static final String FALL_HEADER = "FALL";
    private static final String NO_CHANGE_HEADER = "NO_CHANGE";

    private int rise;
    private int fall;
    private int noChange;

    SessionsCount(int rise, int fall, int noChange) {
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

    @Override
    public List<String> getValuesList(){
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(this.rise));
        list.add(String.valueOf(this.fall));
        list.add(String.valueOf(this.noChange));
        return list;
    }

    @Override
    public List<String> getHeaders(){
        List<String> list = new ArrayList<>();
        list.add(RISE_HEADER);
        list.add(FALL_HEADER);
        list.add(NO_CHANGE_HEADER);
        return list;
    }
}
