package watcher;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class Watcher {
	
	private LinkedHashSet watchers;
	
	private boolean vary;
	
	protected Watcher() {
		watchers = new LinkedHashSet();
	}
	
	public synchronized void addWatcher(LookOn lookOn) {
			
		if (lookOn == null)
				throw new NullPointerException("can't add null observer");
			watchers.add(lookOn);
	}
	
	protected synchronized void setVary() {
		vary = true;
	}
	
	public void informWathcers() {
		informWathcers(null);
	}
	
	public void informWathcers(Object obj) {
	    
		if (vary == false)
	      return;
	    Set s;
	    
	    synchronized (this) {
	          s = (Set) watchers.clone();
	    }
	    
	    int i = s.size();
	    Iterator iter = s.iterator();
	    while (--i >= 0)
	      ((LookOn) iter.next()).refresh(this, obj);
	    clearVary();
	}
	
	protected synchronized void clearVary() {
		vary = false;
	}
	
}
