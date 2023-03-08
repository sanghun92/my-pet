package com.shshon.mypet.endpoint;

import com.shshon.mypet.endpoint.v1.ApiResponseV1;
import com.shshon.mypet.endpoint.v1.ErrorResponseV1;
import com.shshon.mypet.endpoint.v1.OkResponseV1;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    @GetMapping("/favicon.ico")
    @ResponseBody
    public void returnNoFavicon() {
    }

    @GetMapping("/")
    public String returnRestDocs() {
        return "redirect:/docs/index.html";
    }

    @GetMapping("/check")
    @ResponseBody
    public ApiResponseV1<Object> check() {
        Map<String, String> body = new HashMap<>();
        return ApiResponseV1.ok(body);
    }
}
