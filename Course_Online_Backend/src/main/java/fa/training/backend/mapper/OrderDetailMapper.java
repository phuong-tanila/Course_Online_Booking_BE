package fa.training.backend.mapper;

import fa.training.backend.entities.OrderDetail;
import fa.training.backend.model.OrderDetailModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailModel toModel(OrderDetail orderDetail);

    OrderDetail toEntity(OrderDetailModel orderDetailModel);

    List<OrderDetail> toEntityList(List<OrderDetailModel> orderDetailModels);

    List<OrderDetailModel> toModelList(List<OrderDetail> orderDetails);

}
