package webController;


import java.util.Date;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import model.DoLoginCheck;

/**
 * Application Lifecycle Listener implementation class SessionCounter
 *
 */
@WebListener("/sdlistener")
public class SessionCounter implements HttpSessionListener {

    /**
     * Default constructor. 
     */
    public SessionCounter() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent event)  { 
         // TODO Auto-generated method stub
//    	HttpSession ds = event.getSession( );
//        Date tstartSession = (Date) ds.getAttribute("tstartSession");  
//        if (tstartSession == null) {  
//        	tstartSession = new Date();  
//        }   
//        ds.setAttribute("tstartSession", tstartSession);
//        System.out.println("session start"); 
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
         // TODO Auto-generated method stub
    	HttpSession ds=se.getSession();  
    	Date tstartSession = ((Date)ds.getAttribute("tstartSession")); 
    	String username=(String)ds.getAttribute("username");
    	if(tstartSession != null&&username!=null) { 
    		DoLoginCheck t=new DoLoginCheck();
    		boolean flag=t.InsertAccessLog(username, tstartSession, new Date());
    		if(flag) System.out.println("accesslog insert success");
    		else System.out.println("accesslog insert failed");
    	}  
        else {  
//        	long diff =(new Date()).getTime()- tstartSession.getTime() ;
//            long day = diff / (1000 * 60 * 60 * 24);
//            long hour=(diff /(60*60*1000)-day*24);
//            long min=((diff /(60*1000))-day*24*60-hour*60);
//            long s=(diff /1000-day*24*60*60-hour*60*60-min*60);
//            System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
//        	System.out.println("session lost");
        }  
        ds.removeAttribute("tstartSession");  
        ds.removeAttribute("username"); 
    	  
    }
	
}
