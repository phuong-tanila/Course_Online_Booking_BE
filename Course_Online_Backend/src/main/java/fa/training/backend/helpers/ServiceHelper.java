package fa.training.backend.helpers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceHelper {
    @Getter
    @Setter
    @AllArgsConstructor
    private class OrderDTO {
        private String property;
        private Sort.Direction direction;
    }
    public static Pageable getPageable(Integer pageNo, Integer pageSize, HashMap<String, String> orderHashMap){

        List<Sort.Order> sortOrders = new ArrayList<>();

        orderHashMap.forEach((property, direction) -> {

            sortOrders.add(new Sort.Order(
                    direction.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
                    property
                    )
            );
        });
        return PageRequest.of(pageNo, pageSize, Sort.by(sortOrders));
    }
}
