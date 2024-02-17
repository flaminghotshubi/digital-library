package com.minorproject.digitallibrary.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String externalTxnId;

	@CreationTimestamp
	private Date transactionTime;
	
	@UpdateTimestamp
	private Date updatedOn;

	@Enumerated(value = EnumType.STRING)
	private TransactionStatus status;
	
	@Enumerated(value = EnumType.STRING)
	private TransactionType type;
	
	private Double fine;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Book book;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn
	private Student student;
	
}
