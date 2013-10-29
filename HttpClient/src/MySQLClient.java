import java.math.BigDecimal;
import java.net.UnknownHostException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

//
//		CREATE TABLE `benchmark` (
//		 `stringkey` varchar(50) NOT NULL,
//		 `stringvalue` varchar(50) NOT NULL,
//		 PRIMARY KEY (`stringkey`)
//		) ENGINE=InnoDB DEFAULT CHARSET=utf8


public class MySQLClient implements Runnable{

	private String uuid;
	private long loopNum;
	
	private static final String MYSQL_IP = "192.168.1.19";
	private static final int MYSQL_PORT = 3306;
	private static final String MYSQL_DB = "test";
	private static final String MYSQL_USERNAME = "admin";
	private static final String MYSQL_PASSWORD = "admin";
	
	public void run() {
		Connection conn = null;
		
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		    conn =
		       DriverManager.getConnection("jdbc:mysql://"
		    		   +MYSQL_IP+":"
		    		   +MYSQL_PORT+"/"
		    		   +MYSQL_DB+"?" 
		    		   +"user="+MYSQL_USERNAME
		    		   +"&password="+MYSQL_PASSWORD);

		    Statement stmt = null;
		    ResultSet rs = null;

		        stmt = conn.createStatement();
		        //rs = stmt.executeQuery("SELECT * FROM info_device LIMIT 1");

		        // or alternatively, if you don't know ahead of time that
		        // the query will be a SELECT...
		        long T1 = System.currentTimeMillis();
		        for(long i=0;i<loopNum;i++)
				{
			        if (stmt.execute("INSERT INTO benchmark (stringkey, stringvalue) VALUES ('"+getUuid()+Long.toString(i)+"','"+getUuid()+Long.toString(i)+"') ")) {
	//		            rs = stmt.getResultSet();
			        }
//		        	stmt.execute("SELECT * FROM benchmark WHERE 1 LIMIT 500000");
//		        	rs = stmt.getResultSet();
//		        	while (rs.next()) {}
					//System.out.println("Insert:" + uuid+Long.toString(i));
				}
		        long T2 = System.currentTimeMillis();
		        System.out.println("Time spend is:" +timeSpend(T1,T2));
		        // Now do something with the ResultSet ....

		} catch (SQLException ex) {
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
                                                                                                                                                                                                                                                                                                                                   	
	/**
	 * calculate ms between begin time and end time:
	 * @param beginTime 
	 * @param endTime
	 * @return spend time in string format
	 */
	public String timeSpend(long beginTime, long endTime)
	{
		BigDecimal begin  = new BigDecimal(beginTime);
		BigDecimal end  = new BigDecimal(endTime);
		return end.subtract(begin) + "ms";
	}
	
	public static void main(String[] args)
	{
		return;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public long getLoopNum() {
		return loopNum;
	}

	public void setLoopNum(long loopNum) {
		this.loopNum = loopNum;
	}
	
}
