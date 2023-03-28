/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fa.training.backend.repositories;

import fa.training.backend.entities.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 15tha
 */
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>{
    
}
