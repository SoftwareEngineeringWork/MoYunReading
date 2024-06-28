package com.zwj.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.zwj.utils.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${server.port}")
    private String port;

    private static final String ip = "http://localhost";

    @PostMapping("/upload")
    public ResponseEntity<Result<?>> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            // 获取上传的文件名
            String filename = file.getOriginalFilename();
            // 由于文件可能同名，因此在前面加上唯一标识
            String flag = IdUtil.fastSimpleUUID();
            // 设置存放路径
            String rootFilePath = "C:\\Users\\cxz\\Desktop\\library-management-master\\springboot\\src\\main\\resources\\files";
            // 确保路径存在，如果不存在则创建
            Path dir = Paths.get(rootFilePath);
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
            // 完整的文件保存路径
            String targetFilePath = rootFilePath + "\\" + flag + "_" + filename;

            // 使用 Spring 的 transferTo 方法保存文件
            file.transferTo(Paths.get(targetFilePath));

            // 使用 Hutool 写入文件的另一种方式
            // FileUtil.writeBytes(file.getBytes(), Paths.get(targetFilePath));

            // 返回路径
            return ResponseEntity.ok(Result.success(ip + ":" + port + "/api/files/" + flag + "_" + filename));
        } else {
            return ResponseEntity.ok(Result.error("上传失败，文件为空"));
        }
    }

    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        //获取上传的文件名
        String filename = file.getOriginalFilename();
        //由于文件可能同名,因此在前面加上唯一标识
        String flag = IdUtil.fastSimpleUUID();
        //设置存放路径
        String rootFilePath = System.getProperty("user.dir")+"/springboot/src/main/resources/files/"+ flag +"_"+ filename;
        //file.transferTo(Paths.get(rootFilePath));
        //hutool下的工具类,将文件写入路径中
        FileUtil.writeBytes(file.getBytes(),rootFilePath);
        //返回路径

        String url =ip + ":" + port + "/files/" + flag;
        JSONObject json = new JSONObject();
        json.set("errno",0);
        JSONArray array = new JSONArray();
        JSONObject data = new JSONObject();
        array.add(data);
        data.set("url",url);
        json.set("data",array);
        return json;
    }

    @GetMapping("/{flag}")
    public void getFile(HttpServletResponse response, @PathVariable String flag){
        //新建输出流
        OutputStream os;
        //文件上传的根路径
        String basePath = System.getProperty("user.dir")+"/springboot/src/main/resources/files/";
        //获取所有的文件名
        List<String> fileNames = FileUtil.listFileNames(basePath);
        //找到匹配的文件
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
        //通过输出流输出
        if (StrUtil.isNotEmpty(fileName)){
            try {
                response.addHeader("Content-Disposition","attachment;fileName=" + URLEncoder.encode(fileName,"UTF-8"));
                response.setContentType("application/octet-stream");
                //通过文件路径读取为字节流
                byte[] bytes = FileUtil.readBytes(basePath + fileName);
                //通过输出流返回文件
                os = response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            } catch (Exception e) {
                System.out.println("文件下载失败!");
            }
        }
    }

}
