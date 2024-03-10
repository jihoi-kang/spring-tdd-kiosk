package sample.springtddkiosk.spring.api.controller.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import sample.springtddkiosk.spring.ControllerTestSupport;
import sample.springtddkiosk.spring.api.controller.order.request.OrderCreateRequest;

class OrderControllerTest extends ControllerTestSupport {

    @DisplayName("신규 주문을 등록한다")
    @Test
    void createOrder() throws Exception {
        // given
        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of("001", "002"))
            .build();

        // when & then
        mockMvc.perform(
                post("/api/v1/orders")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value("200"))
            .andExpect(jsonPath("$.status").value("OK"))
            .andExpect(jsonPath("$.message").value("OK"))
            .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("신규 주문을 등록할 때 상품번호는 1개 이상이여야한다")
    @Test
    void createOrderWithEmptyProductNumbers() throws Exception {
        // given
        OrderCreateRequest request = OrderCreateRequest.builder()
            .productNumbers(List.of())
            .build();

        // when & then
        mockMvc.perform(
                post("/api/v1/orders")
                    .content(objectMapper.writeValueAsString(request))
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("400"))
            .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
            .andExpect(jsonPath("$.message").value("상품 번호 목록은 필수입니다"))
            .andExpect(jsonPath("$.data").isEmpty());
    }


}