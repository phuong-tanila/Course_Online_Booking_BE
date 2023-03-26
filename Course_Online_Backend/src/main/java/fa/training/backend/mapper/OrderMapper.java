package fa.training.backend.mapper;

import fa.training.backend.entities.Order;
import fa.training.backend.model.OrderModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderModel toModel(Order order);

    Order toEntity(OrderModel orderModel);

    List<Order> toEntityList(List<OrderModel> orderModels);

    List<OrderModel> toModelList(List<Order> orders);

}
