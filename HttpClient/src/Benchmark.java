import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * @author yangqike@vanelife.com
 */
public class Benchmark {
	private enum TestType { HTTP, Redis, MySQL, MongoDB };
	private static final TestType testType = TestType.MySQL; //Permitted Options: HTTP, Redis, MySQL, MongoDB
	private static final long loopNumBenchmark = 0;
	private static final long loopNumObj = 100000;
	
	public static void main(String[] args) {
		int corePoolSize = 50;
		int maximumPoolSize = 100;
		long keepAliveTime = 60;
		ArrayBlockingQueue queue = new ArrayBlockingQueue(1000000);
		ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,
			maximumPoolSize, keepAliveTime, TimeUnit.MILLISECONDS, queue);
		long i = 0;
		while (true) {
			i++;
			if( loopNumBenchmark!=0 && i>loopNumBenchmark){
					break;
			}
			
			try {
				System.out.println("Thread start:");
				System.out.println("getActiveCount:" 		+ pool.getActiveCount()			);
				System.out.println("getCompletedTaskCount:"	+ pool.getCompletedTaskCount()	);
				System.out.println("getCorePoolSize:" 		+ pool.getCorePoolSize()		);
				System.out.println("getPoolSize:" 			+ pool.getPoolSize()			);
				System.out.println("getLargestPoolSize:"	+ pool.getLargestPoolSize()		);
				System.out.println("getTaskCount:" 			+ pool.getTaskCount()			);
				System.out.println("getQueue().size:" 		+ pool.getQueue().size()		);

				Thread.sleep(50L);
				UUID uuid = UUID.randomUUID();
				
				switch (testType) {
		            case HTTP:
						String deviceSn = uuid.toString().replace("-", "").toUpperCase();
						String accessKey = uuid.toString().replace("-", "").toUpperCase();
						HttpClient httpClientTest = new HttpClient();
						//httpClientTest.setThreadNo(i);
		                break;
		            case Redis:
		                System.out.println("Redis are better.");
		                break;
		            case MySQL: 
		            	MySQLClient mysql = new MySQLClient();
		            	mysql.setUuid(uuid.toString());
		            	mysql.setLoopNum(loopNumObj);
						pool.submit(mysql);
		            	break;
		            case MongoDB:
		            	MongoDbClient md = new MongoDbClient();
						md.setUuid(uuid.toString());
						md.setLoopNum(loopNumObj);
						pool.submit(md);
		                break;
		            default:
		                System.out.println("Midweek days are so-so.");
		                break;
				}	
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
