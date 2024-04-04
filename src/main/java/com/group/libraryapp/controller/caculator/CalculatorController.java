package com.group.libraryapp.controller.caculator;

import com.group.libraryapp.dto.calculator.request.CalculatorAddRequest;
import com.group.libraryapp.dto.calculator.request.CalculatorMultiplyRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
//Ctrl_alt-o: import 문을 한번에 정리하고 싶을 때
@RestController
public class CalculatorController {
    @GetMapping("/add") //GET   /add
    public int addTwoNumbers(CalculatorAddRequest request){
        //http에 들어가는 숫자 2개와 이 함수의 인자를 연결시키기 위해 이자의 데이터형 앞에
        //@RequestParam 어노테이션 사용해줌.
        return request.getNumber1() + request.getNumber2();
        //단어 중간에 커서 놓고, Ctrl+w 하면 적당한 영역을 드래그 잡아줌
    }

    @PostMapping("/multiply")   //Post  /multiply
    public int multiplyTwoNumbers(@RequestBody CalculatorMultiplyRequest request){
        //GET에서 Query에서의 인자를 받아줄때 @RequestParam을 사용해준 것처럼
        //POST에서는 Body를 통해 들어온 속성들을 @RequestBody를 통해 객체로 넘겨준다.
        return request.getNumber1()* request.getNumber2();
    }
}
