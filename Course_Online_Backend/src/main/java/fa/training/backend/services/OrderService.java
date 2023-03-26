package fa.training.backend.services;

import fa.training.backend.entities.Order;
import fa.training.backend.exception.RecordNotFoundException;
import fa.training.backend.helpers.ServiceHelper;
import fa.training.backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public List<Order> getCoursesByUserId(int userId, Pageable pageable) {
        List<Order> orders = orderRepository.getAllOrderByUserId(userId, pageable);
        return orders;
    }
    
    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }
//    public List<Order> findAll() {
//        List<Order> orders = orderRepository.findAll();
//        System.err.println(orders);
//        return orders;
//    }
}
