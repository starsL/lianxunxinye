package com.lx.service;

import com.lx.dao.StudentDao;
import com.lx.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    public Student add(Student stu) {
        studentDao.insertTemplate(stu,true);
        return studentDao.unique(stu.getId());
    }

    public List<Student> list(Integer pageNum, Integer pageSize) {
        List<Student> all = studentDao.selectByPage(pageNum,pageSize);
        return all;
    }
    /*public List<Student> list() {
        List<Student> all = studentDao.all();
        return all;
    }*/

    public Student update(Student stu) {
        studentDao.updateById(stu);
        return studentDao.unique(stu.getId());
    }

    public void delete(Integer id) {
        studentDao.deleteById(id);
    }
    public List<Student> findAll(){
        List<Student> all = studentDao.all();
        return all;
    }
}
