package me.hyunsoo.springcoreadvanced.v0.trace.hellotrace;


import lombok.extern.slf4j.Slf4j;
import me.hyunsoo.springcoreadvanced.v0.trace.TraceId;
import me.hyunsoo.springcoreadvanced.v0.trace.TraceStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component //싱글톤으로 사용하기 위해, 스프링 빈으로 등록한다. 컴포넌트 스캔의 대상이 된다.
public class HelloTraceV2 {

    private static final String START_PREFIX = "-->";
    private static final String COMPLETE_PREFIX = "<--";
    private static final String EX_PREFIX = "<X-";

    public TraceStatus begin(String message){
        TraceId traceId = new TraceId(); //level 0, id : UUID로 생성해서 넘어 옴.
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTimeMs, message);
    }

    public TraceStatus beginSync(TraceId beforeTraceId, String message){
        TraceId nextId = beforeTraceId.createNextId(); //이전꺼에서 지금꺼의 정보를 만듬
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}", nextId.getId(), addSpace(START_PREFIX, nextId.getLevel()), message);
        return new TraceStatus(nextId, startTimeMs, message);
    }


    public void end(TraceStatus status){
        complete(status, null);
    }

    public void exception(TraceStatus status, Exception e){
        complete(status, e);
    }

    public void complete(TraceStatus status, Exception e){
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if(e == null){
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTimeMs);
        }else{
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(),resultTimeMs ,e.toString());
        }

    }







    private static String addSpace(String prefix, int level){
        StringBuilder sb = new StringBuilder();
        //자신의 레벨이때만, prefix 처리
        //이외는 no prefix showing
        for(int i = 0; i < level; i++){
            sb.append(i == level - 1 ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }

}
