package manage.mail.controller;

import com.alibaba.excel.EasyExcel;
import manage.mail.entity.ExcelData;
import manage.mail.entity.ExcelDataListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/upload")
public class TestController {
    @PostMapping
    public String upload(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return "File is empty";
        }
        EasyExcel.read(multipartFile.getInputStream(), ExcelData.class, new ExcelDataListener()).sheet().doRead();
        return "OK";
    }
}
