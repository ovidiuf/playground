package playground.spring.sia.chapterthree.tacocloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import playground.spring.sia.chapterthree.tacocloud.model.Order;
import playground.spring.sia.chapterthree.tacocloud.persistence.OrderRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    @GetMapping("/current")
    public String renderOrderForm() {

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {

        if (errors.hasErrors()) {

            return "orderForm";
        }

        orderRepository.save(order);

        sessionStatus.setComplete();

        return "redirect:/";
    }
}
