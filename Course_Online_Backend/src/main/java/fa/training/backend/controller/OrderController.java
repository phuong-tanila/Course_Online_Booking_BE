package fa.training.backend.controller;

import fa.training.backend.entities.Order;
import fa.training.backend.mapper.OrderMapper;
import fa.training.backend.model.OrderModel;
import fa.training.backend.services.OrderService;
import fa.training.backend.util.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    public SortOrder sortOrder;
    @Autowired
    public OrderMapper orderMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderModel>> getCourseByCategory(@PathVariable("userId") int userId,
                                                                @RequestParam(defaultValue = "0") Integer pageNo,
                                                                @RequestParam(defaultValue = "20") Integer pageSize,
                                                                @RequestParam(defaultValue = "buyDate,desc") String[] sort
    ) {
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrder.getSortOrder(sort)));
            List<Order> orders = orderService.getCoursesByUserId(userId, pageable);
            System.err.println(orders);
            List<OrderModel> result = orderMapper.toModelList(orders);
            if (result.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
