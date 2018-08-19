CREATE TABLE `call_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(20) NOT NULL,
  `callType` int(11) DEFAULT '0',
  `callTime` timestamp NOT NULL,
  `callLogId` varchar(20) DEFAULT NULL,
  `phone` varchar(50) NOT NULL,
  `deviceId` varchar(20) DEFAULT 'ISP',
  `duration` int(11) DEFAULT '0',
  `createdAt` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `uuid` (`uuid`),
  CONSTRAINT `call_record_ibfk_1` FOREIGN KEY (`uuid`) REFERENCES `user` (`uuid`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8


CREATE TABLE `user` (
  `uuid` varchar(20) NOT NULL,
  `created_at` timestamp NOT NULL,
  `phone` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL,
  `id_no` varchar(20) NOT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8


