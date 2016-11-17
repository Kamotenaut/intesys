package utils;

import java.util.LinkedList;
import java.util.Queue;

public class SimpleQueue {
	private Queue<Object> queue;
	private int size;
		
		public SimpleQueue(){
			setQueue(new LinkedList<Object>());
			size = 0;
		}
		
		// queue operations
		
		public Object enqueue(Object value){
			if(queue.add(value)){
				size++;
				return value;}
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

		public int getSize() {
			return size;
		}
}
