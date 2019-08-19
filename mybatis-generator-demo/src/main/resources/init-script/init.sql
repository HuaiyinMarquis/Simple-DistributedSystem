CREATE TABLE IF NOT EXISTS `t_order` (
   `order_id`       VARCHAR(36)    NOT NULL,
   `user_id`    VARCHAR(36)    NOT NULL,
   `product_id`     VARCHAR(36)    NOT NULL,
   `product_name`   VARCHAR(15)    NULL,
   `order_price`    DECIMAL(7, 2)  NOT NULL DEFAULT 0,
   `status`         ENUM ('CREATED', 'PAID', 'DELIVERING', 'COMPLETED') NOT NULL,
   `is_deleted`     INT(1)         NOT NULL DEFAULT 1,
   PRIMARY KEY (`order_id`),
   INDEX `idx_customer_id_status_is_deleted` (`customer_id`,`status`,`is_deleted`)
)character set = utf8;

CREATE TABLE IF NOT EXISTS `t_user` (
  `id`                   VARCHAR(36)  NOT NULL,
  `name`                 VARCHAR(20)  NOT NULL UNIQUE,
  `password_hash`        VARCHAR(255) NOT NULL,
  `role_id`              VARCHAR(36)  NOT NULL,
  `role_name`            VARCHAR(20)  NOT NULL,
  `last_login_time`      TIMESTAMP(6) NULL,
  `last_login_client_ip` VARCHAR(15)  NULL,
  `created_time`         TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by`           VARCHAR(36)  NOT NULL,
  `updated_time`         TIMESTAMP(6) NULL,
  `updated_by`           VARCHAR(36)  NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `t_role` (
  `id`           VARCHAR(36)  NOT NULL,
  `role_name`    VARCHAR(20)  NOT NULL UNIQUE,
  `description`  VARCHAR(90)  NULL,
  `permissions`  TEXT         NOT NULL,
  `created_time` TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
  `created_by`   VARCHAR(36)  NOT NULL,
  `updated_time` TIMESTAMP(6) NULL,
  `updated_by`   VARCHAR(36)  NULL,
  PRIMARY KEY (`id`)
);