package com.lujunyu.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Excel 和 bean 之间的转化。
 * <p>
 * 读取支持xlsx和xls格式。
 */
public class ExcelUtils {
    /**
     * 将数据写入到EXCEL文档
     *
     * @param list     数据集合
     * @param filePath 文件路径
     * @throws Exception
     */
    public static <T> void writeToFile(List<T> list, String filePath) throws Exception {
        // 创建并获取工作簿对象
        Workbook wb = getWorkBook(list);
        // 写入到文件
        FileOutputStream out = new FileOutputStream(filePath);
        wb.write(out);
        out.close();
    }

    /**
     * 获得Workbook对象
     *
     * @param list 数据集合
     * @return Workbook
     * @throws Exception
     */
    public static <T> Workbook getWorkBook(List<T> list) throws Exception {
        // 创建工作簿
        Workbook wb = new SXSSFWorkbook();

        if (list == null || list.size() == 0)
            return wb;

        // 创建一个工作表sheet
        Sheet sheet = wb.createSheet();
        // 申明行
        Row row = sheet.createRow(0);
        // 申明单元格
        Cell cell = null;

        CreationHelper createHelper = wb.getCreationHelper();

        Field[] fields = list.get(0).getClass().getDeclaredFields();

        XSSFCellStyle titleStyle = (XSSFCellStyle) wb.createCellStyle();
        // 设置前景色
        titleStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(159, 213, 183)));
        Font font = wb.createFont();
        // 设置字体
        titleStyle.setFont(font);

        int columnIndex = 0;
        Excel excel = null;
        for (Field field : fields) {
            field.setAccessible(true);
            excel = field.getAnnotation(Excel.class);
            if (excel == null || excel.skip() == true) {
                continue;
            }
            // 列宽注意乘256
            sheet.setColumnWidth(columnIndex, excel.width() * 256);
            // 写入标题
            cell = row.createCell(columnIndex);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(excel.name());

            columnIndex++;
        }

        int rowIndex = 1;

        CellStyle cs = wb.createCellStyle();

        for (T t : list) {
            row = sheet.createRow(rowIndex);
            columnIndex = 0;
            Object o = null;
            for (Field field : fields) {

                field.setAccessible(true);

                // 忽略标记skip的字段
                excel = field.getAnnotation(Excel.class);
                if (excel == null || excel.skip() == true) {
                    continue;
                }
                // 数据
                cell = row.createCell(columnIndex);

                o = field.get(t);
                // 如果数据为空，跳过
                if (o == null)
                    continue;

                // 处理日期类型
                if (o instanceof Date) {
                    cs.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
                    cell.setCellStyle(cs);
                    cell.setCellValue((Date) field.get(t));
                } else if (o instanceof Double || o instanceof Float) {
                    cell.setCellValue((Double) field.get(t));
                } else if (o instanceof Boolean) {
                    Boolean bool = (Boolean) field.get(t);
                    cell.setCellValue(bool);

                } else if (o instanceof Integer) {

                    Integer intValue = (Integer) field.get(t);
                    cell.setCellValue(intValue);
                } else {
                    cell.setCellValue(field.get(t).toString());
                }
                columnIndex++;
            }
            rowIndex++;
        }
        return wb;
    }

    /**
     *
     * @param in
     * @param clazz
     * @param xlsx 是否是xlsx结尾的Excel
     * @return
     * @throws Exception
     */
    public static <T> List<T> readFromStream(InputStream in, Class<T> clazz, boolean xlsx) throws Exception {
        if (in == null || clazz == null) {
            return null;
        }
        if (xlsx) {
            return readFromWorkbook(new XSSFWorkbook(in), clazz);
        } else {
            return readFromWorkbook(new HSSFWorkbook(in), clazz);
        }
    }

    /**
     * 从文件读取数据，最好是所有的单元格都是文本格式，日期格式要求yyyy-MM-dd HH:mm:ss。
     *
     * @param file Excel文件
     * @return
     * @throws Exception
     */
    public static <T> List<T> readFromFile(File file, Class<T> clazz) throws Exception {
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }
        if (file.getName().endsWith("xlsx")) {
            return readFromWorkbook(new XSSFWorkbook(new FileInputStream(file)), clazz);
        } else if (file.getName().endsWith("xls")) {
            return readFromWorkbook(new HSSFWorkbook(new FileInputStream(file)), clazz);
        }
        throw new RuntimeException("不支持的文件类型");
    }

    private static <T> List<T> readFromWorkbook(Workbook wb, Class<T> clazz) throws Exception {
        Field[] fields = clazz.getDeclaredFields();

        Map<String, String> textToKey = new HashMap<String, String>();

        Excel _excel = null;
        for (Field field : fields) {
            _excel = field.getAnnotation(Excel.class);
            if (_excel == null || _excel.skip() == true) {
                continue;
            }
            textToKey.put(_excel.name(), field.getName());
        }
        Sheet sheet = wb.getSheetAt(0);
        Row title = sheet.getRow(0);
        // 标题数组，后面用到，根据索引去标题名称，通过标题名称去字段名称用到 textToKey
        String[] titles = new String[title.getPhysicalNumberOfCells()];
        for (int i = 0; i < title.getPhysicalNumberOfCells(); i++) {
            titles[i] = title.getCell(i).getStringCellValue();
        }

        List<T> list = new ArrayList<T>();
        T t = null;

        int rowIndex = 0;
        int columnCount = titles.length;
        Cell cell = null;
        Row row = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (Iterator<Row> it = sheet.rowIterator(); it.hasNext(); ) {

            row = it.next();
            if (rowIndex++ == 0) {
                continue;
            }
            if (row == null) {
                break;
            }
            t = clazz.newInstance();
            for (int i = 0; i < columnCount; i++) {
                cell = row.getCell(i);
                readCellContent(textToKey.get(titles[i]), fields, cell, t, sdf);
            }
            list.add(t);
        }
        return list;
    }

    /**
     * 从单元格里面读取数据。
     *
     * @param key    当前单元格对应的Bean字段
     * @param fields Bean所有的字段数组
     * @param cell   单元格对象
     * @param t      转化的对象。
     * @throws Exception
     */
    private static <T> void readCellContent(String key, Field[] fields, Cell cell, T t, DateFormat df) throws Exception {
        Object o = null;
        try {
            switch (cell.getCellTypeEnum()) {
                case _NONE:
                    break;
                case NUMERIC:
                    o = cell.getNumericCellValue();
                    break;
                case STRING:
                    o = cell.getStringCellValue();
                    break;
                case FORMULA:
                    o = cell.getCellFormula();
                    break;
                case BLANK:
                    break;
                case BOOLEAN:
                    o = cell.getBooleanCellValue();
                    break;
                case ERROR:
                    break;
            }
            if (o == null) {
                return;
            }
            //设置bean的值。

            for (Field field : fields) {
                if (field.getName().equals(key)) {
                    Class<?> type = field.getType();
                    field.setAccessible(true);
                    if (type.equals(Date.class)) {
                        if (o.getClass().equals(Date.class)) {
                            field.set(t, o);
                        } else {
                            field.set(t, df.parse(o.toString()));
                        }
                    } else if (type.equals(String.class)) {
                        if (o.getClass().equals(String.class)) {
                            field.set(t, o);
                        } else {
                            field.set(t, o.toString());
                        }
                    } else if (type.equals(BigDecimal.class)) {
                        if (o.getClass().equals(BigDecimal.class)) {
                            field.set(t, o);
                        } else {
                            field.set(t, BigDecimal.valueOf(Double.parseDouble(o.toString())));
                        }
                    } else if (type.equals(Boolean.class)) {
                        if (o.getClass().equals(Boolean.class)) {
                            field.set(t, o);
                        } else {
                            field.set(t, Boolean.parseBoolean(o.toString()));
                        }
                    } else if (type.equals(Long.class) || (type.equals(long.class))) {
                        BigDecimal decimal = new BigDecimal(o.toString());
                        field.set(t, decimal.longValue());
                    } else if (type.equals(Integer.class) || (type.equals(int.class))) {
                        BigDecimal decimal = new BigDecimal(o.toString());
                        field.set(t, decimal.intValue());
                    } else if (type.equals(Float.class) || (type.equals(float.class))) {
                        BigDecimal decimal = new BigDecimal(o.toString());
                        field.set(t, decimal.floatValue());
                    } else if (type.equals(Double.class) || (type.equals(double.class))) {
                        BigDecimal decimal = new BigDecimal(o.toString());
                        field.set(t, decimal.doubleValue());
                    }
                }
            }
        } catch (Exception ex) {
            if (cell != null) {
                throw new RuntimeException(String.format("%s行%s列数据格式不正确", cell.getRowIndex() + 1, cell.getColumnIndex() + 1));
            }
            throw ex;
        }
    }
}