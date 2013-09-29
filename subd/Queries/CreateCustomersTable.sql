USE TEST_DB
GO
CREATE TABLE dbo.Customers
(
	CustomerId int PRIMARY KEY NOT NULL,
	Name varchar(30) NOT NULL,
	Email varchar(30) NOT NULL,
	Info text
)
GO