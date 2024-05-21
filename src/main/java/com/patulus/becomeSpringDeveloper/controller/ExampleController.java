package com.patulus.becomeSpringDeveloper.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {

    @GetMapping("/thymeleaf/example")
    // Model 객체는 View로 값을 넘겨주는 객체이며 따로 생성할 필요 없이 인자로 선언하기만 하면 스프링이 알아서 생성
    public String thymeleafExample(Model model) {
        Person examplePerson = new Person(1L, "홍길동", 11, List.of("운동", "독서"));

        model.addAttribute("person", examplePerson);
        model.addAttribute("today", LocalDate.now());

        // <SpringBootProject>/resources/templates/ 에서 해당하는 html 파일을 찾고 없으면 해당 문자열을 브라우저에서 보여준다.
        return "example";
    }

    @Setter
    @Getter
    @AllArgsConstructor
    class Person {
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }

}
