import java.util.UUID;
import java.math.BigDecimal;
import java.net.UnknownHostException;
import com.mongodb.*;

public class MongoDbClient implements Runnable{

	private String uuid;
	private long loopNum;
	
	private static final String REDIS_IP = "192.168.1.13";
	private static final int REDIS_PORT = 27017;
	private static final String REDIS_DB = "test";
	private static final String REDIS_COLLECTION = "benchmark";
	
	public void run(){
		Mongo mongo = null;
		try
		{
			mongo = new Mongo(REDIS_IP,REDIS_PORT);
			
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
		catch(MongoException e)
		{
			e.printStackTrace();
		}
		
		DB db = mongo.getDB(REDIS_DB);
		DBCollection coll = db.getCollection(REDIS_COLLECTION);
		long T1 = System.currentTimeMillis();
		for(long i=0;i<loopNum;i++)
		{
			
			BasicDBObject redisObj = new BasicDBObject();
			redisObj.put(uuid+Long.toString(i), uuid+Long.toString(i));
			
			//coll.insert(redisObj);
			DBCursor cursor = coll.find().limit(500000);
			try {
			   while(cursor.hasNext()) {
				   cursor.next();
			       //System.out.println(cursor.next());
			   }
			} finally {
			   cursor.close();
			}
			
			//System.out.println("Insert:" + uuid+Long.toString(i));
		}
		long T2 = System.currentTimeMillis();
		System.out.println("Time spend is:" +timeSpend(T1,T2));
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
