-- Sélectionner la base de données
DROP DATABASE IF EXISTS trading_db;
CREATE DATABASE trading_db;
USE trading_db;

-- Créer la table BidList
CREATE TABLE BidList (
  BidListId INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  bidQuantity DECIMAL(20, 2),
  askQuantity DECIMAL(20, 2),
  bid DECIMAL(20, 2),
  ask DECIMAL(20, 2),
  benchmark VARCHAR(125),
  bidListDate TIMESTAMP,
  commentary VARCHAR(125),
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (BidListId)
);

-- Créer la table Trade
CREATE TABLE Trade (
  TradeId INT NOT NULL AUTO_INCREMENT,
  account VARCHAR(30) NOT NULL,
  type VARCHAR(30) NOT NULL,
  buyQuantity DECIMAL(20, 2),
  sellQuantity DECIMAL(20, 2),
  buyPrice DECIMAL(20, 2),
  sellPrice DECIMAL(20, 2),
  tradeDate TIMESTAMP,
  security VARCHAR(125),
  status VARCHAR(10),
  trader VARCHAR(125),
  benchmark VARCHAR(125),
  book VARCHAR(125),
  creationName VARCHAR(125),
  creationDate TIMESTAMP,
  revisionName VARCHAR(125),
  revisionDate TIMESTAMP,
  dealName VARCHAR(125),
  dealType VARCHAR(125),
  sourceListId VARCHAR(125),
  side VARCHAR(125),
  PRIMARY KEY (TradeId)
);

-- Créer la table CurvePoint
CREATE TABLE CurvePoint (
  Id INT NOT NULL AUTO_INCREMENT,
  CurveId INT,
  asOfDate TIMESTAMP,
  term DECIMAL(20, 2),
  value DECIMAL(20, 2),
  creationDate TIMESTAMP,
  PRIMARY KEY (Id)
);

-- Créer la table Rating
CREATE TABLE Rating (
  Id INT NOT NULL AUTO_INCREMENT,
  moodysRating VARCHAR(125),
  sandPRating VARCHAR(125),
  fitchRating VARCHAR(125),
  orderNumber INT,
  PRIMARY KEY (Id)
);

-- Créer la table RuleName
CREATE TABLE RuleName (
  Id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(125),
  description VARCHAR(125),
  json VARCHAR(125),
  template VARCHAR(512),
  sqlStr VARCHAR(125),
  sqlPart VARCHAR(125),
  PRIMARY KEY (Id)
);

-- Créer la table Users
CREATE TABLE Users (
  Id INT NOT NULL AUTO_INCREMENT,
  username VARCHAR(125),
  password VARCHAR(125),
  fullname VARCHAR(125),
  role VARCHAR(125),
  PRIMARY KEY (Id)
);

-- Insérer les données utilisateurs (CORRIGE le point-virgule double)
INSERT INTO Users(fullname, username, password, role) 
VALUES("Administrator", "admin", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "ADMIN");

INSERT INTO Users(fullname, username, password, role) 
VALUES("User", "user", "$2a$10$pBV8ILO/s/nao4wVnGLrh.sa/rnr5pDpbeC4E.KNzQWoy8obFZdaa", "USER");