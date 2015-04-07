package br.com.silvaaraujo.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * O framework QUARTZ possui dois tipos de triggers: 
 * <lu> 
 * 	<li><b>SimpleTrigger</b> - que sera apresentado neste exemplo, permite definir o intervalo de execucao.</li>
 *  <li>CronTrigger - permite utilizar uma expressao (pattern) para definir a data e hora de execucao.</li>
 * </lu>
 * @see  http://quartz-scheduler.org/generated/2.2.1/html/qs-all/#page/Quartz_Scheduler_Documentation_Set%2Fco-trg_simple_triggers.html%23
 * @author Thiago Silva
 */
public class SimpleTriggerExample {

	public static void main(String[] args) {
		
		/*Define o job e relaciona ele com nossa classe de negocio que implementa a interface Job do Quartz.*/
		JobDetail job = JobBuilder
			.newJob(MyJob.class)
			.withIdentity("simpleJob", "grupo1")
			.build();
		
		/*A trigger define a regra de execucao, qual o intervalo, repeticao, etc...*/
		Trigger trigger = TriggerBuilder
			.newTrigger()
			.withIdentity("SimpleTrigger", "grupo1")
			.withSchedule(
				SimpleScheduleBuilder
				.simpleSchedule()
				.withIntervalInSeconds(10)
				.repeatForever()
			).build();
		
		/*Para se ter uma instancia de um scheduler eh necessario consegui-lo atraves de uma factory*/
		SchedulerFactory factory = new StdSchedulerFactory();   
		Scheduler scheduler = null; 
		try {
			scheduler = factory.getScheduler();
			
			/*Apos ter uma instancia do scheduler em maos eh necessario definir 
			 * o job e a trigger que deverao ser utilizadas por esse scheduler*/
			scheduler.scheduleJob(job, trigger);
			
			/*Inicia a execucao do scheduler definido anteriormente*/
			scheduler.start();
			
			/*pausa a execucao do programa para que o agendamento possa ser executado*/
			Thread.sleep(30000);
			
			/*finaliza o scheduler*/
			scheduler.shutdown(true);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}