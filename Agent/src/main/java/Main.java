import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import com.sun.tools.attach.VirtualMachine;

public class Main {
	public static void main(String[] args) throws Exception {
		VirtualMachine vm = VirtualMachine.attach(getProcessID() + "");
		vm.loadAgent(args[0]);
		System.out.println("MAIN");
	}
	
	public static final int getProcessID() { 
	    RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
	    System.out.println(runtimeMXBean.getName());
	    return Integer.valueOf(runtimeMXBean.getName().split("@")[0]) 
	        .intValue(); 
	  } 
}
