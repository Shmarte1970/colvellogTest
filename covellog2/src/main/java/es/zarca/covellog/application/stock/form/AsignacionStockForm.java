package es.zarca.covellog.application.stock.form;

import java.util.Objects;

/**
 *
 * @author francisco
 */
public class AsignacionStockForm {
    private String booking;
    private Integer stockId;

    public AsignacionStockForm(String booking, Integer stockId) {
        this.booking = booking;
        this.stockId = stockId;
    }

    public String getBooking() {
        return booking;
    }

    public void setBooking(String booking) {
        this.booking = booking;
    }

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.booking);
        hash = 47 * hash + Objects.hashCode(this.stockId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AsignacionStockForm other = (AsignacionStockForm) obj;
        if (!Objects.equals(this.booking, other.booking)) {
            return false;
        }
        if (!Objects.equals(this.stockId, other.stockId)) {
            return false;
        }
        return true;
    }

}
