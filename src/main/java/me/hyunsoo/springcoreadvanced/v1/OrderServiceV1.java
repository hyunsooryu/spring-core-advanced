package me.hyunsoo.springcoreadvanced.v1;


import lombok.RequiredArgsConstructor;
import me.hyunsoo.springcoreadvanced.v0.trace.TraceStatus;
import me.hyunsoo.springcoreadvanced.v0.trace.hellotrace.HelloTraceV1;
import me.hyunsoo.springcoreadvanced.v0.trace.hellotrace.HelloTraceV2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepositoryV1;
    private final HelloTraceV1 trace;

    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = trace.begin("OrderServiceV1.orderItem()");
            orderRepositoryV1.save(itemId);
            trace.end(status);
        }catch (Exception e){
            trace.exception(status, e);
            throw e;
        }
    }

}
