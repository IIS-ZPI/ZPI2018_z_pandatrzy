package pl.panda.trzy.analysis;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> getValuesList(){
        List<String> list = new ArrayList<>();
        list.add(String.valueOf(this.rise));
        list.add(String.valueOf(this.fall));
        list.add(String.valueOf(this.noChange));
        return list;
    }

    public List<String> getHeaders(){
        List<String> list = new ArrayList<>();
        list.add("RISE");
        list.add("FALL");
        list.add("NO_CHANGE");
        return list;
    }
}
