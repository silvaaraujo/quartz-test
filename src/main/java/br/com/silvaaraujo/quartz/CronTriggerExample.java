package br.com.silvaaraujo.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * O framework QUARTZ possui dois tipos de triggers: 
 * <lu> 
 * 	<li>SimpleTrigger - permite definir o intervalo de execucao.</li>
 *  <li><b>CronTrigger</b> - que sera apresentado neste exemplo, permite utilizar uma expressao (pattern) para definir a data e hora de execucao.</li>
 * </lu>
 *
 * @see http://quartz-scheduler.org/generated/2.2.1/html/qs-all/#page/Quartz_Scheduler_Documentation_Set%2Fco-trg_crontriggers.html%23
 * @author Thiago Silva
 */
public class CronTriggerExample {
	
	public static void main(String[] args) {
		
		/*Define o job e relaciona ele com nossa classe de negocio que implementa a interface Job do Quartz.*/
		JobDetail job = JobBuilder
			.newJob(MyJob.class)
			.withIdentity("simpleJob", "grupo1")
			.build();
		
		Trigger trigger = TriggerBuilder
			.newTrigger()
			.withIdentity("cronTrigger", "group1")
			.withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
			.forJob(job)
			.build();
		
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