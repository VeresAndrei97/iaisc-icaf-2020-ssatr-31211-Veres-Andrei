import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

import java.util.Iterator; 
import java.util.Map; 
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.simple.JSONArray; 
import org.json.simple.JSONObject; 
import org.json.simple.parser.*; 


class ExecutionThread extends Thread {
	Semaphore s1;
	CyclicBarrier cb;
	int s, activity_min, activity_max;
	public ExecutionThread(Semaphore s1, CyclicBarrier cb, int activity_min, int activity_max, int s) {
		this.s1 = s1;
		this.s = s;
		this.cb = cb;
		this.activity_min = activity_min;
		this.activity_max = activity_max;
	}
	public void run() {
		while(true) {
			System.out.println(this.getName() + " - STATE 1");
			try {
				s1.acquire();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.out.println(this.getName() + " - STATE 2");
			int k = (int) Math.round(Math.random()*(activity_max - activity_min) + activity_min);
			for (int i = 0; i < k * 100000; i++) {
				i++; i--;
			}
			try {
				sleep(s*100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			s1.release();

			System.out.println(this.getName() + " - STATE 3");
			try {
				cb.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class ExecutionThread2 extends Thread{
	Semaphore s1;
	Semaphore s2;
	CyclicBarrier cb;
	int s, activity_min, activity_max;
	public ExecutionThread2(Semaphore s1, Semaphore s2, CyclicBarrier cb, int activity_min, int activity_max, int s) {
		this.s1 = s1;
		this.s2 = s2;
		this.s = s;
		this.cb = cb;
		this.activity_min = activity_min;
		this.activity_max = activity_max;
	}
	public void run() {
		while(true) {
			System.out.println(this.getName() + " - STATE 1");
				try {
					s1.acquire();
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					s2.acquire();
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			
			System.out.println(this.getName() + " - STATE 2");
			int k = (int) Math.round(Math.random()*(activity_max - activity_min) + activity_min);
			for (int i = 0; i < k * 100000; i++) {
				i++; i--;
			}
			try {
				sleep(s*100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			s1.release();
			s2.release();

			System.out.println(this.getName() + " - STATE 3");
			try {
				cb.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class tema_1 {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
		Semaphore s1 = new Semaphore(1);
		Semaphore s2 = new Semaphore(2);
		CyclicBarrier cb = new CyclicBarrier(3, new Runnable() {public void run() { System.out.println("Bariera"); }});
		
		new ExecutionThread(s1, cb, 2, 4, 4).start();
		new ExecutionThread2(s1, s2, cb, 3, 6, 3).start();
		
		// JSON READ
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader("DataFile.json"));
			JSONObject jsonObject = (JSONObject) obj;
			String name = (String) jsonObject.get("name");
			int activity_max = (int) jsonObject.get("activity_max");
			int activity_min = (int) jsonObject.get("activity_min");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		}
	}

