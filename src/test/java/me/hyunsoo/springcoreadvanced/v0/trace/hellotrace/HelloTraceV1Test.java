package me.hyunsoo.springcoreadvanced.v0.trace.hellotrace;

import me.hyunsoo.springcoreadvanced.v0.trace.TraceId;
import me.hyunsoo.springcoreadvanced.v0.trace.TraceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HelloTraceV1Test {

    @Autowired
    HelloTraceV1 traceV1;

    @Test
    void begin_end(){
        TraceStatus status = traceV1.begin("hello");
        traceV1.end(status);
    }

    @Test
    void begin_exception(){
        TraceStatus status = traceV1.begin("hello");
        traceV1.exception(status, new IllegalStateException());
    }

}