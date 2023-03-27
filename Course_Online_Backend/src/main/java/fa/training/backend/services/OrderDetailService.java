/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fa.training.backend.services;

import fa.training.backend.entities.OrderDetail;
import fa.training.backend.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 15tha
 */
@Service
public class OrderDetailService {
    @Autowired OrderDetailRepository orderDetailRepository;
    
    public OrderDetail save(OrderDetail orderDetail){
        return orderDetailRepository.save(orderDetail);
    }
}
