package pl.panda.trzy.nbp;

import java.math.BigDecimal;

public class Rate {
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public BigDecimal    getMid() {
        return mid;
    }

    public void setMid(BigDecimal mid) {
        this.mid = mid;
    }

    private String no;
    private String effectiveDate;
    private BigDecimal mid;
}
