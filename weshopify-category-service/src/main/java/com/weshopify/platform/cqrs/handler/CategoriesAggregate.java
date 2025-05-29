package com.weshopify.platform.cqrs.handler;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.weshopify.platform.cqrs.commands.CategoryCommand;
import com.weshopify.platform.cqrs.domainevent.CategoryEvent;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class CategoriesAggregate {

	@AggregateIdentifier
	private String eventId;
	private int id;
	private String name;
	private String alias;
	private int parentCategory;
	private boolean enabled;
	
	@CommandHandler
	public CategoriesAggregate(CategoryCommand command) {
		log.info("in command handler");
		CategoryEvent event=CategoryEvent.builder()
		.id(command.getId())
		.eventId(command.getEventId())
		.name(command.getName())
		.alias(command.getAlias())
		.enabled(command.isEnabled())
		.parentCategory(command.getParentCategory())
		.build();
		
		log.info("publishing the created event to event handler");

		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	protected void on(CategoryEvent event) {
		log.info("in event sourcing handler");

		this.eventId=event.getEventId();
		this.id=event.getId();
		this.name=event.getName();
		this.alias=event.getAlias();
		this.enabled=event.isEnabled();
		this.parentCategory=event.getParentCategory();
	}
}
