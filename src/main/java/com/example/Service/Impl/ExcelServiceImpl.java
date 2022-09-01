package com.example.Service.Impl;

import com.example.Service.ExcelService;
import com.example.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public Result ExcelImport(File multipartfile) {
        log.info(multipartfile.getName());
        log.info(String.valueOf(multipartfile.isFile()));
        List l = new ArrayList();
        return null;
    }

}
