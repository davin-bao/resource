package com.davin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author davin.bao
 * @date 2024/6/5
 */
@Controller
public class IndexController {
    @GetMapping(value="/")
    @ResponseBody
    public String index()
    {
        return "index";
    }
}
