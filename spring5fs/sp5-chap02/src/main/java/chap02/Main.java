package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx =
				new AnnotationConfigApplicationContext(AppContext.class);
		
		Greeter g1 = ctx.getBean("greeter", Greeter.class);
		String msg1 = g1.greet("안녕우");
		
		Greeter g2 = ctx.getBean("greeter1", Greeter.class);
		String msg2 = g2.greet("안여누");
		
		System.out.println(msg1);
		System.out.println(msg2);
		
		ctx.close();
	}
}
