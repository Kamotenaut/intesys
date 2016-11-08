package utils;

import java.util.LinkedList;
import java.util.Queue;


public class ObjectQueue {
	private Queue<Object> queue;
	private int size;
	
	public ObjectQueue(){
		setQueue(new LinkedList<Object>());
		size = 0;
	}
	
	// queue operations
	public Object enqueue(Object obj){
		if(queue.add(obj)){
			size++;
			return obj;}
		return null;
	}
	
	public Object dequeue(){
		Object result = queue.poll();
		if(result != null)
			size--;
		return result;
	}
	
	public Object head(){
		return queue.peek();
	}
	
	public Object tail(){
		Object result = null;
		for (Object temp : queue)
		     result = temp;
		return result;
	}
	
	public boolean isEmpty(){
		return queue.isEmpty();
	}
	
	// other

	public Queue<Object> getQueue() {
		return queue;
	}

	public void setQueue(Queue<Object> queue) {
		this.queue = queue;
	}
	
	// derived from normal queue functions
	
	public Object rotate(){
		Object temp = dequeue();
		if(temp != null)
			enqueue(temp);
		return temp;
	}
	/*
	public Object search(int id){
		Object result = null;
		for(int i = 0; i < getSize(); i++){
			Object temp = rotate();
			if(temp.getId() == id)
				result = temp;
		}
		return result;
	}
	public Object searchAndRemove(int id){
		Object result = null;
		for(int i = 0; i < getSize()+1; i++){
			Object temp = dequeue();
			if(temp.getId() != id)
				enqueue(temp);
			else
				result = temp;
		}
		return result;
	}
	*/

	public int getSize() {
		return size;
	}
}
