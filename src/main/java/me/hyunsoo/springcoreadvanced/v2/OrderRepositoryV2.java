package me.hyunsoo.springcoreadvanced.v2;


import lombok.RequiredArgsConstructor;
import me.hyunsoo.springcoreadvanced.v0.trace.TraceId;
import me.hyunsoo.springcoreadvanced.v0.trace.TraceStatus;
import me.hyunsoo.springcoreadvanced.v0.trace.hellotrace.HelloTraceV1;
import me.hyunsoo.springcoreadvanced.v0.trace.hellotrace.HelloTraceV2;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV2 {

    private  final HelloTraceV2 trace;

    public void save(TraceId beforeTraceId, String itemId){

        TraceStatus status = null;
        try{
            status = trace.beginSync(beforeTraceId, "OrderRepositoryV2.save()");
            if("ex".equals(itemId)){
                throw new IllegalStateException("예외 발생");
            }
            sleep(1000); //1초간 저장 시간 걸림.
            trace.end(status);
        }catch (Exception e){
            trace.exception(status, e);
            throw  e;
        }
    }

    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
