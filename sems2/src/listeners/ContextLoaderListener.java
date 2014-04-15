package listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dao.MysqlSubjectDao;
import util.DBConnectionPool;

public class ContextLoaderListener implements ServletContextListener {
	
	DBConnectionPool dbConnectionPool;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("contextInitialized...");
		dbConnectionPool.closeAll();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("contextDestroyed...");
		
		ServletContext sc = event.getServletContext();
		dbConnectionPool = new DBConnectionPool();

		dbConnectionPool.setDriver(sc.getInitParameter("driver"));
		dbConnectionPool.setUrl(sc.getInitParameter("url"));
		dbConnectionPool.setUsername(sc.getInitParameter("username"));
		dbConnectionPool.setPassword(sc.getInitParameter("password"));

		MysqlSubjectDao subjectDao = new MysqlSubjectDao();
		subjectDao.setDBConnectionPool(dbConnectionPool);
		sc.setAttribute("subjectDao", subjectDao);

	}

}
