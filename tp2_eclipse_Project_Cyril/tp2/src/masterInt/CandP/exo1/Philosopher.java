package masterInt.CandP.exo1;

import java.util.Random;


public class Philosopher implements Runnable {

	private int _id;
	private Fork _leftFork;
	private Fork _rightFork;

	public Philosopher(int id, Fork leftFork, Fork rightFork) {
		// TODO Auto-generated constructor stub
		this._id = id;
		this._leftFork = leftFork;
		this._rightFork = rightFork;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				this._leftFork.get_s();;
				System.out.println(String.format("p%s get fork %s", this._id,this._leftFork.get_id()));
				this._rightFork.get_s();
				System.out.println(String.format("p%s get fork %s", this._id,this._rightFork.get_id()));
				eat();
				this._leftFork.put_s();
				this._rightFork.put_s();
				think();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void eat() {
		System.out.println(String.format("philosopher %s start to eat.", this._id));
		try {
			Random r = new Random(50);
			Thread.sleep(r.nextInt(3000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(String.format("philosopher %s finish eating.", this._id));
	}

	public void think() {
		System.out.println(String.format("philosopher %s start to think.", this._id));
		try {
			Random r = new Random(100);
			Thread.sleep(r.nextInt(3000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(String.format("philosopher %s finish thinking.", this._id));
	}

}
