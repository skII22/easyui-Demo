package com.example.Service;

import com.example.common.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public interface ExcelService {
    Result ExcelImport(File file);
}
