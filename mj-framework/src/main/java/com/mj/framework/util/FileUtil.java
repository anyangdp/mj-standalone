package com.mj.framework.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class FileUtil {
	
	public static final String[]RESTYPE_IMG = {"png","jpg","gif","bmp","jpe","jfif"};
	public static final String[]RESTYPE_VIDEO = {"mp4","avi","mov","wmv","swf","mp3","wma"};
	public static final String[]RESTYPE_DOC = {"doc","docx","xls","xlsx","ppt","pptx","pdf","txt","html","htm"};
	public static List<String> IMGList = Arrays.asList(RESTYPE_IMG);
	public static List<String> VIDEOList = Arrays.asList(RESTYPE_VIDEO);
	public static List<String> DOCList = Arrays.asList(RESTYPE_DOC);
    /**
     *  根据路径删除指定的目录或文件，无论存在与�?
     *@param sPath  要删除的目录或文�?
     *@return 删除成功返回 true，否则返�?false�?
     */
	
    public static boolean DeleteFolder(String sPath) {
    	boolean flag = false;
    	File file = new File(sPath);
        // 判断目录或文件是否存�?
        if (!file.exists()) {  // 不存在返�?false
            return flag;
        } else {
            // 判断是否为文�?
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return deleteDirectory(sPath);
            }
        }
    }
    
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件�?
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
    	boolean flag = false;
    	File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    /**
     * 删除目录（文件夹）以及目录下的文�?
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔�?
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则�?�?
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文�?包括子目�?
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文�?
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
					break;
				}
            } //删除子目�?
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
					break;
				}
            }
        }
        if (!flag) {
			return false;
		}
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 拷贝文件
     * @param args
     */
    
    
    
    /**
     * 传入文件名(不带目录)返回文件后缀   test.txt-->txt
     * @param fileName
     * @return
     */
    public static String getPreFix(String fileName){
    	File f = new File(fileName);
    	String prefix = f.getName().substring(f.getName().lastIndexOf(".")+1);
    	return prefix;
    }

    /**
     * 传入文件名(不带目录)返回文件名(不带后缀)   test.txt-->test
     * @param fileName
     * @return
     */
    public static String getPreName(String fileName){
    	File f = new File(fileName);
    	String name = f.getName().substring(0,f.getName().lastIndexOf("."));
    	return name;
    }
    
    /**
     * java File.java类中的length()方法返回的文件长度是长以字节为单位，下面的方法是根据字节数格式化相应的其他单位(KB、MB、GB等)。
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
		double kiloByte = size/1024;
		if(kiloByte < 1) {
			return size + "Byte(s)";
		}
		
		double megaByte = kiloByte/1024;
		if(megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}
		
		double gigaByte = megaByte/1024;
		if(gigaByte < 1) {
			BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}
		
		double teraBytes = gigaByte/1024;
		if(teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}
    
    public static String showIcon(String filename){
    	String icon = "res/resource/default_other.png";
    	String prefix = FileUtil.getPreFix(filename).toLowerCase();
    	if(IMGList.contains(prefix)){
    		icon = filename.replace("_origin", "_200x200");
    	}
    	if(VIDEOList.contains(prefix)){
    		icon = "res/resource/default_video.png";
    	}
    	if(DOCList.contains(prefix)){
    		icon = "res/resource/default_doc.png";
    	}
    	return icon;
    }
    
    /**
     * 判断是否是图片类型---通过文件名后缀简单处理
     * @param filename
     * return boolean
     */
    public static Boolean isImg(String filename){
    	Boolean flag = false;
    	String prefix = FileUtil.getPreFix(filename);
    	if(IMGList.contains(prefix.toLowerCase())){
    		flag = true;
    	}
    	return flag;
    }
    
	public static byte[] m_decode(String bytes) {
	   return org.apache.commons.codec.binary.Base64.decodeBase64(bytes);
    }
    
	public static void m_fwrite(byte[] b, String path){
		FileOutputStream fop = null;
		File file;
		try {
			file = new File(path);
			fop = new FileOutputStream(file);
			if (!file.exists()) {
				file.createNewFile();
			}
		   fop.write(b);
		   fop.flush();
		   fop.close();
		}catch (IOException e){
		   e.printStackTrace();
		}finally{
		   try{
			   if(fop != null){
				   fop.close();
			   }
		   }catch(IOException e){
			   e.printStackTrace();
		   }
		}
	}
	
	/**
	 * 追加内容 
	 * @param filePath
	 */
	public static void addContent(String filePath, String content) {
		FileWriter fw = null;
		try {
			//如果文件存在，则追加内容；如果文件不存在，则创建文件，并加入头文件
			File f = new File(filePath);
			fw = new FileWriter(f, true);
			
			PrintWriter pw = new PrintWriter(fw);
			pw.println(content);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static InputStream getResourcesFileInputStream(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
	}

	public static String getPath() {
		return FileUtil.class.getResource("/").getPath();
	}

	public static File createNewFile(String pathName) {
		File file = new File(getPath() + pathName);
		if (file.exists()) {
			file.delete();
		} else {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
		}
		return file;
	}

	public static File readFile(String pathName) {
		return new File(getPath() + pathName);
	}

	public static File readUserHomeFile(String pathName) {
		return new File(System.getProperty("user.home") + File.separator + pathName);
	}

	/**
	 * 创建文件
	 *
	 * @param file 文件
	 * @return true or false
	 */
	public static Boolean createFile(MultipartFile file, String fileName, String filePath, String name) {
		String finalFilePath = toString(filePath, LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), "/", name);
		try {
			File localFile = new File(finalFilePath);
			if (localFile.exists()) {
				localFile.delete();
			} else {
				if (!localFile.getParentFile().getParentFile().exists()) {
					localFile.getParentFile().getParentFile().mkdirs();
				}
				if (!localFile.getParentFile().exists()) {
					localFile.getParentFile().mkdirs();
				}
			}
			FileOutputStream out = new FileOutputStream(finalFilePath);
			out.write(file.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return new File(finalFilePath).exists();
	}

	public static String toString(String... str) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (str != null && str.length > 0) {
			for (int i = 0; i < str.length; ) {
				stringBuilder.append(str[i++]);
			}
		}
		return stringBuilder.toString();
	}

    public static void main(String[] args) {

		String fileName = "E:\\fff.txt";
		File f = new File(fileName);
		if(f.isFile()){//存在
			addContent(fileName,"sss");
		}else{
			addContent(fileName,"userId|userName|carPlate|phoneNumber|userAddress|carType|begin|end|changeTime|cardID|");
			addContent(fileName,"aaa");
		}
		
		
    }


}
