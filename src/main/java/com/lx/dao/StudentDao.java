package com.lx.dao;

import com.lx.pojo.Student;
import com.sun.org.apache.bcel.internal.generic.Select;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.Sql;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.annotatoin.SqlStatement;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 学生表： Mapper 接口
 * </p>
 *
 * @author lixing
 * @since 2020-03-11
 */
@SqlResource("common.student")
public interface StudentDao extends BaseMapper<Student> {
    List<Student> selectByPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
}
