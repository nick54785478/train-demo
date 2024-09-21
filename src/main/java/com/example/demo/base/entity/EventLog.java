package com.example.demo.base.entity;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.base.enums.EventLogSendQueueStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "EVENT_LOG")
@EntityListeners(AuditingEntityListener.class)
public class EventLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "UUID")
	private String uuid;

	@Column(name = "USER_ID")
	private String userId; // 使用者帳號

	@Column(name = "EVENT_CLASS_NAME")
	private String className; // Event 類型

	@CreatedDate
	@Column(name = "OCCURED_AT")
	private Date occuredAt;

	@Column(name = "TARGET_ID")
	private String targetId;

	@Column(name = "BODY")
	private String body;

	@Column(name = "TOPIC")
	private String topic;

	/**
	 * Event 狀態
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "SEND_QUEUE_STATUS")
	private EventLogSendQueueStatus status;

	/**
	 * 在持久化之前執行的方法，用於設置 status。
	 */
	@PrePersist
	public void prePersist() {
		if (Objects.isNull(this.status)) {
			this.status = EventLogSendQueueStatus.INITIAL;
		}
	}

	/**
	 * 確認 Event 已發布後更新
	 * 
	 * @param 發布的實體
	 */
	public void publish(String body) {
		this.body = body;
		this.status = EventLogSendQueueStatus.SENT;
	}

	/**
	 * 確認 Event 已消費後更新
	 */
	public void consume() {
		this.status = EventLogSendQueueStatus.CONSUMED;
	}

}
