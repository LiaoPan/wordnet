package com.lemon.controller;

import com.lemon.tools.DatabaseConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Wang Haobo on 2016/9/4.
 */
@WebServlet(name="RegisterServlet", urlPatterns={"/register"})
public class RegisterServlet extends HttpServlet{
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String errMsg = "";
        RequestDispatcher rd;
        String id = request.getParameter("id");
        String password = request.getParameter("password");
        String conPassword = request.getParameter("conPassword");

        if (id== null|| password ==null||conPassword==null) {
            rd = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
            rd.forward(request, response);
        } else {

            if ((id.length() == 0) || (password.length() == 0) || !password.equals(conPassword))
                errMsg += "注册失败，请检查用户名和密码非空，并确定密码一致";

            else {
                try {
                    Connection conn = DatabaseConnection.getInitConn().getConnection();
                    PreparedStatement pst = conn.prepareStatement("select password from user_login where id = ?");
                    pst.setObject(1, id);
                    // 查询结果集
                    ResultSet rs = pst.executeQuery();
                    if (rs.next()) {
                        errMsg += "用户已经存在，请重新设置用户名";
                    } else {
                        pst = conn.prepareStatement("insert into user_login(id, password,category) values(?, ?,?)");
                        pst.setObject(1, id);
                        pst.setObject(2, password);
                        pst.setObject(3, "master");
                        // 查询结果集
                        int addUser = pst.executeUpdate();
                        //boolean addUser = dd.insert("insert into user_login(id, password，category) values(?, ?,?)",id, password,"master");
                        if (addUser == 0) {
                            errMsg += "注册用户出现错误";
                        }

                        // 注册成功，转发到welcome.jsp
                        HttpSession session = request.getSession(true);
                        session.setAttribute("id", id);

                        //获取转发对象
                        rd = request.getRequestDispatcher("/WEB-INF/pages/welcome.jsp");

                        // 转发请求
                        rd.forward(request, response);
                    }
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 如果出错，转发到重新注册
            if (errMsg != null && !errMsg.equals("")) {
                rd = request.getRequestDispatcher("/WEB-INF/pages/register.jsp");
                request.setAttribute("err", errMsg);
                rd.forward(request, response);
            }
        }
    }
}
