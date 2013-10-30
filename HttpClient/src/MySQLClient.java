import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.text.DecimalFormat;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class MySQLClient implements Runnable{

	private String uuid;
	private long loopNum;
	
	private static final String MYSQL_IP = "192.168.1.13";
	private static final int MYSQL_PORT = 3307;
	private static final String MYSQL_DB = "test";
	private static final String MYSQL_USERNAME = "admin";
	private static final String MYSQL_PASSWORD = "admin";
	
	/**
	 * test function
	 */
	public void run() {
		Connection conn = null;
		Statement stmt = null;
        DecimalFormat fmtTime = new DecimalFormat("0.000");
        
		try {
			//Initialize
			Class.forName("com.mysql.jdbc.Driver");
		    conn =
		       DriverManager.getConnection("jdbc:mysql://"
		    		   +MYSQL_IP+":"
		    		   +MYSQL_PORT+"/"
		    		   +MYSQL_DB+"?" 
		    		   +"user="+MYSQL_USERNAME
		    		   +"&password="+MYSQL_PASSWORD);

	        stmt = conn.createStatement();
	        //initialize(stmt);

	        //Run SQL
	        long T1 = System.currentTimeMillis();
	        long i = 0;
			while (true) {
				i++;
				if( loopNum!=0 && i>loopNum){
						break;
				}
				//select(stmt,getUuid(),i);
				insert(stmt,getUuid(),i);
			}
	        long T2 = System.currentTimeMillis();
	        Double avgTime =  (double)(T2-T1)/loopNum;
	        System.out.println( "Time spend is: " + timeSpend(T1,T2) + 
	        					", Loop num is: " +  Long.toString(loopNum) +
	        					", Average time is: " + fmtTime.format(avgTime)
	        					
	        );
	        // Now do something with the ResultSet ....

		} catch (SQLException ex) {
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
         
	/**
	 * SELECT TEST
	 * @param stmt
	 * @param strarg
	 * @param intarg
	 */
	public void select(Statement stmt, String strarg, long intarg)
	{
		//Modify this statement to change what u want to select
		String sql = "SELECT * FROM benchmark WHERE 1 LIMIT 1 ";
		ResultSet rs = null;
		try{
			stmt.execute(sql);
			rs = stmt.getResultSet();
        	while (rs.next()) {
        		System.out.println("index:" + rs.getString("testindex") + 
        				", str1:'" + rs.getString("teststring1") + 
        				"', str2:'" + rs.getString("teststring2") + 
        				"', int:" + rs.getString("testint")
        				);
        	}
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	/**
	 * INSERT TEST
	 * @param stmt
	 * @param strarg
	 * @param intarg
	 */
	public void insert(Statement stmt, String strarg, long intarg)
	{
		String sql = "INSERT INTO benchmark (teststring1, teststring2, testint) VALUES ('"+strarg+"','"+strarg+strarg+"',"+Long.toString(intarg)+") ";
		ResultSet generatedKeys = null;
		int affectedRows = 0;
		try{
			affectedRows = stmt.executeUpdate(sql);
			if(affectedRows>0)
			{
				generatedKeys = stmt.getGeneratedKeys();
				while(generatedKeys.next()){
					System.out.println("Insert ID is: " + generatedKeys.getInt(1));
				}
			}
			else
			{
				System.out.println("Warning: Affect Rows is 0");
			}
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	/**
	 * Initialize MySQL to create test database and table
	 * @param stmt
	 */
	public void initialize(Statement stmt)
	{
		String sqlCreateDB = "	CREATE DATABASE IF NOT EXISTS test "+
							 "   DEFAULT CHARACTER SET = utf8 "+
							 "   DEFAULT COLLATE = utf8_general_ci ";
		String sqlCreateTable = "CREATE TABLE IF NOT EXISTS `benchmark` (" +
			"`testindex` int(11) NOT NULL AUTO_INCREMENT," +
			" `teststring1` varchar(50) NOT NULL," +
			" `teststring2` varchar(100) NOT NULL," +
			" `testint` bigint(20) DEFAULT NULL," +
			" PRIMARY KEY (`testindex`)" +
			") ENGINE=InnoDB DEFAULT CHARSET=utf8 ";
		try{
			stmt.execute(sqlCreateDB);
			stmt.execute(sqlCreateTable);
		}
		catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
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
