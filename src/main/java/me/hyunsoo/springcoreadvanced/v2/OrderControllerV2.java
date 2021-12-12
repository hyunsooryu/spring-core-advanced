package me.hyunsoo.springcoreadvanced.v2;

import lombok.RequiredArgsConstructor;
import me.hyunsoo.springcoreadvanced.v0.trace.TraceStatus;
import me.hyunsoo.springcoreadvanced.v0.trace.hellotrace.HelloTraceV1;
import me.hyunsoo.springcoreadvanced.v0.trace.hellotrace.HelloTraceV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderServiceV2;

    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId){

       TraceStatus status = null;
        try {
            status = trace.begin("OrderControllerV2.request()");
            orderServiceV2.orderItem(status.getTraceId(), itemId);
            trace.end(status);
        }catch (Exception e){
            trace.exception(status, e);
            //예외를 먹어버린다. 예외가 나가야 한다. 그건 그대로 남겨야 하기때문에 예외를 배출 시켜줘야한다.
            throw e;
        }

        return "ok";
    }
}
