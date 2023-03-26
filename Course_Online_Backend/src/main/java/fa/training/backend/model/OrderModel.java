package fa.training.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import fa.training.backend.entities.OrderDetail;
import fa.training.backend.entities.User;
import fa.training.backend.model.CategoryModels.CategoryModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
public class OrderModel {
    @JsonProperty
    public int id;
    public Date buyDate;
    public String paymentMethod;
    public Boolean paymentStatus;
    public String coupon;
    public String paymentId;
    public Set<OrderDetailModel> orderDetails;
}
