package com.ztkj.platform.update.Utils;
import java.io.File;

/**
 * @Author: zhang-jun
 * @Date: 2020/8/8 15:48
 * @Description: 文件操作类
 */
public class FileUtils {

    private static String BashLocal=new PathUtils().getBaseLocal();

    private static boolean initBashLocal(){
        File file = new File(BashLocal);
        if(!PathUtils.isWindows()){
            file.setWritable(true,false);
        }
        boolean mkdir=true;
        if(!file.exists()){
            mkdir = file.mkdir();
        }
        return mkdir;
    }

    public static boolean makeDir(String firstdir,String secondDir){
        if(!initBashLocal()){
            return false;
        }
        File file = new File(BashLocal+PathUtils.parator+firstdir);
        boolean mkdir=true;
        if(!file.exists()){
            mkdir = file.mkdir();
        }
        if(mkdir){
            file =new File(BashLocal+PathUtils.parator+firstdir+PathUtils.parator+secondDir);
            boolean  mksedir=true;
            if(!file.exists()){
               mksedir = file.mkdir();
            }
            if(mksedir){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }

    }

    public static File getFile(String firstdir,String secondDir,String fileName){
        initBashLocal();
        File file = new File(BashLocal+PathUtils.parator+firstdir);
        if(!file.exists()){
          return null;
        }
        file =new File(BashLocal+PathUtils.parator+firstdir+PathUtils.parator+secondDir);
        if(!file.exists()){
            return null;
        }
        file =new File(BashLocal+PathUtils.parator+firstdir+PathUtils.parator+secondDir+PathUtils.parator+fileName);
        if(!file.exists()){
            return null;
        }
        return file;
    }
    public static File getFilePath(String firstdir,String secondDir){
        initBashLocal();
        File file = new File(BashLocal+PathUtils.parator+firstdir);
        if(!file.exists()){
            return null;
        }
        file =new File(BashLocal+PathUtils.parator+firstdir+PathUtils.parator+secondDir);
        if(!file.exists()){
            return null;
        }
        return file;
    }
    public static File getAvailableNameFile(String FilePath,String FileName){
        File file = new File(FilePath+FileName);
       String  FileNameNoteSufix=FileName.substring(0,FileName.lastIndexOf("."));
       String FileSufix=FileName.substring(FileName.lastIndexOf("."),FileName.length());
        boolean isout=true;
        int i=0;
       while(isout){
           //如果文件存在
           i++;
           if(file.exists()){
               String FileNameTemp=FileNameNoteSufix + i+FileSufix;
               file=new File(FilePath+FileNameTemp);
           }else{
               isout=false;
           }
       }
       return file;
    }

  /*  public static void main(String[] args) {
        System.out.println(PathUtils.getBaseLocal());
    }*/
}
