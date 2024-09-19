CREATE TABLE TRAIN (
    UUID CHAR(36) NOT NULL PRIMARY KEY,
    TRAIN_NO INT,
    TRAIN_KIND VARCHAR(255),
    CREATED_DATE DATETIME,
    CREATED_BY VARCHAR(255),
    LAST_UPDATED_DATE DATETIME,
    LAST_UPDATED_BY VARCHAR(255)
);

CREATE TABLE TRAIN_STOP (
    UUID CHAR(36) NOT NULL PRIMARY KEY,
    TRAIN_UUID CHAR(36),
    SEQ INT,
    NAME VARCHAR(255),
    TIME TIME,
    DELETE_FLAG VARCHAR(255),
    CREATED_DATE DATETIME,
    CREATED_BY VARCHAR(255),
    LAST_UPDATED_DATE DATETIME,
    LAST_UPDATED_BY VARCHAR(255),
    CONSTRAINT FK_TRAIN_STOP_TRAIN FOREIGN KEY (TRAIN_UUID) REFERENCES TRAIN(UUID)
);

CREATE TABLE TRAIN_TICKET (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    TICKET_NO VARCHAR(255) NOT NULL,
    TRAIN_UUID VARCHAR(255) NOT NULL,
    FROM_STOP VARCHAR(255) NOT NULL,
    TO_STOP VARCHAR(255) NOT NULL,
    PRICE DECIMAL(19, 2) NOT NULL,
    CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATED_BY VARCHAR(255),
    LAST_UPDATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR(255)
);

CREATE TABLE TRAIN_SEAT (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    TICKET_UUID CHAR(36) NOT NULL,
    TRAIN_UUID CHAR(36) NOT NULL,
    BOOK_UUID CHAR(36) NOT NULL,
    TAKE_DATE DATE,
    SEAT_NO VARCHAR(255),
    BOOKED CHAR(2),
    ACTIVE_FLAG CHAR(2),
    CREATED_DATE DATETIME,
    CREATED_BY VARCHAR(255),
    LAST_UPDATED_DATE DATETIME,
    LAST_UPDATED_BY VARCHAR(255)
);

CREATE TABLE TICKET_BOOKING (
    UUID CHAR(36) NOT NULL PRIMARY KEY,
    USERNAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL,
    TRAIN_UUID CHAR(36) NOT NULL,
    TICKET_UUID CHAR(36) NOT NULL,
    ACTIVE_FLAG CHAR(2),
    CREATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CREATED_BY VARCHAR(255),
    LAST_UPDATED_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    LAST_UPDATED_BY VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS `EVENT_LOG` (
  ID BIGINT(20) NOT NULL AUTO_INCREMENT,
  UUID CHAR(36) NOT NULL,
  USER_ID VARCHAR(32) NOT NULL,
  EVENT_CLASS_NAME VARCHAR(255) NOT NULL,
  OCCURED_AT DATETIME NOT NULL,
  TARGET_ID CHAR(36),
  BODY LONGTEXT NOT NULL,
  SEND_QUEUE_STATUS VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `EVENT_IDEMPOTENT_LOG` (
  EVENT_TYPE VARCHAR(190) NOT NULL,
  UNIQUE_KEY VAR(64) NOT NULL,
  TARGET_ID CHAR(36),
  CREATED_DATE TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (EVENT_TYPE, UNIQUE_KEY),
  KEY `idx_type_key` (`EVENT_TYPE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


CREATE TABLE TRAIN (
    UUID CHAR(36) NOT NULL PRIMARY KEY,
    TRAIN_NO INT,
    TRAIN_KIND VARCHAR(255),
    CREATED_DATE DATETIME,
    CREATED_BY VARCHAR(255),
    LAST_UPDATED_DATE DATETIME,
    LAST_UPDATED_BY VARCHAR(255)
);

CREATE TABLE MONEY_ACCOUNT (
    UUID CHAR(36) NOT NULL PRIMARY KEY,
    USERNAME VARCHAR(255),
    EMAIL VARCHAR(255),
	BALANCE DECIMAL(19, 2),
    CREATED_DATE DATETIME,
    CREATED_BY VARCHAR(255),
    LAST_UPDATED_DATE DATETIME,
    LAST_UPDATED_BY VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS `EVENT_SOURCE` (
  ID BIGINT AUTO_INCREMENT PRIMARY KEY,
  UUID CHAR(100) NOT NULL,
  AGGREGATE_ID CHAR(36) NOT NULL,
  USER_ID VARCHAR(32) NOT NULL,
  EVENT_CLASS_NAME VARCHAR(255) NOT NULL,
  OCCURED_AT DATETIME NOT NULL,
  BODY LONGTEXT NOT NULL,
  STATUS CHAR(20) NOT NULL,
  VERSION BIGINT NOT NULL,
  CONSTRAINT unique_target_id_version UNIQUE (AGGREGATE_ID, VERSION)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


