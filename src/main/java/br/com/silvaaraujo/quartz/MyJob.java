package br.com.silvaaraujo.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Esta classe que implementa a interface Job eh a logica de negocio que sera executa no disparo do scheduler.
 *  
 * @author thiago
 */
public class MyJob implements Job {
	
	private static final Logger log = Logger.getLogger(MyJob.class);
	private static final SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Executando o job em " + f.format(new Date()));
	}

}
