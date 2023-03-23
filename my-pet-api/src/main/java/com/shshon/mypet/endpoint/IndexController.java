package com.shshon.mypet.endpoint;

import com.shshon.mypet.endpoint.v1.response.ApiResponseV1;
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
        return "redirect:/api/docs/index.html";
    }

    @GetMapping("/check")
    @ResponseBody
    public ApiResponseV1<Object> check() {
        Map<String, String> body = new HashMap<>();
        return ApiResponseV1.ok(body);
    }
}
