package com.lx.controller;

import com.alibaba.fastjson.JSON;
import com.lx.pojo.Student;
import com.lx.service.StudentService;
import com.lx.util.DownloadUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@RestController
@RequestMapping("/springboot")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     *
     * 添加学生信息
     * @param stu
     * @return student
     *
     */

    @PostMapping("/add")
    @ApiOperation(value = "添加用户信息，并返回添加的用户信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "添加成功，true或false代表可用或不可用"),
            @ApiResponse(code = 400, message = "请求参数有误，比如name不是指定值")
    })
    @ResponseBody
    public String addStudent(Student stu){
        return JSON.toJSONString(studentService.add(stu));
    }
    /**
     *
     * 查询学生信息
     * @param pageNum，pageSize
     * @return list
     *
     */
    @GetMapping(value = "/list/{pageNum}/{pageSize}")
    @ApiOperation(value = "查询用户信息，并返回指定页数的结果集")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查询成功"),
            @ApiResponse(code = 400, message = "请求参数有误，比如pageNum超出范围")
    })
    public String StudentList(@PathVariable(value = "pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize){
        return JSON.toJSONString(studentService.list(1,2));
    }
    /**
     *
     * 修改学生信息
     * @param stu
     * @return student
     *
     */

    @PutMapping("/update")
    @ApiOperation(value = "修改用户信息，并返回修改后的用户信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "修改成功"),
            @ApiResponse(code = 400, message = "请求参数有误，比如stu不是指定值")
    })
    public String UpdateStudent(@ApiParam(value = "要修改的数据", example = "id= 1，name=lisi，age=23")Student stu){
        return JSON.toJSONString(studentService.update(stu));
    }
    /**
     *
     * 删除学生信息
     * @param id
     *
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除用户信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除成功"),
            @ApiResponse(code = 400, message = "请求参数有误，比如id不是指定值")
    })
    public void Delete(@ApiParam(value = "根据id值查询数据", example = "1")Integer id){
       studentService.delete(id);
    }
    /**
     *
     * 导出学生信息
     * @param stu
     * @return student
     *
     */
    @Autowired
    private HttpSession session;
    @Autowired
    private HttpServletResponse response;

    @RequestMapping(value = "/printStudent",name = "导出学生信息方法")
    public void printStudent() throws Exception{

        List<Student>  studentList = studentService.findAll();

//        String projectRoot = session.getServletContext().getRealPath("");//此方法可以获取项目的根目录
        System.out.println(session.getServletContext().getRealPath(""));
        File filePath = ResourceUtils.getFile("classpath:xlsprint/student.xlsx");
//        filePath就是模板文件的路径
        FileInputStream inputStream = new FileInputStream(filePath);
//      读取用来导出学生信息的模板
        Workbook workbook = new XSSFWorkbook(inputStream);
//        获取工作表sheet
        Sheet sheet = workbook.getSheetAt(0);

        Row row = null;
        Cell cell = null;
        int rowIndex=2;

        for (Student stu : studentList) {
            row = sheet.createRow(rowIndex);
//            学生编号    学生姓名     学生年龄
            cell = row.createCell(0);
            cell.setCellValue(stu.getId());

            cell = row.createCell(1);
            cell.setCellValue(stu.getName());

            cell = row.createCell(2);
            cell.setCellValue(stu.getAge());

            rowIndex++;
        }

//        把workbook文件导出
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);  //把workbook的内容写入到缓存流中
        DownloadUtil.download(byteArrayOutputStream,response,"学生信息表.xlsx");

    }

}
