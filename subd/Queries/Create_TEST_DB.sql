USE master;
GO
CREATE DATABASE TEST_DB
ON
( NAME = Test_DB_dat,
  FILENAME = 'E:\Projects\University\7-semester\subd\TEST_DB.mdf',
  SIZE = 10,
  MAXSIZE = 50,
  FILEGROWTH = 5 )
  LOG ON
  (NAME = TEST_DB_log,
  FILENAME = 'E:\Projects\University\7-semester\subd\TEST_DB.ldf',
  MAXSIZE=25MB,
  FILEGROWTH=5MB );
GO