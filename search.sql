/*
 Navicat Premium Data Transfer

 Source Server         : db1
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : search

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 26/11/2023 12:24:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for contact
-- ----------------------------
DROP TABLE IF EXISTS `contact`;
CREATE TABLE `contact`  (
  `Contactid` int(0) NOT NULL,
  `workphone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `mobilephone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Contactid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contact
-- ----------------------------
INSERT INTO `contact` VALUES (1, '777-999-8888', '333-444-5555', 'contact1@example.com');
INSERT INTO `contact` VALUES (2, '666-111-2222', '888-777-9999', 'contact2@example.com');
INSERT INTO `contact` VALUES (3, '222-333-4444', '555-666-7777', 'contact3@example.com');

-- ----------------------------
-- Table structure for contactrelation
-- ----------------------------
DROP TABLE IF EXISTS `contactrelation`;
CREATE TABLE `contactrelation`  (
  `departmentname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Contactid` int(0) NOT NULL,
  PRIMARY KEY (`departmentname`, `Contactid`) USING BTREE,
  INDEX `contacts`(`Contactid`) USING BTREE,
  CONSTRAINT `contacts` FOREIGN KEY (`Contactid`) REFERENCES `contact` (`Contactid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contactrelation
-- ----------------------------
INSERT INTO `contactrelation` VALUES ('ABC Corporation', 1);
INSERT INTO `contactrelation` VALUES ('XYZ Company', 2);
INSERT INTO `contactrelation` VALUES ('LMN Industries', 3);

-- ----------------------------
-- Table structure for contributor
-- ----------------------------
DROP TABLE IF EXISTS `contributor`;
CREATE TABLE `contributor`  (
  `Outcomeid` int(0) NOT NULL,
  `Researcherid` int(0) NOT NULL,
  `ranks` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Outcomeid`, `Researcherid`) USING BTREE,
  INDEX `contributors`(`Researcherid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of contributor
-- ----------------------------
INSERT INTO `contributor` VALUES (1, 1, 1);
INSERT INTO `contributor` VALUES (1, 2, 2);
INSERT INTO `contributor` VALUES (2, 4, 1);
INSERT INTO `contributor` VALUES (2, 5, 2);
INSERT INTO `contributor` VALUES (3, 7, 1);

-- ----------------------------
-- Table structure for director
-- ----------------------------
DROP TABLE IF EXISTS `director`;
CREATE TABLE `director`  (
  `Directorid` int(0) NOT NULL AUTO_INCREMENT,
  `Labid` int(0) NOT NULL,
  `startdate` date NULL DEFAULT NULL,
  `term` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Directorid`) USING BTREE,
  INDEX `labdirector`(`Labid`) USING BTREE,
  CONSTRAINT `labdirector` FOREIGN KEY (`Labid`) REFERENCES `researchlab` (`Labid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of director
-- ----------------------------
INSERT INTO `director` VALUES (1, 1, '2022-01-01', 5);
INSERT INTO `director` VALUES (2, 2, '2021-05-15', 4);
INSERT INTO `director` VALUES (3, 3, '2023-02-10', 3);

-- ----------------------------
-- Table structure for leader
-- ----------------------------
DROP TABLE IF EXISTS `leader`;
CREATE TABLE `leader`  (
  `Leaderid` int(0) NOT NULL,
  `workphone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `mobilephone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Leaderid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leader
-- ----------------------------
INSERT INTO `leader` VALUES (1, '123-456-7890', '987-654-3210', 'john.doe@example.com');
INSERT INTO `leader` VALUES (2, '111-222-3333', '444-555-6666', 'jane.smith@example.com');
INSERT INTO `leader` VALUES (3, '555-888-9999', '111-000-2222', 'bob.johnson@example.com');
INSERT INTO `leader` VALUES (4, '777-555-1234', '333-999-5678', 'alice.jones@example.com');
INSERT INTO `leader` VALUES (5, '555-888-4321', '111-444-8765', 'charlie.smith@example.com');
INSERT INTO `leader` VALUES (6, '999-333-6789', '666-111-9876', 'david.wong@example.com');
INSERT INTO `leader` VALUES (7, '888-222-5678', '444-777-9876', 'emma.white@example.com');
INSERT INTO `leader` VALUES (8, '666-111-9876', '222-333-5678', 'frank.jones@example.com');
INSERT INTO `leader` VALUES (9, '333-555-4321', '999-888-1234', 'grace.miller@example.com');

-- ----------------------------
-- Table structure for officespace
-- ----------------------------
DROP TABLE IF EXISTS `officespace`;
CREATE TABLE `officespace`  (
  `Spaceid` int(0) NOT NULL AUTO_INCREMENT,
  `Spacename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `Labid` int(0) NULL DEFAULT NULL,
  `area` double NULL DEFAULT NULL,
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Spaceid`) USING BTREE,
  INDEX `labspace`(`Labid`) USING BTREE,
  CONSTRAINT `labspace` FOREIGN KEY (`Labid`) REFERENCES `researchlab` (`Labid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of officespace
-- ----------------------------
INSERT INTO `officespace` VALUES (1, 'Lab B', 2, 150, '456 Oak St');
INSERT INTO `officespace` VALUES (2, 'Office C', 3, 180, '789 Pine St');
INSERT INTO `officespace` VALUES (3, 'Room A', 1, 200, '123 Main St');
INSERT INTO `officespace` VALUES (4, '广州国际金融大厦', 4, 31000, '广东省广州市珠江西路5号广州国际金融中心');
INSERT INTO `officespace` VALUES (5, 'C7学院楼', NULL, 2500, '华南理工大学大学城校区教学区C7');

-- ----------------------------
-- Table structure for outcome
-- ----------------------------
DROP TABLE IF EXISTS `outcome`;
CREATE TABLE `outcome`  (
  `Projectid` int(0) NULL DEFAULT NULL,
  `Outcomeid` int(0) NOT NULL,
  `outcomename` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `achievedate` date NULL DEFAULT NULL,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Outcomeid`) USING BTREE,
  INDEX `projectoutcome`(`Projectid`) USING BTREE,
  CONSTRAINT `projectoutcome` FOREIGN KEY (`Projectid`) REFERENCES `project` (`Projectid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of outcome
-- ----------------------------
INSERT INTO `outcome` VALUES (1, 1, 'Quantum Entanglement Paper', '2023-01-31', '专利');
INSERT INTO `outcome` VALUES (2, 2, 'Gene Mutation Discovery', '2022-11-30', '论文');
INSERT INTO `outcome` VALUES (3, 3, 'AI Chatbot Software', '2023-06-20', '软件著作权');

-- ----------------------------
-- Table structure for partner
-- ----------------------------
DROP TABLE IF EXISTS `partner`;
CREATE TABLE `partner`  (
  `Partnerid` int(0) NOT NULL,
  `partnername` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Leaderid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Partnerid`) USING BTREE,
  INDEX `leader`(`Leaderid`) USING BTREE,
  CONSTRAINT `leader` FOREIGN KEY (`Leaderid`) REFERENCES `leader` (`Leaderid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of partner
-- ----------------------------
INSERT INTO `partner` VALUES (1, 'Partner X', 'Street X', 2);
INSERT INTO `partner` VALUES (2, 'Partner Y', 'Street Y', 3);
INSERT INTO `partner` VALUES (3, 'Partner Z', 'Street Z', 1);

-- ----------------------------
-- Table structure for principle
-- ----------------------------
DROP TABLE IF EXISTS `principle`;
CREATE TABLE `principle`  (
  `Principleid` int(0) NOT NULL,
  `principlename` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Leaderid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Principleid`) USING BTREE,
  INDEX `leaderprin`(`Leaderid`) USING BTREE,
  CONSTRAINT `leaderprin` FOREIGN KEY (`Leaderid`) REFERENCES `leader` (`Leaderid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of principle
-- ----------------------------
INSERT INTO `principle` VALUES (1, 'ABC Corporation', 'XYZ Street', 1);
INSERT INTO `principle` VALUES (2, 'XYZ Company', 'ABC Avenue', 2);
INSERT INTO `principle` VALUES (3, 'LMN Industries', 'PQR Lane', 3);

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project`  (
  `Projectid` int(0) NOT NULL,
  `Leaderid` int(0) NULL DEFAULT NULL,
  `projectname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `content` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `totalfund` int(0) NULL DEFAULT NULL,
  `startdate` date NULL DEFAULT NULL,
  `enddate` date NULL DEFAULT NULL,
  PRIMARY KEY (`Projectid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of project
-- ----------------------------
INSERT INTO `project` VALUES (1, 5, 'Quantum Experiment', 'Study on quantum entanglement', 50000, '2022-03-01', '2023-03-01');
INSERT INTO `project` VALUES (2, 8, 'Genetic Research', 'Exploring gene mutations', 70000, '2021-12-15', '2022-12-15');
INSERT INTO `project` VALUES (3, 9, 'AI Chatbot Development', 'Creating an intelligent chatbot', 80000, '2023-01-01', '2024-01-01');

-- ----------------------------
-- Table structure for qualitier
-- ----------------------------
DROP TABLE IF EXISTS `qualitier`;
CREATE TABLE `qualitier`  (
  `Qualitierid` int(0) NOT NULL,
  `qualitiername` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `Leaderid` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`Qualitierid`) USING BTREE,
  INDEX `leaderq`(`Leaderid`) USING BTREE,
  CONSTRAINT `leaderq` FOREIGN KEY (`Leaderid`) REFERENCES `leader` (`Leaderid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of qualitier
-- ----------------------------
INSERT INTO `qualitier` VALUES (1, 'Quality Monitor 1', 'Monitor Street 1', 4);
INSERT INTO `qualitier` VALUES (2, 'Quality Monitor 2', 'Monitor Street 2', 6);
INSERT INTO `qualitier` VALUES (3, 'Quality Monitor 3', 'Monitor Street 3', 5);

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record`  (
  `Researcherid` int(0) NOT NULL,
  `Projectid` int(0) NOT NULL,
  `participationdate` date NULL DEFAULT NULL,
  `workload` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `allocatedfund` double NULL DEFAULT NULL,
  INDEX `projectrecord`(`Projectid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of record
-- ----------------------------
INSERT INTO `record` VALUES (1, 1, '2022-03-01', '50', 15000);
INSERT INTO `record` VALUES (2, 1, '2022-03-01', '40', 10000);
INSERT INTO `record` VALUES (4, 2, '2023-01-15', '60', 20000);
INSERT INTO `record` VALUES (5, 2, '2022-12-15', '30', 8000);
INSERT INTO `record` VALUES (7, 3, '2023-01-01', '70', 25000);

-- ----------------------------
-- Table structure for researcher
-- ----------------------------
DROP TABLE IF EXISTS `researcher`;
CREATE TABLE `researcher`  (
  `Researcherid` int(0) NOT NULL AUTO_INCREMENT,
  `Labid` int(0) NULL DEFAULT NULL,
  `researchername` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `gender` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `level` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `age` int(0) NULL DEFAULT NULL,
  `researcharea` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Researcherid`) USING BTREE,
  INDEX `laber`(`Labid`) USING BTREE,
  CONSTRAINT `laber` FOREIGN KEY (`Labid`) REFERENCES `researchlab` (`Labid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of researcher
-- ----------------------------
INSERT INTO `researcher` VALUES (1, 1, 'John Doe', 'Male', 'Ph.D.', 35, 'Quantum Physics');
INSERT INTO `researcher` VALUES (2, 2, 'Jane Smith', 'Female', 'M.Sc.', 28, 'Genetics');
INSERT INTO `researcher` VALUES (3, 3, 'Bob Johnson', 'Male', 'Ph.D.', 40, 'Machine Learning');
INSERT INTO `researcher` VALUES (4, 3, 'Jackvideo', 'Male', 'Normal', 20, 'GTA VII');
INSERT INTO `researcher` VALUES (5, 2, 'SchurFroggy', 'Male', 'Ph.D', 16, 'Games');

-- ----------------------------
-- Table structure for researchlab
-- ----------------------------
DROP TABLE IF EXISTS `researchlab`;
CREATE TABLE `researchlab`  (
  `Labid` int(0) NOT NULL AUTO_INCREMENT,
  `labname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `researcharea` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Labid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of researchlab
-- ----------------------------
INSERT INTO `researchlab` VALUES (1, 'Physics Lab', 'Quantum Physics');
INSERT INTO `researchlab` VALUES (2, 'Biology Lab', 'Genetics');
INSERT INTO `researchlab` VALUES (3, 'AI Lab', 'Machine Learning');
INSERT INTO `researchlab` VALUES (4, '人道主义实验室', '人道主');
INSERT INTO `researchlab` VALUES (5, '1231231', '123123');
INSERT INTO `researchlab` VALUES (6, '12312', '12313321');

-- ----------------------------
-- Table structure for secretary
-- ----------------------------
DROP TABLE IF EXISTS `secretary`;
CREATE TABLE `secretary`  (
  `Secretaryid` int(0) NOT NULL AUTO_INCREMENT,
  `hiretime` date NULL DEFAULT NULL,
  `responsibility` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`Secretaryid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of secretary
-- ----------------------------
INSERT INTO `secretary` VALUES (1, '2022-05-10', 'Schedule management, administrative support');
INSERT INTO `secretary` VALUES (2, '2023-01-15', 'Travel arrangement, meeting coordination');
INSERT INTO `secretary` VALUES (3, '2021-09-27', 'Document preparation, correspondence handling');
INSERT INTO `secretary` VALUES (4, '2022-11-03', 'Event planning, calendar management');

-- ----------------------------
-- Table structure for subproject
-- ----------------------------
DROP TABLE IF EXISTS `subproject`;
CREATE TABLE `subproject`  (
  `Projectid` int(0) NOT NULL,
  `Subprojectid` int(0) NOT NULL,
  `Leaderid` int(0) NULL DEFAULT NULL,
  `deadline` date NULL DEFAULT NULL,
  `budget` double NULL DEFAULT NULL,
  `technicalindex` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  INDEX `project`(`Projectid`) USING BTREE,
  CONSTRAINT `project` FOREIGN KEY (`Projectid`) REFERENCES `project` (`Projectid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of subproject
-- ----------------------------
INSERT INTO `subproject` VALUES (1, 1, 5, '2023-01-01', 20000, 'High');
INSERT INTO `subproject` VALUES (1, 2, 5, '2023-02-01', 30000, 'Medium');
INSERT INTO `subproject` VALUES (2, 1, 8, '2022-10-01', 25000, 'Low');
INSERT INTO `subproject` VALUES (3, 1, 9, '2023-05-01', 40000, 'High');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NULL DEFAULT NULL,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '111111');

SET FOREIGN_KEY_CHECKS = 1;
