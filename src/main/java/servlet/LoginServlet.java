package main.java.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import main.java.bo.UserBo;
import main.java.service.UserService;
import main.java.util.APIObject;
import main.java.util.ServletUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "登录校验", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static UserService userService = new UserService();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtil.outputJSON(response,"请使用 POST 方式登录" );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBo userData = new UserBo();
		
		userData.setUsername(request.getParameter("username"));
		userData.setPassword(request.getParameter("password"));
		
		System.out.printf("登录请求： 用户名 [%s]，密码 [%s] \n", userData.getUsername(), userData.getPassword());
		
		UserBo loginUser = userService.queryUser(userData);
		
		if (loginUser != null) {
			System.out.printf("用户 [%s] 登录成功 \n", userData.getUsername());
			
			ServletUtil.setLoginUserToSessionAttribute(request, loginUser);
			
			APIObject apiObject = new APIObject();
			apiObject.setMessage("登录成功");

			ServletUtil.outputJSON(response, apiObject);
		}
		else {
			System.out.println("登录失败");

			ServletUtil.outputJSON(response, "用户名或密码错误，请重新登录");
		}
	}

}
