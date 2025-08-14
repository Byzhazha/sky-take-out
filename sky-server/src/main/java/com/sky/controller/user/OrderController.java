package com.sky.controller.user;

import com.sky.dto.OrdersPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.OrderVO;
import org.springframework.web.bind.annotation.GetMapping;
import com.sky.dto.OrdersPaymentDTO;
import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.Orderservice;
import com.sky.vo.OrderPaymentVO;
import com.sky.vo.OrderSubmitVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Tag(name = "用户端订单相关接口")
@Slf4j
public class OrderController {

    @Autowired
    private Orderservice orderservice;

    @PostMapping("/submit")
    @Operation(summary = "用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO ordersSubmitDTO) {
        log.info("用户下单，参数为{}", ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderservice.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付
     *
     * @param ordersPaymentDTO
     * @return
     */
    @PutMapping("/payment")
    @Operation(summary = "订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
        OrderPaymentVO orderPaymentVO = orderservice.payment(ordersPaymentDTO);
        log.info("生成预支付交易单：{}", orderPaymentVO);

        //【修改点】 模拟支付成功，直接调用paySuccess
        orderservice.paySuccess(ordersPaymentDTO.getOrderNumber());
        return Result.success(orderPaymentVO);
    }
    @GetMapping("/historyOrders")
    @Operation(summary = "历史订单查询")
    public Result<PageResult> historyOrders(OrdersPageQueryDTO ordersPageQueryDTO) {
        log.info("历史订单查询：{}", ordersPageQueryDTO);
        PageResult pageResult = orderservice.historyOrders(ordersPageQueryDTO);
        return Result.success(pageResult);
    }
    @GetMapping("/orderDetail/{id}")
    @Operation(summary = "查询订单详情")
    public Result<OrderVO> detail(@PathVariable("id") Long id) {
        OrderVO orderVO = orderservice.getOrderDetail(id);
        return Result.success(orderVO);
    }
    @PutMapping("/cancel/{id}")
    @Operation(summary = "取消订单")
    public Result cancel(@PathVariable("id") Long id) throws Exception {
        orderservice.cancelOrder(id);
        return Result.success();
    }
    @PostMapping("/repetition/{id}")
    @Operation(summary = "再来一单")
    public Result repetition(@PathVariable Long id) {
        orderservice.repetition(id);
        return Result.success();
    }

}