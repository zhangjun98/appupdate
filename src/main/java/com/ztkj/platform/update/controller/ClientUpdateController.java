package com.ztkj.platform.update.controller;

import com.ztkj.platform.update.Utils.FileUtils;
import com.ztkj.platform.update.beans.ProductEntity;
import com.ztkj.platform.update.beans.ResponseData;
import com.ztkj.platform.update.common.CommonResponse;
import com.ztkj.platform.update.common.ResponseCode;
import com.ztkj.platform.update.common.haveNewVersionResponse;
import com.ztkj.platform.update.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 09:25
 * @Description:   客户端 进行下载与 获取文件大小的控制器
 */
@RestController
@RequestMapping("/client")
@Api("用户查询更新信息控制器")
public class    ClientUpdateController {
    @Autowired
    ClientService clientService;

    @GetMapping ("/version/{productpackge}/{version}")
    @ResponseBody
    @ApiOperation("比对版本信息的接口")
    private CommonResponse getProductNewVersion(@PathVariable("productpackge") String productid, @PathVariable("version") Integer version){
        Integer newVersion = clientService.getNewVersion(productid);
        if(newVersion==null){
            //返回 无对应版本
            return new CommonResponse(ResponseCode.NO_This_Product_Version,"服务器中无此产品信息");
        }else if(newVersion.intValue()<=version.intValue()){
            //返回 是新版本
            return new CommonResponse(ResponseCode.IsNewVersion,"本地版本为最新版本");
        }else{
            ProductEntity entity = clientService.getProduct(productid);
            return new haveNewVersionResponse<ResponseData>(ResponseCode.Has_New_Version,"有待更新版本",new ResponseData(entity));
        }
    }
    @GetMapping("/download/{productpackge}")
    private CommonResponse DownLoadFile(@PathVariable("productpackge") String productid,
                                        HttpServletResponse response,HttpServletRequest request,
                                        @RequestHeader(required = false) String range) throws Exception {
     return DownLoadFile(productid,null,null,response,request,range);
    }
    @GetMapping("/download/{productpackge}/{version}")
    private CommonResponse DownLoadFile(@PathVariable("productpackge") String productid,
                                        @PathVariable(required = false) Integer version,
                                        HttpServletResponse response,HttpServletRequest request,
                                        @RequestHeader(required = false) String range) throws Exception {
        return DownLoadFile(productid,version,null,response,request,range);
    }

    /**
     * 文件下载
     * @param productid
     * @param version
     */
    @GetMapping("/download/{productpackge}/{version}/{filename}")
    @ResponseBody
    @ApiOperation("文件下载的接口")
    private CommonResponse DownLoadFile(        @PathVariable("productpackge") String productid,
                                                @PathVariable(required = false) Integer version,
                                                @PathVariable(value = "filename",required = false) String filename,
                                                HttpServletResponse response,HttpServletRequest request,
                                                @RequestHeader(required = false) String range) throws Exception {
        ProductEntity product = clientService.getProduct(productid);
        if(product==null){
            return new CommonResponse(ResponseCode.NO_This_Product_Version,"没有该版本号对应产品");
        }
        File file=null;
        if(version==null){
            Integer versionCode = product.getVersionCode();
            String productFileName = product.getProductFileName();
            file= FileUtils.getFile(productid, String.valueOf(versionCode),productFileName);
        }else{
            //获取历史版本
            ProductEntity product1 = clientService.getHistroyProductOne(productid, version);
            if(product1==null){
                return new CommonResponse(ResponseCode.NO_This_Product_Version,"没有该版本号对应产品");
            }else {
                if(filename!=null){
                    file= FileUtils.getFile(productid, String.valueOf(version),filename);
                }else{
                    file= FileUtils.getFile(productid, String.valueOf(version),product1.getProductFileName());
                }

            }
        }
        if(file==null||!file.exists()){
            return new CommonResponse(ResponseCode.NO_This_Product_Version,"没有该版本号对应产品");
        }
        //开始下载位置
        long startByte = 0;
        //结束下载位置
        long endByte = file.length() - 1;
        //有range的话
        if (range != null && range.contains("bytes=") && range.contains("-")) {
            //http状态码要为206
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            range = range.substring(range.lastIndexOf("=") + 1).trim();
            String[] ranges = range.split("-");
            try {
                //判断range的类型
                if (ranges.length == 1) {
                    //类型一：bytes=-2343
                    if (range.startsWith("-")) {
                        endByte = Long.parseLong(ranges[0]);
                    }
                    //类型二：bytes=2343-
                    else if (range.endsWith("-")) {
                        startByte = Long.parseLong(ranges[0]);
                    }
                }
                //类型三：bytes=22-2343
                else if (ranges.length == 2) {
                    startByte = Long.parseLong(ranges[0]);
                    endByte = Long.parseLong(ranges[1]);
                }
            } catch (NumberFormatException e) {
                startByte = 0;
                endByte = file.length() - 1;
            }
        } else {
            //没有ranges即全部一次性传输，需要用200状态码，这一行应该可以省掉，因为默认返回是200状态码
            response.setStatus(HttpServletResponse.SC_OK);
        }
        //要下载的长度（endByte为总长度-1，这时候要加回去）
        long contentLength = endByte - startByte + 1;
        //文件名
        String fileName = file.getName();
        //文件类型
        String contentType = request.getServletContext().getMimeType(fileName);
        if (request.getHeader(HttpHeaders.USER_AGENT).contains("MSIE")) {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } else {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }
        //各种响应头设置
        //参考资料：https://www.ibm.com/developerworks/cn/java/joy-down/index.html
        response.setHeader("Accept-Ranges", "bytes");
        response.setContentType(contentType);
        response.setHeader("Content-Type", contentType);
        //参考资料：http://hw1287789687.iteye.com/blog/2188500
        response.setHeader("Content-Disposition", "inline;filename="+ fileName);
        response.setHeader("Content-Length", String.valueOf(contentLength));
        // [要下载的开始位置]-[结束位置]/[文件总大小]
        response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + file.length());
        BufferedOutputStream outputStream = null;
        RandomAccessFile randomAccessFile = null;
        //已传送数据大小
        long transmitted = 0;
        try {
            randomAccessFile = new RandomAccessFile(file, "r");
            outputStream = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[4096];
            int len = 0;
            randomAccessFile.seek(startByte);
            while ((transmitted + len) <= contentLength && (len = randomAccessFile.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
                transmitted += len;
                //下载太快了，停下
               // Thread.sleep(10);
            }
            //处理不足buff.length部分
            if (transmitted < contentLength) {
                len = randomAccessFile.read(buff, 0, (int) (contentLength - transmitted));
                outputStream.write(buff, 0, len);
                transmitted += len;
            }
            outputStream.flush();
            response.flushBuffer();
            randomAccessFile.close();
           // System.out.println("下载完毕：" + startByte + "-" + endByte + "：" + transmitted);
        } catch (ClientAbortException e) {
           // System.out.println("用户停止下载：" + startByte + "-" + endByte + "：" + transmitted);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new CommonResponse(ResponseCode.Download_Scuess,"下载成功");
    }
        @GetMapping("/123")
        private void test(){
    System.out.println("流利");
}
}
