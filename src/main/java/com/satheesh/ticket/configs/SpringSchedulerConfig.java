package com.satheesh.ticket.configs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.satheesh.ticket.dao.TicketRepository;

@Configuration
@EnableScheduling
public class SpringSchedulerConfig {

	@Autowired
	TicketRepository ticketRepo;

	private Logger logger = LogManager.getLogger(this.getClass());

	// Runs every minute to close the resolved tickets older than 30 days
	@Scheduled(fixedRate = 60000)
	public void scheduleFixedRateTask() {
		try {
			Long timeBefore30days = (System.currentTimeMillis() / 1000) - (30 * 24 * 60 * 60);
			ticketRepo.updateResolvedTicketsToClosed(timeBefore30days);
		} catch (Exception e) {
			logger.error("Could not close old resolved tickets", e);
		}
	}
}
