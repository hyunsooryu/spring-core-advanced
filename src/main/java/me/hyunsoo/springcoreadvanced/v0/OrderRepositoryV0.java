package me.hyunsoo.springcoreadvanced.v0;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

    public void save(String itemId){
        if("ex".equals(itemId)){
            throw new IllegalStateException("예외 발생");
        }
        sleep(1000); //1초간 저장 시간 걸림.
    }


    private void sleep(int millis){
        try {
            Thread.sleep(millis);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }


}
