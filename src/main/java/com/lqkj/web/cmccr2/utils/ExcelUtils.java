package com.lqkj.web.cmccr2.utils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用easy excel导出EXCEL的工具方法
 */
public class ExcelUtils {

    /**
     * 根据以“,”分隔的字符串
     * 构建EXCEL表头
     * EXCEL导出时构建
     *
     * @param strs 以“,”分隔的字符串
     * @return
     */
    public static List<List<String>> loadHead(String strs) {
        List<List<String>> list = new ArrayList<>();
        String[] strings = strs.split(",");
        for (int i = 0; i < strings.length; i++) {
            List<String> stringList = new ArrayList<>();
            stringList.add(strings[i]);
            list.add(stringList);
        }
        return list;
    }

    /**
     * 根据任意长度的参数数组
     * 构建对象，超出长度的参数被舍弃
     *
     * @param columns 参数数组
     * @return
     */
    public static ExcelModel loadExcelModel(List<String> columns) {
        ExcelModel excelModel = new ExcelModel();

        Integer length = columns.size() > 30 ? 30 : columns.size();
        for (int i = 0; i < length; i++) {
            String setMethod = "setColumn" + (i + 1);
            try {
                excelModel.getClass()
                        .getDeclaredMethod(setMethod, String.class)
                        .invoke(excelModel, columns.get(i));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return excelModel;
    }

    /**
     * 构建单一sheet的下载excel流
     *
     * @param clazz     实体类
     * @param dataList  数据列表
     * @param sheetName sheet名称
     * @param fileName  文件名称
     * @return 文件流
     * @throws IOException IO异常
     */
    public static ResponseEntity<InputStreamResource> downloadOneSheetExcel(Class<? extends BaseRowModel> clazz,
                                                                            List<? extends BaseRowModel> dataList,
                                                                            String sheetName, String fileName)
            throws IOException {
        try{

                FileOutputStream out = new FileOutputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
                ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
                Sheet sheet = new Sheet(1, 0, clazz);
                sheet.setSheetName(sheetName);
                writer.write(dataList, sheet);
                writer.finish();
                return loadFromOutputSteam(out, fileName);
            }catch(IOException e){
                e.printStackTrace();
                return null;
        }
    }

    /**
     * 读取单sheet的Excel
     * @param inputStream excel输入流
     * @param clazz 类
     * @return
     */
    public static List<Object> readOneSheetExcel(InputStream inputStream, Class<? extends BaseRowModel> clazz){
        AnalysisEventListener<T> listener = new ExcelListener();

        ExcelReader excelReader = new ExcelReader(inputStream, null, listener, true);
        excelReader.read(new Sheet(1, 1, clazz));
        List<Object> dataList = ((ExcelListener) listener).getDatas();
        //((ExcelListener) listener).clearData();

        return dataList;
    }

    /**
     * 构建多sheet下载流
     * @param clazzList 类列表
     * @param headList 工作表表头列表
     * @param dataList 数据列表
     * @param sheetNameList 工作表表名列表
     * @param fileName 下载文件名
     */
    public static ResponseEntity<InputStreamResource> downloadMultipleSheetExcel(
            List<Class<? extends BaseRowModel>> clazzList,
            List<List<List<String>>> headList,
            List<List<? extends BaseRowModel>> dataList,
            List<String> sheetNameList,
            String fileName)
            throws IOException {

        FileOutputStream out = new FileOutputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
        ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX, true);
        for(int i = 0; i < headList.size(); i ++){
            Sheet sheet = new Sheet(1 + i, 0, clazzList.get(i));
            sheet.setSheetName(sheetNameList.get(i));
            sheet.setHead(headList.get(i));
            writer.write(dataList.get(i), sheet);
        }
        writer.finish();
        try {
            return loadFromOutputSteam(out, fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 读取多sheet的Excel
     * @param inputStream excel输入流
     * @param clazzList 类列表
     * @return
     */
    public static List<List<Object>> readMultipleSheetExcel(InputStream inputStream,
                                                      List<Class<? extends BaseRowModel>> clazzList){
        List<List<Object>> readList = new ArrayList<>();

        AnalysisEventListener<T> listener = new ExcelListener();
        ExcelReader excelReader = new ExcelReader(inputStream, null, listener, true);

        for(int i = 0; i < clazzList.size(); i++){
            excelReader.read(new Sheet(i + 1, 1, clazzList.get(i)));
            List<Object> dataList = new ArrayList<>();
            dataList.addAll(((ExcelListener) listener).getDatas());
            readList.add(dataList);
            ((ExcelListener) listener).clearData();
        }

        return readList;
    }

    /**
     * 用输出流、文件名
     * 构建下载流
     * @param outputStream 输出流
     * @param fileName 下载文件名
     */
    private static ResponseEntity<InputStreamResource> loadFromOutputSteam(OutputStream outputStream,
                                                                           String fileName) throws IOException {
        outputStream.close();
        InputStream in = new FileInputStream(FileUtils.getTempDirectoryPath() + "/template.xlsx");
        String newName = URLEncoder.encode(fileName, "UTF-8");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + newName)
                .body(new InputStreamResource(in));
    }

    /**
     * 字符串去除特殊字符
     * @param str 原字符串
     * @return 去除特殊字符否的字符串
     */
    public static String removeSpecialCharacter(String str){
        return str.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？“”]+","");
    }

    /**
     * 根据类、列表长度
     * 构建类列表
     * EXCEL导出多个sheet
     * 且映射相同类时使用
     * @param clazz 类
     * @param length 列表长度
     * @return
     */
    public static List<Class<? extends BaseRowModel>> loadClazzListWithOneClass(
            Class<? extends BaseRowModel> clazz,
            Integer length){
        List<Class<? extends BaseRowModel>> clazzList = new ArrayList<>();
        for(int i = 0; i < length; i++){
            clazzList.add(clazz);
        }

        return clazzList;
    }

    /**
     * 根据列表长度
     * 构建空数据列表
     * 导出下载模板时使用
     * @param length 列表长度
     * @return
     */
    public static List<List<? extends BaseRowModel>> loadEmptyDataList(Integer length){
        List<List<? extends BaseRowModel>> dataList = new ArrayList<>();
        for(int i = 0; i < length; i++){
            dataList.add(new ArrayList<>());
        }

        return dataList;
    }
}
