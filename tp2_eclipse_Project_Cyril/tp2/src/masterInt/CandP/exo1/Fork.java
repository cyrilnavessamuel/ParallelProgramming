package masterInt.CandP.exo1;

import java.util.concurrent.Semaphore;

public class Fork {

	private int _id;
	private boolean _isAvailable;
	
	private final Semaphore available = new Semaphore(1,true);
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public boolean is_Available() {
		synchronized (this) {
			return _isAvailable;
		}
	}

	public void set_Available(boolean _isAvailable) {
		synchronized (this) {
			this._isAvailable = _isAvailable;
		}
	}

	public Fork(int id) {
		// TODO Auto-generated constructor stub
		this._id = id;
		this._isAvailable = true;
	}
	
	//synchronized
	
	public synchronized void get() throws InterruptedException
	{
		while(!this.is_Available())
			wait();
		this.set_Available(false);
	}
	
	public synchronized void put() 
	{
		this.set_Available(true);
		notifyAll();
	}

	//semaphore version
	public void get_s() throws InterruptedException
	{
		this.available.acquire();
	}
	
	public void put_s()
	{
		this.available.release();
	}
	
}
