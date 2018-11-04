package com.example.dainty.superclass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class  MysqImpl {

	public void insert(String sql) {
		// TODO Auto-generated method stub

	}

	public void update(String sql) {
		// TODO Auto-generated method stub

	}

	public void delete(String sql) {
		// TODO Auto-generated method stub

	}

//	public List<Teacher> query(String sql) {
//		// TODO Auto-generated method stub
//		Connection con = null;
//		PreparedStatement stmt = null;
//		ResultSet rs = null;
//		List<Teacher> teachers = new ArrayList<Teacher>();
//		try{
//			//获取连接
//			con = JdbcUtil.getConnection();
//
//
//			//创建PreparedStatement
//			stmt = con.prepareStatement(sql);
//
//
//			rs=stmt.executeQuery();
//
//			while(rs.next()) {
//				Teacher t = new Teacher();
//				t.settId(rs.getInt("id"));
//				t.settName(rs.getString("name"));
//
//				teachers.add(t);
//			}
//
//			System.out.println(rs.toString());
//
//		}catch(Exception e){
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			JdbcUtil.close(con, stmt,rs);
//		}
//		return teachers;
//	}
//
//	public int addTeacher(Teacher t) {
//        String sql = "insert into Teahcer(studentNo, loginPwd, sex)value (?,?,?)";
//        Object[] params = { t.gettId(), t.gettName(),t.gettSex()};
//        return excuteDML(sql, params);
//    }


	public static int addClassTable(ClassTable c) {
    	String sql = "insert into ClassTable(classTableId, classLine, classColumn,teacherName,semester,inClass)value (?,?,?,?,?,?)";
    	Object[] params = {c.getClassTableId(),c.getLine(),c.getColumn(),c.getTeacherName(),c.getSemster(),c.getInClass()};
    	return excuteDML(sql, params);
    }


    public static PreparedStatement createPreparedStatement(Connection conn,
        String sql, Object[] params) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);

        if (params != null && params.length > 0) {
            int i = 1;// 标识“？”占位符的位置，默认为1，表示第一个？
            for (Object p : params) {
                pstmt.setObject(i, p);
                i++;
            }
        }
        return pstmt;
    }

    //公用的DML语句执行方法，执行增、删、改，返回影响行数
    public static int excuteDML(String sql, Object[] params) {
        // 获取Connection连接对象
        Connection conn = null;
        // 获取编译执行对象
        PreparedStatement pstmt = null;
        int result = 0;
        try {
            conn = JdbcUtil.getConnection();
            pstmt = createPreparedStatement(conn, sql, params);
            result = pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn, pstmt, null);
        }
        return result;
    }


    public void selectTeacherByTeacherName(String tName) {
    }



    public static List<ClassTable> selectClassTableByTeacherName(String tName,String sem) {
        Connection conn = null;
        // 获取编译执行对象
        PreparedStatement pstmt = null;
        ResultSet result = null ;
        List<ClassTable> classtables = new ArrayList<ClassTable>();


        String sql = "select * from classtable where teacherName = '" + tName+"'"+"&&semester='"+sem+"'";
        try {
            conn = JdbcUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            result = pstmt.executeQuery();

            while(result.next()) {

            	int classTableId  = Integer.parseInt(result.getString("classTableId"));
            	int classLine = Integer.parseInt(result.getString("classLine"));
            	int classColumn= Integer.parseInt(result.getString("classColumn"));
            	String teacherName= result.getString("teacherName");
            	String semester= result.getString("semester");
            	String inClass= result.getString("inClass");
            	ClassTable c = new ClassTable(classTableId);
            	c.setColumn(classColumn);
            	c.setLine(classLine);
            	c.setSemster(semester);
            	c.setInClass(inClass);
            	c.setTeacherName(teacherName);

            	classtables.add(c);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            JdbcUtil.close(conn, pstmt, null);
        }
        return classtables;
    }


}