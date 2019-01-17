package pl.panda.trzy.nbp;

import java.math.BigDecimal;
import java.util.Date;

public class Rate {

    public Rate(BigDecimal mid) {
        this.mid = mid;
    }

    public Rate(Date effectiveDate, BigDecimal mid) {
        this.effectiveDate = effectiveDate;
        this.mid = mid;
    }

    public Rate() {
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public BigDecimal    getMid() {
        return mid;
    }

    public void setMid(BigDecimal mid) {
        this.mid = mid;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    private String no;
    private Date effectiveDate;
    private BigDecimal mid;
}
