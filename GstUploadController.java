package com.example.RK8.Controller;

import com.example.RK8.Service.GstReturn2BService;
import com.example.RK8.Service.GstReturn3BService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/upload")
public class GstUploadController {

    private final GstReturn2BService gstReturn2BService;
    private final GstReturn3BService gstReturn3BService;

    public GstUploadController(
            GstReturn2BService gstReturn2BService,
            GstReturn3BService gstReturn3BService
    ) {
        this.gstReturn2BService = gstReturn2BService;
        this.gstReturn3BService = gstReturn3BService;
    }

    @PostMapping("/2b")
    public String upload2B(@RequestParam("file") MultipartFile file) {
        gstReturn2BService.upload2B(file);
        return "GSTR-2B uploaded successfully";
    }

    @PostMapping("/3b")
    public String upload3B(@RequestParam("file") MultipartFile file) {
        gstReturn3BService.upload3B(file);
        return "GSTR-3B uploaded successfully";
    }   

}

