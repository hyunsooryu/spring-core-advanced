package me.hyunsoo.springcoreadvanced.v2;


import lombok.RequiredArgsConstructor;
import me.hyunsoo.springcoreadvanced.v0.trace.TraceId;
import me.hyunsoo.springcoreadvanced.v0.trace.TraceStatus;
import me.hyunsoo.springcoreadvanced.v0.trace.hellotrace.HelloTraceV1;
import me.hyunsoo.springcoreadvanced.v0.trace.hellotrace.HelloTraceV2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepositoryV2;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId beforeTraceId, String itemId) {
        TraceStatus status = null;
        try {
            status = trace.beginSync(beforeTraceId,"OrderServiceV2.orderItem()");
            orderRepositoryV2.save(status.getTraceId(), itemId);
            trace.end(status);
        }catch (Exception e){
            trace.exception(status, e);
            throw e;
        }
    }

}
