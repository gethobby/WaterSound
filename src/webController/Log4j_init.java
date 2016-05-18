package webController;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class Log4j_init
 * Servlet3.0提供了注解(annotation)，使得不再需要在web.xml文件中进行Servlet的部署描述
 */
/*
30  * 完成了一个使用注解描述的Servlet程序开发。
31 　　使用@WebServlet将一个继承于javax.servlet.http.HttpServlet的类定义为Servlet组件。
32 　　@WebServlet有很多的属性：
33     　　1、asyncSupported：    声明Servlet是否支持异步操作模式。
34     　　2、description：　　    Servlet的描述。
35     　　3、displayName：       Servlet的显示名称。
36     　　4、initParams：        Servlet的init参数。
37     　　5、name：　　　　       Servlet的名称。
38     　　6、urlPatterns：　　   Servlet的访问URL。
39     　　7、value：　　　        Servlet的访问URL。
40 　　Servlet的访问URL是Servlet的必选属性，可以选择使用urlPatterns或者value定义。
41 　　像上面的Servlet3Demo可以描述成@WebServlet(name="Servlet3Demo",value="/Servlet3Demo")。
42 　　也定义多个URL访问：
43 　　如@WebServlet(name="Servlet3Demo",urlPatterns={"/Servlet3Demo","/Servlet3Demo2"})
44 　　或者@WebServlet(name="AnnotationServlet",value={"/Servlet3Demo","/Servlet3Demo2"})
45  *
46  */
@WebServlet(
		urlPatterns = { "/Log4j_init" }, 
		initParams = { 
				@WebInitParam(name = "log4j_init_file", value = "log4j.properties")
		},
		/*
		 * 在servlet的配置当中，<load-on-startup>1</load-on-startup>的含义是：
		标记容器是否在启动的时候就加载这个servlet。
		当值为0或者大于0时，表示容器在应用启动时就加载这个servlet；
		当是一个负数时或者没有指定时，则指示容器在该servlet被选择时才加载。
		正数的值越小，启动该servlet的优先级越高。
		 */
		loadOnStartup=1
		)
public class Log4j_init extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Log4j_init() {
        super();
        // TODO Auto-generated constructor stub

    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(){
		// TODO Auto-generated method stub
		String prefix ="D:\\WaterSound\\Log\\";
		String file = getInitParameter("log4j_init_file");
		// 如果没有给出相应的配置文件，则不进行初始化
		if(file != null)
		{
			//testcode System.out.println(prefix+file);
			PropertyConfigurator.configure(prefix+file); //（1）
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
