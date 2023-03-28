package fa.training.backend.controller;

import fa.training.backend.entities.Course;
import fa.training.backend.entities.Order;
import fa.training.backend.entities.OrderDetail;
import fa.training.backend.entities.User;
import fa.training.backend.exception.RecordNotFoundException;
import fa.training.backend.mapper.OrderMapper;
import fa.training.backend.model.ExceptionResponse;
import fa.training.backend.model.OrderModel;
import fa.training.backend.services.CourseService;
import fa.training.backend.services.OrderDetailService;
import fa.training.backend.services.OrderService;
import fa.training.backend.util.SortOrder;
import java.security.Principal;
import java.util.Date;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import javax.annotation.security.RolesAllowed;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    public SortOrder sortOrder;
    @Autowired
    public OrderMapper orderMapper;
    @Autowired
    public CourseService courseService;
    @Autowired
    public OrderDetailService orderDetailService;

    @RolesAllowed("US")
    @GetMapping("/")
    public ResponseEntity<List<OrderModel>> getCourseByCategory(
            Principal principal,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(defaultValue = "buyDate,desc") String[] sort
    ) {
        try {
            User currentUser = (User) ((Authentication) principal).getPrincipal();
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortOrder.getSortOrder(sort)));
            List<Order> orders = orderService.getCoursesByUserId(currentUser.getId(), pageable);
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

    @RolesAllowed("US")
    @PostMapping("/checkout")
    public ResponseEntity checkOut(
            Principal principal,
            @RequestBody List<Integer> courseIds,
            @RequestParam(defaultValue = "") String paymentId,
            @RequestParam(defaultValue = "false") boolean paymentStatus,
            @RequestParam(defaultValue = "") String coupon,
            @RequestParam(defaultValue = "cod") String paymentMethod
    ) {
        User currentUser = (User) ((Authentication) principal).getPrincipal();
        System.out.println(currentUser);
        Order order = new Order();
        order.setCoupon(coupon);
        order.setPaymentId(paymentId);
        order.setPaymentStatus(paymentStatus);
        order.setBuyDate(new Date());
        order.setPaymentMethod(paymentMethod);
        Set<OrderDetail> orderDetails = new HashSet<>();
        order.setUser(currentUser);
//        order.setId();
        Order createdOrder = orderService.saveOrder(order);
        System.out.println(order);
        for (Integer courseId : courseIds) {
            try {
                Course course = courseService.findById(courseId);
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setCourse(course);
                orderDetail.setPrice(course.getTuitionFee());
                System.out.println(order);
                orderDetail.setOrder(createdOrder);
                orderDetailService.save(orderDetail);
                orderDetails.add(orderDetail);
            } catch (RecordNotFoundException ex) {
                return new ResponseEntity<ExceptionResponse>(
                        new ExceptionResponse(
                                "RecordNotFoundException",
                                "One of course ids passed in is not found!"
                        ),
                        HttpStatus.BAD_REQUEST
                );
            }
        }
//        order.setOrderDetails(orderDetails);

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
