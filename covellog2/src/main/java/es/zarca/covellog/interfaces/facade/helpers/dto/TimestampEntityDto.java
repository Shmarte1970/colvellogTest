package es.zarca.covellog.interfaces.facade.helpers.dto;

import java.util.Date;

/**
 *
 * @author francisco
 */
public class TimestampEntityDto {
    private Date createdDate;
    private Date updatedDate;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
         
}
